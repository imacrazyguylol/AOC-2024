import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;

class day12_2 extends Extra {
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
                    if (r.visitedPlants.contains(grid[i][j])) {
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
        int corners = 0;
        Vector<Plant> neighbors = new Vector<>();
        Plant[] adjacent;

        int I;
        int J;
        char type;

        public Plant(int i, int j, char type) {
            this.I = i;
            this.J = j;
            this.type = type;
        }

        public void generateAdjacent() {
            // U = 0, R = 1, D = 2, L = 3
            // null is an edge
            this.adjacent = new Plant[] {
                    I >= 1 && grid[I - 1][J].type == this.type ? grid[I - 1][J] : null,
                    J < size - 1 && grid[I][J + 1].type == this.type ? grid[I][J + 1] : null,
                    I < size - 1 && grid[I + 1][J].type == this.type ? grid[I + 1][J] : null,
                    J >= 1 && grid[I][J - 1].type == this.type ? grid[I][J - 1] : null
            };

            for (Plant p : adjacent) {
                if (p != null) {
                    neighbors.add(p);
                }
            }
        }

        // checks null adjacent tiles (null means either edge of map or edge of region, aka different type) for corners
        public void searchAdjacent() {
            if (adjacent[1] != null)
                adjacent[1].generateAdjacent();
            if (adjacent[3] != null)
                adjacent[3].generateAdjacent();

            // Check up direction first
            if (adjacent[0] == null) { // is an edge
                if (adjacent[1] == null) // up right outer corner
                    corners++;
                if (adjacent[3] == null) // up left outer corner
                    corners++;
            } else { // not an edge; check internal corners
                adjacent[0].generateAdjacent();
                if (adjacent[1] != null &&
                        (adjacent[1].adjacent[0] == null && adjacent[0].adjacent[1] == null)) // up right internal
                                                                                              // corner
                    corners++;
                if (adjacent[3] != null &&
                        (adjacent[3].adjacent[0] == null && adjacent[0].adjacent[3] == null)) // up left internal corner
                    corners++;
            }

            // check down now
            if (adjacent[2] == null) { // is an edge
                if (adjacent[1] == null) // down right outer corner
                    corners++;
                if (adjacent[3] == null) // down left outer corner
                    corners++;
            } else { // not an edge; check internal corners
                adjacent[2].generateAdjacent();
                if (adjacent[1] != null &&
                        (adjacent[1].adjacent[2] == null && adjacent[2].adjacent[1] == null)) // down right internal
                                                                                              // corner
                    corners++;
                if (adjacent[3] != null && adjacent[3].type == this.type &&
                        (adjacent[3].adjacent[2] == null && adjacent[2].adjacent[3] == null)) // down left internal
                                                                                              // corner
                    corners++;
            }
        }

        public boolean equals(Plant other) {
            return this.I == other.I && this.J == other.J;
        }

        public String toString() {
            // return String.format("(%s @ [%d, %d] | sides: %d)", type, I, J, sides);
            return type + " " + corners;
        }
    }

    static class Region {
        Vector<Plant> visitedPlants = new Vector<>();
        private Plant startingPlant;
        private int area = 0;
        private int corners = 0;
        int price;

        public Region(Plant start) {
            startingPlant = start;
            startingPlant.generateAdjacent();
        }

        // BFS search
        public void generate() {
            Queue<Plant> q = new ArrayDeque<>();
            q.add(startingPlant);

            Plant current;
            while (!q.isEmpty()) {
                current = q.remove();
                // updates the sides for this current plant and sets all of its neighbors to
                // have the same initial sides
                current.searchAdjacent();

                // add all the valid neighbors to the queue
                q.addAll(current.neighbors);

                // remove all potential duplicated (so long as my .equals works)
                visitedPlants.add(current);
                q.removeAll(visitedPlants);
            }

            this.area = visitedPlants.size();
            for (Plant p : visitedPlants) {
                this.corners += p.corners;
            }

            this.price = area * corners;
        }

        public boolean equals(Region other) {
            for (Plant p : other.visitedPlants) {
                for (Plant t : this.visitedPlants) {
                    if (t.equals(p)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public String toString() {
            return visitedPlants.toString();
        }
    }
}
