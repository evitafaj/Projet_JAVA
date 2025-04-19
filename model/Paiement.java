package model;

import java.util.Date;

public class Paiement {
    private int idPaiement;
    private double montant;
    private Date date;
    private String mode;
    private int idCommande;

    public Paiement(int idPaiement, double montant, Date date, String mode, int idCommande) {
        this.idPaiement = idPaiement;
        this.montant = montant;
        this.date = date;
        this.mode = mode;
        this.idCommande = idCommande;
    }

    public Paiement(double montant, Date date, String mode, int idCommande) {
        this(-1, montant, date, mode, idCommande);
    }

    public int getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(int idPaiement) {
        this.idPaiement = idPaiement;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    @Override
    public String toString() {
        return "Paiement{" +
                "montant=" + montant +
                ", date=" + date +
                ", mode='" + mode + '\'' +
                '}';
    }
}
