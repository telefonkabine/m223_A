package ch.m223.model;

import ch.m223.beans.MeldungFormBean;

public class AuftragModel {
	private int preis;
	private int fk_AtkienID;
	private int kontostand;
	private String symbol;
	private String name;
	private boolean isUser;
	private int auftragId;
	

	public String doKaufenOrStornieren(){
//		storno
		if (true) {

			MeldungFormBean.aktuelleMeldung = new MeldungFormBean()
					.getMeldung6();
			
		} else {

			MeldungFormBean.aktuelleMeldung = new MeldungFormBean()
					.getMeldung4();
//			kaufen

		}
		System.out.println(isUser);
		return "/private/Auftraege?faces-redirect=true";
	}
	
	
	public int getAuftragId() {
		return auftragId;
	}
	public void setAuftragId(int auftragId) {
		this.auftragId = auftragId;
	}
	public boolean isUser() {
		return isUser;
	}
	public void setUser(boolean isUser) {
		this.isUser = isUser;
	}
	public int getKontostand() {
		return kontostand;
	}
	public void setKontostand(int kontostand) {
		this.kontostand = kontostand;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPreis() {
		return preis;
	}
	public void setPreis(int preis) {
		this.preis = preis;
	}
	public int getFk_AtkienID() {
		return fk_AtkienID;
	}
	public void setFk_AtkienID(int fk_AtkienID) {
		this.fk_AtkienID = fk_AtkienID;
	}
	
	
}
