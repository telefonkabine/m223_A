/**
 * @author dennis.gehrig
 * @date   29.05.2015
 */
package ch.m223.beans;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class BenutzerFormBean {
	private int rolle;
	private String name;
	private String vorname;
	private String login;
	private String passwort;
	
	private static final Map<String,Object> ROLLENLISTE;
	static{
		ROLLENLISTE = new LinkedHashMap<String, Object>();
		ROLLENLISTE.put("Administrator", 1);
		ROLLENLISTE.put("Aktienh�ndler", 2);
	}
	
	public int getRolle() {
		return rolle;
	}
	public void setRolle(int rolle) {
		System.out.println("L�uft durch setRolle()" + rolle);
		this.rolle = rolle;
	}
	
	public Map<String,Object> getRollenListe() {
		return ROLLENLISTE;
	}
	
	public String saveUser(){
		
		return "Admin?faces-redirect=true";
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
	public String getPasswort() {
		return passwort;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	
	
}
