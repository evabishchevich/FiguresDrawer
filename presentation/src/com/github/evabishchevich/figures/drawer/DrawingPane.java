package com.github.evabishchevich.figures.drawer;

import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class DrawingPane {

    private Pane pane;
//    private ColorPicker colorPicker;
    private int figuresCount = 0;

    DrawingPane(int colorPickerWidth) {
        pane = new Pane();
        pane.setPadding(new Insets(20));
        pane.setStyle("-fx-background-color: white");
        pane.setMinWidth(Region.USE_COMPUTED_SIZE);

//        colorPicker = new ColorPicker(Color.YELLOWGREEN);
//        colorPicker.setMinWidth(colorPickerWidth);
    }

    Pane getPane() {
        return pane;
    }

//    public Color getColor() {
//        return colorPicker.getValue();
//    }

//    ColorPicker getColorPicker() {
//        return colorPicker;
//    }

    void add(Shape shape) {
        pane.getChildren().add(figuresCount++, shape);
    }

    void removeLast() {
        if (figuresCount > 0) {
            pane.getChildren().remove(--figuresCount);
        }
    }

    public void clear() {
        pane.getChildren().clear();
    }
}
