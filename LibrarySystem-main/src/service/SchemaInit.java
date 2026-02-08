package service;

import java.sql.*;

public class SchemaInit {

    public static void init() {
        try (Connection c = DB.getConnection();
             Statement st = c.createStatement()) {

            // Create table if missing (new DBs)
            st.execute("""
                CREATE TABLE IF NOT EXISTS books (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    author TEXT NOT NULL,
                    availability INTEGER NOT NULL DEFAULT 1
                );
            """);

            // Migrate old column name if needed
            if (!columnExists(c, "books", "availability")) {
                st.execute("ALTER TABLE books ADD COLUMN availability INTEGER NOT NULL DEFAULT 1");
                st.execute("UPDATE books SET availability = available");
            }

            System.out.println("Database and books table ready");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean columnExists(Connection c, String table, String column) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("PRAGMA table_info(" + table + ")");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                if (column.equalsIgnoreCase(rs.getString("name"))) {
                    return true;
                }
            }
        }
        return false;
    }
}
