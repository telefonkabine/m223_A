package ch.m223.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ch.m223.connectionPooling.ConnectionPooling;
import ch.m223.connectionPooling.ConnectionPoolingImplementation;

public class AuftragDAO {
	
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
			con.close();	
			
			return true;
		
			} catch (SQLException e) {
			System.out.println("Es trat ein Fehler mit SQL auf");
			e.printStackTrace();
			}
		
			return false;
	}

}
