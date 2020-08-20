package com.company;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String tempNumber1 = getFirstNumber(str);
        String tempNumber2 = getSecondNumber(str);
        char operation = getOperation(str);

        int number1;
        int number2;
        boolean romanNumber = false;
        if (isNumeric(tempNumber1) && isNumeric(tempNumber2)) {
            number1 = Integer.parseInt(tempNumber1); // "9" -> 9
            number2 = Integer.parseInt(tempNumber2);
        } else {
            number1 = romanToArabic(tempNumber1); // "x" -> 10
            number2 = romanToArabic(tempNumber2);
            romanNumber = true;
        }
        isCorrectOperation(operation);
        isCorrectNumbers(number1, number2);
        int result = doOperation(number1, number2, operation);
        if (romanNumber) {
            System.out.println(arabicToRoman(result));
        } else {
            System.out.println(result);
        }
    }

    public static String getFirstNumber(String str) {
        StringBuilder number = new StringBuilder();
        str = str.replaceAll(" ", "");
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '/' || str.charAt(i) == '*') {
                break;
            }else {
                number.append(str.charAt(i));
            }
        }
        return number.toString();
    }

    public static String getSecondNumber(String str) {
        StringBuilder number = new StringBuilder();
        str = str.replaceAll(" ", "");
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '/' || str.charAt(i) == '*') {
                for (int j = i + 1; j < str.length(); j++) {
                    number.append(str.charAt(j));
                }
            }
        }
        return number.toString();
    }

    public static char getOperation(String str) {
        char operation = '%';
        str = str.replaceAll(" ", "");
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '/' || str.charAt(i) == '*') {
                operation = str.charAt(i);
            }
        }
        return operation;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    static void isCorrectOperation(char operation) throws Exception {
        if (operation != '+' && operation != '-' && operation != '*' && operation != '/') {
            throw new Exception("Incorrect Operation");
        }
    }

    static void isCorrectNumbers(int number1, int number2) throws Exception {
        if (number1 > 10 || number2 > 10 || number1 < 0 || number2 < 0) {
            throw new Exception("Your number should be between 1-10!");
        }
    }

    static int doOperation(int number1, int number2, char operation) {
        int result;
        switch (operation) {
            case '+' -> result = number1 + number2;
            case '-' -> result = number1 - number2;
            case '*' -> result = number1 * number2;
            case '/' -> result = number1 / number2;
            default -> result = -1;
        }

        return result;
    }

    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " cannot be converted to a Roman Numeral");
        }

        return result;
    }

    public static String arabicToRoman(int number) {
        if ((number <= 0) || (number >= 100)) {
            throw new IllegalArgumentException(number + " is not in range (0, 100]");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
}