package controller;
import view.EquationView;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    DatabaseManager dbManager = new DatabaseManager();
                    EquationService equationService = new EquationService(dbManager);
                    EquationView equationView = new EquationView(equationService);

                    JFrame frame = new JFrame("EquationView");
                    frame.setContentPane(equationView.getPanelMain());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);

                    new EquationController(equationView, equationService);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
