package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aaa.DbHelper;

@WebServlet("/books")
public class GoodsServlet extends BasicController{
	public DbHelper db=new DbHelper();
	@Override
	protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
		//得到传过来的参数
		//一个servlet要处理多个请求，用op来区别
		String op=req.getParameter("op");
		 if("getAllTypebooks".equals(op))
		 {
			 getAllBooks(req,resp);
		 }else if("getBooks".equals(op))
		 {
			 getBooks(req,resp);
		 }else if("getBookdetail".equals(op))
		 {
			 getBookdetail(req,resp);
		 }else if("gwc".equals(op))
		 {
			 gwc(req,resp);
		 }else if("getBooksgwc".equals(op))
		 {
			 getBooksgwc(req,resp);
		 }else if("del".equals(op))
		 {
			 del(req,resp);
		 }else if("add".equals(op))
		 {
			 add(req,resp);
		 }else if("sub".equals(op))
		 {
			 sub(req,resp);
		 }else if("Shou".equals(op))
		 {
			 Shou(req,resp);
		 }
	}
	
	private void getAllType(HttpServletRequest req,HttpServletResponse resp)
	{
		String sql="select * from goodstype";
		List<Map<String,Object>> list=db.findAll(sql);
		if(list.size()>0)
		{
			this.send(resp,list);
		}else {
			this.send(resp,-1);
		}
	}
	
	
	
	private void getAllGoods(HttpServletRequest req,HttpServletResponse resp)
	{
		String sql="select * from goodsinfo";
		List<Map<String,Object>> list=db.findAll(sql);
		if(list.size()>0)
		{
			this.send(resp,list);
		}else {
			this.send(resp,-1);
		}
	}
	
	private void getBooksgwc(HttpServletRequest req,HttpServletResponse resp)
	{
		String sql="select * from gw where gnames=?";
		String gnames=req.getParameter("gnames");
		List<Map<String,Object>> list=db.findAll(sql,gnames);
		if(list.size()>0)
		{
			this.send(resp,list);
		}else {
			this.send(resp,-1);
		}
	}
	private void getBooks(HttpServletRequest req,HttpServletResponse resp)
	{   int gno=Integer.parseInt(req.getParameter("gno"));
		String sql="select * from booksinfo where tno=?";
		List<Map<String,Object>> list=db.findAll(sql,gno);
		if(list.size()>0)
		{
			this.send(resp,list);
		}else {
			this.send(resp,-1);
		}
	}
	
	private void Shou(HttpServletRequest req,HttpServletResponse resp)
	{   String shu=req.getParameter("shu");
		String sql="select * from booksinfo where gname=?";
		List<Map<String,Object>> list=db.findAll(sql,shu);
		if(list.size()>0)
		{
			this.send(resp,list);
		}else {
			this.send(resp,-1);
		}
	}
	private void getBookdetail(HttpServletRequest req,HttpServletResponse resp)
	{   int gno=Integer.parseInt(req.getParameter("gno"));
		String sql="select * from booksinfo where gno=?";
		List<Map<String,Object>> list=db.findAll(sql,gno);
		if(list.size()>0)
		{
			this.send(resp,list);
		}else {
			this.send(resp,-1);
		}
	}
	
	private void del(HttpServletRequest req,HttpServletResponse resp)
	{   int gno=Integer.parseInt(req.getParameter("gno"));
		String sql="delete  from gw where gno=?";
		int result =db.doUpdate(sql,gno);
		if(result>0)
		{
			this.send(resp, 1);
			
		}else {
			this.send(resp,-1);
		}
	}
	
	private void add(HttpServletRequest req,HttpServletResponse resp)
	{   int gno=Integer.parseInt(req.getParameter("gno"));
	    int num=Integer.parseInt(req.getParameter("shu"));
	    int nums=num+1;
		String sql="update gw set num=? where gno=?";
		int result =db.doUpdate(sql,nums,gno);
		if(result>0)
		{
			this.send(resp, 1);
			
		}else {
			this.send(resp,-1);
		}
	}
	
	private void sub(HttpServletRequest req,HttpServletResponse resp)
	{   int gno=Integer.parseInt(req.getParameter("gno"));
	    int num=Integer.parseInt(req.getParameter("shu"));
	    int nums=num-1;
		String sql="update gw set num=? where gno=?";
		int result =db.doUpdate(sql,nums,gno);
		if(result>0)
		{
			this.send(resp, 1);
			
		}else {
			this.send(resp,-1);
		}
	}
	private void findByGno(HttpServletRequest req,HttpServletResponse resp)
	{
		try {
			int gno=Integer.parseInt(req.getParameter("gno"));
			String sql="select * from goodsinfo,goodstype where goodsinfo.tno=goodstype.tno and gno=?";
			List<Map<String,Object>> list=db.findAll(sql,gno);
			if(list.size()>0)
			{
				this.send(resp, list);
			}else {
				this.send(resp, 0);
			}
		}catch(NumberFormatException e)
		{
			//上面代码出错了
			this.send(resp, -1);
		}
	}
	
	private void getAllBooks(HttpServletRequest req,HttpServletResponse resp)
	{
		String sql="select * from bookstype";
		List<Map<String,Object>> list=db.findAll(sql);
		if(list.size()>0)
		{
			this.send(resp,list);
		}else {
			this.send(resp,-1);
		}
	}
	
	private void gwc(HttpServletRequest req,HttpServletResponse resp)
	{
		String gno=req.getParameter("gno");
		String gname=req.getParameter("gname");
		String price=req.getParameter("price");
		String pic=req.getParameter("pic");
		String gnames=req.getParameter("gnames");
		String sql="insert into gw values(null,?,1,?,?,?,1)";
		int result =db.doUpdate(sql,gname,price,pic,gnames);
		if(result>0)
		{
			this.send(resp, 1);
			
		}else {
			this.send(resp,-1);
		}
	}
}
