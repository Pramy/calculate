package com.pramyness.demo;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/**
 * IntelliJ IDEA 17
 * Created by Pramy on 2018/9/16.
 */
public class Expression implements Cloneable {


    private static final String ADD = "＋";

    private static final String SUBTRACT = "-";

    private static final String MULTIPLY = "×";

    private static final String DIVIDE = "÷";

    private static final String LEFT_BRACKETS = "(";

    private static final String RIGHT_BRACKETS = ")";

    private static final String[] SYMBOLS = {ADD, SUBTRACT, MULTIPLY, DIVIDE};

    private Node root;

    private int bound;

    public Expression(int sum, int bound) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if (sum < 1) {
            throw new RuntimeException("result must greater than 0");
        }
        if (bound < 1) {
            throw new RuntimeException("bound must greater than 1");
        }
        this.bound = bound;
        if (sum == 1) {
            root = build(sum);
        } else {
            root = build(random.nextInt(sum) + 1);
        }
    }

    public Expression(String expression) {
        this.root = build(expression);
    }

    private boolean leftBrackets(String left, String mid) {
        return (isOne(left) && isTwo(mid));
    }

    private boolean rightBrackets(String right, String mid) {
        return !(isOne(mid) && isTwo(right));
    }

    private boolean isOne(String s) {
        return s.equals(ADD) || s.equals(SUBTRACT);
    }

    private boolean isTwo(String s) {
        return s.equals(MULTIPLY) || s.equals(DIVIDE);
    }

    public void before() {
        before(root, "");
    }

    private void before(Node root, String s) {
        if (root == null) {
            return;
        }
        System.out.printf("%-15shigh:%d\n", s + root.toString().trim(), root.high);
        before(root.left, s + ">");
        before(root.right, s + ">");
    }

    private Node build(int sum) {
        if (sum == 0) {
            return new Node(createFraction(bound), null, null, 1);
        }
        ThreadLocalRandom random = ThreadLocalRandom.current();
        final SymbolNode parent = new SymbolNode(null, null, SYMBOLS[random.nextInt(4)]);
        int left = random.nextInt(sum);
        parent.left = build(left);
        parent.right = build(sum - left - 1);
        Fraction result = calculate(parent.symbol, parent.left.result, parent.right.result);

        if (result.isNegative()) {
            Node tmp = parent.left;
            parent.left = parent.right;
            parent.right = tmp;
            result.abs();
        }
        parent.result = result;
        parent.high = Math.max(parent.left.high, parent.right.high) + 1;
        return parent;
    }

    private Node build(String expression) {
        String[] strings = expression.split(" ");
        Stack<Node> nodeStack = new Stack<>();
        Stack<String> symbolStack = new Stack<>();
        for (int i = 0; i < strings.length; i++) {
            if (!isSymbol(strings[i])) {
                nodeStack.push(new Node(new Fraction(strings[i]), null, null, 1));
            } else {
                while (!symbolStack.isEmpty() && !tryPush(strings[i], symbolStack.peek())) {
                    String symbol = symbolStack.pop();

                    if (symbol.equals(LEFT_BRACKETS) && strings[i].equals(RIGHT_BRACKETS)) {
                        break;
                    }
                    push(symbol, nodeStack);

                }
                //如果符号不是")"就进栈
                if (!strings[i].equals(RIGHT_BRACKETS)) {
                    symbolStack.push(strings[i]);
                }
            }
        }
        while (!symbolStack.isEmpty()) {
            push(symbolStack.pop(), nodeStack);
        }
        return nodeStack.pop();
    }

    private void push(String symbol, Stack<Node> nodeStack) {

        if (!symbol.equals(LEFT_BRACKETS)) {
            Node right = nodeStack.pop();
            Node left = nodeStack.pop();
            SymbolNode node = new SymbolNode(right, left, symbol);
            node.result = calculate(symbol, left.result, right.result);
            node.high = Math.max(left.high, right.high) + 1;
            nodeStack.push(node);
        }
    }


    private boolean tryPush(String s, String target) {
        return s.equals(LEFT_BRACKETS) || (isTwo(s) && isOne(target)) ||
                (!s.equals(RIGHT_BRACKETS) && target.equals(LEFT_BRACKETS));
    }

    private boolean isSymbol(String s) {
        return s.equals(ADD) || s.equals(SUBTRACT) || s.equals(MULTIPLY) || s.equals(DIVIDE)
                || s.equals(LEFT_BRACKETS) || s.equals(RIGHT_BRACKETS);
    }


    private Fraction createFraction(int bound) {
        if (bound == 1) {
            return new Fraction(0, 1);
        }
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int b ;
        if (random.nextBoolean()) {
            return new Fraction(random.nextInt(bound), 1);
        } else {
            if (random.nextBoolean()) {
                b = random.nextInt(bound);
                b = b == 0 ? 1 : b;
                return new Fraction(random.nextInt(bound), b);
            } else {
                b = random.nextInt(bound);
                b = b == 0 ? 1 : b;
                int a = b == 1 ? 0 : random.nextInt(b);
                return new Fraction(random.nextInt(bound) * b + a, b);
            }
        }
    }

    private Fraction calculate(String symbol, Fraction left, Fraction right) {
        switch (symbol) {
            case ADD:
                return left.add(right);
            case SUBTRACT:
                return left.subtract(right);
            case MULTIPLY:
                return left.multiply(right);
            default:
                return left.divide(right);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Expression expression = (Expression) super.clone();
        if (expression.root != null) {
            expression.root = (Node) expression.root.clone();
        }
        return expression;
    }

    @Override
    public String toString() {
        return print(root);
    }

    public String getResult() {
        if (root == null) {
            return "";
        } else {
            return root.result.toString();
        }
    }

    private String print(Node node) {
        if (node == null) {
            return "";
        }
        String left = print(node.left);

        String mid = node.toString();
        if (node.left instanceof SymbolNode && node instanceof SymbolNode) {
            if (leftBrackets(((SymbolNode) node.left).symbol, ((SymbolNode) node).symbol)) {
                left = LEFT_BRACKETS + " " + left + " " + RIGHT_BRACKETS;
            }
        }
        String right = print(node.right);
        if (node.right instanceof SymbolNode && node instanceof SymbolNode) {
            if (rightBrackets(((SymbolNode) node.right).symbol, ((SymbolNode) node).symbol)) {
                right = LEFT_BRACKETS + " " + right + " " + RIGHT_BRACKETS;
            }
        }
        return left + mid + right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Expression that = (Expression) o;

        return root.equals(that.root);
    }

    @Override
    public int hashCode() {
        return root.hashCode();
    }

    static class Node implements Cloneable {

        Fraction result;

        Node right;

        Node left;

        int high;

        Node(Fraction result, Node right, Node left, int high) {
            this.result = result;
            this.right = right;
            this.left = left;
            this.high = high;
        }

        @Override
        public String toString() {
            return result.toString();
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            Node node = (Node) super.clone();
            Node right = node.right;
            Node left = node.left;
            if (right != null) {
                node.right = (Node) right.clone();
            }
            if (left != null) {
                node.left = (Node) left.clone();
            }
            return node;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (result != null ? !result.equals(node.result) : node.result != null) return false;
            if (right != null ? !right.equals(node.right) : node.right != null) return false;
            return left != null ? left.equals(node.left) : node.left == null;
        }

        @Override
        public int hashCode() {
            int result1 = result != null ? result.hashCode() : 0;
            result1 = 31 * result1 + (right != null ? right.hashCode() : 0);
            result1 = 31 * result1 + (left != null ? left.hashCode() : 0);
            return result1;
        }
    }

    static class SymbolNode extends Node {

        String symbol;

        SymbolNode(Node right, Node left, String symbol) {
            super(null, right, left, 0);
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return " " + symbol + " ";
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SymbolNode that = (SymbolNode) o;
            //树高不一样返回false
            if (this.high != that.high) {
                return false;
            }
            boolean flag = (symbol != null ? symbol.equals(that.symbol) : that.symbol == null);
            //符号不一样就是false
            if (!flag) {
                return false;
            }
            boolean left = this.left != null ? this.left.equals(that.left) : that.left == null;
            boolean right = this.right != null ? this.right.equals(that.right) : that.right == null;

            // true & true 两棵子树一样
            if (left && right) {
                return true;
            }

            // false,true 和 true,false
            if (left ^ right) {
                return false;
            }
            //false,false
            if (that.symbol == null) {
                return false;
            }
            if (this.symbol.equals(ADD) || this.symbol.equals(MULTIPLY)) {
                left = this.left != null && this.left.equals(that.right);
                right = this.right != null && this.right.equals(that.left);
            }

            return left && right;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
            return result;
        }
    }

}
