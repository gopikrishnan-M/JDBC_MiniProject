package LearningJDBC.LibraryProject;

import java.sql.*;

public class DBHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
    private static final String USER = "root";
    private static final String PASS = "Gopikris0021";
    /*the below part is optional
    *It loads the MySQL JDBC driver class into memory before you make any database connections
    * in java 6+ while calling drivermanager it is mandatory
    * here even if it is present no problem*/
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC Driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Insert a book into the database
    public static void insertBook(Books book) {
        String sql = "INSERT INTO books (serial_number, name, author, year, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, book.serialNumber);
            ps.setString(2, book.name);
            ps.setString(3, book.author);
            ps.setDate(4, book.year); // DATE format
            ps.setDouble(5, book.price);
            ps.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a book by serial number
    public static void deleteBook(int serialNumber) {
        String sql = "DELETE FROM books WHERE serial_number = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, serialNumber);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Book removed successfully.");
            } else {
                System.out.println("No book found with that serial number.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Show details of a single book
    public static void showBook(int serialNumber) {
        String sql = "SELECT * FROM books WHERE serial_number = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, serialNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println(rs.getInt("serial_number"));
                    System.out.println(rs.getString("name"));
                    System.out.println(rs.getString("author"));
                    System.out.println(rs.getDate("year"));
                    System.out.println(rs.getDouble("price"));
                    System.out.println("*************************************************");
                } else {
                    System.out.println("No book found with that serial number.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Show all books
    public static void showAllBooks() {
        String sql = "SELECT * FROM books";
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println(rs.getInt("serial_number"));
                System.out.println(rs.getString("name"));
                System.out.println(rs.getString("author"));
                System.out.println(rs.getDate("year"));
                System.out.println(rs.getDouble("price"));
                System.out.println("*************************************************");
            }
            if (!found) {
                System.out.println("No books available in the library.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get the next available serial number
    public static int getNextSerialNumber() {
        String sql = "SELECT IFNULL(MAX(serial_number), 0) + 1 AS next_sno FROM books";
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("next_sno");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    // Get book price by serial number
    public static double getBookPrice(int serialNumber) {
        String sql = "SELECT price FROM books WHERE serial_number = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, serialNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("price");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Get current balance
    public static double getLibraryBalance() {
        String sql = "SELECT balance FROM library_balance WHERE id = 1";
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble("balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Update balance
    public static void updateLibraryBalance(double newBalance) {
        String sql = "UPDATE library_balance SET balance = ? WHERE id = 1";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
