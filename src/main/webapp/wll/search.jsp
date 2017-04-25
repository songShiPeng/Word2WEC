<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!doctype html>
<html>
<head>
	<meta charset="gb2312">
	<title>古诗</title>
	<meta name="Keywords" content="" >
	<meta name="Description" content="" >
	<link href="css/index.css" rel="stylesheet">
	<!--[if lt IE 9]>
	<script src="js/modernizr.js"></script>
	<![endif]-->
</head>
<body>
<header>

</header>
<style>
	#targetPoem{
		background-repeat: no-repeat;
		position: relative;
		left: 200px;
	}
	#targetPoemContent{
		background-repeat: no-repeat;
		color: black;
		position: relative;
		left: 80px;
		top: 10px;
	}
</style>
<script type="application/javascript" src="/wll/js/jquery-3.2.1.min.js"></script>
<script type="application/javascript">
	$(function () {
		var bImages= $('#bImages').val();
		if(null != bImages){
//            $('#targetPoem').css.append("'background-image' : '/poemImages/"+bImages+" 'background-repeat : no-repeat");
		    $('#targetPoem').css("background-image","url(/poemImages/"+bImages+")");
		}

    });
	function submitImage() {
        var x=document.getElementById("fromInput");  // 找到元素
        x.value="1";
        document.getElementById("poemForm").submit();
    }
</script>
<div class="navswf">
	<object id="customMenu" data="images/nav.swf" width="528" height="70" type="application/x-shockwave-flash"><param name="allowScriptAccess" value="always"><param name="allownetworking" value="all"><param name="allowFullScreen" value="true"><param name="wmode" value="transparent"><param name="menu" value="false"><param name="scale" value="noScale"><param name="salign" value="1">
	</object>

</div>

<div id="option" style="margin-left: 260px;height: 30px;">
	<form action="getPoem.mvc" method="post" id="poemForm">
	<label>输入汉字:</label>&nbsp;&nbsp;
		<form:input type="text" class="search" path="poemRequest.hanZi" placeholder="请输入汉字" ></form:input>
<form:select path="poemRequest.ziShu" id="classList">
	<form:option value="5" label="字数"/>
	<form:option value="5" label="五言"/>
	<form:option value="7" label="七言"/>
</form:select>
	</select>
<form:select path="poemRequest.xs" id="classList">
	<form:option value="PPZP" label="诗歌形式"/>
	<form:option value="PPZP" label="平平仄平"/>
	<form:option value="ZPZP" label="仄平仄平"/>
	<form:option value="ZZPZ" label="仄仄平仄"/>
</form:select>

	<button type="submit">开始作诗</button>
		<input type="hidden" name="from"  id ="fromInput" value="6"></input>
	<button type="submit">换一换</button>
        <input type="button"  onclick="javascript:submitImage();" value="图片作诗"/>
	</form>
<input type="hidden" value="${poemRequest.images}" id="bImages"/>
</div>

<div class="blank"></div>
<article style="margin-top: -18px;" id = "article" style="">
	<div class="content">
		<div class="bloglist">
			<!--article begin-->

			<ul>
				<div id="targetPoem" color="red">
					<div id = "targetPoemContent">
					${poemRequest.targetPoem}
					</div>

				</div>
			</ul>
			<!--article end-->
		</div>
	</div>
</article>
</body>
</html>
