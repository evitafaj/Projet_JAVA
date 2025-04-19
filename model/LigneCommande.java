package model;

public class LigneCommande {
    private int idLigneCommande;
    private int quantite;
    private double prixTotalLigne;
    private int idCommande;
    private int idProduit;

    public LigneCommande(int idLigneCommande, int quantite, double prixTotalLigne, int idCommande, int idProduit) {
        this.idLigneCommande = idLigneCommande;
        this.quantite = quantite;
        this.prixTotalLigne = prixTotalLigne;
        this.idCommande = idCommande;
        this.idProduit = idProduit;
    }

    public LigneCommande(int quantite, double prixTotalLigne, int idCommande, int idProduit) {
        this(-1, quantite, prixTotalLigne, idCommande, idProduit);
    }
    public LigneCommande() {
    }

    public int getIdLigneCommande() {
        return idLigneCommande;
    }

    public void setIdLigneCommande(int idLigneCommande) {
        this.idLigneCommande = idLigneCommande;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixTotalLigne() {
        return prixTotalLigne;
    }

    public void setPrixTotalLigne(double prixTotalLigne) {
        this.prixTotalLigne = prixTotalLigne;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    @Override
    public String toString() {
        return "LigneCommande{" +
                "quantite=" + quantite +
                ", prixTotalLigne=" + prixTotalLigne +
                ", idProduit=" + idProduit +
                '}';
    }
}
