/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption;

import java.nio.ByteBuffer;
import java.util.Random;

/**
 * Part of Encryption.
 * @author Markus Pahkamaa
 */
public class OwnBigInteger {
    
    // Content
    private byte[] data;

    private final static long BYTE_MODULUS = 256;
    private final static ByteBuffer longConverter = ByteBuffer.allocate(Long.BYTES);
    /**
     * Creates OwnBigInteger instance from byte array.
     * @param data 
     */
    public OwnBigInteger(byte[] data) {
        this.data = data;
    }
    
    /**
     * Creates OwnBigInteger from long value
     * @param value long type
     * @return OwnBigInteger instance
     */
    public static OwnBigInteger valueOf(long value){
        
        longConverter.putLong(value);
        
        byte [] array = longConverter.array();
        longConverter.clear();
        
        return new OwnBigInteger(array);
    }
    
    /**
     * Compares if two OwnBigIntegers are same or not
     * @param value
     * @return true or false
     */
    public boolean equals(OwnBigInteger value){
        
        boolean returnValue = false;
        
        if ( this.data.length != value.data.length ){
            return returnValue;
        }
        int i = 0;
        while ( i < this.data.length ){
            
            if ( this.data[i] != value.data[i] ){
                return returnValue;
            }
        }
        returnValue = true;
        
        return returnValue;
    }
    
    /**
     * Generates a probable prime of wanted length.
     * @param wantedBitLength, How many bit length the new OwnBigInteger value should be.
     * @param generator, Random generator that is used to generate those bits.
     * @return 
     */
    public OwnBigInteger probablePrime(int wantedBitLength, Random generator){
        
        int length = wantedBitLength / 8;
        int remains = wantedBitLength % 8;
        
        if ( remains > 0 ){
            length++;
        }
        
        byte [] array = new byte[length];
        generator.nextBytes(array);
        
        array[0] = (byte) (array[0] | 0x01); // set the lowest bit to 1.
        array[length-1] = (byte) (array[length-1] & remains); // set bits over remains to zero.
        array[length-1] = (byte) (array[length-1] | (1 << remains)); // shift bit 1 enought times to left.
        
        
        return new OwnBigInteger(array);
    }
}
