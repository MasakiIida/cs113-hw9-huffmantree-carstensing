package edu.miracosta.cs113;

/**
 * @author Nery and Company
 * @Date 11/18/2018
 * @version 1.0
 *
 * Class: CS 113 MW 1:30 - 3:20
 * Homework 09 - HuffmanTree
 */

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * HuffmanTreeTest : Tester class for Huffman tree implementation.
 *
 * @author King
 * @version 1.0
 */
public class HuffmanTreeTest {

    /** Original and expected decoded values. */
    private static final String[] DECODED = {"Hello World!!! Ready for Spring 2019?",
                                        "the\tquick\tbrown\tfox\tjumps\tover\tthe\tlazy\tdog\t\t?!\n\n",
                                        "while walking wearily home...\ni wondered where wally was.\n",
                                        "Mike made mellow music with his nice new Neumann microphone."};

    /** Encoded values based on their own Huffman tree. */
    private static final String[] ENCODED = {"001000110111011101101101000011101111111100111110011001100101000100110" +
                    "010000111001101010011111011111101010100101111110001110011100001011000101001100100000000101",
            "11110100111000011110101101000111111011110011011111111011110100000000101011101111010111000011110011101" +
                    "010110111111000011011010100100100010111011111010011100001100101001100000100010010010010101011" +
                    "0001011101101100101100011000",
            "01001101101111100110001011001111111000110101110111001100010001110000011101111100001000110011111110110" +
                    "011011101110111010111011000100111101110101000010001001101001000100110001000100110001011001111" +
                    "111100001000101100111010101110101",
            "10010000010001001111011111010110010101111011110111011010110001001011101111100111010000000111100101000" +
                    "100011010011001000001010011011100000011011110111001101011101011100111001111111010111101110110" +
                    "111100000111011110010100000010000101110011100001"};

    /** A HuffmanTree to be built for each new String value. */
    HuffmanTree tree;

    @Test
    public void testDecodedValues() {
        for (int i = 0; i < DECODED.length; i++) {
            tree = new HuffmanTree(DECODED[i]);
            assertEquals("", ENCODED[i], tree.encode(DECODED[i]));
        }
    }

} // End of class HuffmanTreeTest
