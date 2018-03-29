package com.github.evabishchevich.figures.drawer.shape;

import com.github.evabishchevich.figures.drawer.figures.Parallelogram;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class ParallelogramShape implements DrawingShape {

    private Parallelogram parallelogram;
    private Color color;

    public ParallelogramShape(Parallelogram parallelogram, Color color) {
        this.parallelogram = parallelogram;
        this.color = color;
    }

    @Override
    public Shape getShape() {
        double[] coordinates = new double[]{
                parallelogram.point1.x,
                parallelogram.point1.y,
                parallelogram.point2.x,
                parallelogram.point2.y,
                parallelogram.point3.x,
                parallelogram.point3.y,
                parallelogram.point4.x,
                parallelogram.point4.y
        };
        Shape rawShape = new Polygon(coordinates);
        rawShape.setFill(color);
        rawShape.setStroke(Color.BLACK);
        rawShape.setStrokeWidth(1.0);
        return rawShape;
    }
}
