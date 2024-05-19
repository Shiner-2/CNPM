package com.example.hhd.Algo;

import com.example.hhd.AppController;
import com.example.hhd.Dictionary.DictionaryController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecentWord {
   ArrayList<String> recentWords = new ArrayList<>();

   public RecentWord() {
   }

   /**
    * Constructor.
    * @param wordList : list of recent words separated by '\n'
    */
   public RecentWord(String wordList) {
      importWord(wordList);
//      recentWords.addAll(List.of(wordList.split("\n")));
   }

   public void importWord(String wordList) {
      recentWords.clear();
      recentWords.addAll(List.of(wordList.split("\n")));
   }

   public void addWord(String word) {
      recentWords.remove(word);
      recentWords.add(0, word);
      while (recentWords.size() > 25) {
         recentWords.removeLast();
      }
   }

   public void removeWord(String word) {
      recentWords.remove(word);
   }

   public ArrayList<String> get(String target) {
      ArrayList<String> result = new ArrayList<>();
      for (String w : recentWords) {
         if (w.startsWith(target)) {
            result.add(w);
         }
      }
      return result;
   }

   public String exportWord() {
      return String.join("\n", recentWords);
   }

   public void clear() {
      recentWords.clear();
   }
}
