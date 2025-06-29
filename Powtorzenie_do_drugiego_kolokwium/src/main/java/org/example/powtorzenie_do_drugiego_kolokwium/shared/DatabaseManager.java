package org.example.powtorzenie_do_drugiego_kolokwium.shared;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager(String dbPath) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS dot(
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                x REAL NOT NULL,
                y REAL NOT NULL,
                color TEXT NOT NULL,
                radius REAL NOT NULL
            );
        """;
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
    }

    public void saveDot(Dot dot) throws SQLException {
        String sql = "INSERT INTO dot(x, y, color, radius) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setDouble(1, dot.x());
        stmt.setDouble(2, dot.y());
        stmt.setString(3, toColorString(dot.color()));
        stmt.setDouble(4, dot.radius());
        stmt.executeUpdate();
    }

    public List<Dot> getSavedDots() throws SQLException {
        List<Dot> dots = new ArrayList<>();
        String sql = "SELECT * FROM dot";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            double x = rs.getDouble("x");
            double y = rs.getDouble("y");
            double radius = rs.getDouble("radius");
            String[] rgb = rs.getString("color").split(",");
            javafx.scene.paint.Color color = new javafx.scene.paint.Color(
                    Double.parseDouble(rgb[0]),
                    Double.parseDouble(rgb[1]),
                    Double.parseDouble(rgb[2]),
                    1.0
            );
            dots.add(new Dot(x, y, color, radius));
        }
        return dots;
    }

    private String toColorString(javafx.scene.paint.Color color) {
        return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
    }
}
