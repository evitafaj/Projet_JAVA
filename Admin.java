package Modele;

public class Admin {
    //Les atributs
    private int idAdmin;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;

    //Constructeur JDBC
    public Admin(int id, String nom, String prenom, String adresse, String email, String motDePasse) {}

    //Constructeur JDBC
    public Admin(int idAdmin, String nom, String prenom, String email, String motDePasse) {
        this.idAdmin = idAdmin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    //Getter
    public int getIdAdmin() {return idAdmin;}
    public String getNom() {return nom;}
    public String getPrenom() {return prenom;}
    public String getEmail() {return email;}
    public String getMotDePasse() {return motDePasse;}

    //Setters
    public void setIdCAdmin(int id) { this.idAdmin = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email) { this.email = email; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
}
