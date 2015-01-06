/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * Encryption project for University of Helsinki.
 * Course: Algoritmien harjoitustyö.
 * Encryption is based on RSA-algorithm.
 * @author Markus Pahkamaa
 */
public class Encryption {

    // Data that has been read from a file to be encrypted.
    private static String input = new String();
    
    // Integer that is created from input string.
    private static BigInteger muodostettuLuku;
    
    // Small modulus for transforming bigInteger to a String or vice versa.
    private static final BigInteger modulusLuku = BigInteger.valueOf(256L);
    
    // Encrypted data read from encrypted file.
    private static BigInteger luettu;
    
    // Common Big modulus for public and private keys.
    private static BigInteger nModulus;
    
    // Public exponent for encryption, currently it's 17.
    private static BigInteger eExponent;
    
    // Private exponent for decryption.
    private static BigInteger dExponent;
    
    /**
     * Generates the integer from read data.
     */
    private static void generateInt(){
        if ( !input.isEmpty() ){
            int i = 0;
            
            while ( i < input.length() ){
                
                String ch = new String(""+input.charAt(i));
                BigInteger tmp = new BigInteger(ch.getBytes());
                
                BigInteger power = modulusLuku;
                power = power.pow(i);
                tmp = tmp.multiply(power);
                if ( muodostettuLuku == null ){
                    muodostettuLuku = new BigInteger(tmp.toByteArray());
                }else {
                    muodostettuLuku = muodostettuLuku.add(tmp);
                }
                i++;
            }
            if (muodostettuLuku.compareTo(nModulus) >= 1){
                System.out.println("Virhe, viesti on liian pitkä!");
                return;
            }
            muodostettuLuku = muodostettuLuku.modPow(eExponent, nModulus);
            writeFile(new File("encrypted.txt"), muodostettuLuku.toByteArray());
        }
        else 
        {
            System.out.println("No data to generate integer");    
        }
    }

    /**
     * Decrypts the files content.
     * @param fileToDecrypt file to decrypt.
     */
    private static void generateString(File fileToDecrypt){   
        
        String array_str = new String("");
        
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            byte[] array = new byte[1024];
            FileInputStream fis = new FileInputStream(fileToDecrypt);
            BufferedInputStream bis = new BufferedInputStream(fis);
            
            while ( true ){
                
                int error = bis.read(array);
                
                if ( error == -1 ){
                    break;
                }
                
                baos.write(array, 0, error);
            }
            bis.close();
            baos.close();

            luettu = new BigInteger(baos.toByteArray());
            
        }catch ( IOException ex ){
            System.out.println("Error...");
            System.out.println(ex.getMessage());
        }   
        
        if ( luettu != null ){
            
            luettu = luettu.modPow(dExponent, nModulus);
            
            while ( true ){
                BigInteger jakojaannos = luettu.mod(modulusLuku);
                String tmp = new String(jakojaannos.toByteArray());
                luettu = luettu.subtract(jakojaannos);
                array_str = array_str + tmp;
                luettu = luettu.divide(modulusLuku);
                if ( luettu.equals(BigInteger.valueOf(0L))){
                    break;
                }
            }
        }
        
        array_str = removePadding(array_str);
        
        File fout = new File("decrypted.txt");
        try{
            FileWriter fwr = new FileWriter(fout);
            try (BufferedWriter bwr = new BufferedWriter(fwr)) {
                bwr.write(array_str);
                bwr.close();
                /*int i = 0;
                while (i < array_str.size() ){
                    bwr.write(array_str.get(i));
                    i++;
                }*/
            }
        }
        catch ( IOException ex ){
            System.out.println("File writing failed: " + ex.getMessage());
        }
    }
    
    /**
     * Reads contents of a file
     * @param fileToRead file which data is to be read.
     * @throws Exception 
     */
    private static void readContents(File fileToRead) throws Exception{
        if ( fileToRead.canRead()) {
            FileReader fr = new FileReader(fileToRead);
            BufferedReader bfr = new BufferedReader(fr);
            String tmp = bfr.readLine();
            while ( tmp != null ){
                input = input + tmp;
                tmp = bfr.readLine();
            }
            addPadding();
        }
        else{
            throw new Exception("File cannot be read!");
        }
    }
    
    /**
     * Adds random padding to the input data. 
     * 2 characters to beginning of string and to ending of string
     */
    private static void addPadding(){
        if ( !input.isEmpty()){
            Random rnd = new Random(System.nanoTime());
            
            String tmpBegining = new String("");
            char first = (char) (rnd.nextInt() % 256);
            char second = (char) (rnd.nextInt() % 256);
            tmpBegining = "" + first + second;
            
            String tmpEnding = new String("");
            char secondLast = (char) (rnd.nextInt() % 256);
            char last = (char) (rnd.nextInt() % 256);
            tmpEnding = "" + secondLast + last;
            
            input = tmpBegining + input + tmpEnding;
        }
    }
    
    /**
     * Removes the padding from given string, currently we have 2 extra characters
     * at the beginning of string and at the end of the string.
     * @param paddedText, String that contains padding
     * @return 
     */
    private static String removePadding(String paddedText){
        String returnValue = new String("");
        
        if ( !paddedText.isEmpty()){
            // Beginning position should be 2 and correct last position should be
            // length - 2.
            returnValue = paddedText.substring(2, paddedText.length()-2);
        }
        
        return returnValue;
    }
    /**
     * Generates BigInteger keys.
     * public exponent e
     * private exponent d
     * and modulus n.
     * Writes them to a files private.key and public.key
     */
    private static void generateKeys(){
        
        boolean generationFailed = false;
        
        Random rnd = new Random(System.nanoTime());
        
        BigInteger p = BigInteger.probablePrime(512, rnd);
        BigInteger q = BigInteger.probablePrime(512, rnd);
        
        while ( p.equals(q)){
            
            q = BigInteger.probablePrime(512, rnd);
        }
        
        BigInteger n = p.multiply(q);
        
        BigInteger fii = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.valueOf(17L);//BigInteger.valueOf(65537L);
        
        BigInteger [] debugVariable = fii.divideAndRemainder(e);
        
        if ( debugVariable[0].compareTo(BigInteger.ZERO) == 0){
            System.out.println("Ei ole sopiva luku...");
            generationFailed = true;
        }
        
        if ( debugVariable[1].compareTo(BigInteger.ZERO) == 0 ){
            System.out.println("Ei ole sopiva luku...");
            generationFailed = true;
        }
        
        BigInteger d = null;
        try{
            d = e.modInverse(fii);    
            
        }catch( Exception ex ){
            generationFailed = true;
            System.out.println(ex.getMessage());
            System.out.println("Please wait, trying again...");
        }
        
        
        BigInteger test = d.multiply(e).mod(fii);
        if ( test.equals(BigInteger.ONE)){
            System.out.println("d*e mod n = 1. OK Numbers");
        }
        
        if ( generationFailed ){
            // recursion needed if the key generation 
            // fails so that key-pair is generated
            generateKeys(); 
        }else{
            writeFileExponentModulus(new File("public.key"), e, n);
            writeFileExponentModulus(new File("private.key"), d, n);
        }
        
        /**
         * TESTING
         * TODO: check if creating unit tests is possible
         
        BigInteger test2 = new BigInteger("2");
        test2 = test2.modPow(e, n);
        test2 = test2.modPow(d, n);
        
        if ( test2.equals(new BigInteger("2"))){
            System.out.println("Keys seem to be working.");
        }
        
        BigInteger test3 = new BigInteger("T".getBytes());
        test3 = test3.modPow(e, n);
        test3 = test3.modPow(d, n);
        
        if ( test3.equals(new BigInteger("T".getBytes()))){
            System.out.println("Keys seem to be working.");
        }
        
        BigInteger test4 = generateIntLocally("Ta");
        test4 = test4.modPow(e, n);
        test4 = test4.modPow(d, n);
        
        String decrypt = generateStringLocally(test4);
        if ( decrypt.equals("Ta")){
            System.out.println("Keys seem to be working.");
            System.out.println(decrypt);
        }
        */
        
    }
    
    /**
     * @param fileToWrite file where data is written into
     * @param exponent exponent bytearray
     * @param modulus modulus bytearray
     */
    private static void writeFileExponentModulus(File fileToWrite, 
            BigInteger exponent, 
            BigInteger modulus ){
    
        try{
            FileWriter fwr = new FileWriter(fileToWrite);
            try(BufferedWriter bfw = new BufferedWriter(fwr)){
                bfw.write(exponent.toString());
                bfw.write("\n");
                bfw.write(modulus.toString());
                bfw.write("\n");
            }
        }
        catch ( IOException ex ){
            System.out.println("Error...");
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Read the exponent and modulus from given file. Private exponent d and
     * public exponent e variables will be same, but those are used in different
     * situations.
     * @param fileToRead input file for data
     */
    private static void readFileExponentModulus(File fileToRead){
        
         try {
            FileReader fr = new FileReader(fileToRead);
            BufferedReader bfr = new BufferedReader(fr);
            String [] array = new String[2];
            
            for ( int i = 0; i < 2; i++ ){
                array[i] = bfr.readLine();
            }
            
            // private exponent
            dExponent = new BigInteger(array[0]);
            // public exponent
            eExponent = new BigInteger(array[0]);
            // common modulus
            nModulus = new BigInteger(array[1]);
            
        }catch ( IOException ex ){
            System.out.println("Error...");
            System.out.println(ex.getMessage());
        } 
        
    }
    
    /**
     * @param fileToWrite file where data is written into
     * @param bArray byte array to write into the file
     */
    private static void writeFile(File fileToWrite, byte [] bArray ){
        
        try{
            FileOutputStream fos = new FileOutputStream(fileToWrite);
            try (BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                bos.write(bArray);
            }

        }catch( IOException ex ){
            System.out.println("Error, something happened.");
            System.out.println(ex.getMessage());
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
                generateKeys();
            }
        }

        if ( args.length == 3 ){
            if ( args[0].equals("-encrypt")){
                System.out.println("Ecrypting...");
                
                try {
                    readFileExponentModulus(new File(args[1]));
                    readContents(new File(args[2]));
                    generateInt();
                }catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }  
            }

            if ( args[0].equals("-decrypt")){
                System.out.println("Decrypting...");
                readFileExponentModulus(new File(args[1]));
                generateString(new File(args[2]));
            }

            if ( args[1].equals("-sign")){
                System.out.println("Signing... Not yet implemented");
            }
        }
    } 
}
