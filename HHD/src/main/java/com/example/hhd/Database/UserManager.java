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
     * Checks if the username already exists in the database.
     * @param username the username to check
     * @return true if the username exists, false otherwise
     */
    private static boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM Users WHERE Username = ?";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if there is at least one row
            }
        } catch (SQLException e) {
            System.out.println("Database error when checking username: " + e.getMessage());
            return false;
        }
    }

    /**
     * Adds a new user to the database.
     * @param username the user's username
     * @param password the user's password (hashed for security)
     */
    public static void addUser(String username, String password) {
        if (usernameExists(username)) {
            System.out.println("Error: Username already exists.");
            return;
        }
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
     * @param username the username of the user to update
     * @param newPassword the new password to be set (hashed)
     */
    public static void updateUserPassword(String username, String newPassword) {
        if (!usernameExists(username)) {
            System.out.println("Error updating password: Username does not exist.");
            return;
        }
    
        String sql = "UPDATE Users SET Password = ? WHERE Username = ?";
        try {
            DBHelper.executeUpdate(sql, pstmt -> {
                pstmt.setString(1, newPassword);
                pstmt.setString(2, username);
            });
            System.out.println("User password updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating user password: " + e.getMessage());
        }
    }   

    /**
     * Deletes a user from the database.
     * @param username the username of the user to delete
     */
    public static void deleteUser(String username) {
        if (!usernameExists(username)) {
            System.out.println("Error deleting user: Username does not exist.");
            return;
        }
    
        String sql = "DELETE FROM Users WHERE Username = ?";
        try {
            DBHelper.executeUpdate(sql, pstmt -> {
                pstmt.setString(1, username);
            });
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
        if (!usernameExists(username)) {
            System.out.println("Error: Username does not exist.");
            return;
        }
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
