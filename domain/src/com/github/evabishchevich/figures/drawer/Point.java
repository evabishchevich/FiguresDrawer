package com.github.evabishchevich.figures.drawer;


import java.io.Serializable;

public final class Point implements Serializable {

    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
