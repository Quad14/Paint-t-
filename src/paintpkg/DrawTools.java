package paintpkg;

import static java.lang.Math.abs;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import static paintpkg.GUI.tempgc;

/**
 * This class creates all the drawing tools and there operations.
 * @author Jacob
 */
public class DrawTools extends Menus {

    int widthInt = 1;
    String textString = "";

    /**
     * Draws a line based on two points given by the mouse location. Constantly 
     * blanks and draws on a canvas to show the item as you draw it.
     * @param scene 
     */
    public void line(Scene scene) {
        //Start the process by creating a new canvas and adding it to the canvas
        //stack
        Canvas newCanvas = new Canvas(sceneX, sceneY);

        GraphicsContext gc = newCanvas.getGraphicsContext2D();
        //Create a new canvas and add it to the stack
        canvasStack.push(newCanvas);
        ((VBox) scene.getRoot()).getChildren().removeAll(stackPane);
        stackPane.getChildren().removeAll(canvasStack);
        stackPane.getChildren().addAll(canvasStack);
        ((VBox) scene.getRoot()).getChildren().addAll(stackPane);

        gc.setStroke(colorChooser.getValue());
        gc.setFill(colorChooser.getValue());
        gc.setLineWidth(widthInt);
        //Set the stroke size and color
        tempgc.setStroke(colorChooser.getValue());
        tempgc.setFill(colorChooser.getValue());
        tempgc.setLineWidth(widthInt);

        tempCanvas.toFront();
        //Get the mouse presses for drawing the line
        tempCanvas.setOnMousePressed((MouseEvent event) -> {
            tempCanvas.setOnMouseDragged((MouseEvent event3) -> {
                tempgc.clearRect(0, 0, sceneX, sceneY);
                tempgc.strokeLine(event.getX(), event.getY(), event3.getX(), event3.getY());
                //Nulls the events on mouse realse and clears the temp canvas
                tempCanvas.setOnMouseReleased((MouseEvent event2) -> {
                    gc.strokeLine(event.getX(), event.getY(), event2.getX(), event2.getY());
                    tempgc.clearRect(0, 0, sceneX, sceneY);
                    tempCanvas.setOnMouseDragged(null);
                    tempCanvas.setOnMouseReleased(null);
                });
            });
            tempCanvas.setOnMousePressed(null);
        });
        saved = false; //Used for smart save//Used for smart save
    }
    
    /**
     * Freehand drawing while the mouse is held down.
     * @param scene 
     */
    public void freeHand(Scene scene) {
        //Start the process by creating a new canvas and adding it to the canvas
        //stack
        Canvas newCanvas = new Canvas(sceneX, sceneY);

        GraphicsContext gc = newCanvas.getGraphicsContext2D();
        canvasStack.push(newCanvas);

        ((VBox) scene.getRoot()).getChildren().removeAll(stackPane);
        stackPane.getChildren().removeAll(canvasStack);
        stackPane.getChildren().addAll(canvasStack);
        ((VBox) scene.getRoot()).getChildren().addAll(stackPane);
        //Set stroke anc color
        gc.setStroke(colorChooser.getValue());
        gc.setFill(colorChooser.getValue());
        gc.setLineWidth(widthInt);

        newCanvas.setOnMouseDragged((MouseEvent event2) -> {
            gc.lineTo(event2.getX(), event2.getY());
            gc.stroke();
        });
        //Null events when mouse is realeased
        newCanvas.setOnMouseReleased((MouseEvent event3) -> {
            newCanvas.setOnMouseDragged(null);
            newCanvas.setOnMouseReleased(null);
        });
        saved = false; //Used for smart save
    }
    
    /**
     * Save as freehand but it draws in white.
     * @param scene 
     */
    public void eraser(Scene scene) {
        //Start the process by creating a new canvas and adding it to the canvas
        //stack
        Canvas newCanvas = new Canvas(sceneX, sceneY);

        GraphicsContext gc = newCanvas.getGraphicsContext2D();
        canvasStack.push(newCanvas);

        ((VBox) scene.getRoot()).getChildren().removeAll(stackPane);
        stackPane.getChildren().removeAll(canvasStack);
        stackPane.getChildren().addAll(canvasStack);
        ((VBox) scene.getRoot()).getChildren().addAll(stackPane);
        //Force the color to white
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(widthInt);

        newCanvas.setOnMouseDragged((MouseEvent event2) -> {
            gc.lineTo(event2.getX(), event2.getY());
            gc.stroke();
        });
        //Set mouse events to null
        newCanvas.setOnMouseReleased((MouseEvent event3) -> {
            newCanvas.setOnMouseDragged(null);
            newCanvas.setOnMouseReleased(null);
        });
        saved = false; //Used for smart save
    }

    /**
     * Draws the wire square. The if statements make it so one can draw from any
     * direction.
     * @param scene 
     */
    public void wireSquare(Scene scene) {
        //Start the process by creating and new canvas and adding it to the
        //canvas stack
        Canvas newCanvas = new Canvas(sceneX, sceneY);

        GraphicsContext gc = newCanvas.getGraphicsContext2D();
        canvasStack.push(newCanvas);

        ((VBox) scene.getRoot()).getChildren().removeAll(stackPane);
        stackPane.getChildren().removeAll(canvasStack);
        stackPane.getChildren().addAll(canvasStack);
        ((VBox) scene.getRoot()).getChildren().addAll(stackPane);
        //Set stroke and color
        gc.setStroke(colorChooser.getValue());
        gc.setFill(colorChooser.getValue());
        gc.setLineWidth(widthInt);

        tempgc.setStroke(colorChooser.getValue());
        tempgc.setFill(colorChooser.getValue());
        tempgc.setLineWidth(widthInt);
        tempCanvas.toFront();

        tempCanvas.setOnMousePressed((MouseEvent event) -> {
            //Preview the wire square
            tempCanvas.setOnMouseDragged((MouseEvent event3) -> {
                tempgc.clearRect(0, 0, sceneX, sceneY);
                if ((event3.getX() > event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.strokeRect(event.getX(), event3.getY(), event3.getX() - event.getX(), event.getY() - event3.getY());
                } else if ((event3.getX() < event.getX()) && (event3.getY() > event.getY())) {
                    tempgc.strokeRect(event3.getX(), event.getY(), event.getX() - event3.getX(), event3.getY() - event.getY());
                } else if ((event3.getX() < event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.strokeRect(event3.getX(), event3.getY(), event.getX() - event3.getX(), event.getY() - event3.getY());
                } else {
                    tempgc.strokeRect(event.getX(), event.getY(), event3.getX() - event.getX(), event3.getY() - event.getY());
                }
                //Place the wire square
                tempCanvas.setOnMouseReleased((MouseEvent event2) -> {
                    if ((event2.getX() > event.getX()) && (event2.getY() < event.getY())) {
                        gc.strokeRect(event.getX(), event2.getY(), event2.getX() - event.getX(), event.getY() - event2.getY());
                    } else if ((event2.getX() < event.getX()) && (event2.getY() > event.getY())) {
                        gc.strokeRect(event2.getX(), event.getY(), event.getX() - event2.getX(), event2.getY() - event.getY());
                    } else if ((event2.getX() < event.getX()) && (event2.getY() < event.getY())) {
                        gc.strokeRect(event2.getX(), event2.getY(), event.getX() - event2.getX(), event.getY() - event2.getY());
                    } else {
                        gc.strokeRect(event.getX(), event.getY(), event2.getX() - event.getX(), event2.getY() - event.getY());
                    }
                    tempgc.clearRect(0, 0, sceneX, sceneY);
                    tempCanvas.setOnMouseDragged(null);
                    tempCanvas.setOnMouseReleased(null);
                });
                tempCanvas.setOnMousePressed(null);
            });
        });
        saved = false; //Used for smart save
    }

    /**
     * Draws the wire round square. The if statements make it so one can draw from any
     * direction.
     * @param scene 
     */
    public void wireRoundSquare(Scene scene) {
        //Start the process by creating a new canvas and placing it in the
        //canvas stack
        Canvas newCanvas = new Canvas(sceneX, sceneY);

        GraphicsContext gc = newCanvas.getGraphicsContext2D();
        canvasStack.push(newCanvas);

        ((VBox) scene.getRoot()).getChildren().removeAll(stackPane);
        stackPane.getChildren().removeAll(canvasStack);
        stackPane.getChildren().addAll(canvasStack);
        ((VBox) scene.getRoot()).getChildren().addAll(stackPane);
        //Set line width and color
        gc.setStroke(colorChooser.getValue());
        gc.setFill(colorChooser.getValue());
        gc.setLineWidth(widthInt);

        tempgc.setStroke(colorChooser.getValue());
        tempgc.setFill(colorChooser.getValue());
        tempgc.setLineWidth(widthInt);
        tempCanvas.toFront();

        tempCanvas.setOnMousePressed((MouseEvent event) -> {
            //Preview the wire round square
            tempCanvas.setOnMouseDragged((MouseEvent event3) -> {
                tempgc.clearRect(0, 0, sceneX, sceneY);
                if ((event3.getX() > event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.strokeRoundRect(event.getX(), event3.getY(), event3.getX() - event.getX(), event.getY() - event3.getY(), 25.0, 25.0);
                } else if ((event3.getX() < event.getX()) && (event3.getY() > event.getY())) {
                    tempgc.strokeRoundRect(event3.getX(), event.getY(), event.getX() - event3.getX(), event3.getY() - event.getY(), 25.0, 25.0);
                } else if ((event3.getX() < event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.strokeRoundRect(event3.getX(), event3.getY(), event.getX() - event3.getX(), event.getY() - event3.getY(), 25.0, 25.0);
                } else {
                    tempgc.strokeRoundRect(event.getX(), event.getY(), event3.getX() - event.getX(), event3.getY() - event.getY(), 25.0, 25.0);
                }
                //Place the wire round square
                tempCanvas.setOnMouseReleased((MouseEvent event2) -> {
                    if ((event2.getX() > event.getX()) && (event2.getY() < event.getY())) {
                        gc.strokeRoundRect(event.getX(), event2.getY(), event2.getX() - event.getX(), event.getY() - event2.getY(), 25.0, 25.0);
                    } else if ((event2.getX() < event.getX()) && (event2.getY() > event.getY())) {
                        gc.strokeRoundRect(event2.getX(), event.getY(), event.getX() - event2.getX(), event2.getY() - event.getY(), 25.0, 25.0);
                    } else if ((event2.getX() < event.getX()) && (event2.getY() < event.getY())) {
                        gc.strokeRoundRect(event2.getX(), event2.getY(), event.getX() - event2.getX(), event.getY() - event2.getY(), 25.0, 25.0);
                    } else {
                        gc.strokeRoundRect(event.getX(), event.getY(), event2.getX() - event.getX(), event2.getY() - event.getY(), 25.0, 25.0);
                    }
                    saved = false; //Used for smart save
                    tempgc.clearRect(0, 0, sceneX, sceneY);
                    tempCanvas.setOnMouseDragged(null);
                    tempCanvas.setOnMouseReleased(null);
                });
                tempCanvas.setOnMousePressed(null);
            });
        });
        saved = false; //Used for smart save
    }

    /**
     * Draws the wire circle. The if statements make it so one can draw from any
     * direction.
     * @param scene 
     */
    public void wireCircle(Scene scene) {
        //Start the process but creating a new canvas and adding it to the stack
        Canvas newCanvas = new Canvas(sceneX, sceneY);

        GraphicsContext gc = newCanvas.getGraphicsContext2D();
        canvasStack.push(newCanvas);

        ((VBox) scene.getRoot()).getChildren().removeAll(stackPane);
        stackPane.getChildren().removeAll(canvasStack);
        stackPane.getChildren().addAll(canvasStack);
        ((VBox) scene.getRoot()).getChildren().addAll(stackPane);
        //Set line width and color
        gc.setStroke(colorChooser.getValue());
        gc.setFill(colorChooser.getValue());
        gc.setLineWidth(widthInt);

        tempgc.setStroke(colorChooser.getValue());
        tempgc.setFill(colorChooser.getValue());
        tempgc.setLineWidth(widthInt);
        tempCanvas.toFront();
        tempCanvas.setOnMousePressed((MouseEvent event) -> {
            //Preview the wire circle
            tempCanvas.setOnMouseDragged((MouseEvent event3) -> {
                tempgc.clearRect(0, 0, sceneX, sceneY);
                if ((event3.getX() > event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.strokeOval(event.getX(), event3.getY(), event3.getX() - event.getX(), event.getY() - event3.getY());
                } else if ((event3.getX() < event.getX()) && (event3.getY() > event.getY())) {
                    tempgc.strokeOval(event3.getX(), event.getY(), event.getX() - event3.getX(), event3.getY() - event.getY());
                } else if ((event3.getX() < event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.strokeOval(event3.getX(), event3.getY(), event.getX() - event3.getX(), event.getY() - event3.getY());
                } else {
                    tempgc.strokeOval(event.getX(), event.getY(), event3.getX() - event.getX(), event3.getY() - event.getY());
                }
                //Place the wire circle
                tempCanvas.setOnMouseReleased((MouseEvent event2) -> {
                    if ((event2.getX() > event.getX()) && (event2.getY() < event.getY())) {
                        gc.strokeOval(event.getX(), event2.getY(), event2.getX() - event.getX(), event.getY() - event2.getY());
                    } else if ((event2.getX() < event.getX()) && (event2.getY() > event.getY())) {
                        gc.strokeOval(event2.getX(), event.getY(), event.getX() - event2.getX(), event2.getY() - event.getY());
                    } else if ((event2.getX() < event.getX()) && (event2.getY() < event.getY())) {
                        gc.strokeOval(event2.getX(), event2.getY(), event.getX() - event2.getX(), event.getY() - event2.getY());
                    } else {
                        gc.strokeOval(event.getX(), event.getY(), event2.getX() - event.getX(), event2.getY() - event.getY());
                    }
                    saved = false; //Used for smart save
                    tempgc.clearRect(0, 0, sceneX, sceneY);
                    tempCanvas.setOnMouseDragged(null);
                    tempCanvas.setOnMouseReleased(null);
                });
            });
            tempCanvas.setOnMousePressed(null);
        });
        saved = false; //Used for smart save
    }

    /**
     * Draws the solid square. The if statements make it so one can draw from 
     * any direction.
     * @param scene 
     */
    public void solidSquare(Scene scene) {
        Canvas newCanvas = new Canvas(sceneX, sceneY);

        GraphicsContext gc = newCanvas.getGraphicsContext2D();
        canvasStack.push(newCanvas);

        ((VBox) scene.getRoot()).getChildren().removeAll(stackPane);
        stackPane.getChildren().removeAll(canvasStack);
        stackPane.getChildren().addAll(canvasStack);
        ((VBox) scene.getRoot()).getChildren().addAll(stackPane);
        gc.setStroke(colorChooser.getValue());
        gc.setFill(colorChooser.getValue());
        gc.setLineWidth(widthInt);

        tempgc.setStroke(colorChooser.getValue());
        tempgc.setFill(colorChooser.getValue());
        tempgc.setLineWidth(widthInt);
        tempCanvas.toFront();
        //Preview the solid square
        tempCanvas.setOnMousePressed((MouseEvent event) -> {
            tempCanvas.setOnMouseDragged((MouseEvent event3) -> {
                tempgc.clearRect(0, 0, sceneX, sceneY);
                if ((event3.getX() > event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.fillRect(event.getX(), event3.getY(), event3.getX() - event.getX(), event.getY() - event3.getY());
                } else if ((event3.getX() < event.getX()) && (event3.getY() > event.getY())) {
                    tempgc.fillRect(event3.getX(), event.getY(), event.getX() - event3.getX(), event3.getY() - event.getY());
                } else if ((event3.getX() < event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.fillRect(event3.getX(), event3.getY(), event.getX() - event3.getX(), event.getY() - event3.getY());
                } else {
                    tempgc.fillRect(event.getX(), event.getY(), event3.getX() - event.getX(), event3.getY() - event.getY());
                }
                //Place the solid square
                tempCanvas.setOnMouseReleased((MouseEvent event2) -> {
                    if ((event2.getX() > event.getX()) && (event2.getY() < event.getY())) {
                        gc.fillRect(event.getX(), event2.getY(), event2.getX() - event.getX(), event.getY() - event2.getY());
                    } else if ((event2.getX() < event.getX()) && (event2.getY() > event.getY())) {
                        gc.fillRect(event2.getX(), event.getY(), event.getX() - event2.getX(), event2.getY() - event.getY());
                    } else if ((event2.getX() < event.getX()) && (event2.getY() < event.getY())) {
                        gc.fillRect(event2.getX(), event2.getY(), event.getX() - event2.getX(), event.getY() - event2.getY());
                    } else {
                        gc.fillRect(event.getX(), event.getY(), event2.getX() - event.getX(), event2.getY() - event.getY());
                    }
                    saved = false; //Used for smart save
                    tempgc.clearRect(0, 0, sceneX, sceneY);
                    tempCanvas.setOnMouseDragged(null);
                    tempCanvas.setOnMouseReleased(null);
                });
                tempCanvas.setOnMousePressed(null);
            });
        });
        saved = false; //Used for smart save
    }

    /**
     * Draws the solid round square. The if statements make it so one can draw 
     * from any direction.
     * @param scene 
     */
    public void solidRoundSquare(Scene scene) {
        //Start the process by creating a new canvas and adding it to the stack
        Canvas newCanvas = new Canvas(sceneX, sceneY);

        GraphicsContext gc = newCanvas.getGraphicsContext2D();
        canvasStack.push(newCanvas);

        ((VBox) scene.getRoot()).getChildren().removeAll(stackPane);
        stackPane.getChildren().removeAll(canvasStack);
        stackPane.getChildren().addAll(canvasStack);
        ((VBox) scene.getRoot()).getChildren().addAll(stackPane);
        gc.setStroke(colorChooser.getValue());
        gc.setFill(colorChooser.getValue());
        gc.setLineWidth(widthInt);
        //Set the color and the size
        tempgc.setStroke(colorChooser.getValue());
        tempgc.setFill(colorChooser.getValue());
        tempgc.setLineWidth(widthInt);
        tempCanvas.toFront();
        
        tempCanvas.setOnMousePressed((MouseEvent event) -> {
            tempCanvas.setOnMouseDragged((MouseEvent event3) -> {
                //Preview the round square
                tempgc.clearRect(0, 0, sceneX, sceneY);
                if ((event3.getX() > event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.fillRoundRect(event.getX(), event3.getY(), event3.getX() - event.getX(), event.getY() - event3.getY(), 25.0, 25.0);
                } else if ((event3.getX() < event.getX()) && (event3.getY() > event.getY())) {
                    tempgc.fillRoundRect(event3.getX(), event.getY(), event.getX() - event3.getX(), event3.getY() - event.getY(), 25.0, 25.0);
                } else if ((event3.getX() < event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.fillRoundRect(event3.getX(), event3.getY(), event.getX() - event3.getX(), event.getY() - event3.getY(), 25.0, 25.0);
                } else {
                    tempgc.fillRoundRect(event.getX(), event.getY(), event3.getX() - event.getX(), event3.getY() - event.getY(), 25.0, 25.0);
                }
                //Actually place the round square
                tempCanvas.setOnMouseReleased((MouseEvent event2) -> {
                    if ((event2.getX() > event.getX()) && (event2.getY() < event.getY())) {
                        gc.fillRoundRect(event.getX(), event2.getY(), event2.getX() - event.getX(), event.getY() - event2.getY(), 25.0, 25.0);
                    } else if ((event2.getX() < event.getX()) && (event2.getY() > event.getY())) {
                        gc.fillRoundRect(event2.getX(), event.getY(), event.getX() - event2.getX(), event2.getY() - event.getY(), 25.0, 25.0);
                    } else if ((event2.getX() < event.getX()) && (event2.getY() < event.getY())) {
                        gc.fillRoundRect(event2.getX(), event2.getY(), event.getX() - event2.getX(), event.getY() - event2.getY(), 25.0, 25.0);
                    } else {
                        gc.fillRoundRect(event.getX(), event.getY(), event2.getX() - event.getX(), event2.getY() - event.getY(), 25.0, 25.0);
                    }
                    saved = false; //Used for smart save
                    tempgc.clearRect(0, 0, sceneX, sceneY);
                    tempCanvas.setOnMouseDragged(null);
                    tempCanvas.setOnMouseReleased(null);
                });
                tempCanvas.setOnMousePressed(null);
            });
        });
        saved = false; //Used for smart save
    }

    /**
     * Draws the solid circle. The if statements make it so one can draw from any
     * direction.
     * @param scene 
     */
    public void solidCircle(Scene scene) {
        //Start the process by creating a new canvas and adding it to the stack
        Canvas newCanvas = new Canvas(sceneX, sceneY);

        GraphicsContext gc = newCanvas.getGraphicsContext2D();
        canvasStack.push(newCanvas);

        ((VBox) scene.getRoot()).getChildren().removeAll(stackPane);
        stackPane.getChildren().removeAll(canvasStack);
        stackPane.getChildren().addAll(canvasStack);
        ((VBox) scene.getRoot()).getChildren().addAll(stackPane);
        gc.setStroke(colorChooser.getValue());
        gc.setFill(colorChooser.getValue());
        gc.setLineWidth(widthInt);
        
        //Set the color and size
        tempgc.setStroke(colorChooser.getValue());
        tempgc.setFill(colorChooser.getValue());
        tempgc.setLineWidth(widthInt);
        tempCanvas.toFront();
        tempCanvas.setOnMousePressed((MouseEvent event) -> {
            //Preview the solid circle
            tempCanvas.setOnMouseDragged((MouseEvent event3) -> {
                tempgc.clearRect(0, 0, sceneX, sceneY);
                if ((event3.getX() > event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.fillOval(event.getX(), event3.getY(), event3.getX() - event.getX(), event.getY() - event3.getY());
                } else if ((event3.getX() < event.getX()) && (event3.getY() > event.getY())) {
                    tempgc.fillOval(event3.getX(), event.getY(), event.getX() - event3.getX(), event3.getY() - event.getY());
                } else if ((event3.getX() < event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.fillOval(event3.getX(), event3.getY(), event.getX() - event3.getX(), event.getY() - event3.getY());
                } else {
                    tempgc.fillOval(event.getX(), event.getY(), event3.getX() - event.getX(), event3.getY() - event.getY());
                }
                //Place the solid circle
                tempCanvas.setOnMouseReleased((MouseEvent event2) -> {
                    if ((event2.getX() > event.getX()) && (event2.getY() < event.getY())) {
                        gc.fillOval(event.getX(), event2.getY(), event2.getX() - event.getX(), event.getY() - event2.getY());
                    } else if ((event2.getX() < event.getX()) && (event2.getY() > event.getY())) {
                        gc.fillOval(event2.getX(), event.getY(), event.getX() - event2.getX(), event2.getY() - event.getY());
                    } else if ((event2.getX() < event.getX()) && (event2.getY() < event.getY())) {
                        gc.fillOval(event2.getX(), event2.getY(), event.getX() - event2.getX(), event.getY() - event2.getY());
                    } else {
                        gc.fillOval(event.getX(), event.getY(), event2.getX() - event.getX(), event2.getY() - event.getY());
                    }
                    saved = false; //Used for smart save//Used for smart save
                    tempgc.clearRect(0, 0, sceneX, sceneY);
                    tempCanvas.setOnMouseDragged(null);
                    tempCanvas.setOnMouseReleased(null);
                });
            });
            tempCanvas.setOnMousePressed(null);
        });
        saved = false; //Used for smart save//Used for smart save
    }

    /**
     * This method gets the width that was entered by the user.
     * @param inputWidth 
     */
    public void widthControl(TextField inputWidth) {
        inputWidth.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                widthInt = Integer.parseInt(inputWidth.getText());
            }
        });
    }

    /**
     * The method places the text that the user has entered.
     * @param scene
     * @param inputText 
     */
    public void text(Scene scene, TextField inputText) {
        inputText.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                textString = inputText.getText();
            }
        });
        //Start the process by creating a new canvas and adding it to the stack
        Canvas newCanvas = new Canvas(sceneX, sceneY);

        GraphicsContext gc = newCanvas.getGraphicsContext2D();
        canvasStack.push(newCanvas);
        
        //Remove and re-add the canvas stack
        ((VBox) scene.getRoot()).getChildren().removeAll(stackPane);
        stackPane.getChildren().removeAll(canvasStack);
        stackPane.getChildren().addAll(canvasStack);
        ((VBox) scene.getRoot()).getChildren().addAll(stackPane);
        gc.setStroke(colorChooser.getValue());
        gc.setFill(colorChooser.getValue());
        Font font = new Font("Verdena", widthInt);
        gc.setFont(font);
        tempgc.setFont(font);
        
        //Set the size and color
        tempgc.setStroke(colorChooser.getValue());
        tempgc.setLineWidth(widthInt);
        tempCanvas.toFront();

        //Preview the drawing of the text and place it
        tempCanvas.setOnMouseMoved((MouseEvent event3) -> {
            tempgc.clearRect(0, 0, sceneX, sceneY);
            tempgc.strokeText(textString, event3.getX(), event3.getY());
            tempCanvas.setOnMouseReleased((MouseEvent event2) -> {
                gc.strokeText(textString, event2.getX(), event2.getY());
                tempgc.clearRect(0, 0, sceneX, sceneY);
                tempCanvas.setOnMouseMoved(null);
                tempCanvas.setOnMouseReleased(null);
            });
            tempCanvas.setOnMousePressed(null);
        });
        saved = false; //Used for smart save
    }

    /**
     * This method cuts and places a piece of the image by taking a screen shot
     * based on the users mouse movement and then creating another square which
     * is then filled with that screen shot.
     * @param scene 
     */
    public void cut(Scene scene) {
        //Start the process with a new canvas being added to the stack
        Canvas newCanvas = new Canvas(sceneX, sceneY);
        GraphicsContext gc = newCanvas.getGraphicsContext2D();
        canvasStack.push(newCanvas);
        gc.setFill(Color.WHITE);
        ((VBox) scene.getRoot()).getChildren().removeAll(stackPane);
        stackPane.getChildren().removeAll(canvasStack);
        stackPane.getChildren().addAll(canvasStack);
        ((VBox) scene.getRoot()).getChildren().addAll(stackPane);
        tempCanvas.toFront();
        gc.setLineWidth(1); //This is so the changing line width from the user
                            //Does not effect the line width
                            
        //First draw a square and show the user as it draws
        tempCanvas.setOnMousePressed((MouseEvent event) -> {
            tempCanvas.setOnMouseDragged((MouseEvent event3) -> {
                tempgc.clearRect(0, 0, sceneX, sceneY);
                if ((event3.getX() > event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.strokeRect(event.getX(), event3.getY(), event3.getX() - event.getX(), event.getY() - event3.getY());
                } else if ((event3.getX() < event.getX()) && (event3.getY() > event.getY())) {
                    tempgc.strokeRect(event3.getX(), event.getY(), event.getX() - event3.getX(), event3.getY() - event.getY());
                } else if ((event3.getX() < event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.strokeRect(event3.getX(), event3.getY(), event.getX() - event3.getX(), event.getY() - event3.getY());
                } else {
                    tempgc.strokeRect(event.getX(), event.getY(), event3.getX() - event.getX(), event3.getY() - event.getY());
                }
                tempCanvas.setOnMouseReleased((MouseEvent event2) -> {
                    SnapshotParameters snapParams = new SnapshotParameters();

                    int x = ((int) scene.getWidth() - sceneX) / 2;
                    int y = ((int) scene.getHeight() - (int) tempCanvas.getHeight()) / 2;
                    int firstEventX = (int) event.getX();
                    int firstEventY = (int) event.getY();
                    int secondEventX = (int) event2.getX();
                    int secondEventY = (int) event2.getY();
                    int thirdEventX = (int) event3.getX();
                    int thirdEventY = (int) event3.getY();
                    //All of these if statements are to prevent the user from
                    //selecting parts of the image outside of the view port
                    if (firstEventX > sceneX) {
                        firstEventX = sceneX;
                    }
                    if (firstEventX < 0) {
                        firstEventX = 0;
                    }
                    if (secondEventX > sceneX) {
                        secondEventX = sceneX;
                    }
                    if (secondEventX < 0) {
                        secondEventX = 0;
                    }
                    if (thirdEventX > sceneX) {
                        thirdEventX = sceneX;
                    }
                    if (thirdEventX < 0) {
                        thirdEventX = 0;
                    }
                    if (firstEventY > sceneY) {
                        firstEventY = sceneY;
                    }
                    if (firstEventY < 0) {
                        firstEventY = 0;
                    }
                    if (secondEventY > sceneY) {
                        secondEventY = sceneY;
                    }
                    if (secondEventY < 0) {
                        secondEventY = 0;
                    }
                    if (thirdEventY > sceneY) {
                        thirdEventY = sceneY;
                    }
                    if (thirdEventY < 0) {
                        thirdEventY = 0;
                    }
                    //Here we grab the screenshot based on the created square
                    int width = abs((int) firstEventX - (int) secondEventX);
                    int height = abs((int) firstEventY - (int) secondEventY);
                    if ((thirdEventX > firstEventX) && (thirdEventY < firstEventY)) {
                        snapParams.setViewport(new Rectangle2D(x + thirdEventX, y + thirdEventY - 25, width, height));
                    } else if ((thirdEventX < firstEventX) && (thirdEventY > firstEventY)) {
                        snapParams.setViewport(new Rectangle2D(x + thirdEventX, y + firstEventY - 25, width, height));
                    } else if ((thirdEventX < firstEventX) && (thirdEventY < firstEventY)) {
                        snapParams.setViewport(new Rectangle2D(x + thirdEventX, y + thirdEventY - 25, width, height));
                    } else {
                        snapParams.setViewport(new Rectangle2D(x + firstEventX, y + firstEventY - 25, width, height));
                    }
                    saved = false; //Used for smart save
                    //Create a new rectangle and fill it with the screen shot so the
                    //user can see the placement
                    tempgc.clearRect(0, 0, sceneX, sceneY);
                    tempCanvas.setOnMouseDragged(null);
                    tempCanvas.setOnMouseReleased(null);
                    WritableImage writableImage = new WritableImage(width, height);
                    stackPane.snapshot(snapParams, writableImage);
                    if ((event3.getX() > event.getX()) && (event3.getY() < event.getY())) {
                        gc.fillRect(event.getX(), event3.getY(), event3.getX() - event.getX(), event.getY() - event3.getY());
                    } else if ((event3.getX() < event.getX()) && (event3.getY() > event.getY())) {
                        gc.fillRect(event3.getX(), event.getY(), event.getX() - event3.getX(), event3.getY() - event.getY());
                    } else if ((event3.getX() < event.getX()) && (event3.getY() < event.getY())) {
                        gc.fillRect(event3.getX(), event3.getY(), event.getX() - event3.getX(), event.getY() - event3.getY());
                    } else {
                        gc.fillRect(event.getX(), event.getY(), event3.getX() - event.getX(), event3.getY() - event.getY());
                    }
                    tempCanvas.setOnMouseMoved((MouseEvent event4) -> {
                        tempgc.clearRect(0, 0, sceneX, sceneY);
                        tempgc.setFill(new ImagePattern(writableImage));
                        tempgc.fillRect(event4.getX(), event4.getY(), width, height);
                        //Here we fill the rectangle after the user creates a new one
                            tempCanvas.setOnMousePressed((MouseEvent event5) -> {
                            tempgc.clearRect(0, 0, sceneX, sceneY);
                            gc.setFill(new ImagePattern(writableImage));
                            gc.fillRect(event5.getX(), event5.getY(), width, height);
                            tempCanvas.setOnMouseMoved(null);
                            tempCanvas.setOnMousePressed(null);
                        });
                        tempCanvas.setOnMouseReleased(null);
                    });
                });
            });
            tempCanvas.setOnMousePressed(null);
        });
        saved = false; //Used for smart save//Used for smart save
    }

    /**
     * Dropper tool method that gets the color where the user clicks by checking
     * the color of the pixel.
     */
    public void dropper() {
        //Use pixel reader to get the pixel that is clicked
        SnapshotParameters snapParams = new SnapshotParameters();
        WritableImage wImage = new WritableImage(sceneX, sceneY);
        PixelReader pixelReader = wImage.getPixelReader();
        int x = ((int) stackPane.getWidth() - sceneX) / 2;
        int y = ((int) stackPane.getHeight() - sceneY) / 2;
        snapParams.setViewport(new Rectangle2D(x, y + 75, sceneX, sceneY));
        tempCanvas.toFront();
        stackPane.snapshot(snapParams, wImage);
        tempCanvas.setOnMousePressed((MouseEvent event) -> {
            System.out.println(pixelReader.getColor((int)event.getX(), (int)event.getY()));
            colorChooser.setValue(pixelReader.getColor((int)event.getX(), (int)event.getY()));
            tempCanvas.setOnMousePressed(null);
        });
    }
}