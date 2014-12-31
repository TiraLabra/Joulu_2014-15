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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Markus
 */
public class Encryption {

    private static String input = new String();
    
    private static BigInteger muodostettuLuku;
    private static BigInteger modulusLuku = BigInteger.valueOf(256L);
    private static BigInteger luettu;
    private static BigInteger nModulus;
    private static BigInteger eExponent;
    private static BigInteger dExponent;
    
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
            muodostettuLuku = muodostettuLuku.modPow(eExponent, nModulus);
            writeFile(new File("encrypted.txt"), muodostettuLuku.toByteArray());
        }
        else 
        {
            System.out.println("No data to generate integer");    
        }
    }

private static void generateString(File fileToDecrypt)
    {   
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
            
        }catch ( Exception ex ){
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
            BufferedWriter bwr = new BufferedWriter(fwr);
            int i = 0;
            while (i < array_str.size() ){
                bwr.write(array_str.get(i));
                i++;
            }    
            bwr.close();
        }
        catch ( Exception ex ){
            System.out.println("File writing failed: " + ex.getMessage());
        }
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
    
    private static void generateKeys(){
        Random rnd = new Random(System.nanoTime());
        
        BigInteger p = BigInteger.probablePrime(512, rnd);
        BigInteger q = BigInteger.probablePrime(512, rnd);
        
        while ( p.equals(q)){
            
            q = BigInteger.probablePrime(512, rnd);
        }
        
        BigInteger n = p.multiply(q);
        
        BigInteger fii = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.valueOf(65537L);
        BigInteger d = e.modInverse(fii);
        
        writeFileExponentModulus(new File("public.key"), e.toByteArray(), n.toByteArray());
        writeFileExponentModulus(new File("private.key"), d.toByteArray(), n.toByteArray());
    }
    
    private static void writeFileExponentModulus(File fileToWrite, 
            byte [] exponent, 
            byte [] modulus ){
    
        try{
            FileOutputStream fos = new FileOutputStream(fileToWrite);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            
            bos.write(exponent);
            bos.write("\n\n\n".getBytes());
            bos.write(modulus);
            
            bos.close();
        }
        catch ( Exception ex ){
            System.out.println("Error...");
            System.out.println(ex.getMessage());
        }
    }
    
    private static void readFileExponentModulus(File fileToRead){
        
         try {
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
            String splitter = new String(baos.toByteArray());
            String [] tmpArray = splitter.split("\n\n\n");
            // private key
            dExponent = new BigInteger(tmpArray[0].getBytes());
            
            // public key
            eExponent = new BigInteger(tmpArray[0].getBytes());
            
            // common part, modulus
            nModulus = new BigInteger(tmpArray[1].getBytes());
            
        }catch ( Exception ex ){
            System.out.println("Error...");
            System.out.println(ex.getMessage());
        } 
        
    }
    
    private static void writeFile(File fileToWrite, byte [] bArray ){
        
        try{
            FileOutputStream fos = new FileOutputStream(fileToWrite);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            bos.write(bArray);

            bos.close();

        }catch( Exception ex ){
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
                System.out.println("Signing...");
            }
        }
    } 
}
