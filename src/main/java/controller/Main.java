package controller;
import view.EquationView;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DatabaseManager dbManager = null;
                try {
                    dbManager = new DatabaseManager();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                EquationService equationService = new EquationService(dbManager);
                JFrame frame = new JFrame("EquationView");
                frame.setContentPane(new EquationView(equationService).getPanelMain());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
