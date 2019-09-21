package com.cu_bx.assignment.model;

import javafx.beans.property.SimpleStringProperty;

public final class Member {

	private final SimpleStringProperty id;
	private final SimpleStringProperty name;
	private final SimpleStringProperty mobile;
	private final SimpleStringProperty email;

	public Member(String id, String name, String mobile, String email) {
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.mobile = new SimpleStringProperty(mobile);
		this.email = new SimpleStringProperty(email);
	}

	public String getId() {
		return id.get();
	}

	public String getName() {
		return name.get();
	}

	public String getMobile() {
		return mobile.get();
	}

	public String getEmail() {
		return email.get();
	}

}
