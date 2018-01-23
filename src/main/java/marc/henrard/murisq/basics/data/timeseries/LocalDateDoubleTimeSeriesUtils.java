/**
 * Copyright (C) 2017 - present by Marc Henrard.
 */
package marc.henrard.murisq.basics.data.timeseries;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.collect.timeseries.LocalDateDoublePoint;
import com.opengamma.strata.collect.timeseries.LocalDateDoubleTimeSeries;

/**
 * Utilities for {@link LocalDateDoubleTimeSeries}.
 * 
 * @author Marc Henrard
 */
public class LocalDateDoubleTimeSeriesUtils {
  
  /**
   * Computes the daily volatility of a time series. 
   * <p>
   * The volatility is computed as the square root of the sum of the squares of the differences 
   * between consecutive points divided by the number of days between the first and last point:
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
  
  /**
   * Computes the correlation of a two time series. 
   * <p>
   * The correlation is computed as the square root
   * of the sum of the squares of the differences between consecutive points divided by the number of
   * days between the first and last point:
   *   delta_{j,i} = v_{j,i+1} - v_{j,i}  j=1,2
   *   var_j = sum_i(delta_{j,i}^2)
   *   rho = sum_i(delta_{1,i}*delta_{2,i}) / sqrt(var_1 * var_2)
   * 
   * @param timeSeries  the time series
   * @return the correlation
   */
  public static double correlation(
      LocalDateDoubleTimeSeries timeSeries1, 
      LocalDateDoubleTimeSeries timeSeries2) {
    List<LocalDateDoublePoint> points1 = timeSeries1.stream().collect(Collectors.toList());
    List<LocalDateDoublePoint> points2 = timeSeries2.stream().collect(Collectors.toList());
    int nbPts = points1.size();
    ArgChecker.isTrue(points2.size() == nbPts, "time series must have the same length");
    double var_1 = 0.0;
    double var_2 = 0.0;
    double cov = 0.0;
    for (int looppt = 0; looppt < nbPts - 1; looppt++) {
      double change1 = points1.get(looppt + 1).getValue() - points1.get(looppt).getValue();
      double change2 = points2.get(looppt + 1).getValue() - points2.get(looppt).getValue();
      var_1 += change1 * change1;
      var_2 += change2 * change2;
      cov += change1 * change2;
    }
    return cov / Math.sqrt(var_1 * var_2);
  }

}
