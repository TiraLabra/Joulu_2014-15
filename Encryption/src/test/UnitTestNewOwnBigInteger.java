/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;
import encryption.NewOwnBigInteger;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Markus
 */
public class UnitTestNewOwnBigInteger {
    @Test
    public static void TestAdding(){
        long tmp1 = 983L;
        long tmp2 = 567L;
        NewOwnBigInteger test1 = NewOwnBigInteger.valueOf(tmp1);
        NewOwnBigInteger test2 = NewOwnBigInteger.valueOf(tmp2);
        
        NewOwnBigInteger result = test1.add(test2);

        long res = tmp1+tmp2;
        NewOwnBigInteger test3 = NewOwnBigInteger.valueOf(res);
        Assert.assertTrue(test3.equals(result));
    }
    
    @Test
    public static void TestAdding2(){
        long tmp1 = -983L;
        long tmp2 = 567L;
        NewOwnBigInteger test1 = NewOwnBigInteger.valueOf(tmp1);
        NewOwnBigInteger test2 = NewOwnBigInteger.valueOf(tmp2);
        
        NewOwnBigInteger result = test1.add(test2);

        long res = tmp1+tmp2;
        NewOwnBigInteger test3 = NewOwnBigInteger.valueOf(res);
        Assert.assertTrue(test3.equals(result));
    }
    
    @Test
    public static void TestAdding3(){
        long tmp1 = -983L;
        long tmp2 = -567L;
        NewOwnBigInteger test1 = NewOwnBigInteger.valueOf(tmp1);
        NewOwnBigInteger test2 = NewOwnBigInteger.valueOf(tmp2);
        
        NewOwnBigInteger result = test1.add(test2);

        long res = tmp1+tmp2;
        NewOwnBigInteger test3 = NewOwnBigInteger.valueOf(res);
        Assert.assertTrue(test3.equals(result));
    }
    
    @Test
    public static void TestAdding4(){
        long tmp1 = 983L;
        long tmp2 = -567L;
        NewOwnBigInteger test1 = NewOwnBigInteger.valueOf(tmp1);
        NewOwnBigInteger test2 = NewOwnBigInteger.valueOf(tmp2);
        
        NewOwnBigInteger result = test1.add(test2);

        long res = tmp1+tmp2;
        NewOwnBigInteger test3 = NewOwnBigInteger.valueOf(res);
        Assert.assertTrue(test3.equals(result));
    }
    
    @Test
    public static void TestSubtracting(){
        
        long big1 = 453908;
        long big2 = 434507;
        //long big1 = 123;
        //long big2 = 75;
        NewOwnBigInteger eka = NewOwnBigInteger.valueOf(big1);
        NewOwnBigInteger toka = NewOwnBigInteger.valueOf(big2);
        eka = eka.subtract(toka);
        
        long result = big1-big2;
        
        Assert.assertTrue(eka.equals(NewOwnBigInteger.valueOf(result)));
    }
    
    @Test
    public static void TestSubtracting2(){
        long big1 = 453908;
        long big2 = 434507;
        
        NewOwnBigInteger eka = NewOwnBigInteger.valueOf(big2);
        NewOwnBigInteger toka = NewOwnBigInteger.valueOf(big1);
        eka = eka.subtract(toka);
        
        long result = big2-big1;
        
        Assert.assertTrue(eka.equals(NewOwnBigInteger.valueOf(result)));     
    }
    
    @Test
    public static void TestSubtracting3(){
        long big1 = -10000;
        long big2 = -34514;
        
        long result = big1 - big2;
        
        NewOwnBigInteger eka = NewOwnBigInteger.valueOf(big1);
        NewOwnBigInteger toka = NewOwnBigInteger.valueOf(big2);
        
        eka = eka.subtract(toka);
        
        Assert.assertTrue(eka.equals(NewOwnBigInteger.valueOf(result)));               
    }
    
    @Test
    public static void TestSubtracting4(){
        long big1 = 10000;
        long big2 = -34514;
        
        long result = big1 - big2;
        
        NewOwnBigInteger eka = NewOwnBigInteger.valueOf(big1);
        NewOwnBigInteger toka = NewOwnBigInteger.valueOf(big2);
        
        eka = eka.subtract(toka);
        
        Assert.assertTrue(eka.equals(NewOwnBigInteger.valueOf(result)));               
    }
    
    @Test
    public static void TestSubtracting5(){
        long big1 = -10000;
        long big2 = 34514;
        
        long result = big1 - big2;
        
        NewOwnBigInteger eka = NewOwnBigInteger.valueOf(big1);
        NewOwnBigInteger toka = NewOwnBigInteger.valueOf(big2);
        
        eka = eka.subtract(toka);
        
        Assert.assertTrue(eka.equals(NewOwnBigInteger.valueOf(result)));               
    }
    
    @Test
    public static void TestMultiply(){
        long tmp1 = 123456789;
        long tmp2 = 123456789;
        NewOwnBigInteger test1 = new NewOwnBigInteger(NewOwnBigInteger.valueOf(tmp1));
        NewOwnBigInteger test2 = test1.multiply(NewOwnBigInteger.valueOf(tmp2));
        
        long tulos = tmp1 * tmp2;
        
        Assert.assertTrue(test2.equals(NewOwnBigInteger.valueOf(tulos)));
    }
    
    @Test
    public static void TestMultiply2(){
        long tmp1 = 123456789;
        long tmp2 = -123456789;
        
        NewOwnBigInteger test1 = new NewOwnBigInteger(NewOwnBigInteger.valueOf(tmp1));
        NewOwnBigInteger test2 = test1.multiply(NewOwnBigInteger.valueOf(tmp2));
        
        long tulos = tmp1 * tmp2;
        
        Assert.assertTrue(test2.equals(NewOwnBigInteger.valueOf(tulos)));
    }
    
    @Test
    public static void TestMultiply3(){
        long tmp1 = -2342343;
        long tmp2 = -2342343;
        
        NewOwnBigInteger test1 = new NewOwnBigInteger(NewOwnBigInteger.valueOf(tmp1));
        NewOwnBigInteger test2 = test1.multiply(NewOwnBigInteger.valueOf(tmp2));
        
        long tulos = tmp1 * tmp2;
        
        Assert.assertTrue(test2.equals(NewOwnBigInteger.valueOf(tulos)));
    }
    
    @Test
    public static void TestDivision(){
        
        long tmp1 = 234549;
        long tmp2 = 43;
        
        NewOwnBigInteger test1 = NewOwnBigInteger.valueOf(tmp1);
        NewOwnBigInteger test2 = NewOwnBigInteger.valueOf(tmp2);
        
        NewOwnBigInteger res = test1.divide(test2);
        
        long result = tmp1 / tmp2;
        
        Assert.assertTrue(res.equals(NewOwnBigInteger.valueOf(result)));
    }
    
    @Test
    public static void TestStringCreation(){
        
        NewOwnBigInteger test1 = new NewOwnBigInteger("0000000531");
        long tmp1 = 531L;
        
        Assert.assertTrue(test1.equals(NewOwnBigInteger.valueOf(tmp1)));
    }
    
    @Test
    public static void TestPower(){
        
        long tmp1 = 33;
        long tmp2 = 33;
        int tmp3 =  33;
        
        BigInteger big1 = BigInteger.valueOf(tmp1);
        
        NewOwnBigInteger obig1 = NewOwnBigInteger.valueOf(tmp1);
        NewOwnBigInteger obig2 = NewOwnBigInteger.valueOf(tmp2);
        
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
        
        NewOwnBigInteger test1 = NewOwnBigInteger.valueOf(tmp1);
        NewOwnBigInteger test2 = NewOwnBigInteger.valueOf(tmp2);
        
        NewOwnBigInteger [] res = test1.divideAndRemainder(test2);
        
        long result = tmp1 / tmp2;
        long remainder = tmp1 % tmp2;
        
        boolean correct = false;
        if ( res.length == 2 ){
            if ( res[0].equals(NewOwnBigInteger.valueOf(result)) && res[1].equals(NewOwnBigInteger.valueOf(remainder))){
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
        
        NewOwnBigInteger test1 = NewOwnBigInteger.valueOf(tmp1);
        NewOwnBigInteger test2 = NewOwnBigInteger.valueOf(tmp2);
        NewOwnBigInteger test3 = NewOwnBigInteger.valueOf(tmp3);
        
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
        
        NewOwnBigInteger test1 = NewOwnBigInteger.valueOf(tmp1);
        test1 = test1.multiply(test1);
        test1 = test1.mod(NewOwnBigInteger.valueOf(tmp2));
        
        BigInteger check1 = BigInteger.valueOf(tmp1);
        check1 = check1.multiply(check1);
        check1 = check1.mod(BigInteger.valueOf(tmp2));
        
        String res1 = test1.toString();
        String res2 = check1.toString();
        
        Assert.assertTrue(res1.equals(res2));
    }
    
    @Test
    public static void TestDivisionLong(){
        
        Random rnd = new Random();
        NewOwnBigInteger test1 = NewOwnBigInteger.probablePrime(512, rnd);
        NewOwnBigInteger divider = NewOwnBigInteger.valueOf(17l);
        
        NewOwnBigInteger result = test1.divide(divider);
        
        BigInteger check1 = new BigInteger(test1.toString());
        BigInteger div = BigInteger.valueOf(17l);
        BigInteger res = check1.divide(div);
        
        String res1 = result.toString();
        String res2 = res.toString();
        
        Assert.assertTrue(res1.equals(res2));
    }
    
    @Test
    public static void TestDivideRemainder2(){
        long tmp1 = 33;
        long tmp2 = 2;
        NewOwnBigInteger test1 = NewOwnBigInteger.valueOf(tmp1);
        NewOwnBigInteger test2 = NewOwnBigInteger.valueOf(tmp2);
        
        NewOwnBigInteger [] res = test1.divideAndRemainder(test2);
        
        test1 = res[0];
        res = test1.divideAndRemainder(test2);
        test1 = res[0];
        
        long result = tmp1 / tmp2;
        result = result / tmp2;
        
        Assert.assertTrue(test1.equals(NewOwnBigInteger.valueOf(result)));
    }
    
    @Test
    public static void TestGCD(){
        long tmp1 = 49807342;
        long tmp2 = 23450954;
        
        BigInteger check1 = BigInteger.valueOf(tmp1);
        BigInteger check2 = BigInteger.valueOf(tmp2);
        BigInteger res = check1.gcd(check2);
        
        NewOwnBigInteger test1 = NewOwnBigInteger.valueOf(tmp1);
        NewOwnBigInteger test2 = NewOwnBigInteger.valueOf(tmp2);
        NewOwnBigInteger res2 = test1.gcd(test2);
        
        String r1 = res.toString();
        String r2 = res2.toString();
        Assert.assertTrue(r1.equals(r2));
    }
    
    @Test
    public static void TestModInverse(){
        
        long tmp1 = 42;
        long tmp2 = 2017;
        
        BigInteger check1 = BigInteger.valueOf(tmp1);
        BigInteger check2 = BigInteger.valueOf(tmp2);
        BigInteger res = check1.modInverse(check2);
        
        NewOwnBigInteger test1 = NewOwnBigInteger.valueOf(tmp1);
        NewOwnBigInteger test2 = NewOwnBigInteger.valueOf(tmp2);
        NewOwnBigInteger res2 = test1.modInverse(test2);
        
        String r1 = res.toString();
        String r2 = res2.toString();
        
        Assert.assertTrue(r1.equals(r2));
    }
    
    @Test
    public static void TestProbablePrimes(){
        Random rnd = new Random();
        ArrayList<NewOwnBigInteger> lista = new ArrayList<>();
        
        for ( int i = 0; i < 20; i ++ ){
            lista.add(NewOwnBigInteger.probablePrime(512, rnd));
        }
        
        for ( NewOwnBigInteger n : lista )
        {
            System.out.println(n.toString());
        }
    }
    
    public static void main(String [] args){
        
        // Addition tests
        TestAdding();
        TestAdding2();
        TestAdding3();
        TestAdding4();
        
        // Substract tests 
        TestSubtracting(); // Simple test
        TestSubtracting2(); // Simple test
        TestSubtracting3();
        TestSubtracting4();
        TestSubtracting5();
        
        
        // Multiply tests
        TestMultiply(); // Simple test
        TestMultiply2();
        TestMultiply3();
        
        
        TestDivision();
        TestDivideRemainder();
        TestDivideRemainder2();
        TestStringCreation();
        TestPower();
        TestModPower();
        TestModPow2();
        TestGCD();
        
        TestModInverse();
        
        TestProbablePrimes();
        
        TestDivisionLong();
    }
}
