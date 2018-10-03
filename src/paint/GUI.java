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

    public int sceneX = 1000;
    public int sceneY = 500;
    public static boolean saved = true;
    public static boolean fileOpened = false;
    Canvas blankCanvas = new Canvas(sceneX, sceneY);
    VBox vbox = new VBox();                       //New vertical layout box
    public Stack canvasStack = new Stack();
    public Stack redoStack = new Stack();


    public void initialize(Stage mainStage) {
        
        Canvas tempCanvas = new Canvas(sceneX, sceneY);
        GraphicsContext tempgc = tempCanvas.getGraphicsContext2D();
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(vbox, sceneX, sceneY);//New scene
        Menus menus = new Menus();
        MainTools mainTools = new MainTools();
        DrawTools drawTools = new DrawTools();
        menus.MainMenu(scene, mainTools, stackPane, tempCanvas, tempgc);
        menus.DrawMenu(scene, drawTools, stackPane, tempCanvas, tempgc);

        mainStage.setTitle("Pain(t)");
        mainStage.setScene(scene);
        mainStage.setMaximized(TRUE);
        mainStage.show();
    }
}