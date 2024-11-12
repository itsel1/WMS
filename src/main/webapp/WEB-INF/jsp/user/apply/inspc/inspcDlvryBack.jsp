<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	 </style>
	<!-- basic scripts -->
	</head> 
	<title>검품배송 신청</title>
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
			<%@ include file="/WEB-INF/jsp/importFile/userSideMenu.jsp" %>
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
								배송대행 신청
							</li>
							<li class="active">검품배송 신청</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								검품배송 신청
							</h1>
						</div>
						<div id="inner-content-side" >
							<div id="search-div">
								<br>
								<form name="search-button" id="search-botton" method="get" action="">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="widget-box">
										<div class="widget-body" >
											<div class="widget-main" >
												<div class="form-group">
													<div class="col-xs-12">
														<table style="width:100%">
															<tr>
																<td style="padding:0px 20px; min-width:150px;">
																	<select id="formSelect" style="height: 34px;width:100%;">
																		<option value="0">선택하세요</option>
																		<option value="1">쿠팡</option>
																		<option value="2">11번가</option>
																		<option value="3">네이버</option>
																		<option value="4">ACI</option>
																		<option value="5">개인 사업자</option>
																	</select>
																</td>
																<td style="padding:0px 30px;">
																	<input type="file" id="" style="display: initial;width:100%"/>
																</td>
															</tr>
														</table>
													</div>
												</div>
												<br/>
											</div>
										</div>
									</div>
								</form>
								<br/>
								<form name="rgstr-button" id="search-botton" method="post" action="">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div id="rgstr-user" style="text-align: right">
										<button type="submit" class="btn btn-sm btn-primary">
										 Upload
										 <!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
										</button>
									</div>
								</form>
							</div>
							<br>
							<div id="tableForm">
							
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
		<!-- <script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script> -->
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
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		
		
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#forthMenu").toggleClass('open');
					$("#forthMenu").toggleClass('active'); 
					$("#4thTwo").toggleClass('active');
				});

				//select target form change
				$('#formSelect').on('change', function(){
					$('#tableForm').children().remove();
					if($(this).val() != 0){
						var list = { selOption: $(this).val() }
						$.ajax({
							type : "POST",
							beforeSend : function(xhr)
							{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							url : "inspcSubpage",
							data : list,
							dataType : "text",
							error : function(){
								alert("subpage ajax Error");
							},
							success : function(data){
								$("#tableForm").html(data);
							}
						})
					}
				});
			
			})
		</script>
		<!-- script addon end -->
	</body>
</html>
