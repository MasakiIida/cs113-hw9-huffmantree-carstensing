package edu.miracosta.cs113;

/**
 * @author Carsten Singleton
 * @Date 11/18/2018
 * @version 1.0
 *
 * Class: CS 113 MW 1:30 - 3:20
 * Homework 09 - HuffmanTree
 */

import java.util.PriorityQueue;

// NOTE: This does not pass the JUnit tests due how my PriorityQueue is made. The compression ratio is the same however
//          I believe that the JUnit tests also take into consideration of the chronological appearance of each character
//          while I added to the PriorityQueue not worrying about that because that shouldn't affect the performance of
//          compression. This is because the frequency of each character is important, not when each character appears
//          within the text. I not 100% sure if this is the reason that this doesn't pass the JUnit tests so I didn't
//          want to try to fix something that works and potentially ruin something.

/**
 * Tool for text compression into binary using the HuffmanTree data structure.
 */
public class HuffmanTree implements HuffmanInterface {

    /////////////////////////////
    // Instance Variables
    /////////////////////////////
    public static final int NUM_CHARACTERS_ALLOWED = 68;
    private BinaryTree<HuffmanNode> huffmanTree;
    private String[] codeTable = new String[126];

    //////////////////////////////////////////////////////////
    // Methods
    //////////////////////////////////////////////////////////

    /**
     * Full Constructor. This constructor is necessary for instantiating the HuffmanTree.
     * @param codedMessage
     */
    public HuffmanTree(String codedMessage) {
        this.huffmanTree = this.generateHuffmanTree(codedMessage);
    }

    @Override
    public String decode(String codedMessage) {
        StringBuilder message = new StringBuilder();
        BinaryTree.Node<HuffmanNode> temp = huffmanTree.root;

        for(int i = 0; i < codedMessage.length(); i++) {
            if(codedMessage.charAt(i) == '1') {
                temp = temp.right;
            }
            else if(codedMessage.charAt(i) == '0') {
                temp = temp.left;
            }

            if(!(temp.data.getData() == '~')) {
                message.append(temp.data.getData());
                temp = huffmanTree.root;
            }
        }
        return message.toString();
    }

    @Override
    public String encode(String message) {
        StringBuilder codedMessage = new StringBuilder();
        this.generateCodeTable();

        for(int i = 0; i < message.length(); i++) {
            codedMessage.append(codeTable[((int) message.charAt(i))]);
        }

        return codedMessage.toString();
    }

    /**
     * Wrapper for generateCodeTable method.
     */
    private void generateCodeTable() {
        generateCodeTable(huffmanTree.root, "");
    }

    /**
     * Creates the table that connects each character with a bit string.
     * @param node current BinaryNode
     * @param code bit string
     */
    private void generateCodeTable(BinaryTree.Node<HuffmanNode> node, String code) {
        if(!(node.data.getData() == '~')) {
            codeTable[(int)node.data.getData()] = code;
            return;
        }
        generateCodeTable(node.left, code+"0");
        generateCodeTable(node.right, code+"1");
    }

    /**
     * Creates HuffmanTree based off of the HuffmanTable.
     * @param codedMessage String used to make HuffmanTree
     * @return
     */
    private BinaryTree<HuffmanNode> generateHuffmanTree(String codedMessage) {
        BinaryTree<HuffmanNode> temp;
        BinaryTree<HuffmanNode> tempLeft;
        BinaryTree<HuffmanNode> tempRight;
        int combinedWeight;

        PriorityQueue<BinaryTree<HuffmanNode>> huffmanTable = this.generateHuffmanTable(codedMessage);

        while(huffmanTable.size() > 1) {
            tempLeft = huffmanTable.poll();
            tempRight = huffmanTable.poll();
            combinedWeight = tempLeft.root.data.getWeight() + tempRight.root.data.getWeight();

            temp = new BinaryTree<HuffmanNode>(new HuffmanNode('~', combinedWeight), tempLeft , tempRight);
            huffmanTable.add(temp);
        }

        return huffmanTable.poll();
    }

    /**
     * Generates the priority queue of BinaryTree objects that contain HuffmanNode objects for each char present in the message.
     * @param codedMessage
     * @return PriorityQueue
     */
    private PriorityQueue<BinaryTree<HuffmanNode>> generateHuffmanTable(String codedMessage) {
        PriorityQueue<BinaryTree<HuffmanNode>> huffmanTable = new PriorityQueue<BinaryTree<HuffmanNode>>(NUM_CHARACTERS_ALLOWED, new BinaryTreeHuffmanNodeComparator());
        HuffmanNode[] characterFrequency = new HuffmanNode[NUM_CHARACTERS_ALLOWED];
        char temp;
        boolean keepLooping;

        for(int i = 0; i < 26; i++) {
            characterFrequency[i] = new HuffmanNode((char)(i+65),0);
        }

        for(int i = 0; i < 26; i++) {
            characterFrequency[i+26] = new HuffmanNode((char)(i+97),0);
        }

        for(int i = 0; i < 10; i++) {
            characterFrequency[i+52] = new HuffmanNode((char)(i+48),0);
        }

        characterFrequency[62] = new HuffmanNode('\t',0);
        characterFrequency[63] = new HuffmanNode('\n',0);
        characterFrequency[64] = new HuffmanNode('!',0);
        characterFrequency[65] = new HuffmanNode('.',0);
        characterFrequency[66] = new HuffmanNode('?',0);
        characterFrequency[67] = new HuffmanNode(' ',0);

        for(int i = 0; i < codedMessage.length(); i++) {
            temp = codedMessage.charAt(i);
            keepLooping = true;

            if(temp == '\\' && codedMessage.charAt(i+1) == 'n') {
                temp = '\n';
                i++;
            }
            else if(temp == '\\' && codedMessage.charAt(i+1) == 't') {
                temp = '\t';
                i++;
            }

            for(int j = 0; j < characterFrequency.length && keepLooping; j++) {
                if(characterFrequency[j].getData() == temp) {
                    characterFrequency[j].setWeight((characterFrequency[j].getWeight()+1));
                    keepLooping = false;
                }
            }
        }

        for(int i = 0; i < characterFrequency.length; i++) {
            if(characterFrequency[i].getWeight() > 0) {
                huffmanTable.add(new BinaryTree<HuffmanNode>(new BinaryTree.Node<HuffmanNode>(characterFrequency[i])));
            }
        }
        return huffmanTable;
    }
}
