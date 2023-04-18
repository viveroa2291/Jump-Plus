package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface MovieDao {
	
		public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
		
		public List<Movie> getAllMovies();
		
		public List<Double> getAverageRating(int movieId);
		
		public List<Integer> getNumberRating(int movieId); 
		
		public Optional<Movie> getMovieById(int id);
		
		public boolean createMovie(String title, double rating);
		
		public boolean deleteMovie(int id);
		
		public boolean updateMovieRating(double rating, int id);
		
}
