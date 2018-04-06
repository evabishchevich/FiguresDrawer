package com.github.evabishchevich.figures.drawer;

import com.github.evabishchevich.figures.drawer.figures.Figure;

import java.util.ArrayList;
import java.util.List;

public class FiguresContainer {

    private List<Figure> figures;

    public FiguresContainer(int size) {
        figures = new ArrayList<>(size);
    }

    public void add(Figure newFigure) {
        figures.add(newFigure);
    }

    public List<Figure> getFigures() {
        return figures;
    }
}
