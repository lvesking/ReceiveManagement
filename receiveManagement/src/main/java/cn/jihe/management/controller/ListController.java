 package cn.jihe.management.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jihe.management.bean.ReceiveList;
import cn.jihe.management.bean.SelectDate;
import cn.jihe.management.excel.ReadExcel;
import cn.jihe.management.jdbc.DBUTils;
import cn.jihe.management.services.DataTimeService;
import cn.jihe.management.services.ListService;
import cn.jihe.management.services.ExcelJsonService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        ListService listService=new ListService();
        JSONArray datas=listService.getUltimatelyData();
		//判断数据不为空则响应
		if(datas!=null) {
			response.getWriter().print(datas.toString());
			response.getWriter().flush();
			response.getWriter().close();
		}
		
	}
	
	 
	 
	
	 

}
