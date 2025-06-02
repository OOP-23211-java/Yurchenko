package src.main.java;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Укажите имя входного файла.");
            return;
        }

        String inputFile = args[0];
        WordCounter counter = new WordCounter();

        try {
            String content = FileReader.readFile(inputFile);
            counter.processText(content);
            CSVWriter.writeCSV("output.csv", counter.getSortedStats(), counter.getTotalWords());
            System.out.println("Результат записан в output.csv");
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}