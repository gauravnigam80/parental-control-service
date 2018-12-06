/**
 * 
 */
package uk.co.sky.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.sky.movie.metadata.exception.TechnicalFailureException;
import uk.co.sky.movie.metadata.exception.TitleNotFoundException;
import uk.co.sky.movie.metadata.services.MovieService;
import uk.co.sky.movie.metadata.services.ParentalControlLevel;
import uk.co.sky.movie.services.ParentalControleService;
import uk.co.sky.movie.services.exception.ParentalControlLevelException;
import uk.co.sky.movie.services.exception.ParentalControlServiceException;
import uk.co.sky.movie.services.impl.ParentalControleServiceImpl;

/**
 * @author Gaurav Nigam
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ParentalControlServiceTest {

	@Mock
	private MovieService movieService;

	private ParentalControleService service = null;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();


	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		service = new ParentalControleServiceImpl(movieService);
	}

	@Test
	public void testTechnicalFailureException() throws TitleNotFoundException, TechnicalFailureException, ParentalControlServiceException, ParentalControlLevelException {
		expectedException.expect(ParentalControlServiceException.class);
		expectedException.expectMessage("Technical failure happened");
		when(movieService.getParentalControlLevel(Mockito.anyString())).thenThrow(new TechnicalFailureException("Technical failure happened"));
		
		service.isMovieSuitableToWatch("PG", "1");
	}

	@Test
	public void testTitleNotFoundException() throws ParentalControlServiceException, ParentalControlLevelException, TitleNotFoundException, TechnicalFailureException{
		expectedException.expect(ParentalControlServiceException.class);
		expectedException.expectMessage("Movie Title not found");
		when(movieService.getParentalControlLevel(Mockito.anyString())).thenThrow(new TitleNotFoundException("Movie Title not found"));

		service.isMovieSuitableToWatch("PG", "1");
	}
	
	@Test
	public void testCustomerRequestedParentalControlLevelIsNull() throws TitleNotFoundException, TechnicalFailureException, ParentalControlServiceException, ParentalControlLevelException {
		expectedException.expect(ParentalControlLevelException.class);
		expectedException.expectMessage("Customer Parental Control Level is null or empty");

		when(movieService.getParentalControlLevel(Mockito.anyString())).thenReturn("PG");
		
		//When Customer parental control level is null
		service.isMovieSuitableToWatch(null, "1");
	}

	@Test
	public void testCustomerRequestedParentalControlLevelIsEmpty() throws TitleNotFoundException, TechnicalFailureException, ParentalControlServiceException, ParentalControlLevelException {
		expectedException.expect(ParentalControlLevelException.class);
		expectedException.expectMessage("Customer Parental Control Level is null or empty");
		
		when(movieService.getParentalControlLevel(Mockito.anyString())).thenReturn("PG");
		
		//When Customer parental control level is empty
		service.isMovieSuitableToWatch("", "1");
	}

	@Test
	public void testCustomerRequestedParentalControlLevelIsNotValid() throws TitleNotFoundException, TechnicalFailureException, ParentalControlServiceException, ParentalControlLevelException {
		expectedException.expect(ParentalControlLevelException.class);
		expectedException.expectMessage("Customer Parental Control Level is invalid");
		when(movieService.getParentalControlLevel(Mockito.anyString())).thenReturn(ParentalControlLevel.LEVEL_18.getLevel());
		
		service.isMovieSuitableToWatch("30", "1");
	}

	@Test
	public void testRequestedMovieParentalControlLevelIsNull() throws TitleNotFoundException, TechnicalFailureException, ParentalControlServiceException, ParentalControlLevelException {
		expectedException.expect(ParentalControlLevelException.class);
		expectedException.expectMessage("Requested Movie Parental Control Level is null or empty");

		when(movieService.getParentalControlLevel(Mockito.anyString())).thenReturn(null);
		
		//When requested movie parental control level is null
		service.isMovieSuitableToWatch("PG", "1");
	}

	@Test
	public void testRequestedMovieRequestedParentalControlLevelIsEmpty() throws TitleNotFoundException, TechnicalFailureException, ParentalControlServiceException, ParentalControlLevelException {
		expectedException.expect(ParentalControlLevelException.class);
		expectedException.expectMessage("Requested Movie Parental Control Level is null or empty");

		when(movieService.getParentalControlLevel(Mockito.anyString())).thenReturn("");
		
		//When requested movie parental control level is empty
		service.isMovieSuitableToWatch("PG", "1");
	}

	@Test
	public void testRequestedMovieParentalControlLevelIsInvalid() throws TitleNotFoundException, TechnicalFailureException, ParentalControlServiceException, ParentalControlLevelException {
		expectedException.expect(ParentalControlLevelException.class);
		expectedException.expectMessage("Requested Movie Parental Control Level is invalid");

		when(movieService.getParentalControlLevel(Mockito.anyString())).thenReturn("PG12");
		
		//When requested movie parental control level is null
		service.isMovieSuitableToWatch("PG", "1");
	}

	@Test
	public void testCustomerRequestedParentalControlLevelIsLessThanMovieParentalControlLevel() throws TitleNotFoundException, TechnicalFailureException, ParentalControlServiceException, ParentalControlLevelException {
		when(movieService.getParentalControlLevel(Mockito.anyString())).thenReturn(ParentalControlLevel.LEVEL_18.getLevel());
		
		Boolean isMovieSuitableToWatch = service.isMovieSuitableToWatch(ParentalControlLevel.LEVEL_PG.getLevel(), "1");
		assertTrue("Expected false but returned true", Boolean.FALSE == isMovieSuitableToWatch );
	}

	@Test
	public void testCustomerRequestedParentalControlLevelIsEqualToMovieParentalControlLevel() throws TitleNotFoundException, TechnicalFailureException, ParentalControlServiceException, ParentalControlLevelException {
		when(movieService.getParentalControlLevel(Mockito.anyString())).thenReturn(ParentalControlLevel.LEVEL_18.getLevel());
		
		Boolean isMovieSuitableToWatch = service.isMovieSuitableToWatch(ParentalControlLevel.LEVEL_18.getLevel(), "1");
		assertTrue("Expected true but returned false", Boolean.TRUE == isMovieSuitableToWatch );
	}

	@Test
	public void testCustomerRequestedParentalControlLevelIsGreaterThanMovieParentalControlLevel() throws TitleNotFoundException, TechnicalFailureException, ParentalControlServiceException, ParentalControlLevelException {
		when(movieService.getParentalControlLevel(Mockito.anyString())).thenReturn(ParentalControlLevel.LEVEL_15.getLevel());
		
		Boolean isMovieSuitableToWatch = service.isMovieSuitableToWatch(ParentalControlLevel.LEVEL_18.getLevel(), "1");
		assertTrue("Expected true but returned false", Boolean.TRUE == isMovieSuitableToWatch );
	}

}
