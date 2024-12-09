import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

class day7_1 extends Extra {
    public static void main(String[] aaa) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader( new File("inputs/willsday7")));
        String line;
        long sum = 0;

        while ((line = in.readLine()) != null) {
            String[] params = line.split(": ");
            double y = Double.parseDouble(params[0]);
            long[] x = Extra.parseLongs(params[1], " ");

            sum += testOperators(y, x.length - 1, x) ? y : 0;
        }

        System.out.println(sum);
    }

    public static boolean testOperators(double y, int n, long[] x) {
        if (n == 0) {
            if (y == x[0]) {
                System.out.println(Arrays.toString(x));
                return true;
            } else {
                return false;
            }
        } else {
            return testOperators(y - x[n], n - 1, x) || testOperators(y / x[n], n - 1, x);
        }
    }
}

