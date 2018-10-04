package paint;

import static java.lang.Boolean.TRUE;
import java.util.Stack;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUI {

    public static int sceneX = 1000;
    public static int sceneY = 500;
    public static boolean saved = true;
    public static boolean fileOpened = false;
    public static Canvas blankCanvas = new Canvas(sceneX, sceneY);
    VBox vbox = new VBox();                       //New vertical layout box
    public static Stack canvasStack = new Stack();
    public static Stack redoStack = new Stack();
    public static StackPane stackPane = new StackPane();
    public static Canvas tempCanvas = new Canvas(sceneX, sceneY);
    public static GraphicsContext tempgc = tempCanvas.getGraphicsContext2D();
    public void initialize(Stage mainStage) {

        //Canvas tempCanvas = new Canvas(sceneX, sceneY);
        
        //StackPane stackPane = new StackPane();
        Scene scene = new Scene(vbox, sceneX, sceneY);//New scene
        Menus menus = new Menus();
        MainTools mainTools = new MainTools();
        DrawTools drawTools = new DrawTools();
        menus.MainMenu(scene, mainTools);
        menus.DrawMenu(scene, drawTools);

        mainStage.setTitle("Pain(t)");
        mainStage.setScene(scene);
        mainStage.setMaximized(TRUE);
        mainStage.show();
    }
}
