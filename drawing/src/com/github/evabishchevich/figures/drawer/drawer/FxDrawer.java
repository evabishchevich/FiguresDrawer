package com.github.evabishchevich.figures.drawer.drawer;

import com.github.evabishchevich.figures.drawer.shape.DrawingShape;

import java.io.Serializable;

public interface FxDrawer extends Serializable {

    DrawingShape processCursorPosition(int x, int y);

    DrawingShape getShape();
}
