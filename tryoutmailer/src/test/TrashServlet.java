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

public class TrashServlet extends HttpServlet
{
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
	{
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		
		req.getRequestDispatcher("header.html").include(req, resp);
		req.getRequestDispatcher("link.html").include(req, resp);
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet res=null;
		
		HttpSession session=req.getSession();
		
		if(session==null)
		{
			resp.sendRedirect("index.html");
		}
		else
		{
			String email=(String)session.getAttribute("email");
		   out.print("<span style=\"float:right;\">Hi "+email+"</span>");	
		   out.print("<p>Trash</p>");
		   
		   String msg=(String)req.getAttribute("msg");
		   
		   if(msg!=null)
		   {
			   out.print("<p>"+msg+"</p>");
		   }
		   
		   try
		   {
			   Class.forName("com.mysql.jdbc.Driver");
			   con=DriverManager.getConnection("jdbc:mysql://localhost:3306/latenight1?user=j2ee&password=j2ee");
			   
			   pstmt=con.prepareStatement("select * from company_mailer_message where receiver=? or sender=? and trash=? order by id desc");
			   pstmt.setString(1,email);
			   pstmt.setString(2,email);
			   pstmt.setString(3,"yes");
			   
			   res=pstmt.executeQuery();
			   
			   out.print("<table border=\"1\" style=\"width:700px;\">");
			   out.print("<tr style=\"background-color:grey;color:white;\"><td>Sender</td><td>Subject</td></tr>");
			   
			   while(res.next())
			   {
				   out.print("<tr><td>"+res.getString("sender")+"</td><td>"+res.getString("subject")+"</td></tr>");
			   }
			   out.print("</table>");
			   
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
		
		
		
	}

}
