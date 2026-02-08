package service.dao;

import finalproject.Book;
import service.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        try (Connection con = DB.getConnection()) {

            boolean hasAvailability = hasColumn(con, "books", "availability");
            boolean hasAvailable = hasColumn(con, "books", "available");

            if (!hasAvailability && !hasAvailable) {
                throw new SQLException("books table has no 'availability' or 'available' column");
            }

            // Read a unified "availability" value no matter which column exists
            String availabilityExpr;
            if (hasAvailability && hasAvailable) {
                availabilityExpr = "COALESCE(availability, available)"; // prefer availability if present
            } else if (hasAvailability) {
                availabilityExpr = "availability";
            } else {
                availabilityExpr = "available";
            }

            String sql = "SELECT id, title, author, " + availabilityExpr + " AS availability FROM books ORDER BY id";

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    books.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getInt("availability") == 1
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public void insert(Book book) {
        try (Connection con = DB.getConnection()) {

            boolean hasAvailability = hasColumn(con, "books", "availability");
            boolean hasAvailable = hasColumn(con, "books", "available");

            if (!hasAvailability && !hasAvailable) {
                throw new SQLException("books table has no 'availability' or 'available' column");
            }

            // IMPORTANT: if both exist, we MUST insert both (because 'available' is NOT NULL)
            String sql;
            if (hasAvailability && hasAvailable) {
                sql = "INSERT INTO books(title, author, availability, available) VALUES (?, ?, 1, 1)";
            } else if (hasAvailability) {
                sql = "INSERT INTO books(title, author, availability) VALUES (?, ?, 1)";
            } else {
                sql = "INSERT INTO books(title, author, available) VALUES (?, ?, 1)";
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, book.getTitle());
                ps.setString(2, book.getAuthor());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAvailability(int bookId, boolean availableValue) {
        try (Connection con = DB.getConnection()) {

            boolean hasAvailability = hasColumn(con, "books", "availability");
            boolean hasAvailable = hasColumn(con, "books", "available");

            if (!hasAvailability && !hasAvailable) {
                throw new SQLException("books table has no 'availability' or 'available' column");
            }

            // If both exist, update both so they stay consistent
            if (hasAvailability && hasAvailable) {
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE books SET availability=?, available=? WHERE id=?")) {
                    int v = availableValue ? 1 : 0;
                    ps.setInt(1, v);
                    ps.setInt(2, v);
                    ps.setInt(3, bookId);
                    ps.executeUpdate();
                }
            } else if (hasAvailability) {
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE books SET availability=? WHERE id=?")) {
                    ps.setInt(1, availableValue ? 1 : 0);
                    ps.setInt(2, bookId);
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE books SET available=? WHERE id=?")) {
                    ps.setInt(1, availableValue ? 1 : 0);
                    ps.setInt(2, bookId);
                    ps.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean hasColumn(Connection con, String table, String column) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("PRAGMA table_info(" + table + ")");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                if (column.equalsIgnoreCase(rs.getString("name"))) return true;
            }
        }
        return false;
    }
}
