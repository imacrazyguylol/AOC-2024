import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class asdasfds {
    public static void main(String[] aaa) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day1")));

        int[] left = new int[1000];
        List<Integer> right = new ArrayList<>();
        String line;
        int i = 0;
        while ((line = in.readLine()) != null) {
            String[] split = line.split("   ");
            left[i] = Integer.parseInt(split[0]);
            right.add(i, Integer.parseInt(split[1]));
            i++;
        }

        int sum = 0;
        for (i = 0; i < 1000; i++) {
            int count = 0;
            for (int j = 0; j < right.size(); j++) {
                if (left[i] == right.get(j)) {
                    count++;
                }
            }
            sum += left[i] * count;
        }

        System.out.println(sum);
    }
}