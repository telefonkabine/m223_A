package ch.m223.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ch.m223.dao.AktieDAO;


@ManagedBean
@SessionScoped
public class AktienErfassenBean {

	private String name;
	private String kuerzel;
	private double nominalpreis;
	private double dividende;
	private int benutzerID;
	private int anzahl;
	private AktieDAO aktieDao;

	public AktienErfassenBean() {

		aktieDao = new AktieDAO();
		
		
	}

	public String AktieErfassen() {
		
		aktieDao.InsertAktie(name, kuerzel, nominalpreis, dividende, benutzerID, anzahl);
		
		return null;
	}
	
	public String save(){
	
	return "Index?faces-redirect=true";
	}
	
	public String back2(){
		
	return "Aktienerfassen?faces-redirect=true";	
	}
	
	public String next(){
		
	return "Aktienbestaetigung?faces-redirect=true";	
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
