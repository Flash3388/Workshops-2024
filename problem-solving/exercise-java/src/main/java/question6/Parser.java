package question6;

public class Parser {

    // this class is responsible for parsing the user input.
    // consider: the user provides the following input "2 + 55"
    // how do we extract the operands and operator from this string?
    // the concept is simple: we know what we expect to see in the string
    // at any given moment. At the beginning we expected to see a number, afterwards there
    // should be an operator and later another number.
    // For each of these parts, we need to analyze each character in the string
    // to find the entire number. So we start at index 0 and go over all the characters
    // as long as they compose a valid number (are digits). Once we reach a character that
    // is not a valid part of a number (say, a letter) then we stop reading the number.
    // this is the basic idea.

    private static final char[] VALID_OPERATORS = {
            '+', '-', '*', '/'
    };

    private final String line;
    private int currentIndex;

    public Parser(String line) {
        this.line = line;
        currentIndex = 0;
    }

    public boolean isAtEnd() {
        return currentIndex >= line.length();
    }

    public double parseNumber() throws ParseException {
        eatWhitespace();

        StringBuilder chars = new StringBuilder();
        int startIndex = currentIndex;
        while (currentIndex < line.length()) {
            char ch = eat();

            if (Character.isDigit(ch)) {
                chars.append(ch);
            } else if (currentIndex > startIndex && ch == '.') {
                chars.append(ch);
            } else {
                // end of number
                break;
            }
        }

        String numberTxt = chars.toString();
        try {
            return Double.parseDouble(numberTxt);
        } catch (NumberFormatException e) {
            // not a valid number
            throw new ParseException("not a valid number: " + numberTxt);
        }
    }

    public char parseOperator() throws ParseException {
        eatWhitespace();

        char ch = eat();
        if (!isOperator(ch)) {
            throw new ParseException("not valid operator: " + ch);
        }

        return ch;
    }

    private void eatWhitespace() {
        while (currentIndex < line.length()) {
            char ch = line.charAt(currentIndex);
            currentIndex++;

            if (!Character.isWhitespace(ch)) {
                break;
            }
        }
    }

    private char eat() throws ParseException {
        char ch = line.charAt(currentIndex);
        currentIndex++;

        return ch;
    }

    private static boolean isOperator(char ch) {
        for (char operator : VALID_OPERATORS) {
            if (operator == ch) {
                return true;
            }
        }

        return false;
    }
}
