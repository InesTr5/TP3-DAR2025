package clientPackage;

import model.Operation;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientCalculatriceMulti {
    private static final String HOST = "localhost";
    private static final int PORT = 1234;

    public static void main(String[] args) {
        System.out.println("Démarrage client vers " + HOST + ":" + PORT);
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner sc = new Scanner(System.in)) {

            oos.flush(); 
            
            Object bienvenue = ois.readObject();
            System.out.println("Serveur : " + bienvenue);

            while (true) {
                System.out.print("Entrez l'opération (ex: 12 * 3) ou 'exit' : ");
                String line = sc.nextLine().trim();
                if (line.equalsIgnoreCase("exit")) {
                    
                    oos.writeObject(new Operation("exit"));
                    oos.flush();
                    Object resp = ois.readObject();
                    System.out.println("Serveur : " + resp);
                    break;
                }


                String[] parts = line.split("\\s+");
                if (parts.length != 3) {
                    System.out.println("Format invalide. Exemple valide: 5 * 3");
                    continue;
                }
                double a, b;
                String op = parts[1];
                try {
                    a = Double.parseDouble(parts[0]);
                    b = Double.parseDouble(parts[2]);
                } catch (NumberFormatException ex) {
                    System.out.println("Opérandes invalides.");
                    continue;
                }
                
                Operation operation = new Operation(a, op, b);
                oos.writeObject(operation);
                oos.flush();

                Object reply = ois.readObject();
                if (reply instanceof Double) {
                    System.out.println("Résultat : " + reply);
                } else {
                    System.out.println("Serveur : " + reply);
                }
            }

            System.out.println("Fin du client.");

        } catch (Exception e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }
}
