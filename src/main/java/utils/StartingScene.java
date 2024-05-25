package utils;

import com.example.minesweepersolvernew.Board;
import com.example.minesweepersolvernew.Main;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

//import static com.example.minesweepersolvernew.Main.*;

public class StartingScene {


    public static Scene initStartScene() {

        VBox startBox = new VBox(10);
        startBox.setMaxSize(700, 700);
        startBox.setAlignment(Pos.CENTER);
        Scene startScene = new Scene(startBox, 700, 700);

        Slider rowsSlider = initSlider(80, 16, 10, 10, 4);
        Slider colsSlider = initSlider(80, 16, 10, 10, 4);
        Slider mineSlider = initSlider(800, 14, 40, 100, 10);

        HBox rowsBox = initSliderWithTextField(80, 100, rowsSlider);
        HBox colsBox = initSliderWithTextField(80, 100, colsSlider);
        HBox mineBox = initSliderWithTextField(800, 500, mineSlider);

        ChangeListener<Number> mineUpdateListener = (observable, oldValue, newValue) ->{
            mineSlider.setValue((int) ((rowsSlider.getValue() * colsSlider.getValue()) / 6));
        };

        rowsSlider.valueProperty().addListener(mineUpdateListener);
        colsSlider.valueProperty().addListener(mineUpdateListener);

        Button startButton = new Button("ok");
        startButton.setOnAction(event -> startGame(rowsSlider, colsSlider, mineSlider));

        startBox.getChildren().addAll(rowsBox, colsBox, mineBox, startButton);

        return startScene;
    }


    private static void openPopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Popup Window");

        StackPane popupLayout = new StackPane();

        Button exitButton = new Button("Too many mines for board dimension");
        exitButton.setTextAlignment(TextAlignment.CENTER);
        exitButton.setOnAction(event -> popupStage.close());
        exitButton.setMaxWidth(200);
        exitButton.setWrapText(true);
        popupLayout.getChildren().add(exitButton);

        Scene popupScene = new Scene(popupLayout, 250, 150);
        popupStage.setScene(popupScene);

        popupStage.showAndWait();
    }

    public static Slider initSlider(int max, int val, int blockIncrement, int MajTickUnit, int MinTickUnit){
        Slider slider = new Slider(0,max,val);
        slider.setMaxWidth(500);
        slider.setPrefWidth(500);
        slider.setBlockIncrement(blockIncrement);
        slider.setMajorTickUnit(MajTickUnit);
        slider.setMinorTickCount(MinTickUnit);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        return slider;
    }

    private static HBox initSliderWithTextField(double max, double value, Slider slider) {
        TextField textField = new TextField(Integer.toString((int)value));
        textField.setPrefWidth(50);

        textField.setOnAction(e -> {
            try {
                int newValue = Integer.parseInt(textField.getText());
                if (newValue >= (double) 0 && newValue <= max) {
                    slider.setValue(newValue);
                } else {
                    textField.setText(Integer.toString((int)slider.getValue()));
                }
            } catch (NumberFormatException ex) {
                textField.setText(Integer.toString((int)slider.getValue()));
            }
        });

        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            textField.setText(String.format("%d", newVal.intValue()));
        });

        HBox hBox = new HBox(10, slider, textField);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private static void startGame(Slider rowsSlider, Slider colsSlider, Slider mineSlider) {
        Main.numCols = (int) colsSlider.getValue();
        Main.numRows = (int) rowsSlider.getValue();
        Main.numMines = (int) mineSlider.getValue();
        if (Main.numMines > 800) Main.numMines = 800;
        if (Main.numMines > Main.numRows * Main.numCols) {
            openPopup();
        } else {
            setupBoardAndCenterStage();
        }
    }

    private static void setupBoardAndCenterStage() {
        Main.imageDimension = Math.min((Main.stageHeight - 200) / Main.numRows, Main.stageWidth / Main.numCols);
        Main.setupBoard();
        Board.reset();
        Main.myStage.setScene(Main.playScene);
        Main.myStage.sizeToScene();
        centerStage();
    }

    private static void centerStage() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double centerX = bounds.getMinX() + (bounds.getWidth() - Main.myStage.getWidth()) / 2.0;
        double centerY = bounds.getMinY() + (bounds.getHeight() - Main.myStage.getHeight()) / 2.0;
        Main.myStage.setX(centerX);
        Main.myStage.setY(centerY);
    }

}
