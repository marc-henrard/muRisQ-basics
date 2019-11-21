/**
 * Copyright (C) 2018 - present by Marc Henrard.
 */
package marc.henrard.murisq.basics.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.opengamma.strata.collect.array.DoubleArray;

/**
 * Tests {@link TrapeziumRuleIntegral}.
 * 
 * @author Marc Henrard
 */
public class TrapeziumRuleIntegralTest {

  private final static ScaledSecondTime TIME_MEASURE = ScaledSecondTime.DEFAULT;
  private final static ZoneId ZONE = ZoneId.of("Europe/Brussels");
  private final static LocalTime TIME = LocalTime.of(4, 15);
  private final static ZonedDateTime VALUATION_DATE =
      ZonedDateTime.of(LocalDate.of(2018, 8, 18), TIME, ZONE);
  private final static TrapeziumRuleIntegral TRAPEZIUM_RULE =
      TrapeziumRuleIntegral.of(TIME_MEASURE, VALUATION_DATE);
  
  private final static Offset<Double> TOLERANCE_INT = Offset.offset(1.0E-8);
  
  /* Compare trapezium rule integral to a local implementation */
  @Test
  public void amalgamateDates_localDate() {
    DoubleArray values = DoubleArray.of(1.0, 4.0, 2.0, 3.0);
    List<LocalDate> dates = ImmutableList.of(
        LocalDate.of(2018, 9, 19), LocalDate.of(2018, 10, 20), 
        LocalDate.of(2018, 11, 21), LocalDate.of(2018, 12, 22));
    int nbDates = dates.size();
    double[] times = new double[nbDates];
    for (int loopdates = 0; loopdates < nbDates; loopdates++) {
      times[loopdates] = TIME_MEASURE.relativeTime(VALUATION_DATE, dates.get(loopdates));
    }
    double integralExpected = times[0] * values.get(0);
    for (int loopdates = 0; loopdates < nbDates - 1; loopdates++) {
      integralExpected += 0.5 * (values.get(loopdates) + values.get(loopdates + 1))
          * (times[loopdates+1] - times[loopdates]);
    }
    double integralComputed = TRAPEZIUM_RULE.amalgamateDates(values, dates);
    assertThat(integralComputed).isCloseTo(integralExpected, TOLERANCE_INT);
  }

  
  /* Compare trapezium rule running figures to a re-computation of the sub-array */
  @Test
  public void amalgamateDates_localDate_running() {
    DoubleArray values = DoubleArray.of(1.0, 4.0, 2.0, 3.0);
    List<LocalDate> dates = ImmutableList.of(
        LocalDate.of(2018, 9, 19), LocalDate.of(2018, 10, 20),
        LocalDate.of(2018, 11, 21), LocalDate.of(2018, 12, 22));
    DoubleArray integralComputed = TRAPEZIUM_RULE.amalgamateDatesRunning(values, dates);
    int nbDates = dates.size();
    for (int i = 0; i < nbDates; i++) {
      double integralExpected =
          TRAPEZIUM_RULE.amalgamateDates(values.subArray(0, i + 1), dates.subList(0, i + 1));
      assertThat(integralComputed.get(i)).isCloseTo(integralExpected, TOLERANCE_INT);
    }
  }
  
  /* Compare trapezium rule integral to a local implementation */
  @Test
  public void amalgamateDates_zonedDateTime() {
    DoubleArray values = DoubleArray.of(1.0, 4.0, 2.0, 3.0);
    List<ZonedDateTime> dates = ImmutableList.of(
        ZonedDateTime.of(LocalDate.of(2018, 9, 19), TIME, ZONE),
        ZonedDateTime.of(LocalDate.of(2018, 10, 20), TIME, ZONE),
        ZonedDateTime.of(LocalDate.of(2018, 11, 21), TIME, ZONE),
        ZonedDateTime.of(LocalDate.of(2018, 12, 22), TIME, ZONE));
    int nbDates = dates.size();
    double[] times = new double[nbDates];
    for (int loopdates = 0; loopdates < nbDates; loopdates++) {
      times[loopdates] = TIME_MEASURE.relativeTime(VALUATION_DATE, dates.get(loopdates));
    }
    double integralExpected = times[0] * values.get(0);
    for (int loopdates = 0; loopdates < nbDates - 1; loopdates++) {
      integralExpected += 0.5 * (values.get(loopdates) + values.get(loopdates + 1))
          * (times[loopdates+1] - times[loopdates]);
    }
    double integralComputed = TRAPEZIUM_RULE.amalgamateZonedDates(values, dates);
    assertThat(integralComputed).isCloseTo(integralExpected, TOLERANCE_INT);
  }

  /* Compare trapezium rule running figures to a re-computation of the sub-array */
  @Test
  public void amalgamateDates_zonedDateTime_running() {
    DoubleArray values = DoubleArray.of(1.0, 4.0, 2.0, 3.0);
    List<ZonedDateTime> dates = ImmutableList.of(
        ZonedDateTime.of(LocalDate.of(2018, 9, 19), TIME, ZONE),
        ZonedDateTime.of(LocalDate.of(2018, 10, 20), TIME, ZONE),
        ZonedDateTime.of(LocalDate.of(2018, 11, 21), TIME, ZONE),
        ZonedDateTime.of(LocalDate.of(2018, 12, 22), TIME, ZONE));
    DoubleArray integralComputed = TRAPEZIUM_RULE.amalgamateZonedDatesRunning(values, dates);
    int nbDates = dates.size();
    for (int i = 0; i < nbDates; i++) {
      double integralExpected =
          TRAPEZIUM_RULE.amalgamateZonedDates(values.subArray(0, i + 1), dates.subList(0, i + 1));
      assertThat(integralComputed.get(i)).isCloseTo(integralExpected, TOLERANCE_INT);
    }
  }

  /* Check values and dates have same size */
  @Test
  public void amalgamateDates_wrong_size() {
    DoubleArray values = DoubleArray.of(1.0, 4.0, 2.0, 3.0);
    List<LocalDate> dates = ImmutableList.of(
        LocalDate.of(2018, 9, 19), LocalDate.of(2018, 10, 20), 
        LocalDate.of(2018, 11, 21));
    assertThatIllegalArgumentException().isThrownBy(() -> TRAPEZIUM_RULE.amalgamateDates(values, dates));
  }

  /* Check values and dates have same size */
  @Test
  public void amalgamateDates_wrong_size_2() {
    DoubleArray values = DoubleArray.of(1.0, 4.0, 2.0, 3.0);
    List<ZonedDateTime> dates = ImmutableList.of(
        ZonedDateTime.of(LocalDate.of(2018, 9, 19), TIME, ZONE),
        ZonedDateTime.of(LocalDate.of(2018, 10, 20), TIME, ZONE),
        ZonedDateTime.of(LocalDate.of(2018, 11, 21), TIME, ZONE));
    assertThatIllegalArgumentException().isThrownBy(() -> TRAPEZIUM_RULE.amalgamateZonedDates(values, dates));
  }
  
}
