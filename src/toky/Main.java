package toky;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Maximálny tok:");
        MaximalnyTok maximalnyTok = new MaximalnyTok("subory/toky.txt");
        maximalnyTok.zapisMaxTok("subory/toky2.txt");
        System.out.println();
        NajlacnejsiTok najlacnejsiTok = new NajlacnejsiTok("subory/toky2.txt");
    }
}
