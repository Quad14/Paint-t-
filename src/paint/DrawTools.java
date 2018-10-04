package paint;

import static java.lang.Math.abs;
import java.util.Stack;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class DrawTools extends Menus {

    int widthInt = 1;

    public void line(Scene scene, ColorPicker colorChooser) {
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
        tempCanvas.setOnMousePressed((MouseEvent event) -> {
            tempCanvas.setOnMouseDragged((MouseEvent event3) -> {
                tempgc.clearRect(0, 0, sceneX, sceneY);
                tempgc.strokeLine(event.getX(), event.getY(), event3.getX(), event3.getY());

                tempCanvas.setOnMouseReleased((MouseEvent event2) -> {
                    gc.strokeLine(event.getX(), event.getY(), event2.getX(), event2.getY());
                    tempgc.clearRect(0, 0, sceneX, sceneY);
                    tempCanvas.setOnMouseDragged(null);
                    tempCanvas.setOnMouseReleased(null);
                });
            });
            tempCanvas.setOnMousePressed(null);
        });
        saved = false;
    }

    public void freeHand(Scene scene, ColorPicker colorChooser) {
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

        newCanvas.setOnMouseDragged((MouseEvent event2) -> {
            gc.lineTo(event2.getX(), event2.getY());
            gc.stroke();
        });
        newCanvas.setOnMouseReleased((MouseEvent event3) -> {
            newCanvas.setOnMouseDragged(null);
            newCanvas.setOnMouseReleased(null);
        });
        saved = false;
    }

    public void wireSquare(Scene scene, ColorPicker colorChooser) {
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
        saved = false;
    }

    public void wireCircle(Scene scene, ColorPicker colorChooser) {
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
        tempCanvas.setOnMousePressed((MouseEvent event) -> {
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
                    saved = false;
                    tempgc.clearRect(0, 0, sceneX, sceneY);
                    tempCanvas.setOnMouseDragged(null);
                    tempCanvas.setOnMouseReleased(null);
                });
            });
            tempCanvas.setOnMousePressed(null);
        });
        saved = false;
    }

    public void solidSquare(Scene scene, ColorPicker colorChooser) {
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
                    saved = false;
                    tempgc.clearRect(0, 0, sceneX, sceneY);
                    tempCanvas.setOnMouseDragged(null);
                    tempCanvas.setOnMouseReleased(null);
                });
                tempCanvas.setOnMousePressed(null);
            });
        });
        saved = false;
    }

    public void solidCircle(Scene scene, ColorPicker colorChooser) {
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
        tempCanvas.setOnMousePressed((MouseEvent event) -> {
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
                    saved = false;
                    tempgc.clearRect(0, 0, sceneX, sceneY);
                    tempCanvas.setOnMouseDragged(null);
                    tempCanvas.setOnMouseReleased(null);
                });
            });
            tempCanvas.setOnMousePressed(null);
        });
        saved = false;
    }

    public void widthControl(TextField inputWidth) {
        inputWidth.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                widthInt = Integer.parseInt(inputWidth.getText());
            }
        });
    }

    public void cut(Scene scene) {
        Canvas newCanvas = new Canvas(sceneX, sceneY);

        GraphicsContext gc = newCanvas.getGraphicsContext2D();
        canvasStack.push(newCanvas);
        gc.setFill(Color.WHITE);
        ((VBox) scene.getRoot()).getChildren().removeAll(stackPane);
        stackPane.getChildren().removeAll(canvasStack);
        stackPane.getChildren().addAll(canvasStack);
        ((VBox) scene.getRoot()).getChildren().addAll(stackPane);
        tempCanvas.toFront();

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
                    int secondEventX = (int)event2.getX();
                    int secondEventY = (int)event2.getY();
                    int thirdEventX = (int) event3.getX();
                    int thirdEventY = (int) event3.getY();
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

                    int width = abs((int) firstEventX - (int) secondEventX);
                    int height = abs((int) firstEventY - (int) secondEventY);
                    if ((thirdEventX > firstEventX) && (thirdEventY < firstEventY)) {
                        snapParams.setViewport(new Rectangle2D(x + thirdEventX, y + thirdEventY - 25, width, height));
                        //gc.fillRect(firstEventX, thirdEventY, thirdEventX - firstEventX, firstEventY - thirdEventY);
                    } else if ((thirdEventX < firstEventX) && (thirdEventY > firstEventY)) {
                        snapParams.setViewport(new Rectangle2D(x + thirdEventX, y + firstEventY - 25, width, height));
                        //gc.fillRect(thirdEventX, firstEventY, firstEventX - thirdEventX, thirdEventY - firstEventY);
                    } else if ((thirdEventX < firstEventX) && (thirdEventY < firstEventY)) {
                        snapParams.setViewport(new Rectangle2D(x + thirdEventX, y + thirdEventY - 25, width, height));
                        //gc.fillRect(thirdEventX, thirdEventY, firstEventX - thirdEventX, firstEventY - thirdEventY);
                    } else {
                        snapParams.setViewport(new Rectangle2D(x + firstEventX, y + firstEventY - 25, width, height));
                        //gc.fillRect(firstEventX, firstEventY, thirdEventX - firstEventX, thirdEventY - firstEventY); 
                    }


                    //snapParams.setViewport(new Rectangle2D(x + event.getX(), y + event.getY() - 25, width, height));
                    saved = false;
                    tempgc.clearRect(0, 0, sceneX, sceneY);
                    tempCanvas.setOnMouseDragged(null);
                    tempCanvas.setOnMouseReleased(null);
                    WritableImage writableImage = new WritableImage(width, height);
                    stackPane.snapshot(snapParams, writableImage);
                    if ((event3.getX() > event.getX()) && (event3.getY() < event.getY())) {
                        //snapParams.setViewport(new Rectangle2D(x + event3.getX(), y + event3.getY() - 25, width, height));
                        gc.fillRect(event.getX(), event3.getY(), event3.getX() - event.getX(), event.getY() - event3.getY());
                    } else if ((event3.getX() < event.getX()) && (event3.getY() > event.getY())) {
                        //snapParams.setViewport(new Rectangle2D(x + event3.getX(), y + event.getY() - 25, width, height));
                        gc.fillRect(event3.getX(), event.getY(), event.getX() - event3.getX(), event3.getY() - event.getY());
                    } else if ((event3.getX() < event.getX()) && (event3.getY() < event.getY())) {
                        //snapParams.setViewport(new Rectangle2D(x + event3.getX(), y + event3.getY() - 25, width, height));
                        gc.fillRect(event3.getX(), event3.getY(), event.getX() - event3.getX(), event.getY() - event3.getY());
                    } else {
                        //snapParams.setViewport(new Rectangle2D(x + event.getX(), y + event.getY() - 25, width, height));
                        gc.fillRect(event.getX(), event.getY(), event3.getX() - event.getX(), event3.getY() - event.getY());
                    }
                    tempCanvas.setOnMouseMoved((MouseEvent event4) -> {
                        tempgc.clearRect(0, 0, sceneX, sceneY);
                        tempgc.setFill(new ImagePattern(writableImage));
                        tempgc.fillRect(event4.getX(), event4.getY(), width, height);
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
        saved = false;
    }
}
