function init_menu(){
	$.getJSON(context_path + "/rest/menu", function(data){
		$.each(data, function(i, pm){
			if(i==0){
				$.getJSON(context_path + "/rest/menu/"+ pm.id, function(data){
					$.each(data, function(j, sm){
						if(j==0){
							var scurrent = "id=current";
						}
						$("#smenu").append("<li "+ scurrent +"><a href="+ sm.action +"><div class="+ sm.style +" name="+ sm.name +">"+ sm.name +"</div></a></li>");
					});
				});
				var pcurrent = "id=current";
			}
			$("#navigation").append("<li "+ pcurrent +"><a href='javascript:;' onclick='load_sec_menu(this, "+ pm.id +")'><div class="+ pm.style +">"+ pm.name +"</div></a></li>");
		});
	});
}

function load_sec_menu(obj, pid){
	$("#navigation").children("[id=current]").removeAttr("id");
	$(obj).parent().attr("id", "current");
	$("#smenu").children().remove();
	var mini = $("#arrow_div").attr("mini");
	$.getJSON(context_path + "/rest/menu/"+ pid, function(data){
		$.each(data, function(j, sm){
			var scurrent = "";
			var name = sm.name
			if(j==0){
				scurrent = "id=current";
			}
			if(mini && mini == "true"){
				name = ""
			}
			$("#smenu").append("<li "+ scurrent +"><a href='javascript:;' onclick='"+ sm.action +"'><div class="+ sm.style +" name="+ sm.name +">"+ name +"</div></a></li>");
		});
	});
	
	
	
	
}