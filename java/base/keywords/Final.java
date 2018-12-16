import java.util.ArrayList;
import java.util.List;

public class Final {

    public static void main(String[] args) {
        final List<Integer> ints = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            ints.add(i);
        }
        for (Integer i : ints) {
            System.out.println(String.valueOf(i));
        }

        // final int iii = 23;
        // iii = 2333;
    }
}
