package question5;

import java.util.Scanner;

public class Question5 {

    // A simple calculator program for the console.
    // The program runs forever, each time expecting
    // an input of a line with the format "{num1}[+,-,*,/]{num2}"
    // example: 5 * 5
    // example: 2+ 1
    // example: 12/3
    // It parses this line of information and performs the operations,
    // then printing the output.
    // to close the calculator, the user must input "exit" in either
    // lower case or upper case
    // the code is made up of the main and a Parser that reads the user info and parses
    // it.

    // test that this program works for the following inputs:
    // input: 5+2
    // expected: output 7
    // input: 1 * 2
    // expected: output 2
    // input: 55 /2
    // expected: output 27.5
    // input: 12.4- 1
    // expected: 11.4
    // input: 44     -     1
    // expected: output 43
    // input: exit
    // expected: clean program exit
    // input: Exit
    // expected: clean program exit
    // input: EXIT
    // expected: clean program exit
    // input: 22//1
    // expected: program prints of a bad input
    // input: 13# 213
    // expected: program prints of a bad input
    // input: 1231415
    // expected: program prints of a bad input

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.print('>');
            String line = in.nextLine();
            if (line == "EXIT") {
                break;
            }

            execute(line);
        }
    }

    private static void execute(String line) {
        Parser parser = new Parser(line);

        try {
            double number1 = parser.parseNumber();
            char operator = parser.parseOperator();
            double number2 = parser.parseNumber();

            double result = compute(number1, operator, number2);
            System.out.println(result);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    private static double compute(double number1, char operator, double number2) throws ParseException {
        switch (operator) {
            case '+':
                return number1 + number2;
            case '-':
                return number1 - number2;
            case '*':
                return number1 * number2;
            case '/':
                return number1 / number2;
            default:
                throw new ParseException("unknown operator");
        }
    }

}
