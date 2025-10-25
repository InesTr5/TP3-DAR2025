package serverPackage;

import model.Operation;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final int clientNumber;
    private final AtomicInteger compteurGlobal;

    public ClientHandler(Socket socket, int clientNumber, AtomicInteger compteurGlobal) {
        this.socket = socket;
        this.clientNumber = clientNumber;
        this.compteurGlobal = compteurGlobal;
    }

    @Override
    public void run() {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            oos.flush(); 
            oos.writeObject("Bienvenue, vous êtes le client n°" + clientNumber);
            oos.flush();

            System.out.println("Handler démarré pour client n°" + clientNumber);

            Object obj;
            while ((obj = ois.readObject()) != null) {
                if (!(obj instanceof Operation)) {
                    oos.writeObject("Erreur : objet inattendu");
                    oos.flush();
                    continue;
                }

                Operation op = (Operation) obj;

                if (op.getOperateur().equalsIgnoreCase("exit")) {
                    System.out.println("Client n°" + clientNumber + " demande déconnexion.");
                    oos.writeObject("Déconnexion confirmée.");
                    oos.flush();
                    break;
                }

                double res = calculer(op);

                int total = compteurGlobal.incrementAndGet();
                System.out.println("Op traitée pour client n°" + clientNumber + " : " + op + " = " + res + " (total ops = " + total + ")");

                
                oos.writeObject(res);
                oos.flush();
            }
        } catch (EOFException eof) {
            System.out.println("Flux fermé par le client n°" + clientNumber);
        } catch (Exception e) {
            System.err.println("Erreur handler client n°" + clientNumber + " : " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
            System.out.println("Connexion client n°" + clientNumber + " terminée.");
        }
    }

    private double calculer(Operation op) {
        double a = op.getOperande1();
        double b = op.getOperande2();
        String oper = op.getOperateur();
        switch (oper) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return b != 0 ? a / b : Double.NaN;
            default: return Double.NaN;
        }
    }
}
