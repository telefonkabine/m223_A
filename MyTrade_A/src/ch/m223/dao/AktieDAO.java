package ch.m223.dao;

import java.sql.*;

import javax.faces.context.FacesContext;

import ch.m223.connectionPooling.ConnectionPooling;
import ch.m223.connectionPooling.ConnectionPoolingImplementation;
import ch.m223.model.AktieModel;

public class AktieDAO {

	//Eine neue Aktien hinzufügen
	public synchronized boolean insertAktie(String name, String kuerzel, double nominalpreis, double dividende, int benutzerID, int anzahl) {
		
		//ID des aktuell eingeloggten Benutzer definieren
		FacesContext context = FacesContext.getCurrentInstance();
		String benutzerIDString;
		
		benutzerIDString = (String) context.getExternalContext().getSessionMap().get("id");
		if(benutzerIDString == null){
			System.out.println("Fehler: AktieDAO, Methode: InsertAktie");
			
			return false;
		} 
		benutzerID= Integer.parseInt(benutzerIDString);
		
		try {
			ConnectionPooling connectionPooling;
			connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
			Connection con = connectionPooling.getConnection();

			String insertTableSQL = "INSERT INTO aktie (Name, kuerzel, nominalpreis, dividende, fk_benutzerID) "
                    + "VALUES (?, ?, ?, ?, ?)";
			
				PreparedStatement preparedStatement = con.prepareStatement(insertTableSQL);
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, kuerzel);
				preparedStatement.setDouble(3, nominalpreis);
				preparedStatement.setDouble(4, dividende);
				preparedStatement.setInt(5, benutzerID);
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
			}
		
			return false;
	}
	
	public synchronized AktieModel getAktieFromUserID(int benutzerID){
		//to do: Aktien anhand von BenutzerID auslesen und in eine Liste speichern.
		try{
			
			ConnectionPooling connectionPooling;
			connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
			
			Connection con = connectionPooling.getConnection();
			
			PreparedStatement preparedStatement = con.prepareStatement("SELECT AktienID, dividende, fk_benutzerID, kuerzel, name, nominalpreis FROM mytrade.aktie WHERE fk_benutzerID = ?");
			preparedStatement.setInt(3, benutzerID);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			AktieModel aktien = new AktieModel();
			if(rs.next()){
				System.out.println(rs.getString("login"));
				aktien.setKuerzel("kuerzel");

				return aktien;
			}
			preparedStatement.close();
			connectionPooling.putConnection(con);	
		} catch(SQLException sqle){
			System.out.println("Es trat ein Fehler im SQL auf.");
			sqle.printStackTrace();
		}
		
		return null;
	}

}
