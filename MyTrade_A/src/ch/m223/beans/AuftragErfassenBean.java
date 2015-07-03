package ch.m223.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ch.m223.dao.AktieDAO;
import ch.m223.dao.AuftragDAO;
import ch.m223.model.AktieModel;

@ManagedBean
@SessionScoped
public class AuftragErfassenBean {
		
	AktieDAO aktieDao;
	AuftragDAO auftragDao;
	AktieModel a;
	private String displayname;
	private int aktieID;
	private double preis;
	private int anzahl;
	private long maxAnzahl;
	
	public AuftragErfassenBean() {
		anzahl = 1; //Standartwert
	}
	public String verkaufen(AktieModel aktieModel){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.getSessionMap().put("Aktie", aktieModel);
		displayCurrentAktie();
		return "/private/haendler/Auftragerfassen?faces-redirect=true";
	}
	
	public void displayCurrentAktie(){
		AktieModel a;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		a = (AktieModel) externalContext.getSessionMap().get("Aktie");
		displayname = "" + a.getName() + " / " + a.getKuerzel() + "";
		System.out.println(displayname);
	}
	
	public String insertAuftrag(){
		auftragDao = new AuftragDAO();
		a = a.getAktieFromSession();
		auftragDao.insertAuftrag(preis, a.getKuerzel(), anzahl);
		
		MeldungFormBean m = new MeldungFormBean();
		m.setAktuelleMeldung(m.getMeldung5());
	return "/private/haendler/Portfolio?faces-redirect=true";
	}
	
	// Zurueck-Button von Auftrag erfassen zum Portfolio
	public String back(){
		return "/private/haendler/Portfolio?faces-redirect=true";	
	}
	
	//Getters and Setters
	public long getMaxAnzahl() {
		aktieDao = new AktieDAO();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		a = (AktieModel) externalContext.getSessionMap().get("Aktie");
		return maxAnzahl = aktieDao.getAnzahlAktieBySymbol(a.getKuerzel());
	}
	public void setMaxAnzahl(long maxAnzahl) {
		this.maxAnzahl = maxAnzahl;
	}
	public AktieDAO getAktieDao() {
		return aktieDao;
	}
	public void setAktieDao(AktieDAO aktieDao) {
		this.aktieDao = aktieDao;
	}
	public AktieModel getA() {
		return a;
	}
	public void setA(AktieModel a) {
		this.a = a;
	}
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public int getAktieID() {
		return aktieID;
	}
	public void setAktieID(int aktieID) {
		this.aktieID = aktieID;
	}
	public double getPreis() {
		return preis;
	}
	public void setPreis(double preis) {
		this.preis = preis;
	}
	public int getAnzahl() {
		return anzahl;
	}
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
	public AuftragDAO getAuftragDao() {
		return auftragDao;
	}
	public void setAuftragDao(AuftragDAO auftragDao) {
		this.auftragDao = auftragDao;
	}
}
