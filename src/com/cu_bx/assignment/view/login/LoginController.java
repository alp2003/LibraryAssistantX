package com.cu_bx.assignment.view.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.digest.DigestUtils;

import com.cu_bx.assignment.settings.Preferences;
import com.cu_bx.assignment.view.main.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML
	private JFXTextField username;

	@FXML
	private JFXPasswordField password;

	@FXML
	private Label titleLabel;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private MenuItem itemM;

	Preferences preferences;

	@FXML
	void handleCancel(ActionEvent event) {
		((Stage) username.getScene().getWindow()).close();

	}

	@FXML
	void handleLogin(ActionEvent event) {
		titleLabel.setText("Library Assistent Login");
		titleLabel.setStyle("-fx-background-color: black; -fx-text-fill: white");
		String unameString = username.getText();
		String passString = DigestUtils.shaHex(password.getText());

		if (unameString.equals(preferences.getUsername()) && passString.equals(preferences.getPassword())) {
			closeStage();
			loadMain("/com/cu_bx/assignment/view/main/Main.fxml", "Library Assistent X");
		} else {
			titleLabel.setText("Invalid Credentails");
			titleLabel.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white");
		}

	}

	private void closeStage() {
		((Stage) username.getScene().getWindow()).close();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		preferences = Preferences.getPreferences();
	}

	private void loadMain(String location, String title) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(location));
			Parent parent = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle(title);
			stage.setScene(new Scene(parent));
			((MainController) fxmlLoader.getController()).setStage(stage);
			stage.show();
		} catch (IOException e) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
		}
	}

}
