package com.alom.push.tcp;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private final Socket socket;
    @Getter
    private final String nickname;
    private final BufferedReader in;
    private final PrintWriter out;
    private volatile boolean connected;

    public Client(Socket socket, String nickname) throws IOException {
        this.socket = socket;
        this.nickname = nickname;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.connected = true;
    }

    public Client(Socket socket) throws IOException {
        this(socket, null);
    }

    public boolean sendMessage(String message) {
        if (out != null && connected) {
            out.println(message);
            return !out.checkError();
        }
        return false;
    }

    public String readMessage() throws IOException {
        if (in != null && connected) {
            return in.readLine();
        }
        return null;
    }

    public void disconnect() {
        connected = false;

        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }

    public boolean isConnected() {
        return connected && socket != null && !socket.isClosed();
    }
}