package com.cu_bx.assignment.settings;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class SettingsController implements Initializable {
	private Stage settingsStage;

	@FXML
	private JFXTextField nDaysWithoutFine;

	@FXML
	private JFXTextField finePerDay;

	@FXML
	private JFXTextField username;

	@FXML
	private JFXPasswordField password;

	@FXML
	void handleCancel(ActionEvent event) {
		// settingsStage.close();
		((Stage) nDaysWithoutFine.getScene().getWindow()).close();
	}

	@FXML
	void handleSave(ActionEvent event) {
		int nDays = Integer.parseInt(nDaysWithoutFine.getText());
		float finePerDayFloat = Float.parseFloat(finePerDay.getText());
		String usernameString = username.getText();
		String passwordString = password.getText();

		Preferences preferences = Preferences.getPreferences();
		preferences.setnDaysWithoutFine(nDays);
		preferences.setFinePerDay(finePerDayFloat);
		preferences.setUsername(usernameString);
		preferences.setPassword(passwordString);
		Preferences.writePreferencesToFile(preferences);
	}

	public void setStage(Stage stage) {
		settingsStage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initDefaultValues();
	}

	private void initDefaultValues() {
		Preferences preferences = Preferences.getPreferences();
		nDaysWithoutFine.setText(preferences.getnDaysWithoutFine() + "");
		finePerDay.setText(preferences.getFinePerDay() + "");
		username.setText(preferences.getUsername());
		password.setText(preferences.getPassword());
	}

}
