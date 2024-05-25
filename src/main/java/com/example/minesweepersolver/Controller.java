package com.example.minesweepersolver;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import utils.Images.Color;

public class Controller {
    public static void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.SHIFT) {
            Main.shiftPressed = true;
            Main.fadeInText();
        }
    }

    public static void handleKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.SHIFT) {
            Main.shiftPressed = false;
            Main.fadeOutText();
        }
    }

    public static void handleClick(boolean isLeftClick,int column, int row){
        if(!Main.gameOver) {
            Color.updateColor(Main.grid.getChildren().get(row*Main.numRows+column),0,0,0);
            if (isLeftClick) {
                Board.handleClick(row, column);
                Main.updateBoard();
                if(Board.hasWon()){
                    System.out.println("well done");
                    Main.gameOver = true;
                }
            } else {
                Board.flag(row, column);
                Main.updateBoard();
            }
        }
    }

    public static void handleClick(MouseEvent mouseEvent, int column, int row){
        if(!Main.gameOver) {
            Color.updateColor(Main.grid.getChildren().get(row*Main.numCols+column),0,0,0);
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                Board.handleClick(column, row);
                Main.updateBoard();
                if(Board.hasWon()){
                    System.out.println("well done");
                    Main.gameOver = true;
                }
                if (Board.hasLost()){
                    Main.gameOverSequence();
                }

            } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                Board.flag(column, row);
                Main.updateBoard();
            }
        }
    }

    public static void handleMouseClick(MouseEvent mouseEvent, int column, int row, ColorAdjust colorAdjust) {
        Controller.handleClick(mouseEvent, column, row);
        if (Main.gameOver) {
            Main.gameOverSequence();
        }
    }

    public static void handleMouseMoved(MouseEvent event){
        if (Main.shiftPressed) {
            double mouseX = event.getX();
            double mouseY = event.getY();
            Main.probPanel.relocate(mouseX + 20, mouseY - 60);
        }
    }

    public static void handleMouseEnter(MouseEvent event, int column, int row, ColorAdjust colorAdjust) {
        if (event.isShiftDown()) {
            colorAdjust.setBrightness(-0.5);
            if (Main.haveCalculated && Main.currentProbArray[column][row] >= 0) {
                Main.fadeInText();
                Main.text.setText(String.format("%.2f", Main.currentProbArray[column][row]));
            } else {
                Main.fadeOutText();
            }
        } else {
            Main.fadeOutText();
        }
    }

}