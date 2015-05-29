package ch.m223.beans;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class BenutzerFormBean {
	private String rolle;

	private static Map<String,Object> rollenListe;
	static{
		rollenListe = new LinkedHashMap<String, Object>();
		rollenListe.put("1", "Administrator");
		rollenListe.put("2", "Aktienhändler");
	}
	
	public String getRolle() {
		return rolle;
	}
	public void setRolle(String rolle) {
		this.rolle = rolle;
	}
	
	public Map<String,Object> getRollenListe() {
		return rollenListe;
	}
}
