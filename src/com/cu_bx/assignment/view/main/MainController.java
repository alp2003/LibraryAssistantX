package com.cu_bx.assignment.view.main;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cu_bx.assignment.database.DatabaseHandler;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainController implements Initializable {

	private DatabaseHandler databaseHandler;
	private Stage mStage;
	@FXML
	private HBox book_info;

	@FXML
	private HBox member_info;

	@FXML
	private TextField bookIDInput;

	@FXML
	private Text bookName;

	@FXML
	private Text bookAuthor;

	@FXML
	private Text bookStatus;

	@FXML
	private TextField memberIDInput;

	@FXML
	private Text memberName;

	@FXML
	private Text memberPhone;

	@FXML
	private JFXTextField bookID;

	@FXML
	private ListView<String> issueDataList;

	private Boolean isReadyForSubmission = false;

	@FXML
	void onLoadBookInfo(ActionEvent event) {
		clearBookCache();
		String id = bookIDInput.getText();
		String qu = "SELECT * FROM BOOK WHERE id = " + "'" + id + "'";
		Boolean flag = false;
		try {
			ResultSet resultSet = databaseHandler.execQuery(qu);
			while (resultSet.next()) {
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				Boolean isAvail = resultSet.getBoolean("isAvail");
				flag = true;
				bookName.setText(title);
				bookAuthor.setText(author);
				bookStatus.setText(isAvail ? "Availiable" : "Unavailiable");
			}

			if (!flag) {
				bookName.setText("No Such Book");

			}
		} catch (Exception e) {

		}

	}

	@FXML
	void onLoadMemberInfo(ActionEvent event) {
		clearMemberCache();
		String id = memberIDInput.getText();
		String qu = "SELECT * FROM MEMBER WHERE id = " + "'" + id + "'";
		Boolean flag = false;
		try {
			ResultSet resultSet = databaseHandler.execQuery(qu);
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				String mobile = resultSet.getString("mobile");
				memberName.setText(name);
				memberPhone.setText(mobile);
				flag = true;
			}

			if (!flag) {
				memberName.setText("No Such Member");
			}
		} catch (Exception e) {

		}

	}

	private void clearBookCache() {
		bookName.setText("");
		bookAuthor.setText("");
		bookStatus.setText("");
	}

	private void clearMemberCache() {
		memberName.setText("");
		memberPhone.setText("");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		JFXDepthManager.setDepth(book_info, 1);
		JFXDepthManager.setDepth(member_info, 1);
		databaseHandler = DatabaseHandler.getInstance();

	}

	@FXML
	void OnLoadSettings(ActionEvent event) {

	}

	@FXML
	void onLoadAddBook(ActionEvent event) {
		loadWindow("/com/cu_bx/assignment/view/addbook/AddBook.fxml", "Add New Book");
	}

	@FXML
	void onLoadAddMember(ActionEvent event) {
		loadWindow("/com/cu_bx/assignment/view/addmember/AddMember.fxml", "Add New Member");
	}

	@FXML
	void onLoadBookList(ActionEvent event) {
		loadWindow("/com/cu_bx/assignment/view/booklist/BookList.fxml", "Book List");
	}

	@FXML
	void onLoadMemberList(ActionEvent event) {
		loadWindow("/com/cu_bx/assignment/view/memberlist/MemberList.fxml", "Member List");
	}

	@FXML
	void loadSettings(ActionEvent event) {
		loadWindow("/com/cu_bx/assignment/settings/Settings.fxml", "Settings");
	}

	@FXML
	void onLoadIssue(ActionEvent event) {
		String memberID = memberIDInput.getText();
		String bookID = bookIDInput.getText();

		Alert alert = MainController.createAlert(AlertType.CONFIRMATION, "Confirmation Issue Operation", null,
				"Are you sure want to issue the book " + bookName.getText() + "\nto " + memberName.getText() + " ?");
		Optional<ButtonType> response = alert.showAndWait();

		if (response.get() == ButtonType.OK) {
			String str = "INSERT INTO ISSUE(memberID, bookID) VALUES(" + "'" + memberID + "'," + "'" + bookID + "'"
					+ ")";
			String str2 = "UPDATE BOOK SET isAvail = false WHERE id = '" + bookID + "'";
			if (databaseHandler.execAction(str) && databaseHandler.execAction(str2)) {
				MainController.createAlert(AlertType.INFORMATION, "Success", null, "Book Issue Complete").showAndWait();
			} else {
				MainController.createAlert(AlertType.ERROR, "Fail", null, "Book Issue Fail").showAndWait();
			}
		} else {
			MainController.createAlert(AlertType.ERROR, "Cancelled", null, "Issue Operation Failed").showAndWait();
		}

	}

	private void loadWindow(String location, String title) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource(location));
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(title);
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (IOException e) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	@FXML
	void onLoadBookInfo2(ActionEvent event) {
		ObservableList<String> issueData = FXCollections.observableArrayList();
		isReadyForSubmission = false;

		String bookid = bookID.getText();
		String que = "SELECT * FROM ISSUE WHERE bookID = '" + bookid + "'";
		ResultSet rs = databaseHandler.execQuery(que);
		try {
			while (rs.next()) {
				String bookIDStr = bookid;
				String memberIDStr = rs.getString("memberID");
				Timestamp issueTimestamp = rs.getTimestamp("issueTime");
				int renewCount = rs.getInt("renew_count");

				issueData.add("Issue Data and Time :" + issueTimestamp.toGMTString());
				issueData.add("Renew Count: " + renewCount);
				issueData.add("Book Info:-");
				String queBook = "SELECT * FROM BOOK WHERE ID = '" + bookIDStr + "'";
				ResultSet rBook = databaseHandler.execQuery(queBook);
				while (rBook.next()) {
					issueData.add("\tBook Name : " + rBook.getString("title"));
					issueData.add("\tBook ID : " + rBook.getString("id"));
					issueData.add("\tBook Author : " + rBook.getString("author"));
					issueData.add("\tBook Publisher : " + rBook.getString("publisher"));
				}
				issueData.add("Member Info:-");
				String queMember = "SELECT * FROM MEMBER WHERE ID = '" + memberIDStr + "'";
				ResultSet rMember = databaseHandler.execQuery(queMember);
				while (rMember.next()) {
					issueData.add("\tMember Name: " + rMember.getString("name"));
					issueData.add("\tMember ID: " + rMember.getString("id"));
					issueData.add("\tMember Mobile: " + rMember.getString("mobile"));
					issueData.add("\tMember Email: " + rMember.getString("email"));
				}

				isReadyForSubmission = true;

			}
		} catch (SQLException e) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
		}

		issueDataList.getItems().setAll(issueData);

	}

	@FXML
	void loadSubmission(ActionEvent event) {
		if (!isReadyForSubmission) {
			Alert alert = MainController.createAlert(AlertType.ERROR, "Failed", null,
					"Please select book to submission");

			alert.setX(mStage.getX() + mStage.getWidth() / 2 - 225);
			alert.setY(mStage.getY());
			alert.showAndWait();
			// Commet
			return;
		}

		String bookid = bookID.getText();
		String que = "DELETE FROM ISSUE WHERE bookID = '" + bookid + "'";
		String que2 = "UPDATE BOOK SET IsAvail = true WHERE id = '" + bookid + "'";

		if (databaseHandler.execAction(que) && databaseHandler.execAction(que2)) {
			Alert alert = createAlert(AlertType.INFORMATION, "Success", null, "The Book Has Been Submitted");
			Point2D windowCenter = new Point2D(mStage.getX() + mStage.getWidth() / 2,
					mStage.getY() + mStage.getHeight() / 2);
			Screen currntScreen = Screen.getScreens().stream()
					.filter(screen -> screen.getBounds().contains(windowCenter)).findAny().get();
			Rectangle2D rectangle2d = currntScreen.getBounds();
			double x = mStage.getX() + (mStage.getWidth() / 2) - (225);
			double y = mStage.getY() + (mStage.getHeight() / 2) - 50;
			alert.setX(x);
			alert.setY(y);
			alert.showAndWait();
		} else {
			MainController.createAlert(AlertType.ERROR, "Failed", null, "The Book Has Been Failed").showAndWait();

		}
	}

	@FXML
	void loadRenew(ActionEvent event) {
		if (!isReadyForSubmission) {
			Alert alert = MainController.createAlert(AlertType.ERROR, "Failed", null, "Please select book to renew");
			alert.setX(mStage.getX() + mStage.getWidth() / 2 - 225);
			alert.setY(mStage.getY());
			alert.showAndWait();
			return;
		}

		Alert alert = MainController.createAlert(AlertType.CONFIRMATION, "Confirmation Renew Operation", null,
				"Are you sure want to renew the book? ");
		Optional<ButtonType> response = alert.showAndWait();
		if (response.get() == ButtonType.OK) {
			String bookid = bookID.getText();
			String que = "UPDATE ISSUE SET  issueTime = CURRENT_TIMESTAMP, renew_count = renew_count+1 WHERE bookID = '"
					+ bookid + "'";
			if (databaseHandler.execAction(que)) {
				createAlert(AlertType.INFORMATION, "Success", null, "The Book Has Been Renewed").showAndWait();
			} else {
				createAlert(AlertType.ERROR, "Failed", null, "The Renew has been failed").showAndWait();
			}
		} else {
			createAlert(AlertType.ERROR, "Cancelled", null, "The Renew has been cancelled").showAndWait();
		}
	}

	public void setStage(Stage stage) {
		mStage = stage;
	}

	public static Alert createAlert(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}

}
