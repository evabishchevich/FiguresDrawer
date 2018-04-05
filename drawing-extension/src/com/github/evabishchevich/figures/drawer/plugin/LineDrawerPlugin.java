package com.github.evabishchevich.figures.drawer.plugin;

import com.github.evabishchevich.figures.drawer.DrawingPlugin;
import com.github.evabishchevich.figures.drawer.drawer.FxDrawer;

public class LineDrawerPlugin implements DrawingPlugin {

    @Override
    public String getName() {
        return "Line";
    }

    @Override
    public FxDrawer getDrawer(int x, int y) {
        return new LineDrawer(x, y);
    }
}
