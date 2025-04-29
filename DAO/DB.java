package DAO;

import java.sql.*;
import java.util.*;

public class DB {
    private static DB instance;
    private static final String URL = "jdbc:mysql://localhost:8889/modele_shopping_nina?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection connection;
    private String tableName;

    private DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static DB getInstance() {
        if (instance == null) {
            instance = new DB();
        }
        return instance;
    }

    public DB table(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public Map<String, Object> insert(Map<String, Object> data) {
        if (tableName == null || data.isEmpty()) {
            throw new IllegalStateException("Table name or data is not set");
        }

        StringJoiner columns = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");

        for (String column : data.keySet()) {
            columns.add(column);
            placeholders.add("?");
        }

        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            for (Object value : data.values()) {
                stmt.setObject(index++, value);
            }
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);

                    Map<String, Object> where = new HashMap<>();
                    where.put("idCommande", id); // 🔥 Attention à bien utiliser idCommande
                    return selectOne(where);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Map<String, Object>> select(Map<String, Object> where) {
        List<Map<String, Object>> results = new ArrayList<>();
        if (tableName == null) {
            throw new IllegalStateException("Table name is not set");
        }

        StringBuilder sql = new StringBuilder("SELECT * FROM " + tableName);
        if (where != null && !where.isEmpty()) {
            sql.append(" WHERE ");
            StringJoiner conditions = new StringJoiner(" AND ");
            for (String column : where.keySet()) {
                conditions.add(column + " = ?");
            }
            sql.append(conditions.toString());
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            if (where != null) {
                int index = 1;
                for (Object value : where.values()) {
                    stmt.setObject(index++, value);
                }
            }

            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public Map<String, Object> selectOne(Map<String, Object> where) {
        List<Map<String, Object>> results = select(where);
        return results.isEmpty() ? null : results.get(0);
    }

    public List<Map<String, Object>> update(Map<String, Object> data, Map<String, Object> where) {
        if (tableName == null || data.isEmpty() || where == null || where.isEmpty()) {
            throw new IllegalStateException("Table name, data or where conditions are not set");
        }

        StringJoiner updates = new StringJoiner(", ");
        for (String column : data.keySet()) {
            updates.add(column + " = ?");
        }

        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET " + updates);
        sql.append(" WHERE ");
        StringJoiner conditions = new StringJoiner(" AND ");
        for (String column : where.keySet()) {
            conditions.add(column + " = ?");
        }
        sql.append(conditions.toString());

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int index = 1;
            for (Object value : data.values()) {
                stmt.setObject(index++, value);
            }
            for (Object value : where.values()) {
                stmt.setObject(index++, value);
            }

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return select(where);
            } else {
                return new ArrayList<>();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean delete(Map<String, Object> where) {
        if (tableName == null || where == null || where.isEmpty()) {
            throw new IllegalStateException("Table name or where conditions are not set");
        }

        StringBuilder sql = new StringBuilder("DELETE FROM " + tableName + " WHERE ");
        StringJoiner conditions = new StringJoiner(" AND ");
        for (String column : where.keySet()) {
            conditions.add(column + " = ?");
        }
        sql.append(conditions.toString());

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int index = 1;
            for (Object value : where.values()) {
                stmt.setObject(index++, value);
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> results = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public Connection getConnection() {
        return connection;
    }
}
