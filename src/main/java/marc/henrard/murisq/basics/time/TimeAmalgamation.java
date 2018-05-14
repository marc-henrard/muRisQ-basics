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
   * Amalgamate the values defined on a list of {@link LocalDate}.
   * 
   * @param values  the values
   * @param dates  the dates
   * @return the amalgamated value
   */
  public double amalgamateDates(DoubleArray values, List<LocalDate> dates);
  
  /**
   * Amalgamate the values defined on a list of {@link ZonedDateTime}.
   * 
   * @param values  the values
   * @param dates  the dates
   * @return the amalgamated value
   */
  public double amalgamateZonedDates(DoubleArray values, List<ZonedDateTime> dates);
  
  /**
   * Returns the underlying time measurement mechanism.
   * 
   * @return the time measurement
   */
  public TimeMeasurement getTimeMeasurement();

}
