package com.github.evabishchevich.figures.drawer;

import com.github.evabishchevich.figures.drawer.figures.*;

public interface FigureVisitor {

    void draw(Figure figure);

    void draw(Circle circle);

    void draw(Ellipse ellipse);

    void draw(Line line);

    void draw(Parallelogram parallelogram);

    void draw(Rectangle rectangle);

    void draw(Square square);

}
