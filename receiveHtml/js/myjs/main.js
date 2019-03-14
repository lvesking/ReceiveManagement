

function AjaxData() {
	
	$.ajax({
				type: "get",
				url: "/receiveManagement/receiveData",
				async: true,
				success: function(obj) {
					var data=JSON.parse(obj);
					for (var j=0;j<data.length;j++) {
							var week="";
							var state="";
							 //判断星期
							for (var h=0;h<weekDay.length;h++) {
								if(data[j].week==h){
									week=h;
								}
							}
							//判断属于哪个区间
							for (var g=0;g<exps.length;g++) {
								if(data[j].state==(g+"")){
									state=data[j].state;
								}
							}
							
							
							//如果是异常窗口  最后一格html()不等于空  添加一行
							if($(".supplier .col-12:eq("+(subscript+(vertical[oldState]-1))+")").html()!=""&&subscript!=undefined&&(oldState=='7'||oldState=='8')){
								addLink(1,".exp_border_"+oldState);
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
							var timeIndex=0;
							//设置没到的时间为白色
							if('00:00'<getTime&&getTime<'10:00'){
								timeIndex=0
							}
							if('10:00'<getTime&&getTime<'11:00'){
								timeIndex=1
							}
							if('11:00'<getTime&&getTime<'11:30'){
								timeIndex=2
							}
							if('11:30'<getTime&&getTime<'14:00'){
								timeIndex=3
							}
							if('14:00'<getTime&&getTime<'15:00'){
								timeIndex=4
							}
							if('15:00'<getTime&&getTime<'16:00'){
								timeIndex=5
							}
							if('16:00'<getTime&&getTime<'23:59'){
								timeIndex=6
							}
							if(week>getDt){
								data[j].colorCode="2";
							}
							if(week==getDt&&state>timeIndex){
								data[j].colorCode="2";
								
							}
							if(week<=getDt&&state==7){
								data[j].colorCode="1";
							}
							if(week<=getDt&&state==8&&getTime>'13:00'){
								
								data[j].colorCode="1";
							}
							
							//循环赋值 不为空就添加到下一格
							for (var o=0;o<vertical[state];o++) {	
								if($(".supplier .col-12:eq("+(subscript+o)+")").html()==""){
									
									$(".supplier .col-12:eq("+(subscript+o)+")").html(data[j].fvarCDesc);
									$(".quantity .col-12:eq("+(subscript+o)+")").html(data[j].quantity);
									if(data[j].colorCode=="0"){
										$(".status .col-12:eq("+(subscript+o)+")").css("background","green");
										if(subscript>=(6*weekDay.length*(exps.length-2))){
											$(".status .col-12:eq("+(subscript+o)+")").css("background","red");
										}
									}else if(data[j].colorCode=="1"){
										$(".status .col-12:eq("+(subscript+o)+")").css("background","red");
									}else{
										$(".status .col-12:eq("+(subscript+o)+")").css("background","white");
									}
									
									break;
								}
								
							}
							
							
							
							
							oldState=state;
					}
					
					deleteLastLink();
				}
		})
	var switchs=setInterval(timerSwitch,5000);
}



