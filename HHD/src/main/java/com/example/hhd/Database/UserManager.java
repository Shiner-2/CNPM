package com.example.hhd.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {

    /**
     * Adds a new user to the database.
     * @param username the user's username
     * @param password the user's password (hashed for security)
     */
    public static void addUser(String username, String password) {
        String sql = "INSERT INTO Users(Username, Password) VALUES(?,?)";
        try {
            DBHelper.executeUpdate(sql, pstmt -> {
                pstmt.setString(1, username);
                pstmt.setString(2, password); // Ensure password is hashed
            });
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    /**
     * Updates a user's password in the database.
     * @param userId the user's ID
     * @param newPassword the new password to be set (hashed)
     */
    public static void updateUserPassword(int userId, String newPassword) {
        String sql = "UPDATE Users SET Password = ? WHERE UserID = ?";
        try {
            DBHelper.executeUpdate(sql, pstmt -> {
                pstmt.setString(1, newPassword); // Ensure password is hashed
                pstmt.setInt(2, userId);
            });
            System.out.println("User password updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating user password: " + e.getMessage());
        }
    }

    /**
     * Deletes a user from the database.
     * @param userId the ID of the user to delete
     */
    public static void deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try {
            DBHelper.executeUpdate(sql, pstmt -> pstmt.setInt(1, userId));
            System.out.println("User deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    /**
     * Retrieves a user by username and prints their details.
     * @param username the username to search for
     */
    public static void getUserByUsername(String username) {
        String sql = "SELECT UserID, Username, Password FROM Users WHERE Username = ?";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("UserID: " + rs.getInt("UserID") + ", Username: " + rs.getString("Username") + ", Password: " + rs.getString("Password"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
        }
    }
}
