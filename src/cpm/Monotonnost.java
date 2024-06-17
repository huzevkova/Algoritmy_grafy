package cpm;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Monotonnost {
    private int pocetVrcholov;
    private int[][] h;
    private int[] ideg;
    private int[][] odegVrcholy;
    private ArrayList<Integer> postupnost;

    public Monotonnost(String subor) throws FileNotFoundException {
        this.precitajUdaje(subor);

        this.postupnost = new ArrayList<>();

        for (int i = 1; i < this.ideg.length; i++) {
            if (this.ideg[i] == 0) {
                this.postupnost.add(i);
            }
        }

        int vrcholPostupnosti = 0;
        while (this.postupnost.size() < this.pocetVrcholov) {
            int i = this.postupnost.get(vrcholPostupnosti);
            if (this.odegVrcholy[i] != null) {
                for (int v : this.odegVrcholy[i]) {
                    this.ideg[v]--;
                    if (this.ideg[v] == 0) {
                        this.postupnost.add(v);
                    }
                }
            }
            vrcholPostupnosti++;
        }
    }

    public ArrayList<Integer> getPostupnost() {
        return this.postupnost;
    }

    public ArrayList<String> getPostupnostPismen() {
        ArrayList<String> pismena = new ArrayList<>();
        for (int i : this.postupnost) {
            pismena.add(this.konvertuj(i));
        }
        return pismena;
    }
    public int[][] getOdegVrcholy() {
        return this.odegVrcholy;
    }

    public int getPocetVrcholov() {
        return this.pocetVrcholov;
    }

    public int[][] getH() {
        return this.h;
    }


    public void precitajUdaje(String subor) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream(subor));

        int pocetHran = 1;
        this.pocetVrcholov = 1;

        while (scan.hasNextLine()) {
            String s = scan.nextLine();
            int u = this.konvertuj(s.substring(0, s.indexOf(" ")));
            int v = this.konvertuj(s.substring(s.indexOf(" ") + 1));

            if (this.pocetVrcholov < u || this.pocetVrcholov < v) {
                this.pocetVrcholov = Math.max(u, v);
            }
            pocetHran++;
        }

        this.h = new int[pocetHran][2];
        this.ideg = new int[this.pocetVrcholov + 1];
        this.odegVrcholy = new int[this.pocetVrcholov + 1][];

        scan.close();
        scan = new Scanner(new FileInputStream(subor));

        for (int i = 1; i < pocetHran; i++) {
            String s = scan.nextLine();
            this.h[i][0] = this.konvertuj(s.substring(0, s.indexOf(" ")));
            this.h[i][1] = this.konvertuj(s.substring(s.indexOf(" ") + 1));

            this.ideg[this.h[i][1]]++;
            if (this.odegVrcholy[this.h[i][0]] == null) {
                this.odegVrcholy[this.h[i][0]] = new int[]{this.h[i][1]};
            } else {
                int[] hviezda = new int[this.odegVrcholy[this.h[i][0]].length + 1];
                System.arraycopy(this.odegVrcholy[this.h[i][0]], 0, hviezda, 0, this.odegVrcholy[this.h[i][0]].length);
                hviezda[this.odegVrcholy[this.h[i][0]].length] = this.h[i][1];
                this.odegVrcholy[this.h[i][0]] = hviezda;
            }
        }
    }

    public int konvertuj(String s) {
        ArrayList<String> pismena = new ArrayList<>( Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"));
        return pismena.indexOf(s) + 1;
    }

    public String konvertuj(int i) {
        ArrayList<String> pismena = new ArrayList<>( Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"));
        return pismena.get(i - 1);
    }
}

