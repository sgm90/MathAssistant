package DAO;

import model.Equation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquationDAO {
    private static Connection connection;

    public EquationDAO() throws SQLException {
        String url = "jdbc:postgresql://localhost/mydatabase";
        String user = "myuser";
        String password = "mypassword";

        this.connection = DriverManager.getConnection(url, user, password);
        initializeDatabase();
    }

    private void initializeDatabase() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT EXISTS (" +
                        "SELECT FROM information_schema.tables " +
                        "WHERE  table_schema = 'public' " +
                        "AND    table_name   = 'equations'" +
                        ")"
        );
        if (!resultSet.next() || !resultSet.getBoolean(1)) {
            statement.execute(
                    "CREATE TABLE equations (" +
                            "id SERIAL PRIMARY KEY, " +
                            "equation VARCHAR(255) NOT NULL, " +
                            "root DOUBLE PRECISION NOT NULL" +
                            ")"
            );
        }
    }

    public static void saveEquation(Equation equation) throws SQLException {
        String sql = "INSERT INTO equations (equation, root) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, equation.getEquation());
            statement.setDouble(2, equation.getRoot());
            statement.executeUpdate();
        }
    }

    public List<Equation> findEquationsByRoot(double root) throws SQLException {
        String sql = "SELECT * FROM equations WHERE root = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, root);
            ResultSet resultSet = statement.executeQuery();

            List<Equation> equations = new ArrayList<>();
            while (resultSet.next()) {
                String equation = resultSet.getString("equation");
                double rootValue = resultSet.getDouble("root");
                equations.add(new Equation(equation, rootValue));
            }

            return equations;
        }
    }
}