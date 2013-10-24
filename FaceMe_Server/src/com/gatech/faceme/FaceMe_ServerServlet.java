package com.gatech.faceme;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class FaceMe_ServerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
