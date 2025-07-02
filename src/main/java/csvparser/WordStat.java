package csvparser;

public class WordStat implements Comparable<WordStat> {
    private final String word;
    private final int count;

    public WordStat(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(WordStat other) {
        return Integer.compare(this.count, other.count);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordStat other)) return false;
        return word.equals(other.word) && count == other.count;
    }

    @Override
    public int hashCode() {
        return word.hashCode() * 31 + count;
    }

}
