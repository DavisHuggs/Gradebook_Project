/*
 * Assignment: Gradebook Project
 * Name: Davis Huggins
 */
package gradebook.assignment;

import java.time.LocalDate;

public interface AssignmentInterface {
	//variables for assignments
	public double score = 0;
	public char letter = 0;
	public String name = "";
	public LocalDate dueDate = null;
	
	//getters for assignments
	public abstract double getScore();
	public abstract char getLetter();
	public abstract String getName();
	public abstract LocalDate getDueDate();
	
	//setters for assignments
	public abstract void setScore(double score);
	public abstract void setLetter(char letter);
	public abstract void setName(String name);
	public abstract void setDueDate(LocalDate date);
	
	//toString for assignment
	public abstract String toString();
}
