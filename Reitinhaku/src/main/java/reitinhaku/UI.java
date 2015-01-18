package reitinhaku;

import java.io.FileNotFoundException;
import java.util.Scanner;
import reitinhaku.logiikka.Astar;
import reitinhaku.logiikka.Solmu;
import reitinhaku.logiikka.Verkko;

/**
 * Luokka vastaa käyttöliittymän toiminnasta
 *
 * @author Samuel
 */
public class UI {

    private Scanner lukija;
    private Verkko verkko;
    private Verkkolukija vlukija;

    /**
     *
     */
    public UI() {
        lukija = new Scanner(System.in);
        vlukija = new Verkkolukija();
    }

    /**
     * Suorittaa ohjelman
     *
     * @param tiedostonimi
     * @throws FileNotFoundException
     */
    public void suorita(String tiedostonimi) throws FileNotFoundException {
        int heur = 0;
        try {
            verkko = vlukija.lueVerkko(tiedostonimi);
        } catch (Exception e) {
            uusiRandom();
        }
        printVerkko(verkko);
        while (true) {
            System.out.println("");
            System.out.println("Anna lahtosolmun x ja y koordinaattit pilkulla erotettuna." + "\n" + "Kirjoita muodossa \"x,y\".");
            System.out.println("Vaihda käytettävä heuristiikka painamalla h (oletuksena Manhattan etäisyys)."
                    + " Luo satunnainen verkko painamalla r."
                    + " Lopeta ohjelma painamalla q");
            String syote = lukija.nextLine();
            if (syote.equals("q")) {
                System.out.println("lopetetaan");
                break;
            }
            long aikaAlussa = System.currentTimeMillis();
            if (kelvollinenSyöte(syote, verkko.getMaxX(), verkko.getMaxY())) {
                int[] lahtosolmu = muodostaKoordinaatit(syote);
                Solmu lahto = verkko.getSolmu(lahtosolmu[0], lahtosolmu[1]);
                while (true) {
                    System.out.println("Anna maalisolmun x ja y koordinaattit pilkulla erotettuna." + "\n" + "Kirjoita muodossa \"x,y\".");
                    syote = lukija.nextLine();
                    if (kelvollinenSyöte(syote, verkko.getMaxX(), verkko.getMaxY())) {
                        int[] maalisolmu = muodostaKoordinaatit(syote);
                        Solmu maali = verkko.getSolmu(maalisolmu[0], maalisolmu[1]);
                        polku(verkko, lahto, maali, heur);
                        break;
                    } else {
                        System.out.println("Ei kelvolliset koordinaatit\n");
                    }
                }
            } else if (syote.equals("r")) {
                uusiRandom();
            } else if (syote.equals("h")) {
                heur = uusiHeuristiikka();
            } else {
                System.out.println("Ei kelvolliset koordinaatit\n");
            }
        }

    }

    /**
     * Vastaa uuden heuristiikan valitsemisesta
     *
     * @return
     */
    public int uusiHeuristiikka() {
        while (true) {
            System.out.println("Vaihda käytettävä heuristiikka." + "\n" + "0=Manhattan" + "\n" + "1=Euklidinen" + "\n" + "muu=Dijkstra");
            String syote = lukija.nextLine();
            if (vlukija.onNumero(syote) && Integer.parseInt(syote) == 0) {
                System.out.println("Manhattan etäisyys valittu");
                return 0;
            }
            if (vlukija.onNumero(syote) && Integer.parseInt(syote) == 1) {
                System.out.println("Euklidinen etäisyys valittu");
                return 1;
            } else {
                System.out.println("Dijkstra valittu");
                return 2;
            }
        }

    }

    /**
     * Vastaa uuden random verkon luomisesta
     */
    public void uusiRandom() {
        Verkkolukija vlukija = new Verkkolukija();
        while (true) {
            System.out.println("Anna verkon korkeus ja leveys pilkulla erotettuna." + "\n" + "Kirjoita muodossa \"x,y\".");
            String syote = lukija.nextLine();
            if (kelvollinenSyöte(syote, 1000000, 100000)) {
                int[] koordinaatit = muodostaKoordinaatit(syote);
                verkko = vlukija.randomVerkko(koordinaatit[0], koordinaatit[1]);
                break;
            } else {
                System.out.println("Ei kelvolliset koordinaatit\n");
            }
        }
        System.out.println("");
        printVerkko(verkko);
    }

    /**
     *
     * @param verkko
     * @param lahto
     * @param maali
     * @param heur
     */
    public void polku(Verkko verkko, Solmu lahto, Solmu maali, int heur) {
        long alkuAika = System.currentTimeMillis();
        Astar a = new Astar(verkko, lahto, maali, heur);
        Solmu p = a.haku();
        long loppuAika = System.currentTimeMillis();
        long aika = loppuAika - alkuAika;
        printPolku(p, verkko);
        System.out.println("Polun kustannus: " + p.getKustannus());
        System.out.println("Polun löytymiseen kului aikaa: " + (aika) + "ms.");
    }

    /**
     *
     * @param polku
     * @param verkko
     */
    public void printPolku(Solmu polku, Verkko verkko) {
        String[][] p = new String[verkko.getMaxY() + 1][verkko.getMaxX() + 1];
        p[polku.getY()][polku.getX()] = "M";
        while (polku.getEdellinen() != null) {
            polku = polku.getEdellinen();
            p[polku.getY()][polku.getX()] = "P";
        }
        p[polku.getY()][polku.getX()] = "L";
        System.out.println("Polku (L=Lahto, P=osa polkua, M=Maali):");
        for (int i = 0; i < verkko.getMaxY() + 1; i++) {
            for (int j = 0; j < verkko.getMaxX() + 1; j++) {
                if (p[i][j] != null) {
                    System.out.print(p[i][j] + " ");
                } else {
                    System.out.print("# ");
                }
            }
            System.out.println("");
        }
    }

    /**
     *
     * @param verkko
     */
    public void printVerkko(Verkko verkko) {
        System.out.println("Verkko:");
        for (int i = 0; i < verkko.getMaxY() + 1; i++) {
            for (int j = 0; j < verkko.getMaxX() + 1; j++) {
                System.out.print(verkko.getSolmu(j, i).getPaino() + " ");
            }
            System.out.println("");
        }
    }

    private int[] muodostaKoordinaatit(String s) {
        String[] syote = s.split(",");
        int[] koordinaatit = new int[2];

        koordinaatit[0] = Integer.parseInt(syote[0]);
        koordinaatit[1] = Integer.parseInt(syote[1]);

        return koordinaatit;
    }

    private boolean kelvollinenSyöte(String s, int maxX, int maxY) {
        if (s.matches("\\d+[,]\\d+")) {
            String[] syote = s.split(",");
            if (Integer.parseInt(syote[0]) >= 0 && Integer.parseInt(syote[0]) <= maxX && Integer.parseInt(syote[1]) >= 0 && Integer.parseInt(syote[1]) <= maxY) {
                return true;
            }
        }
        return false;
    }

}
