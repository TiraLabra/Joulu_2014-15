package reitinhaku;

import java.io.FileNotFoundException;

/**
 *
 * @author Samuel
 */
public class App {

    /**
     * Käynnistää ohjelman
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        UI ui = new UI();
        String tiedostonimi;
        try {
            tiedostonimi = args[0];
        } catch (Exception e) {
            tiedostonimi = "verkko1.txt";
        }
        ui.suorita(tiedostonimi);
    }

}
