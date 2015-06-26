package ch.m223.beans;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ch.m223.dao.AktieDAO;
import ch.m223.model.AktieModel;

@ManagedBean
@SessionScoped
public class MeinPortfolioBean {
	
	AktieDAO a;
	ArrayList<AktieModel> list;
	
	public ArrayList<AktieModel> getList() {
		return list;
	}
	public void setList(ArrayList<AktieModel> list) {
		this.list = list;
	}
	public MeinPortfolioBean() {
		a = new AktieDAO();
		list =new ArrayList<AktieModel>();
		list = a.getAktieByUserId(2);
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
//	public void test(){
//		
//		UserModel u = new UserModel();
//		a.getAktieByUserId(u.getUserObjectFromSession().getBenutzerID());
//	}
	
	//to do: verkaufen methode
	public String Verkaufen(){
		
	return null;
	}
	

}
