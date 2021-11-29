package gradebook.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import gradebook.assignment.AssignmentInterface;
import gradebook.assignment.Discussion;
import gradebook.assignment.Program;
import gradebook.assignment.Quiz;
import gradebook.ui.Gradebook;

public class GradebookDB {
	private static Connection connection = DBUtil.getConnection(Gradebook.in);
	
	public static boolean toMySQL(ArrayList<AssignmentInterface> assignIntArr) {
		if(connection == null) {
			connection = DBUtil.getConnection(Gradebook.in);
		}
		
		String insertGradebook
        = "INSERT INTO Gradebook (assignmenttype, score, letter, name, date, uniquevariable) "
        + "VALUES (?, ?, ?, ?, ?, ?)";
		
		try (PreparedStatement ps = connection.prepareStatement(insertGradebook)) {
			for(int i = 0; i < assignIntArr.size(); i++) {
			    ps.setString(1, assignIntArr.get(i).getAssignmentType());
			    ps.setDouble(2, assignIntArr.get(i).getScore());
			    
			    String letter = String.valueOf(assignIntArr.get(i).getLetter());
			    
			    ps.setString(3, letter);
			    ps.setString(4, assignIntArr.get(i).getName());
			    
			    String date = (assignIntArr.get(i).getDueDate()).toString();
			    
			    ps.setString(5, date);
			    
			    String unique = "";
				if(assignIntArr.get(i) instanceof Quiz) {
					unique = String.valueOf((((Quiz) assignIntArr.get(i)).getNumQuestions()));
				}
				else if(assignIntArr.get(i) instanceof Program) {
					unique = (((Program) assignIntArr.get(i)).getConcept());
				}
				else if(assignIntArr.get(i) instanceof Discussion) {
					unique = (((Discussion) assignIntArr.get(i)).getReading());
				}
			    
			    ps.setString(6, unique);
			    
			    ps.executeUpdate();
			}
		} catch (SQLException | NullPointerException e) {
		    System.out.println(e);
		    return false;
		}
		
		return true;
	}
			
	
	public static boolean fromMySQL(ArrayList<AssignmentInterface> assignIntArr) {
		if(connection == null) {
			connection = DBUtil.getConnection(Gradebook.in);
		}
		
		ArrayList<AssignmentInterface> temp2 = new ArrayList<>();
		
		String getAll = "SELECT * FROM Gradebook";
		
        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(getAll)) {
        	
            while (rs.next()) {
                String type = rs.getString("assignmenttype");
                double score = rs.getDouble("score");
                char letter = (rs.getString("letter")).charAt(0);
                String name = rs.getString("name");
                LocalDate date = LocalDate.parse(rs.getString("date"));
                
                String unique = rs.getString("uniquevariable");

				if(type.equals("Quiz")) {
					temp2.add(new Quiz(score, letter, name, date, Integer.parseInt(unique)));
				}
				else if(type.equals("Program")) {
					temp2.add(new Program(score, letter, name, date, unique));
				}
				else if(type.equals("Discussion")) {
					temp2.add(new Discussion(score, letter, name, date, unique));
				}
            }
      
            //not adding them because arr is 0
            for(int j = 0; j < temp2.size(); j++) {
            	for(int k = 0; k < assignIntArr.size(); k++) {
                	if(((assignIntArr.get(k) instanceof Quiz) && (temp2.get(j) instanceof Quiz) && 
                			assignIntArr.get(k).getScore() == temp2.get(j).getScore() &&
                			(assignIntArr.get(k).getName()).equals(temp2.get(j).getName()) &&
                			assignIntArr.get(k).getLetter() == temp2.get(j).getLetter() &&
                			(assignIntArr.get(k).getDueDate()).compareTo(temp2.get(j).getDueDate()) == 0 &&
                			((Quiz) assignIntArr.get(k)).getNumQuestions() == ((Quiz) temp2.get(j)).getNumQuestions())){
                		temp2.remove(j);
                	}
                	else if(((assignIntArr.get(k) instanceof Program) && (temp2.get(j) instanceof Program) && 
                			assignIntArr.get(k).getScore() == temp2.get(j).getScore() &&
                			(assignIntArr.get(k).getName()).equals(temp2.get(j).getName()) &&
                			assignIntArr.get(k).getLetter() == temp2.get(j).getLetter() &&
                			(assignIntArr.get(k).getDueDate()).compareTo(temp2.get(j).getDueDate()) == 0 &&
                			((Program) assignIntArr.get(k)).getConcept().equals(((Program) temp2.get(j)).getConcept()))){
                    	temp2.remove(j);
                	}
                	else if(((assignIntArr.get(k) instanceof Discussion) && (temp2.get(j) instanceof Discussion) && 
                			assignIntArr.get(k).getScore() == temp2.get(j).getScore() &&
                			(assignIntArr.get(k).getName()).equals(temp2.get(j).getName()) &&
                			assignIntArr.get(k).getLetter() == temp2.get(j).getLetter() &&
                			(assignIntArr.get(k).getDueDate()).compareTo(temp2.get(j).getDueDate()) == 0 &&
                			((Discussion) assignIntArr.get(k)).getReading().equals(((Discussion) temp2.get(j)).getReading()))){
                    	temp2.remove(j);
                	}
            	}
            }
            
            for(int l = 0; l < temp2.size(); l++) {
            	assignIntArr.add(temp2.get(l));
            	Gradebook.currElements++;
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println(e);
            return false;
        }
		
		return true;
	}
	
	public static boolean searchSQL(Scanner in){
		if(connection == null) {
			connection = DBUtil.getConnection(Gradebook.in);
		}
		
		ArrayList<AssignmentInterface> temp = new ArrayList<>();
		int sqlOption = -1;
		
		System.out.println("Search SQL:\n"
				+ "--------------\n"
				+ "\nhow would you like to search through the grades?\n"
				+ "(1) All Quizzes\n"
				+ "(2) All Programs\n"
				+ "(3) All Discussions\n"
				+ "(4) All Grades\t\t(within score range)\n"
				+ "(5) All Grades\t\t(within date range)\n"
				+ "(6) All Grades\t\t(only even)\n");
		
		sqlOption = Gradebook.checkInteger(in, 6, 1, sqlOption);
		
		switch(sqlOption) {
		case 1:
			String getQuiz = "SELECT * FROM Gradebook "
							+ "WHERE assignmenttype LIKE 'Quiz'";
			
	        try (Statement statement = connection.createStatement();
	                ResultSet rs = statement.executeQuery(getQuiz)) {
	            
	            while(rs.next()) {
	                String type = rs.getString("assignmenttype");
	                double score = rs.getDouble("score");
	                char letter = (rs.getString("letter")).charAt(0);
	                String name = rs.getString("name");
	                LocalDate date = LocalDate.parse(rs.getString("date"));
	                String unique = rs.getString("uniquevariable");
	            	
	                temp.add(new Quiz(score, letter, name, date, Integer.parseInt(unique)));
	            }
	        } catch (SQLException | NullPointerException e) {
	            System.out.println(e);
	            return false;
	        }
			break;
		case 2:
			String getProgram = "SELECT * FROM Gradebook "
					+ "WHERE assignmenttype LIKE 'Program'";
	
		    try (Statement statement = connection.createStatement();
		            ResultSet rs = statement.executeQuery(getProgram)) {
		        
		        while(rs.next()) {
		            String type = rs.getString("assignmenttype");
		            double score = rs.getDouble("score");
		            char letter = (rs.getString("letter")).charAt(0);
		            String name = rs.getString("name");
		            LocalDate date = LocalDate.parse(rs.getString("date"));
		            String unique = rs.getString("uniquevariable");
		        	
		            temp.add(new Program(score, letter, name, date, unique));
		        }
		    } catch (SQLException | NullPointerException e) {
		        System.out.println(e);
		        return false;
		    }
			break;
		case 3:
			String getDiscussion = "SELECT * FROM Gradebook "
					+ "WHERE assignmenttype LIKE 'Discussion'";
	
		    try (Statement statement = connection.createStatement();
		            ResultSet rs = statement.executeQuery(getDiscussion)) {
		        
		        while(rs.next()) {
		            String type = rs.getString("assignmenttype");
		            double score = rs.getDouble("score");
		            char letter = (rs.getString("letter")).charAt(0);
		            String name = rs.getString("name");
		            LocalDate date = LocalDate.parse(rs.getString("date"));
		            String unique = rs.getString("uniquevariable");
		        	
		            temp.add(new Discussion(score, letter, name, date, unique));
		        }
		    } catch (SQLException | NullPointerException e) {
		        System.out.println(e);
		        return false;
		    }
			break;
		case 4:
			double min = -1;
			double max = -1;
			
			System.out.println("what is the minimum of the score range you would like?");
			min = Gradebook.checkDouble(in, 0, min);
			
			System.out.println("what is the maximum of the score range you would like?");
			max = Gradebook.checkDouble(in, 0, max);
			
			String getAll = "SELECT * FROM Gradebook";
	
		    try (Statement statement = connection.createStatement();
		            ResultSet rs = statement.executeQuery(getAll)) {
		        
		        while(rs.next()) {
		        	double score = rs.getDouble("score");
		        	
		        	if(score < max && score > min) {
			            String type = rs.getString("assignmenttype");
			            char letter = (rs.getString("letter")).charAt(0);
			            String name = rs.getString("name");
			            LocalDate date = LocalDate.parse(rs.getString("date"));
			            String unique = rs.getString("uniquevariable");
			        	
			            if(type.equals("Quiz")){
			            	temp.add(new Quiz(score, letter, name, date, Integer.parseInt(unique)));
			            }
			            else if(type.equals("Program")) {
		            		temp.add(new Program(score, letter, name, date, unique));
			            }
			            else if(type.equals("Discussion")) {
			            	temp.add(new Discussion(score, letter, name, date, unique));
			            }
		        	}
		        }
		    } catch (SQLException | NullPointerException e) {
		        System.out.println(e);
		        return false;
		    }
			break;
		case 5:
			String min2;
			String max2;
			LocalDate datemin;
			LocalDate datemax;
			
			System.out.println("what is the minimum of the date range you would like?\t\t(XXXX-XX-XX)");
			while(true) {
				try {
					min2 = in.next();
					datemin = LocalDate.parse(min2);
					break;
				}
				catch(DateTimeParseException e) {
					System.out.println("\nthat is not the correct format...(YEAR-MO-DA)\t\t\ttry again!");
				}
			}
			
			System.out.println("what is the maximum of the date range you would like?");
			while(true) {
				try {
					max2 = in.next();
					datemax = LocalDate.parse(max2);
					break;
				}
				catch(DateTimeParseException e) {
					System.out.println("\nthat is not the correct format...(YEAR-MO-DA)\t\t\ttry again!");
				}
			}
			
			String getAll3 = "SELECT * FROM Gradebook";
	
		    try (Statement statement = connection.createStatement();
		            ResultSet rs = statement.executeQuery(getAll3)) {
		        
		        while(rs.next()) {
		        	LocalDate date = LocalDate.parse(rs.getString("date"));
		        	
		        	if(date.compareTo(datemin) > 0 && date.compareTo(datemax) < 0) {
		        		double score = rs.getDouble("score");
			            String type = rs.getString("assignmenttype");
			            char letter = (rs.getString("letter")).charAt(0);
			            String name = rs.getString("name");
			            String unique = rs.getString("uniquevariable");
			        	
			            if(type.equals("Quiz")){
			            	temp.add(new Quiz(score, letter, name, date, Integer.parseInt(unique)));
			            }
			            else if(type.equals("Program")) {
		            		temp.add(new Program(score, letter, name, date, unique));
			            }
			            else if(type.equals("Discussion")) {
			            	temp.add(new Discussion(score, letter, name, date, unique));
			            }
		        	}
		        }
		    } catch (SQLException | NullPointerException e) {
		        System.out.println(e);
		        return false;
		    }
			break;
		case 6:
			String getAll2 = "SELECT * FROM Gradebook";
	
		    try (Statement statement = connection.createStatement();
		            ResultSet rs = statement.executeQuery(getAll2)) {
		        
		        while(rs.next()) {
		        	double score = rs.getDouble("score");
		        	
		        	if(score % 2 == 0) {
			            String type = rs.getString("assignmenttype");
			            char letter = (rs.getString("letter")).charAt(0);
			            String name = rs.getString("name");
			            LocalDate date = LocalDate.parse(rs.getString("date"));
			            String unique = rs.getString("uniquevariable");
			        	
			            if(type.equals("Quiz")){
			            	temp.add(new Quiz(score, letter, name, date, Integer.parseInt(unique)));
			            }
			            else if(type.equals("Program")) {
		            		temp.add(new Program(score, letter, name, date, unique));
			            }
			            else if(type.equals("Discussion")) {
			            	temp.add(new Discussion(score, letter, name, date, unique));
			            }
		        	}
		        }
		    } catch (SQLException | NullPointerException e) {
		        System.out.println(e);
		        return false;
		    }
			break;
		}
		
		System.out.println("\nMySQL Search Results:\n");
		
		int gradebookEntry = 1;
		
		for(int i = 0; i < temp.size(); i++) {
			if(temp.get(i) != null) {
				System.out.println("Gradebook Entry #" + gradebookEntry			//prints all gradebook entries
						+ "\n----------------------------");
				System.out.println(temp.get(i).toString());
				System.out.println("\n");
				gradebookEntry++;
			}
			
		}
		
		temp.clear();
		return true;
	}
}
