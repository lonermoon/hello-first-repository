package com.studentapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet
{

	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
	{
		
		resp.setContentType("text/html");
		
	    PrintWriter out=resp.getWriter();
	    
	    Connection con=null;
	    PreparedStatement pstmt=null;
	    ResultSet rs=null;
	    
	    String regno=req.getParameter("regno"); 
	    RequestDispatcher dispatcher1=req.getRequestDispatcher("./Header.html");
		dispatcher1.include(req,resp);
	    
	    
		     
	    try
	    {
	    	
	    	
	    	// 1.Load the Driver
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
	    	
	    	// 2.Get the db Connection via Driver
	    	
	    	con=DriverManager.getConnection("jdbc:mysql://localhost:3306/becme91011_db?user=root&password=root");
	    	pstmt=con.prepareStatement("select * from students_info s1,guardian_info g1 where s1.regno=g1.regno and s1.regno=?");
	    	
	    	
	    	pstmt.setInt(1,Integer.parseInt(regno));
	    	
	    rs=pstmt.executeQuery();
	    
	   if(rs.next()==true)
	   {
		   int regno1=rs.getInt("regno");
		   String fnm=rs.getString("firstname");
		   String mnm=rs.getString("middlename");
		   String lnm=rs.getString("lastname");
		   String gfnm=rs.getString("gfirstname");
		   String gmnm=rs.getString("gmiddlename");
		   String glnm=rs.getString("glastname");
		   
		   
		   //2. Generate HTML Response
			 out.print("<p style=\"color:darkgreen;font:12px arial;\">Success</p>"); 
		   out.print("<table border=\"1\" style=\"font:12px arial;margin:80px 110px; width:80%; text-align:center; \">");
			out.print("<tr style=\"background:darkgreen;color:#eee;\"><th>Regno.</th><th>Firstname</th><th>Middlename</th><th>Lastname</th>");
			out.print("<th>GFirstname</th><th>GMiddlename</th><th>GLastname</th></tr>");
			out.print("<tr><td>"+regno1+"</td><td>"+fnm+"</td><td>"+mnm+"</td><td>"+lnm+"</td><td>"+gfnm+"</td><td>"+gmnm+"</td><td>"+glnm);
			
	   }
	   else
	   {
		   
		   RequestDispatcher dispatcher=req.getRequestDispatcher("./homepage");
		   dispatcher.include(req, resp);
		   out.print("Invalid Search");
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
	    		if(rs!=null)
	    		 rs.close();
	    		
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }
	    
	}    

}
