// Sahithi Yakkali
// TA: Hitesh Boinpally
// 3/2/2021
// Assignment 7: QuestionGame
// This is the implementation of a game called 20 Questions. In this game the client will choose
// a secret object and the computer will try to guess the object by asking only yes or no
// questions. If the computer guesses the object the computer wins and if it doesn't the client
// wins. This class will be for the questions and answers of the game.
import java.util.*;
import java.io.*;

public class QuestionsGame {
    // will take in what the user inputs
    private Scanner console;
    // this is the root of the binary tree which has the questions and objects in it
    private QuestionNode overallRoot;

    // this is a constructor method that starts a new QuestionGame the binary tree will have
    // a leaf node with computer in it
    public QuestionsGame() {
        overallRoot = new QuestionNode("computer");
        console = new Scanner(System.in);
    }

    // this method is if the client wants to use another tree from instead of the one that's
    // being used currently, the scanner is to read the file
    // we are assuming that the file is legal and in the standard format
    public void read(Scanner input) {
        overallRoot = readTwo(input);
    }

    // this method is for helping read in a new tree instead of the current tree
    // it returns a Question tree and takes in a Scanner to help read the file
    private QuestionNode readTwo(Scanner input) {
        if (input.nextLine().equals("A:")) {
            return new QuestionNode(input.nextLine());
        } else {
            return new QuestionNode(input.nextLine(), readTwo(input), readTwo(input));
        }
    }

    // this method is for storing the current tree to an output file that is given by the
    // PrintStream passed as the parameter
    // this way if the client wants to they can play another game using questions from this game
    // the output file that is in standard format/pre-order format
    public void write(PrintStream output) {
        writeTwo(output, overallRoot);
    }

    // this method is for storing the current tree to an output file that is given by the
    // PrintStream passed as the parameter, the root is also passed as a parameter so that
    // it can fully be read and written in the output
    private void writeTwo(PrintStream output, QuestionNode root) {
        if(root.yes == null && root.no == null) {
            output.println("A:");
            output.println(root.data);
        } else {
            output.println("Q:");
            output.println(root.data);
            writeTwo(output, root.yes);
            writeTwo(output, root.no);
        }
    }

    // This method plays the guessing game with the client by asking yes or no questions
    // it'll modify and add in a new question and answer if it doesn't guess the object correctly
    public void askQuestions() {
        overallRoot = askQuestionsTwo(overallRoot);
    }

    // This method helps with asking the right questions according to the root node that is passed
    // as a parameter to it, this method returns a new question tree
    // it guesses asking the yes or no questions it currently has and if it can't guess the object
    // it will ask for the object the client was thinking of and a yes or no question to help
    // guess that object
    private QuestionNode askQuestionsTwo(QuestionNode root) {
        if (root.yes == null && root.no == null) {
            if(yesTo("Would your object happen to be " + root.data + "?")) {
                System.out.println("Great, I got it right!");
            } else {
                System.out.print("What is the name of your object? ");
                QuestionNode object = new QuestionNode(console.nextLine());
                System.out.println("Please give me a yes/no question that");
                System.out.println("distinguishes between your object");
                System.out.print("and mine--> ");
                String question = console.nextLine();
                if (yesTo("And what is the answer for your object?")) {
                    root = new QuestionNode(question, object, root);
                } else {
                    root = new QuestionNode(question, root, object);
                }
            }
        } else {
            if (yesTo(root.data)) {
                root.yes = askQuestionsTwo(root.yes);
            } else {
                root.no = askQuestionsTwo(root.no);
            }
        }
        return root;
    }

    // Do not modify this method in any way
    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}
