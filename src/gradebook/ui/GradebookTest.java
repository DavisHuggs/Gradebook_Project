package gradebook.ui;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gradebook.assignment.*;

public class GradebookTest {
	ArrayList<AssignmentInterface> assignIntArr = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		LocalDate date = LocalDate.parse("1212-12-12");
		assignIntArr.add(new Quiz(100, 'a', "davis", date, 20));
		assignIntArr.add(new Program(100, 'a', "davis", date, "school"));
		assignIntArr.add(new Discussion(100, 'a', "davis", date, "work"));
	}
	
	//test created grade
	@Test
	public void testAddGrade() {
		Gradebook.addGrade(1, assignIntArr, 100, 'a', "davis", LocalDate.parse("1212-12-12"), "school", "work", 20);
		Assertions.assertEquals(assignIntArr.size(), 4);
	}
	
	//testing to see if it was properly added
	@Test
	public void testAddGradeProper() {
		int gradeOption = 1;
		double score = 50;
		char letter = 'f';
		String name = "special";
		LocalDate date = LocalDate.parse("1212-12-12");
		String concept = "school";
		String reading = "work";
		boolean theSame = false;
		
		Gradebook.addGrade(gradeOption, assignIntArr, score, letter, name, date, concept, reading, 20);
		
		int size = assignIntArr.size() - 1;
		
		if(assignIntArr.get(size).getAssignmentType().equals("Quiz") &&
			assignIntArr.get(size).getDueDate().compareTo(date) == 0 &&
			assignIntArr.get(size).getScore() == score &&
			assignIntArr.get(size).getName().equals(name) &&
			assignIntArr.get(size).getLetter() == letter &&
			((Quiz) assignIntArr.get(size)).getNumQuestions() == 20){
				theSame = true;
			}
		
		Assertions.assertTrue(theSame);
	}
	
	@Test
	public void testRemoveGrade() {
		Gradebook.removeGrade("davis", assignIntArr);
		boolean isCorrect = false;
		
		if(assignIntArr.get(0).getAssignmentType().equals("Program") &&
				assignIntArr.get(1).getAssignmentType().equals("Discussion")) {
			isCorrect = true;
		}
		
		Assertions.assertTrue(isCorrect);
	}
}
