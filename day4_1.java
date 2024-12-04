import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class day4_1 {
    static char[][] grid = new char[140][140];

    static int grid(int y, int x) { // i didn't realize I accidentally made in an int but it worked so im keeping it lmfao
        try {
            return grid[y][x];
        } catch (Exception e) {
            return '.';
        }
    }

    static int checkAllDirs(int y, int x) {
        int sum = 0;

        // down + right
        sum += (grid(y + 1, x + 1) == 'M' && grid(y + 2, x + 2) == 'A' && grid(y + 3, x + 3) == 'S') ? 1 : 0;
        // down
        sum += (grid(y + 1, x) == 'M' && grid(y + 2, x) == 'A' && grid(y + 3, x) == 'S') ? 1 : 0;
        // down + left
        sum += (grid(y + 1, x - 1) == 'M' && grid(y + 2, x - 2) == 'A' && grid(y + 3, x - 3) == 'S') ? 1 : 0;
        // left
        sum += (grid(y, x - 1) == 'M' && grid(y, x - 2) == 'A' && grid(y, x - 3) == 'S') ? 1 : 0;
        // up + left
        sum += (grid(y - 1, x - 1) == 'M' && grid(y - 2, x - 2) == 'A' && grid(y - 3, x - 3) == 'S') ? 1 : 0;
        // up
        sum += (grid(y - 1, x) == 'M' && grid(y - 2, x) == 'A' && grid(y - 3, x) == 'S') ? 1 : 0;
        // up + right
        sum += (grid(y - 1, x + 1) == 'M' && grid(y - 2, x + 2) == 'A' && grid(y - 3, x + 3) == 'S') ? 1 : 0;
        // right
        sum += (grid(y, x + 1) == 'M' && grid(y, x + 2) == 'A' && grid(y, x + 3) == 'S') ? 1 : 0;

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
                if (grid(y, x) == 'X') {
                    sum += checkAllDirs(y, x);
                }
            }
        }
        // System.out.println(Arrays.toString(Arrays.stream(grid).map(l ->
        // Arrays.toString(l)).toArray()));
        System.out.println(sum);
    }
}