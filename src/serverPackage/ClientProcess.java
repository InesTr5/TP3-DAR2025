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
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("Bienvenue, vous êtes le client n°" + nb);

            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("exit")) break;
                System.out.println("Message du client " + nb + ": " + message);
                out.println("Serveur → Reçu : " + message.toUpperCase());
            }

            System.out.println("Client n°" + nb + " déconnecté.");

        } catch (IOException e) {
            System.out.println("Erreur avec le client n°" + nb);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
