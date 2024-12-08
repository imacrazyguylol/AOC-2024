import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class day8_1 {
    public static final int size = 50;

    public static void main(String[] aaa) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day8")));
        String line;

        char[][] grid = new char[day8_1.size][day8_1.size];
        Map<Character, List<int[]>> nodes = new HashMap<>(); // integer is coordinates as x + (size * y) format

        int i = 0;
        while ((line = in.readLine()) != null) {
            grid[i] = line.toCharArray();

            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '.')
                    continue;

                if (!nodes.containsKey(grid[i][j])) {
                    nodes.put(grid[i][j], new ArrayList<>());
                }

                nodes.get(grid[i][j]).add(new int[] { j, i });
            }

            i++;
        }

        int sum = 0;
        for (char c : nodes.keySet()) {
            List<int[]> l = nodes.get(c);
            System.out.println(c + "|" + l.toString());

            for (i = 0; i < l.size(); i++) {
                int[] d1 = l.get(i);

                int x1 = d1[0];
                int y1 = d1[1];

                for (int j = i + 1; j < l.size(); j++) {
                    int[] d2 = l.get(j);

                    int x2 = d2[0];
                    int y2 = d2[1];

                    int[] an1 = new int[] { x1 - (x2 - x1), y1 - (y2 - y1) };

                    try {
                        if ((grid[an1[1]][an1[0]] != '#')) {
                            // System.out.println(Arrays.toString(an1));
                            grid[an1[1]][an1[0]] = '#';
                            sum++;
                        }
                    } catch (Exception e) {
                    }

                    int[] an2 = new int[] { x2 - (x1 - x2), y2 - (y1 - y2) };

                    try {
                        if ((grid[an2[1]][an2[0]] != '#')) {
                            // System.out.println(Arrays.toString(an2));
                            grid[an2[1]][an2[0]] = '#';
                            sum++;
                        }
                    } catch (Exception e) {
                    } // lol
                }
            }
        }

        for (char[] y : grid) {
            for (char x : y) {
                System.out.print(x);
            }
            System.out.println();
        }

        System.out.println(sum);
    }
}
