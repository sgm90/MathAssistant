package service;

import java.util.Stack;

public class EquationSolver {
    public static double calculateExpression(String expression) {
        return doTheLogicForCalculation(expression);
    }

    private static double doTheLogicForCalculation(String expression){

        Stack<Double> numbers = new Stack<Double>();
        Stack<Character> operators = new Stack<Character>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c)) {
                double num = parseNumber(expression, i);
                numbers.push(num);
                i += Double.toString(num).length() - 1;
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                processClosingBracket(numbers, operators);
                operators.pop();
            } else if (isOperator(c)) {
                while (!operators.empty() && hasPrecedence(c, operators.peek())) {
                    applyOperator(numbers, operators);
                }
                operators.push(c);
            }
        }
        while (!operators.empty()) {
            applyOperator(numbers, operators);
        }
        return numbers.pop();
    }

    private static void applyOperator(Stack<Double> numbers, Stack<Character> operators) {
        double b = numbers.pop();
        double a = numbers.pop();
        char operator = operators.pop();
        double result;
        switch (operator) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                if (b == 0) {
                    throw new IllegalArgumentException("Cannot divide by zero");
                }
                result = a / b;
                break;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
        numbers.push(result);
    }

    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }


    private static void processClosingBracket(Stack<Double> numbers, Stack<Character> operators) {
        while (operators.peek() != '(') {
            applyOperator(numbers, operators);
        }
    }

    private static double parseNumber(String expression, int start) {
        StringBuilder sb = new StringBuilder();
        while (start < expression.length() && (Character.isDigit(expression.charAt(start)) || expression.charAt(start) == '.')) {
            sb.append(expression.charAt(start));
            start++;
        }
        return Double.parseDouble(sb.toString());
    }

}