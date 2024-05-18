package com.example.hhd.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {

    /**
     * Checks if a username and password combination is valid.
     *
     * @param username The username to check.
     * @param password The plain text password entered by the user.
     * @return true if the credentials are valid, false otherwise.
     */
    public static boolean isValidCredentials(String username, String password) {
        String sql = "SELECT Password FROM Users WHERE Username = ?";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String db_password = rs.getString("Password");
                    return (password).equals(db_password);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error validating credentials: " + e.getMessage());
        }
        return false;
    }

    /**
     * Adds a new user and their profile to the database.
     *
     * @param username the user's username
     * @param password the user's password (should be hashed before calling this method)
     * @param displayName the user's display name
     * @return true if user is added successfully, false otherwise
     */
    public static boolean addUser(String username, String password, String displayName) {
        String sqlUser = "INSERT INTO Users(Username, Password) VALUES(?,?)";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmtUser = conn.prepareStatement(sqlUser, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmtUser.setString(1, username);
            pstmtUser.setString(2, password);
            int affectedRows = pstmtUser.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Adding user failed, no rows affected.");
                return false;
            }
            ResultSet rs = pstmtUser.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                return addProfile(userId, displayName);
            }
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
        return false;
    }

    /**
     * Adds a profile for a specific user ID.
     *
     * @param userId the user ID for which the profile is added
     * @param displayName the display name for the user
     * @return true if profile is added successfully, false otherwise
     */
    private static boolean addProfile(int userId, String displayName) {
        String sqlProfile = "INSERT INTO UserProfiles(UserID, DisplayName) VALUES(?,?)";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmtProfile = conn.prepareStatement(sqlProfile)) {
            pstmtProfile.setInt(1, userId);
            pstmtProfile.setString(2, displayName);
            int affectedRows = pstmtProfile.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Adding user profile failed, no rows affected.");
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error adding user profile: " + e.getMessage());
        }
        return false;
    }

    /**
     * Updates the password for a user.
     *
     * @param username the username whose password is to be updated
     * @param newPassword the new password (should be hashed before calling this method)
     * @return true if the password is updated successfully, false otherwise
     */
    public static boolean updatePassword(String username, String newPassword) {
        String sql = "UPDATE Users SET Password = ? WHERE Username = ?";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Updating password failed, no rows affected.");
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating password: " + e.getMessage());
        }
        return false;
    }

    /**
     * Updates the profile display name for a specific user.
     *
     * @param username the username whose profile is to be updated
     * @param newDisplayName new display name to set
     * @return true if the profile is updated successfully, false otherwise
     */
    public static boolean updateProfile(String username, String newDisplayName) {
        String sql = "UPDATE UserProfiles SET DisplayName = ? WHERE UserID = (SELECT UserID FROM Users WHERE Username = ?)";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newDisplayName);
            pstmt.setString(2, username);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Updating profile failed, no rows affected.");
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating user profile: " + e.getMessage());
        }
        return false;
    }

    /**
     * Deletes a user and their profile from the database.
     *
     * @param username the username of the user to delete
     * @return true if the user and profile are deleted successfully, false otherwise
     */
    public static boolean deleteUser(String username) {
        if (!deleteUserProfile(username)) {
            return false;
        }
        String sql = "DELETE FROM Users WHERE Username = ?";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Deleting user failed, no rows affected.");
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
        return false;
    }

    /**
     * Deletes a user's profile.
     *
     * @param username the username whose profile is to be deleted
     * @return true if the profile is deleted successfully, false otherwise
     */
    private static boolean deleteUserProfile(String username) {
        String sql = "DELETE FROM UserProfiles WHERE UserID = (SELECT UserID FROM Users WHERE Username = ?)";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Deleting user profile failed, no rows affected.");
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting user profile: " + e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves the user details.
     *
     * @param username the username to search for
     * @return String array containing user ID, Username, and Password or null if user not found
     */
    public static String[] getUser(String username) {
        String sql = "SELECT UserID, Username, Password FROM Users WHERE Username = ?";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new String[]{String.valueOf(rs.getInt("UserID")), rs.getString("Username"), rs.getString("Password")};
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves and returns the profile details for a specific username.
     *
     * @param username the username whose profile is to be displayed
     * @return String containing the display name or null if profile not found
     */
    public static String getProfile(String username) {
        String sql = "SELECT DisplayName FROM UserProfiles WHERE UserID = (SELECT UserID FROM Users WHERE Username = ?)";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("DisplayName");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving profile: " + e.getMessage());
        }
        return null;
    }
}
