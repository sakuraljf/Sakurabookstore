package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aaa.DbHelper;
//意思是这个servlet接受的请求地址，就是member
@WebServlet("/member")
public class MemberController extends BasicController {
public DbHelper db=new DbHelper();
@Override
protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
	//得到传过来的参数
	//一个servlet要处理多个请求，用op来区别
	String op=req.getParameter("op");
	 if("reg".equals(op))
	 {
		 reg(req,resp);
	 }else if("getmes".equals(op))
	 {
		 getmes(req,resp);
	 }else if("gx".equals(op))
	 {
		 gx(req,resp);
	 }else if("yanzhen".equals(op))
	 {
		 yanzhen(req,resp);
	 }
}
private void reg(HttpServletRequest req,HttpServletResponse resp)
{
	//得到传过来的数据
	String uname=req.getParameter("uname");
	String pwd=req.getParameter("pwd");
	String tel=req.getParameter("tel");
	String email=req.getParameter("email");
	String sql="insert into memberinfo values(null,?,md5(?),?,?,null,1)";
	int result =db.doUpdate(sql, uname,pwd,tel,email);
	if(result>0)
	{
		this.send(resp, 1);
		
	}else {
		this.send(resp,-1);
	}
}

private void getmes(HttpServletRequest req,HttpServletResponse resp)
{   String name=req.getParameter("name");
	String sql="select * from memberinfo where nickname=?";
	DbHelper db=new DbHelper();
	List<Map<String,Object>> list=db.findAll(sql,name);
	if(list.size()>0)
	{
		this.send(resp,list);
	}else {
		this.send(resp,-1);
	}
}

private void gx(HttpServletRequest req,HttpServletResponse resp)
{
	//得到传过来的数据
	String uname=req.getParameter("uname");
	String tel=req.getParameter("tel");
	String email=req.getParameter("email");
	String sql="update  memberinfo set tel=?,email=? where nickname=?";
	int result =db.doUpdate(sql,tel,email,uname);
	
	if(result>0)
	{
		this.send(resp, 1);
		
	}else {
		this.send(resp,-1);
	}
}

private void yanzhen(HttpServletRequest req,HttpServletResponse resp)
{
	
	String uname=req.getParameter("uname");
	
	String sql="select * from memberinfo where nickname=?";
	List<Map<String,Object>> list=db.findAll(sql,uname);
	if(list.size()>0)
	{
		this.send(resp,list);
	}else {
		this.send(resp,-1);
	}
}
}
