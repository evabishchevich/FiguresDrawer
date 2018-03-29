package com.github.evabishchevich.figures.drawer.figures;


import com.github.evabishchevich.figures.drawer.Point;
import com.github.evabishchevich.figures.drawer.FigureVisitor;

public class Parallelogram extends Polygone {

    public final Point point1;
    public final Point point2;
    public final Point point3;
    public final Point point4;

    public Parallelogram(Point point1, Point point2, Point point3, Point point4) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.point4 = point4;
        checkIfParallelogram();
    }

    private void checkIfParallelogram() {
        Line leftSide = new Line(point4, point1);
        Line rightSide = new Line(point3, point2);
        if (!leftSide.isParallel(rightSide)) {
            throw new IllegalArgumentException("Given points are not parallelogram");
        }
    }

    @Override
    public void accept(FigureVisitor visitor) {

    }
}
