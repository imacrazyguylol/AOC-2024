import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class day15_2 {
    static char[][] grid = new char[50][50];
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day15")));
        String line;

        
        char[] moves;
        int rI = 0;
        int rJ = 0;

        
        for (int i = 0; i < 50; i++) {
            line = in.readLine();
            grid[i] = line.toCharArray();
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == '@') {
                    rI = i;
                    rJ = j;
                }
            }
        }

        in.readLine();

        while ((line = in.readLine()) != null) {
            moves = line.toCharArray();

            for (char dir : moves) {
                if (attemptMoveObj(dir, rI, rJ)) {
                    switch (dir) {
                        case '^':
                            rI--;
                            break;
                        case '>':
                            rJ++;
                            break;
                        case 'v':
                            rI++;
                            break;
                        case '<':
                            rJ--;
                            break;
                    }
                };
                // printMatrix(grid);
            }
        };
        
        int sum = 0;
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'O') {
                    sum += (100 * i) + j;
                }
            }
        }

        System.out.println("\n\n\n" + sum);
        in.close();
    }
    
    // returns whether object can move
    public static boolean attemptMoveObj(char dir, int iPos, int jPos) {
        int objI = iPos;
        int objJ = jPos;
        
        switch (dir) {
            case '^':
                objI--;
                break;
            case '>':
                objJ++;
                break;
            case 'v':
                objI++;
                break;
            case '<':
                objJ--;
                break;
        }

        if (grid[objI][objJ] == '#') {
            return false;
        } else if (grid[objI][objJ] == '.') {
            grid[objI][objJ] = grid[iPos][jPos];
            grid[iPos][jPos] = '.';
            return true;
        } else {
            if (attemptMoveObj(dir, objI, objJ)) {
                grid[objI][objJ] = grid[iPos][jPos];
                grid[iPos][jPos] = '.';
                return true;
            } else {
                return false;
            }
        }
    }

    public static void printMatrix(char[][] matrix) {
        String out = "";
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                out += matrix[i][j];
            }
            out += "\n";
        }

        System.out.println(out);
    }
}
