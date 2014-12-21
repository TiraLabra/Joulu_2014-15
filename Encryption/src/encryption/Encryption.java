/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption;

/**
 *
 * @author Markus
 */
public class Encryption {

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
