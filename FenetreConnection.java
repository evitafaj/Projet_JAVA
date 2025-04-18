package Vue;

import DAO.*;
import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreConnection extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel resultLabel;

    public FenetreConnection(DaoFactory daoFactory) {
        setTitle("Connexion Utilisateur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        emailField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Se connecter");
        resultLabel = new JLabel("", SwingConstants.CENTER);

        add(new JLabel("Email :", SwingConstants.CENTER));
        add(emailField);
        add(new JLabel("Mot de passe :", SwingConstants.CENTER));
        add(passwordField);
        add(loginButton);
        add(resultLabel);

        // Action du bouton
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String mdp = new String(passwordField.getPassword());

                ClientDAO clientDAO = daoFactory.getClientDAO();
                AdminDAO adminDAO = new AdminDAOImpl(daoFactory);

                Client client = clientDAO.seConnecter(email, mdp);
                if (client != null) {
                    // Ouvre la fenêtre client et ferme la connexion
                    dispose();
                    new FenetreClient(client);
                    return;
                }

                Admin admin = adminDAO.seConnecter(email, mdp);
                if (admin != null) {
                    // Ouvre la fenêtre admin et ferme la connexion
                    dispose();
                    new FenetreAdmin(admin, daoFactory);
                    return;
                }

                resultLabel.setText("Identifiants incorrects.");
            }
        });

        setVisible(true);
    }
}
