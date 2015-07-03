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
	
	public String verkaufen(AktieModel aktieModel){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.getSessionMap().put("Aktie", aktieModel);
		//Test
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
		AktieModel a;
		auftragDao = new AuftragDAO();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		a = (AktieModel) externalContext.getSessionMap().get("Aktie");
		System.out.println("anzahl:" +  anzahl);
//		aktieID = a.getAktienId();
		auftragDao.insertAuftrag(preis, a.getKuerzel(), anzahl);
		
	return "/private/haendler/Portfolio?faces-redirect=true";
	}
	
	
	public String back(){
		
	return "/private/haendler/Portfolio?faces-redirect=true";	
	}

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
}
