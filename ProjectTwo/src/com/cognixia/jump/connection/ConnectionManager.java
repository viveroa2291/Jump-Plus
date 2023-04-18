package com.cognixia.jump.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
	
		private static Connection connection;
		
		private static void makeConnection() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
			Properties props = new Properties();
			
			props.load(new FileInputStream("resources/config.properties"));
			
			String url = props.getProperty("url");
			String username = props.getProperty("username");
			String password = props.getProperty("password");
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
		}
		
		public static Connection getConnection() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
			if(connection == null) {
				System.out.println("Connecting...");
				makeConnection();
			}
			// System.out.println("Connection Successful!");

			return connection;
		}
		public static void main(String[] args) {
			System.out.println("Welcome to Student Grade Book!");
			System.out.println("Establishing db connection...");
			try {
				Connection connection = ConnectionManager.getConnection();
				System.out.println("Connection made to the Student Grade Book database");
			} catch (FileNotFoundException e) {
				System.out.println("Couldn't load detail for connection, can't make connection");
			} catch (ClassNotFoundException e) {
				System.out.println("Couldn't load driver, can't make connection");
			} catch (IOException e) {
				System.out.println("Couldn't load connection details, can't make connection");
			} catch (SQLException e) {
				System.out.println("Couldn't connect to the db");
			}
		}
}