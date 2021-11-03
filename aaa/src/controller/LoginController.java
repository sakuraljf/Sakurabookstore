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
@WebServlet("/logins")
public class LoginController extends BasicController {
public DbHelper db=new DbHelper();
@Override
protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
	//得到传过来的参数
	//一个servlet要处理多个请求，用op来区别
	String op=req.getParameter("op");
	 if("reg".equals(op))
	 {
		 reg(req,resp);
	 }else if("checkLogin".equals(op))
	 {
		 checkLogin(req,resp);
	 }
}
private void reg(HttpServletRequest req,HttpServletResponse resp)
{
	//得到传过来的数据
	String uname=req.getParameter("uname");
	String pwd=req.getParameter("pwd");
	String sql="select * from memberinfo where nickname =? and pwd=md5(?)";
	DbHelper db=new DbHelper();
	List<Map<String,Object>> list=db.findAll(sql, uname,pwd);
	if(list.size()>0)
	{
		//登录成功，应该将值存起来
		//Session会话，从你打开浏览器，到关闭，三十分钟不操作，也会删除session
		req.getSession().setAttribute("loginUser", list.get(0));
		this.send(resp, 1);
	}else {
		this.send(resp, -1);
	}
}

private void checkLogin(HttpServletRequest req,HttpServletResponse resp)
{   //验证一下session里面有没有值
	if(req.getSession().getAttribute("loginUser")==null)
	{
		this.send(resp,-1);
	}else {
		//登录，先去用户名
		Map<String,Object> map=(Map<String,Object>) req.getSession().getAttribute("loginUser");
		String uname=map.get("nickname")+"";
		this.send(resp,uname);
	}
}


}
