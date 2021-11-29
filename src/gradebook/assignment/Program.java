/*
 * Assignment: Gradebook Project
 * Name: Davis Huggins
 */
package gradebook.assignment;

import java.time.LocalDate;

public class Program implements AssignmentInterface {
	private String concept;
	double score;
	char letter;
	String name;
	LocalDate dueDate;
	
	public Program(double s, char l, String n, LocalDate dD, String c) {
		score = s;
		letter = l;
		name = n;
		dueDate = dD;
		concept = c;
	}
	
	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
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
		return ("\nPROGRAM:\n"
				+ "score = " + score
				+ "\nletter = " + letter
				+ "\nname = " + name
				+ "\ndue date = " + dueDate
				+ "\nconcept = " + concept);
	}

	@Override
	public String getAssignmentType() {
		return "Program";
	}
}
