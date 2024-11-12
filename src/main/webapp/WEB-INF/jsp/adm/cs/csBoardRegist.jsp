<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	 </style>
	<!-- basic scripts -->

		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script src="assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/assets/js/bootstrap.min.js"></script>
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.4/codemirror.min.css"/>
		<!-- Editor's Style -->
		<link rel="stylesheet" href="https://uicdn.toast.com/editor/2.0.0/toastui-editor.min.css" />
	<!-- basic scripts End-->
	</head> 
	<title>공지사항</title>
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
								고객센터
							</li>
							<li class="active">공지사항</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								cs 리스트
							</h1>
						</div>
						<div id="inner-content-side">
							
							<h2>
								cs 등록
							</h2>
							<div id="inner-content-side" >
								<div class="page-header">
									<div class="row">
										<div class="col-xs-6 col-md-5">
											제목 : <input type="text" style="width:90%" id="title" name="title"/>
										</div>
										<div class="col-xs-6 col-md-4">
											<div class="input-group input-group-sm">
												<span class="input-group-addon">
													등록 일자: 
													<i class="ace-icon fa fa-calendar"></i>
												</span>
												<input type="text" name="datepicker" id="datepicker" class="form-control" />
											</div>
										</div>
										<div id="table-contents" class="table-contents" style="width:100%; overflow:auto; font-size:medium;">
											HAWB NO :
											<input type="text" name="hawbNo" id="hawbNo" value=""/> 
										</div>
								</div>
								<div id="table-contents" class="table-contents" style="width:100%; overflow: auto; font-size:medium;">
									내용:
									<input type="text" name="contents" id="contents" value=""/>
									<div id='editor' name='editor'>
								
									</div>
									<input type="hidden" id="testssss" value='${testssss }'/>
								</div>
							</div>
							<div class="hr hr-8 solid"></div>
							<div id="btnDiv" class="pull-right">
								<button class="btn btn-white btn-md btn-danger" id="backList">취소</button>
								<button class="btn btn-white btn-md btn-primary" id="registCs">등록</button>
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
		
		<!-- script on paging start -->
		
		<script src="/assets/js/jquery.dataTables.min.js"></script>
		<script src="/assets/js/jquery.dataTables.bootstrap.min.js"></script>
		<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="/assets/js/jquery-ui.min.js"></script>
		<script src="/assets/js/fixedHeader/dataTables.fixedHeader.min.js"></script>
		<script src="/assets/js/fixedHeader/dataTables.fixedHeader.js"></script>
		<script src="/assets/js/dataTables.buttons.min.js"></script>
		<script src="/assets/js/buttons.flash.min.js"></script>
		<script src="/assets/js/buttons.html5.min.js"></script>
		<script src="/assets/js/buttons.print.min.js"></script>
		<script src="/assets/js/buttons.colVis.min.js"></script>
		<script src="/assets/js/dataTables.select.min.js"></script>
		
		<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
		<!-- script addon start -->
		<script type="text/javascript">


		$("#registCs").on("click",function(){
			$.ajax({
				url:'/mngr/cs/csRegistOn',
				type: 'POST',
				data: {
					title : $("#title").val(),
					contents : $("#contents").val(),
					hawbNo : $("#hawbNo").val()
					
				} ,
				beforeSend : function(xhr)
				{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {
					alert("등록 성공");
	            }, 		    
	            error : function(xhr, status) {
	                alert(xhr + " : " + status);
	                alert("수정123123에 실패 하였습니다. 수정사항을 확인해 주세요.");
	            }
			}) 
		})

		$("#datepicker").datepicker({
			showOtherMonths: true,
			selectOtherMonths: false,
			dateFormat:'yymmdd' , 
			autoclose:true, 
			todayHighlight:true
		});
			function fnTrClick(idx){
				location.href="/mngr/board/ntcDetail?ntcNo="+idx;
			}
			$('#backList').on('click',function(e){
				location.href="/mngr/cs/csBoard";
			})
			$('#registCs').on('click', function(e){
				location.href="/mngr/cs/csBoard";
				})
				
			jQuery(function($) {
				//
				$(document).ready(function() {
					$("#11thMenu").toggleClass('open');
					$("#11thMenu").toggleClass('active'); 
					$("#11thTwo").toggleClass('active');
				});
			})
		</script>
		<!-- script addon end -->
	</body>
	</body>
</html>
