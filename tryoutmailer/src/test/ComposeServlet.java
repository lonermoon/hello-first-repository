package test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ComposeServlet extends HttpServlet 
{
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
	{
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		
		req.getRequestDispatcher("header.html").include(req,resp);
		req.getRequestDispatcher("link.html").include(req,resp);
		
		HttpSession session=req.getSession(false);
		
		if(session==null)
		{
			resp.sendRedirect("index.html");
		}
		else
		{
			String email=(String)session.getAttribute("email");
			out.print("<span style=\"float:right\">Hi "+email+"</span>");
			
			String msg=(String)session.getAttribute("msg");
			
			if(msg!=null)
			{
				out.print("<p>"+msg+"</p>");
			}
			
			req.getRequestDispatcher("composeform.html").include(req, resp);
			
		}
		
		req.getRequestDispatcher("footer.html").include(req, resp);
	}

}
