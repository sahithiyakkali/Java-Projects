// Sahithi Yakkali
// TA: Hitesh Boinpally
// 3/2/2021
// Assignment 7: QuestionGame
// This is the implementation of a game called 20 Questions. In this game the client will choose
// a secret object and the computer will try to guess the object by asking only yes or no
// questions. If the computer guesses the object the computer wins and if it doesn't the client
// wins. This class will be for the single nodes in a tree.
public class QuestionNode {
    // stores either an object or a question
    public String data;
    // the left subtree of the node if the answer to the question is yes
    public QuestionNode yes;
    // the right subtree of the node if the answer to the question is yes
    public QuestionNode no;

    // this is a constructor method, it makes a Question node with text 
    // in it but null left or right subtrees
    public QuestionNode(String word) {
        this(word, null, null);
    }

    // this is a constructor method, it makes a Question node with text 
    // in it and also left and right subtrees for yes and no answers of the text
    public QuestionNode(String data, QuestionNode yes, QuestionNode no) {
        this.data = data;
        this.yes = yes;
        this.no = no;
    }
}
