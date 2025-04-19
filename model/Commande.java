package model;

import java.util.Date;

public class Commande {
    private int idCommande;
    private Date date;
    private String statut;
    private double total;
    private int idClient;


    public Commande(int idCommande, Date date, String statut, double total, int idClient) {
        this.idCommande = idCommande;
        this.date = date;
        this.statut = statut;
        this.total = total;
        this.idClient = idClient;
    }


    public Commande(Date date, String statut, double total, int idClient) {
        this.date = date;
        this.statut = statut;
        this.total = total;
        this.idClient = idClient;
    }


    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return "Commande #" + idCommande + " | Date : " + date + " | Statut : " + statut + " | Total : " + total + " € | Client ID : " + idClient;
    }
}
