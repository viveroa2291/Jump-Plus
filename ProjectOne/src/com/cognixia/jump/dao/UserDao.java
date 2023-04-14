package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public interface UserDao {

	public Optional<User> validateUser(String username, String password);
	
	public List<UserMovie>(User user, int movie_id, String movie);
	
	public boolean createUser(User user);	
	
	void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;

}
