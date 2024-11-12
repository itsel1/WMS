<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
		.page-content {
			width: 100%;
			padding: 10px;
		}
		
		.dropbtn {
			background-color: #C18282;
			color: white;
			padding: 16px;
			font-size: 16px;
			border: none;
			cursor: pointer;
			width: 100%;
		}
		
		.dropbtn:hover, .dropbtn:focus {
			background-color: #853C3C;
		}
		
		#myInput {
			/* display: none; */
			background-image: url('/image/search-icon.png');
			background-position: 14px 16px;
			background-repeat: no-repeat;
			width: 100%;
			box-sizing: border-box;
			font-size: 16px;
			padding: 14px 20px 12px 42px;
			border: 1px solid #ddd;
			//border-bottom: 1px solid #ddd;
		}
		
		#myInput:focus {
			outline: 2px solid #ddd;
		}
		
		.dropdown {
			position: relative;
			display: inline-block;
			width: 100%;
		}
		
		.dropdown-content {
			/* display: none; */
			margin-top: 20px;
			position: absolute;
			background-color: #f6f6f6;
			width: 100%;
			border: 1px solid #ddd;
			z-index: 1;
		}
		
		.dropdown-content a {
			color: black;
			padding: 12px 16px;
			text-decoration: none;
			display: block;
			cursor: pointer;
		}
		
		.dropdown-content a:hover {
			background-color: #ebebeb;
		}
		
		.show {
			display: block;
		}
	</style>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/chosen.min.css" />
	</head> 
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
								HS Code
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="dropdown">
							<!-- <button onclick="myFunction()" class="dropbtn">Search Code</button> -->
							<input type="text" placeholder="[HS Code] Category | Product Name | Material" id="myInput" onkeyup="filterFunction()">
							<div id="myDropdown" class="dropdown-content">
								<!-- <input type="text" placeholder="[HS Code] Category | Product Name | Material" id="myInput" onkeyup="filterFunction()"> -->
								<c:forEach items="${codeList}" var="codeList">
									<a onclick="fn_regHsCode('${codeList.idx}', '${codeList.hsCode}', '${codeList.category}', '${codeList.itemDetail}', '${codeList.material}', '${cnt}')">[${codeList.hsCode}] ${codeList.category} | ${codeList.itemDetail} | ${codeList.material}</a>
								</c:forEach>
							</div>					
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
		
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/assets/js/bootstrap.min.js"></script>
		
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <script src="/testView/js/jquery-3.3.1.min.js"></script> -->
		<script src="/testView/js/jquery-ui.js"></script>
		<script src="/testView/js/popper.min.js"></script>
		<!-- <script src="/testView/js/bootstrap.min.js"></script> -->
		<script src="/testView/js/owl.carousel.min.js"></script>
		<script src="/testView/js/jquery.magnific-popup.min.js"></script>
		<script src="/testView/js/aos.js"></script>
		
		<script src="/testView/js/main.js"></script>
		
		<!-- script on paging start -->
		
		<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="/assets/js/jquery-ui.min.js"></script>
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
		
		<script src="/assets/js/bootstrap-tag.min.js"></script>
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/chosen.jquery.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			function myFunction() {
				document.getElementById("myInput").classList.toggle("show");
				document.getElementById("myDropdown").classList.toggle("show");
			}

			function filterFunction() {
				var input, filter, ul, li, a, i;
				input = document.getElementById("myInput");
				filter = input.value.toUpperCase();
				div = document.getElementById("myDropdown");
				a = div.getElementsByTagName("a");
				for (i = 0; i < a.length; i++) {
					txtValue = a[i].textContent || a[i].innerText;
				    if (txtValue.toUpperCase().indexOf(filter) > -1) {
				    	a[i].style.display = "";
				    } else {
				    	a[i].style.display = "none";
				    }
				}
			}

		</script>
	</body>
</html>
