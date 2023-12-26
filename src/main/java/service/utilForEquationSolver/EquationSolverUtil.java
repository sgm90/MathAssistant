package service.utilForEquationSolver;

import java.util.Stack;

public class EquationSolverUtil {
    public static boolean hasPrecedence(char op1, char op2) {
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }

    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static double parseNumber(String expression, int start) {
        StringBuilder sb = new StringBuilder();

        while (start < expression.length() && (Character.isDigit(expression.charAt(start)) || expression.charAt(start) == '.')) {
            sb.append(expression.charAt(start));
            start++;
        }
        return Double.parseDouble(sb.toString());
    }
    public static double performOperation(double a, double b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
    public static void applyOperator(Stack<Double> numbers, char operator, Stack<Character> operators) {
        if (numbers.size() < 2) {
            throw new IllegalArgumentException("Invalid equation");
        }
        double b = numbers.pop();
        double a = numbers.pop();
        double result = performOperation(a, b, operator);
        numbers.push(result);
    }

    public static void processOperator(char operator, Stack<Double> numbers, Stack<Character> operators) {
        boolean hasOperand = !numbers.isEmpty();

        while (!operators.empty() && hasPrecedence(operator, operators.peek())) {
            char poppedOperator = operators.pop();
            if (isOperator(poppedOperator)) {
                applyOperator(numbers, poppedOperator, operators);
            } else {
                throw new IllegalArgumentException("Invalid operator: " + poppedOperator);
            }
        }
        operators.push(operator);
    }
    public static void processClosingParenthesis(Stack<Double> numbers, Stack<Character> operators) {
        while (!operators.empty() && operators.peek() != '(') {
            char poppedOperator = operators.pop();
            if (isOperator(poppedOperator)) {
                applyOperator(numbers, poppedOperator, operators);
            } else {
                throw new IllegalArgumentException("Invalid operator: " + poppedOperator);
            }
        }
        if (!operators.empty()) {
            operators.pop();
        } else {
            throw new IllegalArgumentException("Unbalanced brackets");
        }
    }

    public static boolean isRoot(String equation, double number) {
        double leftSide = evaluateExpressionForSide(equation, 'l', number);
        double rightSide = evaluateExpressionForSide(equation, 'r', number);
        double difference = Math.abs(leftSide - rightSide);
        return difference <= Math.pow(10, -9);
    }

    private static double evaluateExpressionForSide(String expression, char side, double number) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                double num = EquationSolverUtil.parseNumber(expression, i);
                numbers.push(num);
                i += Double.toString(num).length() - 1;
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                EquationSolverUtil.processClosingParenthesis(numbers, operators);
            } else if (EquationSolverUtil.isOperator(c)) {
                EquationSolverUtil.processOperator(c, numbers, operators);
            }
        }

        while (!operators.empty()) {
            char poppedOperator = operators.pop();
            if (EquationSolverUtil.isOperator(poppedOperator)) {
                EquationSolverUtil.applyOperator(numbers, poppedOperator, operators);
            } else {
                throw new IllegalArgumentException("Invalid operator: " + poppedOperator);
            }
        }

        return numbers.pop();
    }
}

