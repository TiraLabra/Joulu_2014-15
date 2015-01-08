/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

/**
 *
 * @author Markus
 */
public class FileHandler {
    
    /**
     * @param fileToWrite file where data is written into
     * @param encryptedInt to write into the file
     */
    public void writeFile(File fileToWrite, BigInteger encryptedInt ){
        
        try{
            FileWriter fw = new FileWriter(fileToWrite);
            try(BufferedWriter bwr = new BufferedWriter(fw)){
                bwr.write(encryptedInt.toString());
                bwr.close();
            }
        }catch( IOException ex ){
            System.out.println("Error, something happened.");
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * NOT YET USED.
     * @param fileToWrite
     * @param array containing bigIntegers
     * @param count how many bigIntegers there are to write.
     */
    public void writeFile2(File fileToWrite, BigInteger[] array, int count){
        
        try{
            FileWriter fw = new FileWriter(fileToWrite);
            try(BufferedWriter bwr = new BufferedWriter(fw)){
                for ( int i = 0; i < count; i++ ){
                    bwr.write(array[i].toString());
                    bwr.write("\n");
                }
                bwr.close();
            }
        }catch( IOException ex ){
            System.out.println("Error, something happened.");
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * @param fileToWrite file where data is written into
     * @param exponent exponent bytearray
     * @param modulus modulus bytearray
     */
    public void writeFileExponentModulus(File fileToWrite, 
            BigInteger exponent, 
            BigInteger modulus ){
        try{
            FileWriter fwr = new FileWriter(fileToWrite);
            try(BufferedWriter bfw = new BufferedWriter(fwr)){
                bfw.write(exponent.toString());
                bfw.write("\n");
                bfw.write(modulus.toString());
                bfw.write("\n");
                bfw.close();
            }
        }
        catch ( IOException ex ){
            System.out.println("Error...");
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Writes a decrypted message into a file.
     * @param message to write into a file.
     */
    public void writeDecrypted(String message){
        File fout = new File("decrypted.txt");
        try{
            FileWriter fwr = new FileWriter(fout);
            try (BufferedWriter bwr = new BufferedWriter(fwr)) {
                bwr.write(message);
                bwr.close();
            }
        }
        catch ( IOException ex ){
            System.out.println("File writing failed: " + ex.getMessage());
        }
    }
    
    /**
     * Reads the contents of a single file into a single string
     * @param fileToRead file to read from.
     * @return contents of a file in a single string
     * @throws Exception 
     */
    public String readContents(File fileToRead) throws Exception{
        String returnValue="";
        if ( fileToRead.canRead()) {
            FileReader fr = new FileReader(fileToRead);
            BufferedReader bfr = new BufferedReader(fr);
            String tmp = bfr.readLine();
            while ( tmp != null ){
                returnValue = returnValue + tmp;
                tmp = bfr.readLine();
            }
           // addPadding();
        }
        else{
            throw new Exception("File cannot be read!");
        }
        return returnValue;
    }
    
    /**
     * Reads BigInteger in String format and returns that String as a BigInteger
     * @param fileToRead file that contains encrypted data
     * @return BigInteger that was created from the file content
     */
    public BigInteger readEncryptedData(File fileToRead){
        
        BigInteger returnValue = null;
        
        try {
            FileReader fr = new FileReader(fileToRead);
            String read;
            try (BufferedReader bfr = new BufferedReader(fr)) {
                read = bfr.readLine();
            }

            returnValue = new BigInteger(read);
            
        }catch ( IOException ex ){
            System.out.println("Error...");
            System.out.println(ex.getMessage());
        }  
        
        return returnValue;
    }
}
