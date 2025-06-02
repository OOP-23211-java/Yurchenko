package src.main.java;

import java.io.*;
import java.util.List;

public class CSVWriter {
    public static void writeCSV(String fileName, List<WordStat> stats, int totalWords) throws IOException {
        if (totalWords == 0) {
            throw new IllegalArgumentException("Total words must not be zero");
        }

        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println("Слово,Частота,Частота (%)");
            for (WordStat stat : stats) {
                double percent = 100.0 * stat.getCount() / totalWords;
                writer.printf("%s, \t %d, \t %.2f%n", stat.getWord(), stat.getCount(), percent);
            }
        }
    }
}
