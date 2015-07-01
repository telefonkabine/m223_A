package ch.m223.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

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
//		TODO: u.getBenutzerID(), das kommt anstelle der 2
		
		if (aktieDao.insertAktie(name, kuerzel, nominalpreis, dividende,
				2, anzahl)) {
			m.setAktuelleMeldung(m.getMeldung1());
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			externalContext.getSessionMap().put("meldungFormBean", m);
			return "/private/Admin?faces-redirect=true";

		} else {
			m.setAktuelleMeldung("");
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			externalContext.getSessionMap().put("meldungFormBean", m);
			return "/private/Aktienerfassen?faces-redirect=true";
		}
		
	}

	// Zurueck-Button von Aktienerfassen zur Hauptseite
	public String back() {

		return "/private/Admin?faces-redirect=true";
	}

	// Zurueck-Button von Aktienbestaetigung zu Aktienerfassen
	public String back2() {

		return "/private/Aktienerfassen?faces-redirect=true";
	}

	// Weiter-Button von Aktienerfassen zu Aktienbestaetigung
	public String next() {

		return "/private/Aktienbestaetigung?faces-redirect=true";
	}

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
