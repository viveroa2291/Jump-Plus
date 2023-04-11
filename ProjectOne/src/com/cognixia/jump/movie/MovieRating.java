package com.cognixia.jump.movie;

import java.util.Scanner;

public class MovieRating {

	public static void main(String[] args) {
		
		if(menu() == 1) {
			register();
		}
		else if(menu() == 2) {
			login();
		}
		else if(menu() == 3) {
			viewMovie();
		}
		else if(menu() == 4) {
			exit();
		}
		else {
			System.out.println("That is not an option. Please select 1-4.");
			menu();
		}
	}
	public static int menu() {
		Scanner sc = new Scanner(System.in);
	
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
