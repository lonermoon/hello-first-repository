package com.studentapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AllData extends HttpServlet 
{
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
	{
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		
		Connection con=null;
		Statement stmt=null;
		ResultSet res=null;
		
		try
		{
			
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/becme91011_db?user=j2ee&password=j2ee");
			
			stmt=con.createStatement();
			res=stmt.executeQuery("select * from students_info s1,guardian_info s2,students_otherinfo s3 where s1.regno=s2.regno and s2.regno=s3.regno");
			
			
			RequestDispatcher dispatcher1=req.getRequestDispatcher("./Header.html");
    		dispatcher1.include(req,resp);
			
			
			out.print("<table border=\"1\" style=\"font:12px arial;margin:80px 110px; width:80%; text-align:center; \">");
			out.print("<tr style=\"background:darkgreen;color:#eee;\"><th>Regno.</th><th>Firstname</th><th>Middlename</th><th>Lastname</th>");
			out.print("<th>GFirstname</th><th>GMiddlename</th><th>GLastname</th>");
			out.print("<th>IsAdmin</th><th>Password</th><th colspan='2'>Control Panel</th></tr>");
			
			HttpSession session=req.getSession(false);
			if(session==null)
			{
				out.print("<p style=\"color:darkred;font:12px arial;\">Sorry!!! Login Failed Try again...</p>");
				resp.sendRedirect("./index.html");
			}
			
			else
			{
			
			
			
			while(res.next())
			{
				
				
				int regno=res.getInt(1);
				String fname=res.getString(2);
				String mname=res.getString(3);
				String lname=res.getString(4);
				
				String gfname=res.getString(6);
				String gmname=res.getString(7);
				String glname=res.getString(8);
				
				String isAdmin=res.getString(10);
				String password=res.getString(11);
				
				
				out.print("<tr>");
				out.print("<td>"+regno+"</td>");
				out.print("<td>"+fname+"</td>");
				out.print("<td>"+mname+"</td>");
			    out.print("<td>"+lname+"</td>");
			    out.print("<td>"+gfname+"</td>");
			    out.print("<td>"+gmname+"</td>");
			    out.print("<td>"+glname+"</td>");
			    out.print("<td>"+isAdmin+"</td>");
			    out.print("<td>"+password+"</td>");
			    
			    out.print("<td><a href=\"./edit?id="+regno+"\"action=edit>Edit</a></td>");
			    out.print("<td><a href=\"./delete?id="+regno+"\"action=delete>Delete</a></td>");
			    out.print("<tr>");
				
			}
			
			}
			
			out.print("<table>");
			RequestDispatcher dispatcher2=req.getRequestDispatcher("./Footer.html");
    		dispatcher2.include(req,resp);
		
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
				if(stmt!=null)
					stmt.close();
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
