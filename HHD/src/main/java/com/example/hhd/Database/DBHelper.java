package com.example.hhd.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
  
    private static final String URL = "jdbc:sqlite:HHD/src/main/resources/data/UserDB.db";
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 1000;

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
            System.err.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Initializes the database with required tables.
     * @param conn Connection to the database
     */
    private static void initializeDatabase(Connection conn) {
        String[] sqlStatements = {
            """
            CREATE TABLE IF NOT EXISTS Users (
                UserID INTEGER PRIMARY KEY AUTOINCREMENT,
                Username TEXT NOT NULL UNIQUE,
                Password TEXT NOT NULL
            );
            """,
            """
            CREATE TABLE IF NOT EXISTS UserProfiles (
                ProfileID INTEGER PRIMARY KEY AUTOINCREMENT,
                UserID INTEGER,
                DisplayName TEXT,
                RecentWord_Vi_En TEXT,
                RecentWord_En_Vi TEXT,
                FOREIGN KEY(UserID) REFERENCES Users(UserID)
            );
            """,
            """
            CREATE TABLE IF NOT EXISTS Games (
                GameID INTEGER PRIMARY KEY AUTOINCREMENT,
                GameName TEXT NOT NULL
            );
            """,
            """
            CREATE TABLE IF NOT EXISTS UserGames (
                UserGameID INTEGER PRIMARY KEY AUTOINCREMENT,
                UserID INTEGER,
                GameID INTEGER,
                GameState TEXT,
                HighScore INTEGER,
                WinTimes INTEGER,
                FOREIGN KEY(UserID) REFERENCES Users(UserID),
                FOREIGN KEY(GameID) REFERENCES Games(GameID)
            );
            """
        };

        try (Statement stmt = conn.createStatement()) {
            for (String sql : sqlStatements) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            System.err.println("Error during database initialization: " + e.getMessage());
        }
    }

    /**
     * Executes a SQL update operation (INSERT, UPDATE, DELETE).
     * @param sql the SQL query with placeholders for parameters
     * @param preparer a functional interface to set parameters
     * @throws SQLException if a database access error occurs
     */
    public static void executeUpdate(String sql, StatementPreparer preparer) throws SQLException {
        int attempt = 0;
        while (attempt < MAX_RETRIES) {
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                preparer.prepare(pstmt);
                pstmt.executeUpdate();
                return; // Success
            } catch (SQLException e) {
                if (e.getErrorCode() == SQLiteErrorCode.SQLITE_BUSY.code) {
                    attempt++;
                    if (attempt < MAX_RETRIES) {
                        System.err.println("Database is busy, retrying... (" + attempt + ")");
                        try {
                            Thread.sleep(RETRY_DELAY_MS);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            throw new SQLException("Interrupted during retry delay", ie);
                        }
                    } else {
                        throw new SQLException("Maximum retry attempts reached", e);
                    }
                } else {
                    throw e;
                }
            }
        }
    }

    @FunctionalInterface
    public interface StatementPreparer {
        void prepare(PreparedStatement statement) throws SQLException;
    }

    // Enum to handle SQLite error codes
    private enum SQLiteErrorCode {
        SQLITE_BUSY(5);

        private final int code;

        SQLiteErrorCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
