package Modele;

import java.time.LocalDate;

public class Transport {
    private int id;
    private String adresse;
    private LocalDate dateLivraison;
    private String statut;
    private int idCommande;

    // Constructeur sans ID (nouvelle livraison)
    public Transport(String adresse, LocalDate dateLivraison, String statut, int idCommande) {
        this.adresse = adresse;
        this.dateLivraison = dateLivraison;
        this.statut = statut;
        this.idCommande = idCommande;
    }

    // Constructeur complet
    public Transport(int id, String adresse, LocalDate dateLivraison, String statut, int idCommande) {
        this.id = id;
        this.adresse = adresse;
        this.dateLivraison = dateLivraison;
        this.statut = statut;
        this.idCommande = idCommande;
    }

    // Constructeur vide
    public Transport() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getAdresse() {
        return adresse;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public String getStatut() {
        return statut;
    }

    public int getIdCommande() {
        return idCommande;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }
}
