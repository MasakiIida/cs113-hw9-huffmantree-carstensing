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

// NOTE: This is based off of lexicographical order and not chronological order

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

        // lexicographical ordering for adding to the huffman table
        characterFrequency[0] = new HuffmanNode('\t',0);
        characterFrequency[1] = new HuffmanNode('\n',0);
        characterFrequency[2] = new HuffmanNode(' ',0);
        characterFrequency[3] = new HuffmanNode('!',0);
        characterFrequency[4] = new HuffmanNode('.',0);

        for(int i = 0; i < 10; i++) {
            characterFrequency[i+5] = new HuffmanNode((char)(i+48),0);
        }

        characterFrequency[15] = new HuffmanNode('?',0);

        for(int i = 0; i < 26; i++) {
            characterFrequency[i+16] = new HuffmanNode((char)(i+65),0);
        }

        for(int i = 0; i < 26; i++) {
            characterFrequency[i+42] = new HuffmanNode((char)(i+97),0);
        }


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
