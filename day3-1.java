import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class day3_1 {
    static final Pattern p = Pattern.compile("mul\\([0-9]+,[0-9]+\\)");
    public static void main(String[] aaa) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day3")));
        String line = in.readLine();

        int sum = 0;
        Matcher m = p.matcher(line);
        while (m.find()) {
            String mul = m.group();
            int[] bruh = Arrays.stream(mul.substring(4, mul.length() - 1).split(",")).mapToInt(Integer::parseInt).toArray();
            sum += bruh[0] * bruh[1];
        }

        System.out.println(sum);
    }
}
