package csvparser;

import org.junit.jupiter.api.Test;
import csvparser.WordCounter;
import csvparser.WordStat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordCounterTest {

    @Test
    void testProcessSimpleText() {
        WordCounter counter = new WordCounter();
        counter.processText("Hello world hello");

        assertEquals(3, counter.getTotalWords());

        List<WordStat> stats = counter.getSortedStats();
        assertEquals(2, stats.size());

        // Первое слово — "hello", встречается 2 раза
        assertEquals("hello", stats.get(0).getWord());
        assertEquals(2, stats.get(0).getCount());

        // Второе — "world", 1 раз
        assertEquals("world", stats.get(1).getWord());
        assertEquals(1, stats.get(1).getCount());
    }

    @Test
    void testProcessWithPunctuation() {
        WordCounter counter = new WordCounter();
        counter.processText("This, this. test!");

        assertEquals(3, counter.getTotalWords());

        List<WordStat> stats = counter.getSortedStats();
        assertEquals(2, stats.size());

        assertEquals("this", stats.get(0).getWord());
        assertEquals(2, stats.get(0).getCount());

        assertEquals("test", stats.get(1).getWord());
        assertEquals(1, stats.get(1).getCount());
    }

    @Test
    void testProcessEmptyText() {
        WordCounter counter = new WordCounter();
        counter.processText("   ");

        assertEquals(0, counter.getTotalWords());
        assertTrue(counter.getSortedStats().isEmpty());
    }
}
