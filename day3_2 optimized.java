import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class OptDay3 {
    static final Pattern p = Pattern.compile("(mul\\([0-9]+,[0-9]+\\)|do\\(\\)|don't\\(\\))");

    public static void main(String[] aaa) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day3")));
        String line = in.readLine();

        int sum = 0;
        Matcher m = p.matcher(line);
        boolean enabled = true;

        while (m.find()) {
            if (m.group().equals("don't()")) {
                enabled = false;
                continue;
            } else if (m.group().equals("do()")) {
                enabled = true;
                continue;
            }

            if (enabled) {
                String mul = m.group();
                int[] bruh = Arrays.stream(mul.substring(4, mul.length() - 1).split(","))
                        .mapToInt(Integer::parseInt).toArray();
                sum += bruh[0] * bruh[1];
            }
        }

        System.out.println(sum);
    }
}
