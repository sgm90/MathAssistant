package service.utilForEquationValidator;

public class EquationOperatorValidator {
    public static boolean isValidOperatorPlacement(String equation) {
        // Видаляємо пробіли для спрощення перевірки
        equation = equation.replaceAll("\\s+", "");

        // Перевіряємо, чи є ділителі на нуль
        if (equation.contains("/0")) {
            return false;
        }

        // Перевіряємо, чи є неправильне використання операторів
        String operators = "+-*/=";
        for (int i = 0; i < equation.length() - 1; i++) {
            char current = equation.charAt(i);
            char next = equation.charAt(i + 1);

            if (operators.indexOf(current) != -1 && operators.indexOf(next) != -1) {
                return false; // Два оператори підряд
            }
        }

        return true;
    }
}