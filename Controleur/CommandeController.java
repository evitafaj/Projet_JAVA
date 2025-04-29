/** Author Nina */
package Controleur;

import DAO.CommandeDAOImpl;
import DAO.TransportDAOImpl;
import DAO.PaymentDAOImpl;
import DAO.ClientDAOImpl;
import DAO.DaoFactory;
import Modele.Client;
import Modele.Commande;
import Modele.Payment;
import Modele.Transport;
import Vue.CommandeDetailView;
import Vue.CommandeView;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class CommandeController {

    private CommandeDAOImpl commandeDAO;
    private TransportDAOImpl transportDAO;
    private PaymentDAOImpl paymentDAO;
    private ClientDAOImpl clientDAO;

    public CommandeController() {
        DaoFactory dao = DaoFactory.getInstance("model_shopping", "root", "root");
        this.paymentDAO = new PaymentDAOImpl();
        this.transportDAO = new TransportDAOImpl();
        this.commandeDAO = new CommandeDAOImpl();
        this.clientDAO = new ClientDAOImpl(dao);
    }

    public List<Commande> getAllCommandes() {
        return commandeDAO.getAll();
    }

    public void afficherCommandes(CommandeView view) {
        List<Commande> commandes = getAllCommandes();
        view.afficherCommandes(commandes);
    }

    public void voirDetailsCommande(int commandeId, CommandeDetailView existingView) {
        Commande commande = commandeDAO.getCommandeById(commandeId);
        int ClientID = commande.getIdClient();

        Client client = clientDAO.chercher(ClientID);
        Transport transport = transportDAO.findByCommandeId(commandeId);
        Payment payment = paymentDAO.findByCommandeId(commandeId);
        List<Map<String, Object>> CommandeLine = commandeDAO.getCommandeLineByCommandeId(commandeId);

        if (commande == null) {
            JOptionPane.showMessageDialog(null, "Commande introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (existingView != null) {
            existingView.updateCommandeDetails(commande, client, transport, payment, CommandeLine);
        } else {
            new CommandeDetailView(commande, client, transport, payment, CommandeLine, this);
        }
    }

    public void refreshCommandeDetails(int commandeId, CommandeDetailView existingView) {
        Commande commande = commandeDAO.getCommandeById(commandeId);
        Client client = clientDAO.chercher(commande.getIdClient());
        Transport transport = transportDAO.findByCommandeId(commandeId);
        Payment payment = paymentDAO.findByCommandeId(commandeId);
        List<Map<String, Object>> CommandeLine = commandeDAO.getCommandeLineByCommandeId(commandeId);

        if (commande == null) {
            JOptionPane.showMessageDialog(null, "Commande introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (existingView != null) {
            existingView.updateCommandeDetails(commande, client, transport, payment, CommandeLine);
        }
    }
}