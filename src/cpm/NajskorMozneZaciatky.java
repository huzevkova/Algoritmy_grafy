package cpm;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Vytvorenie postupnosti najskôrších možných začiatkov z metódy CPM.
 *
 * @author Bianka S. Húževková
 * @version 2.0 (20.5.2023)
 */
public class NajskorMozneZaciatky {
    private int pocetVrcholov;
    private int[][] h;
    private int[][] odeg;
    private ArrayList<Integer> postupnost;
    private int T;
    private int[] z;

    public NajskorMozneZaciatky(Monotonnost monotonnost, int[] cenyVrcholov) throws FileNotFoundException {
        this.pocetVrcholov = monotonnost.getPocetVrcholov();
        this.h = monotonnost.getH();
        this.odeg = monotonnost.getOdegVrcholy();
        this.postupnost = monotonnost.getPostupnost();

        int[] dlzky = cenyVrcholov;

        this.z = new int[this.pocetVrcholov + 1];
        int[] x = new int[this.pocetVrcholov + 1];

        for (int k = 0; k < this.pocetVrcholov; k++) {
            int r = this.postupnost.get(k);
            if (this.odeg[r] != null) {
                for (int v : this.odeg[r]) {
                    if (z[r] + dlzky[r] > z[v]) {
                        z[v] = z[r] + dlzky[r];
                        x[v] = r;
                    }
                }
            }
        }

        System.out.println("Najskôr možné začiatky:");
        int T = 0;
        for (int i = 1; i < this.odeg.length; i++) {
            System.out.println(this.konvertuj(i) + ": " + z[i]);
            if (this.odeg[i] == null) {
                if (z[i] + dlzky[i] > T) {
                    T = z[i] + dlzky[i];
                }
            }
        }

        this.T = T;
    }

    public int getTrvanie() {
        return this.T;
    }

    public int[] getZaciatky() {
        return this.z;
    }

    public String konvertuj(int i) {
        ArrayList<String> pismena = new ArrayList<>( Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"));
        return pismena.get(i - 1);
    }

}

