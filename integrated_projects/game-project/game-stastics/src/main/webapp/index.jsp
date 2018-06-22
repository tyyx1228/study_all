<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>传智云</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/styles/rum.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/data/jquery.dataTables.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/echarts/css/echarts.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/commons.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/data/jquery.dataTables.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/menu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/echarts/dist/echarts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/setup.js"></script>

<script type="text/javascript">
	var context_path = "${pageContext.request.contextPath}";
	$(function() {
		init_menu();		
	})
</script>
</head>

<body>
	<div class="bodyq">
		<div class="header">
			<div class="logo">
				<a href="http://www.itcast.cn/" target="_blank"><img src="static/images/logo.png" height="50" align="middle" /></a>
			</div>
			<div class="mainmenu">
				<ul id="navigation">
					
				</ul>
			</div>
			<div class="mini_nav">
				<div class="mini_nav_fl">
					<div id="topAlert" onmouseover="this.className='alert02'" onmouseout="this.className='alert01'" class="alert01" title="告警">
						<div class="prompt" style="display: none;"></div>
					</div>
					<div onmouseover="this.className='information02'" onmouseout="this.className='information01'" class="information01" title="帮助" onclick="help()"></div>
				</div>
				<div class="jump_button">
					<div class="button">
						<span class="user_name">admin</span>
					</div>
				</div>
			</div>
		</div>
		
		<div class="content_sidebar content_sidebar_background">
			<div id="sidebar" class="sidebar">
				<div id="arrow_div" class="sidebar_arrow sidebar_arrow_left" mini="${mini}"></div>
				<ul id="smenu">
					
				</ul>
			</div>
			<div class="meumToolTip tooltip2" style="margin:20px; float:left;display: none;position: absolute; z-index: 1100">
				<div class="icon_arrow"></div>
				<div id="tooltipContent" style="float:left;">正常</div>
			</div>
			
			<div id="conright" class="conright">
				<div id="main" class="main" style="width:1000px;height:525px;margin:0 auto;"></div>
			</div>
		</div>
	</div>
</body>

</html>