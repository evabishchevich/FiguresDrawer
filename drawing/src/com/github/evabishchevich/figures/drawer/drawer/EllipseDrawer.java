package com.github.evabishchevich.figures.drawer.drawer;


import com.github.evabishchevich.figures.drawer.Point;
import com.github.evabishchevich.figures.drawer.figures.Circle;
import com.github.evabishchevich.figures.drawer.figures.Ellipse;
import com.github.evabishchevich.figures.drawer.shape.DrawingShape;
import com.github.evabishchevich.figures.drawer.shape.EllipseShape;
import javafx.scene.paint.Color;

public class EllipseDrawer implements FxDrawer {

    private Ellipse ellipse;
    private Color color;

    public EllipseDrawer(int x, int y, Color color) {
        ellipse = new Circle(new Point(x, y), 0);
        this.color = color;
    }

    @Override
    public DrawingShape processCursorPosition(int x, int y) {
        int horizontalRadius = Math.abs(ellipse.center.x - x);
        int verticalRadius = Math.abs(ellipse.center.y - y);
        ellipse = new Ellipse(ellipse.center, horizontalRadius, verticalRadius);
        return new EllipseShape(ellipse, color);
    }
}
