package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.UserDAO;
import objects.User;
import org.json.JSONObject;

public class UserServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String requestUrl = request.getRequestURI();
		String name = requestUrl.substring("/Rest_API_Practice/user/".length());
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
		
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String pass = request.getParameter("password");
		
		UserDAO.getInstance().newUser(name, username, pass);
		response.getOutputStream(); //how to add information from a POST request to database?
	}
}
