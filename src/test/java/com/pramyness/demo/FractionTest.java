package com.pramyness.demo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * IntelliJ IDEA 17
 * Created by Pramy on 2018/9/16.
 */
public class FractionTest {

    @Test
    public void add() {
        System.out.println(new Fraction(1,3).subtract(new Fraction(5,3)));

    }
// -    55    +    28/89    12
//         55  -  28/89  +  12
//         66'61/89
    @Test
    public void subtract() {
        System.out.println(new Fraction(55,1).subtract(new Fraction(28,89)).add(new Fraction(12,1)));
    }

//     ÷    5/23    +    5    ×    23/45    34
//             5/23  ÷  5  +  23/45  ×  34
//             225/23161
    @Test
    public void divide() {
        System.out.println(new Fraction(2,23).divide(new Fraction(5,1)));
        System.out.println(new Fraction(23,45).multiply(new Fraction(34,1)));
        System.out.println(new Fraction(5,23).divide(new Fraction(5,1))
                .add(new Fraction(23,45).multiply(new Fraction(34,1))));
    }


//     ÷    4'3/4    ×    -    2    7/61    1'30/31
//             4'3/4  ÷  ( 2  -  7/61 )  ×  1'30/31
//             1'129/460
    @Test
    public void testPrint() {
        System.out.println(new Fraction(19,4).divide(new Fraction(2,1).subtract(new Fraction(7,61))
                .multiply(new Fraction(61,31))));
    }

    @Test
    public void test1() {
        System.out.println(new Fraction(2,1).subtract(new Fraction(5,16)));
    }
}