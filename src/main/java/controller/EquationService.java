package controller;

import model.Equation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquationService {
    private DatabaseManager dbManager;

    public EquationService(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public boolean isValidEquation(Equation equation) {
        String eq = equation.getEquation();

        // Перевірка на коректність розміщення дужок
        int balance = 0;
        for (char c : eq.toCharArray()) {
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                if (balance == 0) {
                    return false; // Зайва закриваюча дужка
                }
                balance--;
            }
        }
        if (balance != 0) {
            return false; // Зайва відкриваюча дужка
        }

        // Перевірка на коректність виразу
        String regex = "^[0-9x\\-+*/(). ]+$"; // Допустимі символи
        if (!eq.matches(regex)) {
            return false; // Вираз містить недопустимі символи
        }

        regex = "(^[-+*/])|([-+*/]{2,})|([-+*/]$)"; // Неприпустиме розташування операторів
        if (eq.matches(regex)) {
            return false; // Вираз містить два оператори, що йдуть один за одним, або починається/закінчується на оператор
        }

        regex = "/0"; // Ділення на нуль
        if (eq.contains(regex)) {
            return false; // Вираз містить ділення на нуль
        }

        return true; // Вираз є коректним
    }

    public boolean saveEquation(Equation equation) {
        // Збереження рівняння в БД
        try {
            dbManager.saveEquation(equation);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Equation> findEquationsByRoot(double root) {
        // Пошук рівнянь за коренями
        try {
            return dbManager.findEquationsByRoot(root);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
