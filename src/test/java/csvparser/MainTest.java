package csvparser;

import csvparser.Main;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    void testMainWithValidFile() throws IOException {

        File inputFile = File.createTempFile("test", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            writer.write("hello world hello");
        }

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Main.main(new String[]{inputFile.getAbsolutePath()});

        String output = outContent.toString();
        assertTrue(output.contains("Результат записан в output.csv"));

        inputFile.delete();
        new File("output.csv").delete();
    }

    @Test
    void testMainWithNoArguments() {

        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));


        Main.main(new String[]{});


        String error = errContent.toString();
        assertTrue(error.contains("Укажите имя входного файла."));
    }
}
