package junit;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import servlet.UserServlet;

public class testing extends Mockito{
	
	@Mock
	HttpServletRequest request = mock(HttpServletRequest.class);
	
	@Mock
	HttpServletResponse response = mock(HttpServletResponse.class);
	
	@Mock
	HttpSession session;
	
	@Mock
	RequestDispatcher rd;
	
	@Before
	public void beforeClass() throws Exception {
		MockitoAnnotations.openMocks(this);
		
	}

	@Test
	public void testServlet_doPost() throws IOException, ServletException{

		when(request.getParameter("fullname")).thenReturn("a");
		when(request.getParameter("username")).thenReturn("b");
		when(request.getParameter("password")).thenReturn("c");
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		when(response.getWriter()).thenReturn(writer);
		
		new UserServlet().doPost(request, response);
		String result = stringWriter.getBuffer().toString().trim();
		writer.flush();
		System.out.println(result);
		assertEquals(result, new String("Name: "+"a"+"\nUsername: "+ "b"+"\nPassword: "+"c"));
	}

}
