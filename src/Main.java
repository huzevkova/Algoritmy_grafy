import cpm.Cpm;
import kruskalov.Kruskalov;
import labelset.LabelSet;
import toky.MaximalnyTok;
import toky.NajlacnejsiTok;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        while (true) {
            System.out.println("\nVýber algoritmu:\n" +
                    "1. Label set\n" +
                    "2. Kruskalov algortimus\n" +
                    "3. Toky\n" +
                    "4. CPM\n" +
                    "5. koniec");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();

            if (input == 1) {
                new LabelSet("ATG_DAT/ShortestPath/digraf.txt", 1, 5);
                System.out.println();
                new LabelSet("ATG_DAT/ShortestPath/digraf.txt", 5, 1);
            } else if (input == 2) {
                Kruskalov mini = new Kruskalov("ATG_DAT/ShortestPath/kostra.txt", true);
                System.out.println(mini.vypisHranyVKostre());
                System.out.println();
            } else if (input == 3) {
                System.out.println("Maximálny tok (1) alebo tok s najmenšou cenou (2)?");
                input = sc.nextInt();
                if (input == 1) {
                    System.out.println("Maximálny tok:");
                    MaximalnyTok maximalnyTok = new MaximalnyTok("subory/toky.txt");
                    maximalnyTok.zapisMaxTok("subory/toky2.txt");
                } else if (input == 2) {
                    System.out.println("Najlacnejší tok:");
                    new NajlacnejsiTok("subory/hrany_tok_cena.txt");
                }
            } else if (input == 4) {
                new Cpm("subory/cpm.txt", "subory/cpm_dlzky.txt");
            } else if (input == 5) {
                break;
            }
        }
    }
}
