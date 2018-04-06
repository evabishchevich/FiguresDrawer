package com.github.evabishchevich.figures.drawer.extension;

import com.github.evabishchevich.figures.drawer.drawer.FxDrawer;

public interface DrawingPlugin {

    String getName();

    FxDrawer getDrawer(int x, int y);
}
