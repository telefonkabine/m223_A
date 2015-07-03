/**
 * @author : Jason Angst
 * @date   : 03.07.2015
 * @version: 1.0
 * 
 * **/
package ch.m223.beans;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import ch.m223.dao.AktieDAO;
import ch.m223.model.AktieModel;
import ch.m223.model.UserModel;

@ManagedBean
@SessionScoped
public class MeinPortfolioBean {
	UserModel u;
	AktieDAO a;
	ArrayList<AktieModel> list;
	Double konto;
	
	/**
	 * Holt den Kontostand des Users aus der Session
	 * @return den Kontostand des angemeldeten Users
	 */
	public Double getKontostand(){
		u = new UserModel().getUserObjectFromSession();
		konto = u.getKontostand();
	
	
		return konto;
	}
	
	/**
	 * Holt die Aktien des angemeldeten Users aus der DB
	 * @return Liste aller Aktien des angemeldeten Users
	 */
	public ArrayList<AktieModel> getList() {
		a =    new AktieDAO();
		u =    new UserModel().getUserObjectFromSession();
		list = new ArrayList<AktieModel>();
//		holt Aktien aus DB
		list = a.getAktieByUserId(u.getBenutzerID());
		return list;
	}
	public void setList(ArrayList<AktieModel> list) {
		this.list = list;
	}

}
