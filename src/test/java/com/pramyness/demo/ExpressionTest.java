package com.pramyness.demo;

import org.junit.Test;

import java.lang.reflect.Field;

/**
 * IntelliJ IDEA 17
 * Created by Pramy on 2018/9/16.
 */
public class ExpressionTest {

    @Test
    public void print() throws CloneNotSupportedException, NoSuchFieldException, IllegalAccessException {
        for (int i = 0; i < 20; i++) {
            Expression expression = new Expression(3, 20);
            System.out.println(expression);
        }
    }

    @Test
    public void cloneObject() throws CloneNotSupportedException {
        Expression expression = new Expression(3,20);
        Expression expression1 = (Expression) expression.clone();
        expression.before();
        expression1.before();
        System.out.println(expression==expression1);
        System.out.println(expression.equals(expression1));
    }

    @Test
    public void swapTest() {
        Expression expression = new Expression(3,20);
        expression.before();
        System.out.println(expression);
        change(expression);
        expression.before();
        System.out.println(expression);
    }

    @Test
    public void testEqual() throws CloneNotSupportedException {
        Expression expression = new Expression(3,20);
        System.out.println(expression);
        Expression expression1 = (Expression) expression.clone();
        change(expression1);
        System.out.println(expression1);

        System.out.println(expression.equals(expression1));
        System.out.println("----------------------");
    }

    @Test
    public void batch() {
        for (int i = 0; i < 100; i++) {
            try {
                testEqual();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void build() {
        Expression expression = new Expression("1/15 ÷ ( 6 ÷ ( 2 × 1'3/14 ) )");
        System.out.println(expression);
    }


    private void change(Expression expression) {
        Field field;
        try {
            field = expression.getClass().getDeclaredField("root");
            field.setAccessible(true);
            swap((Expression.Node) field.get(expression));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private boolean swap(Expression.Node node) {

        if (node == null) {
            return false;
        }
        boolean flag;

        flag = swap(node.left) | swap(node.right);


        if (node instanceof Expression.SymbolNode) {
            if (((Expression.SymbolNode) node).symbol.equals("＋") || ((Expression.SymbolNode) node).symbol.equals("×")) {
                Expression.Node tmp = node.left;
                node.left = node.right;
                node.right = tmp;
                return true;
            }
        }
        return flag;
    }
}