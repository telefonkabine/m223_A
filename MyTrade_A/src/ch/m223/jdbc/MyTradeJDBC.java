package ch.m223.jdbc;

import java.sql.*;

public class MyTradeJDBC {

		
		private String treiberName = "com.mysql.jdbc.Driver";
		private String connectionURL = "jdbc:mysql://192.168.1.62/mytrade";


	public boolean accountExistiert(String user, String password) {
		
		return top(user, password);
	}

	public boolean top(String user, String password) {
		try {
			Class.forName(treiberName);

			Connection con;
			con = DriverManager.getConnection(connectionURL, "MyTradeUser", "123456");


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

		} catch (ClassNotFoundException e) {
			System.out.println("Fehler beim Verbindungsaufbau");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Es trat ein Fehler mit SQL auf");
			e.printStackTrace();
		}
		return false;
	}

}
