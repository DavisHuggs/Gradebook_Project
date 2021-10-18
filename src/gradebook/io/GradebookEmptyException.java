/*
 * Assignment: Gradebook Project
 * Name: Davis Huggins
 */
package gradebook.io;

public class GradebookEmptyException extends Exception{
	public GradebookEmptyException() {}
	
	public GradebookEmptyException(Exception cause) {
		super(cause);
	}
}
