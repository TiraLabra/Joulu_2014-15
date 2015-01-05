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
    
    private static BigInteger generateIntLocally(String message){
        BigInteger retValue = null;
        if ( !message.isEmpty() ){
            int i = 0;
            
            while ( i < message.length() ){
                
                String ch = new String(""+message.charAt(i));
                BigInteger tmp = new BigInteger(ch.getBytes());
                
                BigInteger power = modulusLuku;
                power = power.pow(i);
                tmp = tmp.multiply(power);
                if ( retValue == null ){
                    retValue = new BigInteger(tmp.toByteArray());
                }else {
                    retValue = retValue.add(tmp);
                }
                i++;
            }
        }
        else 
        {
            System.out.println("No data to generate integer");    
        }
        return retValue;
    }
    
    private static String generateStringLocally(BigInteger representation){
        String valmis = new String("");
        
        while ( true ){
            BigInteger jakojaannos = representation.mod(modulusLuku);
            String tmp = new String(jakojaannos.toByteArray());
            representation = representation.subtract(jakojaannos);
            valmis = valmis + tmp;
            representation = representation.divide(modulusLuku);
            if ( representation.equals(BigInteger.valueOf(0L))){
                break;
            }
        }
        return valmis;
    }

    /**
     * Decrypts the files content.
     * @param fileToDecrypt file to decrypt.
     */
    private static void generateString(File fileToDecrypt){   
        ArrayList<String> array_str = new ArrayList<>();
        
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
                array_str.add(tmp);
                luettu = luettu.divide(modulusLuku);
                if ( luettu.equals(BigInteger.valueOf(0L))){
                    break;
                }
            }
        }
        
        File fout = new File("decrypted.txt");
        try{
            FileWriter fwr = new FileWriter(fout);
            try (BufferedWriter bwr = new BufferedWriter(fwr)) {
                int i = 0;
                while (i < array_str.size() ){
                    bwr.write(array_str.get(i));
                    i++;
                }
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
        }
        else{
            throw new Exception("File cannot be read!");
        }
    }
    
    /**
     * Generates BigInteger keys.
     * public exponent e
     * private exponent d
     * and modulus n.
     * Writes them to a files private.key and public.key
     */
    private static void generateKeys(){
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
        }
        if ( debugVariable[1].compareTo(BigInteger.ZERO) == 0 ){
            System.out.println("Ei ole sopiva luku...");
        }
        
        BigInteger d = e.modInverse(fii);
        
        BigInteger test = d.multiply(e).mod(fii);
        if ( test.equals(BigInteger.ONE)){
            System.out.println("d*e mod n = 1. OK Numbers");
        }
        
        /**
         * TESTING
         
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
        writeFileExponentModulus(new File("public.key"), e, n);
        writeFileExponentModulus(new File("private.key"), d, n);
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
            /*
            FileOutputStream fos = new FileOutputStream(fileToWrite);
            try (BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                bos.write(exponent.toString());
                bos.write("\n\n\n".getBytes());// Separate the two different BigIntegers.
                bos.write(modulus);
            }*/
        }
        catch ( IOException ex ){
            System.out.println("Error...");
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * @param fileToRead input file for data
     */
    private static void readFileExponentModulus(File fileToRead){
        
         try {
            /*
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            byte[] array = new byte[1024];
            FileInputStream fis = new FileInputStream(fileToRead);
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
             */
            FileReader fr = new FileReader(fileToRead);
            BufferedReader bfr = new BufferedReader(fr);
            String [] array = new String[2];
            
            for ( int i = 0; i < 2; i++ ){
                array[i] = bfr.readLine();
            }
            
            //String splitter = new String(baos);
            // Separate the two different BigIntegers.
            //String [] tmpArray = splitter.split("\n\n\n"); 
            
            // dExponent and eExponent will be the same, but doesn't matter.
            // Only one of them is used in encryption or decryption (or signing)
            
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
        
        generateKeys();
        /*
        try{
            readFileExponentModulus(new File("G:\\GITREPO\\Joulu_2014-15\\Encryption\\public.key"));
            readContents(new File("G:\\GITREPO\\Joulu_2014-15\\Encryption\\testi.txt"));
            generateInt();
        }
        catch( Exception ex){
            System.out.println(ex.getMessage());
        }
        
        try{
            readFileExponentModulus(new File("G:\\GITREPO\\Joulu_2014-15\\Encryption\\private.key"));
            generateString(new File("G:\\GITREPO\\Joulu_2014-15\\Encryption\\encrypted.txt"));
        }catch ( Exception ex ){
            System.out.println(ex.getMessage());
        }
        // ----------
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
            if ( args[0].equals("-encrypt")){*/
                System.out.println("Ecrypting...");
                
                try {
                    readFileExponentModulus(new File("C:\\git_repo\\Joulu_2014-15\\Encryption\\public.key"));
                    readContents(new File("C:\\git_repo\\Joulu_2014-15\\Encryption\\testi.txt"));
                    generateInt();
                }catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                
      //      }

        //    if ( args[0].equals("-decrypt")){
                System.out.println("Decrypting...");
                readFileExponentModulus(new File("C:\\git_repo\\Joulu_2014-15\\Encryption\\private.key"));
                generateString(new File("C:\\git_repo\\Joulu_2014-15\\Encryption\\encrypted.txt"));
           // }

      /*      if ( args[1].equals("-sign")){
                System.out.println("Signing...");
            }
        }*/
        
    } 
}
