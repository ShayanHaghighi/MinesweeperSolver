module com.example.minesweepersolvernew{
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.minesweepersolver to javafx.fxml;
    exports com.example.minesweepersolver;
}