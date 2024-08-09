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
    // 5+2
    // 1 * 2
    // 55 /2
    // 12.4- 1
    // 44     -     1
    // exit
    // Exit
    // EXIT
    // for these we should expect the program to report a bad input
    // 22//1
    // 13# 213
    // 1231415

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.print('>');
            String line = in.nextLine();
            if (line.equals("EXIT")) {
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
