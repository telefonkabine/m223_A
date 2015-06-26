package ch.m223.beans;

import java.util.ArrayList;
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
	
	public String getAktion(){
		AuftragModel auftragM = new AuftragModel();
		
		if(auftragM.isUser()){
			MeldungFormBean.aktuelleMeldung = new MeldungFormBean().getMeldung6();
			return "stornieren";
		}
		else{
			MeldungFormBean.aktuelleMeldung = new MeldungFormBean().getMeldung4();
			return "kaufen";
		}
	}

	public List<AuftragModel> getAuftraege() {
		return auftraege;
	}

	public void setAuftraege(List<AuftragModel> auftraege) {
		this.auftraege = auftraege;
	}
}
