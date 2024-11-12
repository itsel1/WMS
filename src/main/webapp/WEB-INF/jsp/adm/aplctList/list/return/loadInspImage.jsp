<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
	<link rel="stylesheet" href="/assets/css/bootstrap-datepicker3.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
     	@import url(https://fonts.googleapis.com/css?family=Varela+Round);

		.slides {
		    padding: 0;
		    width: 609px;
		    height: 420px;
		    display: block;
		    margin: 0 auto;
		    position: relative;
		}
		
		.slides * {
		    user-select: none;
		    -ms-user-select: none;
		    -moz-user-select: none;
		    -khtml-user-select: none;
		    -webkit-user-select: none;
		    -webkit-touch-callout: none;
		}
		
		.slides input { display: none; }
		
		.slide-container { display: block; }
		
		.slide {
		    top: 0;
		    opacity: 0;
		    width: 609px;
		    height: 420px;
		    display: block;
		    position: absolute;
		
		    transform: scale(0);
		
		    transition: all .7s ease-in-out;
		}
		
		.slide img {
		    width: 100%;
		    height: 100%;
		}
		
		.nav label {
		    width: 200px;
		    height: 100%;
		    display: none;
		    position: absolute;
		
			  opacity: 0;
		    z-index: 9;
		    cursor: pointer;
		
		    transition: opacity .2s;
		
		    color: #FFF;
		    font-size: 156pt;
		    text-align: center;
		    line-height: 380px;
		    font-family: "Varela Round", sans-serif;
		    background-color: rgba(255, 255, 255, .3);
		    text-shadow: 0px 0px 15px rgb(119, 119, 119);
		}
		
		.slide:hover + .nav label { opacity: 0.5; }
		
		.nav label:hover { opacity: 1; }
		
		.nav .next { right: 0; }
		
		input:checked + .slide-container  .slide {
		    opacity: 1;
		
		    transform: scale(1);
		
		    transition: opacity 1s ease-in-out;
		}
		
		input:checked + .slide-container .nav label { display: block; }
		
		.nav-dots {
			width: 100%;
			bottom: 9px;
			height: 11px;
			display: block;
			position: absolute;
			text-align: center;
		}
		
		.nav-dots .nav-dot {
			top: -5px;
			width: 11px;
			height: 11px;
			margin: 0 4px;
			position: relative;
			border-radius: 100%;
			display: inline-block;
			background-color: rgba(0, 0, 0, 0.6);
		}
		
		.nav-dots .nav-dot:hover {
			cursor: pointer;
			background-color: rgba(0, 0, 0, 0.8);
		}
		
		input#img-1:checked ~ .nav-dots label#img-dot-1,
		input#img-2:checked ~ .nav-dots label#img-dot-2,
		input#img-3:checked ~ .nav-dots label#img-dot-3,
		input#img-4:checked ~ .nav-dots label#img-dot-4,
		input#img-5:checked ~ .nav-dots label#img-dot-5,
		input#img-6:checked ~ .nav-dots label#img-dot-6 {
			background: rgba(0, 0, 0, 0.8);
		}
    </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
   
	</head> 
	<title>검수 이미지</title>
	<body class="no-skin">
      	<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
									Home
							</li>
							<li>
								반품
							</li>
							<li class="active">검수</li>
						</ul><!-- /.breadcrumb -->
					</div>
	               
					<div class="page-content">
						<div id="inner-content-side">
							<div style="text-align:center; margin-bottom:20px;">
								<h2>그룹 재고 번호 :: ${groupIdx}</h2>
							</div>
							<ul class="slides">
						    	<c:set var="listSize" value="${fn:length(fileList)}"/>
							    <c:forEach items="${fileList}" var="fileList" varStatus="fileStatus">
							    	<c:set var="index" value="${fileStatus.index+1}"/>
							    	<c:choose>
							    		<c:when test="${index eq '1'}">
							    			<input type="radio" name="radio-btn" id="img-${index}" checked/>
									    	<li class="slide-container">
									    		<div class="slide">
									    			<img src="http://${fileList}"/>
									    		</div>
									    		<div class="nav">
									    			<label for="img-${listSize}" class="prev">&#x2039;</label>
									    			<label for="img-${index+1}" class="next">&#x203a;</label>
									    		</div>
									    	</li>
							    		</c:when>
							    		<c:when test="${index eq listSize}">
							    			<input type="radio" name="radio-btn" id="img-${index}"/>
									    	<li class="slide-container">
									    		<div class="slide">
									    			<img src="http://${fileList}"/>
									    		</div>
									    		<div class="nav">
									    			<label for="img-${index-1}" class="prev">&#x2039;</label>
									    			<label for="img-1" class="next">&#x203a;</label>
									    		</div>
									    	</li>
							    		</c:when>
							    		<c:otherwise>
							    			<input type="radio" name="radio-btn" id="img-${index}"/>
									    	<li class="slide-container">
									    		<div class="slide">
									    			<img src="http://${fileList}"/>
									    		</div>
									    		<div class="nav">
									    			<label for="img-${index-1}" class="prev">&#x2039;</label>
									    			<label for="img-${index+1}" class="next">&#x203a;</label>
									    		</div>
									    	</li>
							    		</c:otherwise>
							    	</c:choose>
							    </c:forEach>
							    <li class="nav-dots">
							    	<c:forEach items="${fileList}" var="fileList" varStatus="status">
							    		<c:set var="index" value="${status.index+1}"/>
							    		<label for="img-${index}" class="nav-dot" id="img-dot-${index}"></label>
							    	</c:forEach>
							    </li>
							</ul>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
         
      <!-- Main container End-->
      
      <!-- Footer Start -->
      <%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
      <!-- Footer End -->
      
      <!--[if !IE]> -->
      <script src="/assets/js/jquery-2.1.4.min.js"></script>
      <!-- <![endif]-->
      <!--[if IE]>
      <script src="/assets/js/jquery-1.11.3.min.js"></script>
      <![endif]-->
      <script type="text/javascript">
         if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
      </script>
      <script src="/assets/js/bootstrap.min.js"></script>
      
      <!-- script on paging start -->
      <script src="/assets/js/bootstrap-datepicker.min.js"></script>
      <script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>
		
      <script src="/assets/js/jquery.dataTables.min.js"></script>
      <script src="/assets/js/jquery.dataTables.bootstrap.min.js"></script>
      <script src="/assets/js/fixedHeader/dataTables.fixedHeader.min.js"></script>
      <script src="/assets/js/fixedHeader/dataTables.fixedHeader.js"></script>
      <script src="/assets/js/dataTables.buttons.min.js"></script>
      <script src="/assets/js/buttons.flash.min.js"></script>
      <script src="/assets/js/buttons.html5.min.js"></script>
      <script src="/assets/js/buttons.print.min.js"></script>
      <script src="/assets/js/buttons.colVis.min.js"></script>
      <script src="/assets/js/dataTables.select.min.js"></script>
      <script src="/assets/js/ace-elements.min.js"></script>
      <script src="/assets/js/ace.min.js"></script>
      <!-- script on paging end -->
      
      <!-- script addon start -->
      <script type="text/javascript">
			function currentDiv(n) {
			  showDivs(slideIndex = n);
			}

			function showDivs(n) {
			  var i;
			  var x = document.getElementsByClassName("mySlides");
			  var dots = document.getElementsByClassName("demo");
			  if (n > x.length) {slideIndex = 1}
			  if (n < 1) {slideIndex = x.length}
			  for (i = 0; i < x.length; i++) {
			    x[i].style.display = "none";
			  }
			  for (i = 0; i < dots.length; i++) {
			    dots[i].className = dots[i].className.replace(" w3-opacity-off", "");
			  }
			  x[slideIndex-1].style.display = "block";
			  dots[slideIndex-1].className += " w3-opacity-off";
			}
	</script>
   </body>
</html>