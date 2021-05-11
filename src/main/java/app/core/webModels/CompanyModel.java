package app.core.webModels;

import app.core.entities.Company;

public class CompanyModel {
	
	private int id;
	private String name;
	private String email;
	private String password;
	private String token;
	
	public CompanyModel(Company company, String token) {
		this.id = company.getId();
		this.name = company.getName();
		this.email = company.getEmail();
		this.password = company.getPassword();
		this.token = token;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
