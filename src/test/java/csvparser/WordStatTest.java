package csvparser;

import org.junit.jupiter.api.Test;
import csvparser.WordStat;

import static org.junit.jupiter.api.Assertions.*;

public class WordStatTest {

    @Test
    void testCompareToDescending() {
        WordStat a = new WordStat("apple", 5);
        WordStat b = new WordStat("banana", 10);
        assertTrue(a.compareTo(b) < 0); // потому что 5 < 10
    }

    @Test
    void testEqualsAndHashCode() {
        WordStat a = new WordStat("word", 2);
        WordStat b = new WordStat("word", 2);
        WordStat c = new WordStat("word", 5);
        WordStat d = new WordStat("other", 2);

        assertEquals(a, b);
        assertNotEquals(a, c); // если обновишь equals
        assertNotEquals(a, d);

        assertEquals(a.hashCode(), b.hashCode());
    }
}

