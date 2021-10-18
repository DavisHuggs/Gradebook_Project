/*
 * Assignment: Gradebook Project
 * Name: Davis Huggins
 */
package gradebook.io;

public class InvalidGradeException extends Exception{
	public InvalidGradeException() {}
	
	public InvalidGradeException(Exception cause) {
		super(cause);
	}
}
