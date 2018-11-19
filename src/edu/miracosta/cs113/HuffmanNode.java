package edu.miracosta.cs113;

/**
 * @author Carsten Singleton
 * @Date 11/18/2018
 * @version 1.0
 *
 * Class: CS 113 MW 1:30 - 3:20
 * Homework 09 - HuffmanTree
 */

/**
 * Used to store the weight and character for the HuffmanTree PriorityQueue
 */
public class HuffmanNode {

    /////////////////////////////
    // Instance Variables
    /////////////////////////////
    private Character data;
    private int weight;

    //////////////////////////////////////////////////////////
    // Methods
    //////////////////////////////////////////////////////////

    /**
     * Default constructor.
     */
    public HuffmanNode() {
        this(null, 0);
    }

    /**
     * Full constructor.
     * @param data Data that node stores
     * @param weight Weight of the node in terms of priority
     */
    public HuffmanNode(Character data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    /**
     * Returns data variable.
     * @return data
     */
    public Character getData() {
        return this.data;
    }

    /**
     * Sets data variable to passed argument.
     * @param data
     */
    public void setData(Character data) {
        this.data = data;
    }

    /**
     * Returns weight variable.
     * @return weight
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * Sets weight bariable to passed argument.
     * @param weight
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Weight: " + this.weight + ", Data: " + this.data.toString();
    }
}
