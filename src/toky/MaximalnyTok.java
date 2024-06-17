package toky;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MaximalnyTok {

    private int[][] h;
    private int[] kapacity;
    private int[] toky;
    private int pocetVrcholov;
    private int[] ceny;

    public MaximalnyTok(String subor) throws FileNotFoundException {
        this.precitajUdaje(subor);
        boolean existuje = true;
        int ustie = this.pocetVrcholov;
        while (existuje) {
            int[] x = new int[this.pocetVrcholov + 1];
            x[1] = 1;
            int r = 1;

            ArrayList<Integer> E = new ArrayList<>();
            E.add(r);

            int minRezerva = Integer.MAX_VALUE;
            while (!E.contains(ustie)) {
                r = E.remove(0);
                for (int i = 0; i < this.h.length; i++) {
                    if (this.h[i][0] == r && x[this.h[i][1]] == 0) {
                        if (this.kapacity[i] - this.toky[i] > 0) {
                            x[this.h[i][1]] = r;
                            if (!E.contains(this.h[i][1])) {
                                E.add(this.h[i][1]);
                            }
                            if (this.kapacity[i] - this.toky[i] < minRezerva) {
                                minRezerva = this.kapacity[i] - this.toky[i];
                            }
                        }
                    } else if (this.h[i][1] == r && x[this.h[i][0]] == 0) {
                        if (this.toky[i] > 0) {
                            x[this.h[i][0]] = -r;
                            if (!E.contains(this.h[i][0])) {
                                E.add(this.h[i][0]);
                            }
                            if (this.toky[i] < minRezerva) {
                                minRezerva = this.toky[i];
                            }
                        }
                    }
                }
                if (E.isEmpty()) {
                    existuje = false;
                    break;
                }
            }

            if (existuje) {
                ArrayList<Integer> zvacsujucaPolocesta = new ArrayList<>();
                zvacsujucaPolocesta.add(ustie);
                int cesta = x[ustie];
                while (cesta != 1) {
                    zvacsujucaPolocesta.add(Math.abs(cesta));
                    cesta = x[Math.abs(cesta)];
                }
                zvacsujucaPolocesta.add(1);
                this.aktualizujToky(minRezerva, zvacsujucaPolocesta);
            }
        }

        for (int i = 0; i < this.h.length; i++) {
            System.out.println("(" + this.konvertuj(this.h[i][0]) + ", " + this.konvertuj(this.h[i][1]) + "): c(" + this.kapacity[i] + "), y(" + this.toky[i] + ")");
        }
        System.out.println("Velkosť toku: " + this.velkostToku());
    }

    public int velkostToku() {
        int velkost = 0;
        for (int i = 0; i < this.h.length; i++) {
            if (this.h[i][0] == 1) {
                velkost += this.toky[i];
            }
        }
        return velkost;
    }

    public void aktualizujToky(int rezerva, ArrayList<Integer> polocesta) {
        for (int i = polocesta.size() - 1; i > 0; i--) {
            for (int j = 0; j < this.h.length; j++) {
                if (this.h[j][0] == polocesta.get(i) && this.h[j][1] == polocesta.get(i - 1)) {
                    this.toky[j] = this.toky[j] + rezerva;
                } else if (this.h[j][0] == polocesta.get(i - 1) && this.h[j][1] == polocesta.get(i)) {
                    this.toky[j] = this.toky[j] - rezerva;
                }
            }
        }
    }

    public void zapisMaxTok(String subor) throws FileNotFoundException {
        File file = new File(subor);
        PrintWriter writer = new PrintWriter(file);

        for (int i = 0; i < this.h.length; i++) {
            writer.print(this.h[i][0] + " " + this.h[i][1] + " " + this.kapacity[i] + " " + this.toky[i] + " " + this.ceny[i]);
            if (i + 1 != this.h.length) {
                writer.println();
            }
        }
        writer.close();
    }

    public void precitajUdaje(String subor) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream(subor));

        int pocetHran = 0;
        this.pocetVrcholov = 1;

        while (scan.hasNextLine()) {
            int u = this.konvertujPrvyKrat(scan.next());
            int v = this.konvertujPrvyKrat(scan.next());
            scan.nextInt();
            scan.nextInt();
            scan.nextInt();

            if (this.pocetVrcholov < u || this.pocetVrcholov < v) {
                this.pocetVrcholov = Math.max(u, v);
            }
            pocetHran++;
        }

        this.h = new int[pocetHran][2];
        this.kapacity = new int[pocetHran];
        this.toky = new int[pocetHran];
        this.ceny = new int[pocetHran];

        scan.close();
        scan = new Scanner(new FileInputStream(subor));

        for (int i = 0; i < pocetHran; i++) {
            this.h[i][0] = this.konvertuj(scan.next());
            this.h[i][1] = this.konvertuj(scan.next());
            this.kapacity[i] = scan.nextInt();
            this.toky[i] = scan.nextInt();
            this.ceny[i] = scan.nextInt();
        }
    }

    public int konvertujPrvyKrat(String s) {
        ArrayList<String> pismena = new ArrayList<>( Arrays.asList("Z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"));
        if (s.equals("U")) {
            return this.pocetVrcholov + 1;
        } else {
            return pismena.indexOf(s) + 1;
        }
    }
    public int konvertuj(String s) {
        ArrayList<String> pismena = new ArrayList<>( Arrays.asList("Z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"));
        if (s.equals("U")) {
            return this.pocetVrcholov;
        } else {
            return pismena.indexOf(s) + 1;
        }
    }

    public String konvertuj(int i) {
        ArrayList<String> pismena = new ArrayList<>( Arrays.asList("Z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"));
        if (i == this.pocetVrcholov) {
            return "U";
        } else {
            return pismena.get(i - 1);
        }
    }
}
