package com.cognixia.jump.gradebook;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GradeBook {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int option; 
		
		 do {
	            option = signinMenu(sc);

	            switch (option) {
	                case 1:
	                    register();
	                    break;
	                case 2:
	                    int loggedInOption;
	                    do {
	                        loggedInOption = loggedInMenu(sc);

	                        switch (loggedInOption) {
	                            case 1:
	                                // View Classes
	                                int classOption;
	                                do {
	                                    classOption = classMenu(sc);

	                                    switch (classOption) {
	                                        case 1:
	                                            averageGrade();
	                                            break;
	                                        case 2:
	                                            medianGrade();
	                                            break;
	                                        case 3:
	                                            sortByGrade();
	                                            break;
	                                        case 4:
	                                            sortByName();
	                                            break;
	                                        case 5:
	                                            updateGrade();
	                                            break;
	                                        case 6:
	                                            addStudent();
	                                            break;
	                                        case 7:
	                                            removeStudent();
	                                            break;
	                                        default:
	                                            System.out.println("Invalid option. Please try again.");
	                                            break;
	                                    }
	                                } while (classOption != 0);
	                                break;
	                            case 2:
	                                // Create Class
	                                createClass();
	                                break;
	                            case 3:
	                                // Logout
	                                break;
	                            default:
	                                System.out.println("Invalid option. Please try again.");
	                                break;
	                        }
	                    } while (loggedInOption != 3);
	                    break;
	                case 3:
	                    exit();
	                    break;
	                default:
	                    System.out.println("Invalid option. Please try again.");
	                    break;
	            }
	        } while (option != 3);

	        sc.close();
	}
	public static int signinMenu(Scanner sc) {
		
		System.out.println("+========================================================+");
		System.out.println("| 1. REGISTER    |");
		System.out.println("| 2. LOGIN       |");
		System.out.println("| 3. EXIT        |");
		System.out.println("+========================================================+");
		
		return sc.nextInt();
	}
	public static int loggedInMenu(Scanner sc) {
		
		System.out.println("+========================================================+");
		System.out.println("| 1. View Classes    |");
		System.out.println("| 2. Create Class    |");
		System.out.println("| 3. Logout        	 |");
		System.out.println("+========================================================+");
		
		return sc.nextInt();
	}
	public static int classMenu(Scanner sc) {
		
		System.out.println("+========================================================+");
		System.out.println("| 1. Find Average Grade    		   |");
		System.out.println("| 2. Find Median Grade             |");
		System.out.println("| 3. Sort Students By Grade        |");
		System.out.println("| 4. Sort Students By Name         |");
		System.out.println("| 5. Update Students Grade         |");
		System.out.println("| 6. Add Student        		   |");
		System.out.println("| 7. Remove Student        		   |");
		System.out.println("| 0. Go back        			   |");
		System.out.println("+========================================================+");
		
		return sc.nextInt();
	}
	public static int register() {
		System.out.println("You selected register.");
		return 1;
	}
	public static int login() {
		System.out.println("You selected to login");
		return 2;
	}
	public static void exit() {
		System.out.println("Exiting...");
	}
	public static void createClass() {
		System.out.println("You selected create a class");
	}
	public static void averageGrade() {
		System.out.println("You selected average grade");
	}
	public static void medianGrade() {
		System.out.println("You selected median grade");
	}
	public static void sortByGrade() {
		System.out.println("You selected to sort by grade");
	}
	public static void sortByName() {
		System.out.println("You selected to sort by name");
	}
	public static void updateGrade() {
		System.out.println("You selected to update students grade");
	}
	public static void addStudent() {
		System.out.println("You selected to add a student");
	}
	public static void removeStudent() {
		System.out.println("You selected to remove a student");
	}
}
