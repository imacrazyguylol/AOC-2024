import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Stream;

class day9_2 {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("inputs/day9")));
        String line = in.readLine();

        int[] diskMap = line.chars().map(e -> e - '0').toArray();
        Vector<FileBlock> fileSystem = new Vector<>(diskMap.length); // now that we're keeping each block together every
                                                                     // char
        // in the diskmap is 1 block
        boolean isFile = true;

        System.out.println(Arrays.toString(diskMap));

        // iterate through diskmap, swapping between file and empty space
        // j increments by the size of the file to get to the next free space on the
        // filesystem
        for (int i = 0; i < diskMap.length; i++) {
            int size = diskMap[i];
            if (isFile) {
                fileSystem.add(i, new FileBlock(size, (i + 1) / 2));
            } else {
                fileSystem.add(i, new FileBlock(size, -2));
            }

            // System.out.println(size);
            isFile = !isFile;
        }

        // Iterate backwards through vector, attempting exactly once to move each block
        // to an empty space as close to the front as possible
        for (int i = fileSystem.size() - 1; i > 0; i--) {
            // initialize file block
            FileBlock block = fileSystem.get(i);

            // System.out.println(i + " " + block.toString() + "\n" + FileBlock.stringify(fileSystem, sumOfArray(diskMap)) + "\n");

            // not looking at empty space, and we will always ignore empty files (they have
            // to be there though because idk)
            if (block.val() < 0)
                continue;

            // we don't have to worry about resizing vector because i will keep up with it
            // start from left, find empty space and test if it's big enough to fit our
            // block
            // if so, swap the necessary space with the block
            // otherwise, move on
            for (int j = 0; j < i; j++) {
                // initialize "check (against)" block
                FileBlock checkBlock = fileSystem.get(j);

                // if it's not an empty space we can't swap into it now can we :P
                // same with if it's too small of an empty space
                if (checkBlock.val() == -2) {
                    // no need to split empty space
                    if (checkBlock.size() == block.size()) {
                        FileBlock temp = checkBlock;
                        fileSystem.set(j, block);
                        fileSystem.set(i, temp);
                        break; // we swapped once so we should stop trying to search for a place to swap this
                               // file block into
                    }

                    // we have to split empty space...
                    else if (checkBlock.size() > block.size()) {
                        FileBlock[] temp = checkBlock.split(block.size());

                        fileSystem.set(j, block);
                        fileSystem.set(i, temp[0]); // the used space goes to the back
                        fileSystem.add(j + 1, temp[1]); // put the unused space where it goes

                        // adjust to shifted vector
                        i++;
                        break;
                    }

                    // space is too small
                    else {
                        continue;
                    }
                }
            }
        }

        long sum = 0;

        int j = 0;
        for (int i = 0; i < fileSystem.size(); i++) {
            FileBlock f = fileSystem.get(i);

            for (int k = 0; k < f.block.length; j++, k++) {
                if (f.size() > 0 && f.val() >= 0) {
                    sum += f.val() * j;
                }
            }
        }

        System.out.println(FileBlock.stringify(fileSystem, sumOfArray(diskMap)));
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

class FileBlock {
    public int[] block;
    private int size;

    public FileBlock(int size, int ID) {
        this.size = size;
        block = new int[size];
        Arrays.fill(block, ID);
    }

    public FileBlock(int[] block) {
        this.block = block;
        this.size = block.length;
    }

    public int size() {
        return size;
    }

    // enforce that a fileblock must always have the same value across the array
    // -1 if it's empty
    public int val() {
        if (size() == 0)
            return -1;
        else
            return block[0];
    }

    // index is inclusive, so: input=[4,4,4,4,4] & index=2 will return
    // [[4,4], [4,4,4]]
    // will add empty space if split is unnecessary, so be careful
    public FileBlock[] split(int index) {
        if (index > size())
            throw new IndexOutOfBoundsException();

        int[] temp1 = new int[index];
        int[] temp2 = new int[size() - index];
        for (int i = 0; i < temp1.length; i++) {
            temp1[i] = val();
        }
        for (int i = 0; i < temp2.length; i++) {
            temp2[i] = val();
        }

        return new FileBlock[] { new FileBlock(temp1), new FileBlock(temp2) };
    }

    public int sumSize() { // sum of n=0 -> size - 1
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += i;
        }
        return sum;
    }

    public String toString() {
        return Arrays.toString(block);
    }

    // size is sumofarray(diskmap)
    public static String stringify(Vector<FileBlock> fileSystem, int size) {
        Vector<Character> v = new Vector<>();

        for (FileBlock fb : fileSystem) {
            for (int i : fb.block) {
                v.add((char) (i + '0'));
            }
        }

        Character[] carr = new Character[size];
        carr = v.toArray(carr);

        char[] real = new char[size];
        for (int i = 0; i < real.length; i++) {
            real[i] = carr[i];
        }

        return new String(real); // FUCKING JAVA
                                 // ADUIOBFUKDVFBU(YFGIVKBSDUFIHGAHSDFLIASGFBOIADVFIBUSDHVBSIUDHVBSDUIVBSDvuhb)
    }
}
