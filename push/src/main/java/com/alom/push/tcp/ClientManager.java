package com.alom.push.tcp;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ClientManager {

    private final ConcurrentHashMap<String, Client> connectedClients = new ConcurrentHashMap<>();

    public void addClient(Client client) {
        if (client == null || client.getNickname() == null) {
            return;
        }

        Client previousClient = connectedClients.putIfAbsent(client.getNickname(), client);

        if (previousClient != null) {
            System.out.println("Client " + client.getNickname() + " déjà connecté, remplacement");
            previousClient.disconnect();
            connectedClients.put(client.getNickname(), client);
        } else {
            System.out.println("Client " + client.getNickname() + " ajouté à la liste des connectés");
        }

    }

    public void removeClient(String nickname) {
        Client client = connectedClients.remove(nickname);
        if (client != null) {
            System.out.println("Client " + nickname + " retiré de la liste des connectés");
        }
    }

    public void sendMessageToClient(String nickname, String message) {
        Client client = connectedClients.get(nickname);

        if (client != null && client.isConnected()) {
            boolean sent = client.sendMessage(message);
            if (sent) {
                System.out.println("Message envoyé à " + nickname + " : " + message);
            } else {
                System.out.println("Échec de l'envoi du message à " + nickname);
                removeClient(nickname);
            }
        } else {
            System.out.println("Client " + nickname + " non connecté");
        }
    }

    public boolean isClientConnected(String nickname) {
        Client client = connectedClients.get(nickname);
        return client != null && client.isConnected();
    }

    public void disconnectAll() {
        System.out.println("Déconnexion de tous les clients (" + connectedClients.size() + ")");
        connectedClients.values().forEach(Client::disconnect);
        connectedClients.clear();
    }
}