/** Author Nina */
package DAO;

import java.util.List;
import java.util.Map;
import Modele.Transport;

public interface TransportDAO {
    Map<String, Object> add(Transport transport);
    List<Map<String, Object>> getAll();
    Map<String, Object> findById(int id);
    Transport findByCommandeId(int id);
    List<Map<String, Object>> update(Transport transport);
    boolean delete(int id);
}