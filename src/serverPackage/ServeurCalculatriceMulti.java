package serverPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ServeurCalculatriceMulti {
    private static final int PORT = 1234;
    
    public static final AtomicInteger compteurOperations = new AtomicInteger(0);

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool(); 
        System.out.println("=== Serveur calculatrice multi-thread sur le port " + PORT + " ===");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            int clientIndex = 0;
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientIndex++;
                System.out.println("Client n°" + clientIndex + " connecté depuis " + clientSocket.getRemoteSocketAddress());

                
                pool.submit(new ClientHandler(clientSocket, clientIndex, compteurOperations));
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }
}
