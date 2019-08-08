import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.studienarbeit.CurrencyInformation;
import com.studienarbeit.Demo;

public class MyTest {
    @Test
    public void test_method_1() {
        Demo d = new Demo();
        d.DoSomething(true);
    }

    @Test
    public void checkCurrencyInformationValues() {
    	
    	CurrencyInformation currencyInformation = new CurrencyInformation(); //check whether Class is available
    	
    	assertEquals("Check whether euro_chf exchange value is correct", 1.09, CurrencyInformation.getEuro_chf_course(), 0);
    	assertEquals("Check whether euro_dollar exchange value is correct", 1.12, CurrencyInformation.getEuro_dollar_course(), 0);
    	assertEquals("Check whether euro_pound exchange value is correct", 0.92, CurrencyInformation.getEuro_pound_course(), 0);
    	assertEquals("Check whether dollar_euro exchange value is correct", 0.89, CurrencyInformation.getDollar_euro_course(), 0);
    	assertEquals("Check whether chf_euro exchange value is correct", 0.91, CurrencyInformation.getChf_euro_course(), 0);
    	assertEquals("Check whether pound_euro exchange value is correct", 1.08, CurrencyInformation.getPound_euro_course(), 0);    	
    }
}