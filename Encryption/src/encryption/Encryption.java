/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;

/**
 *
 * @author Markus
 */
public class Encryption {

    private static String input = new String();
    
    private static void generateInt(){
        if ( !input.isEmpty() ){
            long kokonaisluku = 0L;
            
            
            int i = 0;
            
            while ( i < input.length() ){
                char ch = input.charAt(i);
                kokonaisluku += (long)ch*Math.pow(256, i);
                i++;
            }
            System.out.println("Kokonaisluku: " + kokonaisluku);
            
        }
        System.out.println("No data to generate integer");
    }
    
    private static void readContents(File fileToRead) throws Exception{
        if ( fileToRead.canRead()) {
            FileReader fr = new FileReader(fileToRead);
            BufferedReader bfr = new BufferedReader(fr);
            String tmp = bfr.readLine();
            while ( tmp != null ){
                input = input + tmp;
                tmp = bfr.readLine();
            }
            System.out.println("Tiedoston sisältö: " + input);
        }
        else{
            throw new Exception("File cannot be read!");
        }
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if ( args.length == 0 ){
            System.out.println("Generate public and private keys with command: -generate_keys");
            System.out.println("Encrypt the file with command: -encrypt <public.key> <file>");
            System.out.println("Decrypt the file with command: -decrypt <secret.key> <file>");
            System.out.println("Sign the file with command: -sign <secret.key> <file>");
            return; 
        }
        
        if ( args.length == 1 ){
            if ( args[0].equals("-generate_keys")){
                System.out.println("Generating keys.");
                System.out.println("Nothing yet");            
            }
        }

        if ( args.length == 3 ){
            if ( args[0].equals("-encrypt")){
                System.out.println("Ecrypting...");
                File file = new File(args[2]);
                try {
                    readContents(file);
                    generateInt();
                }catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                
            }

            if ( args[0].equals("-decrypt")){
                System.out.println("Decrypting...");
            }

            if ( args[1].equals("-sign")){
                System.out.println("Signing...");
            }
        }
    } 
}
