package src.main.java;

import java.util.*;

public class WordCounter {
    private final Map<String, Integer> frequencyMap = new HashMap<>();
    private int totalWords = 0;

    public void processText(String text) {
        StringBuilder wordBuilder = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                wordBuilder.append(c);
            } else if (wordBuilder.length() > 0) {
                addWord(wordBuilder.toString().toLowerCase());
                wordBuilder.setLength(0);
            }
        }
        if (wordBuilder.length() > 0) {
            addWord(wordBuilder.toString().toLowerCase());
        }
    }

    private void addWord(String word) {
        frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        totalWords++;
    }

    public List<WordStat> getSortedStats() {
        List<WordStat> stats = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            stats.add(new WordStat(entry.getKey(), entry.getValue()));
        }
        stats.sort(Collections.reverseOrder());
        return stats;
    }

    public int getTotalWords() {
        return totalWords;
    }
}
