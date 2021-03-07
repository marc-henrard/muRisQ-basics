/**
 * Copyright (C) 2018 - present by Marc Henrard.
 */
package marc.henrard.murisq.basics.time;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.joda.beans.Bean;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.collect.array.DoubleArray;

/**
 * Time amalgamation by numerical integration using the trapezium rule between the values and
 * a constant value below the first point.
 * 
 * @author Marc Henrard
 */
@BeanDefinition(factoryName = "of")
public final class TrapeziumRuleIntegral
    implements TimeAmalgamation, ImmutableBean, Serializable { 
  
  /** The method to measure time with a double between two dates or {@link ZonedDateTime}. */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final TimeMeasurement timeMeasurement;
  /** The valuation date and time.*/
  @PropertyDefinition(validate = "notNull")
  private final ZonedDateTime valuationDateTime;

  @Override
  public double amalgamateDates(DoubleArray values, List<LocalDate> dates) {
    int nbDates = dates.size();
    ArgChecker.isTrue(nbDates == values.size(), 
        "dates and values should have the same size");
    double[] times = new double[nbDates];
    for (int loopdates = 0; loopdates < nbDates; loopdates++) {
      times[loopdates] = timeMeasurement.relativeTime(valuationDateTime, dates.get(loopdates));
    }
    return amalgamateTimes(values, times);
  }

  @Override
  public DoubleArray amalgamateDatesRunning(DoubleArray values, List<LocalDate> dates) {
    int nbDates = dates.size();
    ArgChecker.isTrue(nbDates == values.size(),
        "dates and values should have the same size");
    double[] times = new double[nbDates];
    for (int loopdates = 0; loopdates < nbDates; loopdates++) {
      times[loopdates] = timeMeasurement.relativeTime(valuationDateTime, dates.get(loopdates));
    }
    return DoubleArray.ofUnsafe(amalgamateTimesRunning(values, times));
  }

  @Override
  public double amalgamateZonedDates(DoubleArray values, List<ZonedDateTime> dates) {
    int nbDates = dates.size();
    ArgChecker.isTrue(nbDates == values.size(), 
        "dates and values should have the same size");
    double[] times = new double[nbDates];
    for (int loopdates = 0; loopdates < nbDates; loopdates++) {
      times[loopdates] = timeMeasurement.relativeTime(valuationDateTime, dates.get(loopdates));
    }
    return amalgamateTimes(values, times);
  }

  @Override
  public DoubleArray amalgamateZonedDatesRunning(DoubleArray values, List<ZonedDateTime> dates) {
    int nbDates = dates.size();
    ArgChecker.isTrue(nbDates == values.size(),
        "dates and values should have the same size");
    double[] times = new double[nbDates];
    for (int loopdates = 0; loopdates < nbDates; loopdates++) {
      times[loopdates] = timeMeasurement.relativeTime(valuationDateTime, dates.get(loopdates));
    }
    return DoubleArray.ofUnsafe(amalgamateTimesRunning(values, times));
  }

  @Override
  public double amalgamateTimes(DoubleArray values, double[] times) {
    int nbTimes = times.length;
    double integral = times[0] * values.get(0);
    for (int looptime = 0; looptime < nbTimes - 1; looptime++) {
      integral += 0.5 * (values.get(looptime) + values.get(looptime + 1))
          * (times[looptime+1] - times[looptime]);
    }
    return integral;
  }

  @Override
  public double[] amalgamateTimesRunning(DoubleArray values, double[] times) {
    int nbTimes = times.length;
    double[] integrals = new double[nbTimes];
    integrals[0] = times[0] * values.get(0);
    for (int loopdate = 0; loopdate < nbTimes - 1; loopdate++) {
      integrals[loopdate + 1] = integrals[loopdate] + 
          0.5 * (values.get(loopdate) + values.get(loopdate + 1)) * (times[loopdate + 1] - times[loopdate]);
    }
    return integrals;
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code TrapeziumRuleIntegral}.
   * @return the meta-bean, not null
   */
  public static TrapeziumRuleIntegral.Meta meta() {
    return TrapeziumRuleIntegral.Meta.INSTANCE;
  }

  static {
    MetaBean.register(TrapeziumRuleIntegral.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Obtains an instance.
   * @param timeMeasurement  the value of the property, not null
   * @param valuationDateTime  the value of the property, not null
   * @return the instance
   */
  public static TrapeziumRuleIntegral of(
      TimeMeasurement timeMeasurement,
      ZonedDateTime valuationDateTime) {
    return new TrapeziumRuleIntegral(
      timeMeasurement,
      valuationDateTime);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static TrapeziumRuleIntegral.Builder builder() {
    return new TrapeziumRuleIntegral.Builder();
  }

  private TrapeziumRuleIntegral(
      TimeMeasurement timeMeasurement,
      ZonedDateTime valuationDateTime) {
    JodaBeanUtils.notNull(timeMeasurement, "timeMeasurement");
    JodaBeanUtils.notNull(valuationDateTime, "valuationDateTime");
    this.timeMeasurement = timeMeasurement;
    this.valuationDateTime = valuationDateTime;
  }

  @Override
  public TrapeziumRuleIntegral.Meta metaBean() {
    return TrapeziumRuleIntegral.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the method to measure time with a double between two dates or {@link ZonedDateTime}.
   * @return the value of the property, not null
   */
  @Override
  public TimeMeasurement getTimeMeasurement() {
    return timeMeasurement;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the valuation date and time.
   * @return the value of the property, not null
   */
  public ZonedDateTime getValuationDateTime() {
    return valuationDateTime;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      TrapeziumRuleIntegral other = (TrapeziumRuleIntegral) obj;
      return JodaBeanUtils.equal(timeMeasurement, other.timeMeasurement) &&
          JodaBeanUtils.equal(valuationDateTime, other.valuationDateTime);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(timeMeasurement);
    hash = hash * 31 + JodaBeanUtils.hashCode(valuationDateTime);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("TrapeziumRuleIntegral{");
    buf.append("timeMeasurement").append('=').append(JodaBeanUtils.toString(timeMeasurement)).append(',').append(' ');
    buf.append("valuationDateTime").append('=').append(JodaBeanUtils.toString(valuationDateTime));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code TrapeziumRuleIntegral}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code timeMeasurement} property.
     */
    private final MetaProperty<TimeMeasurement> timeMeasurement = DirectMetaProperty.ofImmutable(
        this, "timeMeasurement", TrapeziumRuleIntegral.class, TimeMeasurement.class);
    /**
     * The meta-property for the {@code valuationDateTime} property.
     */
    private final MetaProperty<ZonedDateTime> valuationDateTime = DirectMetaProperty.ofImmutable(
        this, "valuationDateTime", TrapeziumRuleIntegral.class, ZonedDateTime.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "timeMeasurement",
        "valuationDateTime");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -375365969:  // timeMeasurement
          return timeMeasurement;
        case -949589828:  // valuationDateTime
          return valuationDateTime;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public TrapeziumRuleIntegral.Builder builder() {
      return new TrapeziumRuleIntegral.Builder();
    }

    @Override
    public Class<? extends TrapeziumRuleIntegral> beanType() {
      return TrapeziumRuleIntegral.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code timeMeasurement} property.
     * @return the meta-property, not null
     */
    public MetaProperty<TimeMeasurement> timeMeasurement() {
      return timeMeasurement;
    }

    /**
     * The meta-property for the {@code valuationDateTime} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ZonedDateTime> valuationDateTime() {
      return valuationDateTime;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -375365969:  // timeMeasurement
          return ((TrapeziumRuleIntegral) bean).getTimeMeasurement();
        case -949589828:  // valuationDateTime
          return ((TrapeziumRuleIntegral) bean).getValuationDateTime();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code TrapeziumRuleIntegral}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<TrapeziumRuleIntegral> {

    private TimeMeasurement timeMeasurement;
    private ZonedDateTime valuationDateTime;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(TrapeziumRuleIntegral beanToCopy) {
      this.timeMeasurement = beanToCopy.getTimeMeasurement();
      this.valuationDateTime = beanToCopy.getValuationDateTime();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -375365969:  // timeMeasurement
          return timeMeasurement;
        case -949589828:  // valuationDateTime
          return valuationDateTime;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -375365969:  // timeMeasurement
          this.timeMeasurement = (TimeMeasurement) newValue;
          break;
        case -949589828:  // valuationDateTime
          this.valuationDateTime = (ZonedDateTime) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public TrapeziumRuleIntegral build() {
      return new TrapeziumRuleIntegral(
          timeMeasurement,
          valuationDateTime);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the method to measure time with a double between two dates or {@link ZonedDateTime}.
     * @param timeMeasurement  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder timeMeasurement(TimeMeasurement timeMeasurement) {
      JodaBeanUtils.notNull(timeMeasurement, "timeMeasurement");
      this.timeMeasurement = timeMeasurement;
      return this;
    }

    /**
     * Sets the valuation date and time.
     * @param valuationDateTime  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder valuationDateTime(ZonedDateTime valuationDateTime) {
      JodaBeanUtils.notNull(valuationDateTime, "valuationDateTime");
      this.valuationDateTime = valuationDateTime;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("TrapeziumRuleIntegral.Builder{");
      buf.append("timeMeasurement").append('=').append(JodaBeanUtils.toString(timeMeasurement)).append(',').append(' ');
      buf.append("valuationDateTime").append('=').append(JodaBeanUtils.toString(valuationDateTime));
      buf.append('}');
      return buf.toString();
    }

  }

  //-------------------------- AUTOGENERATED END --------------------------
}
