/**
 * Copyright (C) 2018 - present by Marc Henrard.
 */
package marc.henrard.murisq.basics.time;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import com.opengamma.strata.collect.array.DoubleArray;

/**
 * Time amalgamation which does not perform amalgamation.
 * <p>
 * Typically used when the amalgamation process is not yet known.
 * 
 * @author Marc Henrard
 */
public final class TimeAmalgamationVoid 
  implements TimeAmalgamation { 

  @Override
  public double amalgamateDates(DoubleArray values, List<LocalDate> dates) {
    throw new IllegalArgumentException("Amalgamation method not known");
  }

  @Override
  public DoubleArray amalgamateDatesRunning(DoubleArray values, List<LocalDate> dates) {
    throw new IllegalArgumentException("Amalgamation method not known");
  }

  @Override
  public double amalgamateZonedDates(DoubleArray values, List<ZonedDateTime> dates) {
    throw new IllegalArgumentException("Amalgamation method not known");
  }

  @Override
  public DoubleArray amalgamateZonedDatesRunning(DoubleArray values, List<ZonedDateTime> dates) {
    throw new IllegalArgumentException("Amalgamation method not known");
  }

  @Override
  public double amalgamateTimes(DoubleArray values, double[] times) {
    throw new IllegalArgumentException("Amalgamation method not known");
  }

  @Override
  public double[] amalgamateTimesRunning(DoubleArray values, double[] times) {
    throw new IllegalArgumentException("Amalgamation method not known");
  }

  @Override
  public TimeMeasurement getTimeMeasurement() {
    throw new IllegalArgumentException("Amalgamation method not known");
  }
  
}
