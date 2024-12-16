import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class day14_2 {
    public static final int sizeY = 103;
    public static final int sizeX = 101;
    static int[][] grid = new int[sizeY][sizeX];

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day14")));
        String line;
        List<Robot> robots = new ArrayList<>();
        Arrays.stream(grid).forEach(e -> Arrays.fill(e, 0));

        int x, y, vx, vy;
        while ((line = in.readLine()) != null) {
            String[] s = line.split(" v=");
            String[] s1 = s[0].split(",");
            String[] s2 = s[1].split(",");

            x = Integer.parseInt(s1[0].substring(2));
            y = Integer.parseInt(s1[1]);
            vx = Integer.parseInt(s2[0]);
            vy = Integer.parseInt(s2[1]);

            robots.add(new Robot(x, y, vx, vy));
        }

        for (Robot r : robots) {
            grid[r.y][r.x]++;
        }

        int i = 0;
        while (!isChristmasTree()) {
            // printRobots(robots);
            for (Robot r : robots) {
                r.move();
            }

            resetGrid();
            for (Robot r : robots) {
                grid[r.y][r.x]++;
            }
            i++;
        }

        printRobots(robots);
        System.out.println(i);
        // System.out.println(robots.toString());
    }

    static void printRobots(List<Robot> robots) {
        Arrays.stream(grid).forEach(e -> {
            Arrays.stream(e).forEach(x -> System.out.print((char) (x == 0 ? '.' : (char) x + '0')));
            System.out.println();
        });
        System.out.println();
    }

    // moving 5x5 sum across grid, if result contains any 20+, return true
    static boolean isChristmasTree() {
        for (int i = 2; i < sizeY - 2; i++) {
            for (int j = 2; j < sizeX - 2; j++) {
                int[] square = new int[] {
                        grid[i - 2][j - 2],
                        grid[i - 2][j - 1],
                        grid[i - 2][j],
                        grid[i - 2][j + 1],
                        grid[i - 2][j + 2],

                        grid[i - 1][j - 2],
                        grid[i - 1][j - 1],
                        grid[i - 1][j],
                        grid[i - 1][j + 1],
                        grid[i - 1][j + 2],

                        grid[i][j - 2],
                        grid[i][j - 1],
                        grid[i][j],
                        grid[i][j + 1],
                        grid[i][j + 2],

                        grid[i + 1][j - 2],
                        grid[i + 1][j - 1],
                        grid[i + 1][j],
                        grid[i + 1][j - 1],
                        grid[i + 1][j + 2],

                        grid[i + 2][j - 2],
                        grid[i + 2][j - 1],
                        grid[i + 2][j],
                        grid[i + 2][j - 1],
                        grid[i + 2][j + 2]
                };

                int sum = 0;
                for (int x : square) {
                    sum += x;
                }

                if (sum >= 20) {
                    return true;
                }
            }
        }

        return false;
    }

    static void resetGrid() {
        Arrays.stream(grid).forEach(e -> Arrays.fill(e, 0));
    }

    static class Robot {
        int x, y, vx, vy;

        public Robot(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        public void move() {
            x = (x + vx) % sizeX;
            if (x < 0)
                x += sizeX;

            y = (y + vy) % sizeY;
            if (y < 0)
                y += sizeY;
        }

        public String toString() {
            return String.format("(%d, %d)", x, y);
        }
    }
}
