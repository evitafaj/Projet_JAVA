package model;

import java.util.Date;

public class Livraison {
    private int idLivraison;
    private String adresse;
    private Date dateLivraison;
    private String statut;
    private int idCommande;

    public Livraison(int idLivraison, String adresse, Date dateLivraison, String statut, int idCommande) {
        this.idLivraison = idLivraison;
        this.adresse = adresse;
        this.dateLivraison = dateLivraison;
        this.statut = statut;
        this.idCommande = idCommande;
    }

    public Livraison(String adresse, Date dateLivraison, String statut, int idCommande) {
        this(-1, adresse, dateLivraison, statut, idCommande);
    }

    public int getIdLivraison() {
        return idLivraison;
    }

    public void setIdLivraison(int idLivraison) {
        this.idLivraison = idLivraison;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    @Override
    public String toString() {
        return "Livraison{" +
                "adresse='" + adresse + '\'' +
                ", dateLivraison=" + dateLivraison +
                ", statut='" + statut + '\'' +
                '}';
    }
}
