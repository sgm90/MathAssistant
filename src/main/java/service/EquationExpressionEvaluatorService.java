package service;

import service.utilForEquationSolver.EquationSolverUtil;

import java.util.Stack;

public class EquationExpressionEvaluatorService {
    public static double evaluateExpression(String expression) {
        String[] sides = expression.split("=");

        if (sides.length != 2) {
            throw new IllegalArgumentException("Invalid equation format");
        }

        double leftResult = evaluateSide(sides[0].trim());
        double rightResult = evaluateSide(sides[1].trim());

        return leftResult - rightResult;
    }

    private static double evaluateSide(String side) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < side.length(); i++) {
            char c = side.charAt(i);

            if (Character.isDigit(c)) {
                double num = EquationSolverUtil.parseNumber(side, i);
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