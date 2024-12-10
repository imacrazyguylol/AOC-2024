import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.regex.Pattern;

class day3_1_oneline {
    public static void main(String[] aaa) throws Exception {
        // alternate universe one liner (not working rn)
        System.out.println(Pattern.compile("mul\\([0-9]+,[0-9]+\\)").matcher("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))").results().mapToInt( m -> Arrays.stream( m.group().substring(4, m.group().length() - 1).split(",") ).mapToInt(Integer::parseInt).reduce(0, (x,y) -> x * y) ).reduce(0, Integer::sum));
    }
}
