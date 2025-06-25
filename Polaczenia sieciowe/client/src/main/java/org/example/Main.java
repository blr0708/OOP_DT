package org.example;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connect("localhost", 1238);

        // Поток, который слушает входящие сообщения
        new Thread(client).start();

        // Чтение ввода с клавиатуры и отправка сообщений
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = input.readLine()) != null) {
            client.send(line);
        }
    }
}
