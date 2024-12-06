import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class day6_2 {
    public static final int size = 130;
    public static void main(String[] aaa) throws Exception{
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day6")));
        String line;
        Guard guard = new Guard(0, 0, null, false);

        int i = 0;
        char[][] map = new char[day6_2.size][day6_2.size];
        while ((line = in.readLine()) != null) {
            char[] row = line.toCharArray();

            for (int j = 0; j < day6_2.size; j++) {
                if (row[j] == '^') {
                    guard = new Guard(j, i, Dir.U, false);
                    row[j] = '.'; // having this position not be a '^' makes it easier, I'll just keep track of the position
                }
            }

            map[i] = row;
            i++;
        }

        while (!guard.complete) {
            map = guard.travel(map);
        }

        System.out.println("\n");

        System.out.println("FINAL VALUE: " + guard.numLoops);
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
    protected int numLoops = 0;
    protected Set<int[]> touchedTurns = new HashSet<>(); // X, Y, 0 = U | 1 = R | 2 = D | 3 = L


    public Guard(int startingX, int startingY, Dir startingDir, boolean isLooper) {
        this.facing = startingDir;
        X = startingX;
        Y = startingY;

        this.startingX = startingX;
        this.startingY = startingY;
        this.isLooper = isLooper;

/*         touchedXTurns.add(startingX);
        touchedYTurns.add(startingY); */
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
            if (!isLooper) {
                char[][] temp = map;
                temp[Y + increment[1]][X + increment[0]] = '#';

                Guard looper = new Guard(X, Y, facing, true);
                System.out.println("\n\nStarting coords of looper: " + looper.startingX + ", " + looper.startingY);
                // System.out.println("looper facing " + looper.facing);
                // System.out.println("numLoops: " + numLoops);

                while (!looper.complete) {
                    System.out.println("hit obstacle\n\nlooper facing: " + looper.facing);
                    System.out.println("coord: " + looper.X + ", " + looper.Y);

                    temp = looper.travel(temp);
                    if (looper.loops) {
                        this.numLoops++;
                        break;
                    }
                    // System.out.println("looper complete: " + looper.complete);
                }
            }

            map[Y][X] = 'X';
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
        /* if (isLooper) {
            startingX = X;
            startingY = Y;
        } */

        return map;
    }

    public boolean checkIfTouched(int X, int Y, Dir facing) {
        for (int[] arr : touchedTurns) {
            return (arr[0] == X && arr[1] == Y && arr[2] == facing.number(facing));
        }
        return false;
    }

    
}
