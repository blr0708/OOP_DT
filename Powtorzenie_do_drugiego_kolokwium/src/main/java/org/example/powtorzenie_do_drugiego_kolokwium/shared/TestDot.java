package org.example.powtorzenie_do_drugiego_kolokwium.shared;

import javafx.scene.paint.Color;

public class TestDot {
    public static void main(String[] args) {
        // 🔴 Создаём объект Dot
        Dot dot = new Dot(150, 200, Color.RED, 25);

        // 🟠 Преобразуем в строку
        String msg = Dot.toMessage(dot.x(), dot.y(), dot.color(), dot.radius());
        System.out.println("Zakodowana wiadomość: " + msg);

        // 🟢 Снова превращаем строку в объект
        Dot parsed = Dot.fromMessage(msg);
        System.out.println("Odczytany X: " + parsed.x());
        System.out.println("Odczytany Y: " + parsed.y());
        System.out.println("Odczytany promień: " + parsed.radius());
        System.out.println("Odczytany kolor: " + parsed.color());
    }
}
