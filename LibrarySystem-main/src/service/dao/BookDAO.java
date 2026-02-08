package service.dao;

import finalproject.Book;
import service.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT id, title, author, availability FROM books ORDER BY id";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("availability") == 1
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void insert(Book book) {
        String sql = "INSERT INTO books(title, author, availability) VALUES (?, ?, 1)";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAvailability(int bookId, boolean availableValue) {
        String sql = "UPDATE books SET availability=? WHERE id=?";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, availableValue ? 1 : 0);
            ps.setInt(2, bookId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
