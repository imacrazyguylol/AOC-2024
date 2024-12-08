import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class day8_2 {
    public static final int size = 50;

    public static void main(String[] aaa) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day8")));
        String line;

        char[][] grid = new char[size][size];
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

        // iterate over all instances of unique frequencies/charactersw
        int sum = 0;
        for (char c : nodes.keySet()) {
            List<int[]> l = nodes.get(c);

            // iterate each node
            for (i = 0; i < l.size(); i++) {
                int[] n1 = l.get(i);

                int x1 = n1[0];
                int y1 = n1[1];

                // compare to every other node that it hasn't already been compared to
                for (int j = i + 1; j < l.size(); j++) {
                    int[] n2 = l.get(j);

                    int x2 = n2[0];
                    int y2 = n2[1];

                    // attempt to create an antinode in the direction of the first node
                    int m = 0; // =0 to get the initial nodees as well
                    int[] an1 = new int[] { // a node
                            ((m + 1) * x1) - (m * x2),
                            ((m + 1) * y1) - (m * y2)
                    };
                    while (an1[0] < size && an1[0] > -1 && an1[1] < size && an1[1] > -1) {
                        if ((grid[an1[1]][an1[0]] != '#')) {
                            System.out.printf(
                                    "multiplier: %s | node 1: %s, %s | node 2: %s, %s | antinote (A direction): %s\n",
                                    m, x1, y1, x2, y2, Arrays.toString(an1));
                            grid[an1[1]][an1[0]] = '#';
                            sum++;
                        }

                        // increment the multiplier and reevaluate for next node position
                        m++;
                        an1 = new int[] {
                                ((m + 1) * x1) - (m * x2),
                                ((m + 1) * y1) - (m * y2)
                        };
                    }

                    // attempt to create an antinode in the direction of the second node
                    m = 0;
                    int[] an2 = new int[] { // b node
                            ((m + 1) * x2) - (m * x1),
                            ((m + 1) * y2) - (m * y1)
                    };
                    while (an2[0] < size && an2[0] > -1 && (an2[1] < size && an2[1] > -1)) {
                        if ((grid[an2[1]][an2[0]] != '#')) {
                            System.out.printf(
                                    "multiplier: %d | node 1: %d, %d | node 2: %d, %d | antinote (B direction): %s\n",
                                    m, x1, y1, x2, y2, Arrays.toString(an2));
                            grid[an2[1]][an2[0]] = '#';
                            sum++;
                        }

                        m++;
                        an2 = new int[] { // b node
                                ((m + 1) * x2) - (m * x1),
                                ((m + 1) * y2) - (m * y1)
                        };
                    }
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
