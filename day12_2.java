import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.Arrays;

class day12_2 extends Extra {
    public static final int size = 4;
    public static Plant[][] grid = new Plant[size][size];
    public static Set<Region> regions = new HashSet<>();

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day12test")));
        String line;

        int i = 0;
        while ((line = in.readLine()) != null) {
            char[] carr = line.toCharArray();
            for (int j = 0; j < carr.length; j++) {
                grid[i][j] = new Plant(i, j, carr[j]);
            }
            i++;
        }

        for (i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boolean uniquePlant = true;

                for (Region r : regions) {
                    if (r.plants.contains(grid[i][j])) {
                        uniquePlant = false;
                        break;
                    }
                }

                if (uniquePlant) {
                    Region r = new Region(grid[i][j]);

                    r.generate();
                    regions.add(r);
                }
            }
        }

        int sum = 0;
        for (Region r : regions) {
            sum += r.price;
        }

        System.out.println(regions.toString());
        System.out.println(sum);
    }

    static class Plant {
        int sides = 0;

        int I;
        int J;
        char type;

        public Plant(int i, int j, char type) {
            this.I = i;
            this.J = j;
            this.type = type;
        }

        // every plant adds itself to the parent's list
        // sideDirs: U = 0, R = 1, D = 2, L = 3
        public void searchAdjacent(Region parent, boolean[] sideDirs) {
            boolean searchU = false;
            boolean searchR = false;
            boolean searchD = false;
            boolean searchL = false;
            // look up
            if (I >= 1 && this.type == grid[I - 1][J].type) { // there is no side
                if (sideDirs[0]) {
                    sideDirs[0] = false;
                }

                if (!parent.plants.contains(grid[I - 1][J])) {
                    parent.plants.add(grid[I - 1][J]);
                    searchU = true;
                }
            } else if (!sideDirs[0]) { // there is a side
                sides++;
                sideDirs[0] = true;
            }

            // look right
            if (J < size - 1 && this.type == grid[I][J + 1].type) {
                if (sideDirs[1]) {
                    sideDirs[1] = false;
                }

                if (!parent.plants.contains(grid[I][J + 1])) {
                    parent.plants.add(grid[I][J + 1]);
                    searchR = true;
                }
            } else if (!sideDirs[1]) { // there is a side
                sides++;
                sideDirs[1] = true;
            }

            // look down
            if (I < size - 1 && this.type == grid[I + 1][J].type) {
                if (sideDirs[2]) {
                    sideDirs[2] = false;
                }

                if (!parent.plants.contains(grid[I + 1][J])) {
                    parent.plants.add(grid[I + 1][J]);
                    searchD = true;
                }
            } else if (!sideDirs[2]) { // there is a side
                sides++;
                sideDirs[2] = true;
            }

            // look left
            if (J >= 1 && this.type == grid[I][J - 1].type) {
                if (sideDirs[3]) {
                    sideDirs[3] = false;
                }

                if (!parent.plants.contains(grid[I][J - 1])) {
                    parent.plants.add(grid[I][J - 1]);
                    searchL = true;
                }
            } else if (!sideDirs[3]) { // there is a side
                sides++;
                sideDirs[3] = true;
            }

            // start recursions last so that sideDirs is updated for all of them
            if (searchR)
                grid[I][J + 1].searchAdjacent(parent, sideDirs); // right
            if (searchD)
                grid[I + 1][J].searchAdjacent(parent, sideDirs); // down
            if (searchU)
                grid[I - 1][J].searchAdjacent(parent, sideDirs); // recurse up
            if (searchL)
                grid[I][J - 1].searchAdjacent(parent, sideDirs); // left
        }

        public Plant setSides(int val) {
            this.sides = val;
            return this;
        }

        public boolean equals(Plant other) {
            return this.I == other.I && this.J == other.J;
        }

        public String toString() {
            return this.type + " " + sides;
        }
    }

    static class Region {
        Vector<Plant> plants = new Vector<>();
        private int area = 0;
        private int sides = 0;
        int price;

        public Region(Plant start) {
            plants.add(start);
        }

        public void generate() {
            plants.get(0).searchAdjacent(this, new boolean[] { false, false, false, false });

            this.area = plants.size();
            for (Plant p : plants) {
                this.sides += p.sides;
            }

            this.price = area * sides;
        }

        public boolean equals(Region other) {
            for (Plant p : other.plants) {
                for (Plant t : this.plants) {
                    if (t.equals(p)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public String toString() {
            return plants.toString();
        }
    }
}
