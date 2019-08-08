import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.studienarbeit.CurrencyExchange;
import com.studienarbeit.CurrencyInformation;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MyTest {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;
	@Mock
	ServletOutputStream stream;

	@Mock
	RequestDispatcher rd;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void checkCurrencyInformationValues() {

		CurrencyInformation currencyInformation = new CurrencyInformation(); // check whether Class is available

		assertEquals("Check whether euro_chf exchange value is correct", 1.09, CurrencyInformation.getEuro_chf_course(),
				0);
		assertEquals("Check whether euro_dollar exchange value is correct", 1.12,
				CurrencyInformation.getEuro_dollar_course(), 0);
		assertEquals("Check whether euro_pound exchange value is correct", 0.92,
				CurrencyInformation.getEuro_pound_course(), 0);
		assertEquals("Check whether dollar_euro exchange value is correct", 0.89,
				CurrencyInformation.getDollar_euro_course(), 0);
		assertEquals("Check whether chf_euro exchange value is correct", 0.91, CurrencyInformation.getChf_euro_course(),
				0);
		assertEquals("Check whether pound_euro exchange value is correct", 1.08,
				CurrencyInformation.getPound_euro_course(), 0);
	}

	@Test
	public void testServletExchangeRateGetOperation() {

		try {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			Mockito.when(response.getWriter()).thenReturn(pw);
			new CurrencyExchange().doGet(request, response);
			assertTrue(sw.toString().contains("I am running"));
			Mockito.verify(response).setContentType("plain/text");

		} catch (ServletException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void testServletCalculatedValuePostOperation() {

		try {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			Mockito.when(response.getWriter()).thenReturn(pw);

			// BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
			BufferedReader bufferedReader = new BufferedReader(
					new StringReader("{\n" + "	\"requestedOperation\" : \"calculateValue\",\n"
							+ "	\"requestedCourse\": \"euro_pound\",\n" + "	\"sourceValue\": 2\n" + "	\n" + "}"));
			Mockito.when(request.getReader()).thenReturn(bufferedReader);
			new CurrencyExchange().doPost(request, response);
			assertEquals("Currency exchange euro_pound",
					"{\"exchangeCourse\":0.92,\"calculatedValue\":1.84,\"requestOperation\":\"calculateValue\",\"requestedCourse\":\"euro_pound\"}",
					sw.toString());

		} catch (ServletException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void testServletGetValueEuroPoundPostOperation() {

		try {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			Mockito.when(response.getWriter()).thenReturn(pw);

			// BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
			BufferedReader bufferedReader = new BufferedReader(
					new StringReader("{\n" + "	\"requestedOperation\" : \"getCourse\",\n"
							+ "	\"requestedCourse\": \"euro_pound\"\n" + "	\n" + "}"));
			Mockito.when(request.getReader()).thenReturn(bufferedReader);
			new CurrencyExchange().doPost(request, response);
			assertEquals("Get exchange value euro_pound",
					"{\"requestedOperation\":\"getCourse\",\"exchangeCourse\":0.92,\"requestedCourse\":\"euro_pound\"}",
					sw.toString());

		} catch (ServletException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}