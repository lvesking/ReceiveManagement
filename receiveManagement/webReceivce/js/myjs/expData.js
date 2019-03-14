	//异常
			var exps=["09:00-10:00","10:00-11:00","11:00-11:30","13:00-14:00","14:00-15:00","15:00-16:00","16:00-16:30","11:30-13:00","17:30-18:00"];
			var weekDay = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
			var vertical=[];//竖向表格数
			var sum=0;//相同日子和一个区间的时间和供应商编码一样的总数量
			var numbers="";
			$(document).ready(function() {
				f_weeks();
				addLast();
				AjaxData();
				var timer = setInterval(time, 1000);
				//状态
				
			})
			function f_weeks(){
				var week=["天/Sunday","一/Monday","二/Tuesday","三/Wednesday","四/Thursday","五/Friday","六/Saturday"];
				for(var i=0;i<week.length;i++){
					$(".content_header").append(
						'<div class="col borders">'+
					'<div class="row">'+
						'<div class="col-12 inside_borders week" >'+
							'<p>星期'+week[i]+'</p>'+
						'</div>'+
						'<div class="col-12 title_height" >'+
							'<div class="row">'+
								'<div class="col-5 in_inside_borders" >'+
									'<p>供应商Supplier</p>'+
								'</div>'+
								'<div class="col-4 in_inside_borders">'+
									'<p>数量Quantity</p>'+
								'</div>'+
								'<div class="col-3 ">'+
									'<p>状态Status</p>'+
								'</div>'+
							'</div>'+
						'</div>'+
					'</div>'+
				'</div>'
					);
				}
				f_exp();
			}
			
			//遍历所有追加的表
			function f_exp(){
				var num=0;
				for (var i = 0; i < exps.length; i++) {
					z_exp(exps[i]);
					$(".exp_border").eq(i).addClass("exp_border_"+i);
				}
				//最后一个异常下边框显示
				$(".exp_border:last").css("border-bottom","1px solid #000000");
				exp_tables(".exp_border");
				initWebHtml();
			}
			
			
			//追加表
			function z_exp(e){
				var exp='<div class="row exp_border">'+
				'<div class="col-1 borders col_time tab-p ">'+
					'<p class="text-center  h_line">'+e+'</p>'+
				'</div>'+
				'<div class="col-md-auto borders berth addBorder exp-berth" >'+
					'<div class="col-12 data_borders">'+
					'</div>'+
					'<div class="col-12 data_borders">'+
					'</div>'+
				'</div>'+
				'</div>'+
			'</div>';
			$(exp).appendTo(".content");
			}
			
			
			//遍历边框表格
			function exp_tables(need_class){
				for (var i=0;i<weekDay.length;i++) {
					$(need_class).append(
						'<div class="col borders" style="border-bottom: none;">'+
					'<div class="row  message">'+
						'<div class="col-5  t_ver_borders addBorder supplier">'+
						'<div class="col-12 data_borders">'+

						'</div>'+
							
						'<div class="col-12 data_borders">'+

						'</div>'+
							
						'</div>'+
						'<div class="col-4 t_ver_borders addBorder quantity" >'+
							'<div class="col-12 data_borders">'+

							'</div>'+
							'<div class="col-12 data_borders">'+

							'</div>'+
						'</div>'+
						'<div class="col-3  addBorder status" >'+
							'<div class="col-12 data_borders">'+

							'</div>'+
							'<div class="col-12 data_borders">'+

							'</div>'+
							
						'</div>'+
					'</div>'
					);
				}
				$(".tab-p:eq(-2) p").html("11:30-13:00（异常窗口）");
				$(".tab-p:last p").html("17:30-18:00（异常窗口）");
			}
			
			//异常动态添加行  
			function add(j,add_exp_class){
				//初始化
				
				$(".exp-berth p").remove();
				var add='<div class="col-12 data_borders">'+

							'</div>';
			
				for (var i = 0; i < j; i++) {
					$(add_exp_class+" .addBorder .last_borders").before(add);
				}
				//设置异常表的高度
				var expTimeHeight=($(".data_borders").height()+1)*($(add_exp_class+" .exp-berth .col-12").length+j-1);
				$(add_exp_class+" .tab-p").height(expTimeHeight);
				$(add_exp_class+" .addBorder").height(expTimeHeight);
				expBoerth();
			}
			
			
			
			
			
			function addLast(){
					var last='<div class="col-12 last_borders">'+
					
							'</div>';
							
							$(last).appendTo(".addBorder");
							expBoerth();
			}
			
			
			//泊位赋值
			function expBoerth(){
				for (var i = 0; i < $(".exp-berth").length; i++) {
					for (var j = 0; j < $(".exp-berth:eq("+i+") .data_borders").length; j++) {
						$(".exp-berth:eq("+i+") .data_borders:eq("+j+")").append(
							'<p>'+(j+1)+'</p>'
						);
					}
					var lastNum=$(".exp-berth:eq("+i+") .data_borders").length+1;
					$(".exp-berth:eq("+i+") .last_borders").append(
						'<p>'+lastNum+'</p>'	
					);
				}
				tableLengths();
			}
			

			function AjaxData(){
				var a=$.ajax({
					type:"get",
					url:"/tomcat/receiveManagement/receiveData",
					async:true,
					success:function(obj){
						var data=JSON.parse(obj);
						console.log(data)
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
						removeLastTwo(7,"exp_border_7");
						removeLastTwo(8,"exp_border_8");
					}
				});
			}
			//获取列的长度和清空内容表格
			function tableLengths(){
				vertical=[];
				for (var i=0;i<exps.length;i++) {
					vertical.push($(".exp_border_"+i+" .exp-berth .col-12").length);
				}
			}


			function time (){
				var e=new Date();
				var dateTime=e.getFullYear()+"年"+(e.getMonth()+1)+"月"+e.getDate()+"日 "+e.getHours() + ":" + (e.getMinutes()>9?e.getMinutes() :"0"+e.getMinutes())
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



		function initWebHtml(){
			$(".supplier .col-12").html("");
			$(".quantity .col-12").html("");
			$(".status .col-12").html("");
		}
		
		
		
		function removeLastTwo(sums,exp_class){
			if(vertical[sums]>3){//异常格子大于3
				$("."+exp_class+" .supplier").each(function(i,cls){
					var index=2;
					var supplier=$("."+exp_class+"  .supplier:eq("+i+") .last_borders").html();
					var quantity=$("."+exp_class+" .quantity:eq("+i+") .last_borders").html();
					if(supplier!=""&&quantity!=""){
						var status=$("."+exp_class+" .status:eq("+i+") .last_borders").css("background-color");
						for (var v=0;v<vertical[sums];v++) {
							if($("."+exp_class+" .supplier:eq("+i+") .data_borders:eq("+index+")").html()!=""){
								index++;
							}else{
								console.log(index)
								$("."+exp_class+" .supplier:eq("+i+") .data_borders:eq("+index+")").html(supplier);
								$("."+exp_class+" .quantity:eq("+i+") .data_borders:eq("+index+")").html(quantity);
								$("."+exp_class+" .status:eq("+i+") .data_borders:eq("+index+")").css("background",status);
								break;
							}
						}
						$("."+exp_class+" .supplier:eq("+i+") .last_borders").html("");
						$("."+exp_class+" .quantity:eq("+i+") .last_borders").html("");
						$("."+exp_class+" .status:eq("+i+") .last_borders").css("background","#FFFFFF");
						
					}
					console.log(cls)
				})
				
			}
			
		}
