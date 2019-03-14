			let weeks=7;
			$(document).ready(() => {
				//遍历星期
				f_weeks();
			})
			function f_weeks(){
				let week=["天/Sunday","一/Monday","二/Tuesday","三/Wednesday","四/Thursday","五/Friday","六/Saturday"];
				for(let i=0;i<weeks;i++){
					$(".content_header").append(`
						<div class="col borders">
					<div class="row">
						<div class="col-12 inside_borders week" >
							<p>星期${week[i]}</p>
						</div>
						<div class="col-12 inside_borders" ></div>
						<div class="col-12 title_height" >
							<div class="row">
								<div class="col-4 in_inside_borders" >
									<p>供应商Supplier</p>
								</div>
								<div class="col-4 in_inside_borders">
									<p>数量Quantity</p>
								</div>
								<div class="col-4 ">
									<p>状态Status</p>
								</div>
							</div>
						</div>
					</div>
				</div>
					`);
				}
			}
