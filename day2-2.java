import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

class day2_2 {
    public static void main(String[] aaa) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day2")));

        String line;
        int safe = 0;
        while ((line = in.readLine()) != null) {
            int[] report = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();

            int i = 0;
            boolean increasing = report[report.length - 1] - report[0] > 0;

            while (i < report.length - 1) {
                if (
                    report[i + 1] - report[i] > 0 != increasing ||
                    Math.abs(report[i + 1] - report[i]) > 3 || 
                    Math.abs(report[i + 1] - report[i]) < 1
                ) {
                    if (
                        i != 0 &&
                        (
                            report[i + 1] - report[i - 1] > 0 != increasing ||
                            Math.abs(report[i + 1] - report[i - 1]) > 3 || 
                            Math.abs(report[i + 1] - report[i - 1]) < 1 
                        )
                    ) {
                        break;
                    }

                    if (
                        i < report.length - 2 &&
                        (
                            report[i + 2] - report[i] > 0 != increasing ||
                            Math.abs(report[i + 2] - report[i]) > 3 || 
                            Math.abs(report[i + 2] - report[i]) < 1 
                        )
                    ) {
                        break;
                    }
                }

                i++;
            }

            if (i == report.length - 1) {
                safe++;
            }
        }

        System.out.println(safe);
    }
}
