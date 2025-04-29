package Modele;

import java.util.HashMap;
import java.util.Map;

public class Panier {
    private Map<Produit, Integer> produits;
    private Client client;

    public Panier(Client client) {
        this.produits = new HashMap<>();
        this.client = client;
    }

    public int getIdClient() {
        return client.getIdClient();
    }

    public void ajouterProduit(Produit produit, int quantite) {
        if (produit == null || quantite <= 0) {
            throw new IllegalArgumentException("Produit invalide ou quantité <= 0");
        }

        produits.merge(produit, quantite, Integer::sum);
    }

    public void retirerProduit(Produit produit) {
        if (produit != null) {
            produits.remove(produit);
        }
    }

    public void changerQuantite(Produit produit, int nouvelleQuantite) {
        if (nouvelleQuantite <= 0) {
            retirerProduit(produit);
        } else {
            produits.put(produit, nouvelleQuantite);
        }
    }

    public double calculerTotal() {
        double total = 0.0;
        for (Map.Entry<Produit, Integer> entry : produits.entrySet()) {
            Produit produit = entry.getKey();
            int quantite = entry.getValue();
            total += produit.getPrixUnitaire() * quantite;
        }
        return total;
    }

    public Map<Produit, Integer> getProduits() {
        return produits;
    }

    public void viderPanier() {
        produits.clear();
    }

    public boolean estVide() {
        return produits.isEmpty();
    }

    public int getQuantite(Produit produit) {
        return produits.getOrDefault(produit, 0);
    }
}
