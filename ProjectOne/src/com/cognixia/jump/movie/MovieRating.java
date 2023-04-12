package com.cognixia.jump.movie;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MovieRating {

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
		System.out.println("We wil register here.");
	}
	public static void login() {
		System.out.println("We will login here. ");
	}
	public static void viewMovie() {
		System.out.println("Movie View");
	}
	public static void exit() {
		System.out.println("Exiting...");
	}
}
