// Sahithi Yakkali
// TA: Hitesh Boinpally
// 3/9/2021
// Assignment 8: Huffman
// This is the implementation of a the main Huffman program. This class takes a file and 
// compresses it using binary digits so that it can occupy less space.
import java.util.*;
import java.io.*;

public class HuffmanCode {
    // this is the root of the binary tree which has the letter and the frequency of that letter
    private HuffmanNode overallRoot;

    // this is a constructor method that takes an array of frequencies of each letter
    // this method initialize a new HuffmanCode object
    public HuffmanCode(int[] frequencies) {
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
            int freqs = firstNode.freq + secondNode.freq;
            HuffmanNode combine = new HuffmanNode(freqs, -1, firstNode, secondNode);
            queue.add(combine);
        }
        overallRoot = queue.remove();
    }

    // this is a constructor method that uses the input provided through the scanner
    // to initialize a new HuffmanCode object,  here we assume that
    // the input is not null and will always contain data that is legal and in standard format
    public HuffmanCode(Scanner input) {
        //overallRoot = new HuffmanNode(0, -1);
        while (input.hasNextLine()) {
            int asciiValue = Integer.parseInt(input.nextLine());
            String code = input.nextLine();
            overallRoot = makeTree(asciiValue, code, overallRoot);
        }
    }

    // this method helps make a tree of all the letters and returns a huffman node
    // it's parameters are the integer ascii value, the binary code to the node so far, and the
    // huffman node root
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

    // this method stores the current huffman codes to the given output stream in the standard
    // format described above
    public void save(PrintStream output) {
        save(output, overallRoot, "");
    }

    // this method stores the current huffman codes to the given output stream in the standard
    // format described above the other parameters this method needs other than the output stream
    // are the root that the method is currently on and the huffman code for that root so far
    private void save(PrintStream output, HuffmanNode root, String code) {
        if(root.zero == null && root.one == null) {
            output.println(root.charVal);
            output.println(code);
        } else {
            save(output, root.zero, code + "0");
            save(output, root.one, code + "1");
        }
    }

    // the method reads bits from the input stream and writes the characters according to those
    // bits to the output stream, here we are assuming that the input stream contains a legal
    // encoding of characters for the huffman code tree
    // the parameters are the input stream and the output stream
    public void translate(BitInputStream input, PrintStream output) {
        HuffmanNode root = overallRoot;
        while(input.hasNextBit()) {
            translate(input, output, overallRoot);
        }
    }

    // this method helps read bits from the input stream and writes the characters according to
    // those bits to the output stream, here we are assuming that the input stream contains a
    // legal encoding of characters for the huffman code tree
    // the parameters are the input stream, the output stream, the node that the method is on
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
