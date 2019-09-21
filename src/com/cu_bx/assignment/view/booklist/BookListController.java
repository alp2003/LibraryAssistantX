package com.cu_bx.assignment.view.booklist;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cu_bx.assignment.database.DatabaseHandler;
import com.cu_bx.assignment.model.Book;
import com.cu_bx.assignment.view.addbook.AddBookController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class BookListController implements Initializable {

	DatabaseHandler handler;
	ObservableList<Book> bookList = FXCollections.observableArrayList();

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TableView<Book> tableView;

	@FXML
	private TableColumn<Book, String> titleCol;

	@FXML
	private TableColumn<Book, String> idCol;

	@FXML
	private TableColumn<Book, String> authorCol;

	@FXML
	private TableColumn<Book, String> publisherCol;

	@FXML
	private TableColumn<Book, Boolean> availabilityCol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCol();

		loadData();
	}

	private void loadData() {
		handler = DatabaseHandler.getInstance();
		String qu = "SELECT * FROM BOOK";
		ResultSet resultSet = handler.execQuery(qu);
		try {
			while (resultSet.next()) {
				String title = resultSet.getString("title");
				String id = resultSet.getString("id");
				String author = resultSet.getString("author");
				String publisher = resultSet.getString("publisher");
				Boolean availability = resultSet.getBoolean("isAvail");
				bookList.add(new Book(title, id, author, publisher, availability));

			}
		} catch (SQLException e) {
			Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, e);
		}

		tableView.getItems().setAll(bookList);
	}

	private void initCol() {
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
		publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
		availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
	}

}
