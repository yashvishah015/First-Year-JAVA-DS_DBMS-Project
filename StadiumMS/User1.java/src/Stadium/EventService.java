package Stadium;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EventService {
    private static final String URL = "jdbc:mysql://localhost:3306/stadium_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private final Map<Integer, Event> eventCache = new HashMap<>();
    private final HistoryStack historyStack;

    public EventService(HistoryStack historyStack) {
        this.historyStack = historyStack;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean createEvent(String name, String event_date, String location) {
        String query = "INSERT INTO events (name, event_date, location) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, event_date);
            stmt.setString(3, location);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    eventCache.put(id, new Event(id, name,event_date , location));
                    historyStack.addHistory("Event created: " + name);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateEvent(int eventId, String name, String event_date, String location) {
        String query = "UPDATE events SET name = ?, event_date = ?, location = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, event_date);
            stmt.setString(3, location);
            stmt.setInt(4, eventId);
            stmt.executeUpdate();
            eventCache.put(eventId, new Event(eventId, name, event_date, location));
            historyStack.addHistory("Event updated: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEvent(int eventId) {
        String query = "DELETE FROM events WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            stmt.executeUpdate();
            eventCache.remove(eventId);
            historyStack.addHistory("Event deleted: ID " + eventId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Event> viewAllEvents() {
        Map<Integer, Event> allEvents = new HashMap<>();
        String query = "SELECT * FROM events";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String date = rs.getString("event_date");
                String location = rs.getString("location");
                allEvents.put(id, new Event(id, name, date, location));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allEvents;
    }
}
