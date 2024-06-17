package labelset;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        new LabelSet("ATG_DAT/ShortestPath/digraf.txt", 1, 5);

        System.out.println();

        new LabelSet("ATG_DAT/ShortestPath/digraf.txt", 5, 1);

    }
}
