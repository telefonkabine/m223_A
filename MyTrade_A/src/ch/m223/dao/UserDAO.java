/**
 * @author : Dennis Gehrig
 * @date   : 30.07.2015
 * @version: 1.0
 * 
 * **/

package ch.m223.dao;

import java.sql.*;

import javax.faces.context.FacesContext;

import ch.m223.connectionPooling.ConnectionPooling;
import ch.m223.connectionPooling.ConnectionPoolingImplementation;
import ch.m223.dividende.DividendenRechner;
import ch.m223.model.UserModel;

public class UserDAO {

	public boolean accountExistiert(String user, String password) {

		return login(user, password);
	}
	
	/**
	 * �berpr�ft die eingegebenen Login-Daten.  
	 * @author Dennis Gehrig
	 * @param user
	 * @param password
	 * @return True, falls die Daten Korrekt sind. False, falls nicht.
	 */
	public synchronized boolean login(String user, String password) {
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);

		Connection con = connectionPooling.getConnection();
		try {

			PreparedStatement preparedStatement = con.prepareStatement("SELECT login, passwort, benutzerID FROM benutzer "
																	  + "WHERE login = ? AND passwort = SHA1(?)");
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();

			int count = 0;

			while (rs.next()) {
				int benutzerID = rs.getInt("benutzerID");
				count++;

				if (count > 1) {
					System.out.println("Es gibt mehr als einen Benutzer: "+ user);
					return false;
				} else if (count == 1) {

					FacesContext context = FacesContext.getCurrentInstance();
					context.getExternalContext().getSessionMap()
							.put("id", "" + benutzerID);
					System.out.println(benutzerID);
					return true;
				}

			}

			rs.close();
			preparedStatement.close();
			connectionPooling.putConnection(con);

		} catch (SQLException e) {
			System.out.println("Es trat ein Fehler mit SQL auf");
			e.printStackTrace();
			connectionPooling.putConnection(con);
		}
		return false;
	}

	/**
	 * F�gt einen Neuen User in die Datenbank ein. 
	 * @author Dennis Gehrig
	 * @param vorname
	 * @param name
	 * @param loginname
	 * @param passwort
	 * @param fk_typID
	 * @param kontostand
	 * @return True, falls ein User erstellt worden ist. Falls, falls nicht.
	 */
	public synchronized boolean insertUser(UserModel user) {

		if (getUserByLogin(user.getLogin()).getLogin() == null) {
			System.out.println("return null");
			ConnectionPooling connectionPooling;
			connectionPooling = ConnectionPoolingImplementation.getInstance(1,
					10);

			Connection con = connectionPooling.getConnection();
			try {

				PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO benutzer (Name, Vorname, Login, Passwort, Fk_TypID, Kontostand) "
								                    					 + "VALUES (?, ?, ?, SHA1(?), ?, ?)");
				System.out.println(user.getFk_typID());
				preparedStatement.setString(1, user.getName());
				preparedStatement.setString(2, user.getVorname());
				preparedStatement.setString(3, user.getLogin());
				preparedStatement.setString(4, user.getPasswort());
				preparedStatement.setInt(5, user.getFk_typID());
				preparedStatement.setDouble(6, user.getKontostand());
				int rows = preparedStatement.executeUpdate();

				preparedStatement.close();
				connectionPooling.putConnection(con);
				if (rows == 1) {
					return true;
				}
			} catch (SQLException sqle) {
				System.out.println("Es trat ein Fehler mit SQL auf");
				sqle.printStackTrace();
				connectionPooling.putConnection(con);
			}
		} else {
			System.out
					.println("Es gibt bereits einen Benutzer mit diesem Login.");
		}
		return false;
	}

	/**
	 * Holt ein User-Objekt von der Datenbank.
	 * @author Dennis Gehrig
	 * @param login
	 * @return User-Objekt, falls der User in der Datenbank vorhanden ist.
	 */
	public synchronized UserModel getUserByLogin(String login) {
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);

		Connection con = connectionPooling.getConnection();
		try {

			System.out.println("Connection: " + con);
			PreparedStatement preparedStatement = con.prepareStatement("SELECT benutzerID, name, vorname, "
												+ "login, passwort, fk_typID, kontostand "
												+ "FROM benutzer WHERE login = ?");
			preparedStatement.setString(1, login);

			ResultSet rs = preparedStatement.executeQuery();

			UserModel user = new UserModel();
			System.out.println("testgetuser");
			if (rs.next()) {
				System.out.println(rs.getString("login"));
				user.setBenutzerID(rs.getInt("benutzerID"));
				user.setName(rs.getString("name"));
				user.setVorname((rs.getString("vorname")));
				user.setLogin(rs.getString("login"));
				user.setFk_typID(rs.getInt("fk_typID"));
				user.setKontostand(rs.getInt("kontostand"));
			}
			connectionPooling.putConnection(con);
			System.out.println(user.getLogin());
			return user;

		} catch (SQLException sqle) {
			System.out.println("Es trat ein Fehler im SQL auf.");
			sqle.printStackTrace();
			connectionPooling.putConnection(con);
		}
		return null;
	}

	/**
	 * @author Dennis Gehrig
	 * @param userId
	 * @return User-Objekt, falls der User in der Datenbank vorhanden ist.
	 */
	public synchronized UserModel getUserById(int userId) {
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);

		Connection con = connectionPooling.getConnection();
		try {
			PreparedStatement preparedStatement = con.prepareStatement("SELECT benutzerID, name, vorname, "
												+ "login, passwort, fk_typID, kontostand "
												+ "FROM benutzer WHERE benutzerId = ?");
			preparedStatement.setInt(1, userId);

			ResultSet rs = preparedStatement.executeQuery();

			UserModel user = new UserModel();
			System.out.println("testgetuser");
			if (rs.next()) {
				System.out.println(rs.getString("login"));
				user.setBenutzerID(rs.getInt("benutzerID"));
				user.setName(rs.getString("name"));
				user.setVorname((rs.getString("vorname")));
				user.setLogin(rs.getString("login"));
				user.setFk_typID(rs.getInt("fk_typID"));
				user.setKontostand(rs.getInt("kontostand"));
				// return user;
			}
			connectionPooling.putConnection(con);
			System.out.println(user.getLogin());
			return user;

		} catch (SQLException sqle) {
			System.out.println("Es trat ein Fehler im SQL auf.");
			sqle.printStackTrace();
			connectionPooling.putConnection(con);
		}
		return null;
	}

	public void dividendeAnBenutzer() {
		ConnectionPooling connectionPooling;
		connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);

		Connection con = connectionPooling.getConnection();
		try {
			PreparedStatement preparedStatement = con
					.prepareStatement("SELECT DISTINCT kuerzel, dividende FROM aktie");
			ResultSet rs = preparedStatement.executeQuery();

			AktieDAO aktieDao = new AktieDAO();
			int neueDividende;
			while (rs.next()) {
				neueDividende = DividendenRechner.neueDividende(
						rs.getInt("dividende"),
						DividendenRechner.MITTLERE_STREUUNG, 20, 666);
				aktieDao.updateLetzteDividende(rs.getString("kuerzel"),
						neueDividende);

				preparedStatement = con.prepareStatement("UPDATE benutzer "
								  + "INNER JOIN aktie ON benutzer.benutzerId=aktie.fk_benutzerId "
								  + "SET kontostand=kontostand+? "
							      + "WHERE aktie.kuerzel=?");
				preparedStatement.setDouble(1, neueDividende);
				preparedStatement.setString(2, rs.getString("kuerzel"));
				preparedStatement.executeUpdate();
		

			preparedStatement.close();
			connectionPooling.putConnection(con);

			}
			
			} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			connectionPooling.putConnection(con);
		}
	}

}
