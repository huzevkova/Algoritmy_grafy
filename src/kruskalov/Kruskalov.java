package kruskalov;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Kruskalov algortimus na hľadanie najlacnejšej alebo najdrahšej kostry súvislého hranovo ohodnoteného grafu.
 *
 * @author Bianka S. Húževková
 * @version 2.0 (9.3.2023)
 */
public class Kruskalov {
    private int[][] hrany;
    private int pocetVrcholov;
    private int pocetHran;
    private ArrayList<String> hranyVKostre;
    private boolean najlacnejsia;

    public Kruskalov(String subor, boolean najlacnejsia) throws FileNotFoundException {
        long start = System.currentTimeMillis();
        this.nacitaj(subor);
        this.najlacnejsia = najlacnejsia;

        int[][] P = this.zorad(this.najlacnejsia);

        //inicializácia
        this.hranyVKostre = new ArrayList<>();
        int pocetHranKostry = 0;
        long cenaKostry = 0;

        int[] k = new int[this.pocetVrcholov + 1];
        int[][] zoznamyKomponentov = new int[this.pocetVrcholov + 1][];
        for (int i = 0; i < k.length; i++) {
            k[i] = i;
            zoznamyKomponentov[i] = new int[]{i};
        }

        int index = 1;
        if (!this.najlacnejsia) {
            index = 0;
        }

        //algoritmus
        while (index != this.pocetHran && pocetHranKostry != this.pocetVrcholov - 1) {
            int x = P[index][0];
            int y = P[index][1];
            int k1 = k[x];
            int k2 = k[y];
            if (k1 != k2) {
                this.hranyVKostre.add("(" + x + "," + y + ")");
                pocetHranKostry++;
                cenaKostry += P[index][2];
                int min = Math.min(k1, k2);
                int max = Math.max(k1, k2);
                for (int i : zoznamyKomponentov[k2]) {
                    k[i] = k1;
                }

                int[] novyZoznam = new int[zoznamyKomponentov[k1].length + zoznamyKomponentov[k2].length];
                System.arraycopy(zoznamyKomponentov[k1], 0, novyZoznam, 0, zoznamyKomponentov[k1].length);
                System.arraycopy(zoznamyKomponentov[k2], 0, novyZoznam, zoznamyKomponentov[k1].length, zoznamyKomponentov[k2].length);
                zoznamyKomponentov[k1] = novyZoznam;
                zoznamyKomponentov[k2] = null;
            }
            index++;
        }

        //vypis
        if (index == this.pocetVrcholov && pocetHranKostry != this.pocetVrcholov - 1) {
            System.out.println("Kostra neexistuje.");
        } else {
            System.out.println(subor.substring(21));
            System.out.println("Pocet hran: " + this.pocetHran);
            System.out.println("Pocet hran kostry: " + pocetHranKostry);
            System.out.println("Pocet vrcholov: " + this.pocetVrcholov);
            if (this.najlacnejsia) {
                System.out.println("Cena najkratšej kostry: " + cenaKostry);
            } else {
                System.out.println("Cena najdlhšej kostry: " + cenaKostry);
            }

        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println((float)time / 1000);

    }

    /**
     * Vypíše hrany v kostre.
     *
     * @return
     */
    public String vypisHranyVKostre() {
        String s = "";
        for (String hrana : this.hranyVKostre) {
            s += hrana + ", ";
        }
        return s.substring(0, s.length() - 2);
    }

    /**
     * Metóda na prečítanie a zapísanie grafu.
     *
     * @param nazov nazov suboru ktory treba načítať
     * @throws FileNotFoundException
     */
    public void nacitaj(String nazov) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream(nazov));

        this.pocetVrcholov = 1;
        this.pocetHran = 0;

        while (scan.hasNext()) {
            try {
                int u = scan.nextInt();
                int v = scan.nextInt();
                int c = scan.nextInt();
                this.pocetHran++;

                if (this.pocetVrcholov < u || this.pocetVrcholov < v) {
                    this.pocetVrcholov = Math.max(u, v);
                }
            } catch (Exception e) {

            }
        }

        scan.close();
        Scanner scanner = new Scanner(new FileInputStream(nazov));

        this.hrany = new int[this.pocetHran + 1][3];

        for (int i = 1; i < this.pocetHran + 1; i++) {
            this.hrany[i][0] = scanner.nextInt();
            this.hrany[i][1] = scanner.nextInt();
            this.hrany[i][2] = scanner.nextInt();
        }
    }

    /**
     * Metóda ktorá zoradí hrany buď vzostupne alebo zostupne.
     *
     * @param vzostupne či má zoradiť hrany vzostupne alebo nie
     * @return zoradené pole hrán
     */
    public int[][] zorad(boolean vzostupne) {

        if (vzostupne) {
            Arrays.sort(this.hrany, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return Integer.compare(o1[2], o2[2]);
                }
            });
        } else {
            Arrays.sort(this.hrany, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return Integer.compare(o2[2], o1[2]);
                }
            });
        }

        /*
        boolean zoradene = false;
        while (!zoradene) {
            zoradene = true;
            for (int i = this.hrany.length - 1; i > 1; i--) {
                if (this.hrany[i][2] < this.hrany[i - 1][2]) {
                    zoradene = false;
                    int u = this.hrany[i][0];
                    int v = this.hrany[i][1];
                    int c = this.hrany[i][2];
                    this.hrany[i][0] = this.hrany[i - 1][0];
                    this.hrany[i][1] = this.hrany[i - 1][1];
                    this.hrany[i][2] = this.hrany[i - 1][2];
                    this.hrany[i - 1][0] = u;
                    this.hrany[i - 1][1] = v;
                    this.hrany[i - 1][2] = c;
                }
            }
        }*/

        return this.hrany;
    }
}
