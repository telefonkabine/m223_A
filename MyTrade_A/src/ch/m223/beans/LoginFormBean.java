package ch.m223.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ch.m223.dao.UserDAO;



@ManagedBean
@SessionScoped
public class LoginFormBean {

	private String username;
	private String password;
	private UserDAO jdbc;

	public LoginFormBean() {

		jdbc = new UserDAO();
	}

	public String anmelden() {

		if (jdbc.accountExistiert(username, password)) {
			System.out.println("hat geklappt");
			return "Index?faces-redirect=true";
		} else {
			System.out.println("User oder PW falsch");
			return "login?faces-redirect=true";
		}
		
	
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String user) {
		this.username = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
