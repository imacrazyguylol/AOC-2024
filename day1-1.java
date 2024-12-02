import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

class day1_1 {
    public static void main(String[] aaa) throws Exception {
        BufferedReader in = new BufferedReader( new FileReader( new File("inputs/day1") ) );

        int[] left = new int[1000];
        int[] right = new int[1000];
        String line;
        while ((line = in.readLine()) != null) {
            String[] split = line.split("   ");
            int leftInt  = Integer.parseInt(split[0]);
            int rightInt = Integer.parseInt(split[1]);

            int i = 0;
            while (i < 1000 && leftInt < left[i]) {i++;}

            for(;i < 1000; i++) {
                int temp = left[i];
                left[i] = leftInt;
                leftInt = temp;
            }

            i = 0;
            while (i < 1000 && rightInt < right[i]) i++;

            for(;i < 1000; i++) {
                int temp = right[i];
                right[i] = rightInt;
                rightInt = temp;
            }
        }

        int sum = 0;

        for (int i = 0; i < 1000; i++) {
            sum += Math.abs(right[i] - left[i]);
        }

        // System.out.println(Arrays.toString(left));
        // System.out.println(Arrays.toString(right));
        System.out.println(sum);
    }
}