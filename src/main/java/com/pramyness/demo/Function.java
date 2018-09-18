package com.pramyness.demo;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<Expression> set = new HashSet<>();
        try (BufferedWriter exercisesWriter = new BufferedWriter(new FileWriter("Exercises.txt"));
             BufferedWriter answerWriter = new BufferedWriter(new FileWriter("Answers.txt"))
        ) {
            for (int i = 1; set.size()< sum;) {
                try {
                    Expression expression = new Expression(3, bound);
                    if (!set.contains(expression)) {
                        exercisesWriter.write(i + "." + expression.toString() + "\n");
                        answerWriter.write(i + "." + expression.getResult() + "\n");
                        set.add(expression);
                        i++;
                    }
                } catch (RuntimeException ignored) {

                }

            }
            exercisesWriter.flush();
            answerWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void outputGrade(String exercisePath, String answerPath, String gradePath) {
        try (BufferedReader exReader = new BufferedReader(new FileReader(exercisePath));
             BufferedReader anReader = new BufferedReader(new FileReader(answerPath));
             BufferedWriter gradeWriter = new BufferedWriter(new FileWriter(gradePath))
        ) {
            String ex, an;
            int c = 0, w = 0;
            StringBuilder correct = new StringBuilder("Correct: %d (");
            StringBuilder wrong = new StringBuilder("Wrong: %d (");
            while ((ex = exReader.readLine()) != null && (an = anReader.readLine()) != null) {
                int exPoint = ex.indexOf(".");
                int anPoint = an.indexOf(".");
                if (exPoint != -1 && anPoint != -1) {
                    int i = Integer.valueOf(ex.substring(0,exPoint).trim());
                    Expression expression = new Expression(ex.substring(exPoint + 1));
                    Fraction answer = new Fraction(an.substring(anPoint + 1));
                    if (expression.getResult().equals(answer.toString())) {
                        c++;
                        correct.append(" ").append(i);
                    } else {
                        w++;
                        wrong.append(" ").append(i);
                    }
                }
            }
            gradeWriter.write(String.format(correct.append(" )\n").toString(),c));
            gradeWriter.write(String.format(wrong.append(" )\n").toString(),w));
            gradeWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
