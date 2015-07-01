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
	
	//TODO: Auftrag erfassen!
	//Ein neuer Auftrag hinzufügen
	public synchronized boolean insertAuftrag(double preis, int aktieID, int anzahl) {
		//TO DO: ID des aktuell eingeloggten Benutzer definieren
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
		
		Connection con = connectionPooling.getConnection();
		aktieID=1;
		try {

			String insertTableSQL = "INSERT INTO auftrag (preis, fk_benutzerID) "
                    + "VALUES (?, ?)";
			
				PreparedStatement preparedStatement = con.prepareStatement(insertTableSQL);
				preparedStatement.setDouble(1, preis);
				preparedStatement.setInt(2, aktieID);
				preparedStatement.executeUpdate();	
				
				
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
				auftrag.setUser(aktie.getFk_benutzerId() == 2);
			
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
			int neuerKontostand = u.getKontostand() - auftragModel.getPreis();
			PreparedStatement preparedStatement = con.prepareStatement("UPDATE benutzer SET kontostand=? WHERE benutzerId=?");
			preparedStatement.setInt(1, neuerKontostand);
			preparedStatement.setInt(2, u.getBenutzerID());
			preparedStatement.executeUpdate();
			
			u.setKontostand(neuerKontostand);
			
			preparedStatement.close();
			connectionPooling.putConnection(con);	

		} catch(SQLException sqlEx){
			sqlEx.printStackTrace();
			connectionPooling.putConnection(con);
		}
	}

}
