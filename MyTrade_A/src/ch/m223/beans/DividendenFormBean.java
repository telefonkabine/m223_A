package ch.m223.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class DividendenFormBean {

	public String ausschuetten() {

		MeldungFormBean m = new MeldungFormBean();
		m.setAktuelleMeldung(m.getMeldung2());
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.getSessionMap().put("meldungFormBean", m);
		return "/private/admin/Admin?faces-redirect=true";

	}

}
