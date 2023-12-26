package controller;
import DAO.EquationDAO;
import model.Equation;
import service.EquationSolverService;
import service.EquationValidatorService;
import view.EquationView;
import javax.swing.*;
import java.sql.SQLException;
import java.util.List;
public class EquationController {
    private EquationView view;
    private EquationValidatorService validator;
    private EquationSolverService equationSolverService;
    private EquationDAO equationDAO;
    public EquationController(EquationView view) throws SQLException {
        this.view = view;
        this.equationDAO = new EquationDAO();
        this.equationSolverService = new EquationSolverService();

        // Додаємо прослуховувач подій до кнопки перевірки
        view.getCheckButton().addActionListener(e -> {
            String equation = view.getEquationField().getText();
            if (equation.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please type in your equation");
                return;
            }

            try {
                if (EquationValidatorService.validateEquation(equation)) {
                    double root = EquationSolverService.solveEquation(equation);
                    Equation equationObj = new Equation(equation);
                    equationObj.setRoot(root);
                    saveEquation(equationObj);
                    JOptionPane.showMessageDialog(null, "Equation is valid and added to DB");
                } else {
                    JOptionPane.showMessageDialog(null, "Equation is not valid");
                }

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });

        // Додаємо прослуховувач подій до кнопки пошуку
        view.getSearchButton().addActionListener(e -> {
            String rootText = view.getRootField().getText();
            if (!rootText.isEmpty()) {
                double root = Double.parseDouble(rootText);

                try {
                    List<Equation> equations = equationDAO.findEquationsByRoot(root);
                    String message = "Результати пошуку:\n";
                    for (Equation equation : equations) {
                        message += equation.getEquation() + "\n";
                    }
                    JOptionPane.showMessageDialog(null, message);

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Будь ласка, введіть корінь для пошуку");
            }
        });
    }

    private void saveEquation(Equation equation) throws SQLException {
        equationDAO.saveEquation(equation);
    }
}