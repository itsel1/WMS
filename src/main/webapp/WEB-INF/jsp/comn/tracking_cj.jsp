<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
<meta charset="UTF-8">
<title>Tracking</title>
<link href="/custom/css/tracking.css" rel="stylesheet">
<style type="text/css">
</style>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
	<div class="card">
       	<div class="title">${hawbNo}</div>
       	<div class="info">
       		<div class="row">
       			<div class="col-7">
       				<span id="heading">Order Number</span><br/>
       				<span id="details">${orderNo}</span>
       			</div>
       			<div class="col-5">
       				<span id="heading">Receiver</span><br/>
       				<span id="details">${cneeName}</span>
       			</div>
       		</div>
       	</div>
       <%--  <ul class="progressbar">
        	<c:set var="step01" value="step0"/>
        	<c:set var="step02" value="step0"/>
        	<c:set var="step03" value="step0"/>
        	<c:set var="step04" value="step0"/>
        	<c:set var="step05" value="step0"/>
        	<c:forEach items="${fullTracking}" var="tracking">
        		<c:if test="${tracking.statusCode == 100}">
        			<c:set var="step01" value="active"/>
        		</c:if>
        		<c:if test="${tracking.statusCode == 200}">
        			<c:set var="step02" value="active"/>
        		</c:if>
        		<c:if test="${tracking.statusCode == 300}">
        			<c:set var="step03" value="active"/>
        		</c:if>
        		<c:if test="${tracking.statusCode == 500}">
        			<c:set var="step04" value="active"/>
        		</c:if>
        		<c:if test="${tracking.statusCode == 600}">
        			<c:set var="step05" value="active"/>
        		</c:if>
        	</c:forEach>
            <li class="${step01}">배송 접수</li>
            <li class="${step02}">창고 입고</li>
            <li class="${step03}">출고</li>
            <li class="${step04}">배송중</li>
            <li class="${step05}">배달완료</li>
        </ul>
        <div class="container">
        	<div class="wrapper">
        		<ul class="sessions">
        			<c:forEach items="${fullTracking}" var="track">
        			<li>
        				<div class="time">${track.dateTime}</div>
        				<p>${track.description}</p>
        				<p>${track.location}</p>
        			</li>
		        	</c:forEach>
        		</ul>
        	</div>
        </div> --%>
        
        <div class="track-timeline">
	        <ol>
	        	<c:set var="status1" value=""/>
	        	<c:set var="status2" value=""/>
	        	<c:set var="status3" value=""/>
	        	<c:set var="status4" value=""/>
	        	<c:set var="status5" value=""/>
	        	<c:forEach items="${fullTracking}" var="tracking">
	        		<c:if test="${tracking.statusCode == 100}">
	        			<c:set var="status1" value="active"/>
	        		</c:if>
	        		<c:if test="${tracking.statusCode == 200}">
	        			<c:set var="status2" value="active"/>
	        		</c:if>
	        		<c:if test="${tracking.statusCode == 300}">
	        			<c:set var="status3" value="active"/>
	        		</c:if>
	        		<c:if test="${tracking.statusCode == 500}">
	        			<c:set var="status4" value="active"/>
	        		</c:if>
	        		<c:if test="${tracking.statusCode == 600}">
	        			<c:set var="status5" value="active"/>
	        		</c:if>
	        	</c:forEach>
	            <li class="${status1}"><span>배송접수</span></li>
	            <li class="${status2}"><span>입고</span></li>
	            <li class="${status3}"><span>출고</span></li>
	            <li class="${status4}"><span>배송중</span></li>
	            <li class="${status5}"><span>배달완료</span></li>
	        </ol>
		</div>
		
		<div class="container">
        	<div class="wrapper">
        		<ul class="sessions">
        			<c:forEach items="${fullTracking}" var="track">
        			<li class="${track.statusCode eq '600' ? 'completed' : 'in-progress'}">
        				<div class="time">${track.dateTime}</div>
        				<p>${track.descriptionKor}</p>
        				<p>${track.locationKor}</p>
        			</li>
		        	</c:forEach>
        		</ul>
        	</div>
        </div>
    </div>
    
    
</body>
</html>