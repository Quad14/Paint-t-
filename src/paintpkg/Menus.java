package paintpkg;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Menus extends GUI {
    public static ColorPicker colorChooser = new ColorPicker(Color.BLACK);
    public void MainMenu(Scene scene, MainTools mainTools) {
        
        MenuBar menu = new MenuBar();                 //Creates the menu bar
        Menu file = new Menu("File");                 //Creates the file label
        Menu resizeMenu = new Menu("Resize Image");                 //Creates the edit label
        Menu edit = new Menu("Edit");
        MenuItem open = new MenuItem("Open File", //Creates the open,save, save as and exit labels and adds the icons
                new ImageView(new Image("resources/Open_Folder.png", 25.0, 25.0, true, true, true)));
        MenuItem save = new MenuItem("Save",
                new ImageView(new Image("resources/Save.png", 25.0, 25.0, true, true, true)));
        MenuItem saveAs = new MenuItem("Save as",
                new ImageView(new Image("resources/save_as.png", 25.0, 25.0, true, true, true)));
        MenuItem exit = new MenuItem("Exit",
                new ImageView(new Image("resources/Exit.png", 25.0, 25.0, true, true, true)));
        //MenuItem resize = new MenuItem("Resize", //Creates the open,save, save as and exit labels and adds the icons
        //        new ImageView(new Image("resources/resize.png", 25.0, 25.0, true, true, true)));
        MenuItem undo = new MenuItem("Undo",
                new ImageView(new Image("resources/undo.png", 25.0, 25.0, true, true, true)));
        MenuItem redo = new MenuItem("Redo",
                new ImageView(new Image("resources/redo.png", 25.0, 25.0, true, true, true)));
        /*
        CustomMenuItem resizer = new CustomMenuItem();
        CustomMenuItem resizer2 = new CustomMenuItem();
        TextField xField = new TextField();
        xField.setPromptText("X value");
        TextField yField = new TextField();
        yField.setPromptText("Y value");
        resizer.setHideOnClick(false);
        resizer.setContent(xField);
        resizer2.setContent(yField);
        resizeMenu.getItems().add(resizer);
        resizeMenu.getItems().add(resizer2);
         */
        menu.getMenus().addAll(file, edit);
        file.getItems().addAll(open, save, saveAs, exit);     //Puts open,save and exit under file
        edit.getItems().addAll(undo, redo);
        //resizeMenu.getItems().addAll(resize);

        ((VBox) scene.getRoot()).getChildren().addAll(menu);
        open.setOnAction((ActionEvent t) -> {         //Handles clicking the open button
            mainTools.open(scene);
        });
        save.setOnAction((ActionEvent t) -> {         //Handles clicking the save button
            mainTools.save(scene);
        });
        saveAs.setOnAction((ActionEvent t) -> {       //Handles clicking the save as button
            mainTools.saveAs(scene);
        });
        exit.setOnAction((ActionEvent t) -> {         //Handles clicking the exit button
            mainTools.quit(scene);
        });
        undo.setOnAction((ActionEvent t) -> {         //Handles clicking the undo button
            mainTools.undo();
        });
        redo.setOnAction((ActionEvent t) -> {         //Handles clicking the undo button
            mainTools.redo(scene);
        });
        
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
    }

    public void DrawMenu(Scene scene, DrawTools drawTools) {

        MenuBar drawBar = new MenuBar();
        Menu type = new Menu("Type");
        Menu options = new Menu("Options");

        Menu width = new Menu("Width", new ImageView(new Image("resources/Width.png", 25.0, 25.0, true, true, true)));
        MenuItem line = new MenuItem("Line", new ImageView(new Image("resources/Line.png", 25.0, 25.0, true, true, true)));
        MenuItem freeHand = new MenuItem("Line", new ImageView(new Image("resources/freehand.png", 25.0, 25.0, true, true, true)));
        MenuItem fill = new MenuItem("Fill", new ImageView(new Image("resources/fill.png", 25.0, 25.0, true, true, true)));
        MenuItem wireSquare = new MenuItem("Wire Square", new ImageView(new Image("resources/wiresquare.png", 25.0, 25.0, true, true, true)));
        MenuItem wireCircle = new MenuItem("Wire Circle", new ImageView(new Image("resources/wirecircle.png", 25.0, 25.0, true, true, true)));
        MenuItem wireRoundSquare = new MenuItem("Wire Round Square", new ImageView(new Image("resources/wireroundsquare.png", 25.0, 25.0, true, true, true)));
        MenuItem solidSquare = new MenuItem("Solid Square", new ImageView(new Image("resources/solidsquare.png", 25.0, 25.0, true, true, true)));
        MenuItem solidCircle = new MenuItem("Solid Circle", new ImageView(new Image("resources/solidcircle.png", 25.0, 25.0, true, true, true)));
        MenuItem cutandMove = new MenuItem("Cut and Move", new ImageView(new Image("resources/cutandmove.png", 25.0, 25.0, true, true, true)));
        MenuItem eraser = new MenuItem("Eraser", new ImageView(new Image("resources/eraser.png", 25.0, 25.0, true, true, true)));
        MenuItem solidRoundSquare = new MenuItem("Solid Round Square", new ImageView(new Image("resources/solidroundsquare.png", 25.0, 25.0, true, true, true)));
        MenuItem dropper = new MenuItem("Color Dropper", new ImageView(new Image("resources/dropper.png", 25.0, 25.0, true, true, true)));
        Menu text = new Menu("Text", new ImageView(new Image("resources/text.png", 25.0, 25.0, true, true, true)));
        
        CustomMenuItem widthController = new CustomMenuItem();
        TextField inputWidth = new TextField();
        widthController.setHideOnClick(false);
        widthController.setContent(inputWidth);
        width.getItems().add(widthController);
        
        CustomMenuItem textController = new CustomMenuItem();
        TextField inputText = new TextField();
        textController.setHideOnClick(false);
        textController.setContent(inputText);
        text.getItems().add(textController);

        options.getItems().addAll(fill, width, cutandMove);
        drawBar.getMenus().addAll(type, options);
        type.getItems().addAll(eraser, line, freeHand, wireSquare, wireCircle, wireRoundSquare, solidSquare, solidCircle, solidRoundSquare, text, dropper);//Puts line and free hand under type
        GraphicsContext blankCanvasGraphicsContext = blankCanvas.getGraphicsContext2D();
        blankCanvasGraphicsContext.setFill(Color.WHITE);
        blankCanvasGraphicsContext.fillRect(0, 0, sceneX, sceneY);
        stackPane.getChildren().addAll(blankCanvas);
        stackPane.getChildren().addAll(canvasStack);
        stackPane.getChildren().addAll(tempCanvas);
        
        ((VBox) scene.getRoot()).getChildren().addAll(drawBar);
        ((VBox) scene.getRoot()).getChildren().addAll(colorChooser);
        ((VBox) scene.getRoot()).getChildren().addAll(stackPane);
        
        //Handles clicking the line button
        line.setOnAction((ActionEvent t) -> {
            drawTools.line(scene);
        });
        //Handles clicking the free hand button
        freeHand.setOnAction((ActionEvent t) -> {
            drawTools.freeHand(scene);
        });
        //Handles clicking the fill button
        fill.setOnAction((event) -> {
            Canvas fillCanvas = Canvas.class.cast(canvasStack.peek());
            fillCanvas.getGraphicsContext2D().setFill(colorChooser.getValue());
            fillCanvas.getGraphicsContext2D().setStroke(colorChooser.getValue());
            fillCanvas.getGraphicsContext2D().fill();
        });
        text.setOnAction((event) -> {
            drawTools.text(scene, inputText);
        });
        //Handles clicking the width button
        width.setOnAction((event) -> {
            drawTools.widthControl(inputWidth);
        });
        //Handles clicking the wire square button
        wireSquare.setOnAction((event) -> {
            drawTools.wireSquare(scene);
        });
        //Handles clicking the wire circle button
        wireCircle.setOnAction((event) -> {
            drawTools.wireCircle(scene);
        });
        //Handles clicking the solid square button
        solidSquare.setOnAction((event) -> {
            drawTools.solidSquare(scene);
        });
        //Handles clicking the solid circle button
        solidCircle.setOnAction((event) -> {
            drawTools.solidCircle(scene);
        });
        eraser.setOnAction((event) -> {
            drawTools.eraser(scene);
        });
        solidRoundSquare.setOnAction((event) -> {
            drawTools.solidRoundSquare(scene);
        });
        wireRoundSquare.setOnAction((event) -> {
            drawTools.wireRoundSquare(scene);
        });
        cutandMove.setOnAction((event) -> {
            drawTools.cut(scene);
        });
        dropper.setOnAction((event) -> {
            drawTools.dropper();
        });
        
    }
}