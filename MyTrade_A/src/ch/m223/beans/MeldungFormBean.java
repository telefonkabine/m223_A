package ch.m223.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class MeldungFormBean {

	private String meldung1 = "Die Aktie wurde erfolgreich gespeichert: ";
	private String meldung2 = "Die Dividende wurde erfolgreich ausgeschüttet.";
	private String meldung3 = "Der Benutzer wurde erfolgreich angelegt: ";
	private String meldung4 = "Der Auftrag wurde erfolgreich ausgeführt: ";
	private String meldung5 = "Der Auftrag wurde erfolgreich erfasst: ";
	private String meldung6 = "Der Auftrag wurde erfolgreich storniert: ";
	private String aktuelleMeldung = "";

	public String getMeldung1() {
		return meldung1;
	}

	public String getMeldung2() {
		return meldung2;
	}

	public String getMeldung3() {
		return meldung3;
	}

	public String getMeldung4() {
		return meldung4;
	}

	public String getMeldung5() {
		return meldung5;
	}

	public String getMeldung6() {
		return meldung6;
	}

	public void setAktuelleMeldung(String meldung) {
		this.aktuelleMeldung = meldung;
	}
	
	public String getAktuelleMeldung() {
		return aktuelleMeldung;
	}
	
	

}
