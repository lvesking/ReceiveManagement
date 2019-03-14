	
	function AjaxExcel(){
		$.ajax({
				type: "post",
				url: "/receiveManagement/getExcel",
				async: true,
				success: function(obj) {
				}
		})
	}
