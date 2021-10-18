/*
 * Assignment: Gradebook Project
 * Name: Davis Huggins
 */
package gradebook.ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import gradebook.assignment.*;
import gradebook.io.*;

public class Gradebook {
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
	
	//main
	public static void main(String[] args) throws GradebookEmptyException, InvalidGradeException, GradebookFullException {
		Scanner in = new Scanner(System.in);
		int gradebookSize = -1;
		int menuOption = -1;
		int currElements = 0;
		boolean quit = false;
		/*
		 * Initializing AssignmentInterface array:
		 * Ask user to initialize size of gradebook
		 * max size of 20, min size of 0
		 */
		System.out.println("Welcome to the Gradebook!\n"
				+ "===================================\n");
		System.out.println("please enter the desired gradebook size:"
				+ "\t\t\t(Between 1 & 20)");
		
		gradebookSize = checkInteger(in, 20, 1, gradebookSize);
		
		AssignmentInterface[] assignIntArr = new AssignmentInterface[gradebookSize];
		
		/*
		 * Prompt user w/ menu containing different options:
		 * Add grades
		 * Remove grades
		 * Print grades
		 * Print avg
		 * Print high/low score
		 * Print quiz avg
		 * Print disc/reading
		 * Print program conc
		 */
		while(!quit) {
			System.out.println("\n--------------------------------------------------");
			System.out.println("Menu Options:\n"
					+ "----------------------\n"
					+ "(1) Add Grades\n"
					+ "(2) Remove Grades\n"
					+ "(3) Print Grades\n"
					+ "(4) Print Average\n"
					+ "(5) Print Highest/Lowest Score(s)\n"
					+ "(6) Print Quiz Question Average\n"
					+ "(7) Print Discussions/Associated Readings\n"
					+ "(8) Print Program Concepts\n"
					+ "(9) Quit\n");
			System.out.println("Enter a choice:\t\t\t(Between 1 & 9)");
			
			menuOption = checkInteger(in, 9, 1, menuOption);
			System.out.println("----------------------");
			
			switch(menuOption) {
				/*
				 * Adds grades to Gradebook:
				 * User is prompted to add a grade type of their choice,
				 * if there are already too many grades an exception will be thrown
				 */
				case 1:
					try {
						if(currElements == gradebookSize) {		//checks if gradebook is full
							throw new GradebookFullException();
						}
					}
					catch(GradebookFullException e) {
						System.out.println("the gradebook is already full...\t\tno more adding for now!");
						System.out.println("--------------------------------------------------");
						break;
					}
					
					int gradeOption = -1;
					double score = -1;
					String letterInput = "1";
					char letter = '1';
					String name = "";
					LocalDate dueLocalDate = null;
					String dueDate = "";
					int numQuestions = 0;
					String concept = "";
					String reading = "";
					
					System.out.println("Add Grades:\n"
							+ "--------------\n"
							+ "\nwhat type of grade would you like to add?\n"
							+ "(1) Quiz\n"
							+ "(2) Program\n"
							+ "(3) Discussion\n");
					
					gradeOption = checkInteger(in, 3, 1, gradeOption);
					
					System.out.println("\nwhat values would you like for the following?");		//sets base assignment variables
					
					System.out.println("\nSCORE:");
					score = checkDouble(in, 0, score);
					
					System.out.println("\nLETTER:");
					letter = checkLetter(in, letterInput);
					
					System.out.println("\nNAME:");
					name = in.next();
					
					while(true) {
						try {
							System.out.println("\nDUE DATE:\t\t\t(XXXX-XX-XX)");			//gets correct date format for .parse()
							dueDate = in.next();
							dueLocalDate = LocalDate.parse(dueDate);
							break;
						}
						catch(DateTimeParseException e) {
							System.out.println("that is not the correct format...(YEAR-MO-DA)\t\t\ttry again!");
						}
					}
					
					switch(gradeOption) {				//creates respective assignment type
						case 1:
							System.out.println("\nNUMBER OF QUESTIONS:");
							numQuestions = checkInteger(in, 0, numQuestions);
							assignIntArr[findEmptySlot(assignIntArr)] = new Quiz(score, letter, name, dueLocalDate, numQuestions);
							break;
						case 2:
							System.out.println("\nCONCEPT:");
							concept = in.next();
							assignIntArr[findEmptySlot(assignIntArr)] = new Program(score, letter, name, dueLocalDate, concept);
							break;
						case 3:
							System.out.println("\nASSOCIATED READING:");
							reading = in.next();
							assignIntArr[findEmptySlot(assignIntArr)] = new Discussion(score, letter, name, dueLocalDate, reading);
							break;
					}
					
					currElements++;
					System.out.println("--------------------------------------------------");
					break;
				/*
				 * Remove Grades:
				 * User is prompted with what grade type they would like
				 * to remove, If there is no grade of that type, or there are
				 * no grades to remove, an exception will be thrown
				 */
				case 2:
					try {
						if(currElements == 0) {			//checks if gradebook is empty
							throw new GradebookEmptyException();
						}
					}
					catch(GradebookEmptyException e) {
						System.out.println("the gradebook is empty...\t\tno more removing for now!");
						System.out.println("--------------------------------------------------");
						break;
					}
					
					int gradeOption2 = -1;
					boolean quit2 = false;
					while(!quit2) {
						System.out.println("Remove Grades:\n"
								+ "--------------\n"
								+ "\nwhat type of grade would you like to remove?\n"
								+ "(1) Quiz\n"
								+ "(2) Program\n"
								+ "(3) Discussion\n");
						
						gradeOption2 = checkInteger(in, 3, 1, gradeOption2);
						
						for(int i = 0; i < gradebookSize; i++) {			//finds and remove given assignment
							switch(gradeOption2) {
								case 1:
									if(assignIntArr[i] instanceof Quiz) {
										assignIntArr[i] = null;
										quit2 = true;
									}
									break;
								case 2:
									if(assignIntArr[i] instanceof Program) {
										assignIntArr[i] = null;
										quit2 = true;
									}
									break;
								case 3:
									if(assignIntArr[i] instanceof Discussion) {
										assignIntArr[i] = null;
										quit2 = true;
									}
									break;
							}
						}
						try {
							if(quit2 == false) {
								throw new InvalidGradeException();			//checks if assignment even exists
							}
						}
						catch(InvalidGradeException e) {
							System.out.println("\nthe grade you are trying to remove does not exist...\t\ttry again!\n");
						}
					}
					System.out.println("\ngrade removed...");
					currElements--;
					System.out.println("--------------------------------------------------");
					break;
				/*
				 * Print Grades:
				 * Prints all the grades in the gradebook, if there are
				 * no grades it will throw an exception
				 */
				case 3:
					try {
						if(currElements == 0) {
							throw new GradebookEmptyException();			//checks if gradebook is empty
						}
					}
					catch(GradebookEmptyException e) {
						System.out.println("the gradebook is empty...\t\tnothing to print for now!");
						System.out.println("--------------------------------------------------");
						break;
					}
					
					int gradebookEntry = 1;
					
					for(int i = 0; i < assignIntArr.length; i++) {
						if(assignIntArr[i] != null) {
							System.out.println("\nGradebook Entry #" + gradebookEntry			//prints all gradebook entries
									+ "\n----------------------------");
							System.out.println(assignIntArr[i].toString());
							System.out.println("\n");
							gradebookEntry++;
						}
						
					}
					System.out.println("--------------------------------------------------");
					break;
				/*
				 * Print Average:
				 * Prints the average of all the scores in the gradebook,
				 * if it is empty it will throw an exception
				 */
				case 4:
					try {
						if(currElements == 0) {
							throw new GradebookEmptyException();			//checks if gradebook is empty
						}
					}
					catch(GradebookEmptyException e) {
						System.out.println("the gradebook is empty...\t\tno average to print for now!");
						System.out.println("--------------------------------------------------");
						break;
					}
					
					double avg = 0;
					for(int i = 0; i < assignIntArr.length; i++) {			//prints average score
						if(assignIntArr[i] != null) {
							avg += assignIntArr[i].getScore();
						}
					}
					avg = avg/currElements;
					System.out.println("Average Score:\t" + avg);
					System.out.println("--------------------------------------------------");
					break;
				/*
				 * Print High/Low Score:
				 * Prints the highest and lowest score, if there is
				 * only one score it will be the highest and lowest,
				 * if there are no grades it will throw an exception
				 */
				case 5:
					try {
						if(currElements == 0) {
							throw new GradebookEmptyException();			//checks if gradebook is empty
						}
					}
					catch(GradebookEmptyException e) {
						System.out.println("the gradebook is empty...\t\tno high/low to print for now!");
						System.out.println("--------------------------------------------------");
						break;
					}
					
					double lowScore = 0;
					double highScore = 0;
					int count = 0;
					if(currElements == 1) {
						for(int i = 0; i < assignIntArr.length; i++) {			//sets high/low score the same if theres only 1 element
							if(assignIntArr[i] != null) {
								lowScore = assignIntArr[i].getScore();
								highScore = lowScore;
							}
						}
					}
					else {
						for(int i = 0; i < assignIntArr.length; i++) {
							if(assignIntArr[i] != null && count == 0) {
								lowScore = assignIntArr[i].getScore();				//finds high/low score otherwise
								highScore = lowScore;
								count++;
							}
							else if(assignIntArr[i] != null) {
								if(assignIntArr[i].getScore() > highScore) {
									highScore = assignIntArr[i].getScore();
								}
								if(assignIntArr[i].getScore() < lowScore) {
									lowScore = assignIntArr[i].getScore();
								}
							}
						}
					}
					
					System.out.println("Lowest Score:\t" + lowScore);
					System.out.println("Highest Score:\t" + highScore);
					System.out.println("--------------------------------------------------");
					break;
				/*
				 * Prints Average Number of Questions:
				 * Prints the average number of questions amongst all the quizzes,
				 * if there are no quizzes it will print a message,
				 * if it is empty it will throw an exception
				 */
				case 6:
					try {
						if(currElements == 0) {
							throw new GradebookEmptyException();			//checks if gradebook is empty
						}
					}
					catch(GradebookEmptyException e) {
						System.out.println("the gradebook is empty...\t\tno average to print for now!");
						System.out.println("--------------------------------------------------");
						break;
					}
					
					double numQuestionAvg = 0;
					int count4 = 0;
					for(int i = 0; i < assignIntArr.length; i++) {
						if(assignIntArr[i] instanceof Quiz) {					//gets average question number
							numQuestionAvg += ((Quiz) assignIntArr[i]).getNumQuestions();
							count4++;
						}
					}
					
					if(count4 == 0 && currElements > 0) {
						System.out.println("there are no quizzes in the gradebook...");
					}
					else {
						System.out.println("Average Number of Questions:\t" + numQuestionAvg);
					}
					break;
				/*
				 * Print All Discussion Readings:
				 * Prints all discussion readings, if there
				 * are no grades it will throw an exception
				 */
				case 7:
					try {
						if(currElements == 0) {
							throw new GradebookEmptyException();			//checks if gradebook is empty
						}
					}
					catch(GradebookEmptyException e) {
						System.out.println("the gradebook is empty...\t\tno readings to print for now!");
						System.out.println("--------------------------------------------------");
						break;
					}
					
					int count2 = 0;
					for(int i = 0; i < assignIntArr.length; i++) {
						if(assignIntArr[i] instanceof Discussion) {
							System.out.println("\nAssociated Reading #" + (count2+1));					//prints all discussion readings
							System.out.println("\"" + ((Discussion) assignIntArr[i]).getReading() + "\"");
							System.out.println("\n");
							count2++;
						}
					}
					
					if(count2 == 0 && currElements > 0) {
						System.out.println("there are no discussions in the gradebook...");
					}
					System.out.println("--------------------------------------------------");
					break;
				/*
				 * Print All Concepts:
				 * Prints all concepts for Programs, if there
				 * are no grades it will throw an exception
				 */
				case 8:
					try {
						if(currElements == 0) {
							throw new GradebookEmptyException();			//checks if gradebook is empty
						}
					}
					catch(GradebookEmptyException e) {
						System.out.println("the gradebook is empty...\t\tno concepts to print for now!");
						System.out.println("--------------------------------------------------");
						break;
					}
					
					int count3 = 0;
					for(int i = 0; i < assignIntArr.length; i++) {
						if(assignIntArr[i] instanceof Program) {
							System.out.println("\nConcept #" + (count3+1));
							System.out.println("\"" + ((Program) assignIntArr[i]).getConcept() + "\"");			//prints all concepts
							System.out.println("\n");
							count3++;
						}
					}
					
					if(count3 == 0 && currElements > 0) {
						System.out.println("there are no programs in the gradebook...");
					}
					System.out.println("--------------------------------------------------");
					break;
				/*
				 * Quits program
				 */
				case 9:
					quit = true;			//quits program
					System.out.println("LEAVING GRADEBOOK...");
					break;
			}
		}
		
		in.close();
	}

}
