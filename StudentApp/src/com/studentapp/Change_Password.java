package com.studentapp;

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

public class Change_Password extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
	{
		res.setContentType("text/html");
		
		PrintWriter out=res.getWriter();
		
		
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		
		try
		{
			HttpSession session=req.getSession(false);
			if(session==null)
			{
				out.print("<p style=\"color:darkred;font:12px arial;\">Sorry!!! Login Failed Try again...</p>");
				res.sendRedirect("./index.html");
			}
			else
			{

			String rgno=req.getParameter("q1");
			String newPass=req.getParameter("q2");
			String oldPass=req.getParameter("q3");
			
			
			
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/becme91011_db?user=j2ee&password=j2ee");
			
			pstmt=con.prepareStatement("update students_otherinfo set password=? where regno=? and password=?");
			pstmt.setInt(2,Integer.parseInt(rgno));
			pstmt.setString(1,newPass);
			pstmt.setString(3,oldPass);
			
			int count=pstmt.executeUpdate();
			
			if(count!=0)
			{   
				out.print("<p style=\"color:darkgreen;font:12px arial;\">Successfully Updated</p>");
			}
			else
			{
				out.print("<p style=\"color:darkred;font:12px arial;\">Updated Failed</p>");
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
				{
					con.close();
				}
			    if(pstmt!=null)
			    {
			    	pstmt.close();
			    }
			}
			catch(SQLException e)
			{
				
			}
		}
	}
   	

}
