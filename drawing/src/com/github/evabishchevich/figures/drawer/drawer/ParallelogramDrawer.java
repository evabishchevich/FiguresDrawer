package com.github.evabishchevich.figures.drawer.drawer;

import com.github.evabishchevich.figures.drawer.Point;
import com.github.evabishchevich.figures.drawer.figures.Parallelogram;
import com.github.evabishchevich.figures.drawer.shape.DrawingShape;
import com.github.evabishchevich.figures.drawer.shape.ParallelogramShape;

public class ParallelogramDrawer implements FxDrawer {

    private Parallelogram parallelogram;
    private boolean horizontalOffset;

    public ParallelogramDrawer(int x, int y) {
        Point topLeft = new Point(x, y);
        parallelogram = new Parallelogram(topLeft, topLeft, topLeft, topLeft);
        horizontalOffset = System.currentTimeMillis() % 2 == 0;
    }

    @Override
    public DrawingShape processCursorPosition(int x, int y) {
        int diffX = Math.abs(parallelogram.point1.x - x);
        int diffY = Math.abs(parallelogram.point1.y - y);
        if (horizontalOffset) {
            int addition = diffX / 3;
            Point point2 = new Point(parallelogram.point1.x + diffX + addition, parallelogram.point1.y);
            Point point3 = new Point(parallelogram.point1.x + diffX, parallelogram.point1.y + diffY);
            Point point4 = new Point(parallelogram.point1.x - addition, parallelogram.point1.y + diffY);
            parallelogram = new Parallelogram(parallelogram.point1, point2, point3, point4);
            return getShape();
        } else {
            int addition = diffY / 3;
            Point point2 = new Point(parallelogram.point1.x + diffX, parallelogram.point1.y + addition);
            Point point3 = new Point(parallelogram.point1.x + diffX, parallelogram.point1.y + diffY + addition);
            Point point4 = new Point(parallelogram.point1.x, parallelogram.point1.y + diffY);
            parallelogram = new Parallelogram(parallelogram.point1, point2, point3, point4);
            return getShape();
        }
    }

    @Override
    public DrawingShape getShape() {
        return new ParallelogramShape(parallelogram);
    }
}
