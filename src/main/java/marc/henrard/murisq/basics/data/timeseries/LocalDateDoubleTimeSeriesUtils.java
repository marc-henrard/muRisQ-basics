/**
 * Copyright (C) 2017 - present by Marc Henrard.
 */
package marc.henrard.murisq.basics.data.timeseries;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import com.opengamma.strata.collect.timeseries.LocalDateDoublePoint;
import com.opengamma.strata.collect.timeseries.LocalDateDoubleTimeSeries;

/**
 * Utilities for {@link LocalDateDoubleTimeSeries}.
 * 
 * @author Marc Henrard
 */
public class LocalDateDoubleTimeSeriesUtils {
  
  /**
   * Computes the daily volatility of a time series. The volatility is computed as the square root
   * of the sum of the squares of the differences between consecutive points divided by the number of
   * days between the first and last point:
   *   v = sqrt( sum( (v_i+1 - v_i)^2 ) / d)
   *   d = timeSeries.getLatestDate() - timeSeries.getEarliestDate()
   * 
   * @param timeSeries  the time series
   * @return the daily volatility
   */
  public static double dailyVolatility(LocalDateDoubleTimeSeries timeSeries) {
    List<LocalDateDoublePoint> points = timeSeries.stream().collect(Collectors.toList());
    int nbPts = points.size();
    long nbDays = ChronoUnit.DAYS.between(timeSeries.getEarliestDate(), timeSeries.getLatestDate());
    double vol2 = 0.0;
    for (int looppt = 0; looppt < nbPts - 1; looppt++) {
      double change = points.get(looppt + 1).getValue() - points.get(looppt).getValue();
      vol2 += change * change;
    }
    return Math.sqrt(vol2 / nbDays);
  }

}
