import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class UnitTest {
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void test01() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        int sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println("sum = " + sum);
    }
}
