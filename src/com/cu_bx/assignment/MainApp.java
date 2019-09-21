package com.cu_bx.assignment;

import com.cu_bx.assignment.database.DatabaseHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/login/Login.fxml"));
		Parent rootParent = fxmlLoader.load();
		Scene scene = new Scene(rootParent);
		primaryStage.setTitle("Library Assistant Login");
		primaryStage.setScene(scene);
		primaryStage.show();

		new Thread(() -> DatabaseHandler.getInstance()).start();

	}

}
