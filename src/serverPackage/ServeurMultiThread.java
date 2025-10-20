package serverPackage;

import java.io.*;
import java.net.*;

public class ServeurMultiThread {
    private static int nb = 0;

    public static void main(String[] args) {
        int port = 1234;
        System.out.println("Serveur Multi-Threads en attente de connexions...");

        try (ServerSocket ss = new ServerSocket(port)) {
            while (true) {
                Socket socket = ss.accept();
                nb++;

                System.out.println("Client n°" + nb + " connecté depuis " + socket.getRemoteSocketAddress());

                ClientProcess clientProcess = new ClientProcess(socket, nb);
                clientProcess.start();
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }
}
