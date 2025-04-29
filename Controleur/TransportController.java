/** Author Nina */
package Controleur;

import Vue.TransportView;
import Modele.Transport;
import DAO.TransportDAOImpl;

import java.time.LocalDate;

public class TransportController {

    private TransportView transportView;
    private TransportControllerListener listener;

    public interface TransportControllerListener {
        void onTransportInfoSubmitted();
    }

    public void setTransportControllerListener(TransportControllerListener listener) {
        this.listener = listener;
    }

    public void ouvrirTransportView(int idCommande) {
        transportView = new TransportView(idCommande);
        transportView.setValiderButtonListener(e -> enregistrerTransport(idCommande));
    }

    private void enregistrerTransport(int idCommande) {
        String adresse = transportView.getAdresse();  // 获取地址
        LocalDate dateLivraison = transportView.getDateLivraison();  // 获取交货日期

        if (adresse == null || adresse.isEmpty()) {
            transportView.showErrorMessage("Veuillez entrer l'adresse.");
            return;
        }

        Transport transport = new Transport();
        transport.setAdresse(adresse);
        transport.setDateLivraison(dateLivraison);
        transport.setStatut("En préparation");  // 默认状态
        transport.setIdCommande(idCommande);

        TransportDAOImpl transportDAO = new TransportDAOImpl();
        transportDAO.add(transport);

        transportView.showInfoMessage("Transport enregistré avec succès !");
        transportView.dispose();

        if (listener != null) {
            listener.onTransportInfoSubmitted();
        }
    }
}