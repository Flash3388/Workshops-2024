package question2;

import java.util.Scanner;

public class Question2 {

    // the following program implements a simple console-based
    // hangman game. One user enters a word, and another user
    // must guess the word by guessing one letter at a time.
    //
    // at first, one user enters the word they want someone to guess.
    // then other users start doing the guessing.
    // each time, a user enters the letter they guess.
    //      if they already guessed the letter, the program lets them guess again
    //      if they guessed a letter in the word, the guessed part of the word is printed out
    //          if they guessed all the letter, the program ends congratulating them
    //      if they guessed a letter that is not in the word, the program reports they
    //          made a bad guess. After 5 bad guesses the program exits reporting to the user
    //          that they failed and telling them what the word was
    //
    // example:
    // Enter word: hey
    // The word to guess is: hey
    // Enter letter: h
    // h__
    // Enter letter: h
    // already guessed
    // Enter letter: hh
    // expected a single character
    // Enter letter: b
    // Bad guess
    // Enter letter: y
    // h_y
    // Enter letter: e
    // hey
    // Good Guess!
    // program exists
    //
    // the program has some problems though...

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter word: ");
        String word = in.nextLine();
        System.out.println("The word to guess is: " + word);

        int badGuesses = 0;
        String guessedLetters = "";

        while (badGuesses < 5) {
            System.out.println("Enter letter: ");
            String guessLine = in.nextLine();
            if (guessLine.length() == 1) {
                System.out.println("expected a single character");
                continue;
            }

            String character = String.valueOf(guessLine.charAt(0));

            if (guessedLetters.contains(character)) {
                System.out.println("Already guessed");
                continue;
            }

            guessedLetters += character;

            if (word.contains(character)) {
                boolean done = printGuessAndCheckIfDone(guessedLetters, word);
            } else {
                System.out.println("Bad guess");
                badGuesses++;
            }
        }

        System.out.println("Sorry, you've reached your max guesses");
        System.out.println("The word was: " + word);
    }

    private static boolean printGuessAndCheckIfDone(String guessedLetters, String word) {
        boolean guessedAll = true;
        for (int i = 0; i < word.length(); i++) {
            String character = String.valueOf(word.charAt(i));

            if (guessedLetters.contains(character)) {
                System.out.print(character);
            } else {
                guessedAll = false;
            }
        }

        System.out.println();

        return guessedAll;
    }
}
