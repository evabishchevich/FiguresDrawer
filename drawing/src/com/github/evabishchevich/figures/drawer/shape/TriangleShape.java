package com.github.evabishchevich.figures.drawer.shape;

import com.github.evabishchevich.figures.drawer.figures.Triangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class TriangleShape implements DrawingShape {

    private Triangle triangle;
    private Color color;

    public TriangleShape(Triangle triangle, Color color) {
        this.triangle = triangle;
        this.color = color;
    }

    @Override
    public Shape getShape() {
        double[] coordinates = new double[]{
                triangle.a.x,
                triangle.a.y,
                triangle.b.x,
                triangle.b.y,
                triangle.c.x,
                triangle.c.y
        };
        Shape rawShape = new Polygon(coordinates);
        rawShape.setFill(color);
        rawShape.setStroke(Color.BLACK);
        rawShape.setStrokeWidth(1.0);
        return rawShape;
    }
}
