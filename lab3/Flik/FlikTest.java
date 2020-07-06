import static org.junit.Assert.*;

import org.junit.Test;

public class FlikTest {

    @Test
    public void testIsSameNumber() {
        int a = 0;
        int b = 0;
        int c = 1;
        int m = 128;
        int n = 128;
        assertTrue(Flik.isSameNumber(a, b));
        assertFalse(Flik.isSameNumber(a, c));
        assertTrue(Flik.isSameNumber(m, n));
    }
}
