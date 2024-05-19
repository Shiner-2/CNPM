package com.example.hhd;

import com.example.hhd.Algo.Dictionary;
import com.example.hhd.Algo.TrieDictionary;
import com.example.hhd.Database.GameManagement;
import com.example.hhd.Database.UserManager;
import com.example.hhd.Dictionary.DictionaryController;

import java.io.IOException;

public class PublicValue {
    public static String username;
    /*
        displayName
        VI-EN
        EN-VI
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
    public static Dictionary dataVI_EN;
    public static Dictionary dataEN_VI;

    static {
        try {
            dataVI_EN = new TrieDictionary(Dictionary.VI_EN, true);
            dataEN_VI = new TrieDictionary(Dictionary.EN_VI, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        GameManagement.updateUserGame(12, "ABCDEABCDE\nOPA", 0, 0);
    }
}
