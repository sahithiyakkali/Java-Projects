import java.util.*;
import java.io.*;

public class HuffmanCode {
    private HuffmanNode overallRoot;
    public HuffmanCode(int[] frequencies) {
//This constructor should initialize a new HuffmanCode object using the algorithm described for making
// a code from an array of frequencies. frequencies is an array of frequencies where frequences[i]
// is the count of the character with ASCII value i. Make sure to use a PriorityQueue to build the
// huffman code.
        Queue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>();
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                HuffmanNode node = new HuffmanNode(frequencies[i], i);
                queue.add(node);
            }
        }
        while (queue.size() > 1) {
            HuffmanNode firstNode = queue.remove();
            HuffmanNode secondNode = queue.remove();
            HuffmanNode combine = new HuffmanNode(firstNode.freq + secondNode.freq, -1, firstNode, secondNode);
            queue.add(combine);
        }
        overallRoot = queue.remove();
    }
    public HuffmanCode(Scanner input) {
        //overallRoot = new HuffmanNode(0, -1);
        while (input.hasNextLine()) {
            int asciiValue = Integer.parseInt(input.nextLine());
            String code = input.nextLine();
            overallRoot = makeTree(asciiValue, code, overallRoot);
        }
//This constructor should initialize a new HuffmanCode object by reading in a previously constructed
// code from a .code file. You may assume the Scanner is not null and is always contains data
// encoded in legal, valid standard format.
    }
    private HuffmanNode makeTree(int val, String code, HuffmanNode root) {
        if (code.isEmpty()) {
            root = new HuffmanNode(0, val);
        } else { 
            if (root == null) {
                root = new HuffmanNode(0, val);
            }
            String numFromCode = code.substring(0, 1);
            code = code.substring(1);
            if (numFromCode.equals("0")) {
                root.zero = makeTree(val, code, root.zero);
            } else {
                root.one = makeTree(val, code, root.one);
            }
        }
        return root;
    }

    public void save(PrintStream output) {
//This method should store the current huffman codes to the given output stream in the standard
// format described above.
        save(output, overallRoot, "");
    }
    private void save(PrintStream output, HuffmanNode root, String code) {
        if(root.zero == null && root.one == null) {
            output.println(root.charVal);
            output.println(code);
        } else {
            save(output, root.zero, code + "0");
            save(output, root.one, code + "1");
        }
    }
    public void translate(BitInputStream input, PrintStream output) {
        HuffmanNode root = overallRoot;
        while(input.hasNextBit()) {
            translate(input, output, overallRoot);
        }
// This method should read individual bits from the input stream and write the corresponding characters
// to the output. It should stop reading when the BitInputStream is empty. You may assume that
// the input stream contains a legal encoding of characters for this tree’s huffman code. See below
// for the methods that a BitInputStream has.
    }
    private void translate(BitInputStream input, PrintStream output, HuffmanNode root) {
        if (root.zero != null && root.one != null) {
                int zeroOrOne = input.nextBit();
                if (zeroOrOne == 0) {
                    translate(input, output, root.zero);
                } else {
                    translate(input, output, root.one);
                }
        } else {
            output.write(root.charVal);
        }
    }
}