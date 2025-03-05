package tictactoe;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final int BOARD_SIZE = 3;
    private static final char EMPTY_POSITION = ' ';
    private static final char X_SYMBOL = 'X';
    private static final char O_SYMBOL = 'O';

    public static void buildGame(String input) {
        StringBuilder ticTacToeGame = new StringBuilder("---------").append("\n");
        String[] lines = input.split("(?<=\\G...)");

        for(String line: lines) {
            ticTacToeGame.append("|").append(line.replace("", " ")).append("|").append("\n");
        }
        ticTacToeGame.append("---------");
        System.out.println(ticTacToeGame);
    }

    public static boolean isNumericInput(String stringToValidate) {
        return stringToValidate.matches(".*[^0-9\\s].*");
    }

    public static boolean isFormattedCorrectly(String stringToValidate) {
        return stringToValidate.matches("\\d+ \\d+");
    }

    public static int[] validateAndExtractCoordinates(String gameState, String stringToValidate) {
        System.out.println(stringToValidate);

        if(isNumericInput(stringToValidate)) {
            System.out.println("You should enter numbers!");
            return new int[]{-1};
        }

        if(!isFormattedCorrectly(stringToValidate)) {
            System.out.println("Your input is in the wrong format! Enter it correctly!");
            return new int[]{-1};
        }

        String[] stringCoordinates = stringToValidate.split("\\s+");
        int[] coordinates = new int[stringCoordinates.length];

        for(int i = 0; i < stringCoordinates.length; i++ ) {
            try {
                int coordinate = Integer.parseInt(stringCoordinates[i]);
                if (coordinate < 1 || coordinate > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    return new int[] {-1};
                }
                coordinates[i] = coordinate;
            } catch (NumberFormatException e ) {
                System.out.println("Coordinates should be from 1 to 3!");
                return new int[] {-1};
            }
        }


        if(gameState.charAt(((coordinates[0] - 1) * BOARD_SIZE) + (coordinates[1] - 1)) != EMPTY_POSITION){
            System.out.println("This cell is occupied! Choose another one!");
            return new int[]{-1};
        }

        return coordinates;
    }

    public static boolean checkWin(String gameState, char player) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            // Check rows
            if (gameState.charAt(i * BOARD_SIZE) == player &&
                gameState.charAt(i * BOARD_SIZE + 1) == player &&
                gameState.charAt(i * BOARD_SIZE + 2) == player) {
                return true;
            }

            // Check columns
            if (gameState.charAt(i) == player &&
                gameState.charAt(i + BOARD_SIZE) == player &&
                gameState.charAt(i + 2 * BOARD_SIZE) == player) {
                return true;
            }
        }

        // Check diagonals
        return (gameState.charAt(4) == player) &&
               (gameState.charAt(2) == player && gameState.charAt(6) == player ||
                gameState.charAt(0) == player && gameState.charAt(8) == player);

    }
    public static boolean checkGameState(String gameState, char player) {
        if (checkWin(gameState, player)) {
            buildGame(gameState);
            System.out.println(player + " wins");
            return true;
        }
        if(!gameState.contains(String.valueOf(EMPTY_POSITION))){
            buildGame(gameState);
            System.out.println("Draw");
            return true;
        }

        return false;
    }
    public static void makeMove(StringBuilder gameState, int line, int column, char move) {
        int index = ((line-1) * BOARD_SIZE) + (column - 1);
        gameState.replace(index, index+1, String.valueOf(move));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder ticTacToeGame = new StringBuilder("         ");

        boolean isGameOver;
        boolean isXTurn = true;

        do {
            char currentPlayer = isXTurn ? X_SYMBOL : O_SYMBOL;
            // Build game visually
            buildGame(ticTacToeGame.toString());

            int[] coordinates;
            do {
                String makeMoveInput = scanner.nextLine();
                coordinates = validateAndExtractCoordinates(ticTacToeGame.toString(), makeMoveInput);
            } while (Arrays.equals(coordinates, new int[]{-1}));

            makeMove(ticTacToeGame, coordinates[0], coordinates[1], currentPlayer);

            // Check game state
            isGameOver = checkGameState(ticTacToeGame.toString(), currentPlayer);
            isXTurn = !isXTurn;
        } while (!isGameOver);
    }
}
