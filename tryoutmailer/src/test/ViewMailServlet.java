package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewMailServlet extends HttpServlet
{
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
	{
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		
		req.getRequestDispatcher("header.html").include(req, resp);
		req.getRequestDispatcher("link.html").include(req, resp);
		
		HttpSession session=req.getSession(false);
		
		if(session==null)
		{
			resp.sendRedirect("index.html");
		}
		else
		{
			String email=(String)session.getAttribute("email");
			out.print("<span style=\"float:right;\">Hi "+email+"</span>");
			
			int id=Integer.parseInt(req.getParameter("id"));
			
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet res=null;
			
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/latenight1?user=j2ee&password=j2ee");
				 pstmt=con.prepareStatement("select * from company_mailer_message where id=?");
				pstmt.setInt(1,id);
				res=pstmt.executeQuery();
				
				if(res.next())
				{
					out.print("<h1>"+res.getString("subject")+"</h1><hr/>");
					out.print("<p><b>Message:</b><br/> "+res.getString("message")+" <br/> <b>By: "+res.getString("sender")+"</b></p>");
					out.print("<p><a href='DeleteMailServlet?id="+res.getString(1)+"'>Delete Mail</a></p>");			
									
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
				if(con!=null)
					con.close();
				if(pstmt!=null)
					pstmt.close();
				if(res!=null)
					res.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
				
			}
			
		}
		req.getRequestDispatcher("footer.html").include(req, resp);
		
	}

}
