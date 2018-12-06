/**
 * 
 */
package uk.co.sky.movie.services.exception;

/**
 * @author Gaurav Nigam
 *
 */
public class ParentalControlServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParentalControlServiceException(String errorMessage) {
		super(errorMessage);
	}	
}
