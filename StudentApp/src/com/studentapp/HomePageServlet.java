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

public class HomePageServlet extends HttpServlet 
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
	    	
	    	pstmt=con.prepareStatement("select * from students_info s1,guardian_info s2,students_otherinfo s3 where s1.regno=s2.regno and s1.regno=s3.regno and s3.regno=? and password=?");
	    	pstmt.setInt(1,Integer.parseInt(regno));
	    	pstmt.setString(2,password);
	    	
	    	res=pstmt.executeQuery();
	    	
	    	if(res.next()==true)
	    	{
	    		//students_info data
	    		int reg=res.getInt(1);
	    		String fname=res.getString(2);
	    		String mname=res.getString(3);
	    		String lname=res.getString(4);
	    		//guardian_info data
	    		String gfname=res.getString(6);
	    		String gmname=res.getString(7);
	    		String glname=res.getString(8);
	    		
	    		String isAdmin=res.getString(10);
	    		
	    		if(isAdmin.equalsIgnoreCase("Y"))
	    		{
	    		
	    		RequestDispatcher dispatcher1=req.getRequestDispatcher("./Header.html");
	    		dispatcher1.include(req,resp);
	    		
	    		
                out.print("<p style=\"font:bold 15px arial; color:darkgreen;text-decoration:uppercase;margin-top:20px;\">Welcome User :) : <u>"+fname+"</u></p>");
                
                out.print("<a href=\"./alldata \">Click Here To View All</a>");
                
	    		out.print("<table border=\"1\" style=\"font:12px arial;border:1px solid green;color:#333;background:#eee;margin:30px 330px 200px;text-align:center;width:680px; \">");
	    		out.print("<tr style=\"background:green;color:#eee;\">");
	    		out.print("<th>Regno.</th><th>First Name:</th><th>Middle Name</th><th>Last Name:</th>");
	    		out.print("<th>Guardian First Name:</th><th>Guardian Middle Name:</th><th>Guardian Last Name:</th>");
	    		out.print("<tr>");
	    		out.print("<td>"+reg+"</td><td>"+fname+"</td><td>"+mname+"</td><td>"+lname+"</td>");
	    		out.print("<td>"+gfname+"</td><td>"+gmname+"</td><td>"+glname+"</td>");
	    		out.print("</tr>");
	    		out.print("</table>");
	    		
	    		req.getRequestDispatcher("./Footer.html").include(req,resp);
	    	   
	    		}
	    		
	    		else
	    		{
	    			
	    		RequestDispatcher dispatcher1=req.getRequestDispatcher("./Header.html");
	    		dispatcher1.include(req,resp);
	    		
	    		
                out.print("<p style=\"font:bold 15px arial; color:darkgreen;text-decoration:uppercase;margin-top:20px;\">Welcome User :) : <u>"+fname+"</u></p>");
                
                
	    		out.print("<table border=\"1\" style=\"font:12px arial;border:1px solid green;color:#333;background:#eee;margin:30px 200px 200px;text-align:center;width:680px; \">");
	    		out.print("<tr style=\"background:green;color:#eee;\">");
	    		out.print("<th>Regno.</th><th>First Name:</th><th>Middle Name</th><th>Last Name:</th>");
	    		out.print("<th>Guardian First Name:</th><th>Guardian Middle Name:</th><th>Guardian Last Name:</th>");
	    		out.print("<tr>");
	    		out.print("<td>"+reg+"</td><td>"+fname+"</td><td>"+mname+"</td><td>"+lname+"</td>");
	    		out.print("<td>"+gfname+"</td><td>"+gmname+"</td><td>"+glname+"</td>");
	    		out.print("</tr>");
	    		out.print("</table>");
	    		
	    		req.getRequestDispatcher("./Footer.html").include(req,resp);
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
