package com.pramyness.demo;


/**
 * IntelliJ IDEA 17
 * Created by Pramy on 2018/9/16.
 */
public class Fraction {

    private int a;

    private int b;

    public Fraction(String string) {
        string = string.trim();

        int a, b;
        int cc = string.indexOf("'");
        int bb = string.indexOf("/");
        if (cc != -1) {

            int c = Integer.valueOf(string.substring(0, cc));
            b = Integer.valueOf(string.substring(bb + 1));
            a = c * b + Integer.valueOf(string.substring(cc + 1, bb));
        } else if (bb != -1) {
            b = Integer.valueOf(string.substring(bb + 1));
            a = Integer.valueOf(string.substring(0, bb));
        } else {
            a = Integer.valueOf(string);
            b = 1;
        }
        adjust(a,b);
    }

    public Fraction(int a, int b) {
        adjust(a,b);
    }

    private void adjust(int a, int b) {
        if (b == 0) {
            throw new RuntimeException("分母不能为0");
        }
        boolean isNegative = false;
        if ((a < 0 && b > 0) || (a > 0 && b < 0)) {
            isNegative = true;
        }
        a = Math.abs(a);
        b = Math.abs(b);
        int c = gcd(a, b);
        this.a = isNegative ? -a / c : a / c;
        this.b = b / c;
    }


    public Fraction add(Fraction fraction) {
        return new Fraction(this.a * fraction.b + fraction.a * this.b,
                this.b * fraction.b);
    }

    public Fraction subtract(Fraction fraction) {
        return new Fraction(this.a * fraction.b - fraction.a * this.b,
                this.b * fraction.b);
    }

    public Fraction multiply(Fraction fraction) {
        return new Fraction(this.a * fraction.a,
                this.b * fraction.b);
    }

    public Fraction divide(Fraction fraction) {
        return new Fraction(this.a * fraction.b, b * fraction.a);
    }

    public void abs() {
        this.a = Math.abs(this.a);
        this.b = Math.abs(this.b);
    }

    public boolean isNegative() {
        return a < 0;
    }

    private int gcd(int a, int b) {
        int mod = a % b;
        return mod == 0 ? b : gcd(b, mod);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fraction fraction = (Fraction) o;

        if (a != fraction.a) return false;
        return b == fraction.b;
    }

    @Override
    public int hashCode() {
        int result = a;
        result = 31 * result + b;
        return result;
    }

    @Override
    public String toString() {
        if (b == 1) {
            return String.valueOf(a);
        } else {
            int i = a / b;
            if (i != 0) {
                return String.format("%d'%d/%d", i, Math.abs(a) - b * Math.abs(i), b);
            } else {
                return String.format("%d/%d", a, b);
            }
        }
    }
}
