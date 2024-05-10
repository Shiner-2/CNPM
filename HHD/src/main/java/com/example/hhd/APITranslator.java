package com.example.hhd;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class APITranslator {

    public static void main(String[] args) throws IOException {
        String text = "System";
        //Translated text: Hallo Welt!
        try {
            System.out.println("Translated text: " + translate("en", "vi", text));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Translated text: " + translate("vi", "en", text));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = "https://script.google.com/macros/s/AKfycbzhfVzJpR_QDIBAtpiHD8zcvCpt01TNhUcnkQS_yQTzVDPz97933inwllY96R79JW13/exec" +
                "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = con.getInputStream().read(buffer)) != -1; ) {
            result.write(buffer, 0, length);
        }
        // StandardCharsets.UTF_8.name() > JDK 7
        return result.toString(StandardCharsets.UTF_8);
    }

}
