package com.github.evabishchevich.figures.drawer.drawer;

import com.github.evabishchevich.figures.drawer.Point;
import com.github.evabishchevich.figures.drawer.figures.Square;
import com.github.evabishchevich.figures.drawer.shape.DrawingShape;
import com.github.evabishchevich.figures.drawer.shape.ParallelogramShape;
import javafx.scene.paint.Color;

public class SquareDrawer implements FxDrawer {

    private Square square;
    private Color color;

    public SquareDrawer(int x, int y, Color color) {
        Point topLeft = new Point(x, y);
        square = new Square(topLeft, topLeft, topLeft, topLeft);
        this.color = color;
    }

    @Override
    public DrawingShape processCursorPosition(int x, int y) {
        int diffX = Math.abs(square.point1.x - x);
//        int addition = diffX / 5;
        Point point2 = new Point(square.point1.x + diffX, square.point1.y);
        Point point3 = new Point(square.point1.x + diffX, square.point1.y + diffX);
        Point point4 = new Point(square.point1.x, square.point1.y + diffX);
        square = new Square(square.point1, point2, point3, point4);
        return new ParallelogramShape(square, color);
    }
}
