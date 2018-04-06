package com.github.evabishchevich.figures.drawer.plugin;

import com.github.evabishchevich.figures.drawer.Point;
import com.github.evabishchevich.figures.drawer.drawer.FxDrawer;
import com.github.evabishchevich.figures.drawer.figures.Line;
import com.github.evabishchevich.figures.drawer.shape.DrawingShape;

class LineDrawer implements FxDrawer {

    private Line line;

    LineDrawer(int x, int y) {
        line = new Line(new Point(x, y), new Point(x, y));
    }

    @Override
    public DrawingShape processCursorPosition(int x, int y) {
        line = new Line(line.start, new Point(x, y));
        return getShape();
    }

    @Override
    public DrawingShape getShape() {
        return new LineShape(line);
    }
}
