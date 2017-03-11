package com.studentapp;

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

public class Update extends HttpServlet 
{

	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
	{
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		
		String regno=req.getParameter("id");
		String fname=req.getParameter("fname");
		String mname=req.getParameter("mname");
		String lname=req.getParameter("lname");
		
		String gfname=req.getParameter("gfname");
		String gmname=req.getParameter("gmname");
		String glname=req.getParameter("glname");
		
	
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet res=null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/becme91011_db?user=j2ee&password=j2ee");
			
			pstmt=con.prepareStatement("update students_info s1,guardian_info s2 set s1.firstname=?,s1.middlename=?,s1.lastname=?,s2.gfirstname=?,s2.gmiddlename=?,glastname=? where s1.regno=s2.regno and s1.regno=?");

			pstmt.setString(1,fname);
			pstmt.setString(2,mname);
			pstmt.setString(3,lname);
			
			pstmt.setString(4,gfname);
			pstmt.setString(5,gmname);
			pstmt.setString(6,glname);
			pstmt.setInt(7,Integer.parseInt(regno));
			
			int count=pstmt.executeUpdate();
			
			if(count>0)
			{
				out.print("<p style=\"color:darkgreen;font:12px arial;\">Successfully Updated</p>");
				req.getRequestDispatcher("./alldata").forward(req,resp);
			}
			else
			{
				out.print("<p style=\"color:red;font:12px arial;\">Failed to Update</p>");
				req.getRequestDispatcher("./alldata").forward(req,resp);
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
