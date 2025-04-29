package Modele;

import java.sql.Timestamp;

public class Commande {
    private int idCommande;
    private Timestamp date;
    private String statut;
    private double total;
    private int idClient;

    // Constructeurs
    public Commande() {
    }

    public Commande(int id, Timestamp date, String statut, double total, int idClient) {
        this.idCommande = idCommande;
        this.date = date;
        this.statut = statut;
        this.total = total;
        this.idClient = idClient;
    }

    // Getters
    public int getId() {
        return idCommande;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getStatut() {
        return statut;
    }

    public double getTotal() {
        return total;
    }

    public int getIdClient() {
        return idClient;
    }

    // Setters
    public void setId(int id) {
        this.idCommande = idCommande;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + idCommande +
                ", date=" + date +
                ", statut='" + statut + '\'' +
                ", total=" + total +
                ", idClient=" + idClient +
                '}';
    }
}
