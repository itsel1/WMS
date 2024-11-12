<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="shortcut icon" type="image/x-icon" href="/image/favicon/logo_icon.ico" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	 #main-content {
		width: 100%;
		box-sizing: border-box;
	}
	
	#search-content {
		width: 100%;
		box-sizing: border-box;
		display: flex;
		flex-direction: row;
		flex-wrap: nowrap;
		justify-content: flex-start;
		align-content: center;
		align-items: stretch;
	}
	
	#search-content span {
		display: flex;
	    align-items: center;
	    padding-left: 10px;
	    padding-right: 10px;
	    background-color: #e6e6e6;
	    font-weight: 600;
	    border: :1px solid #e6e6e6;
	}
	
	#search-button {
		padding: 5px 10px;
		font-weight: 600;
		background: #666;
		color: #fff;
		border: 1px solid #282828;
	} 
	
	#data-table {
		width: 100%;
		border-collapse: collapse;
		border: 1px solid #e6e6e6;
	}
	
	#data-table td {
		padding: 10px;
		border-collapse: collapse;
		border: 1px solid #e6e6e6;
	}
	
	#data-table th {
		text-align: center;
		background: #666;
		border: 1px solid #e6e6e6;
		border-collapse: collapse;
		color: #fff;
		z-index: 1;
		padding: 10px 15px;
	}
	
	#wrapper {
		border: 1px solid #e6e6e6;
		width: 100%;
		max-height: 500px;
		overflow-y: auto;
		margin-top: 10px;
	}
	
	.sticky-header {
		position: sticky;
		top: 0px;
		height: 30px;
		border: 1px solid #e6e6e6;
	}

	#wrapper::-webkit-scrollbar {
		width: 10px;
	}
	
	#wrapper::-webkit-scrollbar-thumb {
		background-color: #666;
		border-radius: 10px;
		background-clip: padding-box;
		border: 2px solid transparent;
	}
	
	#wrapper::-webkit-scrollbar-track {
		background-color: GhostWhite;
		border-radius: 10px;
		box-shadow: inset 0px 0px 5px white;
	}
	
	#regist-button {
		padding: 5px 10px;
		font-weight: 600;
		background: #44a032;
		color: #fff;
		border: 1px solid #44a032;
	} 
	
	#update-button {
		padding: 0 10px;
		font-weight: 600;
		background: #44a032;
		color: #fff;
		border: 1px solid #44a032;
		border-radius: 5px;
	}
	
	#update-button:hover {
		padding: 1px 10px;
		margin: -1px 0;
	}
	
	 </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	
	</head> 
	<title>회원 관리</title>
	<body class="no-skin">
<!-- headMenu Start -->
		<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			<!-- SideMenu Start -->
			<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp" %>
			<!-- SideMenu End -->
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
							<li>
								회원 관리
							</li>
							<li class="active">수출화주사업자 정보</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								수출화주사업자 등록
							</h1>
						</div>
						<form name="form" id="form" action="/mngr/acnt/userBizList" method="GET">
							<div id="main-content">
								<div id="search-content">
									<span>USER ID</span>
									<input type="text" name="searchUserId" id="searchUserId" value="${parameters.userId}">
									<span>상호명</span>
									<input type="text" name="searchComName" id="searchComName" value="${parameters.comName}">
									<button type="button" id="search-button">search</button>
								</div>
								<br/>
								<button type="button" id="regist-button">등록</button>
								<div id="table-content" style="width:100%;">
									<div id="wrapper">
										<table id="data-table">
											<thead>
												<tr>
													<th class="sticky-header">NO</th>
													<th class="sticky-header">USER ID</th>
													<th class="sticky-header">법인명</th>
													<th class="sticky-header">대표자명</th>
													<th class="sticky-header">사업자 주소</th>
													<th class="sticky-header">사업자 우편번호</th>
													<th class="sticky-header">사업자 번호</th>
													<th class="sticky-header">사업자 통관부호</th>
													<th class="sticky-header"></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${bizList}" var="list" varStatus="status">
													<tr>
														<td style="text-align:center;">${status.count}</td>
														<td style="text-align:center;">${list.userId}</td>
														<td style="text-align:center;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" title="${list.regName}">${list.regName}</td>
														<td style="text-align:center;">${list.regRprsn}</td>
														<td style="text-align:center;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" title="${list.regAddr}">${list.regAddr}</td>
														<td style="text-align:center;">${list.regZip}</td>
														<td style="text-align:center;">${list.regNo}</td>
														<td style="text-align:center;">${list.bizCustomsNo}</td>
														<td style="text-align:center;">
															<button type="button" id="update-button" onclick="openPopUp('${list.userId}')">수정</button>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
								<br/>
								<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
							</div>
						</form>
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
			jQuery(function($) {
				
				$(document).ready(function() {
					$("#zeroMenu").toggleClass('open');
					$("#zeroMenu").toggleClass('active');
					$("#14thThird").toggleClass('active');
				});

				$("#search-button").on('click', function() {
					$("#form").submit();
				});

				$("#regist-button").on('click', function() {
					window.open('/mngr/acnt/registBizInfo', 'bizPopup', 'width=550,height=650');
				});

			});

			function openPopUp(userId) {
				window.open('/mngr/acnt/registBizInfo?userId='+userId, 'bizPopup', 'width=550,height=650');
			}

		</script>
		<!-- script addon end -->
</body>
</html>