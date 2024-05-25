package utils.Images;

import com.example.minesweepersolvernew.Main;
import javafx.scene.image.Image;

import java.util.Objects;

public class Icons {
    public static Image space_icon = new Image((Objects.requireNonNull(Main.class.getResourceAsStream("/media/Minesweeper_space.png"))));
    static Image space_pressed_icon = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/media/Minesweeper_space_indented.png")));
    static Image icon_0 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/media/Minesweeper_empty.png")));
    static Image icon_1 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/media/Minesweeper_1.png")));
    static Image icon_2 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/media/Minesweeper_2.png")));
    static Image icon_3 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/media/Minesweeper_3.png")));
    static Image icon_4 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/media/Minesweeper_4.png")));
    static Image icon_5 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/media/Minesweeper_5.png")));
    static Image icon_6 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/media/Minesweeper_6.png")));
    static Image icon_7 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/media/Minesweeper_7.png")));
    static Image icon_8 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/media/Minesweeper_8.png")));
    static Image mine = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/media/Minesweeper_mine.png")));
    static Image flag = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/media/Minesweeper_flag.png")));
    static Image[] imageArray = {icon_0,icon_1,icon_2,icon_3,icon_4,icon_5,icon_6,icon_7,icon_8,mine,space_icon,flag};
    public static Image integerToImage(int number){
        return imageArray[number];
    }
}

