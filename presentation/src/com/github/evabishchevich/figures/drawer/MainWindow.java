package com.github.evabishchevich.figures.drawer;

import com.github.evabishchevich.figures.drawer.drawer.*;
import com.github.evabishchevich.figures.drawer.shape.DrawingShape;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;


public class MainWindow extends Application {

    private static String ELLIPSE = "Ellipse";
    private static String CIRCLE = "Circle";
    private static String PARALLELOGRAM = "Parallelogram";
    private static String RECTANGLE = "Rectangle";
    private static String SQUARE = "Square";
    private static String TRIANGLE = "Triangle";
    private static int BUTTON_WIDTH = 100;

    private DrawingPane drawingPane = new DrawingPane(BUTTON_WIDTH);
    private FxDrawer currentDrawer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Collection<Button> buttons = setUpFiguresButtons();

        Button undoButton = new Button("Undo");
        undoButton.setMinWidth(BUTTON_WIDTH);
        undoButton.setOnMouseClicked(event -> drawingPane.removeLast());
        Button clearButton = new Button("Clear");
        clearButton.setMinWidth(BUTTON_WIDTH);
        clearButton.setOnMouseClicked(event -> drawingPane.clear());

        VBox buttonsPanel = setUpButtonsPanel();
        buttonsPanel.getChildren().addAll(undoButton, clearButton, new Separator());

        buttons.forEach(button -> {
            button.setMinWidth(BUTTON_WIDTH);
            buttonsPanel.getChildren().add(button);
        });

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
        Color color = Color.AQUA;
        Map<String, Button> buttons = new HashMap<>();
        List<String> buttonNames = Arrays.asList(ELLIPSE, CIRCLE, PARALLELOGRAM, RECTANGLE, SQUARE, TRIANGLE);
        buttonNames.forEach(buttonName -> buttons.put(buttonName, new Button(buttonName)));
        buttons.get(ELLIPSE).setOnMouseClicked(event ->
                drawingPane.getPane().setOnMousePressed(event1 -> {
                    currentDrawer = new EllipseDrawer((int) event1.getX(), (int) event1.getY(), color);
                    new MouseEventAdapter(drawingPane.getPane(), event1);
                })
        );
        buttons.get(CIRCLE).setOnMouseClicked(event -> {
            drawingPane.getPane().setOnMousePressed(event1 -> {
                currentDrawer = new CircleDrawer((int) event1.getX(), (int) event1.getY(), color);
                new MouseEventAdapter(drawingPane.getPane(), event1);
            });
        });
        buttons.get(PARALLELOGRAM).setOnMouseClicked(event -> {
            drawingPane.getPane().setOnMousePressed(event1 -> {
                currentDrawer = new ParallelogramDrawer((int) event1.getX(), (int) event1.getY(), color);
                new MouseEventAdapter(drawingPane.getPane(), event1);
            });
        });
        buttons.get(RECTANGLE).setOnMouseClicked(event -> {
            drawingPane.getPane().setOnMousePressed(event1 -> {
                currentDrawer = new RectangleDrawer((int) event1.getX(), (int) event1.getY(), color);
                new MouseEventAdapter(drawingPane.getPane(), event1);
            });
        });
        buttons.get(SQUARE).setOnMouseClicked(event -> {
            drawingPane.getPane().setOnMousePressed(event1 -> {
                currentDrawer = new SquareDrawer((int) event1.getX(), (int) event1.getY(), color);
                new MouseEventAdapter(drawingPane.getPane(), event1);
            });
        });
        buttons.get(TRIANGLE).setOnMouseClicked(event -> {
            drawingPane.getPane().setOnMousePressed(event1 -> {
                currentDrawer = new TriangleDrawer((int) event1.getX(), (int) event1.getY(), color);
                new MouseEventAdapter(drawingPane.getPane(), event1);
            });
        });
        return buttons.values();
    }

    private VBox setUpButtonsPanel() {
        VBox buttonsPanel = new VBox();
        buttonsPanel.setMaxWidth(BUTTON_WIDTH);
        buttonsPanel.setPadding(new Insets(20));
        buttonsPanel.setSpacing(10);
        return buttonsPanel;
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

    public static void main(String[] args) {
        launch(args);
    }
}
