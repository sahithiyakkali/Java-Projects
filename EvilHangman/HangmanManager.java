// Sahithi Yakkali
// TA: Hitesh Boinpally
// 2/19/2021
// Assignment 4: EvilHangman
// This is the implementation of a game called EvilHangman that is a variation of Hangman.
// When the player guesses a letter this class uses the words in the dictionary and calls
// certain methods in order to modify the set of possible words the word could be so that the 
// player has a hard time guessing the word.

import java.util.*;

public class HangmanManager {
    private int numGuessesLeft; // keeps track of how many guesses the player has left
    private Set<String> wordsPossible; // keeps track of all the words that are Possible
    private Set<Character> lettersGuessed; // keeps track of all the letters player guessed
    private String patternString; // keeps track of the pattern of the word being guessed

    // this method is a constructor that takes in a dictionary of words, the length of the 
    // word being guessed, and the maximum number of guesses the user can have to guess the word
    // the method filters the words in the dictionary so that only words of a 
    // certain length are considered as possible words, it will also filter out duplicate words
    // assumption being made: the words in dictionary are all lowercase and are not empty strings
    // the method will throw an IllegalArgumentException if the length is less than 1 and/or the 
    // max is less than 0
    public HangmanManager(Collection<String> dictionary, int length, int max) {
        if (length < 1 || max < 0) {
            throw new IllegalArgumentException("Invalid length or max number.");
        }
        numGuessesLeft = max;
        wordsPossible = new TreeSet<>();
        for(String word : dictionary) {
            if (word.length() == length) {
                wordsPossible.add(word);
            }
        }
        lettersGuessed = new TreeSet<>();
        patternString = "";
        for (int i = 0; i < length; i++) {
            patternString = patternString + "- ";
        }
    }

    // this method returns what words HangmanManager is still considering
    public Set<String> words() {
        return wordsPossible;
    }

    // this method returns how many guesses the player has left
    public int guessesLeft() {
        return numGuessesLeft;
    }

    // this method returns what letters have already been guessed by the player
    public Set<Character> guesses() {
        return lettersGuessed;
    }

    // will throw an IllegalArgumentException if there are no words that can be considered
    // this method returns the pattern of the word being guessed
    public String pattern() {
        if (wordsPossible.isEmpty()) {
            throw new IllegalStateException();
        }
        return patternString.trim();
    }

    // returns the number of times the guessed letter, the parameter, occurs in the pattern
    // assumption being made: all the letter guesses are lowercase
    // after the guess is passed the set of guessed letters, the current pattern and set of words
    // being considered are all updated
    // if the guessed letter isn't in the new pattern the number of guesses left has is decreases
    // if the number of guesses left is less than 1 or the set of words possible is empty it will
    // throw an IllegalStateException and if the letter was already guessed it will throw an 
    // IllegalArgumentException
    public int record(char guess) {
        if (numGuessesLeft < 1 || wordsPossible.isEmpty()){
            throw new IllegalStateException();
        }
        if (lettersGuessed.contains(guess)) {
            throw new IllegalArgumentException("This letter was already guessed.");
        }
        lettersGuessed.add(guess);

        TreeMap<String, TreeSet<String>> patternChange = listOfPattern(guess);
        // Will update our patterns
        update(patternChange);
        // will keep our current word set updating
        return numGuessedLetter(guess);
    }

    // this method checks to see if the parameter, the guess, is in any of the words in the set
    // this method returns what patterns are available after that guess
    private TreeMap<String, TreeSet<String>> listOfPattern(char guess) {
        TreeMap<String, TreeSet<String>> setForPattern = new TreeMap<>();
        for (String word : wordsPossible){
            String currPattern = "";
            int num = 0;
            for (int i = 0; i < word.length(); i++){
                if (word.charAt(i) == guess){
                    currPattern += guess + " ";
                } else {
                    //currPattern += patternString.charAt(i);
                    currPattern += patternString.substring(num, num + 2);
                }
                num += 2;
            }
            // If the pattern is unique we want to group this as a new set of patterns
            if (!setForPattern.containsKey(currPattern)) {
                setForPattern.put(currPattern, new TreeSet<>());
            }
            // If not we want to find the pattern set it belongs with
            setForPattern.get(currPattern).add(word);
        }
        return setForPattern;
    }

    // this method updates the pattern list when more letters are guessed
    // this method also updates the set of words that HangmanManager considers as possible words
    // it will choose the family of words that has the most possible words in it
    // the parameter is where the patterns are taken and updated from
    private void update(TreeMap<String, TreeSet<String>> listPattern) {
        patternString = "";
        int num = 0;
        for (String pattern: listPattern.keySet()) {
            if (listPattern.get(pattern).size() > num) {
                patternString = pattern;
                num = listPattern.get(pattern).size();
                wordsPossible = listPattern.get(pattern);
            } 
        }
    }

    // this method returns how many guesses the player has left after the player enters a guess
    // the parameter is the guess the player enters
    private int numGuessedLetter(char guess) {
        int numOfGuess = 0;
        for (int i = 0; i < patternString.length(); i++) {
            if (patternString.charAt(i) == guess) {
                numOfGuess++;
            }
        }
        if (numOfGuess == 0) {
            numGuessesLeft--;
        }
        return numOfGuess;
    }
}
