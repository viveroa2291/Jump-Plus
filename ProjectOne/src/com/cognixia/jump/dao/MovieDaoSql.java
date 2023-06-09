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
	public List<Double> getAverageRating(int movieId) {
	    List<Double> avgRating = new ArrayList<>();
	    String sql = "SELECT AVG(rating) AS avg_rating, COUNT(*) AS num_ratings "
	            + "FROM user_movie WHERE movie_id = ?;";
	    
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, movieId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                double averageRating = rs.getDouble("avg_rating");
	                int numRatings = rs.getInt("num_ratings");
	                // Do something with the results
	                avgRating.add(averageRating);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return avgRating;
	}
	
	@Override 
	public List<Integer> getNumberRating(int movieId) {
	    List<Integer> numberRating = new ArrayList<>();
	    String sql = "SELECT AVG(rating) AS avg_rating, COUNT(*) AS num_ratings "
	            + "FROM user_movie WHERE movie_id = ?;";
	    
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, movieId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                double averageRating = rs.getDouble("avg_rating");
	                int numRatings = rs.getInt("num_ratings");
	             
	                numberRating.add(numRatings);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return numberRating;
	}
	
	@Override 
	public List<String> getMovieTitle(int movieId) {
	    List<String> titles = new ArrayList<>();
	    String sql = "SELECT title FROM movies WHERE movie_id = ?;";
	    
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, movieId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                String title = rs.getString("title");
	                titles.add(title);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return titles;
	}
	
	@Override
	public int numberOfMovies() {
	    int count = 0;
	    try (Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM movies")) {
	        if (rs.next()) {
	            count = rs.getInt("count");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return count;
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
	public boolean updateMovieRating(double rating, int movieId, int userId) {
	    try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user_movie(user_id, movie_id, rating) VALUES (?, ?, ?)")) {
	        pstmt.setInt(1, userId);
	        pstmt.setInt(2, movieId);
	        pstmt.setDouble(3, rating);
	        int count = pstmt.executeUpdate();
	        if (count > 0) {
	            try (PreparedStatement pstmt2 = conn.prepareStatement("UPDATE movies SET rating = (SELECT AVG(rating) FROM user_movie WHERE movie_id = ?) WHERE movie_id = ?")) {
	                pstmt2.setInt(1, movieId);
	                pstmt2.setInt(2, movieId);
	                count = pstmt2.executeUpdate();
	                if (count > 0) {
	                    return true;
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
}
