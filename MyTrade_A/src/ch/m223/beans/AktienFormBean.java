package ch.m223.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ch.m223.dao.AktieDAO;
import ch.m223.model.UserModel;

@ManagedBean
@SessionScoped
public class AktienFormBean {

	private String name;
	private String kuerzel;
	private double nominalpreis;
	private double dividende;
	private int benutzerID;
	private int anzahl;
	private AktieDAO aktieDao;

	public AktienFormBean() {
		aktieDao = new AktieDAO();
	}

	// Speichert eingegebene Daten in die Datenbank. Liefert False, falls der
	// INSERT ungueltig ist
	public String save() {
		MeldungFormBean m = new MeldungFormBean();
		UserModel u = new UserModel().getUserObjectFromSession();
		
		if (aktieDao.insertAktie(name, kuerzel, nominalpreis, dividende, u.getBenutzerID(), anzahl)) {
			m.setAktuelleMeldung(m.getMeldung1());
			m.putMeldungToSession(m);
			return "/private/admin/Admin?faces-redirect=true";
		} else {
			m.setAktuelleMeldung("");
			m.putMeldungToSession(m);
			return "/private/admin/Aktienerfassen?faces-redirect=true";
		}
	}

	// Zurueck-Button von Aktienerfassen zur Hauptseite
	public String back() {
		return "/private/admin/Admin?faces-redirect=true";
	}

	// Zurueck-Button von Aktienbestaetigung zu Aktienerfassen
	public String back2() {
		return "/private/admin/Aktienerfassen?faces-redirect=true";
	}

	// Weiter-Button von Aktienerfassen zu Aktienbestaetigung
	public String next() {
		return "/private/admin/Aktienbestaetigung?faces-redirect=true";
	}
	
	//Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKuerzel() {
		return kuerzel;
	}

	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
	}

	public double getNominalpreis() {
		return nominalpreis;
	}

	public void setNominalpreis(double nominalpreis) {
		this.nominalpreis = nominalpreis;
	}

	public double getDividende() {
		return dividende;
	}

	public void setDividende(double dividende) {
		this.dividende = dividende;
	}

	public int getBenutzerID() {
		return benutzerID;
	}

	public void setBenutzerID(int benutzerID) {
		this.benutzerID = benutzerID;
	}

	public AktieDAO getAktieDao() {
		return aktieDao;
	}

	public void setAktieDao(AktieDAO aktieDao) {
		this.aktieDao = aktieDao;
	}

	public AktieDAO getaktieDao() {
		return aktieDao;
	}

	public void setaktieDao(AktieDAO aktieDao) {
		this.aktieDao = aktieDao;
	}

	public int getanzahl() {
		return anzahl;
	}

	public void setanzahl(int anzahl) {
		this.anzahl = anzahl;
	}

}
