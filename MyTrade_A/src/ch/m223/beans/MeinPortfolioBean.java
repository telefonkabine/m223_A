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
	
	public Double getKontostand(){
	konto = u.getKontostand();
	
	return konto;
	}
	
	public ArrayList<AktieModel> getList() {
		return list;
	}
	public void setList(ArrayList<AktieModel> list) {
		this.list = list;
	}
	
	public String verkaufen(AktieModel a){
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		a = (AktieModel) externalContext.getSessionMap().put("aktie", a);
		
		//Test
		System.out.println(a.getAktienId());
		
		return "/private/haendler/Auftragerfassen?faces-redirect=true";
	}
	
	public MeinPortfolioBean() {
		a = new AktieDAO();
		u = new UserModel().getUserObjectFromSession();
		list = new ArrayList<AktieModel>();
		list = a.getAktieByUserId(u.getBenutzerID());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
//	public void test(){
//		
//		UserModel u = new UserModel();
//		a.getAktieByUserId(u.getUserObjectFromSession().getBenutzerID());
//	}
	
	}
	//to do: verkaufen methode
	public String Verkaufen(){
		
	return null;
	}
	


}
