package paintpkg;

import static java.lang.Boolean.TRUE;
import java.util.Stack;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Class to setup the user interface for the program
 * @author Jacob
 */
public class GUI {
    //Size of the image editing area
    public static int sceneX = 1000;
    public static int sceneY = 500;
    public static boolean saved = true;//Used for checking if the file has been saved
    public static boolean fileOpened = false;
    public static Canvas blankCanvas = new Canvas(sceneX, sceneY);
    VBox vbox = new VBox();                       //New vertical layout box
    public static Stack canvasStack = new Stack();//Used for the undo feature
    public static Stack redoStack = new Stack();  //Used for the redo feature
    public static StackPane stackPane = new StackPane();
    public static Canvas tempCanvas = new Canvas(sceneX, sceneY);//Used for the preview of the drawing
    public static GraphicsContext tempgc = tempCanvas.getGraphicsContext2D();
    
    /**
     * Setups the scene along with all the menus
     * @param mainStage 
     */
    public void initialize(Stage mainStage) {
        Scene scene = new Scene(vbox, sceneX, sceneY);//New scene
        Menus menus = new Menus();
        MainTools mainTools = new MainTools();
        DrawTools drawTools = new DrawTools();
        menus.MainMenu(scene, mainTools, mainStage);
        menus.DrawMenu(scene, drawTools);

        mainStage.setTitle("Pain(t)");
        mainStage.setScene(scene);
        mainStage.setMaximized(TRUE);
        mainStage.show();
    }
}
