package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.UserDAO;
import objects.User;
import org.json.JSONObject;

@WebServlet(urlPatterns ="/user/*")
public class UserServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String requestUrl = request.getRequestURI();
		String name = requestUrl.substring("/Rest_API_Practice/user/".length());
		//issue when you submit to /Rest_API_Practice/user, is length 23 but you will start at length 24
		System.out.println(name);
		User user = UserDAO.getInstance().findUser(name);
		
		if(user != null) {
			String json = "{\n";
			json += "\"First Name\": " + JSONObject.quote(user.getName())+ ",\n";
			json += "\"Password\": " +JSONObject.quote(user.getPassword()) + ",\n";
			json += "}";
			response.getOutputStream().println(json);
		}
		else {
			response.getOutputStream().println("{}");
		}
	}
	
	@Override
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String name = request.getParameter("fullname");
		String username = request.getParameter("username");
		String pass = request.getParameter("password");
		
		User user = UserDAO.getInstance().newUser(name, username, pass);
		System.out.println(name + username + pass);
		
		try {
			UserDAO.append(user);
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		response.getWriter().append("Name: "+name+
				"\nUsername: "+ username+
				"\nPassword: "+pass);
		response.sendRedirect(request.getContextPath()+"/static/submitted.html");
	}
}
