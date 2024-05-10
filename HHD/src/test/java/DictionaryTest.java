import com.example.hhd.Algo.Dictionary;
import com.example.hhd.Algo.TrieDictionary;
import com.example.hhd.Algo.Word;

import java.io.File;
import java.io.IOException;

public class DictionaryTest {
    public static void main(String[] args) throws IOException {
        File f = new File("src/test/resources/dictionary.txt");
        Dictionary data = new TrieDictionary();

        for (Word w : data.allWordList()) {
            System.out.println(w.getWord());
            System.out.println(w.getDefinition());
        }
        data.exportToFile(f);
    }
}
