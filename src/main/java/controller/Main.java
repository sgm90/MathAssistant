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
                    DatabaseManager dbManager = new DatabaseManager("jdbc:postgresql://localhost/mydatabase", "myuser", "mypassword");
                    EquationService equationService = new EquationService(dbManager);
                    JFrame frame = new JFrame("EquationView");
                    frame.setContentPane(new EquationView(equationService).getPanelMain());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
