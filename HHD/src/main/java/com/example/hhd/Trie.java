package com.example.hhd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Trie {
    public static class TrieNode {
        HashMap<Character, TrieNode> next = new HashMap<>();
        Word current_word = null;
    }

    TrieNode root = new TrieNode();

    /**
     * Get root of trie.
     * @return root of trie
     */
    public TrieNode getRoot() {
        return root;
    }

    /**
     * Add new_word to trie.
     * @param new_word is the word need to add
     */
    public void insert_word(Word new_word) {
        TrieNode pNode = getRoot();

        String word_target = new_word.getWord();

        for (int i = 0; i < word_target.length(); i++) {
            char currentChar = word_target.charAt(i);

            if (!pNode.next.containsKey(currentChar)) {
                pNode.next.put(currentChar, new TrieNode());
            }

            pNode = pNode.next.get(currentChar);
        }

        pNode.current_word = new_word;
    }

    /**
     * Delete word from trie.
     * @param word is the word to be deleted
     * @return true of successfully delete word, false otherwise
     */
    public boolean delete_word(Word word) {
        TrieNode pNode = getRoot();

        String word_target = word.getWord();

        for (int i = 0; i < word_target.length(); i++) {
            char currentChar = word_target.charAt(i);

            if (!pNode.next.containsKey(currentChar)) {
                //System.out.println("This word has not been inserted");
                return false;
            }

            pNode = pNode.next.get(currentChar);
        }

        if (pNode.current_word == null) {
            //System.out.println("This word has not been inserted");
            return false;
        }

        pNode.current_word = null;
        return true;
    }

    /**
     * Delete word from trie.
     * @param word_target is the word to be deleted
     * @return true of successfully delete word, false otherwise
     */
    public boolean find_word(String word_target) {
        TrieNode pNode = getRoot();

        for (int i = 0; i < word_target.length(); i++) {
            char currentChar = word_target.charAt(i);

            if (!pNode.next.containsKey(currentChar)) {
                return false;
            }

            pNode = pNode.next.get(currentChar);
        }

        if (pNode.current_word == null) {
            return false;
        }

        return true;
    }

    /**
     * Search words that match target.
     * @param target is the prefix
     * @param recommended_size is size of the list
     * @return list of words
     */
    ArrayList<Word> search_word(String target, int recommended_size) {
        ArrayList<Word> result = new ArrayList<>();

        TrieNode pNode = getRoot();

        for (int i = 0; i < target.length(); i++) {
            char currentChar = target.charAt(i);

            if (!pNode.next.containsKey(currentChar)) {
                //System.out.println("This word has not been inserted");
                return result;
            }

            pNode = pNode.next.get(currentChar);
        }

        Queue<TrieNode> queue = new LinkedList<>();
        queue.add(pNode);

        while (!queue.isEmpty() && result.size() < recommended_size) {
            TrieNode currentNode = queue.remove();

            if (currentNode.current_word != null) {
                result.add(currentNode.current_word);
            }

            queue.addAll(currentNode.next.values());
        }

        return result;
    }

    ArrayList<Word> search_all_word(String target) {
        return search_word(target, Integer.MAX_VALUE);
    }
}
