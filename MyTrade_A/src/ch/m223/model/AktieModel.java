package ch.m223.model;

public class AktieModel {
	
	private String name;
	private String kuerzel;
	private int anzahl;
	private int letztedividende;
	
	public AktieModel() {
		
		
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

	public int getLetztedividende() {
		return letztedividende;
	}

	public void setLetztedividende(int letztedividende) {
		this.letztedividende = letztedividende;
	}

}
