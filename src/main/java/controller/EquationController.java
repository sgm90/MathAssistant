package controller;
import DAO.EquationDAO;
import model.Equation;
import service.EquationService;
import service.EquationSolver;
import view.EquationView;
import javax.swing.*;
import java.sql.SQLException;
import java.util.List;
import static DAO.EquationDAO.saveEquation;
public class EquationController {
    private EquationView view;
    private EquationService equationService;
    EquationDAO equationDAO;

    public EquationController(EquationView view, EquationService equationService) throws SQLException {
        this.view = view;
        this.equationService = equationService;



        // Додаємо прослуховувач подій до кнопки перевірки
        view.getCheckButton().addActionListener(e -> {
            String equation = view.getEquationField().getText();
            if (equation.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please type in your equation");
                return;
            }
            if (!EquationService.isEquationValid(equation)) {
                JOptionPane.showMessageDialog(null, "Equation is not valid");
                return;
            }
            double root = EquationSolver.calculateExpression(equation);
            Equation equationObj = new Equation(equation);
            equationObj.setRoot(root);
            try {
                saveEquation(equationObj);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Equation is valid and added to DB");
        });


        // Додаємо прослуховувач подій до кнопки пошуку
        view.getSearchButton().addActionListener(e -> {
            String rootText = view.getRootField().getText();
            if (!rootText.isEmpty()) {
                double root = Double.parseDouble(rootText);

                List<Equation> equations;
                try {
                    equations = equationDAO.findEquationsByRoot(root);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String message = "Результати пошуку:\n";
                for (Equation equation : equations) {
                    message += equation.getEquation() + "\n";
                }
                JOptionPane.showMessageDialog(null, message);
            } else {
                JOptionPane.showMessageDialog(null, "Будь ласка, введіть корінь для пошуку");
            }
        });
    }
}