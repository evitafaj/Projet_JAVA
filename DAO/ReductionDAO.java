package DAO;

import Modele.Reduction;
import java.util.*;

public interface ReductionDAO {
    void ajouterReduction(Reduction reduction);
    void supprimerReduction(int id);
    void modifierReduction(Reduction reduction);
    Reduction getReductionById(int id);
    List<Reduction> getAllReductions();
}