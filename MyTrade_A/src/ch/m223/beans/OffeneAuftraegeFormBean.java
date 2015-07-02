package ch.m223.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import ch.m223.dao.AuftragDAO;
import ch.m223.model.AuftragModel;
import ch.m223.model.UserModel;

@ManagedBean
@SessionScoped
public class OffeneAuftraegeFormBean {
	private List<AuftragModel> auftraege;
	private int kontostand;
	private AuftragDAO auftragDao = new AuftragDAO();

	
	public String doAktion(AuftragModel auftragModel){

		MeldungFormBean m = new MeldungFormBean();
		//		storno
		if (auftragModel.isUser()) {

			m.setAktuelleMeldung(m.getMeldung6());
			System.out.println("storno");
			System.out.println(auftragModel.getAuftragId());
			auftragDao.deleteAuftragById(auftragModel.getAuftragId());
		} 
//		kaufen
		else {

			m.setAktuelleMeldung(m.getMeldung4());
			System.out.println("kaufen");
			auftragDao.doKaufen(auftragModel);
			auftragDao.deleteAuftragById(auftragModel.getAuftragId());
			
		}
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.getSessionMap().put("meldungFormBean", m);
		System.out.println(auftragModel.isUser());
		return "/private/haendler/Auftraege?faces-redirect=true";
	}
	
	public int getKontostand() {
//		TODO: geht erst wenn login, da sonst kein User in der Session ist
//		UserModel u = new UserModel().getUserObjectFromSession();
//		System.out.println("OffeneAuftraegeFormBean, User: " +u);
		return 10000;//this.kontostand = u.getKontostand();
	}

	public void setKontostand(int kontostand) {
		this.kontostand = kontostand;
	}

	public List<AuftragModel> getAuftraege() {
		AuftragDAO auftragDao = new AuftragDAO();
		return auftraege = auftragDao.getAuftraege();
	}

	public void setAuftraege(List<AuftragModel> auftraege) {
		this.auftraege = auftraege;
	}
}
