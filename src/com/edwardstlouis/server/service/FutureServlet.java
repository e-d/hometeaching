package com.edwardstlouis.server.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FutureServlet extends HttpServlet {
	private static final long serialVersionUID = 3181576697308205492L;
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.write("<html><body>" +
				"	<h1>Future Sevlet</h1>Go back to the homepage <a href='http://rcchometeaching.appspot.com'>here</a>." +
				"</body></html>");
		out.close();
	}
}
