package com.cu_bx.assignment.view.booklist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BookListLoader extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent rootParent = FXMLLoader.load(getClass().getResource("BookList.fxml"));
		Scene scene = new Scene(rootParent);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Book List");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
