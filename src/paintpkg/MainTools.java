package paintpkg;

import javafx.geometry.Rectangle2D;
import java.awt.image.RenderedImage;
import javafx.scene.image.Image;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * This Class handles the main menu bar and features on it.
 */
public class MainTools extends Menus {

    String path = "";                                 //Creates a public blank string
    File fileToOpen = new File(path);                 //Creates a new file fileToOpen
    ImageView iv = new ImageView();                   //Creates an imageviewer object
    Image image = new Image(fileToOpen.toURI().toString());//Creates an image object
    FileChooser fileChooser = new FileChooser();  //Creates a new fileChooser object
    FileChooser.ExtensionFilter JPG = new FileChooser.ExtensionFilter("*.jpg", "*.jpg");//Sets the extension type to .jpg
    FileChooser.ExtensionFilter PNG = new FileChooser.ExtensionFilter("*.png", "*.png");//Sets the extension type to .png
    FileChooser.ExtensionFilter BMP = new FileChooser.ExtensionFilter("*.bmp", "*.bmp");//Sets the extension type to .bmp
    /**
     * Setups the image viewer and file extensions.
     */
    public void MainMenu() {
        iv.setFitHeight(sceneY);
        iv.setFitWidth(sceneX);
        stackPane.getChildren().addAll(iv);
    }
    
    /**
     * Method to handle opening a file.
     * @param scene 
     */
    public void open(Scene scene) {
        saved = false; //Used for smart save
        fileOpened = true;
        fileChooser.getExtensionFilters().addAll(JPG, PNG, BMP); //Sets the types for the filechooser
        fileToOpen = fileChooser.showOpenDialog(null);  //Prompts the user for a file
        path = fileToOpen.toURI().toString();           //Sets the public path to the file the user selected
        image = new Image(path);
        iv.setImage(image);                             //Passed the image to the imageviewe
        iv.setFitWidth(sceneX);
        iv.setFitHeight(sceneY);
        stackPane.getChildren().addAll(iv);
        scene.getHeight();
    }
    
    /**
     * Method to handle saving and image by saving it as a screen shot.
     * @param scene 
     */
    public void save(Scene scene) {                     
        if (path.equals("")) {//If no picture has been loaded
            System.out.println("No image loaded");
        } else {
            SnapshotParameters snapParams = new SnapshotParameters();
            int x = ((int) stackPane.getWidth() - sceneX) / 2;
            int y = ((int) stackPane.getHeight() - sceneY) / 2;
            snapParams.setViewport(new Rectangle2D(x, y + 75, sceneX, sceneY));
            WritableImage writableImage = new WritableImage(sceneX, sceneY);
            stackPane.snapshot(snapParams, writableImage);
            RenderedImage rImage = SwingFXUtils.fromFXImage(writableImage, null);//Using Swing method for image handling
            try {
                ImageIO.write(rImage, "png", fileToOpen);//Writing to file as a .png file
                saved = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    /**
     * Method to handle the saveAs function by setting the file path to the users
     * choice and then calling the save method.
     * @param scene 
     */
    public void saveAs(Scene scene) {
        fileChooser.getExtensionFilters().addAll(JPG, PNG, BMP); //Sets the types for the filechooser
        fileToOpen = fileChooser.showSaveDialog(null);  //Prompts the user for a file
        path = fileToOpen.toURI().toString();           //Sets the public path to the file the user selected
        save(scene);
    }
    
    /**
     * Method to properly exit the program. Checks first that the user has saved
     * before closing the application.
     * @param scene 
     */
    public void quit(Scene scene) {
        if (!saved) {//Smart save
            Stage dialog = new Stage();
            VBox vQuitBox = new VBox();
            HBox hQuitBox = new HBox();
            Text text = new Text("Would you like to save your changes?");
            Button saveButton = new Button("Save");
            Button quitButton = new Button("Quit");
            hQuitBox.getChildren().addAll(saveButton, quitButton);
            vQuitBox.getChildren().addAll(text, hQuitBox);
            saveButton.setOnAction((ActionEvent t) -> {       //Handles clicking the save as button
                if (path.equals("")) {
                    saveAs(scene);
                } else {
                    save(scene);
                }
            });
            quitButton.setOnAction((ActionEvent t) -> {       //Handles clicking the save as button
                System.exit(0);
            });
            Scene scene2 = new Scene(vQuitBox);
            dialog.setScene(scene2);
            dialog.show();
        } else {
            System.exit(0);
        }
    }
    
    /**
     * Undo method that removes the canvas from the stack and adds it to a
     * temporary stack for redoing.
     */
    public void undo() {
        try {
            Canvas undoCanvas = Canvas.class.cast(canvasStack.pop());
            redoStack.push(undoCanvas);
            undoCanvas.toBack();
            try {
                canvasStack.peek();
            } catch (Exception e) {
                if(fileOpened == false){
                    saved = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Empty Canvas Stack");
        }
    }
    
    /**
     * Redo method that removes the canvas from the redo stack and adds it
     * back to the main stack.
     * @param scene 
     */
    public void redo(Scene scene) {
        try {
            Canvas redoCanvas = Canvas.class.cast(redoStack.pop());
            canvasStack.push(redoCanvas);
            redoCanvas.toFront();
            saved = false; //Used for smart save
        } catch (Exception e) {
            System.out.println("Empty Redo Stack");
        }
    }
}
