package csvparser;

import java.io.*;

public class FileReader {
    public static String readFile(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (Reader reader = new InputStreamReader(new FileInputStream(fileName))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                content.append((char) ch);
            }
        }
        return content.toString();
    }
}
