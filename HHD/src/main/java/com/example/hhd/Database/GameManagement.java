package com.example.hhd.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameManagement {

    /**
     * Adds a new game to the Games table.
     *
     * @param gameName Name of the game
     * @return true if game is added successfully, false otherwise
     */
    public static boolean addGame(String gameName) {
        String sql = "INSERT INTO Games(GameName) VALUES(?)";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gameName);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error adding game: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates a game in the Games table.
     *
     * @param gameId The ID of the game to update
     * @param newGameName New name of the game
     * @return true if the game is updated successfully, false otherwise
     */
    public static boolean updateGame(int gameId, String newGameName) {
        String sql = "UPDATE Games SET GameName = ? WHERE GameID = ?";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newGameName);
            pstmt.setInt(2, gameId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating game: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a game from the Games table.
     *
     * @param gameId The ID of the game to delete
     * @return true if the game is deleted successfully, false otherwise
     */
    public static boolean deleteGame(int gameId) {
        String sql = "DELETE FROM Games WHERE GameID = ?";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting game: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves game details from the Games table.
     *
     * @param gameId The ID of the game
     * @return a string representation of the game details or null if not found
     */
    public static String getGame(int gameId) {
        String sql = "SELECT GameID, GameName FROM Games WHERE GameID = ?";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return String.format("GameID: %d, GameName: %s", rs.getInt("GameID"), rs.getString("GameName"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving game: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a user game with initial settings to the UserGames table.
     *
     * @param userId The user's ID
     * @param gameId The game's ID
     * @param gameState Initial game state
     * @param highScore Initial high score
     * @param winTimes Initial win times
     * @return true if user game is added successfully, false otherwise
     */
    public static boolean addUserGame(int userId, int gameId, String gameState, int highScore, int winTimes) {
        String sql = "INSERT INTO UserGames(UserID, GameID, GameState, HighScore, WinTimes) VALUES(?,?,?,?,?)";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, gameId);
            pstmt.setString(3, gameState);
            pstmt.setInt(4, highScore);
            pstmt.setInt(5, winTimes);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error adding user game: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates a user game in the UserGames table.
     *
     * @param userGameId The ID of the user game
     * @param newGameState New game state
     * @param newHighScore New high score
     * @param newWinTimes New win times
     * @return true if user game is updated successfully, false otherwise
     */
    public static boolean updateUserGame(int userGameId, String newGameState, int newHighScore, int newWinTimes) {
        String sql = "UPDATE UserGames SET GameState = ?, HighScore = ?, WinTimes = ? WHERE UserGameID = ?";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newGameState);
            pstmt.setInt(2, newHighScore);
            pstmt.setInt(3, newWinTimes);
            pstmt.setInt(4, userGameId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating user game: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a user game from the UserGames table.
     *
     * @param userGameId The ID of the user game to delete
     * @return true if user game is deleted successfully, false otherwise
     */
    public static boolean deleteUserGame(int userGameId) {
        String sql = "DELETE FROM UserGames WHERE UserGameID = ?";
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userGameId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting user game: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves user game details from the UserGames table.
     *
     * @param userId The user's ID
     * @param gameId The game's ID
     * @return A string representation of the user game details, or null if not found
     */
    public static String[] getUserGame(int userId, int gameId) {
        String sql = "SELECT UserGameID, GameState, HighScore, WinTimes FROM UserGames WHERE UserID = ? AND GameID = ?";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    return new String[] {String.valueOf(rs.getInt("UserGameID")), rs.getString("GameState"), String.valueOf(rs.getInt("HighScore")), String.valueOf(rs.getInt("WinTimes"))};

//                    return String.format(
//                            "UserGameID: %d, GameState: %s, HighScore: %d, WinTimes: %d",
//                            rs.getInt("UserGameID"),
//                            rs.getString("GameState"),
//                            rs.getInt("HighScore"),
//                            rs.getInt("WinTimes")
//                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user game: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}