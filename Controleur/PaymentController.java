/** Author Nina */
package Controleur;

import Modele.Payment;
import Vue.PaymentView;
import DAO.PaymentDAOImpl;

import java.util.Map;

public class PaymentController {
    private PaymentView paymentView;
    private int commandeId;

    public PaymentController(double total, int commandeId) {
        this.paymentView = new PaymentView(total);
        this.paymentView.setPaymentController(this);
        this.commandeId = commandeId;
    }

    public void startPaymentProcess() {
        paymentView.setVisible(true);
    }

    // Valider et traiter le paiement
    public void validatePayment(String amountStr, String paymentMethod) {
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                paymentView.showMessage("❌ Invalid amount!", "Error");
                return;
            }

            Payment payment = new Payment(amount, paymentMethod, commandeId);
            PaymentDAOImpl paymentDAO = new PaymentDAOImpl();
            Map<String, Object> result = paymentDAO.add(payment);

            if (result != null) {
                paymentView.showMessage("✅ Payment processed successfully!", "Success");
                this.paymentView.dispose();
            } else {
                paymentView.showMessage("❌ Payment failed", "Error");
            }
        } catch (NumberFormatException e) {
            paymentView.showMessage("❌ Please enter a valid amount.", "Error");
        }
    }
}