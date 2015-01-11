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
    //private final static ByteBuffer longConverter = ByteBuffer.allocate(Long.BYTES);
    /**
     * Creates OwnBigInteger instance from byte array.
     * @param data 
     */
    public OwnBigInteger(byte[] data) {
        this.data = data;
        // TODO needs to be checked that this works correctly.
    }
    
    /** 
     * Creates OwnBigInteger instance from another one, useful for long value.
     * @param value 
     */
    public OwnBigInteger(OwnBigInteger value){
        this.data = value.data;
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
        
        ByteBuffer longConverter = ByteBuffer.allocate(Long.BYTES);
        
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
    
    /**
     * Adds parameter OwnBigInteger into this value and returns a new OwnBigInteger.
     * @param value OwnBigInteger value to be added to this.
     * @return OwnBigInteger
     */
    public OwnBigInteger add(OwnBigInteger value){
        int maxLength = this.data.length + 1;
        
        if ( maxLength < value.data.length ){
            maxLength = value.data.length + 1;
        }
        
        byte [] newValue = new byte[maxLength];
        
        // Starting position is in the end of byte[] array.
        int pos1 = data.length-1;
        int pos2 = value.data.length-1;
        int pos3 = maxLength-1;
        byte tmpCarry = 0x0;
        
        for ( int i = pos3; i > 0; i--, pos1--, pos2-- ){
            
            int tmp1 = 0; 
            int tmp2 = 0;
            
            if ( pos1 >= 0 ) {
                tmp1 = data[pos1];
            }
            
            if ( pos2 >= 0 ){
                tmp2 = value.data[pos2];
            }
            
            int tmp3 = tmp1 + tmp2 + tmpCarry;
            
            newValue[i] = (byte)(tmp3 & 0xff);
            tmp3 >>= 8;
            tmpCarry = (byte)(tmp3 & 0xff);
        }
        
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
        int pos1 = value.data.length-1;
        int pos2 = this.data.length - value.data.length;
        if ( pos2 < 0 ){
            // result will be negative. so maybe we should do addition to value instead.
            return value.add(this);
        }
        
        int j;
        byte tmpCarry;
        for ( int i = pos2; i < value.data.length; i++ ){
            if ( this.data[i] < value.data[i] ){
                // Need a carry from before!!!
                // This can have huge recursion effects.
                // Stop moving towards 0 when the index position doesn't contain 0.
                for ( j = i-1; ;j-- ){
                    
                    if ( this.data[j] > 0 ){
                        this.data[j] = this.data[j]--;
                        tmpCarry = (byte)0xFF;
                        break;
                    }
                    else {
                        this.data[j] = (byte)0xFF;
                    }
                }
                newValue[i] = (byte)(tmpCarry - value.data[i]);
            }else{
                newValue[i] = (byte)(this.data[i] - value.data[i]);
            }
        }
        
        return new OwnBigInteger(newValue);
    }
    
    /**
     * Multiplies this OwnBigInteger with the given value.
     * @param value OwnBigInteger that multiplies this OwnBigInteger
     * @return new OwnBigInteger representation.
     */
    public OwnBigInteger multiply(OwnBigInteger value){
        
        byte [] newValue = new byte[value.data.length + this.data.length];
        
        OwnBigInteger tmp = new OwnBigInteger(this);
        OwnBigInteger multiplier = OwnBigInteger.ONE;
        
        
        // TODO
        while ( !multiplier.equals(value)){
            tmp = tmp.add(tmp);
            multiplier.add(multiplier);
            
            if ( multiplier.add(multiplier).compareTo(value) > 1){
                // going over. need to reset something, or check how much we are going over.
                // so we can continue calculating those sums.
            }
        }
        
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
    
    /**
     * Returns OwnBigInteger which is this^-1 mod value.
     * @param value modulus value.
     * @return new OwnBigInteger value.
     */
    public OwnBigInteger modInverse(OwnBigInteger value){
        byte [] newValue = new byte[Integer.MAX_VALUE];
        
        
        return new OwnBigInteger(newValue);
    }
}


