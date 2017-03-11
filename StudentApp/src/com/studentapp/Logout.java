package com.studentapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout extends HttpServlet
{
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException
	{
		
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		
         HttpSession session=req.getSession(false);
         
         if(session!=null)
         {
        	 session.invalidate();
         }
         out.print("You have logout the application");
         RequestDispatcher dispatcher=req.getRequestDispatcher("./index.html");
         
         dispatcher.include(req,resp);
		
	}

}
