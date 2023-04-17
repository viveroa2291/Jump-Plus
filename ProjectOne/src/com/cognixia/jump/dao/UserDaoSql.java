package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
		try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM USER WHERE username = ? AND password = ?");) {
			
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
	public boolean createUser(String firstName, String lastName, String email, String username, String password) {
		try( PreparedStatement pstmt = conn.prepareStatement("INSERT INTO USER (first_name, last_name, email, username, password)"
				+ "VALUES (?, ?, ?, ?, ?)");) {
		
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, email);
			pstmt.setString(4, username);
			pstmt.setString(5, password);
			
			int count = pstmt.executeUpdate();
			if(count > 0) {
				//Success if its inserted into the table
				return true;
			}
			
		} catch(SQLException e) {
			// Uncomment if problems occur
			 e.printStackTrace();
			return false;
		}
		return false;
	}
	public List<UserMovie> getListOfMoviesTracked(User user) {
		List<UserMovie> moviesTracked = new ArrayList<>();
		try(PreparedStatement pstmt = conn.prepareStatement("select * from user_movie where user_id = ?");){
			pstmt.setInt(1, user.getId());
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int userId = rs.getInt("user_id");
				int movieId = rs.getInt("movie_id");
				String status = rs.getString("status");

				UserMovie tracked = new UserMovie(userId, movieId, status);
				
				// adding the movie into the movie list
				moviesTracked.add(tracked);
			}
			
			
		} catch(SQLException e) {
			// uncomment of you're running into issues and want to know what's
			// going on
//			e.printStackTrace();
		}
		return moviesTracked;
	}	
}
