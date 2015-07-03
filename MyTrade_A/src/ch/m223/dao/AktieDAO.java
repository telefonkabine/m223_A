/**
 * @author : Dennis Gehrig
 * @date   : 30.07.2015
 * @version: 1.0
 * 
 * **/

package ch.m223.dao;

import java.sql.*;
import java.util.ArrayList;
import ch.m223.connectionPooling.ConnectionPooling;
import ch.m223.connectionPooling.ConnectionPoolingImplementation;
import ch.m223.model.AktieModel;
import ch.m223.model.UserModel;

public class AktieDAO {
	
	UserModel u;
	ArrayList<AktieModel> portFolioList;
	int anzahlAktien;

	/**
	 * Fügt eine neue Aktie in die Datenbank ein.
	 * @author : Dennis Gehrig
	 * @param name
	 * @param kuerzel
	 * @param nominalpreis
	 * @param dividende
	 * @param benutzerID
	 * @param anzahl
	 * @return True, falls es geklappt hat. False, falls nicht.
	 */
	public synchronized boolean insertAktie(String name, String kuerzel, double nominalpreis, double dividende, int benutzerID, int anzahl) {
		
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
		
		Connection con = connectionPooling.getConnection();
		try {

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
			connectionPooling.putConnection(con);
			}
		
			return false;
	}
	
	/**
	 * Holt alle Aktien eines Users.
	 * @author : Dennis Gehrig
	 * @param benutzerID
	 * @return Liste mit Aktien für die Portfolio-Ansicht.
	 */
	public synchronized ArrayList<AktieModel> getAktieByUserId(int benutzerID){
		//to do: Aktien anhand von BenutzerID auslesen und in eine Liste speichern.
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
		
		Connection con = connectionPooling.getConnection();
		try{
			UserModel u = new UserModel().getUserObjectFromSession();
			benutzerID = u.getBenutzerID();
			AuftragDAO auftragDao = new AuftragDAO();

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
				System.out.println(aktie);
				if(!auftragDao.isAktieInAuftrag(aktie)){
					portFolioList.add(aktie);
				}
				System.out.println("name:" + aktie.getDividende());
			}
			
			System.out.println(portFolioList.toArray().toString());
			
			preparedStatement.close();
			connectionPooling.putConnection(con);	
		} catch(SQLException sqle){
			System.out.println("Es trat ein Fehler im SQL auf.");
			sqle.printStackTrace();
			connectionPooling.putConnection(con);
		}
		
		return portFolioList;
	}
	
	/**
	 * Holt eine Aktien mit der mitgegebenen AktienID.
	 * @author : Dennis Gehrig
	 * @param aktieId
	 * @return Aktie, falls keine Exception geworfen wird.
	 */
	public AktieModel getAktieById(int aktieId){
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
		Connection con = connectionPooling.getConnection();
		try{
			
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
			connectionPooling.putConnection(con);
		}
			return null;
	}
	
	/**
	 * Holt die Anzahl Aktien, mit dem gleichen Kuerzel.
	 * @param kuerzel
	 * @return Anzahl der gesuchten Aktien.
	 */
	public int getAnzahlAktieBySymbol(String kuerzel){
		anzahlAktien = 0;
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
		Connection con = connectionPooling.getConnection();
		try{
			UserModel u = new UserModel().getUserObjectFromSession();
			System.out.println("Connection: " + con);
			PreparedStatement preparedStatement = con.prepareStatement("SELECT aktienId FROM aktie WHERE kuerzel = ? AND fk_benutzerId = ?");
			preparedStatement.setString(1, kuerzel);
			preparedStatement.setInt(2, u.getBenutzerID());
			
			ResultSet rs = preparedStatement.executeQuery();
						while(rs.next()){
				anzahlAktien ++;
			}
		connectionPooling.putConnection(con);
		
		} catch(SQLException sqle){
			System.out.println("Es trat ein Fehler im SQL auf.");
			sqle.printStackTrace();
			connectionPooling.putConnection(con);
		}
		
		return anzahlAktien;
	}
	
	/**
	 * Aktualisiert die Dividende
	 * @param kuerzel
	 * @param neueDividende
	 */
	public void updateLetzteDividende(String kuerzel, int neueDividende){
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
		
		Connection con = connectionPooling.getConnection();
		try{	
			
			PreparedStatement preparedStatement = con.prepareStatement("UPDATE aktie SET dividende=? WHERE kuerzel=?");
			preparedStatement.setInt(1, neueDividende);
			preparedStatement.setString(2, kuerzel);
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connectionPooling.putConnection(con);
			
			System.out.println("UPDATED Aktie: " + kuerzel + " mit neuer Dividende: " + neueDividende);
		} catch(SQLException sqlEx){
			sqlEx.printStackTrace();
			connectionPooling.putConnection(con);
		}
	}
	
	/**
	 * Holt alle Aktien mit der mitgegebenen UserID und dem Kürzel.
	 * @param benutzerID
	 * @param kuerzel
	 * @return Liste aller gesuchten Aktien.
	 */
	public synchronized ArrayList<AktieModel> getAktieByUserIdWithKuerzel(int benutzerID, String kuerzel){
		//to do: Aktien anhand von BenutzerID auslesen und in eine Liste speichern.
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
		
		Connection con = connectionPooling.getConnection();
		try{
			UserModel u = new UserModel().getUserObjectFromSession();
			benutzerID = u.getBenutzerID();
			AuftragDAO auftragDao = new AuftragDAO();

			PreparedStatement preparedStatement = con.prepareStatement("SELECT AktienID, dividende, fk_benutzerID, kuerzel, name, nominalpreis FROM mytrade.aktie WHERE fk_benutzerID = ? AND kuerzel=?");
			preparedStatement.setInt(1, benutzerID);
			preparedStatement.setString(2, kuerzel);
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
				if(!auftragDao.isAktieInAuftrag(aktie)){
					portFolioList.add(aktie);
				}
				System.out.println("name:" + aktie.getDividende());
			}
			
			System.out.println(portFolioList.toArray().toString());
			
			preparedStatement.close();
		} catch(SQLException sqle){
			System.out.println("Es trat ein Fehler im SQL auf.");
			sqle.printStackTrace();
		}
		connectionPooling.putConnection(con);	
		return portFolioList;
	}

}
