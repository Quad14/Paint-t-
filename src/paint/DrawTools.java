package paint;

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
import static paint.GUI.tempgc;

public class DrawTools extends Menus {

    int widthInt = 1;
    String textString = "";

    public void line(Scene scene) {
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

    public void freeHand(Scene scene) {
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

    public void eraser(Scene scene) {
        Canvas newCanvas = new Canvas(sceneX, sceneY);

        GraphicsContext gc = newCanvas.getGraphicsContext2D();
        canvasStack.push(newCanvas);

        ((VBox) scene.getRoot()).getChildren().removeAll(stackPane);
        stackPane.getChildren().removeAll(canvasStack);
        stackPane.getChildren().addAll(canvasStack);
        ((VBox) scene.getRoot()).getChildren().addAll(stackPane);
        gc.setStroke(Color.WHITE);
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

    public void wireSquare(Scene scene) {
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

    public void wireRoundSquare(Scene scene) {
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
                    tempgc.strokeRoundRect(event.getX(), event3.getY(), event3.getX() - event.getX(), event.getY() - event3.getY(), 25.0, 25.0);
                } else if ((event3.getX() < event.getX()) && (event3.getY() > event.getY())) {
                    tempgc.strokeRoundRect(event3.getX(), event.getY(), event.getX() - event3.getX(), event3.getY() - event.getY(), 25.0, 25.0);
                } else if ((event3.getX() < event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.strokeRoundRect(event3.getX(), event3.getY(), event.getX() - event3.getX(), event.getY() - event3.getY(), 25.0, 25.0);
                } else {
                    tempgc.strokeRoundRect(event.getX(), event.getY(), event3.getX() - event.getX(), event3.getY() - event.getY(), 25.0, 25.0);
                }

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

    public void wireCircle(Scene scene) {
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

    public void solidRoundSquare(Scene scene) {
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
                    tempgc.fillRoundRect(event.getX(), event3.getY(), event3.getX() - event.getX(), event.getY() - event3.getY(), 25.0, 25.0);
                } else if ((event3.getX() < event.getX()) && (event3.getY() > event.getY())) {
                    tempgc.fillRoundRect(event3.getX(), event.getY(), event.getX() - event3.getX(), event3.getY() - event.getY(), 25.0, 25.0);
                } else if ((event3.getX() < event.getX()) && (event3.getY() < event.getY())) {
                    tempgc.fillRoundRect(event3.getX(), event3.getY(), event.getX() - event3.getX(), event.getY() - event3.getY(), 25.0, 25.0);
                } else {
                    tempgc.fillRoundRect(event.getX(), event.getY(), event3.getX() - event.getX(), event3.getY() - event.getY(), 25.0, 25.0);
                }

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

    public void solidCircle(Scene scene) {
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

    public void text(Scene scene, TextField inputText) {
        inputText.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                
            }
        });
        textString = inputText.getText();
        Canvas newCanvas = new Canvas(sceneX, sceneY);

        GraphicsContext gc = newCanvas.getGraphicsContext2D();
        canvasStack.push(newCanvas);

        ((VBox) scene.getRoot()).getChildren().removeAll(stackPane);
        stackPane.getChildren().removeAll(canvasStack);
        stackPane.getChildren().addAll(canvasStack);
        ((VBox) scene.getRoot()).getChildren().addAll(stackPane);
        gc.setStroke(colorChooser.getValue());
        gc.setFill(colorChooser.getValue());
        Font font = new Font("Verdena", widthInt);
        gc.setFont(font);
        tempgc.setFont(font);

        tempgc.setStroke(colorChooser.getValue());
        //tempgc.setFill(colorChooser.getValue());
        tempgc.setLineWidth(widthInt);
        tempCanvas.toFront();

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
        saved = false;
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
                    int secondEventX = (int) event2.getX();
                    int secondEventY = (int) event2.getY();
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

    public void dropper() {
        SnapshotParameters snapParams = new SnapshotParameters();
        WritableImage wImage = new WritableImage(sceneX, sceneY);
        PixelReader pixelReader = wImage.getPixelReader();
        int x = ((int) stackPane.getWidth() - sceneX) / 2;
        int y = ((int) stackPane.getHeight() - sceneY) / 2;
        snapParams.setViewport(new Rectangle2D(x, y + 75, sceneX, sceneY));
        tempCanvas.toFront();
        stackPane.snapshot(snapParams, wImage);
        tempCanvas.setOnMousePressed((MouseEvent event) -> {
            System.out.println("Here ohhhh");
            System.out.println(pixelReader.getColor((int)event.getX(), (int)event.getY()));
            colorChooser.setValue(pixelReader.getColor((int)event.getX(), (int)event.getY()));
            tempCanvas.setOnMousePressed(null);
        });
    }
}
