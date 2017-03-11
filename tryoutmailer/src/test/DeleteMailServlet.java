package test;

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

public class DeleteMailServlet extends HttpServlet
{
      protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
      {
    	  resp.setContentType("text/html");
    	  PrintWriter out=resp.getWriter();
    	  
    	  req.getRequestDispatcher("header.html").include(req, resp);
    	  req.getRequestDispatcher("link.html").include(req, resp);
    	  
    	  Connection con=null;
    	  PreparedStatement pstmt=null;
    	  
    	  HttpSession session=req.getSession(false);
    	  
    	  if(session==null)
    	  {
    		  resp.sendRedirect("index.html");
    	  }
    	  else
    	  {
    		  String email=(String)session.getAttribute("email");
    		  out.print("<span style=\"float:right;\">Hi "+email+ "</span>");
    		  
    		  int id=Integer.parseInt(req.getParameter("id"));
    		  
    		  try
    		  {
    			  Class.forName("com.mysql.jdbc.Driver");
    			  con=DriverManager.getConnection("jdbc:mysql://localhost:3306/latenight1?user=j2ee&password=j2ee");
    			  
    			  pstmt=con.prepareStatement("update company_mailer_message set trash=? where id=?");
                  pstmt.setString(1,"yes");
                  pstmt.setInt(2,id);
                  
                  int count=pstmt.executeUpdate();
                  
                  if(count>0)
                  {
                	  req.setAttribute("msg","Mail Successfully Deleted!");
                	  req.getRequestDispatcher("InboxServlet").forward(req, resp);
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
}
