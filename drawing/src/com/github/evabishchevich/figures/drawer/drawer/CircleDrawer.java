package com.github.evabishchevich.figures.drawer.drawer;

import com.github.evabishchevich.figures.drawer.Point;
import com.github.evabishchevich.figures.drawer.figures.Circle;
import com.github.evabishchevich.figures.drawer.shape.DrawingShape;
import com.github.evabishchevich.figures.drawer.shape.EllipseShape;
import javafx.scene.paint.Color;

public class CircleDrawer implements FxDrawer {

    private Circle circle;
    private Color color;

    public CircleDrawer(int x, int y, Color color) {
        circle = new Circle(new Point(x, y), 0);
        this.color = color;
    }

    @Override
    public DrawingShape processCursorPosition(int x, int y) {
        int radius = Math.abs(circle.center.x - x);
        circle = new Circle(circle.center, radius);
        return new EllipseShape(circle, color);
    }

}
