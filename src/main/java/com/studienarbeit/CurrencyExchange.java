package com.studienarbeit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyExchange extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String operationGetCourse = "getCourse";
	private static final String operationCalculateValue = "calculateValue";

	public CurrencyExchange() {

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("plain/text");
		response.getWriter().append("I am running");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		JSONObject jsonResponse = null;
		StringBuffer sb = new StringBuffer();
		String line = null;

		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
			sb.append(line);

		try {
			String jsonString = sb.toString();
			JSONObject jsonObject = new JSONObject(jsonString);

			String operation = jsonObject.getString("requestedOperation");

			switch (operation) {
			case operationGetCourse:

				Double requestedCourse = this.getExchangeCourse(jsonObject.getString("requestedCourse"));
				jsonResponse = new JSONObject();
				jsonResponse.put("requestedOperation", operationGetCourse);
				jsonResponse.put("requestedCourse", jsonObject.getString("requestedCourse"));
				jsonResponse.put("exchangeCourse", requestedCourse);
				break;

			case operationCalculateValue:

				Double calculatedValue = this.getCalculatedValue(jsonObject.getString("requestedCourse"),
						jsonObject.getDouble("sourceValue"));
				jsonResponse = new JSONObject();
				jsonResponse.put("requestOperation", operationCalculateValue);
				jsonResponse.put("requestedCourse", jsonObject.getString("requestedCourse"));
				jsonResponse.put("exchangeCourse", this.getExchangeCourse(jsonObject.getString("requestedCourse")));
				jsonResponse.put("calculatedValue", calculatedValue);
				break;

			default:
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				out.print("The operation requested ist not available. You can use 'getCourse' or 'calculateValue'");
				out.close();
				return;

			}

		} catch (JSONException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("Your JSONObject is malformed");
			out.close();
			return;

		} catch (NotFoundException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("The course requested is not available");
			out.flush();
			out.close();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("We were not able to read your request");
			out.close();
			return;
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		out.print(jsonResponse.toString());

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

	private Double getCalculatedValue(String requestedCourse, Double sourceValue) throws NotFoundException, Exception {

		Double exchangeCourse = this.getExchangeCourse(requestedCourse);
		Double caluclatedValue = sourceValue * exchangeCourse;
		return caluclatedValue;

	}
}
