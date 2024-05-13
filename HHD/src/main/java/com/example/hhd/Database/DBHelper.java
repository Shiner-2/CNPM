import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {

    private static final String URL = "jdbc:sqlite:english_learning_app.db";

    /**
     * Connects to the SQLite database.
     * @return a Connection object to the database
     */
    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            initializeDatabase(conn);
            return conn;
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Initializes the database with required tables.
     * @param conn Connection to the database
     */
    private static void initializeDatabase(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            // SQL statement for creating a new Users table
            String sqlUsers = "CREATE TABLE IF NOT EXISTS Users (" +
                              "UserID INTEGER PRIMARY KEY AUTOINCREMENT," +
                              "Username TEXT NOT NULL," +
                              "Password TEXT NOT NULL);";
            stmt.execute(sqlUsers);

            // SQL statement for creating a new UserPreferences table
            String sqlPreferences = "CREATE TABLE IF NOT EXISTS UserPreferences (" +
                                    "PreferenceID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "UserID INTEGER," +
                                    "FavoriteWords TEXT," +
                                    "GameData TEXT," +
                                    "RecentSearches TEXT," +
                                    "FOREIGN KEY(UserID) REFERENCES Users(UserID));";
            stmt.execute(sqlPreferences);
        } catch (SQLException e) {
            System.out.println("Error during database initialization: " + e.getMessage());
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
