package Stadium;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private static final String URL = "jdbc:mysql://localhost:3306/stadium_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private final HistoryStack historyStack;
    private Map<Integer, User> userCache = new HashMap<>();

    public UserService(HistoryStack historyStack) {
        this.historyStack = historyStack;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean registerUser(String name, String email, String password) {
        String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    userCache.put(userId, new User(userId, name, email, password));
                    historyStack.addHistory("User registered: ID " + userId + ", Name " + name);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean authenticateUser(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                userCache.put(id, new User(id, name, email, password));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Map<Integer, User> viewAllUsers() {
        Map<Integer, User> allUsers = new HashMap<>();
        String query = "SELECT * FROM users";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                allUsers.put(id, new User(id, name, email, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }
}

