package com.github.evabishchevich.figures.drawer.shape;

import com.github.evabishchevich.figures.drawer.figures.Ellipse;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class EllipseShape implements DrawingShape {

    private Ellipse ellipse;

    public EllipseShape(Ellipse ellipse) {
        this.ellipse = ellipse;
    }

    @Override
    public Shape getShape() {
        Shape rawShape = new javafx.scene.shape.Ellipse(
                ellipse.center.x,
                ellipse.center.y,
                ellipse.horizontalRadius,
                ellipse.verticalRadius
        );
        rawShape.setStroke(Color.BLACK);
        rawShape.setStrokeWidth(1.0);
        return rawShape;
    }
}
