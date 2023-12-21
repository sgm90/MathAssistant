package controller;
import model.Equation;

import java.util.*;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquationService {
    private List<Equation> equations;

    public EquationService(DatabaseManager dbManager) {
        this.equations = new ArrayList<>();
    }

    public void addEquation(Equation equation) {
        if (isValidEquation(equation.getEquation()) && isRootValid(equation.getEquation(), equation.getRoot())) {
            this.equations.add(equation);
        }
    }

    public List<Equation> findEquationsByRoot(double root) {
        List<Equation> result = new ArrayList<>();
        for (Equation equation : this.equations) {
            if (equation.getRoot() == root) {
                result.add(equation);
            }
        }
        return result;
    }

    static boolean isValidEquation(String equation) {
        // Перевірка на відповідність регулярному виразу
        String regex = "^[-+]?\\d*\\.?\\d+([/*+-]-?\\d*\\.?\\d+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(equation);
        return matcher.matches();
    }

    static boolean isRootValid(String equation, double root) {
        // Розрахунок значення рівняння з коренем
        String replacedEquation = equation.replaceAll("x", String.valueOf(root));
        // Розбиття рівняння на дві частини
        String[] parts = replacedEquation.split("=");
        // Обчислення значення кожної частини
        double leftValue = calculateExpression(parts[0]);
        double rightValue = calculateExpression(parts[1]);
        // Перевірка, чи результат відповідає вимогам
        return Math.abs(leftValue - rightValue) <= Math.pow(10, -9);
    }

    private static double calculateExpression(String expression) {
        // Створення двох стеків для чисел та операторів
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // Якщо символ є числом, зчитайте всі наступні цифри
            if (Character.isDigit(c)) {
                int num = 0;
                while (Character.isDigit(c)) {
                    num = num * 10 + (c - '0');
                    i++;
                    if (i < expression.length())
                        c = expression.charAt(i);
                    else
                        break;
                }
                i--;
                // Додайте число до стеку чисел
                numbers.push((double) num);
            } else if (c == '(') {
                // Якщо символ є відкриваючою дужкою, додайте його до стеку операторів
                operators.push(c);
            } else if (c == ')') {
                // Обчисліть вираз у дужках
                while (operators.peek() != '(') {
                    numbers.push(applyOp(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.pop();
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                // Якщо символ є оператором, застосуйте попередній оператор, якщо пріоритет вищий
                while (!operators.empty() && hasPrecedence(c, operators.peek())) {
                    numbers.push(applyOp(operators.pop(), numbers.pop(), numbers.pop()));
                }
                // Додайте поточний оператор до стеку операторів
                operators.push(c);
            }
        }
        // Застосуйте усі залишені оператори до залишених чисел
        while (!operators.empty()) {
            numbers.push(applyOp(operators.pop(), numbers.pop(), numbers.pop()));
        }
        return numbers.pop();
    }

    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    private static double applyOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

}


