package service;

import java.sql.Connection;
import java.sql.Statement;

public class SchemaInit {

    public static void init() {
        String sql = """
        CREATE TABLE IF NOT EXISTS books (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            title TEXT NOT NULL,
            author TEXT NOT NULL,
            available INTEGER NOT NULL
        );
        """;

        try (Connection c = DB.getConnection();
             Statement st = c.createStatement()) {

            st.execute(sql);
            System.out.println("Database and books table ready");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
