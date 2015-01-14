/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption;

import java.math.BigInteger;
import java.util.ArrayList;
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
    //private byte[] data;
    private ArrayList<Character> data;
    
    private int signum = 0; // 0 or 1 if positive, if negative -1. 

    private final static long BYTE_MODULUS = 256;
    //private final static ByteBuffer longConverter = ByteBuffer.allocate(Long.BYTES);
    /**
     * Creates OwnBigInteger instance from byte array.
     * @param data 
     */
    public OwnBigInteger(ArrayList<Character> data) {
        this.data = data;
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
        this.data = new ArrayList<>();
        for ( char c : data.toCharArray() ){
            this.data.add(c);
        };
    }
    
    /**
     * Creates a string from the contents of a character array
     * @return 
     */
    public String toString(){
        String returnValue = "";
        
        for ( char c : this.data ){
            returnValue = returnValue + c;
        }
        
        return returnValue;
    }
    /**
     * Creates OwnBigInteger from long value
     * @param value long type
     * @return OwnBigInteger instance
     */
    public static OwnBigInteger valueOf(long value){
        
        String tmp = "" + value;
        
        return new OwnBigInteger(tmp);
    }

    /**
     * Compares if two OwnBigIntegers are same or not
     * @param value
     * @return true or false
     */
    public boolean equals(OwnBigInteger value){
        
        boolean returnValue = false;
        
        if ( this.data.size() != value.data.size() ){
            return returnValue;
        }
        
        for ( int i = 0; i < this.data.size(); i++ ){
            if ( this.data.get(i) != value.data.get(i) ){
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
  
        int maxLength = wantedBitLength / 4; // 0 - 9 is 4 bits max.
        
        String tmp = "";
        for ( int i = 0; i < maxLength; i++ ){
            int next = generator.nextInt(9); // max 1 decimal integer 0 - 9. but the first has to be with or 8.
            
            if ( i == 0 ){
                next = next | 0x08; // highest bit has to be 1.
            }
            if ( i == maxLength - 1 ){
                next = next | 0x01; // Lowest bit has to be 1.
            }
            tmp = tmp + next;
        }
        
        return new OwnBigInteger(tmp);
    }
    
    /**
     * Adds parameter OwnBigInteger into this value and returns a new OwnBigInteger.
     * @param value OwnBigInteger value to be added to this.
     * @return OwnBigInteger
     */
    public OwnBigInteger add(OwnBigInteger value){
        
        StringBuilder sb = new StringBuilder();
        
        int endPos = this.data.size()-1;
        int pos1 = this.data.size()-1;
        int pos2 = value.data.size()-1;
        int tmpCarry = 0;
        
        if ( endPos < pos2 ){
            endPos = pos2;
        }
        
        while ( endPos >= 0 ){
            
            String valueWhereToAdd = "";
            if ( pos1 >= 0 ){
                valueWhereToAdd = "" + this.data.get(pos1);
            }
            
            String valueToAdd = "";
            if ( pos2 >= 0){
                valueToAdd = "" + value.data.get(pos2);
            }
            
            int first = 0;
            if ( !valueWhereToAdd.isEmpty() ){
                first = Integer.parseInt(valueWhereToAdd);
            }
            
            int second = 0;
            
            if ( !valueToAdd.isEmpty() ){
                second = Integer.parseInt(valueToAdd);
            }
            
            int tmpResult = first + second + tmpCarry;
            
            if ( tmpResult >= 10 ){
                tmpCarry = 1;
                tmpResult = tmpResult - 10; // need to remove this.
            }else{
                tmpCarry = 0;
            }
            
            sb.insert(0, tmpResult);
            
            pos1--;
            pos2--;
            endPos--;
        }
        
        if ( tmpCarry != 0 ){
            sb.insert(0, tmpCarry);
        }
        
        return new OwnBigInteger(sb.toString());
    }
    
    /**
     * Subtracts parameter value from this OwnBigInteger.
     * @param value value to subtract from this.
     * @return new OwnBigInteger representation.
     */
    public OwnBigInteger subtract(OwnBigInteger value){
       
        int pos1 = this.data.size()-1;
        int pos2 = value.data.size()-1;
        int endPos = this.data.size()-1;
        if ( endPos< pos2 ){
            endPos = pos2;
        }
        
        StringBuilder sb = new StringBuilder();
        
        while ( endPos >= 0 ){
            
            String valueWhereToSubtract = "";
            if ( pos1 >= 0 ){
                valueWhereToSubtract = "" + this.data.get(pos1);
            }
            
            String valueToSubtract = "";
            if ( pos2 >= 0){
                valueToSubtract = "" + value.data.get(pos2);
            }
            
            int first = 0;
            if ( !valueWhereToSubtract.isEmpty() ){
                first = Integer.parseInt(valueWhereToSubtract);
            }
            
            int second = 0;
            
            if ( !valueToSubtract.isEmpty() ){
                second = Integer.parseInt(valueToSubtract);
            }
            if ( first < second ){
                
                if ( pos1 != 0 ){
                    // Need to find from higher decimals where to decrease for the carry.
                    first = first+10;
                }
                                
                for ( int i = pos1-1; i > 0; i-- ){
                    String carry = "" + this.data.get(i);
                    int tmp = Integer.parseInt(carry);
                    if ( tmp > 0 ){
                        tmp--;
                        String returnCarry = "" + tmp;
                        this.data.set(i, returnCarry.charAt(0));
                        break;
                    }else{
                        String returnCarry = "9";
                        this.data.set(i, returnCarry.charAt(0));
                        // if the integer is 0, then it has be "decreased" to 9.
                    }
                }
            }
            
            int resultInt = first - second;
            sb.insert(0, resultInt);
            
            pos1--;
            pos2--;
            endPos--;
        }
        
        return new OwnBigInteger(sb.toString());
    }
    
    /**
     * Multiplies this OwnBigInteger with the given value.
     * @param value OwnBigInteger that multiplies this OwnBigInteger
     * @return new OwnBigInteger representation.
     */
    public OwnBigInteger multiply(OwnBigInteger value){

        ArrayList<String> results = new ArrayList<>();        
        
        int pos1 = data.size()-1;
        int pos2 = value.data.size()-1;
        
        int pos3 = data.size()-1;
        if ( pos3 < pos2 ){
            pos3 = pos2;
        }
        
        StringBuilder sb = new StringBuilder();
        
        /**
         *  123456 this  (this OwnBigInteger instance)
         * *000111 value (parameter)
         * -------
         */
        
        for ( ; pos2 >= 0; pos2-- ){ 
            int tmpCarry = 0;
            String multiplyValue = "";
            if ( pos2 >= 0){
                multiplyValue = "" + value.data.get(pos2);
            }
            int multiplier = 0;
            
            if ( !multiplyValue.isEmpty() ){
                multiplier = Integer.parseInt(multiplyValue);
            }
            
            for ( int i = (value.data.size()-1) - pos2; i > 0; i-- ){
                sb.insert(0, "0"); 
            // when we have moved atleast one step from the initial last 
            // position, we have to add zeroes to last positions. 
            // Before multiply results.
            }
            
            for ( ; pos1 >= 0; pos1-- ){
                
                String valueToMultiply = "";
                if ( pos1 >= 0 ){
                    valueToMultiply = "" + this.data.get(pos1);
                }
                
                int multiplyThis = 0;
                if ( !valueToMultiply.isEmpty() ){
                    multiplyThis = Integer.parseInt(valueToMultiply);
                }
                
                int result = multiplyThis * multiplier + tmpCarry;
                int remainder = result % 10;
                tmpCarry = result / 10;
                
                sb.insert(0, remainder);
            }
            if ( tmpCarry != 0 ){
                // if tmpCarry has something still, it has to be inserted as a first.
                sb.insert(0, tmpCarry); 
            }
            pos1 = data.size()-1; // reset the position
            tmpCarry = 0;
            results.add(sb.toString());
            sb.delete(0, sb.length()); // time to start next multiply result.
        }
        
        // Add all multiply results for final result. And return the final sum.
        OwnBigInteger first = new OwnBigInteger(results.remove(0));
        
        for( String s : results ){
            first = first.add(new OwnBigInteger(s));
        }
        
        return first;
    }
    
    public OwnBigInteger divide(OwnBigInteger divider){
        OwnBigInteger result = new OwnBigInteger("0");
        
        // If this is smaller than the divider, then return 0.
        // Currently checks only lengths.
        if ( divider.data.size() > this.data.size() ){
            return result;
        }
        
        return result;
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
        //byte [] newValue = new byte[this.data.length + value.data.length];
        
        // TODO
        
        return new OwnBigInteger("12");
    }
    
    /**
     * Modulus exponent function for OwnBigInteger.
     * result is calculated like: this OwnBigInteger^exponent mod modulus
     * @param exponent exponent value.
     * @param modulus modulus value.
     * @return new OwnBigInteger representation.
     */
    public OwnBigInteger modPow(OwnBigInteger exponent, OwnBigInteger modulus){
        
        //byte [] newValue = new byte[this.data.length + exponent.data.length];
        
        // TODO
        
        return new OwnBigInteger("12");
    }
    
    /**
     * Power function for OwnBigInteger.
     * @param exponent exponent value.
     * @return new OwnBigInteger representation.
     */
    public OwnBigInteger pow(OwnBigInteger exponent){
        
        //byte [] newValue = new byte[Integer.MAX_VALUE];
        
        // TODO
        
        return new OwnBigInteger("12");
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
        //byte [] newValue = new byte[Integer.MAX_VALUE];
        
        
        return new OwnBigInteger("12");
    }
}


