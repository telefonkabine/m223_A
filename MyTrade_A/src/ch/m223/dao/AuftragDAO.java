package ch.m223.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.m223.connectionPooling.ConnectionPooling;
import ch.m223.connectionPooling.ConnectionPoolingImplementation;
import ch.m223.model.AktieModel;
import ch.m223.model.AuftragModel;

public class AuftragDAO {
	
	//TODO: Auftrag erfassen!
	//Ein neuer Auftrag hinzufügen
	public synchronized boolean insertAuftrag(double preis, int aktieID, int anzahl) {
		//TO DO: ID des aktuell eingeloggten Benutzer definieren
		aktieID=1;
		try {
			ConnectionPooling connectionPooling;
			connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
			Connection con = connectionPooling.getConnection();

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
			}
		
			return false;
	}
	
	//TODO: Dennis, denn es ist noch nicht fertig
	public List<AuftragModel> getAuftraege(){
		List<AuftragModel> auftraege = new ArrayList();
		
		AuftragModel auftrag;
		try {
			ConnectionPooling connectionPooling;
			connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
			
			Connection con = connectionPooling.getConnection();
			
			PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM auftrag");
	
			ResultSet rs = preparedStatement.executeQuery();
			
			AktieModel aktie = new AktieModel();
			AktieDAO aktieDao = new AktieDAO();
			while(rs.next()){
				auftrag = new AuftragModel();
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
				
//				TODO: benutzerId von aktie mit userId in Session abgleichen
//				if true auftrag.isUser == true;
				if(aktie.getFk_benutzerId() == 2){
					auftrag.setUser(true);
				}
				else{
					auftrag.setUser(false);
				}
				
				auftraege.add(auftrag);
			}
			
			return auftraege;
		
		} catch(SQLException sqlEx){
			sqlEx.printStackTrace();
		}
		return null;
	}
	

}
