package org.example.powtorzenie_do_drugiego_kolokwium.shared;

import javafx.scene.paint.Color;

public record Dot(double x, double y, Color color, double radius) {

    public static String toMessage(double x, double y, Color color, double radius) {
        // Пример: 150.0;200.0;0.5,0.4,0.3;25.0
        String colorString = color.getRed() + "," + color.getGreen() + "," + color.getBlue();
        return x + ";" + y + ";" + colorString + ";" + radius;
    }

    public static Dot fromMessage(String message) {
        // Пример входа: "150.0;200.0;0.5,0.4,0.3;25.0"
        String[] parts = message.split(";");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);

        String[] colorParts = parts[2].split(",");
        Color color = new Color(
                Double.parseDouble(colorParts[0]),
                Double.parseDouble(colorParts[1]),
                Double.parseDouble(colorParts[2]),
                1.0 // alpha
        );

        double radius = Double.parseDouble(parts[3]);

        return new Dot(x, y, color, radius);
    }
    public String toMessage() {
        String colorString = color.getRed() + "," + color.getGreen() + "," + color.getBlue();
        return x + ";" + y + ";" + colorString + ";" + radius;
    }

}
