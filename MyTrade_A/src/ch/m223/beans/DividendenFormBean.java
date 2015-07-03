package ch.m223.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ch.m223.dao.UserDAO;

@ManagedBean
@SessionScoped
public class DividendenFormBean {

	public String ausschuetten() {
		UserDAO userDao = new UserDAO();
		userDao.dividendeAnBenutzer();
		
		MeldungFormBean m = new MeldungFormBean();
		m.setAktuelleMeldung(m.getMeldung2());
		m.putMeldungToSession(m);
		
		return "/private/admin/Admin?faces-redirect=true";
	}

}
