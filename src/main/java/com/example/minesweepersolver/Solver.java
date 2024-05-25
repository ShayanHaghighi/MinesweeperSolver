package com.example.minesweepersolver;

import java.util.Arrays;

public class Solver {
    private static int MINE = 9;
    static int FLAG = 11;
    private static int CHOSEN_SPACE = 12;
    private static int SPACE = 10;
    static double[][] allPossibilities = new double[Main.numCols][Main.numRows];
    static int level = 0;
    static int numSolutions = 0;

    public static double[][] solveAll(int[][] board){
        allPossibilities = new double[Main.numCols][Main.numRows];
        for (double[] col : allPossibilities) {
            Arrays.fill(col, -1);
        }
        numSolutions = 0;
        findAllSolutions(board);
        divArray(allPossibilities,numSolutions);
        return allPossibilities;
    }

    public static double[][] returnProbs(int[][] board){
        allPossibilities = new double[Main.numCols][Main.numRows];
        for (double[] col : allPossibilities) {
            Arrays.fill(col, -1);
        }
        numSolutions = 0;
        for(int i = 0; i<board.length;i++){
            for(int j = 0; j<board[i].length;j++){
                if(board[i][j]==FLAG){
                    board[i][j] = SPACE;
                }
            }
        }
        findAllSolutions(board);
        divArray(allPossibilities,numSolutions);
        return allPossibilities;
    }

    public static void divArray(double[][] array, int divisor){
        for(int i = 0; i<array.length;i++){
            for(int j = 0; j<array[i].length;j++){
                if(array[i][j] != -1) {
                    array[i][j] = array[i][j] / (double) (divisor);
                }
            }
        }
    }


    private static void findAllSolutionsWithFlags(int[][] board){
//        int[] square = findEmptySquareWithOrdering(board);
        int[] square = findEmptySquare(board);

        if(square==null){
            numSolutions++;
            addToArray(board);
            return;
        }

        // trying with a mine
        board[square[0]][square[1]] = MINE;
        if (isValidBoardWithFlags(board)){
            findAllSolutionsWithFlags(board);
        }

        // trying with a space
        board[square[0]][square[1]] = CHOSEN_SPACE;
        if (isValidBoardWithFlags(board)){
            findAllSolutionsWithFlags(board);
        }

        // reset back to normal
        board[square[0]][square[1]] = SPACE;
    }



    // TODO: ordering
    private static void findAllSolutions(int[][] board){
//        int[] square = findEmptySquareWithOrdering(board);
        int[] square = findEmptySquare(board);

        if(square==null){
            numSolutions++;
            addToArray(board);
            return;
        }

        // trying with a mine
        board[square[0]][square[1]] = MINE;
        if (isValidBoard(board)){
            findAllSolutions(board);
        }

        // trying with a space
        board[square[0]][square[1]] = CHOSEN_SPACE;
        if (isValidBoard(board)){
            findAllSolutions(board);
        }

        // reset back to normal
        board[square[0]][square[1]] = SPACE;
    }

    private static void addToArray(int[][] board){
        for(int i = 0; i<board.length;i++){
            for(int j = 0; j<board[i].length;j++){
                if((board[i][j]==CHOSEN_SPACE || board[i][j]==MINE) && allPossibilities[i][j] == -1){
                    allPossibilities[i][j] = 0;
                }
                if(board[i][j]==MINE) {
                    allPossibilities[i][j]++;
                }
            }
        }
    }

    public static int[] findEmptySquare(int[][] board){
        for(int rowNum = 0; rowNum<board.length;rowNum++){
            for(int colNum = 0; colNum<board[rowNum].length;colNum++){
                if(board[rowNum][colNum]==SPACE){
                    if(!isIsolated(board,rowNum,colNum)){
                        return new int[] {rowNum,colNum};
                    }
                }
            }
        }
        return null;
    }

    public static int[] findEmptySquareWithOrdering(int[][] board){
        if(findEmptySquare(board)==null) {
            return null;
        }
        int minCol=0,minRow=0;
        int minRange = 10;
        for(int i = 0; i<board.length;i++){
            for(int j = 0; j<board[i].length;j++){
                if(0 < board[i][j] && board[i][j] < 9){
                    int[] bounds = upperAndLowerBounds(board,i,j);
                    if(minRange>bounds[1]-bounds[0]){
                        minRange = bounds[1]-bounds[0];
                        minRow = i;
                        minCol = j;
                    }
                }
            }
        }
        int[] square = findSquare(board,minRow,minCol);
        if(square != null){
            return square;
        } else{
            System.exit(1);
            return null;
        }
    }

    private static int[] findSquare(int[][] board, int row, int col){
        System.out.println("search around " + row + ", " + col);
        for(int i = -1;i<2;i++){
            for(int j = -1;j<2;j++){
                try{
                    if(board[row+i][col+j]==SPACE){
                        return new int[] {row+i,col+j};
                    }
                }
                catch (IndexOutOfBoundsException ignored){}
            }
        }
        return null;
    }

    private static boolean isIsolated(int[][] board,int rowNum, int colNum){
        for(int i = -1;i<2;i++){
            for(int j = -1;j<2;j++){
                try{
                    if(board[rowNum+i][colNum+j]<9){
                        return false;
                    }
                }
                catch (IndexOutOfBoundsException ignored){}
            }
        }
        return true;
    }

    private static boolean isValidBoard(int[][] board){
        for(int i = 0; i<board.length;i++){
            for(int j = 0; j<board[i].length;j++){
                if(0 < board[i][j] && board[i][j] < 9){
                    int[] bounds = upperAndLowerBounds(board,i,j);
                    if(board[i][j]<bounds[0] || board[i][j]>bounds[1]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isValidBoardWithFlags(int[][] board){
        for(int i = 0; i<board.length;i++){
            for(int j = 0; j<board[i].length;j++){
                if(0 < board[i][j] && board[i][j] < 9){
                    int[] bounds = upperAndLowerBoundsWithFlags(board,i,j);
                    if(board[i][j]<bounds[0] || board[i][j]>bounds[1]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static int[] upperAndLowerBoundsWithFlags(int[][] board, int colNum ,int rowNum){

        int lowerBound = 0;
        int upperBound = 0;
        for(int i = -1;i<2;i++){
            for(int j = -1;j<2;j++){
                try{
                    if(board[colNum+i][rowNum+j]==MINE || board[colNum+i][rowNum+j]==FLAG){
                        upperBound++;
                        lowerBound++;
                    }
                    if(board[colNum+i][rowNum+j]==SPACE){
                        upperBound++;
                    }
                }
                catch (IndexOutOfBoundsException ignored){}
            }
        }
        return new int[] {lowerBound,upperBound};
    }

    private static int[] upperAndLowerBounds(int[][] board, int colNum ,int rowNum){

        int lowerBound = 0;
        int upperBound = 0;
        for(int i = -1;i<2;i++){
            for(int j = -1;j<2;j++){
                try{
                    if(board[colNum+i][rowNum+j]==MINE){
                        upperBound++;
                        lowerBound++;
                    }
                    if(board[colNum+i][rowNum+j]==SPACE){
                        upperBound++;
                    }
                }
                catch (IndexOutOfBoundsException ignored){}
            }
        }
        return new int[] {lowerBound,upperBound};
    }


    /*
       findAllSolutions(board)
        emptySquare = findEmptySquare()

        if emptySquare == null:
            add solution to list

        // trying with a mine
        emptySquare = MINE
        if (isBoardValid())
            findAllSolutions(board)

        emptySquare = CHOSEN_SPACE

        if (isBoardValid())
            findAllSolutions(board)

        emptySquare = SPACE

        return
     */
}
