package com.pramyness.demo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * IntelliJ IDEA 18
 *
 * @author Pramy
 * @date 2018/9/18
 */
public class FunctionTest {

    private Function function = new Function();

    @Test
    public void outputExercises() {
        function.outputExercises(10000,20);

    }

    @Test
    public void outputGrade() {
        function.outputGrade("Exercises.txt","Answers.txt","Grade.txt");
    }
}