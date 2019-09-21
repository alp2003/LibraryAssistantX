package com.cu_bx.assignment.view.addmember;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cu_bx.assignment.database.DatabaseHandler;
import com.cu_bx.assignment.view.addbook.AddBookController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddMemberController implements Initializable {

	private DatabaseHandler databaseHandler;
	@FXML
	private AnchorPane rootPane;

	@FXML
	private JFXTextField name;

	@FXML
	private JFXTextField id;

	@FXML
	private JFXTextField mobile;

	@FXML
	private JFXTextField email;

	@FXML
	private JFXButton saveButton;

	@FXML
	private JFXButton cancelButton;

	@FXML
	void onCancel(ActionEvent event) {
		Stage stage = (Stage) rootPane.getScene().getWindow();
		stage.close();
	}

	@FXML
	void onSaveMember(ActionEvent event) {
		String mNameString = name.getText();
		String mIdString = id.getText();
		String mMobileString = mobile.getText();
		String mEmailString = email.getText();

		Boolean flag = mNameString.isEmpty() || mIdString.isEmpty() || mMobileString.isEmpty()
				|| mEmailString.isEmpty();
		if (flag) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please fill all fields");
			alert.showAndWait();
			return;
		}

		String qu = "INSERT INTO MEMBER VALUES(" + "'" + mIdString + "'," + "'" + mNameString + "'," + "'"
				+ mMobileString + "'," + "'" + mEmailString + "'" + ")";
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
		String qu = "SELECT name FROM MEMBER";
		ResultSet resultSet = databaseHandler.execQuery(qu);
		try {
			while (resultSet.next()) {
				String title = resultSet.getString("name");
				System.out.println(title);
			}
		} catch (SQLException e) {
			Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, e);
		}
	}

}
