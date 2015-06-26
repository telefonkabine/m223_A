package ch.m223.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ch.m223.dao.UserDAO;
import ch.m223.model.UserModel;



@ManagedBean
@SessionScoped
public class LoginFormBean {

	private String username;
	private String password;
	private UserDAO jdbc;
	FacesContext context;
	

	public LoginFormBean() {

		jdbc = new UserDAO();
	}

	public String anmelden() {
		UserModel u = null;
		if (jdbc.login(username, password)) {
			System.out.println("hat geklappt");
				u = new UserModel();
				u = jdbc.getUserByLogin(username);
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();
				externalContext.getSessionMap().put("user", u);
			return "/private/Portfolio?faces-redirect=true";
		} else {
			System.out.println("User oder PW falsch");
			return "/public/login?faces-redirect=true";
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
