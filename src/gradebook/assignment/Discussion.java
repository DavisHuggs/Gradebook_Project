/*
 * Assignment: Gradebook Project
 * Name: Davis Huggins
 */
package gradebook.assignment;

import java.time.LocalDate;

public class Discussion implements AssignmentInterface{
	private String reading;
	double score;
	char letter;
	String name;
	LocalDate dueDate;
	
	public Discussion(double s, char l, String n, LocalDate dD, String r) {
		score = s;
		letter = l;
		name = n;
		dueDate = dD;
		reading = r;
	}
	
	public String getReading() {
		return reading;
	}

	public void setReading(String reading) {
		this.reading = reading;
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
		return ("\nDISCUSSION:\n"
				+ "score = " + score
				+ "\nletter = " + letter
				+ "\nname = " + name
				+ "\ndue date = " + dueDate
				+ "\nassociated reading = " + reading);
	}
}
