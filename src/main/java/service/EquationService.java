package service;
import model.Equation;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquationService {
    public EquationService() {
    }

    static boolean isEquationBalanced(String equation) {
        // Розбиття рівняння на дві частини
        String[] parts = equation.split("=");
        // Обчислення значення кожної частини
        double leftValue = EquationSolver.calculateExpression(parts[0]);
        double rightValue = EquationSolver.calculateExpression(parts[1]);
        // Перевірка, чи результат відповідає вимогам
        return Math.abs(leftValue - rightValue) <= Math.pow(10, -9);
    }

    public static boolean isEquationValid(String equation) {
       return checkEquation(equation);
    }

    private static boolean checkEquation(String equation) {
        // Перевірка на відповідність регулярному виразу
        String regex = "^[-+]?\\d*\\.?\\d+([/*+-]-?\\d*\\.?\\d+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(equation);
        return matcher.matches();
    }
}


