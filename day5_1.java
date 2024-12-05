import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class day5_1 {
    public static void main(String[] aaa) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day5")));
        String line;
        Map<Integer, List<Integer>> map = new HashMap<>();

        while (!(line = in.readLine()).isEmpty()) {
            int[] pair = Arrays.stream(line.split("\\|")).mapToInt(Integer::parseInt).toArray();
            map.putIfAbsent(pair[0], new ArrayList<Integer>());
            map.get(pair[0]).add(pair[1]);
        }

        int sum = 0;
        outer: while ((line = in.readLine()) != null) {
            int[] nums = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
            for (int i = 0; i < nums.length - 1; i++) {
                int[] testArr;
                try {
                    testArr = map.get(nums[i + 1]).stream().mapToInt(e -> (Integer) e).toArray();
                } catch (Exception ex) {
                    continue;
                }

                for (int j = 0; j < testArr.length; j++) {
                    if (nums[i] == testArr[j]) {
                        continue outer;
                    }
                }
            }

            sum += nums[(nums.length - 1) / 2];
        }

        System.out.println(sum);
    }
}