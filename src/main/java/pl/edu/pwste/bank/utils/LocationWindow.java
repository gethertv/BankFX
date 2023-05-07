package pl.edu.pwste.bank.utils;

import javafx.scene.Parent;
import javafx.stage.Stage;

public class LocationWindow {

    public static double x, y;

    public static void addCursorMove(Parent parent, Stage stage)
    {
        LocationWindow.x = stage.getX();
        LocationWindow.y = stage.getY();
        parent.setOnMousePressed( mouseEvent -> {
            LocationWindow.x = mouseEvent.getX();
            LocationWindow.y = mouseEvent.getY();
        });
        parent.setOnMouseDragged( mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - LocationWindow.x);
            stage.setY(mouseEvent.getScreenY() - LocationWindow.y);
        });

    }

}
