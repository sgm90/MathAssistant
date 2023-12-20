package view;
import controller.EquationService;
import model.Equation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EquationView {
    private JTextField equationField;
    private JButton checkButton;
    private JLabel resultLabel;
    private JPanel panelMain;
    private JTextField rootField;
    private JButton searchButton;

    public JPanel getPanelMain() {
        return panelMain;
    }

    public EquationView(EquationService equationService) {
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String equation = equationField.getText();
                        if (equationService.isValidEquation(new Equation(equation, 0))) {
                            if (equationService.saveEquation(new Equation(equation, 0))) {
                                JOptionPane.showMessageDialog(panelMain, "Everything is fine, the equation is added to the database");
                            } else {
                                JOptionPane.showMessageDialog(panelMain, "Already in the database");
                            }
                        } else {
                            JOptionPane.showMessageDialog(panelMain, "The equation is NOT correct");
                        }
                    }
                }).start();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        double root = Double.parseDouble(rootField.getText());
                        List<Equation> equations = equationService.findEquationsByRoot(root);
                        if (!equations.isEmpty()) {
                            JOptionPane.showMessageDialog(panelMain, "Found equations: " + equations);
                        } else {
                            JOptionPane.showMessageDialog(panelMain, "No equations found with the given root");
                        }
                    }
                }).start();
            }
        });
    }
}