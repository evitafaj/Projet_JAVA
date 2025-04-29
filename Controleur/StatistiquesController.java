/** Author Nina */
package Controleur;

import Vue.StatistiquesView;
import Modele.Commande;
import DAO.CommandeDAOImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatistiquesController {

    private CommandeDAOImpl commandeDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public StatistiquesController() {
        this.commandeDAO = new CommandeDAOImpl();
    }

    public Map<String, List<Commande>> regroupeCommandeByDate() {
        return commandeDAO.regroupeCommandeByDate();
    }

    public Map<String, Map<String, Object>> regroupeCommandeStats(Date startDate, Date endDate) {
        Map<String, List<Commande>> commandesParDate = regroupeCommandeByDate();
        Map<String, Map<String, Object>> statsParDate = new TreeMap<>();

        for (Map.Entry<String, List<Commande>> entry : commandesParDate.entrySet()) {
            try {
                String dateStr = entry.getKey();
                Date commandeDate = dateFormat.parse(dateStr);

                if (!commandeDate.before(startDate) && !commandeDate.after(endDate)) {
                    List<Commande> commandes = entry.getValue();

                    double totalAmount = 0.0;
                    int orderCount = commandes.size();

                    for (Commande commande : commandes) {
                        totalAmount += commande.getTotal();
                    }

                    Map<String, Object> stats = new HashMap<>();
                    stats.put("totalAmount", totalAmount);
                    stats.put("orderCount", orderCount);

                    statsParDate.put(dateStr, stats);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return statsParDate;
    }

    // Affiche les statistiques filtrées
    public void afficherStatistiques(StatistiquesView view, Date startDate, Date endDate) {
        // Si startDate ou endDate est null, on définit les dates par défaut
        if (startDate == null || endDate == null) {
            Calendar calendar = Calendar.getInstance();

            // Date de fin = aujourd'hui
            endDate = calendar.getTime();

            // Date de début = il y a un mois
            calendar.add(Calendar.MONTH, -1);
            startDate = calendar.getTime();
        }

        Map<String, Map<String, Object>> stats = regroupeCommandeStats(startDate, endDate);
        view.afficherStatistiques(stats);
    }
}