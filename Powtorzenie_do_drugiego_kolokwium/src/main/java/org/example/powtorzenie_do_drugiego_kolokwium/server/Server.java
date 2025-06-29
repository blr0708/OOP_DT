package org.example.powtorzenie_do_drugiego_kolokwium.server;

import org.example.powtorzenie_do_drugiego_kolokwium.shared.DatabaseManager;
import org.example.powtorzenie_do_drugiego_kolokwium.shared.Dot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private final List<ClientThread> clients = new ArrayList<>();
    private final DatabaseManager databaseManager;

    public Server(int port) throws IOException, SQLException {
        serverSocket = new ServerSocket(port);
        System.out.println("Serwer nasłuchuje na porcie " + port);
        this.databaseManager = new DatabaseManager("dots.db"); // путь к базе
    }


    public void listen() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Nowy klient podłączony");

            ClientThread handler = new ClientThread(socket, this);
            clients.add(handler);
            sendAllDotsTo(handler); // вот тут!
            new Thread(handler).start();

        }
    }

    public void broadcast(String message) {
        try {
            Dot dot = Dot.fromMessage(message);
            databaseManager.saveDot(dot);
        } catch (Exception e) {
            e.printStackTrace(); // логируем ошибку
        }

        for (ClientThread client : clients) {
            client.send(message);
        }
    }
    public void sendAllDotsTo(ClientThread client) {
        try {
            for (Dot dot : databaseManager.getSavedDots()) {
                client.send(dot.toMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(5000);
            server.listen();
        } catch (IOException | SQLException e) {
            e.printStackTrace(); // или логируй красиво
        }
    }

}
