package com.github.evabishchevich.figures.drawer.drawer;

import com.github.evabishchevich.figures.drawer.Point;
import com.github.evabishchevich.figures.drawer.figures.Circle;
import com.github.evabishchevich.figures.drawer.shape.DrawingShape;
import com.github.evabishchevich.figures.drawer.shape.EllipseShape;

public class CircleDrawer implements FxDrawer {

    private Circle circle;

    public CircleDrawer(int x, int y) {
        circle = new Circle(new Point(x, y), 0);
    }

    @Override
    public DrawingShape processCursorPosition(int x, int y) {
        int radius = Math.abs(circle.center.x - x);
        circle = new Circle(circle.center, radius);
        return getShape();
    }

    @Override
    public DrawingShape getShape() {
        return new EllipseShape(circle);
    }
}
