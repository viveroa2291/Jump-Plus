package com.cognixia.jump.movie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.cognixia.jump.dao.Movie;
import com.cognixia.jump.dao.MovieDao;
import com.cognixia.jump.dao.MovieDaoSql;
import com.cognixia.jump.dao.User;
import com.cognixia.jump.dao.UserDao;
import com.cognixia.jump.dao.UserDaoSql;
import com.cognixia.jump.dao.UserMovie;
import com.cognixia.jump.dao.UserMovieDao;
import com.cognixia.jump.dao.UserMovieDaoSql;

public class MovieRating {
	
	private static List<UserMovie> movieList;
	private static MovieDao movieDao;	
		
	private static User userFound;
	private static UserDao userDao;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		movieDao = new MovieDaoSql();
		
		
		int option = menu(sc);
		try {
			movieDao.setConnection();
			List<Movie> moviesList = movieDao.getAllMovies();
			while(option != 4) {
				if(option == 1) {
				register();
				 option = newMenu(sc);
				}
				else if(option == 2) {
					login();
					option = newMenu(sc);
				}
				else if(option == 3) {
					viewMovie(moviesList);
					option = newMenu(sc);
				}
				else if(option == 5) {
					addMovie();
					option = newMenu(sc);
				}
				else if(option == 6) {
					addRating();
					option = newMenu(sc);
				}
				else {
					System.out.println("That is not an option. Please select 1-4.");
					option = newMenu(sc);
				}
			} 
			} catch (ClassNotFoundException | IOException | SQLException | InputMismatchException e) {
				System.out.println("Please select an integer.");
				option = menu(sc);
		}
		exit();
		sc.close();
	}
	public static int menu(Scanner sc) {
	
		System.out.println("+========================================================+");
		System.out.println("| 1. REGISTER    |");
		System.out.println("| 2. LOGIN       |");
		System.out.println("| 3. VIEW MOVIES |");
		System.out.println("| 4. EXIT        |");
		System.out.println("+========================================================+");
		
		return sc.nextInt();
	}
	public static int newMenu(Scanner sc) {
		System.out.println("+========================================================+");
		System.out.println("| 1. Add Movie   			|");
		System.out.println("| 2. Add Rating  			|");
		System.out.println("| 3. VIEW MOVIES 			|");
		System.out.println("| 4. EXIT        			|");
		System.out.println("+========================================================+");
		int choice = sc.nextInt();
		if(choice == 1) {
			return 5;
		}
		else if(choice == 2) {
			return 6;
		}
		else {
			return choice;
		}
	}
	public static void register() {
		UserDao user1 = new UserDaoSql();
		
		try {
			user1.setConnection();
		} catch(ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}
	
		Scanner sc = new Scanner(System.in);
		String firstName, lastName, email, username, password, confirmPassword;
		System.out.println("You selected to Register a user!");
		
		System.out.print("Enter your first name: ");
		firstName = sc.next();
		System.out.print("Enter your last name: ");
		lastName = sc.next();

		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		   boolean isValidEmail = false;
		   do {
		       System.out.println("Enter your email address: ");
		       email = sc.next();
		       isValidEmail = email.matches(emailRegex);
		       if (!isValidEmail) {
		           System.out.println("Invalid email format. Please try again.");
		       }
		   } while (!isValidEmail);
		
		System.out.print("Enter your login username: ");
		username = sc.next();
		
	    boolean isMatched = false;
	    do {
	        System.out.print("Enter your password: ");
	        password = sc.next();
	        System.out.print("Confirm your password: ");
	        confirmPassword = sc.next();
	        if (password.equals(confirmPassword)) {
	            isMatched = true;
	        } else {
	            System.out.println("Passwords do not match. Please try again.");
	        }
	    } while (!isMatched);

		
		user1.createUser(firstName, lastName, email, username, password);
		
		login();
	}
	public static void login() {	
		System.out.println("Login");
		
		userDao = new UserDaoSql();
			try {
				userDao.setConnection();
				String username, password;
				Scanner sc = new Scanner(System.in);
		
				System.out.println("Enter your username: ");
				username = sc.next();
				System.out.println();
				System.out.println("Enter your password: ");
				password = sc.next();
				System.out.println();
				
				Optional<User> userToFind = userDao.validateUser(username, password);
				
				if(userToFind.isPresent()) {
					userFound = userToFind.get();
System.out.println("-------------------------------------------------------------------------------------------");
					System.out.println("User login Success!\n Welcome " + userFound.getFirstName() + " " + userFound.getLastName() + "!");
					movieList = userDao.getListOfMoviesTracked(userFound);
				}
				else {
					
				}
			}	catch(ClassNotFoundException e) {
				e.printStackTrace();
			}	catch (IOException e) {
				e.printStackTrace();
			}	catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public static void viewMovie(List<Movie> movies) {
		
		MovieDao movie = new MovieDaoSql();
		getTrackedList();
		try {
			movie.setConnection();
		} catch(ClassNotFoundException | IOException | SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		if(movieList == null) {			
			System.out.println("No movies found... Please login");
			login();	
		}
		else {
			List<String> uniqueMovieList = new ArrayList<>();
			System.out.println("List of Movies");
			for(Movie m : movies) {
				if(m.getTitle() instanceof String) {
					String movieTitle = (int) m.getId() + ". " + (String) m.getTitle() + "\t" + m.getRating();
					
					if(!uniqueMovieList.contains(movieTitle)) {
						uniqueMovieList.add(movieTitle);
					}
				}
			}
			for(String str : uniqueMovieList) {
				System.out.println(str);
			}	
			viewAverageRating();
		}
	}
	public static void addMovie() {
		MovieDao movie = new MovieDaoSql();
		Scanner sc = new Scanner(System.in);
		try {
			movie.setConnection();
		} catch(ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}
		
		String title;
		double rating;
		
		System.out.println("Movie Title: ");
		title = sc.nextLine();
		System.out.println(title + " rating (1 - 5): ");
		rating = sc.nextDouble();
		
		movie.createMovie(title, rating);
	}
	public static void addRating() {
		MovieDao movie = new MovieDaoSql();
		Scanner sc = new Scanner(System.in);
		try {
			movie.setConnection();
		} catch(ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}
		int movieId;
		double newRating;
		
		System.out.println("Which movie do you want to rate? (Choose by id): ");
		movieId = sc.nextInt();
		System.out.println("What is the new rating you want to give it? (1 - 5)");
		newRating = sc.nextDouble();
		
		movie.updateMovieRating(newRating, movieId);
	}
	public static void viewAverageRating() {
		
		MovieDao avgRating = new MovieDaoSql();
		MovieDao numRating = new MovieDaoSql();
		MovieDao numberOfMovie = new MovieDaoSql();
		try {
			avgRating.setConnection();
			numRating.setConnection();
			numberOfMovie.setConnection();
		} catch(ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}		
		
		System.out.println("\nAverage Rating\t# of Ratings");
		for(int i = 0; i < numberOfMovie.numberOfMovies(); i++) {
			System.out.println(avgRating.getAverageRating(i).toString() + "\t\t" + numRating.getNumberRating(i).toString());
		}
	}
	public static void exit() {
		System.out.println("Exiting...");
	}
	private static void getTrackedList() {
		for(UserMovie u: movieList) {
			Optional<Movie> movie = movieDao.getMovieById(u.getMovieId());
			//checking if exists
			if(movie.isPresent()) {
				Movie movieFound = movie.get();
				System.out.println(movieFound.getId() + ". " + movieFound.getTitle() + ", Rating " + movieFound.getRating());
			}
		}
	}
}

