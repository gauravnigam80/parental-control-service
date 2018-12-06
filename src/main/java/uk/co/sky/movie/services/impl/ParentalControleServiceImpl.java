/**
 * 
 */
package uk.co.sky.movie.services.impl;

import uk.co.sky.movie.metadata.exception.TechnicalFailureException;
import uk.co.sky.movie.metadata.exception.TitleNotFoundException;
import uk.co.sky.movie.metadata.services.MovieService;
import uk.co.sky.movie.metadata.services.ParentalControlLevel;
import uk.co.sky.movie.services.ParentalControleService;
import uk.co.sky.movie.services.exception.ParentalControlLevelException;
import uk.co.sky.movie.services.exception.ParentalControlServiceException;

/**
 * @author Gaurav Nigam
 *
 */
public class ParentalControleServiceImpl implements ParentalControleService{

	MovieService movieService ;
	
	
	/**
	 * @param movieService
	 */
	public ParentalControleServiceImpl(MovieService movieService) {
		super();
		this.movieService = movieService;
	}

	/**
	 * The method checks if the movie requested has less or equal to parental control level than customer
	 * parental guidance level.
	 * @param customerParentalControlLevel
	 * @param movieId
	 * @throws ParentalControlServiceException
	 * @throws ParentalControlLevelException
	 */
	public Boolean isMovieSuitableToWatch(String customerParentalControlLevel, String movieId) throws ParentalControlServiceException,ParentalControlLevelException{
		try {
			validateParentalControlLevel(customerParentalControlLevel);
			final String movieParentalControlLevel = movieService.getParentalControlLevel(movieId);
			validateMovieParentalControlLevel(movieParentalControlLevel);
			return isParentalControlLevelAcceptable(ParentalControlLevel.fromValue(customerParentalControlLevel),ParentalControlLevel.fromValue(movieParentalControlLevel));
		}catch(TitleNotFoundException | TechnicalFailureException e) {
			throw new ParentalControlServiceException(e.getMessage());
		}
	}


	/**
	 * The method validates the customer parental control level objects and throws exception if it is invalid
	 * @param customerParentalControlLevel
	 * @throws ParentalControlLevelException
	 */
	private void validateParentalControlLevel(String customerParentalControlLevel) throws ParentalControlLevelException {
		if(customerParentalControlLevel == null || customerParentalControlLevel.trim().isEmpty()){
			throw new ParentalControlLevelException("Customer Parental Control Level is null or empty");
		}
		ParentalControlLevel parentalControlLevel = ParentalControlLevel.fromValue(customerParentalControlLevel);
		if(parentalControlLevel == null) {
			throw new ParentalControlLevelException("Customer Parental Control Level is invalid");
		}
	}

	/**
	 * The method validates the parental control level returned by movie service and throws exception if it is invalid
	 * @param movieParentalControlLevel
	 * @throws ParentalControlLevelException
	 */
	private void validateMovieParentalControlLevel(final String movieParentalControlLevel) throws ParentalControlLevelException {
		if (movieParentalControlLevel == null ||movieParentalControlLevel.trim().isEmpty()) {
			throw new ParentalControlLevelException("Requested Movie Parental Control Level is null or empty");
		}
		ParentalControlLevel parentalControlLevel = ParentalControlLevel.fromValue(movieParentalControlLevel);
		if(parentalControlLevel == null) {
			throw new ParentalControlLevelException("Requested Movie Parental Control Level is invalid");
		}
	}

	/**
	 * This method compares the Customer requested parental control level with Movie parental control level
	 * and returns true if customer parental control level's ordinal value is greater or equal to movie parental
	 * control level's ordinal value 
	 * @param customerParentalControlLevel
	 * @param movieParentalControlLevel
	 * @return Boolean Value
	 */
	private Boolean isParentalControlLevelAcceptable(ParentalControlLevel customerParentalControlLevel, ParentalControlLevel movieParentalControlLevel) {
		return customerParentalControlLevel.ordinal() >= movieParentalControlLevel.ordinal();
	}

}
