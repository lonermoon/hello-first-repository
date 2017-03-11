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

public class Edit extends HttpServlet
{

	   protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
	   {
		   resp.setContentType("text/html");
		   PrintWriter out=resp.getWriter();
		   
		   String regno=req.getParameter("id");
		   
		   Connection con=null;
		   PreparedStatement pstmt=null;
		   ResultSet res=null;
		   

   		   req.getRequestDispatcher("./Header.html").include(req,resp);
				   
		   try
		   {
			 Class.forName("com.mysql.jdbc.Driver");
			 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/becme91011_db?user=j2ee&password=j2ee");
			 
			 pstmt=con.prepareStatement("select * from students_info s1,guardian_info s2,students_otherinfo s3 where s1.regno=s2.regno and s2.regno=s3.regno and s1.regno=?");
			 pstmt.setInt(1,Integer.parseInt(regno));
			 
			 res=pstmt.executeQuery();
			 
			 if(res.next())
			 {
				 int regno1=res.getInt(1);
				 String fname=res.getString(2);
				 String mname=res.getString(3);
				 String lname=res.getString(4);
				 
				 String gfname=res.getString(6);
				 String gmname=res.getString(7);
				 String glname=res.getString(8);  
				 
				 String isAdmin=res.getString(10);
				 String password=res.getString(11);
				 
				 out.print("<html>");
				 out.print( "<fieldset style=\"width:490px;margin:50px auto auto;font:13px arial;padding:30px;\">");
				 out.print("<legend>Student Profile Form</legend>");
				 out.print("");
				 out.print("<table>");
				 out.print("<form action=\"./update\" method=\"get\">");
				 out.print("<h4>Enter the Student Details :</h4>");
				 out.print("<tr><td>Register No. :</td><td><input type=\"text\" name=\"id\" readonly value=\""+regno+"\"></td></tr>");
				 out.print("<tr><td>First Name :</td><td><input type=\"text\" name=\"fname\" value=\""+fname+"\"></td></tr>");
				 out.print("<tr><td>Middle Name :</td><td><input type=\"text\" name=\"mname\" value=\""+mname+"\"></td></tr>");
				 out.print("<tr><td>Last Name :</td><td><input type=\"text\" name=\"lname\" value=\""+lname+"\"></td></tr>");
				 out.print("<tr></tr>");
				 out.print("<tr><td><h5>Enter the Guardian Details :</h5></td></tr>");
				 out.print("<tr><td>First Name :</td><td><input type=\"text\" name=\"gfname\" value=\""+gfname+"\"></td></tr>");
				 out.print("<tr><td>Middle Name :</td><td><input type=\"text\" name=\"gmname\" value=\""+gmname+"\"></td></tr>");
				 out.print("<tr><td>Last Name :</td><td><input type=\"text\" name=\"glname\" value=\""+glname+"\"></td></tr>");
				 out.print("<tr></tr>");
				 out.print("<tr></tr>");
				 out.print("<tr><td><input type=\"submit\" value=\"Update\"></td>");
				 out.println("<td><a href=\"./alldata\"><input type='button' value='Cancle'></a></td></tr>");
				 out.print("</table></form>");
				 out.print("</fieldset>");
				 out.print("</html>");
	
			 }
              
	    		req.getRequestDispatcher("./Footer.html").include(req,resp);
			 	 
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
