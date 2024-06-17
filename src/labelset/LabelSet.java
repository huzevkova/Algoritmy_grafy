package labelset;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Label-set algortimus na nájdenie najkratšej cesty v orientovanom digrafe.
 *
 * @author Bianka S. Húževková
 * @version 2.0 (9.3.2023)
 */
public class LabelSet {
    private int[][] h;
    private int[] c;
    private int[] cisloHrany;
    private int pocetVrcholov;
    private int koniec;
    private int zaciatok;

    public LabelSet(String subor, int zaciatok, int koniec) throws FileNotFoundException {
        long start = System.currentTimeMillis();
        this.precitajUdaje(subor);

        this.koniec = koniec;
        this.zaciatok = zaciatok;

        //inicializacia
        int u = zaciatok;
        int r = u;

        int[] t = new int[this.pocetVrcholov + 1];
        int[] x = new int[this.pocetVrcholov + 1];

        //nastavenie 0 pri zaciatocnom vrchole, ostatné "nekonečno"
        for (int i = 1; i < this.pocetVrcholov + 1; i++) {
            if (i == u) {
                t[i] = 0;
            } else {
                t[i] = Integer.MAX_VALUE;
            }
        }

        ArrayList<Integer> E = new ArrayList<>();
        E.add(r);

        //algoritmus
        while (!E.isEmpty()) {
            E.remove((Object)r);
            for (int i = this.cisloHrany[r]; i < this.cisloHrany[r + 1]; i++) {
                int j = this.h[i][1];
                if (t[r] + this.c[i] < t[j]) {
                    t[j] = t[r] + this.c[i];
                    x[j] = r;
                    if (!E.contains(j)) {
                        E.add(j);
                    }
                }
            }

            int min = this.getNajmensiaHodnota(t, E);
            if (min != 0) {
                r = min;
            }
        }

        //výpis
        System.out.println(subor.substring(21));
        System.out.println("Počet vrcholov: " + this.pocetVrcholov);
        System.out.println("Počet hrán: " + (this.h.length - 1));
        for (int i = 1; i <= this.pocetVrcholov; i++) {
            if (t[i] == Integer.MAX_VALUE) {
                System.out.println(i + ": neexistuje cesta");
            } else {
                System.out.println(i + ": t=" + t[i] + " x=" + x[i]);
            }
        }

        this.najdiVrchol(x, t, this.koniec);


        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("Čas: " + (float)time / 1000);

    }

    /**
     * Metóda na nájdenie najkratšej cesty do konkrétneho vrcholu
     */
    public void najdiVrchol(int[] x, int[] t, int v) {
        System.out.println("Z vrcholu: " + this.zaciatok + " do vrcholu: " + v);
        int cesta = x[v];
        long dlzka = t[v];
        int pocet = 1;
        String s = cesta + " -> " + this.koniec;
        while (cesta != this.zaciatok) {
            cesta = x[cesta];
            s = cesta + " -> " + s;
            pocet++;
        }
        System.out.println("Dlzka cesty: " + dlzka);
        System.out.println("Pocet hran: " + pocet);
        System.out.println(s);

    }


    /**
     * Metóda na získanie vrcholu s najmenšou dĺžkou.
     */
    public int getNajmensiaHodnota(int[] t, ArrayList<Integer> e) {
        int min = Integer.MAX_VALUE;
        int vrchol = 0;
        for (int v : e) {
            if (t[v] < min) {
                min = t[v];
                vrchol = v;
            }
        }
        return vrchol;
    }

    /**
     * Metóda na prečítanie a uloženie údajov, t.j. grafu.
     */
    public void precitajUdaje(String subor) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream(subor));

        int pocetHran = 1;
        this.pocetVrcholov = 1;

        while (scan.hasNextLine()) {
            int u = scan.nextInt();
            int v = scan.nextInt();
            scan.nextInt();

            if (this.pocetVrcholov < u || this.pocetVrcholov < v) {
                this.pocetVrcholov = Math.max(u, v);
            }
            pocetHran++;
        }

        this.h = new int[pocetHran][2];
        this.c = new int[pocetHran];
        this.cisloHrany = new int[this.pocetVrcholov + 2];

        scan.close();
        scan = new Scanner(new FileInputStream(subor));

        int index = 1;
        for (int i = 1; i < pocetHran; i++) {
            this.h[i][0] = scan.nextInt();
            if (this.h[i][0] != index) {
                this.cisloHrany[index + 1] = i;
                index++;
            }
            this.h[i][1] = scan.nextInt();
            this.c[i] = scan.nextInt();
        }
        this.cisloHrany[index + 1] = pocetHran;

    }

}
