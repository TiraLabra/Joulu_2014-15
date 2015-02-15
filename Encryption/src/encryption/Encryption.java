/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
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
    //private static BigInteger muodostettuLuku;
    private static NewOwnBigInteger muodostettuLuku;
    
    // Small modulus for transforming bigInteger to a String or vice versa.
    //private static final BigInteger modulusLuku = BigInteger.valueOf(256L);
    private static final NewOwnBigInteger modulusLuku = NewOwnBigInteger.valueOf(256L);
    
    // Encrypted data read from encrypted file.
    //private static BigInteger luettu;
    private static NewOwnBigInteger luettu;
    
    // Common Big modulus for public and private keys.
    //private static BigInteger nModulus;
    private static NewOwnBigInteger nModulus;
    
    // Public exponent for encryption, currently it's 17.
    //private static BigInteger eExponent;
    private static NewOwnBigInteger eExponent;
    
    // Private exponent for decryption.
    //private static BigInteger dExponent;
    private static NewOwnBigInteger dExponent;
    
    // Private filehandler for handling reading and writing.
    private static FileHandler fileHandler;
    
    /**
     * Generates integers from the read data.
     * NOT IN USE.
     */
    /*
    private static void generateInt2(){
        if ( !input.isEmpty() ){
            int i = 0; // character position
            int j = 0; // exponent
            //BigInteger [] array = new BigInteger[10]; // start from 10, increase if needed.
            NewOwnBigInteger [] array = new NewOwnBigInteger[10];
            int k = 0; // array position, how many big integers have been created.
            
            while ( i < input.length() ){
                
                String ch = "" + input.charAt(i);
                //BigInteger tmp = new BigInteger(ch.getBytes());
                NewOwnBigInteger tmp = new NewOwnBigInteger();
                //BigInteger power = modulusLuku;
                NewOwnBigInteger power = modulusLuku;
                power = power.pow(j);
                j++;
                tmp = tmp.multiply(power);
                
                if ( array[k] == null ){
                    array[k] = new BigInteger(tmp.toByteArray());
                }else if ( array[k].add(tmp).compareTo(nModulus) < 0){
                    array[k] = array[k].add(tmp);
                }else{
                    // time to start new BigInteger next round.   
                    // tmp cannot be inserted into a new position
                    // because it's wrong power.
                    if ( k >= array.length-1 ){
                        // needs to create new bigger array.
                        BigInteger [] tmpArray = new BigInteger[k+10];
                        for ( int pos = 0; pos < array.length; pos++ ){
                            tmpArray[pos] = array[pos];
                        }
                        array = tmpArray;
                    }
                    j=0;
                    k++; // new biginteger creation needed to new position
                    i--; // we need to stay in the same position next round.
                }
                i++;
            }
            
           fileHandler.writeFile2(new File("encrypted.txt"), array, k);
        }
    }
    */
    /**
     * Generates the integer from read data.
     */
    private static void generateInt(){
        if ( !input.isEmpty() ){
            int i = 0;
            
            while ( i < input.length() ){
                
                //String ch = ""+input.charAt(i);
                char ch = input.charAt(i);
                NewOwnBigInteger tmp = NewOwnBigInteger.valueOf((long)ch);
                //BigInteger tmp = new BigInteger(ch.getBytes());
                
                //BigInteger power = modulusLuku;
                NewOwnBigInteger power = modulusLuku;
                power = power.pow(NewOwnBigInteger.valueOf((long)i));
                tmp = tmp.multiply(power);
                if ( muodostettuLuku == null ){
                    muodostettuLuku = new NewOwnBigInteger(tmp);//new BigInteger(tmp.toByteArray());
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
            fileHandler.writeFile(new File("encrypted.txt"), muodostettuLuku);
        }
        else 
        {
            System.out.println("No data to generate integer");    
        }
    }

    /** 
     * Reads the integers into strings and makes them BigIntegers for 
     * decrypting.
     * NOT IN USE yet
     * @param fileToDecrypt, file containing encrypted integers.
     */
    /*
    private static void generateString2(File fileToDecrypt){
        String [] array = new String [10];
        int i = 0; // position counter
        try{
            FileReader fr = new FileReader(fileToDecrypt);
            BufferedReader bfr = new BufferedReader(fr);
            String tmp = bfr.readLine();
            while ( tmp != null ){
                
                if ( i > array.length ){
                    String [] tmpArray = new String[i + 10];
                    for ( int j = 0; j < array.length; j++){
                        tmpArray[j] = array[j];
                    }
                    array = tmpArray;
                }
                
                array[i] = tmp;
                tmp = bfr.readLine();
                i++;
            }
        }
        catch(IOException ex){
            System.out.println("Error...");
            System.out.println(ex.getMessage());
        }
        
        if ( i > 0 ){
            String decryptedText = "";
            
            for ( int j = 0; j < i; j++ ){
                decryptedText = decryptedText + 
                        generateStringFromBigIntegerString(array[j]);
            }
            
            fileHandler.writeDecrypted(decryptedText);
        }
    }
    */
    /**
     * Decrypts the data from integer strings.
     * @param longNumber
     * @return String generated from encrypted data
     */
    private static String generateStringFromBigIntegerString(String longNumber){
        String returnString = "";
        //BigInteger bigInt = new BigInteger(longNumber);
        NewOwnBigInteger bigInt = new NewOwnBigInteger(longNumber);
        
        bigInt = bigInt.modPow(dExponent, nModulus);
        
        while ( true ){
            NewOwnBigInteger jakojaannos = bigInt.mod(modulusLuku);
//            BigInteger jakojaannos = bigInt.mod(modulusLuku);
            String tmp = jakojaannos.toString();
            bigInt = bigInt.subtract(jakojaannos);
            returnString = returnString + tmp;
            bigInt = bigInt.divide(modulusLuku);
            if ( bigInt.equals(BigInteger.valueOf(0L))){
                break;
            }
        }
        return returnString;
    }
    
    /**
     * Decrypts the files content.
     * @param fileToDecrypt file to decrypt.
     */
    private static void generateString(File fileToDecrypt){   
        
        String array_str = "";
        
        //BigInteger dataFromFile = fileHandler.readEncryptedData(fileToDecrypt);
        NewOwnBigInteger dataFromFile = fileHandler.readEncryptedData(fileToDecrypt);
        
        if ( dataFromFile != null ){
            
            
            dataFromFile = dataFromFile.modPow(dExponent, nModulus);
            
            while ( true ){
                //BigInteger jakojaannos = dataFromFile.mod(modulusLuku);
                NewOwnBigInteger jakojaannos = dataFromFile.mod(modulusLuku);
                
                int tmp = Integer.parseInt(jakojaannos.toString());//new String(jakojaannos.toByteArray());
                dataFromFile = dataFromFile.subtract(jakojaannos);
                array_str = array_str + (char)tmp;
                dataFromFile = dataFromFile.divide(modulusLuku);
                if ( dataFromFile.equals(NewOwnBigInteger.ZERO)){
                    break;
                }
            }
        }
    //  array_str = removePadding(array_str);  
        fileHandler.writeDecrypted(array_str);
    }
    
    /**
     * Adds random padding to the input data. 
     * 2 characters to beginning of string and to ending of string
     */
    private static void addPadding(){
        if ( !input.isEmpty()){
            Random rnd = new Random(System.nanoTime());
            
            char first = (char) (rnd.nextInt() % 256);
            char second = (char) (rnd.nextInt() % 256);
            String tmpBegining = "" + first + second;
            
            char secondLast = (char) (rnd.nextInt() % 256);
            char last = (char) (rnd.nextInt() % 256);
            String tmpEnding = "" + secondLast + last;
            
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
        String returnValue = "";
        
        if ( !paddedText.isEmpty()){
            // Beginning position should be 2 and correct last position should be
            // length - 2.
            returnValue = paddedText.substring(2, paddedText.length()-2);
        }
        
        return returnValue;
    }
    
    /**
     * Generates BigInteger keys.
     * public exponent e, picked from wikipedia, some nice primenumber.
     * private exponent d, d = e modInverse ((p-1)*(q-1))
     * and modulus n. n = p * q . (two probable prime numbers)
     * Writes them to a files private.key and public.key
     */
    private static void generateKeys(){
        
        boolean generationFailed = false;
        
        Random rnd = new Random(System.nanoTime());
        
        NewOwnBigInteger p = NewOwnBigInteger.probablePrime(512, rnd);//BigInteger p = BigInteger.probablePrime(512, rnd);
        NewOwnBigInteger q = NewOwnBigInteger.probablePrime(512, rnd); //BigInteger q = BigInteger.probablePrime(512, rnd);
        
        while ( p.equals(q)){
            
            q = NewOwnBigInteger.probablePrime(512, rnd);
        }
        
        //BigInteger n = p.multiply(q);
        NewOwnBigInteger n = p.multiply(q);
        
        //BigInteger fii = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        NewOwnBigInteger fii = p.subtract(NewOwnBigInteger.ONE).multiply(q.subtract(NewOwnBigInteger.ONE));
        
        NewOwnBigInteger e = NewOwnBigInteger.valueOf(17L);
        //BigInteger e = BigInteger.valueOf(17L);//BigInteger.valueOf(65537L);
        
        //BigInteger [] debugVariable = fii.divideAndRemainder(e);
        NewOwnBigInteger [] debugVariable = fii.divideAndRemainder2(e);
        
        if ( debugVariable[0].compareTo(NewOwnBigInteger.ZERO) == 0){
            generationFailed = true;
        }
        
        if ( debugVariable[1].compareTo(NewOwnBigInteger.ZERO) == 0 ){
            generationFailed = true;
        }
        
        //BigInteger d = null;
        NewOwnBigInteger d = null;
        try{
            d = e.modInverse(fii);    
            
        }catch( Exception ex ){
            generationFailed = true;
            System.out.println(ex.getMessage());
            System.out.println("Please wait, trying again...");
        }
        
        //BigInteger test = null;
        NewOwnBigInteger test = null;
        if ( d != null ){
            test = d.multiply(e).mod(fii);
        }

        if ( test != null && test.equals(NewOwnBigInteger.ONE)){
            System.out.println("d*e mod n = 1. OK Numbers");
        }
        
        if ( generationFailed ){
            // recursion needed if the key generation 
            // fails so that key-pair is generated
            generateKeys(); 
        }else{
            fileHandler.writeFileExponentModulus(new File("public.key"), e, n);
            fileHandler.writeFileExponentModulus(new File("private.key"), d, n);
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
     * Read the exponent and modulus from given file. Private exponent d and
     * public exponent e variables will be same, but those are used in different
     * situations.
     * @param fileToRead
     */
    public static void readFileExponentModulus(File fileToRead){
        
         try {
            FileReader fr = new FileReader(fileToRead);
            BufferedReader bfr = new BufferedReader(fr);
            String [] array = new String[2];
            
            for ( int i = 0; i < 2; i++ ){
                array[i] = bfr.readLine();
            }
            
            // private exponent
            dExponent = new NewOwnBigInteger(array[0]);
            // public exponent
            eExponent = new NewOwnBigInteger(array[0]);
            // common modulus
            nModulus = new NewOwnBigInteger(array[1]);
            
        }catch ( IOException ex ){
            System.out.println("Error...");
            System.out.println(ex.getMessage());
        }    
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        fileHandler = new FileHandler();
        /*
        if ( args.length == 0 ){
            System.out.println("Generate public and private keys with command: -generate_keys");
            System.out.println("Encrypt the file with command: -encrypt <public.key> <file>");
            System.out.println("Decrypt the file with command: -decrypt <private.key> <file>");
            System.out.println("Sign the file with command: -sign <private.key> <file>");
            return; 
        }
        */
        //if ( args.length == 1 ){
        //    if ( args[0].equals("-generate_keys")){
        //        System.out.println("Generating keys.");
        //        generateKeys();
        //    }
        //}
        try{
            readFileExponentModulus(new File("G:\\GITREPO\\Joulu_2014-15\\Encryption\\public.key"));
            input = fileHandler.readContents(new File("G:\\GITREPO\\Joulu_2014-15\\Encryption\\testi.txt"));
            generateInt();
            readFileExponentModulus(new File("G:\\GITREPO\\Joulu_2014-15\\Encryption\\private.key"));
            generateString(new File("G:\\GITREPO\\Joulu_2014-15\\Encryption\\encrypted.txt"));   
        }catch (Exception ex){
            
        }

        //readFileExponentModulus(new File("G:\\GITREPO\\Joulu_2014-15\\Testausta\\private.key"));
        //generateString(new File("G:\\GITREPO\\Joulu_2014-15\\Testausta\\encrypted.txt"));   
        /*
        if ( args.length == 3 ){
            if ( args[0].equals("-encrypt")){
                System.out.println("Ecrypting...");
               
                try {
                    //readFileExponentModulus(new File("G:\\GITREPO\\Joulu_2014-15\\Encryption\\public.key"));
                    //readContents(new File("G:\\GITREPO\\Joulu_2014-15\\Encryption\\dist\\README.TXT"));
                    readFileExponentModulus(new File(args[1]));
                    
                    //input = fileHandler.readContents(new File("G:\\GITREPO\\Joulu_2014-15\\Encryption\\testi.txt"));
                    input = fileHandler.readContents(new File(args[2]));
                    //generateInt2();
                    generateInt();
                }catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }  
            }

            if ( args[0].equals("-decrypt")){
                System.out.println("Decrypting...");
            //    readFileExponentModulus(new File("G:\\GITREPO\\Joulu_2014-15\\Encryption\\private.key"));
            //    generateString(new File("G:\\GITREPO\\Joulu_2014-15\\Encryption\\encrypted.txt"));                
                readFileExponentModulus(new File(args[1]));

                //generateString2(new File(args[2]));
                //generateString2(new File("G:\\GITREPO\\Joulu_2014-15\\Encryption\\encrypted.txt"));                
                generateString(new File(args[2]));
            }

            if ( args[1].equals("-sign")){
                System.out.println("Signing... Not yet implemented");
            }
        } */
    } 
}