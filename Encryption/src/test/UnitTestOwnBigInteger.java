/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import encryption.OwnBigInteger;
import java.math.BigInteger;
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
    public static void TestStringCreation(){
        
        OwnBigInteger test1 = new OwnBigInteger("0000000531");
        long tmp1 = 531L;
        
        Assert.assertTrue(test1.equals(OwnBigInteger.valueOf(tmp1)));
    }
    
    @Test
    public static void TestPower(){
        
        long tmp1 = 33;
        long tmp2 = 33;
        int tmp3 =  33;
        
        BigInteger big1 = BigInteger.valueOf(tmp1);
        
        OwnBigInteger obig1 = OwnBigInteger.valueOf(tmp1);
        OwnBigInteger obig2 = OwnBigInteger.valueOf(tmp2);
        
        obig1 = obig1.pow(obig2);
        big1 = big1.pow(tmp3);
        
        String eka = obig1.toString();
        String toka = big1.toString();
        
        Assert.assertTrue(eka.equals(toka));
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
    
    @Test
    public static void TestModPower(){
        
        long tmp1 = 5736;
        long tmp2 = 234;
        long tmp3 = 534832;
        // Below are values used in cormen's book. 
//        long tmp1 = 7;
//        long tmp2 = 560;
//        long tmp3 = 561;
        
        OwnBigInteger test1 = OwnBigInteger.valueOf(tmp1);
        OwnBigInteger test2 = OwnBigInteger.valueOf(tmp2);
        OwnBigInteger test3 = OwnBigInteger.valueOf(tmp3);
        
        BigInteger check1 = BigInteger.valueOf(tmp1);
        BigInteger check2 = BigInteger.valueOf(tmp2);
        BigInteger check3 = BigInteger.valueOf(tmp3);
        
        check1 = check1.modPow(check2, check3);
        
        test1 = test1.modPow(test2, test3);
        
        String res1 = check1.toString();
        String res2 = test1.toString();
        
        Assert.assertTrue(res1.equals(res2));
    }
    
    @Test
    public static void TestModPow2(){
        
        long tmp1 = 265944;
        long tmp2 = 534832;
        
        OwnBigInteger test1 = OwnBigInteger.valueOf(tmp1);
        test1 = test1.multiply(test1);
        test1 = test1.mod(OwnBigInteger.valueOf(tmp2));
        
        BigInteger check1 = BigInteger.valueOf(tmp1);
        check1 = check1.multiply(check1);
        check1 = check1.mod(BigInteger.valueOf(tmp2));
        
        String res1 = test1.toString();
        String res2 = check1.toString();
        
        Assert.assertTrue(res1.equals(res2));
    }
    
    @Test
    public static void TestDivideRemainder2(){
        long tmp1 = 33;
        long tmp2 = 2;
        OwnBigInteger test1 = OwnBigInteger.valueOf(tmp1);
        OwnBigInteger test2 = OwnBigInteger.valueOf(tmp2);
        
        OwnBigInteger [] res = test1.divideAndRemainder(test2);
        
        test1 = res[0];
        res = test1.divideAndRemainder(test2);
        test1 = res[0];
        
        long result = tmp1 / tmp2;
        result = result / tmp2;
        
        Assert.assertTrue(test1.equals(OwnBigInteger.valueOf(result)));
    }
    
    @Test
    public static void TestGCD(){
        long tmp1 = 49807342;
        long tmp2 = 23450954;
        
        BigInteger check1 = BigInteger.valueOf(tmp1);
        BigInteger check2 = BigInteger.valueOf(tmp2);
        BigInteger res = check1.gcd(check2);
        
        OwnBigInteger test1 = OwnBigInteger.valueOf(tmp1);
        OwnBigInteger test2 = OwnBigInteger.valueOf(tmp2);
        OwnBigInteger res2 = test1.gcd(test2);
        
        String r1 = res.toString();
        String r2 = res2.toString();
        Assert.assertTrue(r1.equals(r2));
        
    }
    
    public static void main(String [] args){
        
        /*
        TestAdding(); 
        TestSubtracting(); // Simple test
        TestMultiply(); // Simple test
        TestDivision();
        TestDivideRemainder();
        TestStringCreation();
        TestPower();
        TestDivideRemainder2();
        TestModPower();
        TestModPow2();
        TestGCD();
        */
        
    }
}
