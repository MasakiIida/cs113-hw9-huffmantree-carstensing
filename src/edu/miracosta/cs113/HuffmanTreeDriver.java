package edu.miracosta.cs113;

/**
 * @author Carsten Singleton
 * @Date 11/18/2018
 * @version 1.0
 *
 * Class: CS 113 MW 1:30 - 3:20
 * Homework 09 - HuffmanTree
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Driver that utilizes the HuffmanTree Class to allow the user to enter a URL, have it translated into text and have
 * that compressed into a bit string.
 */
public class HuffmanTreeDriver {

    /* General Algorithm:
        Get a URL from the user
        Check if URL is valid and is able to be sent to a file
        Repeat until a valid URL is entered and text file is created
        Take the original file just created and use that text to instantiate a HuffmanTree
        Then send the same text to be encoded into a bit string by the HuffmanTree
        Take the encoded text and send that to be decoded by the HuffmanTree
        Send both the encoded and decoded to two respective text files
        The original file and the decoded file should be the exact same
        Count all of the bits in each file and output to the screen (16 bits per character, or 1 bit per 0 or 1)
        Output the compression ratio (original bit count / encoded bit count)
    */

    public static void main(String[] args) {
        TextFileGenerator textFileGenerator = new TextFileGenerator();
        HuffmanTree huffmanTree;
        Scanner keyboard = new Scanner(System.in);
        PrintWriter encodedOutputStream = null;
        PrintWriter decodedOutputStream = null;
        String url, originalString = "", encodedString, decodedString;
        boolean badURL = true;
        int originalBits, encodedBits, decodedBits;

        while(badURL) {
            System.out.println("Please enter a URL: ");
            url = keyboard.nextLine();

            try {
                originalString = textFileGenerator.makeCleanFile(url, "original.txt");
                badURL = false;
            }
            catch(IOException e) {
                System.out.println("Error: Please try again");
            }
        }

        huffmanTree = new HuffmanTree(originalString);
        encodedString = huffmanTree.encode(originalString);
        decodedString = huffmanTree.decode(encodedString);
        // NOTE: TextFileGenerator.getNumChars() was not working for me. I copied the encoded text file (pure 0s and 1s)
        //  into google docs and the character count is accurate with the String length and not the method
        originalBits = originalString.length() * 16;
        encodedBits = encodedString.length();
        decodedBits = decodedString.length() * 16;

        decodedString = fixPrintWriterNewLine(decodedString);

        try { // creating the encoded and decoded files
            encodedOutputStream = new PrintWriter(new FileOutputStream("src\\edu\\miracosta\\cs113\\encoded.txt"));
            decodedOutputStream = new PrintWriter(new FileOutputStream("src\\edu\\miracosta\\cs113\\decoded.txt"));
            encodedOutputStream.print(encodedString);
            decodedOutputStream.print(decodedString);
        }
        catch(IOException e) {
            System.out.println("Error: IOException!");
        }

        encodedOutputStream.close();
        decodedOutputStream.close();

        System.out.println("Original file bit count: " + originalBits);
        System.out.println("Encoded file bit count: " + encodedBits);
        System.out.println("Decoded file bit count: " + decodedBits);
        System.out.println("Compression ratio: " + (double)originalBits/encodedBits);
    }

    /**
     * Adds \r escape sequence to all \n occurrences in String so that PrintWriter will write newlines.
     * @param decodedString
     * @return String  with \r escape sequences added
     */
    private static String fixPrintWriterNewLine(String decodedString) {
        StringBuilder fixedString = new StringBuilder();
        for(int i = 0; i < decodedString.length(); i++) {

            if(decodedString.charAt(i) == '\n' ) {
                fixedString.append("\r\n");
            }
            else {
                fixedString.append(decodedString.charAt(i));
            }
        }
        return fixedString.toString();
    }
}
