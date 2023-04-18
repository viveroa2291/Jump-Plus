package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.cognixia.jump.connection.ConnectionManager;

public class UserMovieDaoSql implements UserMovieDao {
	
	private Connection conn;
	
	@Override 
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		conn = ConnectionManager.getConnection();
	}

	public boolean getAverageRating(int movie) {
	try( PreparedStatement pstmt = conn.prepareStatement("SELECT AVG(rating) AS avg_rating, COUNT(*) AS num_ratings "
			+ "FROM user_movie WHERE movie_id = ?;");) 
		{
		pstmt.setInt(1, movie);
		
		int count = pstmt.executeUpdate();
		if(count > 0) {
			return true;
		}
	} catch(SQLException e) {
		return false;
	}
	return false;	
	}
}