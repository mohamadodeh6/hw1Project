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
        String path = args[ 0];
        scanner = new Scanner(new File(path));
        int numberOfGames = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= numberOfGames; i++) {
            System.out.println("Game number " + i + " starts.");
            theStudentsGame();
            System.out.println("Game number " + i + " ended.");
            System.out.println("-----------------------------------------------");
        }
        System.out.print("All games have ended.");
    }
    /* returns the number of Takeen students
    * */
    public static int countX(char[][] board, int rows, int columns){
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
    /**
     * Add your description
     */
    public static void theStudentsGame() {
        int[][][] semestersBox;
        char[][] prevBoard, currentBoard;
        int  prevTakeen, currentTakeen = -1, maxSemesters = 100;
        System.out.println("Dear president, please enter the board’s size.");
        String input = scanner.nextLine();
        String[] dimensions = input.split(" X ");
        int rows = Integer.parseInt(dimensions[0]);
        int cols = Integer.parseInt(dimensions[1]);
        semestersBox = new int[maxSemesters][rows][cols];
        prevBoard = new char[rows][cols];
       initBoard(prevBoard, rows, cols);
        currentBoard = new char[rows][cols];
        prevTakeen = initBoardIndexes(prevBoard, rows, cols);
        int i = 0;
        do {
//            if (prevTakeen == 0){
//                System.out.println("There are no more students.");
//                break;
//            }
            if (i == maxSemesters){
                System.out.println("The semesters limitation is over.");
                break;
            }
            System.out.println("Semester number " + (i+1) + ":");
            printBoard(prevBoard, rows, cols);
            updateBoard(prevBoard, currentBoard, rows, cols);
            saveGame(semestersBox, prevBoard, i, rows, cols);
            prevTakeen = countX(prevBoard, rows, cols);
            currentTakeen = countX(currentBoard, rows, cols);
            if (prevTakeen == 0){
                System.out.println("Number of students: " + prevTakeen);
                System.out.println();
                System.out.println("There are no more students.");
                break;
            }
            if (sameBoards(prevBoard, currentBoard, rows, cols)){
                System.out.println("Number of students: " + prevTakeen);
                System.out.println();
                System.out.println("The students have stabilized.");
                break;
            }

            switchBoard(prevBoard, currentBoard, rows,cols);
            System.out.println("Number of students: " + prevTakeen);
            System.out.println();
            i++;
        } while (i <= maxSemesters);

    }
    /* initiates the first board
    * */
    public static void initBoard(char[][] board, int rows, int cols){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = '-';
            }
        }
    }
    //**********//
    /* gets the Takeen indexes and fills the first board
    * */
    public static int initBoardIndexes(char[][] board, int row_max, int col_max){
        int rows, cols, i = 0;
        boolean isPrint=true;
        while(true){
            if (isPrint){
                System.out.println("Dear president, please enter the cell’s indexes.");

            }
            String input = scanner.nextLine();

            if (input.equals("Yokra")) {
                // If the current token is "Yokra", consume it and break out of the loop
//scanner.nextLine();
                break;
            }
            String[] dimensions = input.split(", ");
             rows = Integer.parseInt(dimensions[0]);
             cols = Integer.parseInt(dimensions[1]);

            if(rows < 0 || rows >= row_max || cols < 0 || cols >= col_max){
                System.out.println("The cell is not within the board’s boundaries, enter a new cell.");
                isPrint=false;
                continue;
            }
            isPrint=true;
            if (board[rows][cols] == '-'){
                board[rows][cols] = 'X';
                i++;
            } else if (board[rows][cols] == 'X') {
                board[rows][cols] = '-';
                i--;
            }
//            if (scanner.hasNext("Yokra")){
//                scanner.nextLine();
//                break;
//            }

        }
//        System.out.println("Dear president, please enter the cell’s indexes.");

        return i;
        //*******//
    }
    /* displays the current board
    * */
    public static void printBoard(char[][] board, int rows, int columns){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
    /* creates the next board based on the previous one
    according to the Takeen method's conditions
    * */
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
    /* saves the history of boards during the whole game
    * */
    public static void saveGame(int[][][] semBox, char[][] board, int semNumber, int rows, int cols){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                 semBox[semNumber][i][j] = board[i][j];
            }
        }
    }
    /* returns whether the student remains Takeen
    according to the game rules
    * */
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
    /* copies the current board to the previous one
    * */
    public static void switchBoard(char[][] prevBoard, char[][] currentBoard, int rows, int columns){
        for (int i = 0; i < rows; i++) {
            if (columns >= 0) System.arraycopy(currentBoard[i], 0, prevBoard[i], 0, columns);
        }
    }
    public static boolean sameBoards(char[][] prevBoard, char[][] currBoard, int rows, int columns){
        boolean same = true;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (prevBoard[i][j] != currBoard[i][j]){
                    same = false;
                }
            }
        }
        return same;
    }

}
