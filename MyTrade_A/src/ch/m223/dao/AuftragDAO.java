/**
 * @author : Dennis Gehrig
 * @date   : 30.07.2015
 * @version: 1.0
 * 
 * **/

package ch.m223.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ch.m223.connectionPooling.ConnectionPooling;
import ch.m223.connectionPooling.ConnectionPoolingImplementation;
import ch.m223.model.AktieModel;
import ch.m223.model.AuftragModel;
import ch.m223.model.UserModel;

public class AuftragDAO {
	
	/**
	 * @author : Dennis Gehrig
	 * F�gt einen neuen Auftrag in die Datenbank ein.
	 * @param preis
	 * @param kuerzel
	 * @param anzahl
	 * @return AuftragId's, falls der Insert geklappt hat. Sonst Null.
	 */
	public synchronized int[] insertAuftrag(double preis, String kuerzel, long anzahl) {
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
		
		Connection con = connectionPooling.getConnection();
		try {
			UserModel u = new UserModel().getUserObjectFromSession();
			int aktieId;
			AktieDAO aktieDao = new AktieDAO();
			
			ArrayList<AktieModel> aktienVonUser = aktieDao.getAktieByUserIdWithKuerzel(u.getBenutzerID(), kuerzel);
			String insertTableSQL = "INSERT INTO auftrag (Preis, Fk_AktienID) "
                    			  + "VALUES (?, ?)";
			
				PreparedStatement preparedStatement = con.prepareStatement(insertTableSQL);
				preparedStatement.setDouble(1, preis);
				
				int i = 0;
				int maxIndex = aktienVonUser.size() - 1;
				int[] auftragIds = new int[(int) anzahl];
				while (anzahl>0 && i<=maxIndex){
					anzahl = anzahl -1;
					aktieId = aktienVonUser.get(i).getAktienId();
					preparedStatement.setInt(2, aktieId);
					preparedStatement.executeUpdate();
					auftragIds[i] = aktieId;
					i++;
				}
				
			preparedStatement.close();
			connectionPooling.putConnection(con);	
			
			return auftragIds;
		
			} catch (SQLException e) {
			System.out.println("Es trat ein Fehler mit SQL auf");
			e.printStackTrace();
			connectionPooling.putConnection(con);
			}
		
			return null;
	}
	
	/**
	 * Holt alle Aktien mit der mitgegebenen ID aus der Datenbank.
	 * @author : Dennis Gehrig
	 * @param aktienId
	 * @return Auftrag mit den entsprechenden Aktien.
	 */
	public AuftragModel getAuftragByAktienId(int aktienId){
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
		
		Connection con = connectionPooling.getConnection();
		
		AuftragModel auftrag = new AuftragModel();
		try {
			
			PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM auftrag WHERE fk_aktienId = ?");
			preparedStatement.setInt(1, aktienId);
			ResultSet rs = preparedStatement.executeQuery();
			
			AktieModel aktie = new AktieModel();
			AktieDAO aktieDao = new AktieDAO();

			UserModel u = new UserModel().getUserObjectFromSession();
			if(rs.next()){
				auftrag.setAuftragId(rs.getInt("auftragId"));
				auftrag.setPreis(rs.getInt("preis"));
				auftrag.setFk_AtkienID(rs.getInt("fk_aktienId"));
				aktie = aktieDao.getAktieById(auftrag.getFk_AtkienID());
				auftrag.setName(aktie.getName());
				auftrag.setSymbol(aktie.getKuerzel());
				auftrag.setUser(aktie.getFk_benutzerId() == u.getBenutzerID());
			}
			
			preparedStatement.close();
			connectionPooling.putConnection(con);
			return auftrag;
		
		} catch(SQLException sqlEx){
			connectionPooling.putConnection(con);
			sqlEx.printStackTrace();
		}
		connectionPooling.putConnection(con);
		return null;
	}
	
	/**
	 * Holt alle Auftr�ge aus der Datenbank.
	 * @author : Dennis Gehrig
	 * @return Alle aktuellen Auftr�ge.
	 */
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

			UserModel u = new UserModel().getUserObjectFromSession();
			while(rs.next()){
				auftrag = new AuftragModel();
				auftrag.setAuftragId(rs.getInt("auftragId"));
				auftrag.setPreis(rs.getInt("preis"));
				auftrag.setFk_AtkienID(rs.getInt("fk_aktienId"));
				aktie = aktieDao.getAktieById(auftrag.getFk_AtkienID());
				auftrag.setName(aktie.getName());
				auftrag.setSymbol(aktie.getKuerzel());
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
	
	/**
	 * L�scht einen Auftrag mit 
	 * @author : Dennis Gehrig
	 * @param auftragId
	 */
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
	
	/**
	 * Kauft Aktien und aktualisiert den Besitzer der Aktie und die Kontost�nde der beiden Beteiligten.
	 * @author : Dennis Gehrig
	 * @param auftragModel
	 */
	public void doKaufen(AuftragModel auftragModel){
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
		
		Connection con = connectionPooling.getConnection();
		try{
			UserModel u = new UserModel().getUserObjectFromSession();
			System.out.println("doKaufen, User: " + u + "auftragModel.getPreis: " + auftragModel.getPreis());
			double neuerKontostand = u.getKontostand() - auftragModel.getPreis();
			int k�uferId = u.getBenutzerID();
			PreparedStatement preparedStatement;
			
			preparedStatement = con.prepareStatement("UPDATE benutzer SET kontostand=? WHERE benutzerId=?");
			preparedStatement.setDouble(1, neuerKontostand);
			preparedStatement.setInt(2, k�uferId);
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
			preparedStatement.setInt(1, k�uferId);
			preparedStatement.setInt(2, aktieModel.getAktienId());
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connectionPooling.putConnection(con);	

		} catch(SQLException sqlEx){
			sqlEx.printStackTrace();
			connectionPooling.putConnection(con);
		}
	}
	
	/**
	 * 
	 * @author : Dennis Gehrig
	 * @param aktie
	 * @return True, falls sich die Aktie in einem Auftrag befindet. False, falls nicht.
	 */
	public boolean isAktieInAuftrag(AktieModel aktie){
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
		
		Connection con = connectionPooling.getConnection();
		try{	
			
			PreparedStatement preparedStatement = con.prepareStatement("SELECT fk_aktienId FROM auftrag WHERE fk_aktienId=?");
			preparedStatement.setInt(1, aktie.getAktienId());
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()){
				connectionPooling.putConnection(con);
				return true;
			}
			
			preparedStatement.close();
			connectionPooling.putConnection(con);
			return false;

		} catch(SQLException sqlEx){
			sqlEx.printStackTrace();
			connectionPooling.putConnection(con);
		}
		return false;
	}
}
