import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class day14_1 {
    public static final int sizeY = 103;
    public static final int sizeX = 101;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day14")));
        String line;
        List<Robot> robots = new ArrayList<>();

        int x, y, vx, vy;
        while ((line = in.readLine()) != null) {
            String[] s = line.split(" v=");
            String[] s1 = s[0].split(",");
            String[] s2 = s[1].split(",");

            x = Integer.parseInt(s1[0].substring(2));
            y = Integer.parseInt(s1[1]);
            vx = Integer.parseInt(s2[0]);
            vy = Integer.parseInt(s2[1]);

            x = (x + (100 * vx)) % sizeX;
            if (x < 0)
                x += sizeX;

            y = (y + (100 * vy)) % sizeY;
            if (y < 0)
                y += sizeY;

            robots.add(new Robot(x, y));
        }

        int[] quadCounts = new int[] { 0, 0, 0, 0 };
        for (Robot r : robots) {
            if (r.quadrant >= 0)
                quadCounts[r.quadrant]++;
        }

        System.out.println(robots.toString());
        System.out.println(quadCounts[0] * quadCounts[1] * quadCounts[2] * quadCounts[3]);
    }

    static class Robot {
        int x, y, quadrant;

        public Robot(int x, int y) {
            this.x = x;
            this.y = y;

            if (x > sizeX / 2) {
                if (y > sizeY / 2) {
                    quadrant = 3;
                } else if (y < sizeY / 2) {
                    quadrant = 1;
                } else {
                    quadrant = -1;
                }
            } else if (x < sizeX / 2) {
                if (y > sizeY / 2) {
                    quadrant = 2;
                } else if (y < sizeY / 2) {
                    quadrant = 0;
                } else {
                    quadrant = -1;
                }
            } else {
                quadrant = -1;
            }
        }

        public String toString() {
            return String.format("(%d, %d : q%d)", x, y, quadrant);
        }
    }
}
