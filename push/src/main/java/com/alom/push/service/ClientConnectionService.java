package com.alom.push.service;

import com.alom.push.tcp.Client;
import com.alom.push.tcp.ClientManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientConnectionService {

    private final ClientManager clientManager;
    private final DynamicKafkaConsumerService dynamicKafkaConsumerService;

    public void connectClient(Client client) {
        if (client == null || client.getNickname() == null) {
            return;
        }

        clientManager.addClient(client);
        dynamicKafkaConsumerService.startListeningForUser(client.getNickname());
    }


    public void disconnectClient(String nickname) {
        if (nickname == null) {
            return;
        }

        dynamicKafkaConsumerService.stopListeningForUser(nickname);
        clientManager.removeClient(nickname);
    }

    public void disconnectAllClients() {
        clientManager.disconnectAll();
    }
}