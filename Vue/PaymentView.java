/**Author Nina**/

package Vue;

import Controleur.PaymentController;
import javax.swing.*;
import java.awt.*;

public class PaymentView extends JFrame {
    private JTextField txtAmount;
    private JComboBox<String> cmbPaymentMethod;
    private JButton btnSubmit;
    private JLabel lblTotalAmount;

    private PaymentController paymentController;

    public PaymentView(double totalAmount) {
        setTitle("Payment");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel lblAmount = new JLabel("Amount to pay:");
        txtAmount = new JTextField(String.valueOf(totalAmount));
        txtAmount.setEditable(false);
        JLabel lblPaymentMethod = new JLabel("Payment Method:");
        String[] paymentMethods = {"Credit Card", "PayPal", "Bank Transfer"};
        cmbPaymentMethod = new JComboBox<>(paymentMethods);
        btnSubmit = new JButton("Submit Payment");

        panel.add(lblAmount);
        panel.add(txtAmount);
        panel.add(lblPaymentMethod);
        panel.add(cmbPaymentMethod);
        panel.add(new JLabel());
        panel.add(btnSubmit);

        add(panel, BorderLayout.CENTER);

        btnSubmit.addActionListener(e -> {
            if (paymentController != null) {
                paymentController.validatePayment(txtAmount.getText(), (String) cmbPaymentMethod.getSelectedItem());
            }
        });
    }

    public void setPaymentController(PaymentController paymentController) {
        this.paymentController = paymentController;
    }

    public void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}