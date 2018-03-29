package com.github.evabishchevich.figures.drawer.drawer;

import com.github.evabishchevich.figures.drawer.shape.DrawingShape;

public interface FxDrawer {

    DrawingShape processCursorPosition(int x, int y);
}
