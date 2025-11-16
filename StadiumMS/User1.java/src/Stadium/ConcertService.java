package Stadium;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ConcertService {
    private static final String URL = "jdbc:mysql://localhost:3306/stadium_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private final HistoryStack historyStack;
    private Map<Integer, Concert> concertCache = new HashMap<>();

    public ConcertService(HistoryStack historyStack) {
        this.historyStack = historyStack;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void createConcert(int eventId, String artist, String genre) {
        String query = "INSERT INTO concerts (event_id, artist, genre) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, eventId);
            stmt.setString(2, artist);
            stmt.setString(3, genre);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int concertId = rs.getInt(1);
                    concertCache.put(concertId, new Concert(concertId, eventId, artist, genre));
                    historyStack.addHistory("Concert created: ID " + concertId + ", Artist " + artist);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Concert> viewAllConcerts() {
        Map<Integer, Concert> allConcerts = new HashMap<>();
        String query = "SELECT * FROM concerts";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("c_id");
                int eventId = rs.getInt("event_id");
                String artist = rs.getString("artist");
                String genre = rs.getString("genre");
                allConcerts.put(id, new Concert(id, eventId, artist, genre));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allConcerts;
    }
}
