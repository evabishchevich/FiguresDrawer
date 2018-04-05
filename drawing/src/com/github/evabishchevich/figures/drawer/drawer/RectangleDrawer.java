package com.github.evabishchevich.figures.drawer.drawer;

import com.github.evabishchevich.figures.drawer.Point;
import com.github.evabishchevich.figures.drawer.figures.Rectangle;
import com.github.evabishchevich.figures.drawer.shape.DrawingShape;
import com.github.evabishchevich.figures.drawer.shape.ParallelogramShape;

public class RectangleDrawer implements FxDrawer {

    private Rectangle rectangle;

    public RectangleDrawer(int x, int y) {
        Point topLeft = new Point(x, y);
        rectangle = new Rectangle(topLeft, topLeft, topLeft, topLeft);
    }

    @Override
    public DrawingShape processCursorPosition(int x, int y) {
        int diffX = Math.abs(rectangle.point1.x - x);
        int diffY = Math.abs(rectangle.point1.y - y);
        Point point2 = new Point(rectangle.point1.x + diffX, rectangle.point1.y);
        Point point3 = new Point(rectangle.point1.x + diffX, rectangle.point1.y + diffY);
        Point point4 = new Point(rectangle.point1.x, rectangle.point1.y + diffY);
        rectangle = new Rectangle(rectangle.point1, point2, point3, point4);
        return getShape();
    }

    @Override
    public DrawingShape getShape() {
        return new ParallelogramShape(rectangle);
    }
}
