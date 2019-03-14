var vertical = [];
var exps = ["09:00-10:00", "10:00-11:00", "11:00-11:30", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-16:30", "11:30-13:00", "17:30-18:00"];
var weekDay = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
var dt = new Date();
var getTime = (dt.getHours() > 9 ? dt.getHours() : "0" + dt.getHours()) + ":" + (dt.getMinutes() > 9 ? dt.getMinutes() : "0" + dt.getMinutes());
var getDt=dt.getDay();//返回的是0对应星期一     加1对应后台
var oldState='0';
var onOff=true;
$(document).ready(function() {
	berth();
	AjaxData();
	setInterval(reload,300000);
	var timer = setInterval(time, 1000);
})

function reload(){
	location.reload();
}



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




			//异常动态添加行  
			function addLink(j,add_exp_class){
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
				//设置某个时间段进行写入excel
				if(getTime>"10:57"&&getTime<"11:03"&&onOff){
					AjaxExcel();
					onOff=false;
				}
			}
			


			function weekColor(weekcolor){
				for (var w=0;w<weekDay.length;w++) {
					if(weekcolor==w){
						$(".week:eq("+w+")").css("color","green")
					}
				}
			}
			


function deleteLastLink(){
	for (var g=7;g<exps.length+1;g++) {
		removeLastTwo(g,".exp_border_"+g);
	}
}


//两页数据进行切换
function timerSwitch(){
	$(".self").toggle();
	$(".active").toggle();
	
}


