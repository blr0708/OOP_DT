package org.example.naszczat;

import javafx.application.Platform;

public class ClientReceiver {
    private HelloController controller;

    public void setController(HelloController controller) {
        this.controller = controller;
    }

    public void handle(String message) {

        // 🟢 Обработка BROADCAST-сообщений
        if (message.startsWith("BROADCAST: ")) {
            String text = message.substring("BROADCAST: ".length());

            Platform.runLater(() -> {
                controller.chatHistoryArea.appendText(text + "\n");

                // 👤 Пользователь вошёл
                if (text.endsWith("dołączył do czatu")) {
                    String username = text.split(" ")[0].trim();
                    if (!controller.usersListView.getItems().contains(username)) {
                        controller.usersListView.getItems().add(username);
                    }
                }

                // 🔴 Пользователь вышел
                if (text.endsWith("opuścił czat")) {
                    String username = text.split(" ")[0].trim();
                    controller.usersListView.getItems().remove(username);
                }
            });
        }

        // 🟢 Обработка ONLINE-сообщения
        else if (message.startsWith("🟢 Online: ")) {
            String users = message.substring("🟢 Online: ".length());
            String[] usernames = users.trim().split(",\\s*"); // разделитель — запятая + пробел

            Platform.runLater(() -> {
                controller.usersListView.getItems().setAll(usernames); // заменяем полностью
            });
        }
    }
}
