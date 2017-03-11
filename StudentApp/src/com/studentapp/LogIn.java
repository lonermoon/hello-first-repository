package com.studentapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogIn extends HttpServlet
{
	protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
	{
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		
		String regno=req.getParameter("id");
		String password=req.getParameter("pass");
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet res=null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/becme91011_db?user=j2ee&password=j2ee");
			
			pstmt=con.prepareStatement("select * from students_otherinfo where regno=? and password=?");
			pstmt.setInt(1,Integer.parseInt(regno));
			pstmt.setString(2,password);
			res=pstmt.executeQuery();
			
			if(res.next()==true)
			{
				out.print("<p style=\"font:12px arial; color:green;\">Login Successful</p>");
				
				HttpSession session=req.getSession(true);
				
				/*session.setMaxInactiveInterval(1*60);*/
				
				session.setAttribute("regno",regno);
				
				RequestDispatcher dispatcher=req.getRequestDispatcher("./homepage");
				dispatcher.forward(req,resp);
			}
			else
			{
				out.print("<p style=\"color:darkred;font:12px arial;\">Sorry!!! Login Failed Try again...</p>");
				RequestDispatcher dispatcher=req.getRequestDispatcher("./index.html");
				dispatcher.include(req,resp);
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

}
