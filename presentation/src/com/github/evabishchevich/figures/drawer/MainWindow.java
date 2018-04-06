package com.github.evabishchevich.figures.drawer;

import com.github.evabishchevich.figures.drawer.drawer.*;
import com.github.evabishchevich.figures.drawer.extension.DrawingPlugin;
import com.github.evabishchevich.figures.drawer.extension.LoadingPlugin;
import com.github.evabishchevich.figures.drawer.shape.DrawingShape;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class MainWindow extends Application {

    private static String ELLIPSE = "Ellipse";
    private static String CIRCLE = "Circle";
    private static String PARALLELOGRAM = "Parallelogram";
    private static String RECTANGLE = "Rectangle";
    private static String SQUARE = "Square";
    private static String TRIANGLE = "Triangle";
    private static int BUTTON_WIDTH = 100;

    private static String FIGURES_FILENAME = "figures.bin";
    private static String PLUGINS_DIR = "out/artifacts/";

    private DrawingPane drawingPane = new DrawingPane(Color.LIGHTCORAL);
    private VBox buttonsPanel = setUpButtonsPanel();
    private FxDrawer currentDrawer;
    private List<FxDrawer> drawers = new ArrayList<>();
    private List<LoadingPlugin> loadingPlugins = new ArrayList<>();
    private LoadingPlugin loadingPlugin;
    private ToggleGroup pluginsToggle = new ToggleGroup();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Collection<Button> buttons = setUpFiguresButtons();

        Button undoButton = new Button("Undo");
        undoButton.setMinWidth(BUTTON_WIDTH);
        undoButton.setOnMouseClicked(event -> drawingPane.removeLast());
        Button clearButton = new Button("Clear");
        clearButton.setMinWidth(BUTTON_WIDTH);
        clearButton.setOnMouseClicked(event -> drawingPane.clear());

        buttonsPanel.getChildren().addAll(undoButton, clearButton, new Separator());

        buttons.forEach(button -> {
            button.setMinWidth(BUTTON_WIDTH);
            buttonsPanel.getChildren().add(button);
        });
        setUpSaveAndLoadButtons(buttonsPanel);

        Button loadPlugin = new Button("Load plugins");
        loadPlugin.setOnMouseClicked(event -> loadPlugins());
        buttonsPanel.getChildren().addAll(new Separator(), loadPlugin, new Separator());

        SplitPane mainLayout = new SplitPane();
        mainLayout.setDividerPositions(Region.USE_COMPUTED_SIZE);
        mainLayout.getItems().addAll(drawingPane.getPane(), buttonsPanel);

        Scene scene = new Scene(mainLayout, 800, 800);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(600);
        primaryStage.setTitle("FiguresDrawer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Collection<Button> setUpFiguresButtons() {
        Map<String, Button> buttons = new HashMap<>();
        List<String> buttonNames = Arrays.asList(ELLIPSE, CIRCLE, PARALLELOGRAM, RECTANGLE, SQUARE, TRIANGLE);
        buttonNames.forEach(buttonName -> buttons.put(buttonName, new Button(buttonName)));
        buttons.get(ELLIPSE).setOnMouseClicked(event ->
                setMouseAdapter(event1 -> new EllipseDrawer((int) event1.getX(), (int) event1.getY()))
        );
        buttons.get(CIRCLE).setOnMouseClicked(event ->
                setMouseAdapter(event1 -> new CircleDrawer((int) event1.getX(), (int) event1.getY()))
        );
        buttons.get(PARALLELOGRAM).setOnMouseClicked(event ->
                setMouseAdapter(event1 -> new ParallelogramDrawer((int) event1.getX(), (int) event1.getY()))
        );
        buttons.get(RECTANGLE).setOnMouseClicked(event ->
                setMouseAdapter(event1 -> new RectangleDrawer((int) event1.getX(), (int) event1.getY()))
        );
        buttons.get(SQUARE).setOnMouseClicked(event ->
                setMouseAdapter(event1 -> new SquareDrawer((int) event1.getX(), (int) event1.getY()))
        );
        buttons.get(TRIANGLE).setOnMouseClicked(event ->
                setMouseAdapter(event1 -> new TriangleDrawer((int) event1.getX(), (int) event1.getY()))
        );
        return buttons.values();
    }

    private VBox setUpButtonsPanel() {
        VBox buttonsPanel = new VBox();
        buttonsPanel.setMaxWidth(BUTTON_WIDTH);
        buttonsPanel.setPadding(new Insets(20));
        buttonsPanel.setSpacing(10);
        return buttonsPanel;
    }

    private void setUpSaveAndLoadButtons(VBox buttonsPanel) {
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");
        saveButton.setOnMouseClicked(event -> saveToFile());
        loadButton.setOnMouseClicked(event -> loadFromFile(true));
        buttonsPanel.getChildren().addAll(new Separator(), saveButton, loadButton);
    }

    private void saveToFile() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(drawers);
            out.close();
            OutputStream fileOut = new FileOutputStream(FIGURES_FILENAME);
            ByteArrayInputStream bios = new ByteArrayInputStream(baos.toByteArray());
            if (loadingPlugin != null) {
                loadingPlugin.save(bios, fileOut);
            }
            baos.close();
            bios.close();
            fileOut.close();
            System.out.println("Figures saved to " + FIGURES_FILENAME);
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private void loadFromFile(boolean firstLoad) {
        InputStream fileIn = null;
        ObjectInputStream in = null;
        boolean reloadWithPlugins = false;
        try {
            fileIn = new FileInputStream(FIGURES_FILENAME);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (loadingPlugin != null) {
                loadingPlugin.load(fileIn, baos);
            }
            ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
            in = new ObjectInputStream(bis);
            drawers = (ArrayList<FxDrawer>) in.readObject();
            System.out.println("Figures read from " + FIGURES_FILENAME);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.toString());
            reloadWithPlugins = true;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fileIn != null) {
                    fileIn.close();
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.toString());
            }
        }
        if (reloadWithPlugins && firstLoad) {
            loadFromFileWithPlugins();
        }
        drawers.forEach(fxDrawer -> draw(fxDrawer.getShape()));
    }

    private void loadFromFileWithPlugins() {
        loadPlugins();
        loadFromFile(false);
    }

    private void loadPlugins() {
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
                            processDrawingPlugin((DrawingPlugin) c.getConstructor().newInstance());
                        }
                        if (className.contains(LoadingPlugin.class.getSimpleName())) {
                            processLoadingPlugin((LoadingPlugin) c.getConstructor().newInstance());
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

    private void processDrawingPlugin(DrawingPlugin plugin) {
        Button extraButton = new Button(plugin.getName());
        extraButton.setOnMouseClicked(event ->
                setMouseAdapter(event1 -> plugin.getDrawer((int) event1.getX(), (int) event1.getY()))
        );
        buttonsPanel.getChildren().add(extraButton);
    }

    private void processLoadingPlugin(LoadingPlugin plugin) {
        for (LoadingPlugin p : loadingPlugins) {
            if (p.getName().equals(plugin.getName())) {
                return;
            }
        }
        loadingPlugins.add(plugin);
        RadioButton button = new RadioButton(plugin.getName());
        button.setOnAction(value -> {
                    if (button.isSelected()) {
                        loadingPlugin = plugin;
                    }
                }
        );
        button.setToggleGroup(pluginsToggle);
        buttonsPanel.getChildren().add(button);
    }

    private void setMouseAdapter(Function2<MouseEvent, FxDrawer> drawerCreation) {
        drawingPane.getPane().setOnMousePressed(event1 -> {
            currentDrawer = drawerCreation.apply(event1);
            drawers.add(currentDrawer);
            new MouseEventAdapter(drawingPane.getPane(), event1);
        });
    }

    private void draw(DrawingShape shape) {
        drawingPane.add(shape.getShape());
    }

    private void redraw(DrawingShape shape) {
        drawingPane.removeLast();
        draw(shape);
    }

    private class MouseEventAdapter {

        MouseEventAdapter(Pane pane, MouseEvent event) {
            if (currentDrawer != null) {
                draw(currentDrawer.processCursorPosition((int) event.getX(), (int) event.getY()));
                pane.setOnMouseDragged(event1 -> {
                    DrawingShape shape = currentDrawer.processCursorPosition((int) event1.getX(), (int) event1.getY());
                    redraw(shape);
                });
            }
        }
    }

    @FunctionalInterface
    interface Function2<E, R> {

        R apply(E e);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
