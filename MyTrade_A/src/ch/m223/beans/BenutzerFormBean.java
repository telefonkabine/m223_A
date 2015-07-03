/**
 * @author dennis.gehrig
 * @date   29.05.2015
 */
package ch.m223.beans;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ch.m223.dao.UserDAO;
import ch.m223.model.UserModel;

@ManagedBean
@SessionScoped
public class BenutzerFormBean {
	private int rolle;
	private String name;
	private String vorname;
	private String login;
	private String passwort;
	
	//Liste der Auswahl des Drop-Downs fuer die User-Rolle
	private static final Map<String,Object> ROLLENLISTE;
	static{
		ROLLENLISTE = new LinkedHashMap<String, Object>();
		ROLLENLISTE.put("Administrator", 1);
		ROLLENLISTE.put("Aktienhändler", 2);
	}
	
	public Map<String,Object> getRollenListe() {
		return ROLLENLISTE;
	}
	
	public String saveUser(){
		UserModel user = new UserModel();
		UserDAO userDao = new UserDAO();
		MeldungFormBean m = new MeldungFormBean();
		
		//User in Datenbank schreiben
		user.setName(name);
		user.setVorname(vorname);
		user.setLogin(login);
		user.setPasswort(passwort);
		user.setFk_typID(rolle);
		user.setKontostand(10000);
		userDao.insertUser(user);
		
		//Meldung Ausgeben
		m.setAktuelleMeldung(m.getMeldung3());
		m.putMeldungToSession(m);
		
		return "/private/admin/Admin?faces-redirect=true";
	}
	
	// Weiter-Button von Benutzer erfassen zu Benutzerbestaetigung
	public String next(){
		return "/private/admin/Benutzerbestaetigung?faces-redirect=true";
	}
	
	// Zurueck-Button von Benutzer erfassen zur Adminseite
	public String back(){
		return "/private/admin/Admin?faces-redirect=true";
	}
	
	// Zurueck-Button von Benutzerbestaetigung zur Benutzer erfassen
	public String back2(){
		return "/private/admin/Benutzererfassen?faces-redirect=true";
	}
	
	//Getters and Setters
	public int getRolle() {
		return rolle;
	}
	public void setRolle(int rolle) {
		this.rolle = rolle;
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
