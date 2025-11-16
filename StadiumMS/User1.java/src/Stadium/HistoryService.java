package Stadium;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class HistoryService {
    private static final String URL = "jdbc:mysql://localhost:3306/stadium_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private final Map<Integer, History> historyCache = new HashMap<>();

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean addHistory(String description) {
        String query = "INSERT INTO history (description) VALUES (?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, description);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int historyId = generatedKeys.getInt(1);
                        historyCache.put(historyId, new History(historyId, description));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static class History {
        private final int id;
        private final String description;

        public History(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public int getId() {
            return id;
        }

        
        public String toString() {
            return "History [id=" + id + ", description=" + description + "]";
        }

        public String getDescription() {
            return description;
        }
    }
}
