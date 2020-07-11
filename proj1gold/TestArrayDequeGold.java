import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testArrayDeque() {
        /** addLast */
        StudentArrayDeque<Integer> student = new StudentArrayDeque<Integer>();
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<Integer>();
        for (int i = 0; i < 10; i++) {
            int random = StdRandom.uniform(100);
            student.addLast(random);
            solution.addLast(random);
        }
        for (int i = 0; i < 10; i++) {
            Integer expected = solution.get(i);
            Integer actual = student.get(i);
            assertEquals("Oh noooo!\nThis is bad:\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }

        /** addFirst */
        student = new StudentArrayDeque<Integer>();
        solution = new ArrayDequeSolution<Integer>();
        for (int i = 0; i < 10; i++) {
            int random = StdRandom.uniform(100);
            student.addFirst(random);
            solution.addFirst(random);
        }
        for (int i = 0; i < 10; i++) {
            Integer expected = solution.get(i);
            Integer actual = student.get(i);
            assertEquals("Oh noooo!\nThis is bad:\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }

        /** removeFirst */
        student = new StudentArrayDeque<Integer>();
        solution = new ArrayDequeSolution<Integer>();
        for (int i = 0; i < 10; i++) {
            int random = StdRandom.uniform(100);
            student.addFirst(random);
            solution.addFirst(random);
        }
        for (int i = 0; i < 10; i++) {
            Integer expected = solution.removeFirst();
            Integer actual = student.removeFirst();
            assertEquals("Oh noooo!\nThis is bad:\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }

        /** removeLast */
        student = new StudentArrayDeque<Integer>();
        solution = new ArrayDequeSolution<Integer>();
        String message = "";
        for (int i = 0; i < 10; i++) {
            int random = StdRandom.uniform(100);
            student.addFirst(random);
            solution.addFirst(random);
            message += "\naddFirst(" + random + ")";
        }
        for (int i = 0; i < 10; i++) {
            Integer expected = solution.removeLast();
            Integer actual = student.removeLast();
            message += "\nremoveLast(): " +actual;
            assertEquals(message, expected, actual);
        }

    }
}
