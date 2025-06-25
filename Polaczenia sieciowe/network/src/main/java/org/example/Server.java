package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Server {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Serwer nasłuchuje na porcie " + port);
    }

    public void listen() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Nowy klient podłączony");

            ClientHandler handler = new ClientHandler(socket, this);
            clients.add(handler);
            new Thread(handler).start();
        }
    }

    public void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.send(message);
        }
    }
    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }
    public List<String> getOnlineUsers() {
        List<String> logins = new ArrayList<>();
        for (ClientHandler client : clients) {
            logins.add(client.getLogin());
        }
        return logins;
    }
    public void whisper(ClientHandler sender, String recipient, String message) {
        Optional<ClientHandler> recipientHandler = clients.stream()
                .filter(client -> client.getLogin().equals(recipient))
                .findFirst();

        if (recipientHandler.isPresent()) {
            recipientHandler.get().send("📩 (od " + sender.getLogin() + "): " + message);
        } else {
            sender.send("❌ Nie ma użytkownika o loginie: " + recipient);
        }
    }


}
