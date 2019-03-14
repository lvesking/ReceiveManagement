package cn.jihe.management.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
/**
 * 数据库连接工具类
 * @author Administrator
 *
 */
public class DBUTils {
	public static String driver;
	public static String url;
	public static String username;
	public static String password;
	private static String initialSize;
	private static String MaxActive;
	private static BasicDataSource dataSource;
	//1.获取连接
	static {
	
		Properties pr=new Properties();
		InputStream in=DBUTils.class.getClassLoader().getResourceAsStream("jdbc.properties");
		try {

			pr.load(in);
			driver=pr.getProperty("driver");
			url=pr.getProperty("url");
			username=pr.getProperty("username");
			password=pr.getProperty("password");
			initialSize=pr.getProperty("initialSize");
			MaxActive=pr.getProperty("MaxActive");
			Integer array=Integer.parseInt(initialSize);
			Integer max=Integer.parseInt(MaxActive);
			
			dataSource=new BasicDataSource();
			dataSource.setDriverClassName(driver);
			dataSource.setUrl(url);
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			dataSource.setInitialSize(array);
			dataSource.setMaxActive(max);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConn() throws Exception {
		return dataSource.getConnection();
		
	}
	
	public static void close(Connection conn,Statement stat,ResultSet rs) {
		try {
			if(rs!=null) {
				rs.close();
			}
			if(stat!=null) {
				stat.close();
			}
			if(conn!=null) {
				//归还之前打开自动提交
				conn.setAutoCommit(true);
				conn.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
