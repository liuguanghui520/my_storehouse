package MySQL_Link;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

// com.nep.util.DatabaseUtil.java


import java.sql.*;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/practical_training";
    private static final String USER = "root";
    private static final String PASSWORD = "LRZS*fcj20060720";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("数据库驱动加载失败", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}