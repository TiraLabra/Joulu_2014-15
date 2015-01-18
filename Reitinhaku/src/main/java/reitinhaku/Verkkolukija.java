package reitinhaku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import reitinhaku.logiikka.Verkko;

/**
 *
 * @author Samuel
 */
public class Verkkolukija {

    private String verkkoKansionPolku;
    private int x;
    private int y;

    /**
     *
     */
    public Verkkolukija() {
        this.verkkoKansionPolku = projektinPolku();
    }

    /**
     *
     * @return
     */
    public String projektinPolku() {
        String projektinPolku = System.getProperty("user.dir");
        projektinPolku = projektinPolku.substring(0, projektinPolku.length() - 10) + "Verkot/";
        return projektinPolku;
    }

    /**
     *
     * @param tiedostonimi
     * @return
     * @throws FileNotFoundException
     */
    public Verkko lueVerkko(String tiedostonimi) throws FileNotFoundException {
        File file = new File(verkkoKansionPolku + tiedostonimi);
        Scanner lukija = new Scanner(file);
        asetaKoko(file);
        int[][] v = new int[y][x];
        lukija = new Scanner(file);
        for (int i = 0; i < y; i++) {
            String rivi = lukija.nextLine();
            for (int j = 0; j < x; j++) {
                char m = rivi.charAt(j);
                int paino = 1;
                if (onNumero("" + m) && Integer.parseInt("" + m) >= 1) {
                    paino = Integer.parseInt("" + m);
                }
                v[i][j] = paino;

            }
        }
        lukija.close();
        Verkko verkko = new Verkko(v);
        return verkko;

    }

    /**
     *
     * @param tiedosto
     * @throws FileNotFoundException
     */
    public void asetaKoko(File tiedosto) throws FileNotFoundException {
        Scanner lukija = new Scanner(tiedosto);
        y = 0;
        x = Integer.MAX_VALUE;
        String rivi = "";
        while (lukija.hasNext()) {
            y++;
            rivi = lukija.nextLine();
            if (rivi.length() < x) {
                x = rivi.length();
            }
        }
        lukija.close();

    }

    /**
     *
     * @param s
     * @return
     */
    public boolean onNumero(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param xK
     * @param yK
     * @return
     */
    public Verkko randomVerkko(int xK, int yK) {
        Random rand = new Random();
        int[][] v = new int[yK][xK];
        for (int i = 0; i < yK; i++) {
            for (int j = 0; j < xK; j++) {
                v[i][j] = rand.nextInt((9 - 1) + 1) + 1;
            }
        }
        Verkko verkko = new Verkko(v);
        return verkko;

    }
}
