package com.example.minesweepersolver;

import java.util.Arrays;

public class Board {
    final public static int FLAG = 11;
    final public static int SPACE = 10;
    final public static int MINE = 9;
    static boolean[][] board = new boolean[Main.numCols][Main.numRows];
    static int[][] displayBoard = new int[Main.numCols][Main.numRows];

    // a function that tells you what number a should be at a certain
    public static void reveal(int rowNum, int colNum){
        if(rowNum>=Main.numCols || colNum>=Main.numRows){
            System.out.println("index out of bounds at reveal");
            System.exit(1);
        } else if (board[rowNum][colNum]) {
            displayBoard[rowNum][colNum] = MINE;
        } else {
            int count = 0;
            for(int i = -1;i<2;i++){
                for(int j = -1;j<2;j++){
                    try{
                       if(board[rowNum+i][colNum+j]){
                           count++;
                       }
                    }
                    catch (IndexOutOfBoundsException ignored){}
                }
            }
            displayBoard[rowNum][colNum] = count;
            if(count==0){
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        try {
                            if (displayBoard[rowNum + i][colNum + j] != 0 && !(i==0 && j==0)) {
                                reveal(rowNum + i, colNum + j);
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException ignored){
                        }
                    }
                }
            }
        }
    }

    public static void revealAll(){
        for(int i = 0; i<Main.numCols;i++){
            for(int j = 0; j<Main.numRows;j++){
                if(board[i][j]){
                    displayBoard[i][j] = MINE;
                }
            }
        }
    }

    public static boolean hasWon(){
        for(int i = 0; i<Main.numCols;i++){
            for(int j = 0; j<Main.numRows;j++){
                if(!board[i][j] && displayBoard[i][j]==SPACE){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean hasLost(){
        for (int[] column : displayBoard) {
            for (int square : column) {
                if (square == MINE) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void reset(){
        board = new boolean[Main.numCols][Main.numRows];
        displayBoard = new int[Main.numCols][Main.numRows];
        for (int[] row : displayBoard) {
            Arrays.fill(row, SPACE);
        }
        initBoard();
    }

    // method that updates the displayBoard after a click at x,y
    public static void handleClick(int rowNum, int colNum){
        if(0<displayBoard[rowNum][colNum] && displayBoard[rowNum][colNum]<9){
            chord(rowNum,colNum);
        }
        else if (displayBoard[rowNum][colNum]==SPACE) {
            reveal(rowNum, colNum);
        }
    }

    public static int numFlags(int x, int y){
        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {

                if ((x + i) < Main.numCols && (x + i) > -1 &&
                        (y + j) < Main.numRows && (y + j) > -1 &&
                        !((i) == 0 && (j) == 0) &&
                         displayBoard[x+i][y+j] == FLAG) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void chord(int x , int y){
        if(numFlags(x,y)==displayBoard[x][y]) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {

                    if ((x + i) < Main.numCols && (x + i) > -1 &&
                            (y + j) < Main.numRows && (y + j) > -1 &&
                            !((i) == 0 && (j) == 0) &&
                            displayBoard[x+i][y+j] == SPACE) {
                        reveal(x + i, y + j);
                    }
                }
            }
        }
    }

    public static void flag(int x, int y){
        if(displayBoard[x][y]== SPACE){
            displayBoard[x][y]=FLAG;
        } else if (displayBoard[x][y]==FLAG) {
            displayBoard[x][y]= SPACE;
        }
    }

    public static void initBoard(){
        for (int[] row : displayBoard) {
            Arrays.fill(row, SPACE);
        }
        int minesLeft = Main.numMines;

        if(minesLeft>Main.numCols*Main.numRows){
            System.out.println("too many mines");
            System.exit(1);
        }
      /* board[0][0] = true;
       board[0][2] = true;
   */     while(minesLeft>0){
            int ranX = (int)Math.floor(Math.random()*Main.numCols);
            int ranY = (int)Math.floor(Math.random()*Main.numRows);
            if(!board[ranX][ranY]){
                board[ranX][ranY] = true;
                minesLeft--;
            }
        }
    }



}
