package com.github.evabishchevich.figures.drawer;

import com.github.evabishchevich.figures.drawer.extension.DrawingPlugin;
import com.github.evabishchevich.figures.drawer.extension.LoadingPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginsLoader {

    private static final String PLUGINS_DIR = "out/artifacts/";
    private Function<DrawingPlugin, Void> processDrawingPlugin;
    private Function<LoadingPlugin, Void> processLoadingPlugin;

    public PluginsLoader(Function<DrawingPlugin, Void> processDrawingPlugin,
                         Function<LoadingPlugin, Void> processLoadingPlugin) {
        this.processDrawingPlugin = processDrawingPlugin;
        this.processLoadingPlugin = processLoadingPlugin;
    }

    public void loadPlugins() {
        File pluginsFolder = new File(PLUGINS_DIR);
        File[] jars = pluginsFolder.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
        if (jars != null) {
            for (File file : jars) {
                try {
                    JarFile jarFile = new JarFile(file.getAbsolutePath());
                    Enumeration<JarEntry> e = jarFile.entries();
                    URL[] urls = {new URL("jar:file:" + file.getAbsolutePath() + "!/")};
                    URLClassLoader cl = URLClassLoader.newInstance(urls);
                    while (e.hasMoreElements()) {
                        JarEntry je = e.nextElement();
                        if (je.isDirectory() || !je.getName().endsWith(".class")) {
                            continue;
                        }
                        // -6 because of .class
                        String className = je.getName().substring(0, je.getName().length() - 6);
                        className = className.replace('/', '.');
                        Class c = cl.loadClass(className);
                        if (className.contains(DrawingPlugin.class.getSimpleName())) {
                            processDrawingPlugin.apply((DrawingPlugin) c.getConstructor().newInstance());
                        }
                        if (className.contains(LoadingPlugin.class.getSimpleName())) {
                            processLoadingPlugin.apply((LoadingPlugin) c.getConstructor().newInstance());
                        }
                    }
                    Thread.currentThread().setContextClassLoader(cl);
                } catch (IOException
                        | ClassNotFoundException
                        | IllegalAccessException
                        | InstantiationException
                        | NoSuchMethodException
                        | InvocationTargetException
                        e1) {
                    System.out.println("Error: " + e1.toString());
                }
            }
        }
    }
}
