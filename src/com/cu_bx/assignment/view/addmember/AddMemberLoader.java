package com.cu_bx.assignment.view.addmember;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddMemberLoader extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent rootParent = FXMLLoader.load(getClass().getResource("AddMember.fxml"));
		Scene scene = new Scene(rootParent);
		primaryStage.setTitle("Add New Member");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
