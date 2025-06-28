package org.example.naszczat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class HelloController {
    ClientReceiver receiver = new ClientReceiver();

    private Client client;

    @FXML
    public TextArea chatHistoryArea; // отображает историю сообщений

    @FXML
    public TextField currentTextField; // поле для ввода текста

    @FXML
    public ListView<String> usersListView; // список пользователей

    @FXML
    public Button sendButton; // кнопка отправки

    @FXML
    public void onSendClick(ActionEvent actionEvent)
    {
        String currentTextAreaText = chatHistoryArea.getText();  // всё, что уже есть в истории
        String currentTextFieldText = currentTextField.getText();// что пользователь только что ввёл
        client.send(currentTextFieldText); // отправляем сообщение (или логин)
        currentTextField.clear();
    }

    @FXML
    public void initialize()
    {
        client = new Client();
        receiver = new ClientReceiver();         // 1. создаём объект
        client.setController(this);
        client.setReceiver(receiver);
        receiver.setController(this);

        try {
            client.connect("localhost", 3002);
            new Thread(client).start();

            // 🔐 Запрашиваем логин у пользователя
            TextInputDialog dialog = new TextInputDialog("Anon");
            dialog.setTitle("Logowanie");
            dialog.setHeaderText("Podaj swój login:");
            dialog.setContentText("Login:");

            dialog.showAndWait().ifPresent(login -> {
                client.send(login);      // 1. логин
                client.send("/online");  // 2. запрос списка
            });

        } catch (IOException e) {
            System.out.println("Could not connect to server");
        }
        currentTextField.setOnAction(event -> onSendClick(null));
    }
}
