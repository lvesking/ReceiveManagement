package cn.jihe.management.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import cn.jihe.management.bean.ReceiveList;
import cn.jihe.management.bean.SelectDate;
import cn.jihe.management.controller.StartAndEndTime;
import cn.jihe.management.excel.ReadExcel;
import cn.jihe.management.jdbc.DBUTils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ListService {
	
	
	public JSONArray getUltimatelyData() {
		//192.168.0.90的sqlserver数据库
				Connection conn=null;
				PreparedStatement stat=null;
				ResultSet rs=null;
				ReceiveList receiveList=new ReceiveList();
				JSONObject jo = null;
				JSONArray json = new JSONArray();
				JSONArray datas=null;
				ReadExcel read=new ReadExcel();
				SelectDate date=new SelectDate();
				StartAndEndTime saet=new StartAndEndTime();
				//比较时间  赋值状态值
				DataTimeService ds=new DataTimeService();
				String dates[]=saet.getTime();
				date.setBeginDate(dates[0]);//开始查询的时间(包含)dates[0]
				date.setOverDate(dates[1]);//结束的时间(不包含)dates[1]
				String sql = " select \r\n" + 
						"  h.RH_fvarVendorCode,h.RH_fvarInputTime,l.RL_fnumPlanQty,v.VM_fvarCDesc  \r\n" + 
						"  from ReceiveHead h,ReceiveList l,VendorMaster v \r\n" + 
						"  where h.RH_fvarReceiveNo=l.RL_fvarReceiveNo AND v.VM_fvarCode=h.RH_fvarVendorCode AND"+ 
						" h.RH_fvarInputTime>? AND h.RH_fvarInputTime<?;";
				try {
					//数据库连接
					conn=DBUTils.getConn();
					stat = conn.prepareStatement(sql);
					//赋值开始时间和结束时间查询
					stat.setString(1, date.getBeginDate());
					stat.setString(2, date.getOverDate());
					rs=stat.executeQuery();
					//获取时间进行赋值状态值
					List<?> listtime=read.getTime();
					while (rs.next()) {
						receiveList.setRH_fvarInputTime(rs.getString("RH_fvarInputTime").split("\\.")[0]);
						receiveList.setRH_fvarVendorCode(rs.getString("RH_fvarVendorCode"));
						receiveList.setRL_fnumPlanQty(rs.getString("RL_fnumPlanQty"));
						receiveList.setVM_fvarCDesc(rs.getString("VM_fvarCDesc"));
						
						String[] dateTime=receiveList.getRH_fvarInputTime().split("\\ ");
						String state=null;
						
						for(int i = 0 ; i < listtime.size() ; i++) {
								  String[] times=listtime.get(i).toString().split("-");
									  if(ds.isEffectiveDate(dateTime[1], times[0]+":00", times[1].split("（")[0]+":00")&&i<=listtime.size()-3) {
								        	state=i+"";
								        	break;
								       }else if(ds.isEffectiveDate(dateTime[1], "00:00:00", "13:00:00")){
								        	state="7";
								       }else{
								        	state="8";
								       }
						}
					        String vendorCode=receiveList.getRH_fvarVendorCode();
					        String quantity=receiveList.getRL_fnumPlanQty();
					        String fvarCDesc=receiveList.getVM_fvarCDesc();
						        	jo = new JSONObject();
									jo.put("vendorCode", vendorCode);
									jo.put("fvarCDesc", fvarCDesc);
									jo.put("week", ds.dateToWeek(dateTime[0]));
									jo.put("quantity", Integer.parseInt(quantity));
									jo.put("state", state);
									json.add(jo);
					}
					
					JSONArray data=ListService.delRepeatIndexid(json);//去重
					datas=ExcelJsonService.compareJson(data);//相互比较得到最终数据
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					DBUTils.close(conn, stat, rs);
				}
		
		
		
		return datas;
		
	}
	
	
	
	
	
	
	
	

	 //去掉json数组中的重复数据
	 public static JSONArray delRepeatIndexid(JSONArray array) {
		    JSONArray arrayTemp = new JSONArray();
		    int num = 0;
		    for(int i = 0;i < array.size();i++){
		        if(num==0){
		            arrayTemp.add(array.get(i));
		        }else{
		            int numJ = 0;
		            for(int j = 0;j < arrayTemp.size(); j++){
		                JSONObject newJsonObjectI = (JSONObject)array.get(i);
		                JSONObject newJsonObjectJ = (JSONObject)arrayTemp.get(j);
		                String v = newJsonObjectI.get("vendorCode").toString();
		                String w = newJsonObjectI.get("week").toString();
		                String s = newJsonObjectI.get("state").toString();
		                Integer q = newJsonObjectI.getInt("quantity");
		                String m=newJsonObjectI.get("fvarCDesc").toString();
		                
		                String vs = newJsonObjectJ.get("vendorCode").toString();
		                String ws = newJsonObjectJ.get("week").toString();
		                String ss = newJsonObjectJ.get("state").toString();
		                Integer qs = newJsonObjectJ.getInt("quantity");
		                if(v.equals(vs)&&w.equals(ws)&&s.equals(ss)){
		                	q+=qs;
		                    arrayTemp.remove(j);
		                    JSONObject newObject = new JSONObject();
		                    newObject.put("vendorCode", v);
		                    newObject.put("week", w);
		                    newObject.put("quantity", q);
		                    newObject.put("state", s);
		                    newObject.put("fvarCDesc", m);
		                    arrayTemp.add(newObject);
		                    break;
		                }
		                numJ++;
		            }
		            if(numJ-1 == arrayTemp.size()-1){
		                arrayTemp.add(array.get(i));
		            }
		        }
		        num++;
		    }
		    return arrayTemp;
		}
	 
	 
}
