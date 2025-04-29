package Vue;

import DAO.*;
import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FenetreVoirAvis extends JFrame {

    public FenetreVoirAvis(DaoFactory daoFactory, int idProduit) {
        setTitle("Avis du Produit");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 🔥 Titre
        JLabel titre = new JLabel("⭐ Avis pour le produit ID : " + idProduit, SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titre, BorderLayout.NORTH);

        // 🔥 Zone d'affichage
        JTextArea avisArea = new JTextArea();
        avisArea.setEditable(false);
        avisArea.setFont(new Font("Serif", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(avisArea);
        add(scrollPane, BorderLayout.CENTER);

        // 🔥 Bouton de fermeture
        JButton fermerBtn = new JButton("Fermer");
        fermerBtn.addActionListener(e -> dispose());
        add(fermerBtn, BorderLayout.SOUTH);

        // 🎯 Charger les avis du produit
        AvisDAO avisDAO = new AvisDAOImpl(daoFactory);
        List<Avis> avisList = avisDAO.getAvisByProduit(idProduit);

        if (avisList.isEmpty()) {
            avisArea.setText("❗ Aucun avis pour ce produit pour le moment.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Avis a : avisList) {
                sb.append("Note : ").append(a.getNote()).append("/5\n")
                        .append("Commentaire : ").append(a.getCommentaire()).append("\n")
                        .append("-----------------------------\n");
            }
            avisArea.setText(sb.toString());
        }

        setVisible(true);
    }
}
