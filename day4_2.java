import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class day4_2 {
    static char[][] grid = new char[140][140];

    static char grid(int y, int x) {
        try {
            return grid[y][x];
        } catch (Exception e) {
            return '.';
        }
    }

    static int checkDiagonals(int y, int x) {
        int sum = 0;

        char NW = grid(y - 1, x - 1);
        char NE = grid(y - 1, x + 1);
        char SW = grid(y + 1, x - 1);
        char SE = grid(y + 1, x + 1);

        // only one could possibly be true
        sum += (NW == 'M' && NE == 'M' && SW == 'S' && SE == 'S') ? 1 : 0; // top and bottom
        sum += (SW == 'M' && SE == 'M' && NW == 'S' && NE == 'S') ? 1 : 0;
        sum += (NW == 'M' && SW == 'M' && NE == 'S' && SE == 'S') ? 1 : 0; // left and right
        sum += (NE == 'M' && SE == 'M' && NW == 'S' && SW == 'S') ? 1 : 0;

        return sum;
    }

    public static void main(String[] aaa) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day4")));
        String line;

        int i = 0;
        while ((line = in.readLine()) != null) {
            grid[i] = line.toCharArray();
            i++;
        }

        int sum = 0;
        for (int y = 0; y < 140; y++) {
            for (int x = 0; x < 140; x++) {
                if (grid(y, x) == 'A') {
                    sum += checkDiagonals(y, x);
                }
            }
        }
        // System.out.println(Arrays.toString(Arrays.stream(grid).map(l ->
        // Arrays.toString(l)).toArray()));
        System.out.println(sum);
    }
}