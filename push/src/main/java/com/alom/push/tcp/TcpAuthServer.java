package com.alom.push.tcp;

import com.alom.push.dto.TokenValidationResponseDTO;
import com.alom.push.service.ClientConnectionService;
import com.alom.push.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class TcpAuthServer {

    @Value("${tcp.port:9999}")
    private int tcpPort;

    @Value("${tcp.thread.pool.size:10}")
    private int threadPoolSize;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClientConnectionService clientConnectionService;

    private ServerSocket serverSocket;
    private volatile boolean running = false;
    private ExecutorService executorService;

    public void start() {
        try {
            serverSocket = new ServerSocket(tcpPort);
            running = true;

            executorService = Executors.newFixedThreadPool(threadPoolSize);

            System.out.println("Serveur TCP démarré sur le port " + tcpPort );
            System.out.println("Pool de threads initialisé avec " + threadPoolSize + " threads");
            System.out.println("En attente de connexions...");

            
            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouvelle connexion : " + clientSocket.getRemoteSocketAddress());                
                executorService.execute(() -> handleClient(clientSocket));
            }
            
        } catch (IOException e) {
            if (running) {
                System.err.println("Erreur serveur : " + e.getMessage());
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        Client client = null;

        try {
            client = new Client(clientSocket);

            client.sendMessage("Bienvenue sur notre serveur d'authentification");
            client.sendMessage("Veuillez entrer votre token d'authentification :");

            String token = client.readMessage();

            if (token == null || token.trim().isEmpty()) {
                client.sendMessage("Erreur : Token vide");
                System.out.println("Erreur : Token vide");
                return;
            }
            
            token = token.trim();
            System.out.println("Token reçu : " + token);
            
            TokenValidationResponseDTO tokenValid = this.tokenService.isTokenValid(token);
            
            if (tokenValid != null) {
                client.sendMessage("Authentification réussie !");
                client.sendMessage("Bienvenue " + tokenValid.getNickname() + " !");
                System.out.println("Auth OK : " + tokenValid.getNickname());

                Client authenticatedClient = new Client(clientSocket, tokenValid.getNickname());
                clientConnectionService.connectClient(authenticatedClient);
                client = authenticatedClient;

                String line;
                while ((line = client.readMessage()) != null) {
                    System.out.println("Message reçu de " + tokenValid.getNickname() + " : " + line);
                }

            } else {
                client.sendMessage("Authentification échouée : Token invalide");
                System.out.println("Auth échouée : Token inconnu");
            }
            
        } catch (IOException e) {
            System.err.println("Erreur client : " + e.getMessage());
        } finally {
            if (client != null) {
                if (client.getNickname() != null) {
                    clientConnectionService.disconnectClient(client.getNickname());
                }
                client.disconnect();
            }
            System.out.println("Connexion fermée");
        }
    }

    public void stop() {
        running = false;
        try {
            clientConnectionService.disconnectAllClients();

            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }

            if (executorService != null) {
                executorService.shutdown();
                try {
                    if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                        executorService.shutdownNow();
                    }
                    System.out.println("Pool de threads arrêté");
                } catch (InterruptedException e) {
                    executorService.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'arrêt : " + e.getMessage());
        }
    }
}