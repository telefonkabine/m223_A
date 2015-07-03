package ch.m223.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ch.m223.dao.AuftragDAO;
import ch.m223.model.AuftragModel;
import ch.m223.model.UserModel;

@ManagedBean
@SessionScoped
public class OffeneAuftraegeFormBean {
	private List<AuftragModel> auftraege;
	private double kontostand;
	private AuftragDAO auftragDao = new AuftragDAO();

	
	public String doAktion(AuftragModel auftragModel){

		MeldungFormBean m = new MeldungFormBean();
		//stornieren
		if (auftragModel.isUser()) {

			m.setAktuelleMeldung(m.getMeldung6() + auftragModel.getAuftragId());
			System.out.println("storno");
			System.out.println(auftragModel.getAuftragId());
			auftragDao.deleteAuftragById(auftragModel.getAuftragId());
		} 
		//kaufen
		else {

			m.setAktuelleMeldung(m.getMeldung4() + auftragModel.getAuftragId());
			System.out.println("kaufen");
			auftragDao.doKaufen(auftragModel);
			auftragDao.deleteAuftragById(auftragModel.getAuftragId());
			
		}
		m.putMeldungToSession(m);
		System.out.println(auftragModel.isUser());
		return "/private/haendler/Auftraege?faces-redirect=true";
	}
	
	public double getKontostand() {
		UserModel u = new UserModel().getUserObjectFromSession();
		return this.kontostand = u.getKontostand();
	}
	public void setKontostand(double kontostand) {
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
