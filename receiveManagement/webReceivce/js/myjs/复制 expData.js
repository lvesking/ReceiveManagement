	//异常
			let exps=["11：30-13：00（异常处理窗口）","17:30-18:00（异常处理窗口）"];
			
			$(document).ready(() => {
				f_exp();
			})
			
			//遍历所有追加的异常表
			function f_exp(){
				let num=0;
				for (let i = 0; i < exps.length; i++) {
					z_exp(exps[i]);
					$(".exp_border").eq(i).addClass("exp_border_"+i);
				}
				//最后一个异常下边框显示
				$(".exp_border:last").css("border-bottom","1px solid #000000");
				exp_tables(".exp_border");
			}
			
			
			//追加异常表
			function z_exp(e){
				let exp=`<div class="row exp_border">
				<div class="col borders col_time exp_time">
					<p class="text-center  h_line">${e}</p>
				</div>
				<div class="col-md-auto borders berth addBorder exp-berth" >
					<div class="col-12 data_borders">
					</div>
					<div class="col-12 data_borders">
					</div>
				</div>
				</div>
			</div>`;
			$(exp).appendTo(".content");
			}
			
			
			//遍历边框表格
			function exp_tables(need_class){
				for (let i=0;i<weeks;i++) {
					$(need_class).append(`
						<div class="col borders" style="border-bottom: none;">
					<div class="row">
						<div class="col-4 t_ver_borders addBorder" >
							<div class="col-12 data_borders">

							</div>
							
							<div class="col-12 data_borders">

							</div>
							
						</div>
						<div class="col-4 t_ver_borders addBorder" >
							<div class="col-12 data_borders">

							</div>
							<div class="col-12 data_borders">

							</div>
						</div>
						<div class="col-4  addBorder" >
							<div class="col-12 data_borders">

							</div>
							<div class="col-12 data_borders">

							</div>
							
						</div>
					</div>
					`);
				}
				add(1,".exp_border_1");
			}
			
			
			//异常动态添加行   j=追加的次数
			function add(j,add_exp_class){
				let add=`<div class="col-12 data_borders">

							</div>`;
				let last=`<div class="col-12 last_borders">
				
							</div>`;
				for (let i = 0; i < j; i++) {
					$(add).appendTo(add_exp_class+" .addBorder");
				}
				$(last).appendTo(".addBorder");
				adjustmentHeight(j,add_exp_class);
			}
			
			//设置异常表的高度
			function adjustmentHeight(j,add_exp_class){
				let expTimeHeight=$(".last_borders").height()*(3+j);
				$(add_exp_class+" .exp_time").height(expTimeHeight);
				$(add_exp_class+" .addBorder").height(expTimeHeight);
			}
			
			
			
			
			
			
			
			
			