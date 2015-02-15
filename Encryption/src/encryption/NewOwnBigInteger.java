/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption;
import java.util.Random;

/**
 *
 * @author Markus
 */
public class NewOwnBigInteger {
    
    public static NewOwnBigInteger ZERO = NewOwnBigInteger.valueOf(0L);
    
    public static NewOwnBigInteger ONE = NewOwnBigInteger.valueOf(1L);
    
    public static NewOwnBigInteger TEN = NewOwnBigInteger.valueOf(10L);
    
    private int signum = 0; // 0 or greater for positives, -1 for negatives.
    
    private String data;
    
    /**
     * Creates NewOwnBigInteger instance from String.
     * @param value 
     */
    public NewOwnBigInteger(String value){
        if ( value.length() == 0){
            this.data = "0";
        }
        else if ( value.contains("-")){
            this.signum = -1;
            this.data = value.replaceFirst("-", "");
            
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
        }
        else{
            
            if ( value.length() > 1 ){
                // if length is more than 1, then remove the beginning 0's.
                int beginPos = 0;
                for ( int i = 0; i < value.length(); i++ ){
                    if ( value.charAt(i) == '0' ){
                        beginPos++;
                    }else{
                        break;
                    }
                }
                data = value.substring(beginPos);
            }else
            {
                this.data = value;
            }            
        }
    }
    
    /** 
     * Creates NewOwnBigInteger instance from another one, useful for long value.
     * @param value 
     */
    public NewOwnBigInteger(NewOwnBigInteger value){
        this.data = value.data;
        this.signum = value.signum;
    }
    
    /**
     * Creates a string representation from NewOwnBigInteger content
     * @return - sign if the signum is -1. Otherwise it just returns the data.
     */
    public String toString(){
        String returnvalue = "";
        
        if ( this.signum == -1 ){
            returnvalue = returnvalue + "-";
        }
        
        returnvalue = returnvalue + this.data;
        
        return returnvalue;
    }
    
    /**
     * Creates absolute value representation of NewOwnBigInteger
     * @return new NewOwnBigInteger value, that's signum is 0 (positive).
     */
    public NewOwnBigInteger abs(){
        
        NewOwnBigInteger tmp = new NewOwnBigInteger(this);
        tmp.signum = 0;
        
        return tmp;
    }
    
    /**
     * Creates OwnBigInteger from long value
     * @param value long type
     * @return OwnBigInteger instance
     */
    public static NewOwnBigInteger valueOf(long value){
        
        String tmp = "" + value;
        
        return new NewOwnBigInteger(tmp);
    }
    
    /**
     * Compares if two NewOwnBigInteger are same or not
     * @param value
     * @return true or false
     */
    public boolean equals(NewOwnBigInteger value){
        
        boolean returnValue = false;
        
        if ( this.signum != value.signum ){
            return returnValue;
        }
        
        int compare = this.data.compareTo(value.data);
        
        if ( compare == 0 ){
            returnValue = true;
        }
        
        return returnValue;
    }
    
    /**
     * Generates a probable prime of wanted length.
     * @param wantedBitLength, How many bit length the new NewOwnBigInteger value should be.
     * @param generator, Random generator that is used to generate those bits.
     * @return 
     */
    public static NewOwnBigInteger probablePrime(int wantedBitLength, Random generator){
  
        int maxLength = wantedBitLength / 4; // 0 - 9 is 4 bits max.
        
        String tmp = "";
        for ( int i = 0; i < maxLength; i++ ){
            int next = generator.nextInt(9); // max 1 decimal integer 0 - 9. but the first has to be with or 8.
            
            if ( i == 0 ){
                next = next | 0x08; // highest bit has to be 1.
            }
            if ( i == maxLength - 1 ){
                next = next | 0x01; // Lowest bit has to be 1.
                if ( next == 5 ){ // 5 is not good for last number
                    next = generator.nextInt(9) | 0x01;
                }
            }
            tmp = tmp + next;
        }
        
        return new NewOwnBigInteger(tmp);
    }
    
    /**
     * Adds parameter NewOwnBigInteger into this value and returns a new NewOwnBigInteger.
     * @param value NewOwnBigInteger value to be added to this.
     * @return NewOwnBigInteger
     */
    public NewOwnBigInteger add(NewOwnBigInteger value){
        // -this + -value -> abs this + value (not a problem)
        // this + -value -> abs this.subtract(value)
        if ( this.signum == -1 && value.signum == 0 ){
            if ( this.abs().compareTo(value) > 0 ){
                NewOwnBigInteger result = this.abs().subtract(value);
                result.signum = -1;
                return result;
            }else{
                return value.subtract(this.abs());
            }

        }
        
        if ( this.signum == 0 && value.signum == -1 ){
            return this.subtract(value.abs());
        }
        
        StringBuilder sb = new StringBuilder();
        
        int endPos = this.data.length()-1;
        int pos1 = this.data.length()-1;
        int pos2 = value.data.length()-1;
        int tmpCarry = 0;
        
        if ( endPos < pos2 ){
            endPos = pos2;
        }
        
        while ( endPos >= 0 ){
            
            String valueWhereToAdd = "";
            if ( pos1 >= 0 ){
                valueWhereToAdd = "" + this.data.charAt(pos1);
            }
            
            String valueToAdd = "";
            if ( pos2 >= 0){
                valueToAdd = "" + value.data.charAt(pos2);
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
        
        NewOwnBigInteger result = new NewOwnBigInteger(sb.toString());
        
        if ( this.signum == value.signum ){
            result.signum = this.signum;
        }
        
        return result;
    }
    
    /**
     * Subtracts parameter value from this NewOwnBigInteger.
     * @param value value to subtract from this.
     * @return new NewOwnBigInteger representation.
     */
    public NewOwnBigInteger subtract(NewOwnBigInteger value){
        // -this - -value -> abs this - value, needs to check results signum, 
        // if result is negative, it will convert to positive
        if ( this.signum == -1 && value.signum == -1 ){
            NewOwnBigInteger result = this.abs().subtract(value.abs());
            if (result.signum == -1){
                result.signum = 0;
            }else{
                result.signum = -1;
            }
            return result;
        }
        // -this - +value -> abs this.add(value)
        if ( this.signum == -1 && value.signum == 0 ){
            NewOwnBigInteger result = this.abs().add(value);
            result.signum = -1;
            return result;
        }
        
        if ( this.signum == 0 && value.signum == -1 ){
            NewOwnBigInteger result = this.add(value.abs());
            return result;
        }
        
        // if abs this is smaller than abs value and both positive, then we 
        // need to swap so we substract from the bigger value the smaller value
        // and result will be negative
        if ( this.abs().compareTo(value.abs()) < 0 ){
            NewOwnBigInteger result = value.abs().subtract(this.abs());
            result.signum = -1;
            return result;
        }
        
        // Last where the real subtract is made!
        // OLD VERSION HAS PROBLEMS
        StringBuilder sb = new StringBuilder();
        
        // both values lengths can be different
        int pos1 = this.data.length() -1; 
        int pos2 = value.data.length() -1;
        int endPos = this.data.length() -1;
        int carry = 0;
        
        if ( endPos < pos2 ){
            endPos = pos2;
        }
        
        while ( endPos >= 0 ){
        
            String valueWhereToSubtract = "";
            if ( pos1 >= 0 ){
                valueWhereToSubtract = "" + this.data.charAt(pos1);
            }
            
            String valueToSubtract = "";
            if ( pos2 >= 0){
                valueToSubtract = "" + value.data.charAt(pos2);
            }
            
            int first = 0;
            if ( !valueWhereToSubtract.isEmpty() ){
                first = Integer.parseInt(valueWhereToSubtract);
            }
            
            int second = 0;
            
            if ( !valueToSubtract.isEmpty() ){
                second = Integer.parseInt(valueToSubtract);
            }
            
            second = second + carry;
            carry = 0;
            
            if ( first < second && pos1 > 0 ){
                carry = 1;
                first = first + 10;
            }
            
            int resultInt = first - second;
         
            sb.insert(0, resultInt);
            
            pos1--;
            pos2--;
            endPos--;
        }
        
        String tmp = sb.toString();
        if ( tmp.contains("-")){
            tmp = tmp.replaceAll("-", "");
            tmp = "-" + tmp;
        }
        NewOwnBigInteger returnValue = new NewOwnBigInteger(tmp);
        return returnValue;
    }
    
    /**
     * Multiplies this NewOwnBigInteger with the given value.
     * @param multiplier NewOwnBigInteger that multiplies this NewOwnBigInteger
     * @return new NewOwnBigInteger representation.
     */
    public NewOwnBigInteger multiply(NewOwnBigInteger multiplier){
        
        if ( multiplier.abs().equals(ONE)){
            // convert signum of this.
            if ( this.signum == multiplier.signum ){
                this.signum = 0;
                return this;
            }else{
                this.signum = -1;
                return this;
            }
        }
        
        String [] results = new String[multiplier.data.length()];
        
        int pos1 = data.length()-1;
        int pos2 = multiplier.data.length()-1;
        
        int pos3 = data.length()-1;
        if ( pos3 < pos2 ){
            pos3 = pos2;
        }
        
        StringBuilder sb = new StringBuilder();
        
        /**
         *  123456 this  (this OwnBigInteger instance)
         * *000111 value (parameter)
         * -------
         */
        
        for ( int lkm = 0; pos2 >= 0; pos2--, lkm++ ){ 
            int tmpCarry = 0;
            String multiplyValue = "";
            if ( pos2 >= 0){
                multiplyValue = "" + multiplier.data.charAt(pos2); 
            }
            int mp = 0;
            
            if ( !multiplyValue.isEmpty() ){
                mp = Integer.parseInt(multiplyValue);
            }
            
            for ( int i = (multiplier.data.length()-1) - pos2; i > 0; i-- ){
                sb.insert(0, "0"); 
            // when we have moved atleast one step from the initial last 
            // position, we have to add zeroes to last positions. 
            // Before multiply results.
            }
            
            for ( ; pos1 >= 0; pos1-- ){
                
                String valueToMultiply = "";
                if ( pos1 >= 0 ){
                    valueToMultiply = "" + this.data.charAt(pos1);
                }
                
                int multiplyThis = 0;
                if ( !valueToMultiply.isEmpty() ){
                    multiplyThis = Integer.parseInt(valueToMultiply);
                }
                
                int result = multiplyThis * mp + tmpCarry;
                int remainder = result % 10;
                tmpCarry = result / 10;
                
                sb.insert(0, remainder);
            }
            if ( tmpCarry != 0 ){
                // if tmpCarry has something still, it has to be inserted as a first.
                sb.insert(0, tmpCarry); 
            }
            pos1 = data.length()-1; // reset the position
            results[lkm] = sb.toString();
            sb.delete(0, sb.length()); // time to start next multiply result.
        }
        
        // Add all multiply results for final result. And return the final sum.
        NewOwnBigInteger first = new NewOwnBigInteger(results[0]);
        
        for ( int i = 1; i < results.length; i++ ){
            first = first.add(new NewOwnBigInteger(results[i]));
        }
        
        // If both are negative or positive then result is positive
        // if either is negative, then result is negative.
        if ( this.signum == multiplier.signum ){
            first.signum = 0;
        }else{
            first.signum = -1;
        }
        
        return first;
        
    }
    
    /**
     * Divides this NewOwnBigInteger with the parameter value (divider) and
     * returns the division result integer.
     * @param divider
     * @return 
     */
    public NewOwnBigInteger divide(NewOwnBigInteger divider){
        // change the workings of divide, divede and remains
        // if this.signum == multiplier.signum, then signum will be 0
        // otherwise it will be -1.
        return this.abs().divideAndRemainder(divider)[0];
    }
    
    /**
     * Divides this NewOwnBigInteger with the parameter value, and returns both the 
     * division and remaining parts in NewOwnBigInteger array. Array[0] contains the 
     * division result. Array[1] contains the remainder.
     * @param divider Divider.
     * @return array containing division and remainder.
     */
    public NewOwnBigInteger[] divideAndRemainder(NewOwnBigInteger divider){
        // if this.signum == multiplier.signum, then signum will be 0
        // otherwise it will be -1.
        if ( this.abs().compareTo(divider.abs()) < 0 ){
            NewOwnBigInteger [] array = new NewOwnBigInteger[2];
            array[0] = ZERO;
            array[1] = this;
            return array;
        }
        
        NewOwnBigInteger tmp = this.abs();
        NewOwnBigInteger divi = divider.abs();
        NewOwnBigInteger diviMult = divi.abs();
        
        if ( tmp.data.length() > divi.data.length() + 5){
            NewOwnBigInteger doubler = NewOwnBigInteger.valueOf(10000L);
            NewOwnBigInteger counter = new NewOwnBigInteger(ZERO);
            
            while ( tmp.data.length() > diviMult.data.length() + 5 ){
                
                diviMult = diviMult.multiply(doubler);
                counter = counter.add(ONE);
            }
            
            NewOwnBigInteger result = new NewOwnBigInteger(ZERO);
            
            while ( tmp.compareTo(diviMult) >= 0 ){
                tmp = tmp.subtract(diviMult);
                result = result.add(ONE);
            }
            
            while ( counter.compareTo(ZERO) > 0 ){
                result = result.multiply(doubler);
                counter = counter.subtract(ONE);
            }            
            
            NewOwnBigInteger [] array = tmp.divideAndRemainder(divider);
            result = result.add(array[0]);
            
            if ( this.signum == divider.signum ){
                result.signum = 0;
            }
            else{
                result.signum = -1;
            }
            array[0] = result;

            return array;
        }
        else if (tmp.data.length() > divi.data.length() + 1){
            NewOwnBigInteger doubler = NewOwnBigInteger.valueOf(2L);
            NewOwnBigInteger counter = new NewOwnBigInteger(ZERO);
            
            while ( tmp.data.length() > diviMult.data.length() + 1 ){
                
                diviMult = diviMult.multiply(doubler);
                counter = counter.add(ONE);
            }
            
            NewOwnBigInteger result = new NewOwnBigInteger(ZERO);
            
            while ( tmp.compareTo(diviMult) >= 0 ){
                tmp = tmp.subtract(diviMult);
                result = result.add(ONE);
            }
            
            while ( counter.compareTo(ZERO) > 0 ){
                result = result.multiply(doubler);
                counter = counter.subtract(ONE);
            }            
            
            NewOwnBigInteger [] array = tmp.divideAndRemainder(divider);
            result = result.add(array[0]);
            
            if ( this.signum == divider.signum ){
                result.signum = 0;
            }
            else{
                result.signum = -1;
            }
            array[0] = result;

            return array;
        }
        else
        {
        NewOwnBigInteger result = new NewOwnBigInteger(ZERO);
        
        while ( tmp.compareTo(divi) >= 0 ){
            tmp = tmp.subtract(divi);
            result = result.add(ONE);
        }
        
        NewOwnBigInteger [] array = new NewOwnBigInteger[2];
        
        if ( this.signum == divider.signum ){
            result.signum = 0;
        }
        else{
            result.signum = -1;
        }
        array[0] = result;
        array[1] = tmp; // remainder
        
        return array;
        }
    }
    
    /**
     * Modulus function for NewOwnBigInteger
     * @param modulo modulus
     * @return new NewOwnBigInteger representation.
     */
    public NewOwnBigInteger mod(NewOwnBigInteger modulo){
        // Modulo can't be negative, should throw aritmetic error or something like that.
        // also how it works if this is negative?
        if ( modulo.compareTo(ZERO) < 0 ){
            throw new ArithmeticException("Modulo can't be negative");
        }
        
        return this.abs().divideAndRemainder(modulo)[1];
    }
    
    /**
     * Modulus exponent function for OwnBigInteger.
     * result is calculated like: this OwnBigInteger^exponent mod modulus
     * @param exponent exponent value.
     * @param modulus modulus value.
     * @return new OwnBigInteger representation.
     */
    public NewOwnBigInteger modPow(NewOwnBigInteger exponent, NewOwnBigInteger modulus){

        // modulus can't be negative.
        // exponent can't be negative. (could be, but don't know yet how to handle that)
        String expString = convertToBinary(exponent);
       
        NewOwnBigInteger result = new NewOwnBigInteger(ONE);
        NewOwnBigInteger tmp = new NewOwnBigInteger(this);
        
        int pos = 0;
        
        for ( ; pos < expString.length(); pos++ ){
            result = result.multiply(result).mod(modulus);
            if ( expString.charAt(pos) == '1' ){
                result = result.multiply(tmp).mod(modulus);
            }
        }
        
        return result;
    }
    
    /**
     * Power function for OwnBigInteger.
     * @param exponent exponent value.
     * @return new OwnBigInteger representation.
     */
    public NewOwnBigInteger pow(NewOwnBigInteger exponent){
        // if this is negative and exponent is odd (pariton) then we have a negative result.
        if ( exponent.compareTo(ZERO) < 0 ){
            // should throw arithmetic exception.
            throw new ArithmeticException("Exponent can't be negative!");
        }
        
        if ( exponent.equals(ONE) ){
            return this;
        }
        
        if ( exponent.equals(ZERO)){
            return ONE;
        }
        
        NewOwnBigInteger base = new NewOwnBigInteger(this);
        NewOwnBigInteger result = new NewOwnBigInteger(ONE);
        NewOwnBigInteger expRes = new NewOwnBigInteger(exponent);
        
        while ( !expRes.equals(ZERO) ){
            
            NewOwnBigInteger [] tmpres = expRes.divideAndRemainder(NewOwnBigInteger.valueOf(2L));
            expRes = tmpres[0];
            if ( tmpres[1].equals(ONE)){
                result = result.multiply(base);
            }
            
            base = base.multiply(base);
        }
        
        if ( this.signum == -1 ){
            if ( exponent.abs().divideAndRemainder(valueOf(2L))[1].equals(ONE)){
                // result.signum = -1 // negative
                result.signum = -1;
                // exponent is odd, so result will be negative.
            }else{
                // result.signum = 0; // positive
                result.signum = 0;
                // if exponent is even, it will be positive
            }   
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
    public int compareTo(NewOwnBigInteger value){
        
        if ( this.signum == -1 && value.signum == 0 ){
            return -1;
        }
        if ( this.signum == 0 && value.signum == -1 ){
            return 1;
        }
        // negative numbers
        if ( (this.data.length() > value.data.length()) && this.signum == -1 ){
            return -1;
        }
        
        if ( (this.data.length() < value.data.length() ) && this.signum == -1){
            return 1;
        }
        // positive numbers
        if ( this.data.length() > value.data.length() ){
            return 1;
        }
        
        if ( this.data.length() < value.data.length() ){
            return -1;
        }
        
        // both are as long
        int returnValue = 0;
        // both values are equally long!
        for ( int i = 0; i < this.data.length(); i++ ){
            
            // This data[i] number
            String thisPos = "";
            if ( i >= 0 ){
                thisPos = "" + this.data.charAt(i);
            }
                
            int thisValue = 0;
            if ( !thisPos.isEmpty() ){
                thisValue = Integer.parseInt(thisPos);
            }
            
            // Parameter data[i] number
            String paramPos = "";
            if ( i >= 0 ){
                paramPos = "" + value.data.charAt(i);
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
        if ( this.signum == -1 && returnValue != 0){
            if ( returnValue == 1 )
            {
                returnValue = -1;
            }
            else
            {
                returnValue = 1;
            }
        }
        
        return returnValue;
    }
    
    /**
     * Adapted from C++ version: http://rosettacode.org/wiki/Modular_inverse
     * Returns NewOwnBigInteger which is this^-1 mod value.
     * @param value modulus value.
     * @return new NewOwnBigInteger value.
     */
    public NewOwnBigInteger modInverse(NewOwnBigInteger value){
    
        NewOwnBigInteger a = new NewOwnBigInteger(this);
        NewOwnBigInteger b = new NewOwnBigInteger(value);
        NewOwnBigInteger b0 = new NewOwnBigInteger(value);
        NewOwnBigInteger x0 = new NewOwnBigInteger(ZERO);
        NewOwnBigInteger x1 = new NewOwnBigInteger(ONE);
        if ( value.equals(ONE)){
            return ONE;
        }
        while ( a.compareTo(ONE) > 0 ){
            NewOwnBigInteger q = a.divide(b);
            NewOwnBigInteger t = new NewOwnBigInteger(b);
            b = new NewOwnBigInteger(a.mod(b));
            a = new NewOwnBigInteger(t);
            t = new NewOwnBigInteger(x0);
            x0 = new NewOwnBigInteger(x1.subtract(q.multiply(x0)));
            x1 = new NewOwnBigInteger(t);
        }
        if ( x1.compareTo(ZERO) < 0 ){
            x1 = x1.add(b0);
        }
        
        return x1;
    }
    
    /**
     * Returns the greatest common divisor of this and value.
     * @param value
     * @return Greatest common divisor.
     */
    public NewOwnBigInteger gcd(NewOwnBigInteger value){
        NewOwnBigInteger tmp = new NewOwnBigInteger(this);
        NewOwnBigInteger tmp2 = new NewOwnBigInteger(value);
        
        if ( tmp.compareTo(tmp2) < 0 ){
            NewOwnBigInteger swap = tmp;
            tmp = tmp2;
            tmp2 = swap;
        }
        
        do{
            if ( tmp.compareTo(tmp2) > 0 )
            {
                NewOwnBigInteger [] array = tmp.divideAndRemainder(tmp2);
                tmp = tmp2;
                tmp2 = array[1];
            }
            else if ( tmp.compareTo(tmp2) < 0 )
            {
                NewOwnBigInteger [] array = tmp2.divideAndRemainder(tmp);
                tmp2 = tmp;
                tmp = array[1];
            } 
            
        }while ( !(tmp.equals(ZERO)||tmp2.equals(ZERO)) );
        
        return tmp;
    }
    
    /**
     * Converts a given NewOwnBigInteger to binary representation.
     * This is used for modular exponention to make it faster.
     * @param valueToConvert
     * @return Binary representation of the parameter value.
     */
    private String convertToBinary(NewOwnBigInteger value){
        
        String returnValue = "";
        
        NewOwnBigInteger tmp = new NewOwnBigInteger(value);
        
        NewOwnBigInteger [] array;
        NewOwnBigInteger divider = NewOwnBigInteger.valueOf(2L);
        
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
     * Converts Binary string to decimal representation NewOwnBigInteger.
     * @param binaryPresentation
     * @return NewOwnBigInteger, decimal representation of the parameter value.
     */
    private NewOwnBigInteger convertToDecimal(String binaryPresentation){
        
        NewOwnBigInteger result = new NewOwnBigInteger(ZERO);
        NewOwnBigInteger two = NewOwnBigInteger.valueOf(2L);
        
        long exponent = 0;
        for ( int i = binaryPresentation.length() - 1; i > 0; i--  ){
        
            if ( binaryPresentation.charAt(i) == '1' ){
                NewOwnBigInteger tmp = two.pow(NewOwnBigInteger.valueOf(exponent));
                
                result = result.add(tmp);
            }
            exponent++;
        }
        
        return result;
    }
}