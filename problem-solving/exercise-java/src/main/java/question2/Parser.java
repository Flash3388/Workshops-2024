package question2;

public class Parser {

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
                currentIndex--;
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

            if (Character.isWhitespace(ch)) {
                currentIndex++;
            } else {
                break;
            }
        }
    }

    private char eat() throws ParseException {
        if (isAtEnd()) {
            throw new ParseException("missing data");
        }

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
