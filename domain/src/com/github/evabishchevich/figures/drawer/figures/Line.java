package com.github.evabishchevich.figures.drawer.figures;

import com.github.evabishchevich.figures.drawer.Point;

public class Line implements Figure {

    private final Point p1;
    private final Point p2;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public double getSlope() {
        if (p2.x == p1.x) {
            return (p1.y - p2.y >= 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY);
        }
        return (-(double) (p2.y - p1.y) / (p2.x - p1.x));
    }


    public boolean isParallel(Line line) {
        return Double.compare(this.getSlope(), line.getSlope()) == 0;
    }


}