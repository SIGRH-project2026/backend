package sn.gainde2000.backenmfpai.commons.utils.helpers;

import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
@Slf4j
public class Utilitaire {

        private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public static String generateRandomWord() {
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                int index = random.nextInt(alphabet.length());
                sb.append(alphabet.charAt(index));
            }
            return sb.toString().toUpperCase();
        }

    public static String genererReférence(){
        return "MFP-REF" + "-"  +generateRandomWord();
    }

    public static String genererNumero(){
        return "MFP-DM" + "-"  +new Random().nextInt(90) + 10;
    }


    public static String genererNumeroRefMut(){
        return "MUT" + "-"  +new Random().nextInt(90) + 10;
    }




    // abc.zip
    // abc.pdf,..
    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        // application/pdf
        // application/xml
        // image/gif, ...
        String mineType = servletContext.getMimeType(fileName);
        try {
            MediaType mediaType = MediaType.parseMediaType(mineType);
            return mediaType;
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

}


