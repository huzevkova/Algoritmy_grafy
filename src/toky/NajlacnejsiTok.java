package toky;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class NajlacnejsiTok {

    private int[][] h;
    private int[] kapacity;
    private int[] toky;
    private int[] ceny;
    private int pocetVrcholov;

    public NajlacnejsiTok(String subor) throws FileNotFoundException {
        this.precitajUdaje(subor);
        int staraCena = this.cenaToku();
        int novaCena = staraCena - 1;
        System.out.println("Pôvodná cena: " + staraCena);

        while (true) {
            staraCena = this.cenaToku();
            int[] t = new int[this.pocetVrcholov + 1];
            int[] x = new int[this.pocetVrcholov + 1];

            for (int i = 1; i < t.length; i++) {
                if (i != 1) {
                    t[i] = Integer.MAX_VALUE;
                }
            }

            int cyklus = 0;
            boolean zmena = true;

            outer:
            while (zmena) {
                zmena = false;
                for (int i = 0; i < this.h.length; i++) {
                    if (this.toky[i] > 0) {
                        int u = this.h[i][0];
                        int v = this.h[i][1];
                        if (t[u] > t[v] - this.ceny[i] && u != 1) {
                            t[u] = t[v] - this.ceny[i];
                            x[u] = v;
                            zmena = true;
                            if (this.kontrola(x, u)) {
                                cyklus = u;
                                break outer;
                            }
                        }
                    }
                    if (this.toky[i] < this.kapacity[i]) {
                        int u = this.h[i][0];
                        int v = this.h[i][1];
                        if (t[v] > t[u] + this.ceny[i] && u != 1) {
                            t[v] = t[u] + this.ceny[i];
                            x[v] = u;
                            zmena = true;
                            if (this.kontrola(x, v)) {
                                cyklus = v;
                                break outer;
                            }
                        }
                    }
                }
            }

            ArrayList<Integer> rezervnaPolocesta = new ArrayList<>();
            rezervnaPolocesta.add(cyklus);
            int cesta = x[cyklus];
            while (cesta != cyklus) {
                rezervnaPolocesta.add(cesta);
                cesta = x[cesta];
            }
            rezervnaPolocesta.add(cesta);

            int zapornaCena = 0;
            int rezerva = Integer.MAX_VALUE;
            for (int i = 0; i < rezervnaPolocesta.size() - 1; i++) {
                for (int j = 0; j < this.h.length; j++) {
                    if (this.h[j][0] == rezervnaPolocesta.get(i) && this.h[j][1] == rezervnaPolocesta.get(i + 1)) {
                        rezerva = Math.min(rezerva, this.toky[j]);
                        zapornaCena -= this.ceny[j];
                        break;
                    } else if (this.h[j][1] == rezervnaPolocesta.get(i) && this.h[j][0] == rezervnaPolocesta.get(i + 1)) {
                        rezerva = Math.min(rezerva, this.kapacity[j] - this.toky[j]);
                        zapornaCena += this.ceny[j];
                        break;
                    }
                }
            }

            if (zapornaCena < 0) {
                for (int i = 0; i < rezervnaPolocesta.size() - 1; i++) {
                    for (int j = 0; j < this.h.length; j++) {
                        if (this.h[j][0] == rezervnaPolocesta.get(i) && this.h[j][1] == rezervnaPolocesta.get(i + 1)) {
                            this.toky[j] -= rezerva;
                            break;
                        } else if (this.h[j][1] == rezervnaPolocesta.get(i) && this.h[j][0] == rezervnaPolocesta.get(i + 1)) {
                            this.toky[j] += rezerva;
                            break;
                        }
                    }
                }
                novaCena = this.cenaToku();
                if (novaCena > staraCena) {
                    novaCena = staraCena;
                    break;
                }
            } else {
                break;
            }
        }

        for (int i = 0; i < this.h.length; i++) {
            System.out.println("(" + this.konvertuj(this.h[i][0]) + ", " + this.konvertuj(this.h[i][1]) + "): c(" + this.kapacity[i] + "), y(" + this.toky[i] + ")");
        }

        System.out.println("Nová (najlacnejsia) cena: " + novaCena);
    }

    public boolean kontrola(int[] x, int i) {
        int pocet = 1;
        int cesta = x[i];
        while (cesta != 0 && cesta < x.length && pocet < x.length) {
            pocet++;
            cesta = x[cesta];
            if (cesta == i ) {
                return pocet > 2;
            }
        }
        return false;
    }

    public int cenaToku() {
        int cena = 0;
        for (int i = 0; i < this.toky.length; i++) {
            cena = cena + this.toky[i] * this.ceny[i];
        }
        return cena;
    }

    public void precitajUdaje(String subor) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream(subor));

        int pocetHran = 0;
        this.pocetVrcholov = 1;

        while (scan.hasNextLine()) {
            int u = scan.nextInt();
            int v = scan.nextInt();
            scan.nextInt();
            scan.nextInt();
            scan.nextInt();

            if (this.pocetVrcholov < u || this.pocetVrcholov < v) {
                this.pocetVrcholov = Math.max(u, v);
            }
            pocetHran++;
        }

        this.h = new int[pocetHran][3];
        this.kapacity = new int[pocetHran];
        this.toky = new int[pocetHran];
        this.ceny = new int[pocetHran];

        scan.close();
        scan = new Scanner(new FileInputStream(subor));

        for (int i = 0; i < pocetHran; i++) {
            this.h[i][0] = scan.nextInt();
            this.h[i][1] = scan.nextInt();
            this.kapacity[i] = scan.nextInt();
            this.toky[i] = scan.nextInt();
            this.ceny[i] = scan.nextInt();
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
