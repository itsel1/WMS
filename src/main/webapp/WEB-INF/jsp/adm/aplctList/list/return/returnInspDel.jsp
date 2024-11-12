<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<style>

	</style>
   
	<head>
	<link rel="stylesheet" href="/assets/css/bootstrap-datepicker3.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
     .colorBlack {color:#000000 !important;}
    </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
   
	</head> 
	<title>반품 리스트</title>
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
							<li class="active">반품 검수 리스트</li>
						</ul><!-- /.breadcrumb -->
					</div>
	               
					<div class="page-content">
						<div id="inner-content-side" >
							<form name="returnForm" id="returnForm">
								<input type="hidden" name="orderReference" id="orderReference" value="${returnInspOne.ORDER_REFERENCE}">
								<input type="hidden" name="nno" id="nno" value="${returnInspOne.NNO}">
								<input type="hidden" name="userId" id="userId" value="${returnInspOne.SELLER_ID}"/>
		                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		                    	<div id="table-contents" class="table-contents"">
									<table id="dynamic-table" class="table table-bordered table-hover containerTable" style="vertical-align: middle;">
										<colgroup>
											<col width="12%"/> 
											<col width="15%"/>
											<col width="11%"/>
											<col width="15%"/>
											<col width="21%"/>
											<col width="26%"/>
										</colgroup>
										<tr>
			                    	 		<td>
			                    	 			KOBL NO : ${returnInspOne.KOBL_NO} 
			                    	 		</td>
			                    	 		<td>
			                    	 			ORDER_NO : ${returnInspOne.ORDER_NO }
			                    	 		</td>
			                    	 		<td>주문일자 : ${returnInspOne.ORDER_DATE}</td>
			                    	 		<td>
			                    	 			RETURN TRK COM : ${returnInspOne.RE_TRK_COM}
		                    	 			</td>
			                    	 		<td>
			                    	 			RETURN TRK NO  : ${returnInspOne.RE_TRK_NO}
			                    	 		</td>
			                    	 		<td>
			                    	 			USER ID  : ${returnInspOne.SELLER_ID}
			                    	 		</td>
		                    	 		</tr>
			                    	 	<tr>
			                    	 		<td colspan="6" style="text-align: center;">
			                    	 			상품 정보
			                    	 		</td>
			                    	 	</tr>
			                    	 	<tr>
			                    	 		<td colspan="6" style="vertical-align: middle;">
			                    	 			<div class="row">
			                    	 				<div class="col-xs-12">
					                    	 			<table class="table table-bordered table-hover containerTable">
					                    	 				<colgroup>
																<col width="25%"/>
															</colgroup>
					                    	 				<c:forEach items="${returnInspOne.orderItemList}" var="inspItemList" varStatus="itemStatus">
						                    	 				<tr>
						                    	 					<td style="text-align: center;">
						                    	 						<span class="profile-picture">
						                    	 							<img class="editable img-responsive" src="${inspItemList.ITEM_IMG_URL }" loading="lazy">
					                    	 							</span>
						                    	 					</td>
						                    	 					<td>
						                    	 						<div class="profile-user-info">
						                    	 							<div class="profile-info-row">
						                    	 								<div class="profile-info-name">상품명 #${itemStatus.count }</div>
						                    	 								<div class="profile-info-value bigger-140">
						                    	 									${inspItemList.ITEM_DETAIL }
						                    	 								</div>
						                    	 							</div>
						                    	 							<div class="profile-info-row">
						                    	 								<div class="profile-info-name">Brand</div>
						                    	 								<div class="profile-info-value">
						                    	 									${inspItemList.BRAND }
						                    	 								</div>
						                    	 							</div>
					                    	 							</div>
					                    	 							<div class="profile-user-info ">
						                    	 							<div class="profile-info-row ">
						                    	 								<div class="profile-info-name">상품 수량</div>
						                    	 								<div class="profile-info-value">
					                    	 										<label id="housCntActive" class="bigger-180 real-red">${inspItemList.ITEM_CNT }</label>
					                    	 										<input type="hidden" name="whTotalItemCnt" value="${inspItemList.ITEM_CNT}"/>
						                    	 								</div>
						                    	 							</div>
					                    	 							</div>
					                    	 							<div class="profile-user-info ">
						                    	 							<div class="profile-info-row">
						                    	 								<div class="profile-info-name">제조국</div>
						                    	 								<div class="profile-info-value">
						                    	 									${inspItemList.MAKE_CNTRY }
						                    	 								</div>
						                    	 							</div>
						                    	 							<div class="profile-info-row">
						                    	 								<div class="profile-info-name">제조사</div>
						                    	 								<div class="profile-info-value">
						                    	 									${inspItemList.MAKE_COM }
						                    	 								</div>
						                    	 							</div>
						                    	 						</div>
																	</td>
																</tr>
															</c:forEach>
															<tr>
																<th class="center">Date</th>
																<td style="text-align: left;padding:0px;"><input id="trkDate" id="trkDate" readonly type="text" name="trkDate" value="" style="width:100%"></td>
															</tr>
															<tr>
																<th class="center">폐기비용</th>
																<td style="text-align: left;padding:0px;"><input type="number" id="chgAmt" name="chgAmt" value="" style="width:100%"></td>
															</tr>
														</table>
													</div>
													
			                    	 				<div class="row">
														<div class="col-xs-12 col-sm-3 col-sm-offset-3">
															<button type="button" class="btn btn-lg" id="backBtn" name="backBtn">닫기</button>
														</div>
														<div class="col-xs-12 col-sm-3">
															<button type="button" class="btn btn-lg btn-success" id="saveBtn" name="saveBtn">저장하기</button>
														</div>
													</div>
												</div>
											</td>
										</tr>
		                    	 	</table>
		                    	 </div>
							</form>
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
			jQuery(function($) {
            	$(document).ready(function() {

            	});
            	
   				$("#trkDate" ).datepicker({
   					autoclose:true,
   					todayHighlight:true,
   					format:'yyyymmdd'
   				});

            	function LoadingWithMask() {
               		var maskHeight = $(document).height();
               		var maskWidth = window.document.body.clientWidth;
               		var mask ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
               		//화면에 레이어 추가
                	$('body').append(mask)
	                //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
    	            $('#mask').css({
						'width' : maskWidth,
						'height': maskHeight,
						'opacity' :'0.3'
                	});
	                //마스크 표시
					$('#mask').show();  
        	        //로딩중 이미지 표시
				}
         	});
			$("#backBtn").on("click",function(e){
				close();
			});

			$("#saveBtn").on("click",function(e){
	        	 if($("#trkDate").val()==""){
					alert('일자 를 입력해주세요');
					$("#trkDate").focus();
					return false;
				}
	        	 if($("#chgAmt").val()==""){
					alert('금액을 입력해주세요');
					$("#chgAmt").focus();
					return false;
				}

		        var formData = $('#returnForm').serialize();
				$.ajax({
					url: "/mngr/aplctList/return/inspDel/"+$("#orderReference").val(),
					type: 'POST',
					data: formData,
					beforeSend : function(xhr) {
						xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");		
					},
					success : function(data) {
						if (data == "S") {
							alert("저장되었습니다");
							close();
						} else {
							alert("변경 중 오류가 발생하였습니다. 관리자에게 문의해 주세요.");
						}
					},
					error : function(xhr, status) {
						alert(xhr + " : " + status);
						alert("변경에 실패 하였습니다. 관리자에게 문의해 주세요.");
					}
						
				});
				
			});

	        $(window).bind("beforeunload", function (e){
				//opener.location.href = '/mngr/aplctList/return/returnFailList';
				opener.location.href = '/mngr/order/return/stockList';
				close();
			});

	       
      </script>
   </body>
</html>