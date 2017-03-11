package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ComposeServletProcess extends HttpServlet
{
	protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
	{
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		
		String receiver=req.getParameter("receiver");
		String subject=req.getParameter("subject");
		String message=req.getParameter("message");
		message=message.replaceAll("\n","<br/>");
		String email=(String)req.getSession(false).getAttribute("email");
		
		req.getRequestDispatcher("header.html").include(req,resp);
		req.getRequestDispatcher("link.html").include(req,resp);
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/latenight1?user=j2ee&password=j2ee");
			
			pstmt=con.prepareStatement("insert into company_mailer_message (sender,receiver,subject,message,trash,messagedate) values(?,?,?,?,?,?)");
			pstmt.setString(1,email);
			pstmt.setString(2,receiver);
			pstmt.setString(3,subject);
			pstmt.setString(4,message);
			pstmt.setString(5,"no");
			pstmt.setDate(6,Formatter.getCurrentDate());
			
			int count=pstmt.executeUpdate();
			
			if(count>0)
			{

				HttpSession session=req.getSession(false);
				
				if(session==null)
				{
					resp.sendRedirect("index.html");
				}
				else
				{
					out.print("<p>Message Successfully sent</p>");
					req.getRequestDispatcher("composeform.html").include(req,resp);
					
					
				}
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
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		
	}

}
