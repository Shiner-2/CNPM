import com.example.hhd.Database.DBHelper;
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
            stmt.execute("DROP TABLE IF EXISTS Users");
            stmt.execute("CREATE TABLE Users (UserID INTEGER PRIMARY KEY AUTOINCREMENT, Username TEXT NOT NULL, Password TEXT NOT NULL)");
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
        UserManager.addUser("testuser", "password123");
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT Username FROM Users WHERE Username = ?")) {
            pstmt.setString(1, "testuser");
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next(), "User should be found in the database");
            assertEquals("testuser", rs.getString("Username"), "Username should match");
        } catch (SQLException e) {
            fail("SQL error occurred: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateUserPassword() {
        UserManager.addUser("user1", "oldpass");
        
        // Update the password of the user
        UserManager.updateUserPassword("user1", "newpass");
        
        // Verify that the password was updated correctly
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT Password FROM Users WHERE Username = ?")) {
            pstmt.setString(1, "user1");
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next(), "User should be found");
            assertEquals("newpass", rs.getString("Password"), "Password should be updated");
        } catch (SQLException e) {
            fail("SQL error occurred during password verification: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteUser() {
        UserManager.addUser("user2", "pass2");

        // Delete the user and verify that they are no longer in the database
        UserManager.deleteUser("user2");
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE Username = ?")) {
            pstmt.setString(1, "user2");
            ResultSet rs = pstmt.executeQuery();
            assertFalse(rs.next(), "User should be deleted from the database");
        } catch (SQLException e) {
            fail("SQL error occurred: " + e.getMessage());
        }
    }
}
