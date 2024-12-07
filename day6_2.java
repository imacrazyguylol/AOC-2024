import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

class day6_2 {
    public static final int size = 10;
    public static void main(String[] aaa) throws Exception{
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day6test")));
        String line;
        Guard guard = new Guard(0, 0, null, false);

        int i = 0;
        char[][] temp = new char[day6_2.size][day6_2.size];
        while ((line = in.readLine()) != null) {
            char[] row = line.toCharArray();

            for (int j = 0; j < day6_2.size; j++) {
                if (row[j] == '^') {
                    guard = new Guard(j, i, Dir.U, false);
                    row[j] = '.'; // having this position not be a '^' makes it easier, I'll just keep track of the position
                }
            }

            temp[i] = row;
            i++;
        }

        final char[][] map = temp.clone();

        while (!guard.complete) {
            guard.travel(map);
        }

        System.out.println("\n");
        int sum = 0;
        for (int[] row : guard.obstacles) {
            for (int x : row) {
                System.out.print(x);
                sum += x;
            }
            System.out.println();
        }
        System.out.println("\nFINAL VALUE: " + sum);
        
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

    public int number(Dir direction) {
        switch (direction) {
            case U:
                return 0;
            case R:
                return 1;
            case D:
                return 2;
            case L:
                return 3;
            default:
                return -1;
        }
    }
}

class Guard {
    protected Dir facing;
    protected int X;
    protected int Y;
    protected int startingX;
    protected int startingY;
    protected boolean complete = false;

    protected boolean isLooper = false;
    protected boolean loops = false;
    protected Set<int[]> touchedTurns = new HashSet<>(); // X, Y, 0 = U | 1 = R | 2 = D | 3 = L
    protected int[][] obstacles = new int[day6_2.size][day6_2.size];


    public Guard(int startingX, int startingY, Dir startingDir, boolean isLooper) {
        this.facing = startingDir;
        X = startingX;
        Y = startingY;

        this.startingX = startingX;
        this.startingY = startingY;
        this.isLooper = isLooper;
    }

    public char[][] travel(char[][] map) {
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
            if (!isLooper) {
                map[Y + increment[1]][X + increment[0]] = '#';

                Guard looper = new Guard(X, Y, facing, true);
                // System.out.println("\n\nStarting coords of looper: " + looper.startingX + ", " + looper.startingY);
                // System.out.println("looper facing " + looper.facing);
                // System.out.println("numLoops: " + numLoops);

                while (!looper.complete) {
                    // System.out.println("hit obstacle\n\nlooper facing: " + looper.facing);
                    // System.out.println("coord: " + looper.X + ", " + looper.Y);

                    map = looper.travel(map);
                    if (looper.loops) {
                        if (X + increment[0] != startingX || Y + increment[1] != startingY) {
                            System.out.printf("\nLooped with obstacle placed at: %s, %s starting from guard position %s, %s facing '%s'%n", X + increment[0], Y + increment[1], X, Y, facing);
                            obstacles[Y + increment[1]][X + increment[0]] = 1;
                        }

                        System.out.println("Looper ended at position: " + looper.X + ", " + looper.Y + "");
                        break;
                    }
                    // System.out.println("looper complete: " + looper.complete);
                }
            }

            if (isLooper) map[Y][X] = facing.name().toCharArray()[0];
            Y += increment[1];
            X += increment[0];
            
            if (checkIfTouched(X, Y, facing)) { // should never happen on the regular guard patrol
                loops = true;
                return map;
            }
            if (Y + increment[1] == day6_2.size || X + increment[0] == day6_2.size || Y + increment[1] == -1 || X + increment[0] == -1) {
                complete = true;
                return map;
            }
        }

        touchedTurns.add(new int[] {X, Y, facing.number(facing)});
        facing = facing.turn(facing);
        
        return map;
    }

    public boolean checkIfTouched(int X, int Y, Dir facing) {
        for (int[] arr : touchedTurns) {
            return (arr[0] == X && arr[1] == Y && arr[2] == facing.number(facing));
        }
        return false;
    }
}
