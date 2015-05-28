package ch.m223.connectionPooling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Connection Pool implementation.
 * MIN_CONNECTIONS are always built: Typically 1 (or 0: lazy instantiation).
 * More than MAX_CONNECTIONS will never be initialized.
 * @version 0.1 (May 27, 2015)
 * @author Philipp Gressly Freimann 
 *         (philipp.gressly@santis.ch)
 */
public class ConnectionPoolingImplementation implements ConnectionPooling {

	private final int MIN_CONNECTIONS;
	private final int MAX_CONNECTIONS;

	private List<Connection> freePool;
	private List<Connection> busyPool;

	// TODO: Dependency inject (Property file)
	final String treiberName   = "com.mysql.jdbc.Driver";
	// TODO: Property file
	final String connectionURL = "jdbc:mysql://192.168.1.62/mytrade"; 

	// TODO: Security: store username and pwd externally
	private String myUserName    = "MyTradeUser";
	private String myPassword    = "123456";


	private ConnectionPoolingImplementation(int min, int max) throws ClassNotFoundException, SQLException {
		Class.forName(treiberName);

		MIN_CONNECTIONS = min;
		MAX_CONNECTIONS = max;
		initConnections();
	}


	private void initConnections() throws SQLException {
		freePool = new LinkedList<Connection>();
		busyPool = new LinkedList<Connection>();
		for(int i = 0; i < MIN_CONNECTIONS; i++) {
			Connection con;
			con = createConnection();
			freePool.add(con);
		}
	}


	/**
	 * Create a SQL-Connection object
	 */
	private Connection createConnection() throws SQLException {
		Connection con;
		con = DriverManager.getConnection(connectionURL, 
		    myUserName, myPassword);
		return con;
	}

/**
 * Singleton-Pattern
 */
	private static ConnectionPoolingImplementation theInstance = null;


	public static synchronized ConnectionPoolingImplementation getInstance(int min, int max) {
		if(null == theInstance) {
			try {
				theInstance = new ConnectionPoolingImplementation(min, max);
			} catch (ClassNotFoundException e) {
				System.out.println("Exception in getInstance: " + e);
				e.printStackTrace();
				return null;
			} catch (SQLException sqex) {
				System.out.println("Exception in getInstance: " + e);
				e.printStackTrace();
				return null;
			}
		}
		return theInstance;
	}


	@Override
	public synchronized Connection getConnection() {
		Connection con = null;
		if(0 < freePool.size()) {
			con = freePool.remove(0);
			busyPool.add(con);
			return con;
		}
		// no more free connections:
		if(totalConnectionCount() >= MAX_CONNECTIONS) {
			System.out.println("No more space in the pools. MAX (" +
		                     MAX_CONNECTIONS + ") exceeded.");
			return null;
		}
		try {
			con = createConnection();
		} catch (SQLException e) {
			System.out.println("Error (ConnectionPoolImplementation.getConnection()): " + e);
			e.printStackTrace();
			return null;
		}
		busyPool.add(con);
		return con;
	}


	@Override
	public synchronized void putConnection(Connection con) {
		//TODO: Check, if con = null
		if(busyPool.remove(con)) {
			freePool.add(con);
			return;
		}
		System.out.println("Error: Connection was not `busy`.");
	}


	private int totalConnectionCount() {
		return freePool.size() + busyPool.size();
	}

	/**
	 * Debug purpuse. 
	 */
	@Override
	public String toString() {
		return "ConnectionPooling. Free: " + freePool.size() +
		       " Busy: " + busyPool.size();
	}
}
 // end of class ConnectionPoolingImplementation