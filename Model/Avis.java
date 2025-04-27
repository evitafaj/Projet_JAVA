package Modele;

public class Avis {
    private int id;
    private int produitId;
    private String commentaire;
    private int note;

    public Avis(int id, int produitId, String commentaire, int note) {
        this.id = id;
        this.produitId = produitId;
        this.commentaire = commentaire;
        this.note = note;
    }

    // Constructeur pour ajouter un avis (sans ID)
    public Avis(int produitId, String commentaire, int note) {
        this.produitId = produitId;
        this.commentaire = commentaire;
        this.note = note;
    }

    //Getters
    public int getId() { return id; }
    public int getProduitId() { return produitId; }
    public String getCommentaire() { return commentaire; }
    public int getNote() { return note; }

    //Setters
    public void setId(int id) { this.id = id; }
    public void setProduitId(int produitId) { this.produitId = produitId; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    public void setNote(int note) { this.note = note; }

    @Override
    public String toString() {
        return "⭐ " + note + "/5 : " + commentaire;
    }
}
