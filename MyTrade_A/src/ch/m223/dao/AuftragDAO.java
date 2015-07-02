package ch.m223.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import ch.m223.connectionPooling.ConnectionPooling;
import ch.m223.connectionPooling.ConnectionPoolingImplementation;
import ch.m223.model.AktieModel;
import ch.m223.model.AuftragModel;
import ch.m223.model.UserModel;

public class AuftragDAO {
	
	public synchronized boolean insertAuftrag(double preis, int aktieID, long anzahl) {
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
		
		Connection con = connectionPooling.getConnection();
		try {

			String insertTableSQL = "INSERT INTO auftrag (Preis, Fk_AktienID) "
                    + "VALUES (?, ?)";
			
				PreparedStatement preparedStatement = con.prepareStatement(insertTableSQL);
				preparedStatement.setDouble(1, preis);
				preparedStatement.setInt(2, aktieID);
				
				while (anzahl>0){
				anzahl = anzahl -1;
				preparedStatement.executeUpdate();	
				}
				
			preparedStatement.close();
			connectionPooling.putConnection(con);	
			
			return true;
		
			} catch (SQLException e) {
			System.out.println("Es trat ein Fehler mit SQL auf");
			e.printStackTrace();
			connectionPooling.putConnection(con);
			}
		
			return false;
	}
	
	//TODO: Dennis, denn es ist noch nicht fertig
	public List<AuftragModel> getAuftraege(){
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
		
		Connection con = connectionPooling.getConnection();
		List<AuftragModel> auftraege = new ArrayList<AuftragModel>();
		
		AuftragModel auftrag;
		try {
			
			PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM auftrag");
	
			ResultSet rs = preparedStatement.executeQuery();
			
			AktieModel aktie = new AktieModel();
			AktieDAO aktieDao = new AktieDAO();
//			User aus Session holen 

			UserModel u = new UserModel().getUserObjectFromSession();
			while(rs.next()){
				auftrag = new AuftragModel();
//				AuftragId
				auftrag.setAuftragId(rs.getInt("auftragId"));
//				Preis
				auftrag.setPreis(rs.getInt("preis"));
//				Verbindung zu Aktie
				auftrag.setFk_AtkienID(rs.getInt("fk_aktienId"));
//				AktienObjekt befüllen von DB
				aktie = aktieDao.getAktieById(auftrag.getFk_AtkienID());
//				Name der Aktie in AuftragsObjekt schreiben
				auftrag.setName(aktie.getName());
//				Kuerzel von Aktie in AuftragsObjekt schreiben
				auftrag.setSymbol(aktie.getKuerzel());
				
//				if true auftrag.isUser == true;
//			
//				TODO: wenn login aktiv : auftrag.setUser(aktie.getFk_benutzerId() == u.getBenutzerID());
				auftrag.setUser(aktie.getFk_benutzerId() == u.getBenutzerID());
			
				auftraege.add(auftrag);
			}
			
			preparedStatement.close();
			connectionPooling.putConnection(con);
			return auftraege;
		
		} catch(SQLException sqlEx){
			connectionPooling.putConnection(con);
			sqlEx.printStackTrace();
		}
		return null;
	}
	
	public void deleteAuftragById(int auftragId){
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
		
		Connection con = connectionPooling.getConnection();
		try{	
			
			PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM auftrag WHERE auftragId = ?");
			preparedStatement.setInt(1, auftragId);
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connectionPooling.putConnection(con);	

		} catch(SQLException sqlEx){
			sqlEx.printStackTrace();
			connectionPooling.putConnection(con);
		}
	}
	
	public void doKaufen(AuftragModel auftragModel){
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
		
		Connection con = connectionPooling.getConnection();
		try{
			UserModel u = new UserModel().getUserObjectFromSession();
			System.out.println("doKaufen, User: " + u + "auftragModel.getPreis: " + auftragModel.getPreis());
			double neuerKontostand = u.getKontostand() - auftragModel.getPreis();
			int käuferId = u.getBenutzerID();
			PreparedStatement preparedStatement;
			
			preparedStatement = con.prepareStatement("UPDATE benutzer SET kontostand=? WHERE benutzerId=?");
			preparedStatement.setDouble(1, neuerKontostand);
			preparedStatement.setInt(2, käuferId);
			preparedStatement.executeUpdate();
			u.setKontostand(neuerKontostand);
			
			UserDAO userDao         = new UserDAO();
			AktieDAO aktieDao       = new AktieDAO();
			AktieModel aktieModel   = aktieDao.getAktieById(auftragModel.getFk_AtkienID());
			u                       = userDao.getUserById(aktieModel.getFk_benutzerId());
			
			neuerKontostand = u.getKontostand() + auftragModel.getPreis();
			System.out.println(neuerKontostand);
			
			preparedStatement = con.prepareStatement("UPDATE benutzer SET kontostand=? WHERE benutzerId=?");
			preparedStatement.setDouble(1, neuerKontostand);
			preparedStatement.setInt(2, u.getBenutzerID());
			preparedStatement.executeUpdate();
			
			preparedStatement = con.prepareStatement("UPDATE aktie SET fk_benutzerId=? WHERE aktienId=?");
			preparedStatement.setInt(1, käuferId);
			preparedStatement.setInt(2, aktieModel.getAktienId());
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connectionPooling.putConnection(con);	

		} catch(SQLException sqlEx){
			sqlEx.printStackTrace();
			connectionPooling.putConnection(con);
		}
	}

}
