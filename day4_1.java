import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import com.sun.tools.javac.util.Pair;

class day4_1 {
    public static void main(String[] aaa) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day4")));
        String line;

        char[][] grid = new char[140][140];

        int i = 0;
        while ((line = in.readLine()) != null) {
            grid[i] = line.toCharArray();
            i++;
        }

        // System.out.println(Arrays.toString(Arrays.stream(grid).map(l -> Arrays.toString(l)).toArray()));

    }
}