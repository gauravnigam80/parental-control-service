# Parental-Control-Service

Implementation of ParentalControlService API for video on Demand platform.

This project requires:
1. Java 8
2. Junit 4.11
3. Mockito All 1.9.5

## Implementation Details:
This Parental Control Service requires movie service as reference. It has isMovieSuitableToWatch method with customer’s parental control level preference, a movie id and parental control service exception reference as input. If the customer is able to watch the movie, the ParentalControlService indicate this to the calling client with boolean value true or false.
An error object is used to return the error message to the calling client if any error occurs.

Parental Control Service class is implemented at uk.co.sky.movie.services.impl.ParentalControleServiceImpl

## Test Implementation:
Unit Test class is provided at test/us.co.sky.service.ParentalControlServiceTest

## Running the Test
The Parental Control Service implementation can be tested by running the ParentalControlServiceTest as JUnit.
