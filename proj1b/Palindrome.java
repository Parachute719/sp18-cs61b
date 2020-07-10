public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> a = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            a.addLast(word.charAt(i));
        }
        return a;
    }

    public boolean isPalindrome(String word) {
        /** string version
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        String back = "";
        for (int i = word.length(); i > 0; i--) {
            back +=  word.charAt(i-1);
        }
        if (back.equals(word)) {
            return true;
        }
        return false; */

        /** deque version */
        Deque<Character> wordList = wordToDeque(word);
        return isPalindromeHelper(wordList, wordList.size());
    }

    private boolean isPalindromeHelper(Deque<Character> w, int size) {
        if (size == 0 || size == 1) {
            return true;
        } else if (w.removeFirst() == w.removeLast()) {
            return isPalindromeHelper(w, w.size());
        }
        return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordList = wordToDeque(word);
        return isPalindromeHelper(wordList, wordList.size(), cc);
    }

    public boolean isPalindromeHelper(Deque<Character> w, int size, CharacterComparator cc) {
        if (size == 0 || size == 1) {
            return true;
        } else {
            char firstCharacter = w.removeFirst();
            char lastCharacter = w.removeLast();
            if (cc.equalChars(firstCharacter, lastCharacter)) {
                return isPalindromeHelper(w, w.size(), cc);
            }
            return false;
        }
    }
}
