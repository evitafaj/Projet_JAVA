package Vue;

import DAO.*;
import Modele.*;

import javax.swing.*;
import java.awt.*;

public class FenetreDonnerAvis extends JFrame {

    public FenetreDonnerAvis(DaoFactory daoFactory, int idProduit) {
        setTitle("Donner un avis");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 🔥 Haut : Titre
        JLabel titre = new JLabel("Écrire votre avis", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titre, BorderLayout.NORTH);

        // 🔥 Centre : Zone d'avis + note
        JPanel centrePanel = new JPanel(new GridLayout(4, 1, 5, 5));

        JTextArea commentaireArea = new JTextArea(4, 20);
        commentaireArea.setBorder(BorderFactory.createTitledBorder("Votre commentaire"));

        JPanel notePanel = new JPanel();
        JLabel noteLabel = new JLabel("Note (1-5 étoiles) : ");
        String[] notes = {"1", "2", "3", "4", "5"};
        JComboBox<String> noteComboBox = new JComboBox<>(notes);
        notePanel.add(noteLabel);
        notePanel.add(noteComboBox);

        centrePanel.add(new JScrollPane(commentaireArea));
        centrePanel.add(notePanel);

        add(centrePanel, BorderLayout.CENTER);

        // 🔥 Bas : Boutons
        JPanel bottomPanel = new JPanel(new FlowLayout());

        JButton envoyerBtn = new JButton("Envoyer ✅");
        JButton annulerBtn = new JButton("Annuler ❌");

        bottomPanel.add(envoyerBtn);
        bottomPanel.add(annulerBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // 🎯 Action sur "Envoyer"
        envoyerBtn.addActionListener(e -> {
            String commentaire = commentaireArea.getText().trim();
            int note = Integer.parseInt((String) noteComboBox.getSelectedItem());

            if (commentaire.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❗ Le commentaire ne peut pas être vide !");
                return;
            }

            AvisDAO avisDAO = new AvisDAOImpl(daoFactory);
            Avis avis = new Avis(0, idProduit, commentaire, note);
            avisDAO.ajouterAvis(avis);

            JOptionPane.showMessageDialog(this, "✅ Merci pour votre avis !");
            dispose();
        });

        // 🎯 Action sur "Annuler"
        annulerBtn.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }
}
