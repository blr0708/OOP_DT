package org.example;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {
    private PrintWriter writer;
    private BufferedReader reader;

    public void connect(String host, int port) throws IOException {
        // 1. Подключаемся к серверу
        Socket socket = new Socket(host, port);
        // 2. Настраиваем writer и reader
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Połączono z serwerem");
        // 3. Ввод логина с клавиатуры
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Wpisz login: ");
        String login = console.readLine();

        // 4. Отправляем логин серверу
        writer.println(login);
    }

    public void send(String message) {
        writer.println(message);
    }

    @Override
    public void run() {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Rozłączono z serwerem");
        }
    }
}
