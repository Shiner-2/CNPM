import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {

    private static final String URL = "jdbc:sqlite:english_learning_app.db";

    /**
     * Connects to the SQLite database.
     * @return a Connection object to the database
     */
    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Executes a SQL update operation (INSERT, UPDATE, DELETE).
     * @param sql the SQL query with placeholders for parameters
     * @param preparer a functional interface to set parameters
     * @throws SQLException if a database access error occurs
     */
    public static void executeUpdate(String sql, StatementPreparer preparer) throws SQLException {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            preparer.prepare(pstmt);
            pstmt.executeUpdate();
        }
    }

    @FunctionalInterface
    public interface StatementPreparer {
        void prepare(PreparedStatement statement) throws SQLException;
    }
}
