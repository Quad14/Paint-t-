package paintpkg;

import javafx.application.Application;
import javafx.stage.Stage;

public class Paint extends Application{

    public static void main(String[] args) {
        launch(args); 
    }

    @Override
    public void start(Stage primaryStage) {
         GUI gui = new GUI();
         gui.initialize(primaryStage);
    }
}