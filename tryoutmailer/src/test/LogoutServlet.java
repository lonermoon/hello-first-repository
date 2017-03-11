package test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
    {
    	resp.setContentType("text/html");
    	PrintWriter out=resp.getWriter();
    	req.getRequestDispatcher("header.html").include(req,resp);
    	
    	HttpSession session=req.getSession(false);
    	
    	if(session!=null)
    	{
    		session.invalidate();
    		out.print("<p>Thank you for Stopping by..</p>");
    		req.getRequestDispatcher("login.html").include(req,resp);
    	}
    	
    	req.getRequestDispatcher("footer.html").include(req,resp);
    }
}
