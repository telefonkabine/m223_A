package ch.m223.dao;

import java.sql.*;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import ch.m223.connectionPooling.ConnectionPooling;
import ch.m223.connectionPooling.ConnectionPoolingImplementation;
import ch.m223.model.AktieModel;
import ch.m223.model.UserModel;

public class AktieDAO {
	
	UserModel u;
	ArrayList<AktieModel> portFolioList;

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
	
	public synchronized ArrayList<AktieModel> getAktieByUserId(int benutzerID){
		//to do: Aktien anhand von BenutzerID auslesen und in eine Liste speichern.
		try{
			
			benutzerID = 2; //TestID
			
			ConnectionPooling connectionPooling;
			connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
			
			Connection con = connectionPooling.getConnection();
			
			PreparedStatement preparedStatement = con.prepareStatement("SELECT AktienID, dividende, fk_benutzerID, kuerzel, name, nominalpreis FROM mytrade.aktie WHERE fk_benutzerID = ?");
			preparedStatement.setInt(1, benutzerID);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			portFolioList = new ArrayList<AktieModel>();
			
			while(rs.next()){
				AktieModel aktie = new AktieModel();
				aktie.setAktienId(rs.getInt("AktienID"));
				aktie.setName(rs.getString("Name"));
				aktie.setKuerzel(rs.getString("Kuerzel"));
				aktie.setNominalpreis(rs.getInt("Nominalpreis"));
				aktie.setDividende(rs.getInt("Dividende"));
				aktie.setFk_benutzerId(rs.getInt("Fk_BenutzerID")); //u.getUserObjectFromSession().getBenutzerID()
				portFolioList.add(aktie);
				System.out.println("name:" + aktie.getDividende());
			}
			
			System.out.println(portFolioList.toArray().toString());
			
			preparedStatement.close();
			connectionPooling.putConnection(con);	
		} catch(SQLException sqle){
			System.out.println("Es trat ein Fehler im SQL auf.");
			sqle.printStackTrace();
		}
		
		return portFolioList;
	}
	
	public AktieModel getAktieById(int aktieId){
		try{
			ConnectionPooling connectionPooling;
			connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
			
			Connection con = connectionPooling.getConnection();
			System.out.println("Connection: " + con);
			PreparedStatement preparedStatement = con.prepareStatement("SELECT aktienId, name, kuerzel, nominalpreis, dividende, fk_benutzerId "
																		+ "FROM aktie WHERE aktienId = ?");
			preparedStatement.setInt(1, aktieId);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			AktieModel aktie = null;
			while(rs.next()){
				aktie = new AktieModel();
				aktie.setAktienId(rs.getInt("aktienId"));
				aktie.setDividende(rs.getInt("dividende"));
				aktie.setFk_benutzerId(rs.getInt("fk_benutzerId"));
				aktie.setKuerzel(rs.getString("kuerzel"));
				aktie.setName(rs.getString("name"));
				aktie.setNominalpreis(rs.getInt("nominalpreis"));
				//return user;
			}
		connectionPooling.putConnection(con);
		return aktie;
		
		} catch(SQLException sqle){
			System.out.println("Es trat ein Fehler im SQL auf.");
			sqle.printStackTrace();
		}
			return null;
	}

}
