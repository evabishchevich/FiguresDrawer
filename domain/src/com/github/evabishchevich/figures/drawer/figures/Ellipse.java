package com.github.evabishchevich.figures.drawer.figures;

import com.github.evabishchevich.figures.drawer.Point;
import com.github.evabishchevich.figures.drawer.Visitable;
import com.github.evabishchevich.figures.drawer.FigureVisitor;

public class Ellipse implements Figure, Visitable {

    public final Point center;
    public final int horizontalRadius;
    public final int verticalRadius;

    public Ellipse(Point center, int horizontalRadius, int verticalRadius) {
        checkIsEllipse(horizontalRadius, verticalRadius);
        this.center = center;
        this.horizontalRadius = horizontalRadius;
        this.verticalRadius = verticalRadius;
    }

    private void checkIsEllipse(int radius1, int radius2) {
        if (radius1 < 0 || radius2 < 0) {
            throw new IllegalArgumentException("Radiuses can't be null");
        }
    }

    @Override
    public void accept(FigureVisitor visitor) {

    }

}
