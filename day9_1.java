import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

class day9_1 {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day9")));
        String line = in.readLine();

        int[] diskMap = line.chars().map(e -> e - '0').toArray();
        int[] fileSystem = new int[sumOfArray(diskMap)]; // size = the sum of the numbers on the disk map added
        System.out.println(sumOfArray(diskMap));
        boolean isFile = true;
        int fileSum = 0;

        // System.out.println(Arrays.toString(diskMap));

        // iterate through diskmap, swapping between file and empty space
        // j increments by the size of the file to get to the next free space on the
        // filesystem
        int j = 0;
        for (int i = 0; i < diskMap.length; i++) {

            int size = diskMap[i];
            if (isFile) {
                for (int f = j; f < j + size; f++) {
                    fileSystem[f] = (i + 1) / 2;
                }

                fileSum += diskMap[i];
            } else {
                for (int f = j; f < j + size; f++) {
                    // just in case i need to change them back to chars, this will be a '.'
                    fileSystem[f] = -2;
                }
            }

            // System.out.println(size);
            j += size;
            isFile = !isFile;
        }

        // System.out.println(Arrays.toString(fileSystem));
        // System.out.println(fileSum);
        // System.out.println();

        long sum = 0;
        j = 0;
        // final iteration of filesystem moving everything from back to empty (negative)
        // space in front
        for (int i = 0; i < fileSystem.length; i++) {
            if (i == fileSum)
                break;
            if (fileSystem[i] < 0) {
                j = fileSystem.length - 1;
                while (fileSystem[j] < 0) {
                    j--;
                }
                fileSystem[i] = fileSystem[j];
                fileSystem[j] = -2;
            }

            // System.out.println(i + "" + Arrays.toString(fileSystem));
            sum += fileSystem[i] * i;
        }

        // System.out.println(Arrays.toString(fileSystem));
        System.out.println(sum);
    }

    public static int sumOfArray(int[] arr) {
        int out = 0;
        for (int n : arr) {
            out += n;
        }
        return out;
    }
}
