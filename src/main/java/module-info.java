module dk.easv.tictactoefixed {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens dk.easv.tictactoefixed to javafx.fxml;
    exports dk.easv.tictactoefixed;
}