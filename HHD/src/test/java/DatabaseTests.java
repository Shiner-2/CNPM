import com.example.hhd.Database.DBHelper;
import com.example.hhd.Database.GameManagement;
import com.example.hhd.Database.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTests {

    @BeforeEach
    public void setUp() {
        // Reset the database to a known state before each test
        try (Connection conn = DBHelper.connect(); Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS UserGames");
            stmt.execute("DROP TABLE IF EXISTS Games");
            stmt.execute("DROP TABLE IF EXISTS UserProfiles");
            stmt.execute("DROP TABLE IF EXISTS Users");
            stmt.execute("CREATE TABLE Users (UserID INTEGER PRIMARY KEY AUTOINCREMENT, Username TEXT NOT NULL UNIQUE, Password TEXT NOT NULL)");
            stmt.execute("CREATE TABLE UserProfiles (ProfileID INTEGER PRIMARY KEY AUTOINCREMENT, UserID INTEGER, DisplayName TEXT, FOREIGN KEY(UserID) REFERENCES Users(UserID))");
            stmt.execute("CREATE TABLE Games (GameID INTEGER PRIMARY KEY AUTOINCREMENT, GameName TEXT NOT NULL)");
            stmt.execute("CREATE TABLE UserGames (UserGameID INTEGER PRIMARY KEY AUTOINCREMENT, UserID INTEGER, GameID INTEGER, GameState TEXT, HighScore INTEGER, WinTimes INTEGER, FOREIGN KEY(UserID) REFERENCES Users(UserID), FOREIGN KEY(GameID) REFERENCES Games(GameID))");
        } catch (SQLException e) {
            System.out.println("Setup failed: " + e.getMessage());
        }
    }

    @Test
    public void testConnection() {
        assertNotNull(DBHelper.connect(), "Connection should not be null");
    }

    @Test
    public void testAddUser() {
        String username = "testuser_" + System.currentTimeMillis();
        assertTrue(UserManager.addUser(username, "password123", "Test User"), "User should be added successfully");
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT Username FROM Users WHERE Username = ?")) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next(), "User should be found in the database");
            assertEquals(username, rs.getString("Username"), "Username should match");
        } catch (SQLException e) {
            fail("SQL error occurred: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateUserPassword() {
        String username = "user1_" + System.currentTimeMillis();
        UserManager.addUser(username, "oldpass", "User One");
        assertTrue(UserManager.updatePassword(username, "newpass"), "Password should be updated");

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT Password FROM Users WHERE Username = ?")) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next(), "User should be found");
            assertEquals("newpass", rs.getString("Password"), "Password should be updated");
        } catch (SQLException e) {
            fail("SQL error occurred during password verification: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteUser() {
        String username = "user2_" + System.currentTimeMillis();
        UserManager.addUser(username, "pass2", "User Two");
        assertTrue(UserManager.deleteUser(username), "User should be deleted");

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE Username = ?")) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            assertFalse(rs.next(), "User should be deleted from the database");
        } catch (SQLException e) {
            fail("SQL error occurred: " + e.getMessage());
        }
    }

    @Test
    public void testIsValidCredentials() {
        String username = "user123_" + System.currentTimeMillis();
        UserManager.addUser(username, "password123", "User 123");
        assertTrue(UserManager.isValidCredentials(username, "password123"), "Credentials should be valid");
        assertFalse(UserManager.isValidCredentials(username, "wrongPassword"), "Credentials should be invalid with wrong password");
        assertFalse(UserManager.isValidCredentials("nonExistentUser", "password123"), "Credentials should be invalid with non-existent username");
    }

    @Test
    public void testAddGame() {
        assertTrue(GameManagement.addGame("Test Game"), "Game should be added successfully");
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT GameName FROM Games WHERE GameName = ?")) {
            pstmt.setString(1, "Test Game");
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next(), "Game should be found in the database");
            assertEquals("Test Game", rs.getString("GameName"), "Game name should match");
        } catch (SQLException e) {
            fail("SQL error occurred: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateGame() {
        GameManagement.addGame("Initial Game");
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT GameID FROM Games WHERE GameName = ?")) {
            pstmt.setString(1, "Initial Game");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int gameId = rs.getInt("GameID");
                assertTrue(GameManagement.updateGame(gameId, "Updated Game"), "Game should be updated successfully");

                try (PreparedStatement pstmt2 = conn.prepareStatement("SELECT GameName FROM Games WHERE GameID = ?")) {
                    pstmt2.setInt(1, gameId);
                    ResultSet rs2 = pstmt2.executeQuery();
                    conn.close();
                    assertTrue(rs2.next(), "Updated game should be found in the database");
                    assertEquals("Updated Game", rs2.getString("GameName"), "Game name should match the updated value");
                }
            } else {
                fail("Initial game not found, cannot continue test");
            }
            
        } catch (SQLException e) {
            fail("SQL error occurred: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteGame() {
        GameManagement.addGame("Game to Delete");
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT GameID FROM Games WHERE GameName = ?")) {
            pstmt.setString(1, "Game to Delete");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int gameId = rs.getInt("GameID");
                assertTrue(GameManagement.deleteGame(gameId), "Game should be deleted successfully");

                try (PreparedStatement pstmt2 = conn.prepareStatement("SELECT * FROM Games WHERE GameID = ?")) {
                    pstmt2.setInt(1, gameId);
                    ResultSet rs2 = pstmt2.executeQuery();
                    assertFalse(rs2.next(), "Game should be deleted from the database");
                }
            } else {
                fail("Game to delete not found, cannot continue test");
            }
        } catch (SQLException e) {
            fail("SQL error occurred: " + e.getMessage());
        }
    }

    @Test
    public void testAddUserGame() {
        String username = "user1_" + System.currentTimeMillis();
        UserManager.addUser(username, "pass1", "User One");
        GameManagement.addGame("Test Game");
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmtUser = conn.prepareStatement("SELECT UserID FROM Users WHERE Username = ?");
             PreparedStatement pstmtGame = conn.prepareStatement("SELECT GameID FROM Games WHERE GameName = ?")) {
            pstmtUser.setString(1, username);
            pstmtGame.setString(1, "Test Game");
            ResultSet rsUser = pstmtUser.executeQuery();
            ResultSet rsGame = pstmtGame.executeQuery();
            if (rsUser.next() && rsGame.next()) {
                int userId = rsUser.getInt("UserID");
                int gameId = rsGame.getInt("GameID");
                assertTrue(GameManagement.addUserGame(userId, gameId, "Initial State", 0, 0), "User game should be added successfully");

                String userGameDetails = GameManagement.getUserGame(userId, gameId);
                assertNotNull(userGameDetails, "User game details should be retrieved");
                assertTrue(userGameDetails.contains("Initial State"), "User game state should be 'Initial State'");
            } else {
                fail("User or Game not found, cannot continue test");
            }
        } catch (SQLException e) {
            fail("SQL error occurred: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateUserGame() {
        String username = "user1_" + System.currentTimeMillis();
        UserManager.addUser(username, "pass1", "User One");
        GameManagement.addGame("Test Game");
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmtUser = conn.prepareStatement("SELECT UserID FROM Users WHERE Username = ?");
             PreparedStatement pstmtGame = conn.prepareStatement("SELECT GameID FROM Games WHERE GameName = ?")) {
            pstmtUser.setString(1, username);
            pstmtGame.setString(1, "Test Game");
            ResultSet rsUser = pstmtUser.executeQuery();
            ResultSet rsGame = pstmtGame.executeQuery();
            if (rsUser.next() && rsGame.next()) {
                int userId = rsUser.getInt("UserID");
                int gameId = rsGame.getInt("GameID");
                GameManagement.addUserGame(userId, gameId, "Initial State", 0, 0);

                String userGameDetailsBefore = GameManagement.getUserGame(userId, gameId);
                assertNotNull(userGameDetailsBefore, "User game details should be retrieved before update");

                // Update user game
                try (PreparedStatement pstmtUserGame = conn.prepareStatement("SELECT UserGameID FROM UserGames WHERE UserID = ? AND GameID = ?")) {
                    pstmtUserGame.setInt(1, userId);
                    pstmtUserGame.setInt(2, gameId);
                    ResultSet rsUserGame = pstmtUserGame.executeQuery();
                    if (rsUserGame.next()) {
                        int userGameId = rsUserGame.getInt("UserGameID");
                        assertTrue(GameManagement.updateUserGame(userGameId, "Updated State", 10, 1), "User game should be updated successfully");

                        String userGameDetailsAfter = GameManagement.getUserGame(userId, gameId);
                        assertNotNull(userGameDetailsAfter, "User game details should be retrieved after update");
                        assertTrue(userGameDetailsAfter.contains("Updated State"), "User game state should be 'Updated State'");
                        assertTrue(userGameDetailsAfter.contains("HighScore: 10"), "User game high score should be '10'");
                        assertTrue(userGameDetailsAfter.contains("WinTimes: 1"), "User game win times should be '1'");
                    } else {
                        fail("User game not found, cannot continue test");
                    }
                }
            } else {
                fail("User or Game not found, cannot continue test");
            }
        } catch (SQLException e) {
            fail("SQL error occurred: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteUserGame() {
        String username = "user1_" + System.currentTimeMillis();
        UserManager.addUser(username, "pass1", "User One");
        GameManagement.addGame("Test Game");
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmtUser = conn.prepareStatement("SELECT UserID FROM Users WHERE Username = ?");
             PreparedStatement pstmtGame = conn.prepareStatement("SELECT GameID FROM Games WHERE GameName = ?")) {
            pstmtUser.setString(1, username);
            pstmtGame.setString(1, "Test Game");
            ResultSet rsUser = pstmtUser.executeQuery();
            ResultSet rsGame = pstmtGame.executeQuery();
            if (rsUser.next() && rsGame.next()) {
                int userId = rsUser.getInt("UserID");
                int gameId = rsGame.getInt("GameID");
                GameManagement.addUserGame(userId, gameId, "Initial State", 0, 0);

                // Get user game ID
                try (PreparedStatement pstmtUserGame = conn.prepareStatement("SELECT UserGameID FROM UserGames WHERE UserID = ? AND GameID = ?")) {
                    pstmtUserGame.setInt(1, userId);
                    pstmtUserGame.setInt(2, gameId);
                    ResultSet rsUserGame = pstmtUserGame.executeQuery();
                    if (rsUserGame.next()) {
                        int userGameId = rsUserGame.getInt("UserGameID");
                        assertTrue(GameManagement.deleteUserGame(userGameId), "User game should be deleted successfully");

                        String userGameDetails = GameManagement.getUserGame(userId, gameId);
                        assertNull(userGameDetails, "User game should be deleted");
                    } else {
                        fail("User game not found, cannot continue test");
                    }
                }
            } else {
                fail("User or Game not found, cannot continue test");
            }
        } catch (SQLException e) {
            fail("SQL error occurred: " + e.getMessage());
        }
    }
}
