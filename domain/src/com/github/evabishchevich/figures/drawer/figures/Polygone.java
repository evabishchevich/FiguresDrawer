package com.github.evabishchevich.figures.drawer.figures;

import com.github.evabishchevich.figures.drawer.Point;
import com.github.evabishchevich.figures.drawer.Visitable;

public abstract class Polygone implements Figure, Visitable {

    protected int distance(Point point, Point point2) {
        //(x2-x1)^2+(y2-y1)^2
        return (int) (Math.pow(point2.x - point.x, 2) + (Math.pow(point2.y - point.y, 2)));
    }

}
