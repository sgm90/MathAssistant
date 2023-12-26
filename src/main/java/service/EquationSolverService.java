package service;


import service.utilForEquationSolver.EquationSolverUtil;

public class EquationSolverService {
    public static double solveEquation(String equation) {
        EquationValidatorService.validateEquation(equation);

        if (equation.contains("x") && equation.indexOf("x") != equation.lastIndexOf("x")) {
            String expression = equation.replace("x", "1");
            double result = EquationExpressionEvaluatorService.evaluateExpression(expression);
            if (EquationSolverUtil.isRoot(equation, result)) {
                return result;
            } else {
                throw new IllegalArgumentException("The calculated result does not satisfy the specified condition.");
            }
        }

        double result = EquationExpressionEvaluatorService.evaluateExpression(equation);
        if (EquationSolverUtil.isRoot(equation, result)) {
            return result;
        } else {
            throw new IllegalArgumentException("The calculated result does not satisfy the specified condition.");
        }
    }
}
