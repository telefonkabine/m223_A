/**
 * @author : Jason Angst, Dennis Gehrig
 * @date   : 28.05.2015
 * @version: 1.0
 * 
 * **/
package ch.m223.dao;

import java.sql.*;

import ch.m223.connectionPooling.ConnectionPooling;
import ch.m223.connectionPooling.ConnectionPoolingImplementation;
import ch.m223.model.UserModel;

public class UserDAO {
	

	public boolean accountExistiert(String user, String password) {
		
		
		return login(user, password);
	}
	
	public boolean login(String user, String password) {
		try {
			ConnectionPooling connectionPooling;
			connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
			
			Connection con = connectionPooling.getConnection();

			
			PreparedStatement preparedStatement = con.prepareStatement("SELECT login, passwort FROM benutzer WHERE login = ? AND passwort = MD5(?)");
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			 
			int count = 0;
			
			while (rs.next()) {
				
				count++;
				
				if (count > 1) {
					System.out.println("Es gibt mehr als einen Benutzer: " + user);
					return false;	
				} else if (count == 1){
				return true;
				}	
				
			}
		
				rs.close();
				preparedStatement.close();
				con.close();
			

		} catch (SQLException e) {
			System.out.println("Es trat ein Fehler mit SQL auf");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @author Dennis Gehrig
	 * @param vorname
	 * @param name
	 * @param loginname
	 * @param passwort
	 * @param fk_typID
	 * @param kontostand
	 * @return true if the user was created otherwise false
	 */
	public synchronized boolean insertUser(UserModel user){
		if(getUserByLogin(user.getLogin()) == null){
			System.out.println("return null");
			try{
				
				ConnectionPooling connectionPooling;
				connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
				
				Connection con = connectionPooling.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO benutzer (Name, Vorname, Login, Passwort, Fk_TypID, Kontostand) "
						                                                 + "VALUES (?, ?, ?, SHA1(?), ?, ?)");
				
				preparedStatement.setString(1, user.getName());
				preparedStatement.setString(2, user.getVorname());
				preparedStatement.setString(3, user.getLogin());
				preparedStatement.setString(4, user.getPasswort());
				preparedStatement.setInt(5, user.getFk_typID());
				preparedStatement.setInt(6, user.getKontostand());
				int rows = preparedStatement.executeUpdate();
				
				preparedStatement.close();
				con.close();
				if(rows == 1){
					return true;
				}
			} catch(SQLException sqle){
				System.out.println("Es trat ein Fehler mit SQL auf");
				sqle.printStackTrace();
			}
		}
		else{
			System.out.println("Es gibt bereits einen Benutzer mit diesem Login.");
		}
		return false;
	}

	
	/**
	 * @author Dennis Gehrig
	 * @param login
	 * @return UserModel object or null if no user was found in the db
	 */
	public UserModel getUserByLogin(String login){
		try{
			
			ConnectionPooling connectionPooling;
			connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
			
			Connection con = connectionPooling.getConnection();
			
			PreparedStatement preparedStatement = con.prepareStatement("SELECT benutzerID, name, vorname, login, passwort, fk_typID, kontostand FROM benutzer WHERE login = ?");
			preparedStatement.setString(1, login);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			UserModel user = new UserModel();
			System.out.println("testgetuser");
			if(rs.next()){
				System.out.println(rs.getString("login"));
				user.setBenutzerID(rs.getInt("benutzerID"));
				user.setName(rs.getString("name"));
				user.setVorname((rs.getString("vorname")));
				user.setLogin(rs.getString("login"));
				user.setFk_typID(rs.getInt("fk_typID"));
				user.setKontostand(rs.getInt("kontostand"));
				return user;
			}
			
		} catch(SQLException sqle){
			System.out.println("Es trat ein Fehler im SQL auf.");
			sqle.printStackTrace();
		}
		return null;
	}
	
}
