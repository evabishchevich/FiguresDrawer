package com.github.evabishchevich.figures.drawer.figures;

import com.github.evabishchevich.figures.drawer.Point;

public class Circle extends Ellipse {

    public Circle(Point center, int radius) {
        super(center, radius, radius);
    }
}
