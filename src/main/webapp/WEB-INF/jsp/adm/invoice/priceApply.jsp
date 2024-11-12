<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
.padding0 {
	padding: 0px !important;
}

.colorBlack {
	color: #000000 !important;
}

.table>tbody>tr>td {
	vertical-align: middle !important;
}

.profile-info-name {
	padding: 0px;
	color: black !important;
	text-align: center !important;
}

.profile-user-info-striped {
	border: 0px solid white !important;
}
/* .profile-user-info-striped{border: 0px solid white !important;}
	  .profile-info-name {padding:6px 15px; width:0px; color:black !important; background-color: unset !important;} */
.profile-user-info {
	width: 100% !important;
	margin: 0px;
}

/* .col-xs-12 .col-sm-12 {
	padding: 0px !important;
}
 */
.tdBackColor {
	background-color: #FFFFFF;
}

.tdHeader {
	border: 1px solid black;
	padding: 0px !important;
}

.tdHeaderB {
	border: 1px solid black;
	border-bottom: none;
}

.tdHeaderT {
	border: 1px solid black;
	border-top: none;
}

.tdHeaderL {
	border: 1px solid black;
	border-left: none;
	border-right: none;
}

.tdHeaderR {
	border: 1px solid black;
	border-right: none;
	border-left: none;
}

.tdHeaderTR {
	border: 1px solid black;
	border-top: none;
}

@media ( min-width :768px) {
	.tdHeaderB {
		border: 1px solid black;
		border-bottom: none;
	}
	.tdHeaderT {
		border: 1px solid black;
		border-top: none;
	}
	.tdHeaderL {
		border: 1px solid black;
		border-left: none;
	}
	.tdHeaderR {
		border: 1px solid black;
		border-right: none;
	}
	.tdHeaderTR {
		border: 1px solid black;
		border-right: none;
		border-top: none;
	}
}

@media only screen and (max-width:480px) {
	.hidden-480 {
		display: none !important;
	}
}

@media only screen and (max-width:480px) {
	.hidden-489 {
		display: inline!important;
	}
}
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
		<!-- page specific plugin scripts -->
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>

	<!-- basic scripts End-->
	</head> 
	<title>WMS.ESHOP</title>
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
								Invoice
							</li>
							<li class="active">Price Apply</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
							<div class="page-header">
								<h1>
									Price Apply
								</h1>
							</div>
									<div id="table-contents" class="table-contents" style="overflow: auto;">
									<div class="display" >
										<table   id="dynamic-table" class="table table-bordered" style="width:30%;float:left;">
											<thead>
												<tr>
													<th  class="center colorBlack" colspan=10>미청구 List</th>
												</tr>
												<tr>
													<th class="center colorBlack" style="width:160px;">
														USER_ID
													</th>
													<th class="center colorBlack" style="width:160px;">
														Inv Month
													</th>
													<th class="center colorBlack">
														Bl CNT
													</th>
													<th class="center colorBlack">
														ETC_ORDER CNT
													</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${unPricelist}" var="unPricelist" varStatus="status">
												<tr>
													<td class="colorBlack" style="text-align:center;width:100px;">
													   ${unPricelist.USER_ID}
													</td>
													<td class="colorBlack" style="text-align:center;width:100px;">
													   ${unPricelist.INV_MONTH}
													</td>
													<td class="colorBlack" style="text-align:center;width:100px;">
													   ${unPricelist.BL_CNT}
													</td>
													<td class="colorBlack" style="text-align:center;width:150px;">
													   ${unPricelist.ETC_CNT}
													</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
										</div>
										<div class="display" style="margin-left:30px; width:60%;float:left;padding:0px;">
											<form action="" method="get">
											<table   id="dynamic-table" class="table table-bordered" style="width:80%;margin:0px;">
												<thead>														
													<tr>
													    <th  class="center colorBlack" style="padding:0px;">기간</th>
														<th style="padding:0px;" colspan=3>
															<input type="text" id="fromDate" name="fromDate" readonly value="${parameterInfo.fromDate}"> ~ 
															<input id="toDate" name="toDate" type="text" readonly value="${parameterInfo.toDate}">
														</th>
														<th  style="padding:0px;" class="center colorBlack" >User Id</th>
														<th style="padding:0px;">
															<input type="text" name="userId" value="${parameterInfo.userId}"/>
														</th>
														<th style="padding:0px;">
															<input type="submit" value="조회">
														</th>	
														</tr>
												</thead>
											</table>
											</form>	
											<table id="dynamic-table" class="table table-bordered" >
												<thead>
													<tr>
														<th class="center colorBlack" style="width:160px;">
															USER_ID
														</th>
														<th class="center colorBlack" style="width:180px;">
															기 간
														</th>
														<th class="center colorBlack" style="width:80px;">
															Bl Cnt
														</th>
														<th class="center colorBlack" style="width:80px;">
															기타출고
														</th>
														<th class="center colorBlack" style="width:80px;">
													       미청구 Bl
														</th>
														<th class="center colorBlack">
														   
														</th>
														<th class="center colorBlack">
														   
														</th>
													</tr>
											</thead>
											<tbody>
												<c:forEach items="${priceList}" var="priceList" varStatus="status">
													<tr>
														<td class="colorBlack" style="text-align:center;">
														   ${priceList.USER_ID}
														</td>
														<td class="colorBlack" style="text-align:center;">
														   ${priceList.FROM_DATE} ~ ${priceList.TO_DATE}
														</td>
														<td class="colorBlack" style="text-align:right;">
														   ${priceList.PRICE_BL} 
														</td>
														<td class="colorBlack" style="text-align:right;">
															${priceList.ETC_BL}
														</td>
														<td class="colorBlack" style="text-align:right;">
														   ${priceList.UNPRICE_BL_CNT} 
														</td>
														<td style="text-align:center;padding:0px;width:120px;">
															<input type="button" style="width:80%" id="btnSend" value="Price Apply" onclick="fn_priceApply('${priceList.ORG_STATION}','${priceList.USER_ID}','${priceList.FROM_DATE}','${priceList.TO_DATE}')">
														</td>
														<td class="colorBlack" style="text-align:left;font-color:red;font-weight:bold">
															<c:if test="${priceList.PRICE_YN eq 'N'}">
    															등록된 단가가 존재 하지 않습니다.
															</c:if>
														</td>
													</tr>
												</c:forEach>
											</tbody>	
											<tfoot  style="border:0px solid;">
											<tr>
												<td colspan=8 style="border:0px solid;">
													<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>	
												</td>
											</tr>
											</tfoot>
										</table>	
									
									</div>				
								</div>
							</div>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
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
		<script src="/assets/js/fixedHeader/dataTables.fixedHeader.min.js"></script>
		<script src="/assets/js/fixedHeader/dataTables.fixedHeader.js"></script>
		<script src="/assets/js/dataTables.buttons.min.js"></script>
		<script src="/assets/js/buttons.flash.min.js"></script>
		<script src="/assets/js/buttons.html5.min.js"></script>
		<script src="/assets/js/buttons.print.min.js"></script>
		<script src="/assets/js/buttons.colVis.min.js"></script>
		<script src="/assets/js/dataTables.select.min.js"></script>
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
						
					$("#invoicethMenu").toggleClass('open');
					$("#invoice1").toggleClass('active');


					$(".btnPriceApply").on('change',function(e){
						$("#cond_form").submit();
					})
				})
			})

			function fn_priceApply(orgStation,userId,fromDate,toDate){
				
				var formData = new Object();
				formData.orgStation = orgStation;
				formData.userId = userId;
				formData.fromDate = fromDate;
				formData.toDate = toDate;
				//formData = JSON.stringify(formData);
 				LoadingWithMask();
				$.ajax({
					url:'/mngr/invoice/invPriceApply',
					type: 'GET',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: formData, 
					success : function(data) {
						var Status = data.rstStatus;
						$('#mask').hide();
						if(Status == "SUCCESS"){
							alert(data.rstMsg);
							window.location.reload();
						};
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다.");
		                $('#mask').hide();
		            }
				})
	
			}

			function LoadingWithMask() {
			    //화면의 높이와 너비를 구합니다.
			    var maskHeight = $(document).height();
			    var maskWidth  = window.document.body.clientWidth;
			     
			    //화면에 출력할 마스크를 설정해줍니다.
			    var mask       ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
			  
			    //화면에 레이어 추가
			    $('body')
			        .append(mask)
			        
			    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
			    $('#mask').css({
			            'width' : maskWidth
			            ,'height': maskHeight
			            ,'opacity' :'0.3'
			    });
			  
			    //마스크 표시
			    $('#mask').show();  
			    //로딩중 이미지 표시
			}
		</script>
		<!-- script addon end -->
		
		<link rel="stylesheet" href="https://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css"/> 	
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>

		<script>
			var j$ = jQuery;
			j$( function() {
				j$( "#fromDate" ).datepicker({
					 //maxDate: "+0D",
					 showButtonPanel: true,
					 currentText: 'To Day',
					 dateFormat: "yymmdd",
					 changeMonth: true,
					 monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
					 monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
				});
				j$( "#toDate" ).datepicker({
					 //maxDate: "+0D",
					 showButtonPanel: true,
					 //currentText: 'To Day',
					 dateFormat: "yymmdd",
					 changeMonth: true,
					 monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
					 monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
				});
			} );
		</script>
		

	</body>
</html>
