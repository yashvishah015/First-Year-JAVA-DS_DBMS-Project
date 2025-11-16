package Stadium;



import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CategoryService {
    private static final String URL = "jdbc:mysql://localhost:3306/stadium_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean addCategory(String name, double price, boolean hasAc) {
        String query = "INSERT INTO categories (name, price, has_ac) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setBoolean(3, hasAc);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Category added successfully.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Map<Integer, Category> viewAllCategories() {
        Map<Integer, Category> allCategories = new HashMap<>();
        String query = "SELECT * FROM categories";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                boolean hasAc = rs.getBoolean("has_ac");
                Category category = new Category(id, name, price, hasAc);
                allCategories.put(id, category);  
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCategories;
    }

    public boolean updateCategory(int id, String name, double price, boolean hasAc) {
        String query = "UPDATE categories SET name = ?, price = ?, has_ac = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setBoolean(3, hasAc);
            stmt.setInt(4, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Category updated successfully.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCategory(int id) {
        String query = "DELETE FROM categories WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Category deleted successfully.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

