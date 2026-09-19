package com.mycompany.kidsgame;

import java.util.Scanner;

/**
 * Handles the simple command-line addition game.
 */
public class Game {

    private int score;
    private String summary = "";

    /**
     * Generates one random addition question and updates the score.
     *
     * @param input shared Scanner used to read the player's answer
     */
    public void generateAddQuestion(Scanner input) {
        int num1 = (int) (Math.random() * 20);
        int num2 = (int) (Math.random() * 20);
        int actualAnswer = num1 + num2;

        System.out.println("What is " + num1 + " + " + num2 + " ?");
        int userAnswer = input.nextInt();

        summary += "\n" + num1 + " + " + num2 + " = " + userAnswer
                + " : " + (userAnswer == actualAnswer);

        if (actualAnswer == userAnswer) {
            score++;
        }
    }

    /**
     * Prints the final score and answer summary.
     */
    public void printSummary() {
        System.out.println("Your Score: " + score);
        System.out.println("------ Summary ------" + summary);
    }
}
