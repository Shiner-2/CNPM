package com.example.hhd;

import com.example.hhd.Database.GameManagement;
import com.example.hhd.Database.UserManager;

public class PublicValue {
    public static String username;
    /*
    userID
    username
    password
     */
    public static String[] profile;

    /*
        userid
        username
        password
     */
    public static String[] user;

    /*
        Hangman 1
        Quiz 2
        Scrabble 3
        Wordle 4
        WordPicture 5
     */
    public static void main(String[] args) {
        GameManagement.updateUserGame(12, "ABCDEABCDE\nOPA", 0, 0);
    }
}
