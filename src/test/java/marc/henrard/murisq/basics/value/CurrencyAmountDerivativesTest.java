/**
 * Copyright (C) 2018 - present by Marc Henrard.
 */
package marc.henrard.murisq.basics.value;

import static com.opengamma.strata.collect.TestHelper.assertSerialization;
import static com.opengamma.strata.collect.TestHelper.coverBeanEquals;
import static com.opengamma.strata.collect.TestHelper.coverImmutableBean;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.value.ValueDerivatives;
import com.opengamma.strata.collect.array.DoubleArray;

/**
 * Tests {@link CurrencyAmountDerivatives}.
 * 
 * @author Marc Henrard
 */
@Test
public class CurrencyAmountDerivativesTest {
  
  private static final double VALUE = 421.5;
  private static final Currency CCY = Currency.EUR;
  private static final DoubleArray DERIVATIVES = DoubleArray.of(1.0, 2.0, 3.0);
  
  private static final double TOLERANCE = 1.0E-8;

  public void of() {
    CurrencyAmountDerivatives test = CurrencyAmountDerivatives.of(VALUE, CCY, DERIVATIVES);
    assertEquals(test.getValue(), VALUE, TOLERANCE);
    assertEquals(test.getCurrency(), CCY);
    assertTrue(test.getDerivatives().equalWithTolerance(DERIVATIVES, TOLERANCE));
  }

  public void coverage() {
    CurrencyAmountDerivatives test = CurrencyAmountDerivatives.of(VALUE, CCY, DERIVATIVES);
    coverImmutableBean(test);
    assertNotNull(CurrencyAmountDerivatives.meta());
    CurrencyAmountDerivatives test2 = 
        CurrencyAmountDerivatives.of(421.5, Currency.EUR, DoubleArray.of(1.0, 2.0, 3.0));
    coverBeanEquals(test, test2);
  }

  public void test_serialization() {
    ValueDerivatives test = ValueDerivatives.of(VALUE, DERIVATIVES);
    assertSerialization(test);
  }
  
}
