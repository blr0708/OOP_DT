package org.example.naszczat;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {
    private BufferedReader reader;
    private PrintWriter writer;
    private HelloController controller;
    private ClientReceiver receiver;

    public void setReceiver(ClientReceiver receiver) {
        this.receiver = receiver;
    }
    public void connect(String address, int port) throws IOException {
        Socket socket = new Socket(address, port);
        InputStream input = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(input));
        OutputStream output = socket.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(output),true);
    }

    @Override
    public void run() {
        String message;
        try {
            while((message = reader.readLine()) != null) {
                System.out.println(message);
                controller.chatHistoryArea.appendText("\n"+ message);
                receiver.handle(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void send(String message){
        writer.println(message);
    }

    public void setController(HelloController helloController) {
        this.controller = helloController;
    }
}