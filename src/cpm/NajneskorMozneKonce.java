package cpm;

import java.util.ArrayList;
import java.util.Arrays;

public class NajneskorMozneKonce {

    private int pocetVrcholov;
    private int[][] h;
    private int[][] odeg;
    private int T;
    private ArrayList<Integer> postupnost;
    private int[] k;

    public NajneskorMozneKonce(Monotonnost monotonnost, int T, int[] cenyVrcholov) {
        this.pocetVrcholov = monotonnost.getPocetVrcholov();
        this.h = monotonnost.getH();
        this.odeg = monotonnost.getOdegVrcholy();
        this.postupnost = monotonnost.getPostupnost();
        this.T = T;

        int[] dlzky = cenyVrcholov;

        this.k = new int[this.pocetVrcholov + 1];
        int[] y = new int[this.pocetVrcholov + 1];

        for (int i = 0; i < k.length; i++) {
            k[i] = this.T;
        }

        for (int i = this.pocetVrcholov - 1; i >= 0; i--) {
            int r = this.postupnost.get(i);
            if (this.odeg[r] != null) {
                for (int v : this.odeg[r]) {
                    if (k[r] > k[v] - dlzky[v]) {
                        k[r] = k[v] - dlzky[v];
                        y[r] = v;
                    }
                }
            }
        }

        System.out.println("Najneskôr možné konce: ");
        for (int i = 1; i <= this.pocetVrcholov; i++) {
            System.out.print(this.konvertuj(i) + ": " + k[i]);
            System.out.println();
        }

    }

    public int[] getKonce() {
        return this.k;
    }

    public String konvertuj(int i) {
        ArrayList<String> pismena = new ArrayList<>( Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"));
        return pismena.get(i - 1);
    }

}
