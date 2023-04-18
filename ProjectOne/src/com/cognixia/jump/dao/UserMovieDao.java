package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public interface UserMovieDao {

	public boolean getAverageRating(int movie); 

	void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
}
