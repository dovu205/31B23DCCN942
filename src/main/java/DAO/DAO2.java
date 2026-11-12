package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Cấu hình JDBC dùng chung. Mỗi thao tác mở và đóng kết nối riêng. */
public abstract class DAO2 {
    protected Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
                setting("GARAMAN_DB_URL", "jdbc:mysql://localhost:3306/garaman"),
                setting("GARAMAN_DB_USER", "root"),
                setting("GARAMAN_DB_PASSWORD", ""));
    }

    protected static String setting(String name, String fallback) {
        String value = System.getenv(name);
        return value == null ? fallback : value;
    }
}
