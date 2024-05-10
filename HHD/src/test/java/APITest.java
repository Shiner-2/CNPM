import com.example.hhd.APITranslator;

import java.io.IOException;

public class APITest {
    public static void main(String[] args) throws IOException {
        String text = "Hello guys, i'm uncle Roger. Today, we will cook egg fried rice";
        System.out.println(APITranslator.translate("en", "vi", text));
        String text2 = "Tạm biệt";
        System.out.println(APITranslator.translate("vi", "en", text2));
    }
}
