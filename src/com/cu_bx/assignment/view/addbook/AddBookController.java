package com.cu_bx.assignment.view.addbook;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cu_bx.assignment.database.DatabaseHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddBookController implements Initializable {

	@FXML
	private JFXTextField bookTitle;

	@FXML
	private JFXTextField bookId;

	@FXML
	private JFXTextField bookAuthor;

	@FXML
	private JFXTextField bookPublisher;

	@FXML
	private JFXButton saveButton;

	@FXML
	private JFXButton cancelButton;

	@FXML
	private AnchorPane rootPane;

	DatabaseHandler databaseHandler;

	@FXML
	void onCancel(ActionEvent event) {
		Stage stage = (Stage) rootPane.getScene().getWindow();
		stage.close();
	}

	@FXML
	void onSaveBook(ActionEvent event) {
		String id = bookId.getText();
		String title = bookTitle.getText();
		String author = bookAuthor.getText();
		String publisher = bookPublisher.getText();

		if (id.isEmpty() || title.isEmpty() || author.isEmpty() || publisher.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please fill all fields");
			alert.showAndWait();
			return;
		}

		String qu = "INSERT INTO BOOK VALUES(" + "'" + id + "'," + "'" + title + "'," + "'" + author + "'," + "'"
				+ publisher + "'," + "true" + ")";
		System.out.println(qu);
		if (databaseHandler.execAction(qu)) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Success");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Failed");
			alert.showAndWait();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		databaseHandler = DatabaseHandler.getInstance();
		checkData();
	}

	private void checkData() {
		String qu = "SELECT title FROM BOOK";
		ResultSet resultSet = databaseHandler.execQuery(qu);
		try {
			while (resultSet.next()) {
				String title = resultSet.getString("title");
				System.out.println(title);
			}
		} catch (SQLException e) {
			Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, e);
		}
	}

}
