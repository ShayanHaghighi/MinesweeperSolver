package utils.Images;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class ImageEffects {
    public static void setImg(Node node, int picture){
        ((ImageView)node).setImage(Icons.integerToImage(picture));
        if(picture<9){
            Color.updateColor(node,0,0,0);
        }
    }
}
