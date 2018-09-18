package com.pramyness.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * IntelliJ IDEA 18
 *
 * @author Pramy
 * @date 2018/9/18
 */
public class Function {


    public void outputExercises(int bound) {
        outputExercises(50, bound);
    }

    public void outputExercises(int sum, int bound) {
        if (bound <= 0 || sum <= 0) {
            throw new RuntimeException("bound or sun must greater than 0");
        }
        File exercisesFile = new File("Exercises.txt");
        File answerFile = new File("Answers.txt");

        try (BufferedWriter exercisesWriter = new BufferedWriter(new FileWriter(exercisesFile));
             BufferedWriter answerWriter = new BufferedWriter(new FileWriter(answerFile))
        ) {
            for (int i = 1; i <= sum; i++) {
                Expression expression = new Expression(3, bound);
                exercisesWriter.write(i+"."+expression.toString() + "\n");
                answerWriter.write(i+"."+expression.getResult() + "\n");
            }
            exercisesWriter.flush();
            answerWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
