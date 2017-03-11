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

public class CreateProfile extends HttpServlet
{
	protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
	{
		
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		
		Connection con=null;
		PreparedStatement pstmt1=null;
		PreparedStatement pstmt2=null;
		PreparedStatement pstmt3=null;
		
		try
		{
			String regno=req.getParameter("regno");
			String fname=req.getParameter("fname");
			String mname=req.getParameter("mname");
			String lname=req.getParameter("lname");
			String gfnm=req.getParameter("gfname");
			String gmnm=req.getParameter("gmname");
			String glnm=req.getParameter("glname");
			String admin=req.getParameter("isAdmin");
			String pass=req.getParameter("password");
			
			
			System.out.println(admin);
			
			//1. Load the driver
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			
			//2. Get the db Connection via driver
			
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/becme91011_db?user=j2ee&password=j2ee");
			pstmt1=con.prepareStatement("insert into students_info values(?,?,?,?)");
			
			pstmt1.setInt(1,Integer.parseInt(regno));
			pstmt1.setString(2,fname);
			pstmt1.setString(3,mname);
			pstmt1.setString(4,lname);
			
			pstmt2=con.prepareStatement("insert into guardian_info values(?,?,?,?)");
			pstmt2.setInt(1,Integer.parseInt(regno));
			pstmt2.setString(2,gfnm);
			pstmt2.setString(3,gmnm);
			pstmt2.setString(4,glnm);
			
			pstmt3=con.prepareStatement("insert into students_otherinfo values(?,?,?)");

			pstmt3.setInt(1,Integer.parseInt(regno));
			pstmt3.setString(2,admin);
			pstmt3.setString(3,pass);
			
			int count1=pstmt1.executeUpdate();
			int count2=pstmt2.executeUpdate();
			int count3=pstmt3.executeUpdate();
			
			if(count1>0 && count2>0 && count3>0)
			{   
				
				req.getRequestDispatcher("./alldata").include(req, resp);
				out.println("<p style=\"color:darkgreen;font:12px arial;\">Record has been inserted</p>");
			}
			else
			{
				
				req.getRequestDispatcher("./alldata").include(req, resp);
				out.println("<p style=\"color:darkred;font:12px arial;\">Failed to Create Records</p>");
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
				if(pstmt1!=null)
				{
					pstmt1.close();
				}
				if(pstmt2!=null)
				{
					pstmt1.close();
				}
				if(pstmt3!=null)
				{
					pstmt1.close();
				}
				
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
			
		}
		
		
		
		
		
		}

	}
