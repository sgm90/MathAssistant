package controller;
import service.EquationService;
import view.EquationView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                EquationService equationService = new EquationService();
                EquationView equationView = new EquationView(equationService);

                JFrame frame = new JFrame("EquationView");
                frame.setContentPane(equationView.getPanelMain());
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

                new EquationController(equationView, equationService);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
