package com.github.evabishchevich.figures.drawer;

public interface Visitable {

    void accept(FigureVisitor visitor);
}
