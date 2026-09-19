package com.mycompany.kidsgame;

import java.util.Scanner;

/**
 * Command-line version of the kids math game.
 */
public class KidsGame {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Game game = new Game();

        System.out.println("How many questions?");
        int nQuestions = input.nextInt();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < nQuestions; i++) {
            game.generateAddQuestion(input);
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("You have taken: " + totalTime / 1000 + " seconds");
        game.printSummary();

        input.close();
    }
}
