﻿var vertical = [];
var exps = ["09:00-10:00", "10:00-11:00", "11:00-11:30", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-16:30", "11:30-13:00", "17:30-18:00"];
var weekDay = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
$(document).ready(function() {
	berth();
	AjaxData();
	var timer = setInterval(time, 1000);
})

//泊位赋值
function berth() {
	$(".col-md-auto .col-12").html("");
	$(".col-md-auto").each(function(i, cls) {
		var length = $(".col-md-auto:eq(" + i + ") .data_borders").length;
		for(var d = 0; d < length; d++) {
			$(".col-md-auto:eq(" + i + ") .data_borders").eq(d).html("<p>" + (d + 1) + "</p>");
		}
	})
	tableLengths();
}

//获取列的长度和清空内容表格
function tableLengths() {
	vertical = [];
	for(var o = 0; o < exps.length; o++) {
		vertical.push($(".exp_border_" + o + " .exp-berth .col-12").length);
	}
};

function AjaxData() {
	$.ajax({
				type: "get",
				url: "/receive/receiveManagement/receiveData",
				async: true,
				success: function(obj) {
					var data=JSON.parse(obj);
					for (var j=0;j<data.length;j++) {
							var week="";
							var state="";
							 
							for (var h=0;h<weekDay.length;h++) {
								if(data[j].week==weekDay[h]){
									week=h;
								}
							}
							for (var g=0;g<exps.length;g++) {
								if(data[j].state==(g+"")){
									state=data[j].state;
								}
							}
							
							//如果是异常窗口  最后一格html()不等于空  添加一行
							if($(".supplier .col-12:eq("+(subscript+(vertical[oldState]-1))+")").html()!=""&&subscript!=undefined&&(oldState=="7"||oldState=="8")){
								add(1,".exp_border_"+oldState);
							}
							
							var subscript=0; 
							sub();
							function sub(){
								//每个时间段横向是7 乘与纵向格子数加上纵向格子数乘周期下标
								for (var t=0;t<vertical.length;t++) {
									if(t<state){
										subscript+=vertical[t]*weekDay.length
									}else{
										subscript+=vertical[state]*week;
										break;
									}
								}
							}
							//循环赋值 不为空就添加到下一格
							for (var o=0;o<vertical[state];o++) {	
								if($(".supplier .col-12:eq("+(subscript+o)+")").html()==""){
									$(".supplier .col-12:eq("+(subscript+o)+")").html(data[j].fvarCDesc);
									$(".quantity .col-12:eq("+(subscript+o)+")").html(data[j].quantity);
									if(data[j].colorCode=="0"){
										$(".status .col-12:eq("+(subscript+o)+")").css("background","green");
										if(subscript>=168){
											$(".status .col-12:eq("+(subscript+o)+")").css("background","red");
										}
									}else{
										$(".status .col-12:eq("+(subscript+o)+")").css("background","red");
										
									}
									
									break;
								}
								
							}
							var oldState=state;
					}
					removeLastTwo(7,".exp_border_7");
					removeLastTwo(8,".exp_border_8");
				}
		})
}



			//异常动态添加行  
			function add(j,add_exp_class){
				//初始化
				$(".exp-berth p").remove();
				var add='<div class="col-12 data_borders">'+

							'</div>';
				$(add_exp_class+" .addBorder").each(function(i,cls){
					$(add_exp_class+" .addBorder:eq("+i+") .data_borders:last").before(add);
					
				})
				//设置异常表的高度
				var expTimeHeight=($(".data_borders").height()+1)*($(add_exp_class+" .exp-berth .col-12").length+j-1);
				$(add_exp_class+" .tab-p").height(expTimeHeight);
				$(add_exp_class+" .addBorder").height(expTimeHeight);
				berth();
			}


			function removeLastTwo(sums,exp_class){
				if(vertical[sums]>3){//异常格子大于3
					$(exp_class+" .supplier").each(function(i,cls){
						var index=2;
						var supplier=$(exp_class+"  .supplier:eq("+i+") .data_borders:last").html();
						var quantity=$(exp_class+" .quantity:eq("+i+") .data_borders:last").html();
						if(supplier!=""&&quantity!=""){
							var status=$(exp_class+" .status:eq("+i+") .data_borders:last").css("background-color");
							//循环列数  如果为空赋值  不为空  下一列
							for (var v=0;v<vertical[sums];v++) {
							if($(exp_class+" .supplier:eq("+i+") .data_borders:eq("+index+")").html()!=""){
								index++;
							}else{
								$(exp_class+" .supplier:eq("+i+") .data_borders:eq("+index+")").html(supplier);
								$(exp_class+" .quantity:eq("+i+") .data_borders:eq("+index+")").html(quantity);
								$(exp_class+" .status:eq("+i+") .data_borders:eq("+index+")").css("background",status);
								break;
							}
						}
							
						}
						$(exp_class+" .supplier:eq("+i+") .data_borders:last").remove();
						$(exp_class+" .quantity:eq("+i+") .data_borders:last").remove();
						$(exp_class+" .status:eq("+i+") .data_borders:last").remove();
						$(exp_class+" .col-md-auto:eq("+i+") .data_borders:last p").remove();
						$(exp_class+" .col-md-auto:eq("+i+") .data_borders:nth-last-child(2)").css("border","none");
						var removeTimeHeight=($(".data_borders").height()+1)*($(exp_class+" .exp-berth .col-12").length-1);
						$(exp_class+" .tab-p").height(removeTimeHeight);
						$(exp_class+" .addBorder").height(removeTimeHeight);
					})
				}
			}


			function time() {
				var e = new Date();
				var dateTime = e.getFullYear() + "年" + (e.getMonth() + 1) + "月" + e.getDate() + "日 " + e.getHours() + ":" + (e.getMinutes() > 9 ? e.getMinutes() : "0" + e.getMinutes());
				$("#dataTime").html(dateTime);
				var weekcolor=e.getDay();//获取当前是星期几
				weekColor(weekcolor);
			}
			


			function weekColor(weekcolor){
				for (var w=0;w<weekDay.length;w++) {
					if(weekcolor==w){
						$(".week:eq("+w+")").css("color","green")
					}
				}
			}

			