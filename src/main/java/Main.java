import controller.EquationController;
import service.EquationValidatorService;
import view.EquationView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                EquationValidatorService validator = new EquationValidatorService();
                EquationView equationView = new EquationView();

                JFrame frame = new JFrame("EquationView");
                frame.setContentPane(equationView.getPanelMain());
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

                new EquationController(equationView);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
