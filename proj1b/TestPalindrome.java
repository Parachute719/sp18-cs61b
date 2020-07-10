import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {

    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("B"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertFalse(palindrome.isPalindrome("Bob"));
    }

    @Test
    public void testIsPalindromeOffByOne() {
        OffByOne cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", cc));
        assertTrue(palindrome.isPalindrome("a", cc));
        assertTrue(palindrome.isPalindrome("ab", cc));
        assertTrue(palindrome.isPalindrome("&%", cc));
    }

    @Test
    public void testIsPalindromeOffByN() {
        OffByN cc5 = new OffByN(5);
        assertTrue(palindrome.isPalindrome("af", cc5));
        assertTrue(palindrome.isPalindrome("fk", cc5));
        assertFalse(palindrome.isPalindrome("ab", cc5));
        assertFalse(palindrome.isPalindrome("&%", cc5));
    }
}
