package Modele;

public class Produit {
    private int idProduit;
    private String nom;
    private String description;
    private double prixUnitaire;
    private double prixVrac;
    private int seuilVrac;
    private int idCategorie;

    public Produit(int idProduit, String nom, String description, double prixUnitaire, double prixVrac, int seuilVrac,int idCategorie) {
        this.idProduit = idProduit;
        this.nom = nom;
        this.description = description;
        this.prixUnitaire = prixUnitaire;
        this.prixVrac = prixVrac;
        this.seuilVrac = seuilVrac;
        this.idCategorie = idCategorie;
    }

    //Getters
    public int getIdProduit() { return idProduit; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public double getPrixUnitaire() { return prixUnitaire; }
    public double getPrixVrac() { return prixVrac; }
    public int getSeuilVrac() { return seuilVrac; }
    public int getIdCategorie() { return idCategorie; }
}
