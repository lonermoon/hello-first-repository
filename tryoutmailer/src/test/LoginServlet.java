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

public class LoginServlet extends HttpServlet
{
	protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
	{
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		req.getRequestDispatcher("./header.html").include(req, resp);
		
		String email=req.getParameter("email");
		String password=req.getParameter("password");
		
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet res=null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/latenight1?user=j2ee&password=j2ee");
			pstmt=con.prepareStatement("select * from company_mailer_user where email=? and password=? and authorized=?");
			pstmt.setString(1,email);
			pstmt.setString(2,password);
			pstmt.setString(3,"yes");
			res=pstmt.executeQuery();
			
			if(res.next())
			{
				      //out.print("you are successfully logged in!");
                       HttpSession session=req.getSession(true);
				       session.setAttribute("login","true");
				       session.setAttribute("email", email);
				       resp.sendRedirect("./InboxServlet");
			}
			else
			{
				out.print("<p>Sorry, username or password error</p>");
				req.getRequestDispatcher("./login.html").include(req,resp);
			}
			
			req.getRequestDispatcher("./footer.html").include(req,resp);
			
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
