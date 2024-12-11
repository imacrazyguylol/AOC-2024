public class Extra {
    public static int[] parseInts(String line, String delimiter) {
        String[] temp = line.split(delimiter);
        int[] out = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            out[i] = Integer.parseInt(temp[i]);
        }
        return out;
    }

    public static Long[] parseLongs(String line, String delimiter) {
        String[] temp = line.split(delimiter);
        Long[] out = new Long[temp.length];
        for (int i = 0; i < temp.length; i++) {
            out[i] = Long.parseLong(temp[i]);
        }
        return out;
    }

    public static double[] parseDoubles(String line, String delimiter) {
        String[] temp = line.split(delimiter);
        double[] out = new double[temp.length];
        for (int i = 0; i < temp.length; i++) {
            out[i] = Double.parseDouble(temp[i]);
        }
        return out;
    }

    public static void print(String output) {
        System.out.println(output);
    }

    public static void print(double output) {
        System.out.println(output);
    }

    public static void print(int output) {
        System.out.println(output);
    }

    public static void print(long output) {
        System.out.println(output);
    }
}
