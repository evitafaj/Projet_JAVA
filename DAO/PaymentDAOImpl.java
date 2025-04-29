/** Author Nina */
package DAO;

import Modele.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.DB;
public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public ArrayList<Payment> getAll() {
        return null;
    }

    @Override
    public Payment findById(int paymentId) {
        return null;
    }

    @Override
    public Map<String, Object> add(Payment payment) {
        try {
            Map<String, Object> ligneCommandeData = new HashMap<>();
            ligneCommandeData.put("montant", payment.getAmount());
            ligneCommandeData.put("mode", payment.getPaymentMethod());
            ligneCommandeData.put("idCommande", payment.getIdCommande());

            System.out.println("paymentMethod:"+ payment.getPaymentMethod());

            Map<String, Object> where = new HashMap<>();
            where.put("idCommande", payment.getIdCommande());

            Map<String, Object> existingPayment = DB.getInstance().table("paiement").selectOne(where);

            if (existingPayment != null && !existingPayment.isEmpty()) {
                int existingPaymentId = (Integer) existingPayment.get("id");
                ligneCommandeData.put("id", existingPaymentId);

                List<Map<String, Object>> updatedPayment = DB.getInstance().table("paiement").update(ligneCommandeData, where);

                if (updatedPayment != null && !updatedPayment.isEmpty()) {
                    return updatedPayment.get(0);
                } else {
                    return null;
                }
            } else {
                Map<String, Object> response = DB.getInstance().table("paiement").insert(ligneCommandeData);
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Payment update(Payment payment) {
        return null;
    }

    @Override
    public void delete(Payment payment) {
        return ;
    }

    @Override
    public Payment findByCommandeId(int commandeId) {
        Map<String, Object> where = new HashMap<>();
        where.put("idCommande", commandeId);

        Map<String, Object> result = DB.getInstance().table("paiement").selectOne(where);

        if (result == null || result.isEmpty()) {
            return null;
        }
        try {
            System.out.println(result);
            int paymentId = (Integer) result.get("id");
            double amount = (Double) result.get("montant");
            String paymentMethod = (String) result.get("mode");
            int orderId = (Integer) result.get("idCommande");

            return new Payment(paymentId, amount, paymentMethod, orderId);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }
}