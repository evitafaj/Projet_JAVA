package Vue;

import DAO.ClientDAO;
import DAO.ClientDAOImpl;
import DAO.DaoFactory;
import Modele.Client;

import javax.swing.*;
import java.awt.*;

public class FenetreInscription extends JFrame {
    private JTextField nomField, prenomField, adresseField, emailField;
    private JPasswordField mdpField;
    private JButton inscrireBtn, retourBtn;

    public FenetreInscription(DaoFactory daoFactory) {
        setTitle("Création d'un nouveau compte client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 10, 10));

        nomField = new JTextField();
        prenomField = new JTextField();
        adresseField = new JTextField();
        emailField = new JTextField();
        mdpField = new JPasswordField();
        inscrireBtn = new JButton("Créer mon compte");
        retourBtn = new JButton("Retour");

        add(new JLabel("Nom :"));
        add(nomField);
        add(new JLabel("Prénom :"));
        add(prenomField);
        add(new JLabel("Adresse :"));
        add(adresseField);
        add(new JLabel("Email :"));
        add(emailField);
        add(new JLabel("Mot de passe :"));
        add(mdpField);
        add(retourBtn); // colonne 1
        add(inscrireBtn); // colonne 2

        ClientDAO clientDAO = new ClientDAOImpl(daoFactory);

        // Bouton inscription
        inscrireBtn.addActionListener(e -> {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String adresse = adresseField.getText();
            String email = emailField.getText();
            String mdp = new String(mdpField.getPassword());

            if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || email.isEmpty() || mdp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Merci de remplir tous les champs !");
                return;
            }

            // Vérification : email déjà utilisé ?
            for (Client c : clientDAO.getAll()) {
                if (c.getEmail().equalsIgnoreCase(email)) {
                    JOptionPane.showMessageDialog(this, "❌ Cet email est déjà utilisé.");
                    return;
                }
            }

            Client nouveauClient = new Client(0, nom, prenom, adresse, email, mdp);
            clientDAO.ajouter(nouveauClient);

            JOptionPane.showMessageDialog(this, "✅ Compte créé avec succès !");
            dispose();
            new FenetreConnection(daoFactory);
        });

        // Bouton retour
        retourBtn.addActionListener(e -> {
            dispose();
            new FenetreAccueil(daoFactory);
        });

        setVisible(true);
    }
}
