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
    
    public static OwnBigInteger ZERO = OwnBigInteger.valueOf(0L);
    
    public static OwnBigInteger ONE = OwnBigInteger.valueOf(1L);
    
    public static OwnBigInteger TEN = OwnBigInteger.valueOf(10L);
    
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
        // TODO needs to be checked that this works correctly.
    }
    
    /**
     * Creates OwnBigInteger instance from String value
     * @param data 
     */
    public OwnBigInteger(String data){
        this.data = data.getBytes();
        // TODO
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
    
    public OwnBigInteger add(OwnBigInteger value){
        byte [] newValue = new byte[value.data.length + this.data.length];
        
        // TODO
        
        return new OwnBigInteger(newValue);
    }
    
    /**
     * Subtracts parameter value from this OwnBigInteger.
     * @param value value to subtract from this.
     * @return new OwnBigInteger representation.
     */
    public OwnBigInteger subtract(OwnBigInteger value){
        
        byte [] newValue = new byte[value.data.length+this.data.length];
        
        // TODO
        
        return new OwnBigInteger(newValue);
    }
    
    /**
     * Multiplies this OwnBigInteger with the given value.
     * @param value OwnBigInteger that multiplies this OwnBigInteger
     * @return new OwnBigInteger representation.
     */
    public OwnBigInteger multiply(OwnBigInteger value){
        
        byte [] newValue = new byte[value.data.length + this.data.length];
        
        // TODO
        
        return new OwnBigInteger(newValue);
    }
    
    /**
     * Divides this OwnBigInteger with the parameter value, and returns both the 
     * division and remaining parts in OwnBigInteger array.
     * @param value Divider.
     * @return array containing division and remainder.
     */
    public OwnBigInteger [] divideAndRemainder(OwnBigInteger value){
        
        OwnBigInteger [] array = new OwnBigInteger[2];
        
        // TODO
        
        return array;
    }
    
    /**
     * Modulus function for OwnBigIntegers
     * @param value modulus
     * @return new OwnBigInteger representation.
     */
    public OwnBigInteger mod(OwnBigInteger value){
        byte [] newValue = new byte[this.data.length + value.data.length];
        
        // TODO
        
        return new OwnBigInteger(newValue);
    }
    
    /**
     * Modulus exponent function for OwnBigInteger.
     * result is calculated like: this OwnBigInteger^exponent mod modulus
     * @param exponent exponent value.
     * @param modulus modulus value.
     * @return new OwnBigInteger representation.
     */
    public OwnBigInteger modPow(OwnBigInteger exponent, OwnBigInteger modulus){
        
        byte [] newValue = new byte[this.data.length + exponent.data.length];
        
        // TODO
        
        return new OwnBigInteger(newValue);
    }
    
    /**
     * Power function for OwnBigInteger.
     * @param exponent exponent value.
     * @return new OwnBigInteger representation.
     */
    public OwnBigInteger pow(OwnBigInteger exponent){
        
        byte [] newValue = new byte[Integer.MAX_VALUE];
        
        // TODO
        
        return new OwnBigInteger(newValue);
    }

    /**
     * Compares this OwnBigInteger with the parameter value. 
     * Returns 0 if the values are same.
     * Returns 1 if 
     * Returns -1 if
     * @param value to compare to this OwnBigInteger
     * @return integer -1, 0 or 1.
     */
    public int compareTo(OwnBigInteger value){
        
        if ( this.equals(value) ){
            return 0;
        }
        
        // TODO
        
        return 1;
    }
}


