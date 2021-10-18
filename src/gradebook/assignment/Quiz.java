/*
 * Assignment: Gradebook Project
 * Name: Davis Huggins
 */
package gradebook.assignment;

import java.time.LocalDate;

public class Quiz implements AssignmentInterface{
	private int numQuestions;
	double score;
	char letter;
	String name;
	LocalDate dueDate;
	
	public Quiz(double s, char l, String n, LocalDate dD, int nQ) {
		score = s;
		letter = l;
		name = n;
		dueDate = dD;
		numQuestions = nQ;
	}
	
	public int getNumQuestions() {
		return numQuestions;
	}

	public void setNumQuestions(int numQuestions) {
		this.numQuestions = numQuestions;
	}
	
	@Override
	public double getScore() {
		return score;
	}

	@Override
	public char getLetter() {
		return letter;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public LocalDate getDueDate() {
		return dueDate;
	}

	@Override
	public void setScore(double score) {
		this.score = score;
		
	}

	@Override
	public void setLetter(char letter) {
		this.letter = letter;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setDueDate(LocalDate date) {
		this.dueDate= date;
	}
	
	@Override
	public String toString() {
		return ("\nQUIZ:\n"
				+ "score = " + score
				+ "\nletter = " + letter
				+ "\nname = " + name
				+ "\ndue date = " + dueDate
				+ "\nnumber of questions = " + numQuestions);
	}
}
