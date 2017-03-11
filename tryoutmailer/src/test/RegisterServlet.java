package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet 
{
	protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
	{
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		 
		/*RequestDispatcher dispatcher=null;
		dispatcher=req.getRequestDispatcher("header.html");
		dispatcher.include(req,resp);*/
		
		req.getRequestDispatcher("header.html").include(req,resp);
		
		String name=req.getParameter("name");
		String email=req.getParameter("email");
		String password=req.getParameter("password");
		String gender=req.getParameter("gender");
		String dob=req.getParameter("dob");
		String addressline=req.getParameter("addressline");
		String city=req.getParameter("city");
		String state=req.getParameter("state");
		String country=req.getParameter("country");
		String contact=req.getParameter("contact");
		
		java.sql.Date sqlDOB=Formatter.getSqlDate(dob);
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		
		try
		{
			
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/latenight1?user=j2ee&password=j2ee");
			pstmt=con.prepareStatement("insert into company_mailer_user(name,email,password,gender,dob,addressline,city,state,country,contact,registereddate,authorized) values(?,?,?,?,?,?,?,?,?,?,?,?)");
			
			pstmt.setString(1,name);
			pstmt.setString(2,email);
			pstmt.setString(3,password);
			pstmt.setString(4,gender);
			pstmt.setDate(5,sqlDOB);
			pstmt.setString(6,addressline);
			pstmt.setString(7,city);
			pstmt.setString(8,state);
			pstmt.setString(9,country);
			pstmt.setString(10,contact);
			pstmt.setDate(11,Formatter.getCurrentDate());
			pstmt.setString(12,"yes");
			
			int count=pstmt.executeUpdate();
			
			if(count>0)
			{
				out.print("<p>You are successfully registered</p>");
			    req.getRequestDispatcher("./login.html").include(req,resp);	
			}
			req.getRequestDispatcher("footer.html").include(req,resp);
			
			
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
