package Modele;

public class Payment {
    private int id;
    private double amount;
    private String paymentMethod;
    private boolean isPaid;
    private int idCommande;

    // Constructeur sans ID
    public Payment(double amount, String paymentMethod, int idCommande) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.isPaid = false;
        this.idCommande = idCommande;
    }

    // Constructeur complet avec ID et isPaid
    public Payment(int id, double amount, String paymentMethod, boolean isPaid, int idCommande) {
        this.id = id;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.isPaid = isPaid;
        this.idCommande = idCommande;
    }

    // 🔥 Nouveau constructeur pour compatibilité DAO
    public Payment(int id, double amount, String paymentMethod, int idCommande) {
        this.id = id;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.idCommande = idCommande;
        this.isPaid = false;
    }

    // Getters
    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public int getIdCommande() {
        return idCommande;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    // Méthode pour simuler un paiement
    public void processPayment() {
        if (!isPaid) {
            isPaid = true;
        }
    }
}
