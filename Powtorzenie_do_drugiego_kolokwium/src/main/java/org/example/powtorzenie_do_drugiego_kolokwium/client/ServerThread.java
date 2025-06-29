package org.example.powtorzenie_do_drugiego_kolokwium.client;

import org.example.powtorzenie_do_drugiego_kolokwium.shared.Dot;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class ServerThread implements Runnable {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private Consumer<Dot> dotConsumer;
    public void setDotConsumer(Consumer<Dot> dotConsumer) {
        this.dotConsumer = dotConsumer;
    }

    private boolean connected = false;

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        connected = true;
    }

    public boolean isConnected() {
        return connected;
    }

    public void send(String message) {
        writer.println(message);
    }

    @Override
    public void run() {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println("Odebrano: " + line);

                // 🎯 Преобразуем строку в Dot
                Dot dot = Dot.fromMessage(line);

                // 🎯 Передаём dot потребителю (например, контроллеру)
                if (dotConsumer != null) {
                    dotConsumer.accept(dot);
                }
            }
        } catch (IOException e) {
            System.out.println("Rozłączono z serwerem");
        }
    }
    public static void main(String[] args) throws IOException {
        ServerThread client = new ServerThread();                 // пустой конструктор
        client.connect("localhost", 5000);                        // подключаемся

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = console.readLine();                    // читаем с клавиатуры
            client.send(line);                                   // отправляем серверу
        }
    }


}
