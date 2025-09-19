package com.mipper;

import java.io.IOException;

import com.mipper.controller.MainWindow;
import com.mipper.model.mip.Mip;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Mip using FXML.
 */
public class Main extends Application {

    private Mip mip = new Mip();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Mip");
            fxmlLoader.<MainWindow>getController().setMip(mip);  // inject the Duke instance
            fxmlLoader.<MainWindow>getController().greetUser();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
