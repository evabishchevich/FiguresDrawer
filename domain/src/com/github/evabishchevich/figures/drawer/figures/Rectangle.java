package com.github.evabishchevich.figures.drawer.figures;

import com.github.evabishchevich.figures.drawer.Point;

public class Rectangle extends Parallelogram {

    public Rectangle(Point point1, Point point2, Point point3, Point point4) {
        super(point1, point2, point3, point4);
        checkIfRectangle();
    }

    private void checkIfRectangle() {
        if (point1.x != point4.x || point2.x != point3.x)
            throw new IllegalArgumentException("Given points are not rectangle");
    }

}
