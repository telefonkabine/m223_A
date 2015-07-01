package ch.m223.model;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class UserModel {
	
	private int benutzerID;
	private String name;
	private String vorname;
	private String login;
	private String passwort;
	private int fk_typID;
	private int kontostand;
	
	public UserModel getUserObjectFromSession(){
		UserModel u;
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		u= (UserModel)externalContext.getSessionMap().get("user");
		
	return u;
	}
	public int getBenutzerID() {
		return benutzerID;
	}
	public void setBenutzerID(int benutzerID) {
		this.benutzerID = benutzerID;
	}
	public String getPasswort() {
		return passwort;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public int getFk_typID() {
		return fk_typID;
	}
	public void setFk_typID(int fk_typID) {
		this.fk_typID = fk_typID;
	}
	public int getKontostand() {
		return kontostand;
	}
	public void setKontostand(int kontostand) {
		this.kontostand = kontostand;
	}
}
