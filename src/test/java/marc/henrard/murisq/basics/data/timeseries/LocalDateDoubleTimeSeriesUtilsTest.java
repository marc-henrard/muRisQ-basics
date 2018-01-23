/**
 * Copyright (C) 2017 - present by Marc Henrard.
 */
package marc.henrard.murisq.basics.data.timeseries;

import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.opengamma.strata.collect.array.DoubleArray;
import com.opengamma.strata.collect.timeseries.LocalDateDoubleTimeSeries;
import com.opengamma.strata.collect.timeseries.LocalDateDoubleTimeSeriesBuilder;

import marc.henrard.murisq.basics.data.timeseries.LocalDateDoubleTimeSeriesUtils;

/**
 * Tests {@link LocalDateDoubleTimeSeriesUtils}.
 * 
 * @author Marc Henrard
 */
@Test
public class LocalDateDoubleTimeSeriesUtilsTest {

  private static final List<LocalDate> DATES = ImmutableList.of(
      LocalDate.of(2017, 8, 18), LocalDate.of(2017, 8, 21), LocalDate.of(2017, 8, 22),
      LocalDate.of(2017, 8, 24), LocalDate.of(2017, 8, 25), LocalDate.of(2017, 8, 28));
  private static final int NB_DATES = DATES.size();
  private static final DoubleArray VALUES_1 = DoubleArray.of(0.5, 0.6, 0.7, 0.5, 0.4, 0.3);
  private static final DoubleArray VALUES_2 = DoubleArray.of(0.6, 0.7, 0.5, 0.4, 0.3, 0.4);
  private static final LocalDateDoubleTimeSeries TS_1;
  private static final LocalDateDoubleTimeSeries TS_2;
  static {
    LocalDateDoubleTimeSeriesBuilder builder1 = LocalDateDoubleTimeSeries.builder();
    LocalDateDoubleTimeSeriesBuilder builder2 = LocalDateDoubleTimeSeries.builder();
    for (int i = 0; i < NB_DATES; i++) {
      builder1.put(DATES.get(i), VALUES_1.get(i));
      builder2.put(DATES.get(i), VALUES_2.get(i));
    }
    TS_1 = builder1.build();
    TS_2 = builder2.build();
  }
  
  private static final double TOLERANCE_VOL = 1.0E-8;
  private static final double TOLERANCE_COR = 1.0E-8;
  
  public void dailyVolatility() {
    long nbDays = ChronoUnit.DAYS.between(DATES.get(0), DATES.get(NB_DATES-1));
    double var = 0.0;
    for (int i = 0; i < NB_DATES-1; i++) {
      double change = VALUES_1.get(i+1) - VALUES_1.get(i);
      var += change * change;
    }
    double volExpected = Math.sqrt(var / nbDays);
    double volComputed = LocalDateDoubleTimeSeriesUtils.dailyVolatility(TS_1);
    assertEquals(volComputed, volExpected, TOLERANCE_VOL);
  }

  public void correlation() {
    long nbDays = ChronoUnit.DAYS.between(DATES.get(0), DATES.get(NB_DATES-1));
    double covar = 0.0;
    for (int i = 0; i < NB_DATES - 1; i++) {
      double change1 = VALUES_1.get(i + 1) - VALUES_1.get(i);
      double change2 = VALUES_2.get(i + 1) - VALUES_2.get(i);
      covar += change1 * change2;
    }
    double vol1 = LocalDateDoubleTimeSeriesUtils.dailyVolatility(TS_1);
    double vol2 = LocalDateDoubleTimeSeriesUtils.dailyVolatility(TS_2);
    double corExpected = covar / nbDays / (vol1 * vol2);
    double corComputed = LocalDateDoubleTimeSeriesUtils.correlation(TS_1, TS_2);
    assertEquals(corComputed, corExpected, TOLERANCE_COR);
    double corComputedOrder = LocalDateDoubleTimeSeriesUtils.correlation(TS_2, TS_1);
    assertEquals(corComputed, corComputedOrder, TOLERANCE_COR);
    double corComputed1 = LocalDateDoubleTimeSeriesUtils.correlation(TS_1, TS_1);
    assertEquals(corComputed1, 1.0d, TOLERANCE_COR);
  }
  
  
  
}
