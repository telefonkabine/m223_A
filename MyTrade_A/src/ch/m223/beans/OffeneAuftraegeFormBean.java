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


	public List<AuftragModel> getAuftraege() {
		return auftraege;
	}

	public void setAuftraege(List<AuftragModel> auftraege) {
		this.auftraege = auftraege;
	}
}
