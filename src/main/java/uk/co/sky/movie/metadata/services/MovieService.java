/**
 * 
 */
package uk.co.sky.movie.metadata.services;

import uk.co.sky.movie.metadata.exception.TechnicalFailureException;
import uk.co.sky.movie.metadata.exception.TitleNotFoundException;

/**
 * @author Gaurav Nigam
 *
 */
public interface MovieService {

	String getParentalControlLevel(String movieId)
			throws TitleNotFoundException,
			TechnicalFailureException;
}
