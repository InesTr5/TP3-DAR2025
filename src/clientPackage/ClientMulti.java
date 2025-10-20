package clientPackage;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientMulti {
    public static void main(String[] args) {
        String hote = "localhost";
        int port = 1234;

        try (Socket socket = new Socket(hote, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Connecté au serveur " + hote + " sur le port " + port);
            System.out.println(in.readLine());

            while (true) {
                System.out.print("Message à envoyer ('exit' pour quitter) : ");
                String msg = sc.nextLine();
                out.println(msg);
                if (msg.equalsIgnoreCase("exit")) break;

                String reponse = in.readLine();
                System.out.println("→ Réponse du serveur : " + reponse);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
