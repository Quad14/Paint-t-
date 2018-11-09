package paintpkg;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is used for starting the application.
 */
public class Paint extends Application{
    
    /**
     * Launch the application
     * @param args 
     */
    public static void main(String[] args) {
        launch(args); 
    }

    /**
     * Start the GUI using the primary stage.
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) {
        
         GUI gui = new GUI();
         gui.initialize(primaryStage);
    }
}