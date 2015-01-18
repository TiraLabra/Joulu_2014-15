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
        this.data = new ArrayList<>();
        this.data.addAll(value.data);
    }
    
    /**
     * Creates OwnBigInteger instance from String value
     * @param data 
     */
    public OwnBigInteger(String data){
        
        if ( data.length() == 0 ){
            // should do something in this case.
        }
        
        if ( data.length() > 1 ){
            // if length is more than 1, then remove the beginning 0's.
            int beginPos = 0;
            for ( int i = 0; i < data.length(); i++ ){
                if ( data.charAt(i) == '0' ){
                    beginPos++;
                }else{
                    break;
                }
            }
            data = data.substring(beginPos);
        }
        
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
                                
                for ( int i = pos1-1; i >= 0; i-- ){
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
            // if the result in the first position is 0, we don't add it into the string.
            // we should also check that first position doesn't contain zeroes.
            if ( endPos == 0 && resultInt == 0 ){
                
            }
            else{           
                sb.insert(0, resultInt);
            }
            
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
        
        // needs to compare if divider is bigger than this.
        // TODO.
        OwnBigInteger tmp = new OwnBigInteger(this);
        
        while ( tmp.compareTo(divider) >= 0 ){
            tmp = tmp.subtract(divider);
            result = result.add(OwnBigInteger.ONE);
        }
        
        return result;
    }
    
    /**
     * Divides this OwnBigInteger with the parameter value, and returns both the 
     * division and remaining parts in OwnBigInteger array. Array[0] contains the 
     * division result. Array[1] contains the remainder.
     * @param value Divider.
     * @return array containing division and remainder.
     */
    public OwnBigInteger [] divideAndRemainder(OwnBigInteger value){
        
        OwnBigInteger [] array = new OwnBigInteger[2];
        
        // TODO
        array[0] = this.divide(value);
        
        if ( array[0].equals(ZERO)){
            array[1] = this;
        }else{
            // formula for remainder: this - array[0] * value
            array[1] = this.subtract(value.multiply(array[0]));
        }
        
        return array;
    }
    
    /**
     * Modulus function for OwnBigIntegers
     * @param value modulus
     * @return new OwnBigInteger representation.
     */
    public OwnBigInteger mod(OwnBigInteger value){
        
        OwnBigInteger [] array = divideAndRemainder(value);
        
        return array[1];
    }
    
    /**
     * Modulus exponent function for OwnBigInteger.
     * result is calculated like: this OwnBigInteger^exponent mod modulus
     * @param exponent exponent value.
     * @param modulus modulus value.
     * @return new OwnBigInteger representation.
     */
    public OwnBigInteger modPow(OwnBigInteger exponent, OwnBigInteger modulus){
        
        String expString = convertToBinary(exponent);
       
        OwnBigInteger result = new OwnBigInteger(ONE);
        OwnBigInteger tmp = new OwnBigInteger(this);
        
        int pos = 0;
        
        for ( ; pos < expString.length(); pos++ ){
            result = result.multiply(result).mod(modulus);
            if ( expString.charAt(pos) == '1' ){
                result = result.multiply(tmp).mod(modulus);
            }
        }
        
        /*
        OwnBigInteger base = new OwnBigInteger(this);
        base = base.mod(modulus);
        
        OwnBigInteger tmpExp = new OwnBigInteger(exponent);
        int position = expString.length()-1;
        while ( position > 0 ){
            
            OwnBigInteger [] array = tmpExp.divideAndRemainder(OwnBigInteger.valueOf(2L));
            tmpExp = array[0];
            OwnBigInteger modResult = array[1];
            if ( modResult.equals(ONE)){
                result = result.multiply(base).mod(modulus);
            }
            
            position--;
            base = base.multiply(base).mod(modulus);
            //tmpExp = OwnBigInteger.convertToDecimal(expString.substring(0, position));
        }
        */
        return result;
    }
    
    /**
     * Power function for OwnBigInteger.
     * @param exponent exponent value.
     * @return new OwnBigInteger representation.
     */
    public OwnBigInteger pow(OwnBigInteger exponent){
        
        if ( exponent.equals(ZERO)){
            return ONE;
        }
        
        if ( exponent.equals(ONE)){
            return this;
        }
        
        OwnBigInteger base = new OwnBigInteger(this);
        OwnBigInteger result = new OwnBigInteger(ONE);
        OwnBigInteger expRes = new OwnBigInteger(exponent);
        
        while ( !expRes.equals(ZERO) ){
            
            OwnBigInteger [] tmpres = expRes.divideAndRemainder(OwnBigInteger.valueOf(2L));
            expRes = tmpres[0];
            if ( tmpres[1].equals(ONE)){
                result = result.multiply(base);
            }
            /*else if ( tmpres[1].equals(OwnBigInteger.valueOf(2L))){
                // Means that expRes = 0
                // 1 / 2 = 0, but returns the 2 as a remainder.
                result = result.multiply(base);
            }*/
            
            base = base.multiply(base);
        }
        
        return result;
    }

    /**
     * Compares this OwnBigInteger with the parameter value. 
     * Returns 0 if the values are same.
     * Returns 1 if this is bigger
     * Returns -1 if this is smaller
     * @param value to compare to this OwnBigInteger
     * @return integer -1, 0 or 1.
     */
    public int compareTo(OwnBigInteger value){
        
        if ( this.equals(value) ){
            return 0;
        }
        
        // Currently negative values aren't supported
        if ( this.data.size() > value.data.size() ){
            return 1;
        }
        
        if ( this.data.size() < value.data.size() ){
            return -1;
        }
        
        int returnValue = 0;
        // both values are equally long!
        for ( int i = 0; i < this.data.size(); i++ ){
            
            // This data[i] number
            String thisPos = "";
            if ( i >= 0 ){
                thisPos = "" + this.data.get(i);
            }
                
            int thisValue = 0;
            if ( !thisPos.isEmpty() ){
                thisValue = Integer.parseInt(thisPos);
            }
            
            // Parameter data[i] number
            String paramPos = "";
            if ( i >= 0 ){
                paramPos = "" + value.data.get(i);
            }
                
            int paramValue = 0;
            if ( !paramPos.isEmpty() ){
                paramValue = Integer.parseInt(paramPos);
            }
            
            if ( thisValue > paramValue ){
                returnValue = 1;
                break;
            }else if (thisValue < paramValue){
                returnValue = -1;
                break;
            }
            // No need for else. if thisValue == paramValue, we continue.
        }
        
        return returnValue;
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
    
    /**
     * Returns the greatest common divisor of this and value.
     * @param value
     * @return Greatest common divisor.
     */
    public OwnBigInteger gcd(OwnBigInteger value){
        
        OwnBigInteger tmp = new OwnBigInteger(this);
        OwnBigInteger tmp2 = new OwnBigInteger(value);
        
        OwnBigInteger [] array = null;
        
        do{
            if ( tmp.compareTo(tmp2) > 0 )
            {
                array = tmp.divideAndRemainder(tmp2);
                tmp = tmp2;
                tmp2 = array[1];
            }
            else if ( tmp.compareTo(tmp2) < 0 )
            {
                array = tmp2.divideAndRemainder(tmp);
                tmp2 = tmp;
                tmp = array[1];
            } 
            
        }while ( !array[0].equals(ZERO) );
        
        return array[1];
    }
    
    /**
     * Converts a given OwnBigInteger to binary representation.
     * This is used for modular exponention to make it faster.
     * @param valueToConvert
     * @return Binary representation of the parameter value.
     */
    private String convertToBinary(OwnBigInteger valueToConvert){
        
        String returnValue = "";
        
        OwnBigInteger tmp = new OwnBigInteger(valueToConvert);
        
        OwnBigInteger [] array;
        OwnBigInteger divider = OwnBigInteger.valueOf(2L);
        
        while ( !tmp.equals(ZERO) ){
            
            array = tmp.divideAndRemainder(divider);
            if ( array[1].equals(ONE) ){
                returnValue = "1" + returnValue;
            }else {
                returnValue = "0" + returnValue;
            }
            
            tmp = array[0];
        }
        
        return returnValue;
    }
    
    /**
     * Converts Binary string to decimal representation OwnBigInteger.
     * @param valueToConvert
     * @return OwnBigInteger, decimal representation of the parameter value.
     */
    private static OwnBigInteger convertToDecimal(String valueToConvert){
        
        OwnBigInteger result = new OwnBigInteger(ZERO);
        OwnBigInteger two = OwnBigInteger.valueOf(2L);
        
        long exponent = 0;
        for ( int i = valueToConvert.length() - 1; i > 0; i--  ){
        
            if ( valueToConvert.charAt(i) == '1' ){
                OwnBigInteger tmp = two.pow(OwnBigInteger.valueOf(exponent));
                
                result = result.add(tmp);
            }
            exponent++;
        }
        
        return result;
    }
}


