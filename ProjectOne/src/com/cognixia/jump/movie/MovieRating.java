package com.cognixia.jump.movie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.cognixia.jump.dao.MovieDao;
import com.cognixia.jump.dao.User;
import com.cognixia.jump.dao.UserDao;
import com.cognixia.jump.dao.UserDaoSql;
import com.cognixia.jump.dao.UserMovie;

public class MovieRating {
	
	private static List<UserMovie> movieList;
	private static MovieDao movieDao;
	private static User userFound;
	private static UserDao userDao;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int option = menu(sc);
		try {
			while(option != 4) {
				if(option == 1) {
				register();
				 option = menu(sc);
				}
				else if(option == 2) {
					login();
					option = menu(sc);
				}
				else if(option == 3) {
					viewMovie();
					option = menu(sc);
				}
				else {
					System.out.println("That is not an option. Please select 1-4.");
					option = menu(sc);
				}
			} 
			} catch (InputMismatchException e) {
				System.out.println("Please select an integer.");
				option = menu(sc);
		}
		exit();
		sc.close();
	}
	public static int menu(Scanner sc) {
	
		System.out.println("+========================================================+");
		System.out.println("| 1. REGISTER |");
		System.out.println("| 2.LOGIN |");
		System.out.println("| 3. VIEW MOVIES |");
		System.out.println("| 4.EXIT |");
		System.out.println("+========================================================+");
		
		return sc.nextInt();
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
		
		// Prompt user to enter password twice and check if they match
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

		/*
		System.out.print("Enter your password: ");
		password = sc.next();
		*/
		
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
	public static void viewMovie() {
		System.out.println("Movie View");
	}
	public static void exit() {
		System.out.println("Exiting...");
	}
}

