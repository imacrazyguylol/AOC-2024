import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class day13_1 {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day13")));
        String line1, line2, line3;

        double sum = 0;
        while ((line1 = in.readLine()) != null) {
            line2 = in.readLine();
            line3 = in.readLine();
            String[] s;

            int xA, yA;
            s = line1.split(", ");
            xA = Integer.parseInt(s[0].substring(12));
            yA = Integer.parseInt(s[1].substring(2));

            int xB, yB;
            s = line2.split(", ");
            xB = Integer.parseInt(s[0].substring(12));
            yB = Integer.parseInt(s[1].substring(2));

            int PrizeX, PrizeY;
            s = line3.split(", ");
            PrizeX = Integer.parseInt(s[0].substring(9));
            PrizeY = Integer.parseInt(s[1].substring(2));

            ClawMachine claw = new ClawMachine(PrizeX, PrizeY, xA, xB, yA, yB);
            double[] presses = claw.calculatePresses();
            sum += (presses[0] * 3) + presses[1];

            System.out.printf("%s,%s,%s,%s,%s,%s,  %s %s\n", PrizeX, PrizeY, xA, xB, yA, yB, presses[0], presses[1]);    
            in.readLine();
        }

        System.out.println(sum);
    }

    static class ClawMachine {
        private int PrizeX, PrizeY, xA, xB, yA, yB;

        public ClawMachine(int PrizeX, int PrizeY, int xA, int xB, int yA, int yB) {
            this.PrizeX = PrizeX;
            this.PrizeY = PrizeY;
            this.xA = xA;
            this.xB = xB;
            this.yA = yA;
            this.yB = yB;
        }

        public double[] calculatePresses() {
            double a, b;
            double denominator = ((xA * yB) - (xB * yA));
            a = ((PrizeX * yB) - (PrizeY * xB)) / denominator;
            b = ((PrizeY * xA) - (PrizeX * yA)) / denominator;

            // System.out.println(a + " " + b);
            if (a >= 0 && a <= 100 && b >= 0 && b <= 100 && a % 1 == 0 && b % 1 == 0)
                return new double[] { a, b };
            else
                return new double[] { 0, 0 };
        }
    }
}
