import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;  // Note: Do not change this line.

    /**
     * Scans the number of games, than plays the student game the specified amount of times.
     *
     * @param args (String[]) args[0] is the path to the input file
     */
    public static void main(String[] args) throws IOException {
        String path = args[0];
        scanner = new Scanner(new File(path));
        int numberOfGames = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= numberOfGames; i++) {
            System.out.println("Game number " + i + " starts.");
            theStudentsGame();
            System.out.println("Game number " + i + " ended.");
            System.out.println("-----------------------------------------------");
        }
        System.out.println("All games have ended.");
    }

    /**
     * Add your description
     */
    public static void theStudentsGame() {
        int[][][] semestersBox;
        char[][] prevBoard, currentBoard;
        int semesterCount = 0, prevTakeen, currentTakeen = 0;
        System.out.println("Dear president, please enter the board’s size.");
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        semestersBox = new int[rows][cols][];
        prevBoard = new char[rows][cols];
        currentBoard = new char[rows][cols];
        System.out.println("Dear president, please enter the cell’s indexes.");
        prevTakeen = initBoardIndexes(prevBoard, rows, cols);
        while(prevTakeen != currentTakeen && currentTakeen != 0){
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (Takeen(prevBoard, rows, cols, i, j)){
                        currentBoard[i][j] = 'X';
                    }else{
                        currentBoard[i][j] = '-';
                    }
                }
            }
        }


    }
    //**************************//
    public static int initBoardIndexes(char[][] board, int row_max, int col_max){
        int rows = 0, cols = 0, i = 0;
        while(true){
            if(scanner.hasNextInt()){
                rows = scanner.nextInt();
            }
            if(scanner.hasNextInt()){
                cols = scanner.nextInt();
            }

            if(rows < 0 || rows >= row_max || cols < 0 || cols >= col_max){
                System.out.print("The cell is not within the board’s boundaries, enter a new cell.");
            }
            if (board[rows][cols] == '-'){
                board[rows][cols] = 'X';
                i++;
            } else if (board[rows][cols] == 'X') {
                board[rows][cols] = '-';
                i--;
            }
            if (scanner.hasNext("Yokra")){
                break;
            }

        }
        return i;
        //*******************//
    }
    public static int countX(int[][] board, int rows, int columns){
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == 'X'){
                    count++;
                }
            }
        }
        return count;
    }
    public static void printBoard(char[][] board, int rows, int columns){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.println(board[i][j]);
            }
        }
    }
    public static void updateBoard(char[][] prevBoard, char[][] currentBoard, int rows, int columns){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (Takeen(prevBoard, rows, columns, i, j)) {
                    currentBoard[i][j] = 'X';
                }else{
                    currentBoard[i][j] = '-';
                }
            }
        }

    }
    public static boolean Takeen(char[][] board, int maxRows, int maxCols, int row, int col){
        boolean takeen = false;
        int startRow = Math.max(0, row - 1);
        int endRow = Math.min(maxRows - 1, row + 1);
        int startCol = Math.max(0, col - 1);
        int endCol = Math.min(maxCols - 1, col + 1);
        int takeenFriends = 0;
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                if (i == row && j == col) {
                    continue;
                }
                if (board[i][j] == 'X'){
                    takeenFriends++;
                }
            }
        }
        if(board[row][col] == 'X'){
            takeen = takeenFriends > 1 && takeenFriends <= 3;
        }else if (board[row][col] == '-'){
            takeen = takeenFriends == 3;
        }
        return takeen;
    }
}