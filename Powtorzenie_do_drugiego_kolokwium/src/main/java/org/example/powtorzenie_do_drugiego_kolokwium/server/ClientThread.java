package org.example.powtorzenie_do_drugiego_kolokwium.server;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {
    private final Socket socket;
    private final Server server;
    private PrintWriter writer;
    private BufferedReader reader;

    public ClientThread(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Odebrano: " + message);
                server.broadcast(message); // пересылаем другим клиентам
            }
        } catch (IOException e) {
            System.out.println("Klient rozłączony");
        }
    }

    public void send(String message) {
        writer.println(message);
    }
}
