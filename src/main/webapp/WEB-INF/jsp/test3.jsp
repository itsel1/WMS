<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.wrapper {
	    display: grid;
	    grid-template-columns: 100px 100px 100px;
	    grid-template-rows: 50px 50px ;
	}
	
	.item {
	  background-color: lightblue;
	  border: 1px solid darkblue;
	  color: blue;
	  text-align: center;
	}
</style>
</head>
<body>
	<div class="wrapper">
		<div class="item">1</div>
		<div class="item">2</div>
		<div class="item">3</div>
		<div class="item">4</div>
		<div class="item">5</div>
		<div class="item">6</div>
	</div>
</body>
</html>