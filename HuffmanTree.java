// Sheng Chen, 1263891
// CSE 143 AM
// TA: VAN HOFWEGEN,RAQUEL N
// Programming Assignment #8: Huffman Coding

import java.util.*;
import java.io.*;

// This is the HuffmanTree class that handles the compression/decompression of a text file based
// on an algorithm devised by David A. Huffman. It abandons the tradition of 8 bites per character
// and uses different-length binary encodings for different characters based on their frequencies
// appeared in a text file.
public class HuffmanTree {
   
   // This private field stores the tree built with counts of each character(in ASCII 
   // form) in a text file.
   private HuffmanNode overallRoot;
   
   // Pre: The constructor of the class that takes in an integer array of count of each character
   //      (in ASCII form).
   // Post: It constructs a tree to store them in a way that characters with low frequencies end
   //       up far down in the tree and the ones with high frequencies end up near the top. 
   //       An “eof” character is added to represent the end of file.
   //       The value of this character is one greater than the value of the highest character in 
   //       the frequency array passed in and will have a frequency of 1.
   public HuffmanTree(int[] counts) {
      Queue<HuffmanNode> countQueue = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < counts.length; i++) {
         
         // Only account for characters that appear at least once.
         if (counts[i] != 0) {
            countQueue.add(new HuffmanNode(i, counts[i]));
         }
      }
      
      // Add an "eof" character.
      countQueue.add(new HuffmanNode(counts.length, 1));
      while (countQueue.size() > 1) {
         HuffmanNode child1 = countQueue.remove();
         HuffmanNode child2 = countQueue.remove();
         countQueue.add(new HuffmanNode(0, child1.count + child2.count, child1, child2));
      }
      overallRoot = countQueue.remove();
   }
   
   // Pre: This method takes in a PrintStream object.
   // Post: Writes out each character’s binary code to the PrintStream object, as a sequence 
   //       of line pairs in which the first line of each pair contains the ASCII value of a
   //       character, and the second line has the binary code for that character. The binary
   //       code is determined by going through the structure that contains these characters in
   //       pre-order and assigning 0s and 1s based on left or right.
   public void write(PrintStream output) {
      String binary = "";
      write(output, overallRoot, binary);
   }
   
   // Pre: A private helper method that takes in a PrintStream object, a HuffmanNode object and
   //      a string.
   // Post: Go through the tree that contains the characters from the text file and write out
   //       each one's binary code representation.
   private void write(PrintStream output, HuffmanNode root, String binary) {
      if (root.isLeaf()) {
         output.println(root.value);
         output.println(binary);
      }else {
         write(output, root.zero, binary += "0");
         
         // Unchoose.
         binary = binary.substring(0, binary.length() - 1);
         write(output, root.one, binary += "1");
      }
   }
   
   // Pre: The second constructor of the class that takes in a Scanner object of input. Assume
   //      the Scanner contains a tree from a text file that is in the standard format as 
   //      mentioned above.
   // Post: Constructs a tree that stores characters using the Scanner object.
   public HuffmanTree(Scanner input) {
      while (input.hasNext()) {
         overallRoot = HuffmanTreeBuilder(overallRoot, Integer.parseInt(input.nextLine()), 
                       input.nextLine());
      }
   }
   
   // Pre: A private helper method that takes in a HuffmanNode, an int and a string.
   // Post: Builds the tree based on information passed in.
   private HuffmanNode HuffmanTreeBuilder(HuffmanNode root, int value, String binary) {
      if (root == null) {
         root = new HuffmanNode();
      }
      
      // Base case.
      if (binary.length() == 0) {
      
         // The frequency is set to 0 because it is meaningless here in the second constructor.
         return new HuffmanNode(value, 0);
      }else {
      
         // Recursive case.
         if (binary.charAt(0) == '0') {
            root.zero = HuffmanTreeBuilder(root.zero, value, binary.substring(1));
         }else {
            root.one = HuffmanTreeBuilder(root.one, value, binary.substring(1));
         }
         return root;
      }
   }
   
   // Pre: Takes in a BitInputStream object, a PrintStream object and a int representing the "eof"
   //      character.
   // Post: Reads information from the BitInputStream object and writes out corresponding
   //       characters to the PrintStream object. Stops when it reads a value that's equal to the
   //       value of "eof" passed in. Assume that the BitInputStream object contains a legal 
   //       encoding of characters.
   public void decode(BitInputStream input, PrintStream output, int eof) {
      int currValue = decode(input, overallRoot);
      
      // If the current value is not equal to eof.
      while (currValue != eof) {
         output.write(currValue); 
         
         // Update current value.        
         currValue = decode(input, overallRoot);
      }
   }
   
   // Pre: private helper method that takesin a BitInputStream object, a int representing the "eof"
   //      and a HuffmanNode object.
   // Post: Returns the character value found using binary codes read from the BitInputStream
   //       object.
   private int decode(BitInputStream input, HuffmanNode root) {
      
      // Base case.
      if (root.isLeaf()) {
         return root.value;
      }else {
         
         // Recursive case.
         if (input.readBit() == 0) {
            return decode(input, root.zero);
         }else {
            return decode(input, root.one);
         } 
      } 
   } 
}