package utils.Images;

import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.GridPane;

public class Color {

    public static void updateColor(Node node, double hue, double saturation, double brightness){
        ColorAdjust color = ((ColorAdjust)node.getEffect());
        color.setHue(hue);
        color.setSaturation(saturation);
        color.setBrightness(brightness);
        node.setEffect(color);
    }

    public static void colorImages(double[][] probabilities, GridPane grid, int numRows){
        for(int i = 0; i< probabilities.length;i++) {
            for(int j = 0; j< probabilities[0].length;j++) {
                if (probabilities[i][j] == 0) {
                    updateColor(grid.getChildren().get(j+i*numRows),0.6,1,0);
                } else if (probabilities[i][j] == 1) {
                    updateColor(grid.getChildren().get(j+i*numRows),0,1,0);
                }
                else{
                    updateColor(grid.getChildren().get(j+i*numRows),0,0,0);
                }
            }
        }
    }
}
