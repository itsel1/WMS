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
     .colorBlack {color:#000000 !important;}
    </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
   
	</head> 
	<title>위약 반송 정보</title>
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
							<li class="active">위약 반송</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div id="inner-content-side" >
							<div class="row" style="margin-top:10px;">
								<div class="col-xs-12 col-sm-12">
									<table id="normal_info" style="width:100%;" class="table table-bordered table-hover containerTable">
										<thead>
											<tr>
												<th class="center" style="width:20%;">원송장번호</th>
												<td class="center" style="width:30%;background-color:#fff;">${orderInfo.koblNo}</td>
												<th class="center" style="width:20%;">주문번호</th>
												<td class="center" style="width:30%;background-color:#fff;">${orderInfo.orderNo}</td>
											</tr>
											<tr>
												<th class="center" style="width:20%;">셀러 ID</th>
												<td class="center" style="width:30%;background-color:#fff;">${orderInfo.sellerId}</td>
												<th class="center" style="width:20%;">도착지</th>
												<td class="center" style="width:30%;background-color:#fff;">${orderInfo.dstnNation}</td>
											</tr>
										</thead>
									</table>
									<table id="file_table" style="width:100%;" class="table table-bordered table-hover containerTable">
										<thead>
											<tr>
												<th class="center" style="width:30%;">반송사유서</th>
												<td class="center" style="width:70%;background-color:#fff;">
													<a href="${orderInfo.fileReason}" download>파일 다운로드</a>
												</td>
											</tr>
											<tr>
												<th class="center" style="width:30%;">통장사본</th>
												<td class="center" style="width:70%;background-color:#fff;">
													<a href="${orderInfo.fileCopyBank}" download>파일 다운로드</a>
												</td>
											</tr>
											<tr>
												<th class="center" style="width:30%;">반품신청 캡쳐본</th>
												<td class="center" style="width:70%;background-color:#fff;">
													<a href="${orderInfo.fileCapture}" download>파일 다운로드</a>
												</td>
											</tr>
											<tr>
												<th class="center" style="width:30%;">반품메신저 캡쳐본</th>
												<td class="center" style="width:70%;background-color:#fff;">
													<a href="${orderInfo.fileMessenger}" download>파일 다운로드</a>
												</td>
											</tr>
											<tr>
												<th class="center" style="width:30%;">반품 커머셜</th>
												<td class="center" style="width:70%;background-color:#fff;">
													<a href="${orderInfo.fileCl}" download>파일 다운로드</a>
												</td>
											</tr>
											<tr>
												<th class="center" style="width:30%;">수출신고 필증</th>
												<td class="center" style="width:70%;background-color:#fff;">
													<a href="${orderInfo.fileIc}" download>파일 다운로드</a>
												</td>
											</tr>
										</thead>
									</table>
								</div>
							</div>
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
			
      	</script>
   </body>
</html>