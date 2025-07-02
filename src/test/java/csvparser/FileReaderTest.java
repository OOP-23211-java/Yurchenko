package csvparser;

import org.junit.jupiter.api.Test;
import csvparser.FileReader;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class FileReaderTest {

    @Test
    void testReadFileSuccess() throws IOException {
        File tempFile = File.createTempFile("testfile", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("Привет, мир!");
        }

        String result = FileReader.readFile(tempFile.getAbsolutePath());

        assertEquals("Привет, мир!", result);


        tempFile.delete();
    }

    @Test
    void testReadFileNotFound() {
        assertThrows(IOException.class, () -> {
            FileReader.readFile("файл_которого_нет.txt");
        });
    }

    public static class WordStatTest {
    }
}

