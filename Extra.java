import java.util.Arrays;

public class Extra {
    public static int[] parseInts(String line, String delimiter) {
        String[] temp = line.split(delimiter);
        int[] out = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            out[i] = Integer.parseInt(temp[i]);
        }
        return out;
    }

    public static void print(String output) {
            System.out.println(output);
        }

    public static void print(Object[] output) {
        System.out.println(Arrays.toString(output));
    }
}
