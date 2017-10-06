// Sheng Chen, 1263891
// CSE 143 AM
// TA: VAN HOFWEGEN,RAQUEL N
// Programming Assignment #8: Huffman Coding

// This is the HuffmanNode class that describes the behavior of a HuffmanNode object.
public class HuffmanNode implements Comparable<HuffmanNode> {

   public int value; // ASCII value of the character.
   public int count; // Frequency of this character.
   public HuffmanNode zero; // reference to zero(left) subtree.
   public HuffmanNode one; // reference to one(right) subtree.
   
   // Constructs a empty HuffmanNode (default 0s and nulls).
   public HuffmanNode() {
      this(0, 0, null, null);
   }
   
   // Constructs a HuffmanNode with a value and a count.
   public HuffmanNode(int value, int count) {
      this(value, count, null, null);
   }

   // Constructs a HuffmanNode with a value, a count, a left node and a right node.
   public HuffmanNode(int value, int count, HuffmanNode zero, HuffmanNode one) {
      this.value = value;
      this.count = count;
      this.zero = zero;
      this.one = one;
   }
   
   // Pre: CompareTo method of the class so different nodes can be compared based on frequencies.
   //      Takes in a HuffmanNode.
   // Post: A node that appears less times is considered to be "less" than one that appears more
   //       frequently.
   public int compareTo(HuffmanNode other) {
      return this.count - other.count;
   }
   
   // Pre: A isLeaf method that returns true if the node has no zero(left) or one(right) subtree.
   public boolean isLeaf() {
      return zero == null && one == null;
   }
}