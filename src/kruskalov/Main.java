package kruskalov;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Kruskalov mini = new Kruskalov("ATG_DAT/ShortestPath/kostra.txt", true);
        System.out.println(mini.vypisHranyVKostre());
        System.out.println();

    }
}
