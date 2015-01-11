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
        OwnBigInteger test1 = OwnBigInteger.valueOf(1L);
        OwnBigInteger test2 = test1.add(OwnBigInteger.valueOf(2L));
        
        OwnBigInteger test3 = OwnBigInteger.valueOf(3L);
        Assert.assertTrue(test3.equals(test2));
    }
    
    @Test
    public static void TestSubtracting(){
        OwnBigInteger test1 = OwnBigInteger.ONE;
        OwnBigInteger test2 = test1.subtract(test1);
        
        Assert.assertTrue(test2.equals(OwnBigInteger.ZERO));
    }
    
    @Test
    public static void TestMultiply(){
        OwnBigInteger test1 = OwnBigInteger.ONE;
        OwnBigInteger test2 = test1.multiply(OwnBigInteger.TEN);
        
        Assert.assertTrue(test2.equals(OwnBigInteger.TEN));
    }
    
    public static void main(String [] args){
        TestAdding(); // also tests converting long values to byte arrays
        TestSubtracting(); // Simple test
        TestMultiply(); // Simple test
    }
}
