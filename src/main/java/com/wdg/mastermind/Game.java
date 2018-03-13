package com.wdg.mastermind;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class Game {

    private final int maxNumberOfTurn;

    private Combination random;

    private final List<Turn> userCombinations = new ArrayList<>();

    private final static String TURN_TEMPLATE = "|%s| %s | %s | %d/%d |";

    private Game(int maxNumberOfTurn) {
        this.maxNumberOfTurn = maxNumberOfTurn;
    }

    private void start() throws Exception {

        random = new Combination.RandomBuilder().build();

        System.out.println("J’ai choisi ma combinaison, à vous de deviner ! ");
        System.out.println("Les couleurs possibles sont R J B O V et N. Tapez (RJBO) pour tenter les couleurs R,J,B et O dans l’ordre.");
        System.out.println("Voici la grille actuelle:");
        System.out.println(this.toString());
        newTurn(1);
    }

    private void newTurn(int round) throws Exception {

        System.out.print("Vous > ");
        Scanner in = new Scanner(System.in);
        String newCombination = in.next();

        try {
            Turn turn = new Turn();
            turn.round = round;
            userCombinations.add(turn);

            Combination combination = new Combination.Parser(newCombination).build();
            turn.combination = combination;
            turn.result = combination.compare(random.toString());

            //System.out.println("Ordinateur > ");
            System.out.println("Ordinateur > (" + random.toString() + ")");
            System.out.println(this.toString());

            if (turn.result.isCombinationFound()) {
                System.out.println("Bravo ! Vous avez gagné en " + turn.round + " tours !");
            } else if (turn.round > maxNumberOfTurn) {
                System.out.println("Dommage ! Vous avez perdu");
            } else {
                newTurn(round + 1);
            }

        } catch (Exception e) {
            if (e.getCause() instanceof ParseException) {
                System.out.println("Ordinateur > (" + e.getCause().getMessage() + ")");
                System.out.println(this.toString());
                newTurn(round + 1);
            } else {
                throw e;
            }
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("|-------------------|").append("\n");
        userCombinations.stream().forEach(p -> {
            if (p.combination != null) {
                sb.append(String.format(TURN_TEMPLATE, p.combination.toString(), p.result.getNumberOfCorrectPieces(), p.result.getNumberOfMismatchPieces(), p.round, maxNumberOfTurn)).append("\n");
            } else {
                sb.append(String.format(TURN_TEMPLATE, "XXXX", "0", "0", p.round, maxNumberOfTurn)).append("\n");
            }
        });
        sb.append(String.format(TURN_TEMPLATE, "....", ".", ".", userCombinations.size() + 1, maxNumberOfTurn)).append("\n");
        sb.append("|-------------------|").append("\n");
        return sb.toString();
    }

    public class Turn {
        private Combination combination;
        private Result result;
        private int round;
    }

    public static void main(String... args) throws Exception {

        Game game = new Game(10);
        game.start();
    }
}
