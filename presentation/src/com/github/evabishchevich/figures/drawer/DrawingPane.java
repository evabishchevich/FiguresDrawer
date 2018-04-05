package com.github.evabishchevich.figures.drawer;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class DrawingPane {

    private Pane pane;
    private int figuresCount = 0;
    private Color color;

    DrawingPane(Color color) {
        pane = new Pane();
        pane.setPadding(new Insets(20));
        pane.setStyle("-fx-background-color: white");
        pane.setMinWidth(Region.USE_COMPUTED_SIZE);
        this.color = color;
    }

    Pane getPane() {
        return pane;
    }

    void add(Shape shape) {
        shape.setFill(color);
        pane.getChildren().add(figuresCount++, shape);
    }

    void removeLast() {
        if (figuresCount > 0) {
            pane.getChildren().remove(--figuresCount);
        }
    }

    public void clear() {
        pane.getChildren().clear();
        figuresCount = 0;
    }
}
