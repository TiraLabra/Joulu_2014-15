/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import encryption.OwnBigInteger;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author markus.pahkamaa
 */
public class UnitTestOwnBigInteger {
    
    @Test
    public static void TestAdding(){
        long tmp1 = 983L;
        long tmp2 = 567L;
        OwnBigInteger test1 = OwnBigInteger.valueOf(tmp1);
        OwnBigInteger test2 = OwnBigInteger.valueOf(tmp2);
        
        OwnBigInteger result = test1.add(test2);

        long res = tmp1+tmp2;
        OwnBigInteger test3 = OwnBigInteger.valueOf(res);
        Assert.assertTrue(test3.equals(result));
    }
    
    @Test
    public static void TestSubtracting(){
        
        long big1 = 453908;
        long big2 = 434507;
        //long big1 = 123;
        //long big2 = 75;
        OwnBigInteger eka = OwnBigInteger.valueOf(big1);
        OwnBigInteger toka = OwnBigInteger.valueOf(big2);
        eka = eka.subtract(toka);
        
        long result = big1-big2;
        
        
        Assert.assertTrue(eka.equals(OwnBigInteger.valueOf(result)));
    }
    
    @Test
    public static void TestMultiply(){
        long tmp1 = 123456789;
        long tmp2 = 123456789;
        OwnBigInteger test1 = new OwnBigInteger(OwnBigInteger.valueOf(tmp1));
        OwnBigInteger test2 = test1.multiply(OwnBigInteger.valueOf(tmp2));
        
        long tulos = tmp1 * tmp2;
        
        Assert.assertTrue(test2.equals(OwnBigInteger.valueOf(tulos)));
    }
    
    @Test
    public static void TestDivision(){
        
        long tmp1 = 234549;
        long tmp2 = 43;
        
        OwnBigInteger test1 = OwnBigInteger.valueOf(tmp1);
        OwnBigInteger test2 = OwnBigInteger.valueOf(tmp2);
        
        OwnBigInteger res = test1.divide(test2);
        
        long result = tmp1 / tmp2;
        
        Assert.assertTrue(res.equals(OwnBigInteger.valueOf(result)));
    }
    
    @Test
    public static void TestDivideRemainder(){
        long tmp1 = 234549;
        long tmp2 = 43;
        
        OwnBigInteger test1 = OwnBigInteger.valueOf(tmp1);
        OwnBigInteger test2 = OwnBigInteger.valueOf(tmp2);
        
        OwnBigInteger [] res = test1.divideAndRemainder(test2);
        
        long result = tmp1 / tmp2;
        long remainder = tmp1 % tmp2;
        
        boolean correct = false;
        if ( res.length == 2 ){
            if ( res[0].equals(OwnBigInteger.valueOf(result)) && res[1].equals(OwnBigInteger.valueOf(remainder))){
                correct = true;
            }
        }
        
        Assert.assertTrue(correct);
    }
    
    public static void main(String [] args){
       // TestAdding(); 
       //TestSubtracting(); // Simple test
       // TestMultiply(); // Simple test
        //TestDivision();
        TestDivideRemainder();
        
    }
}
