package service.dao;

import service.DB;

import java.sql.*;
import java.time.LocalDate;

public class LoanDAO {

    public void addLoan(int bookId, int memberId, LocalDate issueDate, LocalDate dueDate) {
        String sql = "INSERT INTO loans(book_id, member_id, issue_date, due_date) VALUES (?, ?, ?, ?)";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            ps.setInt(2, memberId);
            ps.setString(3, issueDate.toString());
            ps.setString(4, dueDate.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int countActiveLoansForMember(int memberId) {
        String sql = "SELECT COUNT(*) FROM loans WHERE member_id=? AND return_date IS NULL";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
