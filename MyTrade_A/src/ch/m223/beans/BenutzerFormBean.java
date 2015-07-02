/**
 * @author dennis.gehrig
 * @date   29.05.2015
 */
package ch.m223.beans;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

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
	
	private static final Map<String,Object> ROLLENLISTE;
	static{
		ROLLENLISTE = new LinkedHashMap<String, Object>();
		ROLLENLISTE.put("Administrator", 1);
		ROLLENLISTE.put("Aktienhändler", 2);
	}
	
	public int getRolle() {
		return rolle;
	}
	public void setRolle(int rolle) {
		System.out.println("Läuft durch setRolle()" + rolle);
		this.rolle = rolle;
	}
	
	public Map<String,Object> getRollenListe() {
		return ROLLENLISTE;
	}
	
	public String next(){
		
	return "/private/admin/Benutzerbestaetigung?faces-redirect=true";
	}
	
	public String back(){
		
	return "/private/admin/Admin?faces-redirect=true";
	}
	
	public String back2(){
		
	return "/private/admin/Benutzererfassen?faces-redirect=true";
	}
	
	public String saveUser(){
		UserModel user = new UserModel();
		UserDAO userDao = new UserDAO();
		MeldungFormBean m = new MeldungFormBean();
		
		user.setName(name);
		user.setVorname(vorname);
		user.setLogin(login);
		user.setPasswort(passwort);
		user.setFk_typID(rolle);
		user.setKontostand(10000);
		
		userDao.insertUser(user);
		
		m.setAktuelleMeldung(m.getMeldung3());
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.getSessionMap().put("meldungFormBean", m);
		
		return "/private/admin/Admin?faces-redirect=true";

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
