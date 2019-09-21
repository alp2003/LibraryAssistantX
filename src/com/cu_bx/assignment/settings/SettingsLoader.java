package com.cu_bx.assignment.settings;

import com.cu_bx.assignment.database.DatabaseHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SettingsLoader extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
		Parent rootParent = fxmlLoader.load();
		Scene scene = new Scene(rootParent);
		primaryStage.setTitle("Settings");
		primaryStage.setScene(scene);
		((SettingsController) fxmlLoader.getController()).setStage(primaryStage);
		primaryStage.show();

		new Thread(() -> DatabaseHandler.getInstance()).start();
	}

}
