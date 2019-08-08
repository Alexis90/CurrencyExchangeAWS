package com.studienarbeit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class CurrencyExchange
 */

public class CurrencyExchange extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String operationGetCourse = "getCourse";

	/**
	 * Default constructor.
	 */
	public CurrencyExchange() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		
		PrintWriter out = response.getWriter();
		
		JSONObject jsonResponse = null;
		StringBuffer sb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				sb.append(line);
		} catch (Exception e) {
			/* report an error */ }

		try {
			String jsonString = sb.toString();
			JSONObject jsonObject = new JSONObject(jsonString);

			String operation = jsonObject.getString("requestType");

			switch (operation) {
			case operationGetCourse:
				try {
				Double requestedCourse = this.getExchangeCourse(jsonObject.getString("requestedCourse"));
				jsonResponse = new JSONObject();
				jsonResponse.put("requestType", operationGetCourse);
				jsonResponse.put("requestedCourse", jsonObject.getString("requestedCourse"));
				jsonResponse.put("value", requestedCourse);
				break;
				} catch(NotFoundException e) {
					
					e.printStackTrace();
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);	
					out.print("The course requested is not available");
					out.flush();
					out.close();
					return;
				}

			default:
				break;
			}

		} catch (JSONException e) {			
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("Your JSONObject is malformed");
			

		}
		finally{
			out.close();
		}
		
		

		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		if (jsonResponse != null) {
			out.print(jsonResponse.toString());
		} else {
			out.print("Sorry, there were no information found for the requested query");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		}
		out.flush();
		out.close();

	}

	private Double getExchangeCourse(String requestedCourse) throws NotFoundException {

		switch (requestedCourse) {
		case "euro_chf":
			return CurrencyInformation.getEuro_chf_course();

		case "euro_dollar":
			return CurrencyInformation.getEuro_dollar_course();

		case "euro_pound":
			return CurrencyInformation.getEuro_pound_course();

		case "chf_euro":
			return CurrencyInformation.getChf_euro_course();

		case "dollar_euro":
			return CurrencyInformation.getDollar_euro_course();

		case "pound_euro":
			return CurrencyInformation.getPound_euro_course();

		default:

			throw new NotFoundException("The course you are looking for is not available");

		}

	}
}
