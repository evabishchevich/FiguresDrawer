package com.github.evabishchevich.figures.drawer.extension;

import java.io.InputStream;
import java.io.OutputStream;

public interface LoadingPlugin {

    String getName();

    void load(InputStream from, OutputStream to);

    void save(InputStream from, OutputStream to);

}
