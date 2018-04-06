package com.github.evabishchevich.figures.drawer.figures;


import com.github.evabishchevich.figures.drawer.Point;
import com.github.evabishchevich.figures.drawer.FigureVisitor;

public class Triangle extends Polygone {

    public final Point a;
    public final Point b;
    public final Point c;

    public Triangle(Point a, Point b, Point c) {
        checkIsTriangle(a, b, c);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    private void checkIsTriangle(Point a, Point b, Point c) {
        if (distance(a, b) > distance(b, c) + distance(c, a)
                || distance(b, c) > distance(a, b) + distance(c, a)
                || distance(a, c) > distance(a, b) + distance(b, c)) {
            throw new IllegalArgumentException("Given points are not triangle");
        }
    }

    @Override
    public void accept(FigureVisitor visitor) {

    }
}
