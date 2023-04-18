package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cognixia.jump.connection.ConnectionManager;

public class MovieDaoSql implements MovieDao {

	private Connection conn;
	
	@Override 
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		conn = ConnectionManager.getConnection();
	}
	
	@Override 
	public List<Movie> getAllMovies() {
		List<Movie> movies = new ArrayList<>();
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from movies");) {
			while(rs.next()) {
				int id = rs.getInt("movie_id");
				String title = rs.getString("title");
				double rating = rs.getDouble("rating");
				
				Movie movie = new Movie(id, title, rating);
			
				movies.add(movie);
			}
		} catch(SQLException e) {
			// uncomment of you're running into issues and want to know what's going on
			 e.printStackTrace();
		}
		return movies;
	}
	@Override 
	public Optional<Movie> getMovieById(int id) {
		try( PreparedStatement pstmt = conn.prepareStatement("select * from movies where movie_id = ?") ) {
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int movieId = rs.getInt("movie_id");
				String title = rs.getString("title");
				double rating = rs.getDouble("rating");
				
				rs.close();
				
				Movie movie = new Movie(movieId, title, rating);
				
				Optional<Movie> movieFound = Optional.of(movie);
				
				return movieFound;
			}
			else {
				rs.close();
				
				return Optional.empty();
			}
		} catch(SQLException e) {
			// just in case an exception occurs, return nothing in the optional
			return Optional.empty();
		}	
	}
	@Override
	public boolean createMovie(String title, double rating) {
		try( PreparedStatement pstmt = conn.prepareStatement("INSERT INTO MOVIES (title, rating)"
				+ "VALUES (?, ?)");) {
			pstmt.setString(1, title);
			pstmt.setDouble(2, rating);
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

	@Override
	public boolean deleteMovie(int id) {
		try( PreparedStatement pstmt = conn.prepareStatement("delete from movies where movie_id = ?");) {
			pstmt.setInt(1, id);
			int count = pstmt.executeUpdate();
			if(count > 0) {
				// if its not deleted
				return true;
			}
		} catch(SQLException e) {
			return false;
		}
		return false;	}

	@Override
	public boolean updateMovieRating(double rating, int id) {
		try(PreparedStatement pstmt = conn.prepareStatement("update movies set rating = ?" 
				+ "where movie_id = ?");){
			pstmt.setDouble(1, rating);
			pstmt.setInt(2, id);
			
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
