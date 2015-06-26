package ch.m223.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class DividendenFormBean {
	
	public void ausschuetten() {
		
		MeldungFormBean.aktuelleMeldung = new MeldungFormBean().getMeldung2();
		return ;
	}

}
