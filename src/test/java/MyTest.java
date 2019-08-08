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
			
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			Mockito.when(response.getWriter()).thenReturn(pw);
			bufferedReader = null;
			bufferedReader = new BufferedReader(
					new StringReader("{\n" + "	\"requestedOperation\" : \"calculateValue\",\n"
							+ "	\"requestedCourse\": \"euro_dollar\",\n" + "	\"sourceValue\": 2\n" + "	\n" + "}"));
			
			Mockito.when(request.getReader()).thenReturn(bufferedReader);
			new CurrencyExchange().doPost(request, response);
			assertEquals("Currency exchange euro_dollar",
					"{\"exchangeCourse\":1.12,\"calculatedValue\":2.24,\"requestOperation\":\"calculateValue\",\"requestedCourse\":\"euro_dollar\"}",
					sw.toString());
			
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			Mockito.when(response.getWriter()).thenReturn(pw);
			bufferedReader = null;
			bufferedReader = new BufferedReader(
					new StringReader("{\n" + "	\"requestedOperation\" : \"calculateValue\",\n"
							+ "	\"requestedCourse\": \"euro_chf\",\n" + "	\"sourceValue\": 2\n" + "	\n" + "}"));
			
			Mockito.when(request.getReader()).thenReturn(bufferedReader);
			new CurrencyExchange().doPost(request, response);
			assertEquals("Currency exchange euro_chf",
					"{\"exchangeCourse\":1.09,\"calculatedValue\":2.18,\"requestOperation\":\"calculateValue\",\"requestedCourse\":\"euro_chf\"}",
					sw.toString());
			
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			Mockito.when(response.getWriter()).thenReturn(pw);
			bufferedReader = null;
			bufferedReader = new BufferedReader(
					new StringReader("{\n" + "	\"requestedOperation\" : \"calculateValue\",\n"
							+ "	\"requestedCourse\": \"chf_euro\",\n" + "	\"sourceValue\": 2\n" + "	\n" + "}"));
			
			Mockito.when(request.getReader()).thenReturn(bufferedReader);
			new CurrencyExchange().doPost(request, response);
			assertEquals("Currency exchange chf_euro",
					"{\"exchangeCourse\":0.91,\"calculatedValue\":1.82,\"requestOperation\":\"calculateValue\",\"requestedCourse\":\"chf_euro\"}",
					sw.toString());
			
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			Mockito.when(response.getWriter()).thenReturn(pw);
			bufferedReader = null;
			bufferedReader = new BufferedReader(
					new StringReader("{\n" + "	\"requestedOperation\" : \"calculateValue\",\n"
							+ "	\"requestedCourse\": \"dollar_euro\",\n" + "	\"sourceValue\": 2\n" + "	\n" + "}"));
			
			Mockito.when(request.getReader()).thenReturn(bufferedReader);
			new CurrencyExchange().doPost(request, response);
			assertEquals("Currency exchange dollar_euro",
					"{\"exchangeCourse\":0.89,\"calculatedValue\":1.78,\"requestOperation\":\"calculateValue\",\"requestedCourse\":\"dollar_euro\"}",
					sw.toString());
			
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			Mockito.when(response.getWriter()).thenReturn(pw);
			bufferedReader = null;
			bufferedReader = new BufferedReader(
					new StringReader("{\n" + "	\"requestedOperation\" : \"calculateValue\",\n"
							+ "	\"requestedCourse\": \"pound_euro\",\n" + "	\"sourceValue\": 2\n" + "	\n" + "}"));
			
			Mockito.when(request.getReader()).thenReturn(bufferedReader);
			new CurrencyExchange().doPost(request, response);
			assertEquals("Currency exchange pound_euro",
					"{\"exchangeCourse\":1.08,\"calculatedValue\":2.16,\"requestOperation\":\"calculateValue\",\"requestedCourse\":\"pound_euro\"}",
					sw.toString());

		} catch (ServletException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	@Test
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
	
	@Test
	public void testServletGetValueEuroDollarPostOperation() {

		try {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			Mockito.when(response.getWriter()).thenReturn(pw);

			// BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
			BufferedReader bufferedReader = new BufferedReader(
					new StringReader("{\n" + "	\"requestedOperation\" : \"getCourse\",\n"
							+ "	\"requestedCourse\": \"euro_dollar\"\n" + "	\n" + "}"));
			Mockito.when(request.getReader()).thenReturn(bufferedReader);
			new CurrencyExchange().doPost(request, response);
			assertEquals("Get exchange value euro_pound",
					"{\"requestedOperation\":\"getCourse\",\"exchangeCourse\":1.12,\"requestedCourse\":\"euro_dollar\"}",
					sw.toString());

		} catch (ServletException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	@Test
	public void testServletGetValueEuroCHFPostOperation() {

		try {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			Mockito.when(response.getWriter()).thenReturn(pw);

			// BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
			BufferedReader bufferedReader = new BufferedReader(
					new StringReader("{\n" + "	\"requestedOperation\" : \"getCourse\",\n"
							+ "	\"requestedCourse\": \"euro_chf\"\n" + "	\n" + "}"));
			Mockito.when(request.getReader()).thenReturn(bufferedReader);
			new CurrencyExchange().doPost(request, response);
			assertEquals("Get exchange value euro_pound",
					"{\"requestedOperation\":\"getCourse\",\"exchangeCourse\":1.09,\"requestedCourse\":\"euro_chf\"}",
					sw.toString());

		} catch (ServletException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	@Test
	public void testServletGetValueCHFEuroPostOperation() {

		try {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			Mockito.when(response.getWriter()).thenReturn(pw);

			// BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
			BufferedReader bufferedReader = new BufferedReader(
					new StringReader("{\n" + "	\"requestedOperation\" : \"getCourse\",\n"
							+ "	\"requestedCourse\": \"chf_euro\"\n" + "	\n" + "}"));
			Mockito.when(request.getReader()).thenReturn(bufferedReader);
			new CurrencyExchange().doPost(request, response);
			assertEquals("Get exchange value euro_pound",
					"{\"requestedOperation\":\"getCourse\",\"exchangeCourse\":0.91,\"requestedCourse\":\"chf_euro\"}",
					sw.toString());

		} catch (ServletException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	@Test
	public void testServletGetValueDollarEuroPostOperation() {

		try {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			Mockito.when(response.getWriter()).thenReturn(pw);

			// BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
			BufferedReader bufferedReader = new BufferedReader(
					new StringReader("{\n" + "	\"requestedOperation\" : \"getCourse\",\n"
							+ "	\"requestedCourse\": \"dollar_euro\"\n" + "	\n" + "}"));
			Mockito.when(request.getReader()).thenReturn(bufferedReader);
			new CurrencyExchange().doPost(request, response);
			assertEquals("Get exchange value euro_pound",
					"{\"requestedOperation\":\"getCourse\",\"exchangeCourse\":0.89,\"requestedCourse\":\"dollar_euro\"}",
					sw.toString());

		} catch (ServletException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	@Test
	public void testServletGetValuePoundEuroPostOperation() {

		try {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			Mockito.when(response.getWriter()).thenReturn(pw);

			// BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
			BufferedReader bufferedReader = new BufferedReader(
					new StringReader("{\n" + "	\"requestedOperation\" : \"getCourse\",\n"
							+ "	\"requestedCourse\": \"pound_euro\"\n" + "	\n" + "}"));
			Mockito.when(request.getReader()).thenReturn(bufferedReader);
			new CurrencyExchange().doPost(request, response);
			assertEquals("Get exchange value euro_pound",
					"{\"requestedOperation\":\"getCourse\",\"exchangeCourse\":1.08,\"requestedCourse\":\"pound_euro\"}",
					sw.toString());

		} catch (ServletException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}