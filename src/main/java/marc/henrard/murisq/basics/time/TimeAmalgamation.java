/**
 * Copyright (C) 2018 - present by Marc Henrard.
 */
package marc.henrard.murisq.basics.time;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import com.opengamma.strata.collect.array.DoubleArray;

/**
 * Description on the way different values should be amalgamated through time.
 * <p>
 * Typical implementation will be some king of numerical integration.
 * 
 * @author Marc Henrard
 */
public interface TimeAmalgamation {
  
  /**
   * Amalgamate the values defined on a list of {@link LocalDate} and return the total amalgamation.
   * 
   * @param values  the values
   * @param dates  the dates
   * @return the amalgamated value
   */
  public double amalgamateDates(DoubleArray values, List<LocalDate> dates);
  
  /**
   * Amalgamate the values defined on a list of {@link LocalDate} and return the running total 
   * at each date.
   * 
   * @param values  the values
   * @param dates  the dates
   * @return the amalgamated values
   */
  public DoubleArray amalgamateDatesRunning(DoubleArray values, List<LocalDate> dates);
  
  /**
   * Amalgamate the values defined on a list of {@link ZonedDateTime} and return the total amalgamation.
   * 
   * @param values  the values
   * @param dates  the dates
   * @return the amalgamated value
   */
  public double amalgamateZonedDates(DoubleArray values, List<ZonedDateTime> dates);
  
  /**
   * Amalgamate the values defined on a list of {@link ZonedDateTime} and return the running total 
   * at each date.
   * 
   * @param values  the values
   * @param dates  the dates
   * @return the amalgamated values
   */
  public DoubleArray amalgamateZonedDatesRunning(DoubleArray values, List<ZonedDateTime> dates);
  
  /**
   * Amalgamate the values defined on an array of times and return the total amalgamation.
   * <p>
   * The times must be coherent with the time measurement. 
   * 
   * @param values  the values
   * @param times  the times
   * @return the amalgamated value
   */
  public double amalgamateTimes(DoubleArray values, double[] times);
  
  /**
   * Amalgamate the values defined on array of times and return the running total at each date.
   * <p>
   * The times must be coherent with the time measurement. 
   * 
   * @param values  the values
   * @param times  the times
   * @return the amalgamated values
   */
  public double[] amalgamateTimesRunning(DoubleArray values, double[] times);
  
  /**
   * Returns the underlying time measurement mechanism.
   * 
   * @return the time measurement
   */
  public TimeMeasurement getTimeMeasurement();

}
