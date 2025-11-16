package Stadium;

import java.sql.*;
import java.util.Map;

public class BookingService {

    private Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/stadium_db";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public void bookTicket(int userId, boolean requireAc) {
        try (Connection conn = connect()) {
            String checkAvailability = "SELECT id FROM tickets WHERE is_sold = FALSE LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(checkAvailability);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int ticketId = rs.getInt("id");

                String updateTicket = "UPDATE tickets SET is_sold = TRUE WHERE id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateTicket);
                updateStmt.setInt(1, ticketId);
                updateStmt.executeUpdate();

                String insertBooking = "INSERT INTO bookings (user_id, ticket_id) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertBooking);
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, ticketId);
                insertStmt.executeUpdate();

                Booking booking = new Booking(userId, ticketId, requireAc);
                System.out.println("Ticket booked successfully!");
                System.out.println("Sending online ticket to user...");
                System.out.println("Booking Details: " + booking);
            } else {
                System.out.println("No tickets available for this seat and event.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, BookingService> viewAllBookings() {
        try (Connection conn = connect()) {

            String query = "SELECT b.id, b.user_id, b.ticket_id, t.is_sold " +
                    "FROM bookings b " +
                    "JOIN tickets t ON b.ticket_id = t.id";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("No bookings found.");
                return null;
            }

            do {
                int bookingId = rs.getInt("id");
                int userId = rs.getInt("user_id");
                int ticketId = rs.getInt("ticket_id");
                boolean isSold = rs.getBoolean("is_sold");

                System.out.println("Booking ID: " + bookingId +
                        ", User ID: " + userId +
                        ", Ticket ID: " + ticketId +
                        ", Ticket Sold: " + isSold);
            } while (rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Booking {
        private int userId;
        private int ticketId;
        private boolean requireAc;

        public int getUserId() {
            return userId;
        }

        public int getTicketId() {
            return ticketId;
        }

        public boolean isRequireAc() {
            return requireAc;
        }

        public Booking(int userId, int ticketId, boolean requireAc) {
            this.userId = userId;
            this.ticketId = ticketId;
            this.requireAc = requireAc;
        }

        public String toString() {
            return "Booking{" +
                    "userId=" + userId +
                    ", ticketId=" + ticketId +
                    ", requireAc=" + requireAc +
                    '}';
        }
    }
}
