import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class day11_1 extends Extra {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day11")));
        Long[] initial = parseLongs(in.readLine(), " ");

        List<Long> stoneLine = Arrays.asList(initial);
        System.out.println(stoneLine.toString());

        for (int n = 1; n <= Integer.parseInt(args[1]); n++) {
            List<Long> temp = new ArrayList<>();
            for (int i = 0; i < stoneLine.size(); i++) {
                long stone = stoneLine.get(i);
                String s = Long.toString(stone);

                if (stone == 0) {
                    temp.add((long) 1);
                }
                else if (s.length() % 2 == 0) {
                    temp.add(Long.parseLong( s.substring(0, s.length() / 2) ));
                    temp.add(Long.parseLong( s.substring(s.length() / 2) ));
                } else {
                    temp.add(stone * 2024);
                }
            }
            
            stoneLine = temp;
            System.out.println(stoneLine.toString());
        }
        
        System.out.println(stoneLine.size());
    }
}
