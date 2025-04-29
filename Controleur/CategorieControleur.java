package Controleur;

import DAO.CategorieDAO;
import Modele.Categorie;
import java.util.List;

public class CategorieControleur {
    private CategorieDAO categorieDAO;

    public CategorieControleur(CategorieDAO categorieDAO) {
        this.categorieDAO = categorieDAO;
    }

    public void ajouterCategorie(Categorie cat) {
        categorieDAO.ajouterCategorie(cat);
    }

    public List<Categorie> getCategories() {
        return categorieDAO.getAllCategories();
    }
}
