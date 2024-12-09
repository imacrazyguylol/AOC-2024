import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//TODO: pre-compute all of the possible obstacle positions using day1 and simply travel from the actual starting position until a loop is hit for every step in the initial computation
class day6_2 {
    public static final int size = 10;
    public static void main(String[] aaa) throws Exception{
        BufferedReader in =  new BufferedReader( new FileReader( new File("inputs/day6test") ) );
        String line;
        Guard guard = new Guard(0, 0, 0, false);
        Set<Integer> distOfTraveled = new HashSet<>();

        int i = 0;
        char[][] map = new char[day6_2.size][day6_2.size];
        while ((line = in.readLine()) != null) {
            char[] row = line.toCharArray();

            for (int j = 0; j < day6_2.size; j++) {
                if (row[j] == '^') {
                    guard = new Guard(j, i, 1, false);
                    row[j] = '.'; // having this position not be a '^' makes it easier, I'll just keep track of the position
                }
            }

            map[i] = row;
            i++;
        }

        final char[][] realMap = map;

        // initial travel to get all locations for objects
        while (!guard.complete) {
            guard.travel(realMap);
        }

        for (i = 0; i < day6_2.size; i++) {
            for (int j = 0; j < day6_2.size; j++) {
                if (realMap[i][j] == 'X') {
                    distOfTraveled.add( j + (130 * i) );
                }
            }
        }

        // travel again to test each object for looping
        int sum = 0;
        for (int dist : distOfTraveled) {
            int oX = dist % 130;
            int oY = (dist - oX) / 130;

            char[][] temp = realMap.clone();
            temp[oY][oX] = '#';

            for (char[] y : temp) {
                for (char x : y) {
                    System.out.print(x);
                }
                System.out.println();
            }
            System.out.println("\n\n Testing new obstacle at " + oX + ", " + oY);
            while (!guard.complete && !guard.looped) {
                guard.loopTravel(temp);
            }

            if (guard.looped) {
                sum++;
            }
        }

        System.out.println("\nFINAL VALUE: " + sum);        
    }
}

class Guard {
    protected int facing;
    protected int X;
    protected int Y;

    protected boolean looped = false;
    protected boolean complete = false;
    protected Map<Integer, Integer> traveled = new HashMap<>(); // distance from start (X + 130Y); 1=U, 2=R, 4=D, 8=L and all others are unused

    public Guard(int startingX, int startingY, int startingDir, boolean isLooper) {
        this.facing = startingDir;
        X = startingX;
        Y = startingY;
    }

    public char[][] travel(char[][] map) { // returns map with X's in all of the positions that it has traveled
        int[] increment = new int[] {0, 0}; // X, Y
        switch (facing) {
            case 1: 
                increment[1] = -1;
                break;
            case 2:
                increment[0] = 1;
                break;
            case 4:
                increment[1] = 1;
                break;
            case 8:
                increment[0] = -1;
                break;
        }

        while (map[Y + increment[1]][X + increment[0]] != '#') { // check if next char where facing is obstacle
            map[Y][X] = 'X';
            Y += increment[1];
            X += increment[0];
            if (Y + increment[1] == day6_2.size || X + increment[0] == day6_2.size || Y + increment[1] == -1 || X + increment[0] == -1) {
                complete = true;
                return map;
            }
        }
        turn();

        return map;
    } 

    public void loopTravel(char[][] map) {
        int[] increment = new int[] {0, 0}; // X, Y
        switch (facing) {
            case 1:
                increment[1] = -1;
                break;
            case 2:
                increment[0] = 1;
                break;
            case 4:
                increment[1] = 1;
                break;
            case 8:
                increment[0] = -1;
                break;
        }

        while (map[Y + increment[1]][X + increment[0]] != '#') { // check if next char where facing is obstacle
            if ( checkIfTouched(X, Y, facing) ) {
                looped = true;
            }

            int dist = X + (130 * Y);
            if ( traveled.putIfAbsent( dist, facing ) != null) {
                traveled.put(dist, traveled.get(dist) | facing);
            }

            Y += increment[1];
            X += increment[0];
            
            if (Y + increment[1] == day6_2.size || X + increment[0] == day6_2.size || Y + increment[1] == -1 || X + increment[0] == -1) {
                complete = true;
                looped = false;
            }
        }

        turn();
    }

    public void turn() {
        switch (facing) {
            case 1:
                facing = 2;
                break;
            case 2:
                facing = 4;
                break;
            case 4:
                facing = 8;
                break;
            case 8:
                facing = 1;
                break;
            default:
                break;
        }
    }

    public boolean checkIfTouched(int X, int Y, int facing) {
        return traveled.get( X + (130 * Y) ) != null && ( traveled.get( X + (130 * Y) ) & facing ) != 0;
    }
}
