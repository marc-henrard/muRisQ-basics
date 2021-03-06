/**
 * Copyright (C) 2018 - present by Marc Henrard.
 */
package marc.henrard.murisq.basics.value;

import java.io.Serializable;
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

import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.collect.array.DoubleArray;

/**
 * A currency amount and its derivatives with respect to parameters used to compute it.
 * 
 * @author Marc Henrard
 */
@BeanDefinition
public final class CurrencyAmountDerivatives
    implements ImmutableBean, Serializable {

  /** The amount of currency. */
  @PropertyDefinition
  private final double value;
  /** The currency.  */
  @PropertyDefinition(validate = "notNull")
  private final Currency currency;
  /** The derivatives of the variable with respect to parameters. */
  @PropertyDefinition(validate = "notNull")
  private final DoubleArray derivatives;
  
  /**
   * Creates an instance from a value, currency and derivatives.
   * 
   * @param value  the value
   * @param currency  the currency
   * @param derivatives  the derivatives of the value
   * @return the object
   */
  public static CurrencyAmountDerivatives of(double value, Currency currency, DoubleArray derivatives) {
    return new CurrencyAmountDerivatives(value, currency, derivatives);
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code CurrencyAmountDerivatives}.
   * @return the meta-bean, not null
   */
  public static CurrencyAmountDerivatives.Meta meta() {
    return CurrencyAmountDerivatives.Meta.INSTANCE;
  }

  static {
    MetaBean.register(CurrencyAmountDerivatives.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static CurrencyAmountDerivatives.Builder builder() {
    return new CurrencyAmountDerivatives.Builder();
  }

  private CurrencyAmountDerivatives(
      double value,
      Currency currency,
      DoubleArray derivatives) {
    JodaBeanUtils.notNull(currency, "currency");
    JodaBeanUtils.notNull(derivatives, "derivatives");
    this.value = value;
    this.currency = currency;
    this.derivatives = derivatives;
  }

  @Override
  public CurrencyAmountDerivatives.Meta metaBean() {
    return CurrencyAmountDerivatives.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the amount of currency.
   * @return the value of the property
   */
  public double getValue() {
    return value;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currency.
   * @return the value of the property, not null
   */
  public Currency getCurrency() {
    return currency;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the derivatives of the variable with respect to parameters.
   * @return the value of the property, not null
   */
  public DoubleArray getDerivatives() {
    return derivatives;
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
      CurrencyAmountDerivatives other = (CurrencyAmountDerivatives) obj;
      return JodaBeanUtils.equal(value, other.value) &&
          JodaBeanUtils.equal(currency, other.currency) &&
          JodaBeanUtils.equal(derivatives, other.derivatives);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(value);
    hash = hash * 31 + JodaBeanUtils.hashCode(currency);
    hash = hash * 31 + JodaBeanUtils.hashCode(derivatives);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("CurrencyAmountDerivatives{");
    buf.append("value").append('=').append(JodaBeanUtils.toString(value)).append(',').append(' ');
    buf.append("currency").append('=').append(JodaBeanUtils.toString(currency)).append(',').append(' ');
    buf.append("derivatives").append('=').append(JodaBeanUtils.toString(derivatives));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code CurrencyAmountDerivatives}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code value} property.
     */
    private final MetaProperty<Double> value = DirectMetaProperty.ofImmutable(
        this, "value", CurrencyAmountDerivatives.class, Double.TYPE);
    /**
     * The meta-property for the {@code currency} property.
     */
    private final MetaProperty<Currency> currency = DirectMetaProperty.ofImmutable(
        this, "currency", CurrencyAmountDerivatives.class, Currency.class);
    /**
     * The meta-property for the {@code derivatives} property.
     */
    private final MetaProperty<DoubleArray> derivatives = DirectMetaProperty.ofImmutable(
        this, "derivatives", CurrencyAmountDerivatives.class, DoubleArray.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "value",
        "currency",
        "derivatives");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 111972721:  // value
          return value;
        case 575402001:  // currency
          return currency;
        case 979228620:  // derivatives
          return derivatives;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public CurrencyAmountDerivatives.Builder builder() {
      return new CurrencyAmountDerivatives.Builder();
    }

    @Override
    public Class<? extends CurrencyAmountDerivatives> beanType() {
      return CurrencyAmountDerivatives.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code value} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> value() {
      return value;
    }

    /**
     * The meta-property for the {@code currency} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Currency> currency() {
      return currency;
    }

    /**
     * The meta-property for the {@code derivatives} property.
     * @return the meta-property, not null
     */
    public MetaProperty<DoubleArray> derivatives() {
      return derivatives;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 111972721:  // value
          return ((CurrencyAmountDerivatives) bean).getValue();
        case 575402001:  // currency
          return ((CurrencyAmountDerivatives) bean).getCurrency();
        case 979228620:  // derivatives
          return ((CurrencyAmountDerivatives) bean).getDerivatives();
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
   * The bean-builder for {@code CurrencyAmountDerivatives}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<CurrencyAmountDerivatives> {

    private double value;
    private Currency currency;
    private DoubleArray derivatives;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(CurrencyAmountDerivatives beanToCopy) {
      this.value = beanToCopy.getValue();
      this.currency = beanToCopy.getCurrency();
      this.derivatives = beanToCopy.getDerivatives();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 111972721:  // value
          return value;
        case 575402001:  // currency
          return currency;
        case 979228620:  // derivatives
          return derivatives;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 111972721:  // value
          this.value = (Double) newValue;
          break;
        case 575402001:  // currency
          this.currency = (Currency) newValue;
          break;
        case 979228620:  // derivatives
          this.derivatives = (DoubleArray) newValue;
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
    public CurrencyAmountDerivatives build() {
      return new CurrencyAmountDerivatives(
          value,
          currency,
          derivatives);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the amount of currency.
     * @param value  the new value
     * @return this, for chaining, not null
     */
    public Builder value(double value) {
      this.value = value;
      return this;
    }

    /**
     * Sets the currency.
     * @param currency  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder currency(Currency currency) {
      JodaBeanUtils.notNull(currency, "currency");
      this.currency = currency;
      return this;
    }

    /**
     * Sets the derivatives of the variable with respect to parameters.
     * @param derivatives  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder derivatives(DoubleArray derivatives) {
      JodaBeanUtils.notNull(derivatives, "derivatives");
      this.derivatives = derivatives;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(128);
      buf.append("CurrencyAmountDerivatives.Builder{");
      buf.append("value").append('=').append(JodaBeanUtils.toString(value)).append(',').append(' ');
      buf.append("currency").append('=').append(JodaBeanUtils.toString(currency)).append(',').append(' ');
      buf.append("derivatives").append('=').append(JodaBeanUtils.toString(derivatives));
      buf.append('}');
      return buf.toString();
    }

  }

  //-------------------------- AUTOGENERATED END --------------------------
}
