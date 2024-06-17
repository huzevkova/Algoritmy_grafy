package cpm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Cpm {

    public Cpm(String subor1, String subor2) throws FileNotFoundException {
        Monotonnost monotonnost = new Monotonnost(subor1);
        System.out.println(monotonnost.getPostupnostPismen().toString());
        NajskorMozneZaciatky nmz = new NajskorMozneZaciatky(monotonnost, this.cenaVrcholov(subor2, monotonnost.getPocetVrcholov()));
        NajneskorMozneKonce nnk = new NajneskorMozneKonce(monotonnost, nmz.getTrvanie(), this.cenaVrcholov(subor2, monotonnost.getPocetVrcholov()));

        System.out.println("Trvanie projektu: " + nmz.getTrvanie());

        int[] z = nmz.getZaciatky();
        int[] k = nnk.getKonce();
        int[] p = this.cenaVrcholov(subor2, monotonnost.getPocetVrcholov());

        System.out.println("Cesta kritických činností: ");
        String s = "";
        for (int i : monotonnost.getPostupnost()) {
            if (k[i] - z[i] - p[i] == 0) {
                s += this.konvertuj(i) + ", ";
            }
        }
        System.out.println(s.substring(0, s.length() - 2));
    }

    public int[] cenaVrcholov(String subor, int pocetV) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream(subor));

        int[] cena = new int[pocetV + 1];

        for (int i = 1; i <= pocetV; i++) {
            cena[i] = scan.nextInt();
        }
        scan.close();

        return cena;
    }

    public String konvertuj(int i) {
        ArrayList<String> pismena = new ArrayList<>( Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"));
        return pismena.get(i - 1);
    }
}