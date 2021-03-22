// Sahithi Yakkali
// TA: Hitesh Boinpally
// 3/9/2021
// Assignment 8: Huffman
// This is the implementation of a class called HuffmanNode. This class will be for the single
// nodes in a tree.
// This class can also compare HuffmanNodes to the current huffmanNode to see which is greater

import java.util.*;
public class HuffmanNode implements Comparable<HuffmanNode> {
   // keeps track of the left subnode
   public HuffmanNode zero;
   // keeps track of the right subnode
   public HuffmanNode one;
   // keeps track of the frequency of the character in the node
   public int freq;
   // keeps track of the character of the node
   public int charVal;

   // this constructor method creates a HuffmanNode which would be a leaf node, the character
   // value provided will be used to keep track of the character and the frequency passed will
   // be used to keep track of how many times the character appears
   public HuffmanNode(int freq, int charVal) {
       this(freq, charVal, null, null);
   }
   
   // this constructor method creates a HuffmanNode, the character value provided will be used to
   // keep track of the character and the frequency passed will be used to keep track of how many
   // times the character appears
   // the two HuffmanNodes passed will be stored as the subnodes of this HuffmanNode
   public HuffmanNode(int freq, int charVal, HuffmanNode zero, HuffmanNode one) {
      this.zero = zero;
      this.one = one;
      this.freq = freq;
      this.charVal = charVal;
   }

   // this method takes in another HuffmanNode to compare it to the one that is currently being 
   // considered, the HuffmanNodes are considered bigger if they have a greater frequency
   // it returns an int that is negative if this HuffmanNode is less than the other, 0 if it's 
   // equal or positive if it's greater than the other HuffmanNode
   public int compareTo(HuffmanNode other) {
      return this.freq - other.freq;
   }
}
