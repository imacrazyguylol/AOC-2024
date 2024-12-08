import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class day6_1 {
    public static final int size = 130;
    public static void main(String[] aaa) throws Exception{
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day6")));
        String line;
        Guard guard = new Guard(0, 0, null);

        int i = 0;
        char[][] map = new char[day6_1.size][day6_1.size];
        while ((line = in.readLine()) != null) {
            char[] row = line.toCharArray();

            for (int j = 0; j < day6_1.size; j++) {
                if (row[j] == '^') {
                    guard = new Guard(j, i, Dir.U);
                    row[j] = '.'; // having this position not be a '^' makes it easier, I'll just keep track of the position
                }
            }

            map[i] = row;
            i++;
        }

        System.out.println(guard.X + ", " + guard.Y);
        while (!guard.complete) {
            map = guard.travel(map);
            // System.out.println(guard.X + ", " + guard.Y);
        }

        int sum = 1;
        for (i = 0; i < day6_1.size; i++) {
            for (int j = 0; j < day6_1.size; j++) {
                if (map[i][j] == 'X') {
                    sum++;
                }
            }
        }

        for (char[] y : map) {
            for (char x : y) {
                System.out.print(x);
            }
            System.out.println();
        }
        System.out.println(sum);
    }
}

enum Dir {
    U,
    D,
    L,
    R;

    public Dir turn(Dir direction) {
        switch (direction) {
            case U:
                direction = R;
                break;
            case R:
                direction = D;
                break;
            case D:
                direction = L;
                break;
            case L:
                direction = U;
                break;
            default:
                break;
        }
        return direction;
    }
}

class Guard {
    protected Dir facing;
    protected int X;
    protected int Y;
    protected boolean complete = false;

    public Guard(int startingX, int startingY, Dir startingDir) {
        this.facing = startingDir;
        this.X = startingX;
        this.Y = startingY;
    }

    public char[][] travel(char[][] map) { // returns map with X's in all of the positions that it has traveled
        int[] increment = new int[] {0, 0}; // X, Y
        switch (facing) {
            case U: 
                increment[1] = -1;
                break;
            case R:
                increment[0] = 1;
                break;
            case D:
                increment[1] = 1;
                break;
            case L:
                increment[0] = -1;
                break;
        }

        while (map[Y + increment[1]][X + increment[0]] != '#') { // check if next char where facing is obstacle
            map[Y][X] = 'X';
            Y += increment[1];
            X += increment[0];
            if (Y + increment[1] == day6_1.size || X + increment[0] == day6_1.size || Y + increment[1] == -1 || X + increment[0] == -1) {
                complete = true;
                return map;
            }
        }
        facing = facing.turn(facing);

        return map;
    } 
}
