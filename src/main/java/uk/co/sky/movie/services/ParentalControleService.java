/**
 * 
 */
package uk.co.sky.movie.services;

import uk.co.sky.movie.services.exception.ParentalControlLevelException;
import uk.co.sky.movie.services.exception.ParentalControlServiceException;

/**
 * @author Gaurav Nigam
 *
 */
public interface ParentalControleService {

	Boolean isMovieSuitableToWatch(String customerParentalControlLevel, String movieId)throws ParentalControlServiceException,ParentalControlLevelException;
}
