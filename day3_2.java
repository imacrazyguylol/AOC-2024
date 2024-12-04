import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// This is the worst thing I have ever programmed in my entire life, and I hope it stays that way
// Part 1 took less than 5 mins 
// Part 2 completed at ~1:50AM EST
class day3_2 {
    static final Pattern mul = Pattern.compile("mul\\([0-9]+,[0-9]+\\)");
    static final Pattern Do = Pattern.compile("do\\(\\)");
    static final Pattern Dont = Pattern.compile("don't\\(\\)");

    public static void main(String[] aaa) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day3")));
        String line = "don't()".concat("do()").concat(in.readLine()); // this is fucking stupid

        int sum = 0;

        Matcher dontMatch = Dont.matcher(line);
        Matcher doMatch = Do.matcher(line);
        Matcher mulMatch = mul.matcher(line);

        dontMatch.find();
        while (doMatch.find(dontMatch.start())) {

            // System.out.println(doMatch.group().concat(" " + doMatch.start()));
            if (mulMatch.find(doMatch.start())) {
                if (dontMatch.find(doMatch.start())) {

                    // System.out.println(dontMatch.group().concat(" " + dontMatch.start()));
                    while (mulMatch.start() < dontMatch.start()) {
                        String mul = mulMatch.group();
                        int[] bruh = Arrays.stream(mul.substring(4, mul.length() - 1).split(","))
                                .mapToInt(Integer::parseInt).toArray();

                        // System.out.println(mul.concat(" " + mulMatch.start()));
                        sum += bruh[0] * bruh[1];
                        if (!mulMatch.find())
                            break;
                    }
                } else {
                    while (!mulMatch.hitEnd()) {
                        String mul = mulMatch.group();
                        int[] bruh = Arrays.stream(mul.substring(4, mul.length() - 1).split(","))
                                .mapToInt(Integer::parseInt).toArray();

                        // System.out.println(mul.concat(" " + mulMatch.start()));
                        sum += bruh[0] * bruh[1];
                        mulMatch.find();
                    }
                    break;
                }
            } else {
                break;
            }
        }

        System.out.println(sum);
    }
}