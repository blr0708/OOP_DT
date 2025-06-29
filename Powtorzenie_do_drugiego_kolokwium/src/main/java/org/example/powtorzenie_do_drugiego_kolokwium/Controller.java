package org.example.powtorzenie_do_drugiego_kolokwium;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.powtorzenie_do_drugiego_kolokwium.client.ServerThread;
import org.example.powtorzenie_do_drugiego_kolokwium.shared.Dot;

import java.io.IOException;

public class Controller {

    @FXML private Canvas canvas;
    @FXML private ColorPicker colorPicker;
    @FXML private Slider radiusSlider;
    private ServerThread serverThread;
    public Controller(ServerThread serverThread) {
        this.serverThread = serverThread;
    }


    @FXML
    public void onMouseClicked(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        double radius = radiusSlider.getValue();
        Color color = colorPicker.getValue();
        if (serverThread == null || !serverThread.isConnected()) {
            System.out.println("⚠️ Не подключено к серверу!");
            return;
        }
        String message = Dot.toMessage(x, y, color, radius);
        serverThread.send(message);
    }

    @FXML
    public void onStartServerClicked(ActionEvent event) {
        // Пока можешь оставить пустым
    }

    @FXML
    public void onConnectClicked(ActionEvent event) {
        try {
            serverThread.connect("localhost", 5000); // здесь создаётся writer
            new Thread(serverThread).start();        // поток чтения
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        serverThread.setDotConsumer(dot -> {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setFill(dot.color());
            gc.fillOval(dot.x() - dot.radius(), dot.y() - dot.radius(),
                    dot.radius() * 2, dot.radius() * 2);
        });
    }
}