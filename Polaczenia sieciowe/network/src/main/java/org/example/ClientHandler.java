package org.example;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Server server;
    private PrintWriter writer;
    private BufferedReader reader;

    private String login; // <- имя пользователя

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    }

    @Override
    public void run() {
        try {
            // 🟢 Шаг 1: получаем логин
            login = reader.readLine();

            // 🟢 Шаг 2: уведомляем остальных
            server.broadcast("🟢 " + login + " dołączył do czatu");

            // 🟢 Шаг 3: читаем сообщения
            String message;
            while ((message = reader.readLine()) != null) {

                if (message.equals("/online")) {
                    List<String> onlineUsers = server.getOnlineUsers();
                    writer.println("🟢 Online: " + String.join(", ", onlineUsers));
                } else if (message.startsWith("/w ")) {
                    String[] parts = message.substring(3).split(" ", 2);
                    if (parts.length == 2) {
                        String recipient = parts[0];
                        String privateMsg = parts[1];
                        server.whisper(this, recipient, privateMsg);
                    } else {
                        writer.println("❌ Zły format. Użyj: /w login wiadomość");
                    }
                } else {
                    server.broadcast(login + ": " + message);
                }
            }
        } catch (IOException e) {
            System.out.println("Klient się rozłączył: " + login);
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}

            // 🔴 Шаг 4: удаляем клиента и оповещаем
            server.removeClient(this);
            server.broadcast("🔴 " + login + " opuścił czat");
        }
    }

    public void send(String message) {
        writer.println(message);
    }
    public String getLogin() {
        return login;
    }

}
