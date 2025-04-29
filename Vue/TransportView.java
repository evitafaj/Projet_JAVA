/**Author nina**/

package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;
import Modele.Transport;
import DAO.TransportDAOImpl;
import java.time.LocalDate;
import java.time.ZoneId;

public class TransportView extends JFrame {

    private JTextField adresseField;
    private JSpinner dateLivraisonSpinner;
    private JButton validerButton;
    private int idCommande;

    public TransportView(int idCommande) {
        this.idCommande = idCommande;
        setTitle("Information de Livraison");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Adresse de livraison :"));
        adresseField = new JTextField();
        panel.add(adresseField);

        panel.add(new JLabel("Date de livraison :"));
        dateLivraisonSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateLivraisonSpinner, "yyyy-MM-dd");
        dateLivraisonSpinner.setEditor(dateEditor);
        panel.add(dateLivraisonSpinner);

        validerButton = new JButton("Valider");
        panel.add(new JLabel());
        panel.add(validerButton);
        this.add(panel);
    }

    public String getAdresse() {
        return adresseField.getText();
    }

    public LocalDate getDateLivraison() {
        Date utilDate = (Date) dateLivraisonSpinner.getValue();
        return utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setValiderButtonListener(ActionListener listener) {
        validerButton.addActionListener(listener);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}