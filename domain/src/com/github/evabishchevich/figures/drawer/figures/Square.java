package com.github.evabishchevich.figures.drawer.figures;

import com.github.evabishchevich.figures.drawer.Point;


public class Square extends Rectangle {

    public Square(Point point1, Point point2, Point point3, Point point4) {
        super(point1, point2, point3, point4);
        checkIfSquare();
    }

    private void checkIfSquare() {
        if ((point2.x - point1.x) != (point3.y - point2.y) ||
                (point3.y - point2.y) != (point3.x - point4.x) ||
                (point3.x - point4.x) != (point4.y - point1.y)
                ) {
            throw new IllegalArgumentException("Given points are not square");
        }
    }

}
