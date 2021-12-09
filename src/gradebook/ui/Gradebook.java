/*
 * Assignment: Gradebook Project
 * Name: Davis Huggins
 */
package gradebook.ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;

import gradebook.assignment.*;
import gradebook.io.*;
import gradebook.db.*;

public class Gradebook {	
	//**TEST**
	//add grade
	public static void addGradeTest(int gradeOption, ArrayList<AssignmentInterface> assignIntArr, double score, char letter, String name, LocalDate dueLocalDate, String concept, String reading, int numQuestions) {
		switch(gradeOption) {				//creates respective assignment type
		case 1:
			assignIntArr.add(new Quiz(score, letter, name, dueLocalDate, numQuestions));
			break;
		case 2:
			assignIntArr.add(new Program(score, letter, name, dueLocalDate, concept));
			break;
		case 3:
			assignIntArr.add(new Discussion(score, letter, name, dueLocalDate, reading));
			break;
		}
	}
	//remove grade
	public static boolean removeGradeTest(String nameoption, ArrayList<AssignmentInterface> assignIntArr) {
		for(int i = 0; i < assignIntArr.size(); i++) {			//finds and remove given assignment
			if((assignIntArr.get(i).getName()).equals(nameoption)) {
				assignIntArr.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	//**OPTIONS**
	//option1
	public static void addGrade(ArrayList<AssignmentInterface> assignIntArr) {
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
		
		gradeOption = GradebookHelper.checkInteger(in, 3, 1, gradeOption);
		
		System.out.println("\nwhat values would you like for the following?");		//sets base assignment variables
		
		System.out.println("\nSCORE:");
		score = GradebookHelper.checkDouble(in, 0, score);
		
		System.out.println("\nLETTER:");
		letter = GradebookHelper.checkLetter(in, letterInput);
		
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
		
		if(gradeOption == 1) {
			System.out.println("\nNUMBER OF QUESTIONS:");
			numQuestions = GradebookHelper.checkInteger(in, 1, numQuestions);
		}
		else if(gradeOption == 2) {
			System.out.println("\nCONCEPT:");
			concept = in.next();
		}
		else if(gradeOption == 3) {
			System.out.println("\nASSOCIATED READING:");
			reading = in.next();
		}
		
		addGradeTest(gradeOption, assignIntArr, score, letter, name, dueLocalDate, concept, reading, numQuestions);
		
		currElements++;
		System.out.println("--------------------------------------------------");
	}
	//option2
	public static void removeGrade(ArrayList<AssignmentInterface> assignIntArr) {
		try {
			if(currElements == 0) {			//checks if gradebook is empty
				throw new GradebookEmptyException();
			}
		}
		catch(GradebookEmptyException e) {
			System.out.println("the gradebook is empty...\t\tno more removing for now!");
			System.out.println("--------------------------------------------------");
			return ;
		}
		
		System.out.println("Remove Grades:\n"
				+ "--------------");
		
		int gradeOption2 = -1;
		boolean quit2 = false;
		while(!quit2) {
			System.out.println("\nwhat is the name of the grade you would like to remove?");
			
			String nameoption = in.next();
			
			quit2 = removeGradeTest(nameoption, assignIntArr);
			
			try {
				if(quit2 == false) {
					throw new InvalidGradeException();			//checks if assignment even exists
				}
			}
			catch(InvalidGradeException e) {
				System.out.println("\nthe grade you are trying to remove does not exist...\t\ttry again!");
				break;
			}
		}
			
		if(quit2 == true) {
			System.out.println("\ngrade removed...");
			currElements--;
		}
		System.out.println("--------------------------------------------------");
	}
	//option3
	public static void printGrades(ArrayList<AssignmentInterface> assignIntArr) {
		try {
			if(currElements == 0) {
				throw new GradebookEmptyException();			//checks if gradebook is empty
			}
		}
		catch(GradebookEmptyException e) {
			System.out.println("the gradebook is empty...\t\tnothing to print for now!");
			System.out.println("--------------------------------------------------");
			return ;
		}
		
		int printOption = -1;
		
		System.out.println("Print Grades:\n"
				+ "--------------\n"
				+ "\nhow would you like to sort the grades?\n"
				+ "(1) Score\n"
				+ "(2) Letter\n"
				+ "(3) Alphabetical Name\n"
				+ "(4) Due Date\n"
				+ "(5) Print Normally\n");
		
		printOption = GradebookHelper.checkInteger(in, 5, 1, printOption);
		
		int gradebookEntry = 1;
		
		ArrayList<AssignmentInterface> sortedArr = new ArrayList<>();
		
		switch(printOption) {
		case 1:
			sortedArr = GradebookHelper.sortByScore(assignIntArr);
			break;
		case 2:
			sortedArr = GradebookHelper.sortByLetter(assignIntArr);
			break;
		case 3:
			sortedArr = GradebookHelper.sortByAlph(assignIntArr);
			break;
		case 4:
			sortedArr = GradebookHelper.sortByDate(assignIntArr);
			break;
		case 5:
			sortedArr = (ArrayList<AssignmentInterface>) assignIntArr.clone();
		}
		
		for(int i = 0; i < sortedArr.size(); i++) {
			if(sortedArr.get(i) != null) {
				System.out.println("\nGradebook Entry #" + gradebookEntry			//prints all gradebook entries
						+ "\n----------------------------");
				System.out.println(sortedArr.get(i).toString());
				System.out.println("\n");
				gradebookEntry++;
			}
			
		}
		System.out.println("--------------------------------------------------");
	}
    //option4
	public static void printAverage(ArrayList<AssignmentInterface> assignIntArr) {
		try {
			if(currElements == 0) {
				throw new GradebookEmptyException();			//checks if gradebook is empty
			}
		}
		catch(GradebookEmptyException e) {
			System.out.println("the gradebook is empty...\t\tno average to print for now!");
			System.out.println("--------------------------------------------------");
			return ;
		}
		
		double avg = 0;
		for(int i = 0; i < assignIntArr.size(); i++) {			//prints average score
			if(assignIntArr.get(i) != null) {
				avg += assignIntArr.get(i).getScore();
			}
		}
		avg = avg/currElements;
		System.out.println("Average Score:\t" + avg);
		System.out.println("--------------------------------------------------");
	}
	//option5
	public static void printHighLow(ArrayList<AssignmentInterface> assignIntArr) {
		try {
			if(currElements == 0) {
				throw new GradebookEmptyException();			//checks if gradebook is empty
			}
		}
		catch(GradebookEmptyException e) {
			System.out.println("the gradebook is empty...\t\tno high/low to print for now!");
			System.out.println("--------------------------------------------------");
			return ;
		}
		
		double lowScore = 0;
		double highScore = 0;
		int count = 0;
		if(currElements == 1) {
			for(int i = 0; i < assignIntArr.size(); i++) {			//sets high/low score the same if theres only 1 element
				if(assignIntArr.get(i) != null) {
					lowScore = assignIntArr.get(i).getScore();
					highScore = lowScore;
				}
			}
		}
		else {
			for(int i = 0; i < assignIntArr.size(); i++) {
				if(assignIntArr.get(i) != null && count == 0) {
					lowScore = assignIntArr.get(i).getScore();				//finds high/low score otherwise
					highScore = lowScore;
					count++;
				}
				else if(assignIntArr.get(i) != null) {
					if(assignIntArr.get(i).getScore() > highScore) {
						highScore = assignIntArr.get(i).getScore();
					}
					if(assignIntArr.get(i).getScore() < lowScore) {
						lowScore = assignIntArr.get(i).getScore();
					}
				}
			}
		}
		
		System.out.println("Lowest Score:\t" + lowScore);
		System.out.println("Highest Score:\t" + highScore);
		System.out.println("--------------------------------------------------");
	}
	//option6
	public static void printQuizAvg(ArrayList<AssignmentInterface> assignIntArr) {
		try {
			if(currElements == 0) {
				throw new GradebookEmptyException();			//checks if gradebook is empty
			}
		}
		catch(GradebookEmptyException e) {
			System.out.println("the gradebook is empty...\t\tno average to print for now!");
			System.out.println("--------------------------------------------------");
			return ;
		}
		
		double numQuestionAvg = 0;
		int count4 = 0;
		for(int i = 0; i < assignIntArr.size(); i++) {
			if(assignIntArr.get(i) instanceof Quiz) {					//gets average question number
				numQuestionAvg += ((Quiz) assignIntArr.get(i)).getNumQuestions();
				count4++;
			}
		}
		
		numQuestionAvg /= count4;
		
		if(count4 == 0 && currElements > 0) {
			System.out.println("there are no quizzes in the gradebook...");
		}
		else {
			System.out.println("Average Number of Questions:\t" + numQuestionAvg);
		}
	}
	//option7
	public static void printReading(ArrayList<AssignmentInterface> assignIntArr) {
		try {
			if(currElements == 0) {
				throw new GradebookEmptyException();			//checks if gradebook is empty
			}
		}
		catch(GradebookEmptyException e) {
			System.out.println("the gradebook is empty...\t\tno readings to print for now!");
			System.out.println("--------------------------------------------------");
			return ;
		}
		
		int count2 = 0;
		for(int i = 0; i < assignIntArr.size(); i++) {
			if(assignIntArr.get(i) instanceof Discussion) {
				System.out.println("\nAssociated Reading #" + (count2+1));					//prints all discussion readings
				System.out.println("\"" + ((Discussion) assignIntArr.get(i)).getReading() + "\"");
				System.out.println("\n");
				count2++;
			}
		}
		
		if(count2 == 0 && currElements > 0) {
			System.out.println("there are no discussions in the gradebook...");
		}
		System.out.println("--------------------------------------------------");
	}
	//option8
	public static void printConcept(ArrayList<AssignmentInterface> assignIntArr) {
		try {
			if(currElements == 0) {
				throw new GradebookEmptyException();			//checks if gradebook is empty
			}
		}
		catch(GradebookEmptyException e) {
			System.out.println("the gradebook is empty...\t\tno concepts to print for now!");
			System.out.println("--------------------------------------------------");
			return ;
		}
		
		int count3 = 0;
		for(int i = 0; i < assignIntArr.size(); i++) {
			if(assignIntArr.get(i) instanceof Program) {
				System.out.println("\nConcept #" + (count3+1));
				System.out.println("\"" + ((Program) assignIntArr.get(i)).getConcept() + "\"");			//prints all concepts
				System.out.println("\n");
				count3++;
			}
		}
		
		if(count3 == 0 && currElements > 0) {
			System.out.println("there are no programs in the gradebook...");
		}
		System.out.println("--------------------------------------------------");
	}
	//option9
	public static void printToFile(ArrayList<AssignmentInterface> assignIntArr) {
		try {
			if(currElements == 0) {
				throw new GradebookEmptyException();			//checks if gradebook is empty
			}
		}
		catch(GradebookEmptyException e) {
			System.out.println("the gradebook is empty...\t\tnothing to write for now!");
			System.out.println("--------------------------------------------------");
			return ;
		}
		
		if(!(GradebookIO.saveGradebook(assignIntArr))) {
			System.out.println("the gradebook did not write to file properly...\t\ttry again");
		}
		else {
			System.out.println("\nsuccesfully printed to file!");
		}
		
		System.out.println("--------------------------------------------------");
	}
	//option10
	public static void printFromFile(ArrayList<AssignmentInterface> assignIntArr) {
		if(GradebookIO.addFromFile(in, assignIntArr)) {
			System.out.println("\nsuccesfully added grades!");
		}
		System.out.println("--------------------------------------------------");
	}
	//option11
	public static void toSQL(ArrayList<AssignmentInterface> assignIntArr) {
		try {
			if(currElements == 0) {
				throw new GradebookEmptyException();			//checks if gradebook is empty
			}
		}
		catch(GradebookEmptyException e) {
			System.out.println("the gradebook is empty...\t\tnothing to add for now!");
			System.out.println("--------------------------------------------------");
			return ;
		}
		
		try {
			if(GradebookDB.createTable() == false) {
				System.out.println("\nfailed to convert to MySQL...\t\ttry again");
				return ;
			}
		} catch (SQLException e) {
			System.out.println("\nfailed to convert to MySQL...\t\ttry again");
			e.printStackTrace();
			return ;
		}
		
		if(GradebookDB.toMySQL(assignIntArr)) {
			System.out.println("\nsuccesfully converted to MySQL!\n");
		}
		else {
			System.out.println("\nfailed to convert to MySQL...\t\ttry again");
		}
		System.out.println("--------------------------------------------------");
	}
	//option12
	public static void fromSQL(ArrayList<AssignmentInterface> assignIntArr) {
		try {
			if(GradebookDB.createTable() == false) {
				System.out.println("\nfailed to convert to MySQL...\t\ttry again");
				return ;
			}
		} catch (SQLException e) {
			System.out.println("\nfailed to convert to MySQL...\t\ttry again");
			e.printStackTrace();
			return ;
		}
		
		if(GradebookDB.fromMySQL(assignIntArr)) {
			System.out.println("\nsuccesfully converted to MySQL!\n");
		}
		else {
			System.out.println("\nfailed to convert to MySQL...\t\ttry again");
		}
		System.out.println("--------------------------------------------------");
	}
	//option13
	public static void SQLSearch(ArrayList<AssignmentInterface> assignIntArr) {
		try {
			if(GradebookDB.createTable() == false) {
				System.out.println("\nfailed to convert to MySQL...\t\ttry again");
				return ;
			}
		} catch (SQLException e) {
			System.out.println("\nfailed to convert to MySQL...\t\ttry again");
			e.printStackTrace();
			return ;
		}
		
		if(!GradebookDB.searchSQL(in)) {
			System.out.println("\nfailed to search MySQL...\t\ttry again");
		}
		System.out.println("--------------------------------------------------");
	}
	
	//**MAIN**
	public static int currElements = 0;
	public static Scanner in = new Scanner(System.in);
	
	//main
	public static void main(String[] args) throws GradebookEmptyException, InvalidGradeException, GradebookFullException {
		int menuOption = -1;
		boolean quit = false;
		/*
		 * Initializing AssignmentInterface array:
		 * Ask user to initialize size of gradebook
		 * max size of 20, min size of 0
		 */
		System.out.println("Welcome to the Gradebook!\n"
				+ "===================================\n");
		
		//AssignmentInterface[] assignIntArr = new AssignmentInterface[gradebookSize];
		
		ArrayList<AssignmentInterface> assignIntArr = new ArrayList<>();
		
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
					+ "(9) Print to File\n"
					+ "(10) Read from File\n"
					+ "(11) To MySQL\n"
					+ "(12) From MySQL\n"
					+ "(13) MySQL Search\n"
					+ "(14) Quit\n");
			System.out.println("Enter a choice:\t\t\t(Between 1 & 14)");
			
			menuOption = GradebookHelper.checkInteger(in, 14, 1, menuOption);
			System.out.println("----------------------");
			
			switch(menuOption) {
				/*
				 * Adds grades to Gradebook:
				 * User is prompted to add a grade type of their choice,
				 * if there are already too many grades an exception will be thrown
				 */
				case 1:
					addGrade(assignIntArr);
					break;
				/*
				 * Remove Grades:
				 * User is prompted with what grade type they would like
				 * to remove, If there is no grade of that type, or there are
				 * no grades to remove, an exception will be thrown
				 */
				case 2:
					removeGrade(assignIntArr);
					break;
				/*
				 * Print Grades:
				 * Prints all the grades in the gradebook, if there are
				 * no grades it will throw an exception
				 */
				case 3:
					printGrades(assignIntArr);
					break;
				/*
				 * Print Average:
				 * Prints the average of all the scores in the gradebook,
				 * if it is empty it will throw an exception
				 */
				case 4:
					printAverage(assignIntArr);
					break;
				/*
				 * Print High/Low Score:
				 * Prints the highest and lowest score, if there is
				 * only one score it will be the highest and lowest,
				 * if there are no grades it will throw an exception
				 */
				case 5:
					printHighLow(assignIntArr);
					break;
				/*
				 * Prints Average Number of Questions:
				 * Prints the average number of questions amongst all the quizzes,
				 * if there are no quizzes it will print a message,
				 * if it is empty it will throw an exception
				 */
				case 6:
					printQuizAvg(assignIntArr);
					break;
				/*
				 * Print All Discussion Readings:
				 * Prints all discussion readings, if there
				 * are no grades it will throw an exception
				 */
				case 7:
					printReading(assignIntArr);
					break;
				/*
				 * Print All Concepts:
				 * Prints all concepts for Programs, if there
				 * are no grades it will throw an exception
				 */
				case 8:
					printConcept(assignIntArr);
					break;
				case 9:
					printToFile(assignIntArr);
					break;
				case 10:
					printFromFile(assignIntArr);
					break;
				case 11:
					toSQL(assignIntArr);
					break;
				case 12:
					fromSQL(assignIntArr);
					break;
				case 13:
					SQLSearch(assignIntArr);
					break;
				/*
				 * Quits program
				 */
				case 14:
					quit = true;			//quits program
					System.out.println("LEAVING GRADEBOOK...");
					break;
			}
		}
		
		DBUtil.closeConnection();
		in.close();
	}

}
