<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>搜索界面</title>
	<style>
		*{
			margin:0;
			padding:0;
		}
		.outer{
			width:920px;
			height:540px;
			background:url("bj.jpg") no-repeat;
			background-size:100% 100%;
			margin:50px auto;
			overflow:hidden;
			border:1px solid white;
		}
		.inner1{
			width:400px;
			height:500px;
			float:left;
			margin-left:15px;
			margin-top:20px;
		}
		.search{
			display:inline-block;
			width:250px;
			height:30px;
			text-indent:2em;
		}
		.mid{
			width:350px;
			height:100px;
			border:1px solid darkgray;
			border-radius: 5px;
			position:relative;
			margin-top:70px;
		}
		.sum{
			width:35px;
			height:20px;
			position:absolute;
			left:15px;
			top:-10px;
			background:white;
			color:blue;
			text-align:center;
			line-height:20px;
		}
		.sum1{
			margin-top:40px;
			margin-left:30px;
		}
		.sg{
			width:70px;
		}
		.btn{
			width:100px;
			height:30px;
			border:1px solid gray;
			border-radius:5px;
			display:block;
			margin-left:100px;
			margin-top:40px;
		}
		.inner2{
			width:480px;
			height:500px;
			border:1px solid gray;
			float:left;
			margin-top:20px;
			/*margin-left:20px;*/
		}
	</style>
</head>
<body>
	<div class="outer">
		<div class="inner1">
            <form action="getPoem.mvc" method="post">
			<div style="margin-top:30px;">输入汉字：<form:input type="text" class="search" path="poemRequest.hanZi" placeholder="请输入汉字" ></form:input></div>
			<div class="mid">
				<div class="sum">字数</div>
                <form:radiobutton  class="sum1" path="poemRequest.ziShu" value="5"/>五言
                <form:radiobutton  class="sum1" path="poemRequest.ziShu" value="7"/>七言
			</div>
			<div class="mid">
				<div class="sum sg">诗歌形式</div>
                <form:radiobutton class="sum1" path="poemRequest.xs" value="PPZP"/>平平仄平
                <form:radiobutton class="sum1" path="poemRequest.xs" value="ZPZP"/>仄平仄平
                <form:radiobutton class="sum1" path="poemRequest.xs" value="ZZPZ"/>仄仄平仄
			</div>
			<button class="btn" type="submit">开始作诗</button>
            </form>
		</div>
		<div class="inner2">
            <div align="center">
                <PRE>${poemRequest.targetPoem}

           <!--
            <form:textarea path="poemRequest.targetPoem" rows="10" cols="50"/>
            -->
            </PRE>
            </div>
		</div>
	</div>
</body>
</html>