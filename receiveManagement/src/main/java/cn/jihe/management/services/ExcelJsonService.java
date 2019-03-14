package cn.jihe.management.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import cn.jihe.management.excel.ReadExcel;
import cn.jihe.management.jdbc.DBUTils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExcelJsonService {
	
	//excel表与数据库的json互相比较
	 public static JSONArray compareJson(JSONArray json1 ){
		 	ReadExcel readExcel=new ReadExcel();
		 	List<Map<String, String>> list=readExcel.getExcel();
		 	JSONArray jsonObject = JSONArray.fromObject(list);
		 	JSONArray datas=new JSONArray();
		 	JSONObject jo;
		 	Connection conn=null;
			PreparedStatement stat=null;
			ResultSet rs=null;
			try {
				String sql = "select distinct v.VM_fvarCDesc from VendorMaster v,ReceiveHead h where v.VM_fvarCode=h.RH_fvarVendorCode AND "
						+ "VM_fvarCode in(?)";
				conn=DBUTils.getConn();
				stat = conn.prepareStatement(sql);
				//每条excel与数据库进行对比
			 	for (int i = 0; i < jsonObject.size(); i++) {
			 		JSONObject oldJson =jsonObject.getJSONObject(i);
			 		jo = new JSONObject();
			 		String v = oldJson.get("vendorCode").toString();
			 		String w = oldJson.get("week").toString();
			 		String s = oldJson.get("state").toString();
			 		Integer q =Integer.parseInt(oldJson.getString("quantity")); 
			 		jo.put("vendorCode", v);
					jo.put("week", w);
					jo.put("quantity", q);
					//根据编码查找到对应的中文名称
					stat.setString(1, v);
					rs=stat.executeQuery();
					while(rs.next()) {
						jo.put("fvarCDesc", rs.getString("VM_fvarCDesc"));
					}
					jo.put("state", s);
					
					jo.put("colorCode", "1");
					for (int j = 0; j < json1.size(); j++) {
						JSONObject Json =json1.getJSONObject(j);
		                String vs = Json.get("vendorCode").toString();
		                String ws = Json.get("week").toString();
		                String ss = Json.get("state").toString();
						if(v.equals(vs)&&w.equals(ws)&&s.equals(ss)) {
							jo.put("colorCode", "0");
						}
					}
					if(jo.size()!=0) {
						datas.add(jo);
					}
				}
			 	
			 	
			 	for (int i = 0; i < json1.size(); i++) {
			 		JSONObject databaseJson =json1.getJSONObject(i);
			 		jo = new JSONObject();
			 		String v = databaseJson.get("vendorCode").toString();
			 		String w = databaseJson.get("week").toString();
			 		String s = databaseJson.get("state").toString();
			 		String f = databaseJson.get("fvarCDesc").toString();
			 		Integer q = databaseJson.getInt("quantity");
					for (int j = 0; j < jsonObject.size(); j++) {
						JSONObject excJson =jsonObject.getJSONObject(j);
						 String vs = excJson.get("vendorCode").toString();
			             String ws = excJson.get("week").toString();
			             String ss = excJson.get("state").toString();
						if(v.equals(vs)&&w.equals(ws)&&s.equals(ss)) {
							break;
						}else if(j==jsonObject.size()-1){
							jo.put("vendorCode", v);
							jo.put("week", w);
							jo.put("quantity", q);
							if(Integer.parseInt(s)<=2||Integer.parseInt(s)==7) {
								jo.put("state", "7");
							}else {
								jo.put("state", "8");
							}
							jo.put("fvarCDesc", f);
							jo.put("colorCode", "1");
						}
					}
					if(jo.size()!=0) {
						datas.add(jo);
					}
				}
			 	
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				DBUTils.close(conn, stat, rs);
			}
		 	return datas;
	 }

}
