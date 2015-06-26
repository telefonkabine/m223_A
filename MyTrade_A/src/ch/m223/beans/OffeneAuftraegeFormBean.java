package ch.m223.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ch.m223.dao.AuftragDAO;
import ch.m223.model.AuftragModel;

@ManagedBean
@SessionScoped
public class OffeneAuftraegeFormBean {
	private AuftragDAO auftragDao = new AuftragDAO();
	private List<AuftragModel> auftraege = auftragDao.getAuftraege();
	private String aktionName;
	private AuftragModel auftragM = new AuftragModel();
	
	
	public String getAktionName() {
		
		if(auftragM.isUser()){
			aktionName = "stornieren";
		}
		else{
			aktionName = "kaufen";
		}
		return aktionName;
	}

	public void setAktionName(String aktionName) {
		this.aktionName = aktionName;
	}
	
//	TODO: Welche View wird angezeigt nach kaufen/stornieren?
	public String doAktion(){
		if(auftragM.isUser()){
			doStornieren();
		}
		else{
			doKaufen();
		}
		return "/private/Auftraege?faces-redirect=true";
	}
	
	private void doKaufen(){
		System.out.println("kaufen");
	}
	
	private void doStornieren(){
		AuftragDAO auftragDao = new AuftragDAO();
		auftragDao.deleteAuftragById(auftragM.getAuftragId());
		System.out.println("stornieren");
	}
//	public String getAktion(){
//		AuftragModel auftragM = new AuftragModel();
//		
//		if(auftragM.isUser()){
//			return "stornieren";
//		}
//		else{
//			return "kaufen";
//		}
//	}

	public List<AuftragModel> getAuftraege() {
		return auftraege;
	}

	public void setAuftraege(List<AuftragModel> auftraege) {
		this.auftraege = auftraege;
	}
}
