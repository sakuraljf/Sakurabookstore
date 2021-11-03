package aaa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbHelper {
//静态块
	static {
		try {
			//加载驱动
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	//获取连接
	public Connection getCon()
	{
		Connection con =null;
		try {
			//建立连接
			con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/snacknet","root","123456");
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return con;
	}
	
	public int doUpdate(String sql,Object...params)
	{
	int result=-1;
	try {
		//获得连接对象
		Connection con =getCon();
		//得到预处理对象
		PreparedStatement ps=con.prepareStatement(sql);
		//注入参数
		doParams(ps,params);
		//开始执行
		result=ps.executeUpdate();
		
	}
		catch(SQLException e)
	{
		e.printStackTrace();
	}
	return result;
	}
	//查询
	public List<Map<String,Object>> findAll(String sql,Object...params)
	{
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			con=getCon();
		    ps=con.prepareStatement(sql);
			doParams(ps,params);
			rs=ps.executeQuery();
			
			//注意，开始不一样了
			//先来存列名
			String []cname=new String[rs.getMetaData().getColumnCount()];
			for(int i=0;i<cname.length;i++)
			{
				cname[i]=rs.getMetaData().getColumnName(i+1);
			}
			
			//开始存执，根据列明来取值
			while(rs.next())
			{
		    Map<String,Object> map=new HashMap<String,Object>();
		    //根据列名来取值
		    for(int i=0;i<cname.length;i++)
		    {
		    	String key=cname[i];
		    	Object value=rs.getObject(key);
		    	map.put(key, value);
		    }
		    list.add(map);
			}
		
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally {
			try {
				con.close();
				ps.close();
				rs.close();
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		return list;
	}
	
	private void doParams(PreparedStatement ps,Object...params)
	{
		try {
			for(int i=0;i<params.length;i++)
			{
				ps.setObject(i+1, params[i]);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
}
