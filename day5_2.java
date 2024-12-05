import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class day5_2 {
    static Map<Integer, List<Integer>> map = new HashMap<>();
    public static void main(String[] aaa) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day5")));
        String line;

        while (!(line = in.readLine()).isEmpty()) {
            int[] pair = Arrays.stream(line.split("\\|")).mapToInt(Integer::parseInt).toArray();
            map.putIfAbsent(pair[0], new ArrayList<Integer>()); 
            map.get(pair[0]).add(pair[1]);
        }

        int sum = 0;
        while ((line = in.readLine()) != null) {
            int[] nums = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray(); 
            outer: for (int i = 0; i < nums.length - 1; i++) {
                int[] testArr;

                try {
                    testArr = map.get(nums[i + 1]).stream().mapToInt(e -> (Integer) e).toArray(); // awful
                } catch (Exception ex) {
                    continue;
                }

                for (int j = 0; j < testArr.length; j++) { // horrendous
                    if (nums[i] == testArr[j]) {
                        
                        // list l is just the same as nums
                        List<Integer> l = new ArrayList<>();
                        for (int x : nums) {
                            l.add(x);
                        }
                        
                        System.out.println(l);

                        l.sort((Integer a, Integer b) -> compareUpdates(a, b));
                        
                        System.out.println("SORTED:\n" + l + "\n");
                        System.out.println(l.get((l.size() - 1) / 2) + "\n");
                        sum += l.get((l.size() - 1) / 2);
                        break outer;
                    }
                }
            }
        }

        System.out.println(sum);
    }

    static int compareUpdates(int a, int b) {
        System.out.println(a + "\n" + b);
        if (!map.containsKey(a)) {
            return 1;
        } else {
            return map.get(a).contains(b) ? -1 : 1;
        }
    }
}