import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

class day12_1 {
    public static final int size = 140;
    public static Plant[][] grid = new Plant[size][size];
    public static Set<Region> regions = new HashSet<>();

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day12")));
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
        int fences;

        int I;
        int J;
        char type;

        public Plant(int i, int j, char type) {
            this.I = i;
            this.J = j;
            this.type = type;
        }

        // every plant adds itself to the parent's list
        // synchronized so that i dont have 2 plants check each other, I only want one
        // at a time
        public synchronized void searchAdjacent(Region parent) {
            // look up
            if (I >= 1 && this.type == grid[I - 1][J].type) {
                if (!parent.plants.contains(grid[I - 1][J])) {
                    parent.plants.add(grid[I - 1][J]);
                    grid[I - 1][J].searchAdjacent(parent);
                }
            } else {
                fences++;
            }

            // look right
            if (J < size - 1 && this.type == grid[I][J + 1].type) {
                if (!parent.plants.contains(grid[I][J + 1])) {
                    parent.plants.add(grid[I][J + 1]);
                    grid[I][J + 1].searchAdjacent(parent);
                }
            } else {
                fences++;
            }

            // look down
            if (I < size - 1 && this.type == grid[I + 1][J].type) {
                if (!parent.plants.contains(grid[I + 1][J])) {
                    parent.plants.add(grid[I + 1][J]);
                    grid[I + 1][J].searchAdjacent(parent);
                }
            } else {
                fences++;
            }

            // look left
            if (J >= 1 && this.type == grid[I][J - 1].type) {
                if (!parent.plants.contains(grid[I][J - 1])) {
                    parent.plants.add(grid[I][J - 1]);
                    grid[I][J - 1].searchAdjacent(parent);
                }
            } else {
                fences++;
            }
        }

        public boolean equals(Plant other) {
            return this.I == other.I && this.J == other.J;
        }

        public String toString() {
            return this.type + " " + fences;
        }
    }

    static class Region {
        Vector<Plant> plants = new Vector<>();
        char type;

        private int area = 0;
        private int perimeter = 0;
        int price;

        public Region(Plant start) {
            this.type = start.type;
            plants.add(start);
        }

        public void generate() {
            plants.get(0).searchAdjacent(this);

            this.area = plants.size();
            for (Plant p : plants) {
                perimeter += p.fences;
            }

            this.price = area * perimeter;
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
