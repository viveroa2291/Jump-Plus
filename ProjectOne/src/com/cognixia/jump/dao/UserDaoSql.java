package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.cognixia.jump.connection.ConnectionManager;

public class UserDaoSql implements UserDao {

	
	private Connection conn;
	
	@Override
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		conn = ConnectionManager.getConnection();
	}
	
	@Override 
	public Optional<User> validateUser(String username, String password) {
		try (PreparedStatement pstmt = conn.prepareStatement("select * from user where username = ? and password = ?");) {
			
			pstmt.setString(1,  username);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			
			if( rs.next() ) {
	        	  int userId = rs.getInt("user_id");
	        	  String firstName = rs.getString("first_name");
	        	  String lastName = rs.getString("last_name");
	        	  String usrname = rs.getString("username");
	        	  String pass = rs.getString("password");
	        	  
	        	  rs.close();
	        	  
	        	  // Creating the User object
	        	  User user = new User(userId, firstName, lastName, usrname, pass);
	        	  
	        	  // placing it in the Optional
	        	  Optional<User> userFound = Optional.of(user);
	        	  
	        	  return userFound;
	          } else {
	        	  rs.close();
	        	  return Optional.empty();
	          }
	          
	          
		} catch (SQLException e) {
			System.out.println("Cannot verify user due to connection issues");
			//e.printStackTrace();
			return Optional.empty();
		}
		}
	@Override
	public boolean createUser(User user) {
		try( PreparedStatement pstmt = conn.prepareStatement("insert into user(firstName, lastName, username, password)"
				+ "values (?, ?, ?, ?)");) {
			pstmt.setString(1, user.getFirstName());
			pstmt.setString(2, user.getLastName());
			pstmt.setString(3, user.getUsername());
			pstmt.setString(4, user.getPassword());
			
			
			int count = pstmt.executeUpdate();
			if(count > 0) {
				//Success if its inserted into the table
				return true;
			}
			
		} catch(SQLException e) {
			// Uncomment if problems occur
			// e.printStackTrace();
			return false;
		}
		return false;
	}
}
