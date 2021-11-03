package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
//这个是基础的servlet,并不具备处理数据的能力
public class BasicController extends HttpServlet{
	
 @Override
   protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
	 doPost(req,resp);
 }
 //封装一下处理的数据
 protected void send(HttpServletResponse resp,int result)
 {
	 try {
		 PrintWriter out =resp.getWriter();
		 out.print(result);
		 out.flush();
		 out.close();
	 }catch(IOException e)
	 {
		 e.printStackTrace();
	 }
 }
 
 
 protected void send(HttpServletResponse resp,String result)
 {
 	try {
 		 PrintWriter out =resp.getWriter();
		 out.print(result);
		 out.flush();
		 out.close();
 	}catch(IOException e)
 	{
 		e.printStackTrace();
 	}
 }
 
 protected void send(HttpServletResponse resp,List<Map<String,Object>> list)
 {
 	try {
 		 PrintWriter out =resp.getWriter();
 		 Gson gson=new Gson();
 		 String json=gson.toJson(list);
		 out.print(json);
		 out.flush();
		 out.close();
 	}catch(IOException e)
 	{
 		e.printStackTrace();
 	}
 }
}

