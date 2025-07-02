package csvparser;

import org.junit.jupiter.api.Test;
import csvparser.CSVWriter;
import csvparser.WordStat;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CSVWriterTest {

    @Test
    void testWriteCSVCreatesCorrectFile() throws IOException {
        // Arrange
        File tempFile = File.createTempFile("output", ".csv");
        List<WordStat> stats = Arrays.asList(
                new WordStat("hello", 2),
                new WordStat("world", 1)
        );
        int totalWords = 3;

        // Act
        CSVWriter.writeCSV(tempFile.getAbsolutePath(), stats, totalWords);

        // Assert
        List<String> lines = Files.readAllLines(tempFile.toPath());

        assertEquals("Слово,Частота,Частота (%)", lines.get(0));
        assertTrue(lines.get(1).startsWith("hello"));
        assertTrue(lines.get(1).contains("2"));
        assertTrue(lines.get(2).startsWith("world"));
        assertTrue(lines.get(2).contains("1"));
        assertTrue(lines.get(2).contains("33,33"));

        tempFile.delete();
    }

    @Test
    void testWriteCSVWithEmptyStats() throws IOException {
        File tempFile = File.createTempFile("output_empty", ".csv");

        CSVWriter.writeCSV(tempFile.getAbsolutePath(), List.of(), 1);

        List<String> lines = Files.readAllLines(tempFile.toPath());
        assertEquals(1, lines.size());  // только заголовок
        assertEquals("Слово,Частота,Частота (%)", lines.get(0));

        tempFile.delete();
    }

    @Test
    void testWriteCSVWithZeroTotalWords() {
        List<WordStat> stats = List.of(new WordStat("test", 1));

        assertThrows(IllegalArgumentException.class, () -> {
            CSVWriter.writeCSV("should_fail.csv", stats, 0);
        });
    }
}
