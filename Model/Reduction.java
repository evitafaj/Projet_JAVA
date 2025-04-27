package Modele;

import java.time.LocalDate;

public class Reduction {
    private int idReduction;
    private double pourcentage;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int idProduit;

    public Reduction(int idReduction, double pourcentage, LocalDate dateDebut, LocalDate dateFin, int idProduit) {
        this.idReduction = idReduction;
        this.pourcentage = pourcentage;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idProduit = idProduit;
    }

    // Getters
    public int getIdReduction() { return idReduction; }
    public double getPourcentage() { return pourcentage; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public int getIdProduit() { return idProduit; }

    // Setters
    public void setIdReduction(int idReduction) { this.idReduction = idReduction; }
    public void setPourcentage(double pourcentage) { this.pourcentage = pourcentage; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public void setIdProduit(int idProduit) { this.idProduit = idProduit; }

    // Méthode pour vérifier si la réduction est active aujourd'hui
    public boolean estActive() {
        LocalDate today = LocalDate.now();
        return (today.isEqual(dateDebut) || today.isAfter(dateDebut)) &&
                (today.isEqual(dateFin) || today.isBefore(dateFin));
    }

    // Méthode pour calculer le prix réduit
    public double appliquerReduction(double prixOriginal) {
        if (estActive()) {
            return prixOriginal * (1 - pourcentage / 100);
        }
        return prixOriginal;
    }
}
