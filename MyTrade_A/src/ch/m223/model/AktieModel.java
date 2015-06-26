package ch.m223.model;

public class AktieModel {
	
	private int aktienId;
	private int nominalpreis;
	private int fk_benutzerId;
	private String name;
	private String kuerzel;
	private int anzahl;
	private int dividende;
	
	public int getAktienId() {
		return aktienId;
	}

	public void setAktienId(int aktienId) {
		this.aktienId = aktienId;
	}

	public int getNominalpreis() {
		return nominalpreis;
	}

	public void setNominalpreis(int nominalpreis) {
		this.nominalpreis = nominalpreis;
	}

	public int getFk_benutzerId() {
		return fk_benutzerId;
	}

	public void setFk_benutzerId(int fk_benutzerId) {
		this.fk_benutzerId = fk_benutzerId;
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

	public int getAnzahl() {
		return anzahl;
	}

	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}

	public int getDividende() {
		return dividende;
	}

	public void setDividende(int dividende) {
		this.dividende = dividende;
	}

}
