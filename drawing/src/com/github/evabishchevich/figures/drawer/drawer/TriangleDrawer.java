package com.github.evabishchevich.figures.drawer.drawer;

import com.github.evabishchevich.figures.drawer.Point;
import com.github.evabishchevich.figures.drawer.figures.Triangle;
import com.github.evabishchevich.figures.drawer.shape.DrawingShape;
import com.github.evabishchevich.figures.drawer.shape.TriangleShape;

public class TriangleDrawer implements FxDrawer {

    private Triangle triangle;
    private boolean drawDown;

    public TriangleDrawer(int x, int y) {
        Point topLeft = new Point(x, y);
        triangle = new Triangle(topLeft, topLeft, topLeft);
        drawDown = System.currentTimeMillis() % 2 == 0;
    }

    @Override
    public DrawingShape processCursorPosition(int x, int y) {
        int diffX = Math.abs(triangle.a.x - x);
        int diffY = Math.abs(triangle.a.y - y);
        Point b;
        Point c;
        if (drawDown) {
            b = new Point(triangle.a.x + diffX, triangle.a.y);
            c = new Point(triangle.a.x, triangle.a.y + diffY);
        } else {
            b = new Point(triangle.a.x, triangle.a.y + diffY);
            c = new Point(triangle.a.x + diffX, triangle.a.y + diffY);
        }
        triangle = new Triangle(triangle.a, b, c);
        return getShape();
    }

    @Override
    public DrawingShape getShape() {
        return new TriangleShape(triangle);
    }
}
