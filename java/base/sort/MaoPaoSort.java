package sort;

public class MaoPaoSort {
    public static void main(String[] args) {
        int[] ints = { 3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48, 34, 23, 43 };


		int length = ints.length;
		for (int i = 0; i < length; i++) {
			int s = i + 1;
			for (int j = i; j > i; j--) {
				if (ints[s] < ints[j]) {
					int t = ints[s];
					ints[s] = ints[j];
					ints[j] = t;
				}
			}
		}

        for (int i : ints) {
            System.out.println(String.valueOf(i));
        }

    }
}
