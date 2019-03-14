package cn.jihe.management.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jihe.management.excel.PresentWeek;
import cn.jihe.management.excel.WriteExcel;

public class ExcelController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ExcelController() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		PresentWeek pw=new PresentWeek();
		Integer weekIndex=pw.getPresentWeek();
		Map item = new HashMap();
		String excelPath="E:\\management\\excel\\";
		File file = new File(excelPath);
		String[] paths=file.list();
        String path =  excelPath+paths[0];
        String path2 = "E:\\management\\oldExcel\\NCP 窗口时间表格 第"+weekIndex+"周.xlsx";
        WriteExcel.replaceModel(item, path, path2);
        resp.getWriter().flush();
        resp.getWriter().close();
	}
	
	

}
