package com.cognixia.jump.dao;

public class UserMovie {
	private int userId;
	private int movieId;
	private String movie;
	
	public UserMovie(int userId, int movieId, String movie) {
		super();
		this.userId = userId;
		this.movieId = movieId;
		this.movie = movie;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	@Override
	public String toString() {
		return "UserMovie [userId=" + userId + ", movieId=" + movieId + ", movie=" + movie + "]";
	}
	
}
