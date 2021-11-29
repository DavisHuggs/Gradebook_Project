/*
 * Assignment: Gradebook Project
 * Name: Davis Huggins
 */
package gradebook.io;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import gradebook.assignment.AssignmentInterface;
import gradebook.assignment.Discussion;
import gradebook.assignment.Program;
import gradebook.assignment.Quiz;
import gradebook.ui.Gradebook;

public class GradebookIO {
	private static int fileNum = 1;
	private static String fileString = ("GradeTextFile" + fileNum + ".txt");
	private static Path filePath = Paths.get("GradeTextFiles", fileString);
	private static File gradebookFile = filePath.toFile();
	
	public static boolean saveGradebook(ArrayList<AssignmentInterface> assignIntArr) {
		try {
			if(Files.exists(filePath)) {
				fileNum++;
				fileString = ("GradeTextFile" + fileNum + ".txt");
				filePath = Paths.get("GradeTextFiles", fileString);
				gradebookFile = filePath.toFile();
				return saveGradebook(assignIntArr);
			}
			
			if(Files.notExists(filePath)) {
				Files.createFile(filePath);
				gradebookFile = filePath.toFile();
			}
		}
		catch(IOException e) {
			return false;
		}

		try(PrintWriter out = new PrintWriter(new BufferedWriter(
								new FileWriter(gradebookFile, true)))){
			//<AssignmentType>\t<GradeName>\t<Score>\t<Letter>\t<DueDate>\t<UniqueVariable>
			String currStr = "";
			
			for(int i = 0; i < assignIntArr.size(); i++) {
				if(assignIntArr.get(i) instanceof Quiz) {
					currStr = "Quiz\t" + assignIntArr.get(i).getName() + "\t" + assignIntArr.get(i).getScore() + "\t"
							+ assignIntArr.get(i).getLetter() + "\t" + assignIntArr.get(i).getDueDate() + "\t"
							+ (((Quiz) assignIntArr.get(i)).getNumQuestions());
				}
				else if(assignIntArr.get(i) instanceof Program) {
					currStr = "Program\t" + assignIntArr.get(i).getName() + "\t" + assignIntArr.get(i).getScore() + "\t"
							+ assignIntArr.get(i).getLetter() + "\t" + assignIntArr.get(i).getDueDate() + "\t"
							+ (((Program) assignIntArr.get(i)).getConcept());
				}
				else if(assignIntArr.get(i) instanceof Discussion) {
					currStr = "Discussion\t" + assignIntArr.get(i).getName() + "\t" + assignIntArr.get(i).getScore() + "\t"
							+ assignIntArr.get(i).getLetter() + "\t" + assignIntArr.get(i).getDueDate() + "\t"
							+ (((Discussion) assignIntArr.get(i)).getReading());
				}
				
				out.println(currStr);
			}
			
			out.flush();
			out.close();
		}
		catch(IOException e) {
			return false;
		}
		
		return true;
	}
	
	public static boolean addFromFile(Scanner in, ArrayList<AssignmentInterface> assignIntArr){
		
		String helpArr[];
		String name;
		double score;
		char letter;
		LocalDate dueDate;
		int numQuestions;
		String unique;
		
		System.out.println("what is the name of the file you would like to read?\t\t(example.txt)");
		fileString = in.next();
		filePath = Paths.get("GradeTextFiles", fileString);
		gradebookFile = filePath.toFile();
		
		if(Files.notExists(filePath)) {
			System.out.println("file doesn't exist...\t\ttry again");
			return false;
		}
		
		//<AssignmentType>\t<GradeName>\t<Score>\t<Letter>\t<DueDate>\t<UniqueVariable>
		
		try(BufferedReader read = new BufferedReader(new FileReader(gradebookFile))){
			String line;
			while((line = read.readLine()) != null) {
				Gradebook.currElements++;
				helpArr = line.split("\t");
				
				if(helpArr[0].equals("Quiz")) {
					name = helpArr[1];
					score = Double.parseDouble(helpArr[2]);
					letter = helpArr[3].charAt(0);
					dueDate = LocalDate.parse(helpArr[4]);
					numQuestions = Integer.parseInt(helpArr[5]);
					assignIntArr.add(new Quiz(score, letter, name, dueDate, numQuestions));
				}
				else if(helpArr[0].equals("Program")) {
					name = helpArr[1];
					score = Double.parseDouble(helpArr[2]);
					letter = helpArr[3].charAt(0);
					dueDate = LocalDate.parse(helpArr[4]);
					unique = helpArr[5];
					assignIntArr.add(new Program(score, letter, name, dueDate, unique));
				}
				else if(helpArr[0].equals("Discussion")) {
					name = helpArr[1];
					score = Double.parseDouble(helpArr[2]);
					letter = helpArr[3].charAt(0);
					dueDate = LocalDate.parse(helpArr[4]);
					unique = helpArr[5];
					assignIntArr.add(new Discussion(score, letter, name, dueDate, unique));
				}
			}
			
			read.close();
		}
		catch(IOException e) {
			System.out.println("the gradebook did not read from file properly...\t\ttry again");
			return false;
		}
		
		return true;
	}
}
