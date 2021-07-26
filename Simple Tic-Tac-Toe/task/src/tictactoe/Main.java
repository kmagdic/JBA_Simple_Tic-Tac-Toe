package tictactoe;

import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static char field[][] = {
            { '_', '_', '_'},
            { '_', '_', '_'},
            { '_', '_', '_'},
    };



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /*
        System.out.print("Enter cells: ");
        String input = scanner.nextLine();
        makeFieldsFromStr(input);
        */

        boolean endOfGame = false;
        boolean nextMoveIsX = true;
        do {
            printGameGrid();

            String gameStatus = checkGameStatus();
            // if gameStatus is different from "Game not finished" -> finish the game
            if (!gameStatus.equals("Game not finished")) {
                System.out.println(gameStatus);
                endOfGame = true;
                continue;
            }


            boolean nextMoveIsNotCorrect = true;
            do {
                int nextMoveRow, nextMoveCol;
                System.out.print("Enter the coordinates: ");
                try {
                    nextMoveRow = scanner.nextInt() - 1;
                    nextMoveCol = scanner.nextInt() - 1;
                } catch (InputMismatchException e) {
                    System.out.println("You should enter numbers!");
                    scanner.nextLine();
                    continue;
                }

                // check if next move coordinates are correct
                if (nextMoveRow < 0 || nextMoveRow > 2 || nextMoveCol < 0 || nextMoveCol > 2) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
                if (field[nextMoveRow][nextMoveCol] != '_') {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }

                field[nextMoveRow][nextMoveCol] = (nextMoveIsX ? 'X' : 'O');
                nextMoveIsX = !nextMoveIsX;
                nextMoveIsNotCorrect = false;

            } while (nextMoveIsNotCorrect);

        } while (!endOfGame);


    }

    private static void makeFieldsFromStr(String input) {
        for (int currRow = 0; currRow < 3; currRow ++) {
            for (int currField = 0; currField < 3; currField ++) {
                field[currRow][currField]  = input.charAt(currRow * 3 + currField);
            }
        }
    }

    private static String checkGameStatus() {

        // checkImpossible
        if ( (checkThreeInRow('X') && checkThreeInRow('O')) ||
                (checkThreeInCol('X') && checkThreeInCol('O')) ||
                Math.abs(countSideChar('X') - countSideChar('O')) > 1)
            return "Impossible";


        // find three in row
        if (checkThreeInRow('X'))
            return "X wins";
        if (checkThreeInRow('O'))
            return "O wins";


        // find three in column
        if (checkThreeInCol('X'))
            return "X wins";
        if (checkThreeInCol('O'))
            return "O wins";

        // find three sidelings
        if (checkThreeSlideing('X'))
            return "X wins";
        if (checkThreeSlideing('O'))
            return "O wins";

        // has empty fields
        for (int currRow = 0; currRow < 3; currRow ++) {
            for (int currCol = 0; currCol < 3; currCol ++) {
                if (field[currRow][currCol] == '_')
                    return "Game not finished";
            }
        }

        // if doesn't have empty fields it is draw
        return "Draw";

    }

    private static int countSideChar(char sideChar) {
        int counter = 0;
        for (int currRow = 0; currRow < 3; currRow ++) {
            for (int currCol = 0; currCol < 3; currCol ++) {
                if (field[currRow][currCol] == sideChar) {
                    counter++;
                }
            }
        }

        return counter;
    }

    private static boolean checkThreeInRow(char sideChar) {
        for (int currRow = 0; currRow < 3; currRow ++) {
            if (field[currRow][0] == sideChar && field[currRow][1] == sideChar && field[currRow][2] == sideChar) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkThreeSlideing(char sideChar) {
        if (field[0][0] == sideChar && field[1][1] == sideChar && field[2][2] == sideChar) {
            return true;
        }

        if (field[0][2] == sideChar && field[1][1] == sideChar && field[2][0] == sideChar) {
            return true;
        }

        return false;
    }

    private static boolean checkThreeInCol(char sideChar) {
        for (int currCol = 0; currCol < 3; currCol ++) {
            if (field[0][currCol] == sideChar && field[1][currCol] == sideChar && field[2][currCol] == sideChar) {
                return true;
            }
        }
        return false;
    }

    private static void printGameGrid() {
        System.out.println("---------");
        for (int currRow = 0; currRow < 3; currRow ++) {
            System.out.print("| ");
            for (int currField = 0; currField < 3; currField ++) {
                char c = field[currRow][currField];
                if  (c == '_')
                    c = ' ';
                System.out.print(c + " ");

            }
            System.out.println("|");
        }
        System.out.println("---------");
    }



}
