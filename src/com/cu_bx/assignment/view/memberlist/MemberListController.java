package com.cu_bx.assignment.view.memberlist;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cu_bx.assignment.database.DatabaseHandler;
import com.cu_bx.assignment.model.Member;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class MemberListController implements Initializable {

	DatabaseHandler databaseHandler;
	ObservableList<Member> memberList = FXCollections.observableArrayList();

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TableView<Member> tableView;

	@FXML
	private TableColumn<Member, String> idCol;

	@FXML
	private TableColumn<Member, String> nameCol;

	@FXML
	private TableColumn<Member, String> mobileCol;

	@FXML
	private TableColumn<Member, String> emailCol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCols();
		loadData();

	}

	private void loadData() {
		databaseHandler = DatabaseHandler.getInstance();
		String qu = "SELECT * FROM MEMBER";
		ResultSet resultSet = databaseHandler.execQuery(qu);
		try {
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String name = resultSet.getString("name");
				String mobile = resultSet.getString("mobile");
				String email = resultSet.getString("email");
				memberList.add(new Member(id, name, mobile, email));
				System.out.println(id);
			}
		} catch (SQLException e) {
			Logger.getLogger(MemberListController.class.getName()).log(Level.SEVERE, null, e);
		}

		tableView.getItems().setAll(memberList);

	}

	private void initCols() {
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
	}

}
