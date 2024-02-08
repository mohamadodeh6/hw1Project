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

    /**
     * countX method counts the number of Takeen students in a given board
     * @param board - current game board
     * @param rows - number of rows in the current board
     * @param columns - number of columns in the current board
     * @return - number of Takeen students on the current board
     */
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
     * theStudentsGame method is the driver function of the game
     * it runs the current game when being called from the main method of the program
     * this method handles the game flow by calling all the other methods that are responsible for checking
     * the game rules and displaying the GUI to the player
     * in other words, this method is the engine of the game
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

    /**
     * The method initiates the first board of the game by filling it with the character '-'
     * @param board - empty board
     * @param rows - the number of rows
     * @param cols - the number of columns
     */
    public static void initBoard(char[][] board, int rows, int cols){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = '-';
            }
        }
    }
    //**********//

    /**
     * initBoardIndexes initiates the first board according to the indexes that the user enters
     * indexes symbolize the Takeen students on the current board
     * @param board - the board of the game
     * @param row_max - maximum number of rows allowed int the board
     * @param col_max - maximum number of columns allowed in the board
     * @return - the initial number of Takeen students in the first board of the current game
     */
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
        }

        return i;
        //*******//
    }

    /**
     * The method prints the game board to the user
     * @param board - the board of the game
     * @param rows - number of rows
     * @param columns - number of columns
     */
    public static void printBoard(char[][] board, int rows, int columns){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * updateBoard method updates the current game board by changing the status of each student
     * depending on the return value of Takeen function
     * @param prevBoard - the game board of the previous semester
     * @param currentBoard - the game board of the current semester
     * @param rows - number of rows of the game board
     * @param columns - number of columns of the game board
     */
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

    /**
     * saveGame method handles the task of saving history of the game board in each semester
     * @param semBox - multi-dimension array that stores the history of the students board for each semester
     * @param board - game board
     * @param semNumber - the number of the current semester
     * @param rows - number of boards' rows
     * @param cols - number of boards' columns
     */
    public static void saveGame(int[][][] semBox, char[][] board, int semNumber, int rows, int cols){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                semBox[semNumber][i][j] = board[i][j];
            }
        }
    }

    /**
     * Takeen function checks whether the student at a given index remains Takeen or not
     * @param board - the board of the game
     * @param maxRows - maximum number of rows allowed
     * @param maxCols - maximum number of columns allowed
     * @param row - a specific row index in the board
     * @param col - a specific column index int the board
     * @return - true if the student at the specified index remains Takeen, false otherwise
     */
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

    /**
     * switchBoard function copies the !!!!!!!!!!!!!!!! Complete this please !!!!!!!!!!!!!!!
     * @param prevBoard - the board of the previous semester
     * @param currentBoard - the board of the current semester
     * @param rows - number of rows of the current board
     * @param columns - number of columns of the current board
     */
    public static void switchBoard(char[][] prevBoard, char[][] currentBoard, int rows, int columns){
        for (int i = 0; i < rows; i++) {
            if (columns >= 0) System.arraycopy(currentBoard[i], 0, prevBoard[i], 0, columns);
        }
    }

    /**
     * sameBoards method compares the current game board with the previous one
     * @param prevBoard - the board of the previous game
     * @param currBoard - the board of the current game
     * @param rows - number of rows
     * @param columns - number of columns
     * @return - true if the current board and the previous board are identical, false otherwise
     */
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