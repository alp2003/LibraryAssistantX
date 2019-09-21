package com.cu_bx.assignment.settings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.codec.digest.DigestUtils;

import com.cu_bx.assignment.view.main.MainController;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import javafx.scene.control.Alert.AlertType;

public class Preferences {
	private static final String CONFIG_FILE = "config.txt";
	private int nDaysWithoutFine;
	private float finePerDay;
	private String username;
	private String password;

	public Preferences(int nDaysWithoutFine, float finePerDay, String username, String password) {

		this.nDaysWithoutFine = nDaysWithoutFine;
		this.finePerDay = finePerDay;
		this.username = username;
		this.password = password;
	}

	public Preferences() {
		nDaysWithoutFine = 14;
		finePerDay = 2;
		username = "admin";
		setPassword("admin");
	}

	public int getnDaysWithoutFine() {
		return nDaysWithoutFine;
	}

	public void setnDaysWithoutFine(int nDaysWithoutFine) {
		this.nDaysWithoutFine = nDaysWithoutFine;
	}

	public float getFinePerDay() {
		return finePerDay;
	}

	public void setFinePerDay(float finePerDay) {
		this.finePerDay = finePerDay;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password.length() < 16) {
			this.password = DigestUtils.shaHex(password);
		}

	}

	public static void writePreferencesToFile(Preferences preferences) {

		Gson gson = new Gson();
		Writer writer = null;
		try {
			writer = new FileWriter(CONFIG_FILE);
			gson.toJson(preferences, writer);
			MainController.createAlert(AlertType.INFORMATION, "Success", null, "Settings updated").showAndWait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			MainController.createAlert(AlertType.ERROR, "Failed", null, "Can't Save Configuration file").showAndWait();
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static Preferences getPreferences() {
		Gson gson = new Gson();
		Preferences preferences = new Preferences();
		try {
			preferences = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);

		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			initConfig();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return preferences;
	}

	public static void initConfig() {
		Preferences preferences = new Preferences();
		Gson gson = new Gson();
		Writer writer = null;
		try {
			writer = new FileWriter(CONFIG_FILE);
			gson.toJson(preferences, writer);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
