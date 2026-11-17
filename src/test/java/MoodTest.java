import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class MoodTest {
    @Test void valuesAvailable() { assertEquals(4, Mood.values().length); }
    @Test void invalidValueRejected() { assertThrows(IllegalArgumentException.class, () -> Mood.valueOf("INVALID")); }
}
