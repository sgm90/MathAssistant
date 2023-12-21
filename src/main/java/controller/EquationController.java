package controller;

import model.Equation;
import view.EquationView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EquationController {
    private EquationView view;
    private EquationService service;

    public EquationController(EquationView view, EquationService service) {
        this.view = view;
        this.service = service;

        // Додаємо прослуховувач подій до кнопки перевірки
        view.getCheckButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String equation = view.getEquationField().getText();
                String rootText = view.getRootField().getText();
                if (!equation.isEmpty() && !rootText.isEmpty()) {
                    double root = Double.parseDouble(rootText);
                    Equation equationObj = new Equation(equation, root);
                    if (service.isValidEquation(equation) && service.isRootValid(equation, root)) {
                        service.addEquation(equationObj);
                        JOptionPane.showMessageDialog(null, "Рівняння вірне, тому додано в базу");
                    } else {
                        JOptionPane.showMessageDialog(null, "Рівняння невірне");
                    }
                } else {
                    if (equation.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Будь ласка, введіть рівняння");
                    }
                    if (rootText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Будь ласка, введіть корінь рівняння");
                    }
                }
            }
        });
        // Додаємо прослуховувач подій до кнопки пошуку
        view.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rootText = view.getRootField().getText();
                if (!rootText.isEmpty()) {
                    double root = Double.parseDouble(rootText);
                    List<Equation> equations = service.findEquationsByRoot(root);
                    String message = "Результати пошуку:\n";
                    for (Equation equation : equations) {
                        message += equation.getEquation() + "\n";
                    }
                    JOptionPane.showMessageDialog(null, message);
                } else {
                    JOptionPane.showMessageDialog(null, "Будь ласка, введіть корінь для пошуку");
                }
            }
        });
    }
}