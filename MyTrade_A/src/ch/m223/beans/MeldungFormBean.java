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
	private String meldung = "";

	public String getMeldung1() {
		return meldung1;
	}

	public void setMeldung1(String meldung1) {
		this.meldung1 = meldung1;
	}

	public String getMeldung2() {
		return meldung2;
	}

	public void setMeldung2(String meldung2) {
		this.meldung2 = meldung2;
	}

	public String getMeldung3() {
		return meldung3;
	}

	public void setMeldung3(String meldung3) {
		this.meldung3 = meldung3;
	}

	public String getMeldung4() {
		return meldung4;
	}

	public void setMeldung4(String meldung4) {
		this.meldung4 = meldung4;
	}

	public String getMeldung5() {
		return meldung5;
	}

	public void setMeldung5(String meldung5) {
		this.meldung5 = meldung5;
	}

	public String getMeldung6() {
		return meldung6;
	}

	public void setMeldung6(String meldung6) {
		this.meldung6 = meldung6;
	}

	public String getMeldung() {
		return meldung;
	}

	public void setMeldung(String meldung) {
		this.meldung = meldung;
	}

}
