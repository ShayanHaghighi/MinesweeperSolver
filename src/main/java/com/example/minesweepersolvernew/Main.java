package com.example.minesweepersolvernew;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

import utils.Images.Icons;
import utils.Images.Icons.*;

public class Main extends Application {

    // solver variables
    public static int numCols, numRows;
    public static int stageHeight = 700, stageWidth = 700, numMines = 10;
    public static int imageDimension;
    // GUI variables
    public static Stage myStage;
    static boolean gameOver = false;
    // solver variables
    static boolean shiftPressed = false, haveCalculated = false;
    static double[][] currentProbArray = new double[numCols][numRows];
    static VBox root = new VBox();
    public static Scene playScene = new Scene(root), startScene;
    static GridPane grid = new GridPane();
    static HBox taskBar;
    static AnchorPane probPanel;
    static AnchorPane gamePanel;
    static Text text = new Text("1.0");


    public static void gameOverSequence() {
        Board.revealAll();
        updateBoard();
        gameOver = true;
    }

    public static void updateBoard() {
        haveCalculated = false;
        List<javafx.scene.Node> nodes = grid.getChildren();
        for (int i = 0; i < nodes.size(); i++) {
            int square = Board.displayBoard[Math.floorDiv(i, numRows)][i % numRows];
            utils.Images.ImageEffects.setImg(nodes.get(i), square);
        }
    }

    public static void setupBoard() {
        grid.getChildren().clear();

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                ImageView image = createImageView(i, j);
                grid.add(image, i, j);
            }
        }
    }

    public static ImageView createImageView(int column, int row) {
        Image test = Icons.space_icon;
        ImageView image = new ImageView(test);
        image.setFitHeight(imageDimension);
        image.setFitWidth(imageDimension);

        ColorAdjust colorAdjust = new ColorAdjust();
        image.setEffect(colorAdjust);

        image.setOnMouseClicked(mouseEvent -> Controller.handleMouseClick(mouseEvent, column, row, colorAdjust));
        image.setOnMouseEntered(event -> Controller.handleMouseEnter(event, column, row, colorAdjust));
        image.setOnMouseExited(event -> colorAdjust.setBrightness(0));

        return image;
    }

    public static void fadeInText() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), probPanel);
        fadeTransition.setFromValue(probPanel.getOpacity()); // Start from fully transparent
        fadeTransition.setToValue(1); // End at fully opaque
        fadeTransition.play();
    }

    public static void fadeOutText() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), probPanel);
        fadeTransition.setFromValue(probPanel.getOpacity()); // Start from fully transparent
        fadeTransition.setToValue(0); // End at fully opaque
        fadeTransition.play();
    }

    public static void rotateAndPrintArr(double[][] array) {
        for (int i = 0; i < numRows; i++) {
            System.out.print("[");
            for (int j = 0; j < numCols; j++) {
                if (array[j][i] == -1) {
                    System.out.print("---, ");
                } else {
                    System.out.print(array[j][i] + ", ");
                }
            }
            System.out.print("]\n");
        }
    }

    public static void doNextStep() {
        double[][] res = Solver.solveAll(Board.displayBoard);
        Timeline timeline = createTimeline(res);
        timeline.play();
    }

    private static Timeline createTimeline(double[][] res) {
        Timeline timeline = new Timeline();
        int count = 0;

        for (int colNum = 0; colNum < res.length; colNum++) {
            for (int rowNum = 0; rowNum < res[0].length; rowNum++) {
                if (res[colNum][rowNum] == 0) {
                    int delay = 10 * count++;
                    addKeyFrame(timeline, delay, colNum, rowNum, true);
                }
           /*     else if (Board.displayBoard[colNum][rowNum] != Board.FLAG && res[colNum][rowNum]==1) {
                    System.out.println("clear at:" + rowNum + "," + colNum);
                    final int col = colNum;
                    final int row = rowNum;
                    KeyFrame keyFrame = new KeyFrame(
                            Duration.millis(10 * (count++)),
                            event -> {
                                handleClick(false,row,col);
                            }
                    );
                    timeline.getKeyFrames().add(keyFrame);
                }*/
            }
        }

        return timeline;
    }

    private static void addKeyFrame(Timeline timeline, int delay, int col, int row, boolean isLeftClick) {
        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(delay),
                event -> Controller.handleClick(isLeftClick, row, col)
        );
        timeline.getKeyFrames().add(keyFrame);
    }

    public static void main(String[] args) {
        launch();
    }

    public void reset(ActionEvent actionEvent) {
        for (Node node : grid.getChildren()) {
            ColorAdjust color = ((ColorAdjust) node.getEffect());
            color.setSaturation(0);
            color.setBrightness(0);
            color.setHue(0);
            node.setEffect(color);
        }

        Board.reset();
        updateBoard();
        gameOver = false;
        haveCalculated = false;
    }

    public void toStartScreen(ActionEvent actionEvent) {
        myStage.setScene(startScene);
        myStage.sizeToScene();
        Board.reset();

        // make sure the start screen isn't offset
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double centerX = bounds.getMinX() + (bounds.getWidth() - 500) / 2.0;
        double centerY = bounds.getMinY() + (bounds.getHeight() - 500) / 2.0;
        myStage.setX(centerX);
        myStage.setY(centerY);
    }

    public void solve(ActionEvent actionEvent) {
        long startTime = System.currentTimeMillis();
        double[][] res = Solver.returnProbs(Board.displayBoard);
        rotateAndPrintArr(res);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        utils.Images.Color.colorImages(res, grid, numRows);
        updateBoard();
        currentProbArray = res;
        haveCalculated = true;
    }

    public void finishGame(ActionEvent actionEvent) {
        clearFlags();
        startTimeline();
    }

    private void clearFlags() {
        for (int i = 0; i < Board.displayBoard.length; i++) {
            for (int j = 0; j < Board.displayBoard[i].length; j++) {
                if (Board.displayBoard[i][j] == Board.FLAG) {
                    Board.displayBoard[i][j] = Board.SPACE;
                }
            }
        }
    }

    private void startTimeline() {
        Timeline timeline = new Timeline();

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5), event -> {
                    doNextStep();
                    if (gameOver) {
                        timeline.stop();
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void initPlayScene() {
        setupStage();
        setupText();
        setupProbPanel();
        setupTaskBar();


        grid.setPadding(new Insets(0, 150, 50, 150));

        gamePanel = new AnchorPane(grid, probPanel);
        setupRootEventHandlers(gamePanel);

        root.getChildren().addAll(taskBar, gamePanel);
    }

    private void setupStage() {
        myStage.setWidth(stageWidth);
        myStage.setHeight(stageHeight);
        root.setAlignment(Pos.CENTER);
        grid.setAlignment(Pos.CENTER);
    }

    private void setupText() {
        text.setStyle("-fx-font-size: 15; -fx-fill: black;");
        text.setLayoutX(10);
        text.setLayoutY(28);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setText("1.0");
    }

    private void setupProbPanel() {
        Rectangle background = new Rectangle(50, 40);
        background.setArcWidth(10);
        background.setArcHeight(10);
        background.setFill(Color.WHITE);
        probPanel = new AnchorPane(background, text);
        probPanel.setOpacity(0);
    }

    private void setupTaskBar() {
        taskBar = new HBox(10);
        taskBar.setLayoutY(100);
        taskBar.setAlignment(Pos.TOP_CENTER);
        taskBar.setPadding(new Insets(50));

        Button start = new Button("to start screen");
        Button reset = new Button("reset");
        Button solve = new Button("solve");
        Button step = new Button("step");

        start.setOnAction(this::toStartScreen);
        reset.setOnAction(this::reset);
        solve.setOnAction(this::solve);
        step.setOnAction(this::finishGame);

        taskBar.getChildren().addAll(start, reset, solve, step);
    }

    private void setupRootEventHandlers(AnchorPane anchorPane) {
        anchorPane.setOnMouseMoved(Controller::handleMouseMoved);
        root.setOnKeyPressed(Controller::handleKeyPress);
        root.setOnKeyReleased(Controller::handleKeyRelease);
    }

    @Override
    public void start(Stage stage) throws IOException {
        myStage = stage;
        startScene = utils.StartingScene.initStartScene();
        initPlayScene();

        stage.setScene(startScene);
        stage.show();
    }
}

