package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(1238);
        server.listen();
    }
}
