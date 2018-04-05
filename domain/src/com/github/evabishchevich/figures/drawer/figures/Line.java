package com.github.evabishchevich.figures.drawer.figures;

import com.github.evabishchevich.figures.drawer.Point;

public class Line implements Figure {

    public final Point start;
    public final Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public double getSlope() {
        if (end.x == start.x) {
            return (start.y - end.y >= 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY);
        }
        return (-(double) (end.y - start.y) / (end.x - start.x));
    }


    public boolean isParallel(Line line) {
        return Double.compare(this.getSlope(), line.getSlope()) == 0;
    }

}