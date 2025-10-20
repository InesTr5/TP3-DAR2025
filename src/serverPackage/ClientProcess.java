package serverPackage;

import java.io.*;
import java.net.*;

public class ClientProcess extends Thread {
    private Socket socket;
    private int nb;

    public ClientProcess(Socket socket, int nb) {
        this.socket = socket;
        this.nb = nb;
    }

    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)
        ) {
            out.println("Bienvenue, vous êtes le client n°" + nb);

            String message;
            while ((message = in.readLine()) != null) {
                if (message.trim().equalsIgnoreCase("exit")) break;

                System.out.println("Message du client " + nb + ": " + message);
                out.println("Serveur → Reçu (" + nb + ") : " + message.toUpperCase());
            }

            System.out.println("Client n°" + nb + " déconnecté.");

        } catch (IOException e) {
            System.err.println("Erreur avec le client n°" + nb + " : " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Erreur de fermeture du socket client " + nb);
            }
        }
    }
}
