package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao {

	public Optional<User> validateUser(String username, String password);
	
	public List<UserMovie> getListOfMoviesTracked(User user);
	
	public boolean createUser(String firstName, String lastName, String email, String username, String password);	
	
	void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;

}
