package ch.m223.dao;

import java.sql.*;

import ch.m223.connectionPooling.ConnectionPooling;
import ch.m223.connectionPooling.ConnectionPoolingImplementation;

public class UserDAO {


	public boolean accountExistiert(String user, String password) {
		
		return login(user, password);
	}

	public boolean login(String user, String password) {
		try {
			ConnectionPooling connectionPooling;
			connectionPooling = ConnectionPoolingImplementation.getInstance(1, 10);
			
			Connection con = connectionPooling.getConnection();


			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT login, passwort FROM benutzer WHERE login = '" + user + "' AND passwort = MD5('" + password + "')");

			int count = 0;
			
			while (rs.next()) {
				
				count++;
				
				if (count > 1) {
					System.out.println("Es gibt mehr als einen Benutzer: " + user);
					return false;
				}
			}
			
			rs.close();
			stmt.close();
			con.close();
			
			return true;

		} catch (SQLException e) {
			System.out.println("Es trat ein Fehler mit SQL auf");
			e.printStackTrace();
		}
		return false;
	}

}