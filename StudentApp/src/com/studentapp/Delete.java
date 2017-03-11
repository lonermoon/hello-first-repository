package com.studentapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Delete extends HttpServlet
{
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
	{
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		
		String regno=req.getParameter("id");
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/BECME91011_DB?user=j2ee&password=j2ee");
			
		    pstmt=con.prepareStatement("delete from students_info where regno=?");
		    pstmt.setInt(1,Integer.parseInt(regno));
		    int count1=pstmt.executeUpdate();
		    
		    pstmt=con.prepareStatement("delete from guardian_info where regno=?");
		    pstmt.setInt(1,Integer.parseInt(regno));
		    int count2=pstmt.executeUpdate();
		   
		    
		    pstmt.setInt(1,Integer.parseInt(regno));
		    pstmt=con.prepareStatement("delete from students_otherinfo where regno=?");
		    pstmt.setInt(1,Integer.parseInt(regno));
		    int count3=pstmt.executeUpdate();
		    con.close();
		    pstmt.close();
		    
		    if(count1>0 && count2>0 && count3>0)
		    {
		    	out.println("<p style='color:green;font:12px arial;'>Successfully Deleted</p>");
		    	resp.sendRedirect("./alldata");
		    	
		    }
		    else
		    {
		    	out.print("<p style='color:red;font:12px arial;'>Unable to Delete</p>");
		    	resp.sendRedirect("./alldata");
		    	
		    }
		    
		    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
