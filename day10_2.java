import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class day10_2 {
    static final int size = 45;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day10")));
        String line;
        Node[][] grid = new Node[size][size];
        List<Node> trailheads = new ArrayList<>();

        int r = 0;
        while ((line = in.readLine()) != null) {
            Node[] temp = new Node[line.length()];

            for (int i = 0; i < line.length(); i++) {
                temp[i] = new Node(new int[] { r, i }, line.charAt(i) - '0');
            }

            grid[r] = temp;
            r++;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Node n = grid[i][j];

                // check up direction
                if (i != size - 1)
                    n.travelTo(grid[i + 1][j]);

                // check right direction
                if (j != size - 1)
                    n.travelTo(grid[i][j + 1]);

                // check down direction
                if (i != 0)
                    n.travelTo(grid[i - 1][j]);

                // check left direction
                if (j != 0)
                    n.travelTo(grid[i][j - 1]);

                // add trailhead
                if (n.value == 0)
                    trailheads.add(n);

            }
        }

        int sum = 0;
        for (Node t : trailheads) {
            int x = t.peaks();
            System.out.printf("%s -> %s | %d\n", t, t.travelable.toString(), x);
            sum += x;
            System.out.println();
        }
        System.out.println(sum);
    }
}

class Node {
    int[] coords;
    List<Node> travelable = new ArrayList<>();
    int value;

    public Node(int[] coords, int value) {
        this.coords = coords;
        this.value = value;
    }

    public void travelTo(Node other) {
        if (this.value + 1 == other.value)
            this.travelable.add(other);
    }

    public int peaks() {
        int sum = 0;

        if (this.value == 9) {
            sum++;
            return sum;
        } else if (this.travelable.size() == 0) {
            return sum;
        } else {
            for (Node next : travelable) {
                sum += next.peaks();
            }
            return sum;
        }
    }

    public boolean equals(Node other) {
        return this.value == other.value;
    }

    public String toString() {
        return Arrays.toString(coords);
    }
}