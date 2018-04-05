package com.github.evabishchevich.figures.drawer.plugin;

import com.github.evabishchevich.figures.drawer.figures.Line;
import com.github.evabishchevich.figures.drawer.shape.DrawingShape;
import javafx.scene.shape.Shape;

public class LineShape implements DrawingShape {

    private Line line;

    public LineShape(Line line) {
        this.line = line;
    }

    @Override
    public Shape getShape() {
        return new javafx.scene.shape.Line(line.start.x, line.start.y, line.end.x, line.end.y);
    }
}
