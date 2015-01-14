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
        OwnBigInteger test1 = OwnBigInteger.ONE;
        OwnBigInteger test2 = test1.subtract(test1);
        
        Assert.assertTrue(test2.equals(OwnBigInteger.ZERO));
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
    
    public static void main(String [] args){
       // TestAdding(); 
       // TestSubtracting(); // Simple test
        TestMultiply(); // Simple test
    }
}
