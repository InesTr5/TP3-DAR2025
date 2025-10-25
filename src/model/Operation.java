package model;

import java.io.Serializable;

public class Operation implements Serializable {
    private static final long serialVersionUID = 1L;

    private double operande1;
    private double operande2;
    private String operateur;

    public Operation(double operande1, String operateur, double operande2) {
        this.operande1 = operande1;
        this.operateur = operateur;
        this.operande2 = operande2;
    }

    public Operation(String operateur) {
        this(0.0, operateur, 0.0);
    }

    public double getOperande1() { return operande1; }
    public double getOperande2() { return operande2; }
    public String getOperateur() { return operateur; }

    @Override
    public String toString() {
        return operateur.equalsIgnoreCase("exit") ? "EXIT" :
               operande1 + " " + operateur + " " + operande2;
    }
}
