package Modele;

public class Client {
    //Les Attributs
    private int idClient;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String motDePasse;

    //Constructeur JDBC
    public Client() {}

    //Constructeur avec les parametres.
    public Client(int id, String nom, String prenom, String adresse, String email, String motDePasse) {
        this.idClient = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    //Getters
    public int getIdClient() { return idClient; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getAdresse() { return adresse; }
    public String getEmail() { return email; }
    public String getMotDePasse() { return motDePasse; }

    //Setters
    public void setIdClient(int id) { this.idClient = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setEmail(String email) { this.email = email; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

}
