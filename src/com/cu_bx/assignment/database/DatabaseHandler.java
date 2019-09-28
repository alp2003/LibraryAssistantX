package com.cu_bx.assignment.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
 * @author alex_
 *
 */
public final class DatabaseHandler {
	private static DatabaseHandler handler = null;

	private static final String DB_URL = "jdbc:derby:database/library2;create=true";
	private static Connection connection = null;
	private static Statement statement = null;

	private DatabaseHandler() {
		createConnection();
		setupBookTable();
		setupMemberTable();
		setupIssueTable();
	}

	public static DatabaseHandler getInstance() {
		if (handler == null) {
			handler = new DatabaseHandler();
		}

		return handler;
	}

	private void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			connection = DriverManager.getConnection(DB_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void setupBookTable() {
		final String TABLE_NAME = "BOOK";
		try {
			statement = connection.createStatement();
			DatabaseMetaData dbMetaData = connection.getMetaData();
			ResultSet tables = dbMetaData.getTables(null, null, TABLE_NAME.toUpperCase(), null);
			if (tables.next()) {
				System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
			} else {
				statement.execute("CREATE TABLE " + TABLE_NAME + "(" + "id varchar(200) primary key,\n"
						+ "title varchar(200),\n" + "author varchar(200),\n" + "publisher varchar(100),\n"
						+ "isAvail boolean default true" + ")");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage() + " ----setupDatabase");
		} finally {

		}

	}

	private void setupMemberTable() {
		final String TABLE_NAME = "MEMBER";
		try {
			statement = connection.createStatement();
			DatabaseMetaData dbMetaData = connection.getMetaData();
			ResultSet tables = dbMetaData.getTables(null, null, TABLE_NAME.toUpperCase(), null);
			if (tables.next()) {
				System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
			} else {
				statement.execute("CREATE TABLE " + TABLE_NAME + "(" + "id varchar(200) primary key,\n"
						+ "name varchar(200),\n" + "mobile varchar(20),\n" + "email varchar(100)\n" + ")");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage() + " ----setupDatabase");
		} finally {

		}

	}

	private void setupIssueTable() {
		final String TABLE_NAME = "ISSUE";
		try {
			statement = connection.createStatement();
			DatabaseMetaData dbMetaData = connection.getMetaData();
			ResultSet tables = dbMetaData.getTables(null, null, TABLE_NAME.toUpperCase(), null);
			if (tables.next()) {
				System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
			} else {
				statement.execute("CREATE TABLE " + TABLE_NAME + "(" + "bookID varchar(200) primary key,\n"
						+ "memberID varchar(200),\n" + "issueTime timestamp default CURRENT_TIMESTAMP,\n"
						+ "renew_count integer default 0,\n" + "FOREIGN KEY (bookID) REFERENCES BOOK(id),\n"
						+ "FOREIGN KEY (memberID) REFERENCES MEMBER(id)" + ")");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage() + " ----setupDatabase");
		} finally {

		}

	}

	public ResultSet execQuery(String query) {
		ResultSet resultSet;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			System.err.println("Exception at execQuery:dataHanlder " + e.getLocalizedMessage());
			return null;
		}

		return resultSet;
	}

	public boolean execAction(String que) {
		try {
			statement = connection.createStatement();
			statement.execute(que);
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Error at execQuery:dataHandler " + e.getLocalizedMessage());
			return false;
		}
	}

}
