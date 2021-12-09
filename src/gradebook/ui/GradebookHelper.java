package gradebook.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

import gradebook.assignment.AssignmentInterface;

public class GradebookHelper {
	/*
	 * Checks integer input to make sure it is between max and min.
	 * Also repeats input if integer is not valid
	 */
	public static int checkInteger(Scanner in, int max, int min, int type) {
		int count = 0;
		do {
			try {
				if(count > 0) {
					System.out.println("\nbe extra sure it is an integer between " + min + " & " + max + " !");
				}
				type = in.nextInt();
				if(type < min || type > max) {
					count++;
				}
			} 
			catch(InputMismatchException e) {
				count++;
				in.next();
			}
		}
		while(type == -1 || type > max || type < min);
		
		return type;
	}
	/*
	 * Checks integer input to make sure it is above min.
	 * Also repeats input if integer is not valid
	 */
	public static int checkInteger(Scanner in, int min, int type) {
		int count = 0;
		do {
			try {
				if(count > 0) {
					System.out.println("\nbe extra sure it is an integer greater than " + min + " !");
				}
				type = in.nextInt();
				if(type < min) {
					count++;
				}
			} 
			catch(InputMismatchException e) {
				count++;
				in.next();
			}
		}
		while(type == -1 || type < min);
		
		return type;
	}
	/*
	 * Checks if integer input is valid.
	 * Also repeats input if integer is not valid
	 */
	public static int checkInteger(Scanner in, int type) {
		do {
			try {
				type = in.nextInt();
			} 
			catch(InputMismatchException e) {
				in.next();
			}
		}
		while(type == -1);
		
		return type;
	}
	/*
	 * Checks if double input is valid.
	 * Also repeats input if double is not valid
	 */
	public static double checkDouble(Scanner in, double type) {
		do {
			try {
				type = in.nextDouble();
			} 
			catch(InputMismatchException e) {
				in.next();
			}
		}
		while(type == -10000000);
		
		return type;
	}
	/*
	 * Checks double input to make sure it is above min.
	 * Also repeats input if double is not valid
	 */
	public static double checkDouble(Scanner in, double min, double type) {
		int count = 0;
		do {
			try {
				if(count > 0) {
					System.out.println("\nbe extra sure it is a double greater than " + min + " !");
				}
				type = in.nextInt();
				if(type < min) {
					count++;
				}
			} 
			catch(InputMismatchException e) {
				count++;
				in.next();
			}
		}
		while(type == -1 || type < min);
		
		return type;
	}
	/*
	 * Checks if letter input is valid.
	 * Also repeats input if letter is not valid
	 */
	public static char checkLetter(Scanner in, String type) {
		int count = 0;
		do {
			try {
				if(count > 0) {
					System.out.println("\nbe extra sure it is a single character between a and f");
				}
				type = in.next();
				count++;
			} 
			catch(InputMismatchException e) {
				in.next();
			}
		}
		while(type.equals("1") || (type.length() > 1) || !(type.matches("^[a-fA-F]*$")));
		
		return Character.toUpperCase(type.charAt(0));
	}
	/*
	 * Finds first empty slot in given array.
	 */
	public static int findEmptySlot(AssignmentInterface[] arr) {
		int index = -1;
		
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == null) {
				index = i;
				return index;
			}
		}
		
		return index;
	}
	/*
	 * Sorts arraylist by score
	 */
	public static ArrayList<AssignmentInterface> sortByScore(ArrayList<AssignmentInterface> arr){
		ArrayList<AssignmentInterface> temp = new ArrayList<>();
		temp = (ArrayList<AssignmentInterface>) arr.clone();
		ArrayList<AssignmentInterface> sortedArr = new ArrayList<>();
		
		while(sortedArr.size() != arr.size() && temp.isEmpty() == false) {
			int index = 0;
			int score = (int) temp.get(0).getScore();
			
			for(int i = 0; i < temp.size(); i++) {
				if(score < temp.get(i).getScore()) {
					score = (int) temp.get(i).getScore();
					index = i;
				}
			}
			sortedArr.add(temp.get(index));
			temp.remove(index);
		}
		
		return sortedArr;
	}
	/*
	 * Sorts arraylist by letter
	 */
	public static ArrayList<AssignmentInterface> sortByLetter(ArrayList<AssignmentInterface> arr){
		ArrayList<AssignmentInterface> temp = new ArrayList<>();
		temp = (ArrayList<AssignmentInterface>) arr.clone();
		ArrayList<AssignmentInterface> sortedArr = new ArrayList<>();
		
		while(sortedArr.size() != arr.size() && temp.isEmpty() == false) {
			int index = 0;
			char letter = (char) temp.get(0).getLetter();
			
			for(int i = 0; i < temp.size(); i++) {
				if(letter < temp.get(i).getLetter()) {
					letter = (char) temp.get(i).getLetter();
					index = i;
				}
			}
			sortedArr.add(temp.get(index));
			temp.remove(index);
		}
		
		Collections.reverse(sortedArr);
		
		return sortedArr;
	}
	/*
	 * Sorts arraylist by alphabetical names
	 */
	public static ArrayList<AssignmentInterface> sortByAlph(ArrayList<AssignmentInterface> arr){
		ArrayList<AssignmentInterface> temp = new ArrayList<>();
		temp = (ArrayList<AssignmentInterface>) arr.clone();
		ArrayList<AssignmentInterface> sortedArr = new ArrayList<>();
		
		while(sortedArr.size() != arr.size() && temp.isEmpty() == false) {
			int index = 0;
			String name = (String) temp.get(0).getName();
			
			for(int i = 0; i < temp.size(); i++) {
				if(name.compareTo((String) temp.get(i).getName()) > 0) {
					name = (String) temp.get(i).getName();
					index = i;
				}
			}
			sortedArr.add(temp.get(index));
			temp.remove(index);
		}
		
		return sortedArr;
	}
	/*
	 * Sorts arraylist by date
	 */
	public static ArrayList<AssignmentInterface> sortByDate(ArrayList<AssignmentInterface> arr){
		ArrayList<AssignmentInterface> temp = new ArrayList<>();
		temp = (ArrayList<AssignmentInterface>) arr.clone();
		ArrayList<AssignmentInterface> sortedArr = new ArrayList<>();
		
		while(sortedArr.size() != arr.size() && temp.isEmpty() == false) {
			int index = 0;
			LocalDate date = (LocalDate) temp.get(0).getDueDate();
			
			for(int i = 0; i < temp.size(); i++) {
				if(date.isBefore(temp.get(i).getDueDate())) {
					date = (LocalDate) temp.get(i).getDueDate();
					index = i;
				}
			}
			sortedArr.add(temp.get(index));
			temp.remove(index);
		}
		
		return sortedArr;
	}
}
