/** Author Nina */
package DAO;

import Modele.Payment;
import java.util.ArrayList;
import java.util.Map;

public interface PaymentDAO {
    ArrayList<Payment> getAll();

    Payment findById(int paymentId);

    Payment findByCommandeId(int commandeId);

    Map<String, Object> add(Payment payment);

    Payment update(Payment payment);

    void delete(Payment payment);
}