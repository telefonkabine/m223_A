/**
 * @author : Christina Nordmann
 * @date   : 03.07.2015
 * @version: 1.0
 * 
 * **/
package ch.m223.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ch.m223.dao.UserDAO;

@ManagedBean
@SessionScoped
public class DividendenFormBean {

	/**
	 * Schüttet die Dividenden aus.
	 * @return String die Seite zu reloaden(Admin.xhtml).
	 */
	public String ausschuetten() {
		UserDAO userDao = new UserDAO();
//		Dividenden ausschütten
		userDao.dividendeAnBenutzer();
		
		MeldungFormBean m = new MeldungFormBean();
		m.setAktuelleMeldung(m.getMeldung2());
		m.putMeldungToSession(m);
		
		return "/private/admin/Admin?faces-redirect=true";
	}

}
