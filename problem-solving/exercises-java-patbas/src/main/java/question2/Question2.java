package question2;

import java.util.Scanner;

public class Question2 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter word: ");
        String word = in.nextLine();
        System.out.println("The word to guess is: " + word);

        int badGuesses = 0;
        String guessedLetters = "";

        while (badGuesses < 5) {
            System.out.print("Enter letter: ");
            String guessLine = in.nextLine();
            if (guessLine.length() != 1) {
                System.out.println("expected a single character");
                continue;
            }

            String character = String.valueOf(guessLine.charAt(0));
            System.out.println("Guessed letter: " + character);

            if (guessedLetters.contains(character)) {
                System.out.println("Already guessed");
                continue;
            }

            guessedLetters += character;

            if (word.contains(character)) {
                boolean done = printGuessAndCheckIfDone(guessedLetters, word);
                if (done) {
                    break;
                }
            } else {
                System.out.println("Bad guess");
                badGuesses++;
            }
        }

        if (badGuesses < 5) {
            System.out.println("Good Guess!");
        } else {
            System.out.println("Sorry, you've reached your max guesses");
            System.out.println("The word was: " + word);
        }
    }

    private static boolean printGuessAndCheckIfDone(String guessedLetters, String word) {
        boolean guessedAll = true;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);

            if (ch == ' ' || ch == '-') {
                System.out.print(ch);
            } else if (guessedLetters.contains(String.valueOf(ch))) {
                System.out.print(ch);
            } else {
                guessedAll = false;
                System.out.print('_');
            }
        }

        System.out.println();

        return guessedAll;
    }
}
