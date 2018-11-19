package edu.miracosta.cs113;

/**
 * @author Carsten Singleton
 * @Date 11/18/2018
 * @version 1.0
 *
 * Class: CS 113 MW 1:30 - 3:20
 * Homework 09 - HuffmanTree
 */

import java.util.Comparator;

/**
 * Comparator for HuffmanTree PriorityQueue
 */
public class BinaryTreeHuffmanNodeComparator implements Comparator<BinaryTree<HuffmanNode>> {

    @Override
    public int compare(BinaryTree<HuffmanNode> o1, BinaryTree<HuffmanNode> o2) {
        if(o1.root.data.getWeight() < o2.root.data.getWeight()){
            return -1;
        }
        else if(o1.root.data.getWeight() >  o2.root.data.getWeight()){
            return 1;
        }
        else {
            return 0;
        }
    }
}
