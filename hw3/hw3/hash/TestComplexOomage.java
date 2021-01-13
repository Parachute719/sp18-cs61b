package hw3.hash;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();

        for (int i = 0; i < 1000; i += 1) {
            ArrayList<Integer> params = new ArrayList<>();
            for (int m = 0; m < 4; m++) { // generate the first 32bits(Integer in Java are 32bits)
                params.add(StdRandom.uniform(255));
            }
            for (int m = 0; m < 4; m++) { // generate the same 32bits to cause hashcodes collision
                params.add(m);
            }
            oomages.add(new ComplexOomage(params));
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /*
     * that shows the flaw in the hashCode function.
     */

    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();
        int N = 1000;
        for (int i = 0; i < N; i++) {
            deadlyList.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
