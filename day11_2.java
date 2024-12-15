import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

class day11_2 {
    static Map<Long, long[]> memo = new HashMap<>();
    static Map<Long, Long> numInstances = new HashMap<>();
    public static void main(String[] args) throws Exception {
        /* BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day11")));
        Long[] initial = parseLongs(in.readLine(), " ");

        for (long i : initial) {
            numInstances.put(i, (long) 1);
        } */

        numInstances.put((long) Integer.parseInt(args[0]), (long) 1);
        System.out.println(numInstances.toString());

        long startTime = System.nanoTime();
        for (int n = 1; n <= Integer.parseInt(args[1]); n++) {
            Map<Long, Long> temp = new HashMap<>();

            for (long stone : numInstances.keySet()) {
                long count = numInstances.get(stone);
                
                long[] calced = memo(stone);

                for (long x : calced) {
                    Long prev = temp.putIfAbsent(x, count);

                    if (prev != null) {
                        temp.replace(x, prev + count);
                    }
                }
            }

            numInstances = temp;
            // System.out.println(numInstances.toString());
        }
        long endTime = System.nanoTime();

        long sum = 0;
        for (long x : numInstances.keySet()) {
            sum += numInstances.get(x);
        }
        System.out.println(sum);
        System.out.println("Took " + ((double) (endTime - startTime) / 1000000) + "ms");
    }

    public static long[] memo(Long n) {
        String s = Long.toString(n);

        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        if (n == 0) {
            memo.put(n, new long[] { 1 });
        } else if (s.length() % 2 == 0) {
            memo.put(n, new long[] { Long.parseLong(s.substring(0, s.length() / 2)),
                    Long.parseLong(s.substring(s.length() / 2)) } );
        } else {
            memo.put(n, new long[] { n * 2024 } );
        }

        return memo.get(n);
    }
}
