package view;
import controller.EquationService;
import model.Equation;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

public class EquationView {
    private JTextField equationField;
    private JButton checkButton;
    private JPanel panelMain;
    private JTextField rootField;
    private JButton searchButton;
    private JLabel equationLabel;
    private JLabel rootLabel;

    public JPanel getPanelMain() {
        return panelMain;
    }

    public EquationView(EquationService equationService) {
        panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));

        JPanel equationPanel = new JPanel(); // панель для розміщення компонентів в першому рядку
        equationPanel.setLayout(new BoxLayout(equationPanel, BoxLayout.X_AXIS));

        JPanel rootPanel = new JPanel(); // панель для розміщення компонентів в другому рядку
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.X_AXIS));

        equationLabel = new JLabel("Equation:");
        equationField = new JTextField(20);
        checkButton = new JButton("Check");

        rootLabel = new JLabel("Root:");
        rootField = new JTextField(20);
        searchButton = new JButton("Search");

        // Збільшуємо розміри компонентів та тексту в 5 разів
        int scaleFactor = 5;
        Font currentFont = equationLabel.getFont();
        Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() * scaleFactor);
        equationLabel.setFont(newFont);
        equationField.setFont(newFont);
        checkButton.setFont(newFont);
        rootLabel.setFont(newFont);
        rootField.setFont(newFont);
        searchButton.setFont(newFont);

        // Додаємо компоненти до equationPanel
        equationPanel.add(equationLabel);
        equationPanel.add(equationField);
        equationPanel.add(checkButton);

        // Додаємо компоненти до rootPanel
        rootPanel.add(rootLabel);
        rootPanel.add(rootField);
        rootPanel.add(searchButton);

        // Додаємо equationPanel та rootPanel до panelMain
        panelMain.add(Box.createVerticalGlue()); // центруємо компоненти по вертикалі
        panelMain.add(equationPanel);
        panelMain.add(Box.createRigidArea(new Dimension(0, 100 * scaleFactor))); // додаємо вертикальний проміжок
        panelMain.add(rootPanel);
        panelMain.add(Box.createVerticalGlue());
    }

    public JTextComponent getRootField() {
        return rootField;
    }

    public AbstractButton getSearchButton() {
        return searchButton;
    }

    public JTextComponent getEquationField() {
        return equationField;
    }

    public AbstractButton getCheckButton() {
        return checkButton;
    }
}