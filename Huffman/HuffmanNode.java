import java.util.*;

public class HuffmanNode implements Comparable<HuffmanNode> {
   public HuffmanNode zero;
   public HuffmanNode one;
   public int freq;
   public int charVal;

   public HuffmanNode(int freq, int charVal) {
       this(freq, charVal, null, null);
   }
   
   public HuffmanNode(int freq, int charVal, HuffmanNode zero, HuffmanNode one) {
      this.zero = zero;
      this.one = one;
      this.freq = freq;
      this.charVal = charVal;
   }

   public int compareTo(HuffmanNode other) {
      return this.freq - other.freq;
   }
}
