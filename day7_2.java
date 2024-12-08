import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class day7_2 extends Extra {
    public static void main(String[] aaa) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader( new File("inputs/day7")));
        String line;
        long sum = 0;

        while ((line = in.readLine()) != null) {
            String[] params = line.split(": ");
            long y = Long.parseLong(params[0]);
            long[] x = Extra.parseLongs(params[1], " ");

            // System.out.println(line);

            sum += testOperators(y, x.length - 1, x) ? y : 0;
            // System.out.println();
        }

        System.out.println(sum);
    }

    public static boolean testOperators(long y, int n, long[] x) {
        if (y <= 0) {
            return false;
        }
        if (n == 0) {
            if (y == x[0]) {
                // System.out.println("Complete");
                // System.out.println( y + " " + x[n] + " " + Arrays.toString(x) + " - Valid: " + (y == x[0]) + "\n" );
                return true;
            } else {
                return false;
            }
        } else {
            int ydigits =  ( String.valueOf(y) ).length();
            int xnDigits = ( String.valueOf(x[n]) ).length();
            // System.out.printf( "y: %s, digits: %s; x[n]: %s, digits: %s\n", 
            // String.valueOf(y), ydigits, x[n], xnDigits );

            // if (ydigits > xnDigits) System.out.println( String.valueOf(y).substring( ydigits - xnDigits ) );

            String deConcat = ydigits > xnDigits && Long.parseLong( String.valueOf(y).substring(ydigits - xnDigits) ) == x[n] ? String.valueOf(y).substring( 0, (ydigits - xnDigits) ) : "";
            // System.out.println( y + " " + x[n] + " " + deConcat );

            double div = (double) y / (double) x[n];
            boolean bruh = true;
            if (div % 1 != 0) { // ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????? why does it only work here im kms
                bruh = false;
            }

            return testOperators( y - x[n], n - 1, x ) || ( bruh ? testOperators( y / x[n], n - 1, x ) : false ) || ( n != 0 && !deConcat.isEmpty() ? testOperators( Long.parseLong( deConcat ), n - 1, x ) : false );
        }
    }
}

