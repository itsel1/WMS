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
	<title>사입</title>
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
								사입
							</li>
							<li class="active">상품 코드 관리</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
							<div class="page-header">
								<h1>
									상품 코드 관리
								</h1>
							</div>
							<form name="uploadAramexExcel" id="uploadAramexExcel" enctype="multipart/form-data">
								<table class="table table-bordered" style="width:100%" >
									<thead>
									<tr>
										<th class="center colorBlack">UserID</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" name="userId" type="text" value="${parameterInfo.userId}">
										</td>
										<th class="center colorBlack" style="width:100px;">Takein Code</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" type="text" name="takeinCode" value="${parameterInfo.takeinCode}" >
										</td>
										<th class="center colorBlack" style="width:150px;">Customer Code</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" type="text" name="cusItemCode" value="${parameterInfo.cusItemCode}">
										</td>
										<th class="center colorBlack" style="width:150px;">상품명</th>
										<td style="padding:0px;width:180px;">
											<input style="width:100%" type="text" name="itemDetail" value="${parameterInfo.itemDetail}">
										</td>
										<th class="center colorBlack" style="width:130px;">사용여부</th>
										<td style="padding:0px;">
											<select name='useYn'>
												<option <c:if test="${parameterInfo.useYn == ''}"> selected</c:if> value=''>
													::전체::
												</option>
												<option <c:if test="${parameterInfo.useYn == 'Y'}"> selected</c:if> value='Y'>
													사용
												</option>
												<option <c:if test="${parameterInfo.useYn == 'N'}"> selected</c:if> value='N'>
												   	미사용
												</option>
											</select>
										</td>
										<th class="center colorBlack" style="width:130px;">승인여부</th>
										<td style="padding:0px;">
											<select name='appvYn'>
												<option <c:if test="${parameterInfo.appvYn == ''}"> selected</c:if> value=''>
													::전체::
												</option>
												<option <c:if test="${parameterInfo.appvYn == 'Y'}"> selected</c:if> value='Y'>
													승인
												</option>
												<option <c:if test="${parameterInfo.appvYn == 'N'}"> selected</c:if> value='N'>
												   	미승인
												</option>
											</select>
											<input type="submit" value="조회">
										</td>
									</tr>
									</thead>
								</table>
							</form>
								<form name="uploadAramexExcel" id="uploadAramexExcel" enctype="multipart/form-data">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="col-xs-12 col-md-12" style="text-align: left;">
									</div>
									<div class="col-xs-12 col-md-12" style="text-align: right;">
										<button type="button" class="btn btn-white btn-inverse btn-xs" onclick="fn_takeinInfo('')">등록하기</button>
										<!-- <button type="button" class="btn btn-white btn-inverse btn-xs" id="btn_Delete" name="">삭제</button> -->
									</div>
								</form>
								<br>
							
							<br>
								<div id="table-contents" class="table-contents" style="overflow: auto;">
								<div class="display" >
									<table   id="dynamic-table" class="table table-bordered">
										<thead>
											<tr>
												<th class="center colorBlack">
													TAKEIN_CODE
												</th>
												<th class="center colorBlack">
													USER_ID
												</th>
												<th class="center colorBlack">
													CUSITEM_CODE
												</th>
												<th class="center colorBlack">
													HS_CODE
												</th>
												<th class="center colorBlack">
													상품바코드
												</th>
												<th class="center colorBlack">
													BRAND
												</th>
												<th class="center colorBlack">
													상품명
												</th>
												<th class="center colorBlack">
													상품옵션
												</th>
												<th class="center colorBlack">
													단가
												</th>
												<th class="center colorBlack">
													화폐단위
												</th>
												<th class="center colorBlack">
													상품제질
												</th>
												<th class="center colorBlack">
													수량 단위
												</th>
												<th class="center colorBlack">
													UNIT_WTA
												</th>
												<th class="center colorBlack">
													UNIT_WTC
												</th>
												<th class="center colorBlack">
													무게 단위
												</th>
												<th class="center colorBlack">
													상품구분
												</th>
												<th class="center colorBlack">
													제조국
												</th>
												<th class="center colorBlack">
													제조사
												</th>
												<th class="center colorBlack">
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${takeinInfo}" var="takeinInfo" varStatus="status">
											<tr>
												<td class="center colorBlack">
													<a href="#" class="takeInfo" onclick="fn_takeinInfo('${takeinInfo.takeInCode}')" style="font-weight:bold;">
													${takeinInfo.takeInCode}
													</a>
												</td>
												<td class="center colorBlack">
													${takeinInfo.userId}
												</td>
												<td class="center colorBlack">
													${takeinInfo.cusItemCode}
												</td>
												<td class="center colorBlack">
													${takeinInfo.hsCode}
												</td>
												<td class="center colorBlack">
													${takeinInfo.itemBarcode}
												</td>
												<td class="center colorBlack">
													${takeinInfo.brand}
												</td>
												<td class="colorBlack">
													${takeinInfo.itemDetail}
												</td>
												<td class="colorBlack">
													${takeinInfo.itemOption}
												</td>
												<td class="center colorBlack">
													${takeinInfo.unitValue}
												</td>
												<td class="center colorBlack">
													${takeinInfo.unitCurrency}
												</td>
												<td class="center colorBlack">
													${takeinInfo.itemMeterial}
												</td>
												<td class="center colorBlack">
													${takeinInfo.qtyUnit}
												</td>
												<td class="center colorBlack">
													${takeinInfo.wta}
												</td>
												<td class="center colorBlack">
													${takeinInfo.wtc}
												</td>
												<td class="center colorBlack">
													${takeinInfo.wtUnit}
												</td>
												<td class="center colorBlack">
													${takeinInfo.itemDiv}
												</td>
												<td class="center colorBlack">
													${takeinInfo.makeCntry}
												</td>
												<td class="center colorBlack">
													${takeinInfo.makeCom}
												</td>
												<td>
												<c:if test="${takeinInfo.appvYn == 'Y'}">
													<input type="button" value="재고등록"  onclick="fn_popupTakeininStock('${takeinInfo.orgStation}','${takeinInfo.takeInCode}')">
												</c:if>
												<c:if test="${takeinInfo.appvYn == 'N'}">
													<input type="button" value="승인" onclick="fn_takeinApprv('${takeinInfo.takeInCode}')">
												</c:if>
												</td>
											</tr>
											</c:forEach>
										</tbody>
										
									</table>
									<form id="stockForm" action="">
									<input type="hidden" id = "orgStation" name="orgStation" value="">
									<input type="hidden" id = "groupIdx" name="groupIdx" value="">
									<input type="hidden" id = "userId" name="userId" value="">
									</form>
									<form id="mgrMsgForm" name="mgrMsgForm">
									</form>								
								</div>
							</div>
							<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
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
		
			$("#dtSearchBtn").on('click',function(){
				$("#dtSearchForm").attr("method", "get");            //get방식으로 바꿔서
				$("#dtSearchForm").submit();
			});

			function fn_takeinApprv(takeinCode) {	
		
					var Data = {"takeinCode":takeinCode}

					$.ajax({
						url:'/mngr/takein/takeinInAppv',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: Data, 
						success : function(data) {
						//alert(data.rstStatus);
						location.reload();
						/*
						var Status = data.rstStatus;
						if(Status == "SUCCESS"){
							location.reload();
						};
						*/
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다.");
		            }
				});				
			}
			

			function LoadingWithMask() {
			    //화면의 높이와 너비를 구합니다.
			    var maskHeight = $(document).height();
			    var maskWidth  = window.document.body.clientWidth;
			     
			    //화면에 출력할 마스크를 설정해줍니다.
			    var mask       ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
			    var loadingImg ='';
			      
			    loadingImg +="<div id='loadingImg'>";
			    loadingImg +=" <img src='/Spinner.gif' style='position: relative; display: block; margin: 0px auto;'/>";
			    loadingImg +="</div>"; 
			  
			    //화면에 레이어 추가
			    $('body')
			        .append(mask)
			        .append(loadingImg)
			        
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


			function fn_takeinInfo(takeinCode){
				window.open("/mngr/takein/popupTakeinInfo?takeinCode="+takeinCode,"PopupWin", "width=720,height=600");
			}

			function fn_popupTakeininStock(orgStation,takeinCode){
				window.open("/mngr/takein/popupTakeininfoReg?orgStation="+orgStation+"&takeinCode="+takeinCode,"PopupWin", "width=720,height=600");			
			}

			function fn_popupStockCancle(groupIdx, OrgStation, userId){
				$("#groupIdx").val(groupIdx);
				$("#orgStation").val(OrgStation);
				$("#userId").val(userId);

				
				var result = confirm("입고 취소 하시겠습니까?");
				if(result){
					var formData = $("#stockForm").serialize();
					$.ajax({
						url:'/comn/StockCancle',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData, 
						success : function(data) {

							alert(data.RST_MSG);
							var Status = data.RST_STATUS;
		
							if(Status == "SUCCESS"){
								location.reload();
							};
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다.");
			            }
					})
				}
			}

			function fn_popupTakeinInfo(){
				window.open("/mngr/takein/popupTakein","PopupWin", "width=720,height=600");			
			}

			/* function fn_popupTakeininfo(){
				window.open("/mngr/takein/popupTakein","PopupWin", "width=720,height=600");			
			} */

			jQuery(function($) {
				$(document).ready(function() {
					
					$("#5thMenu").toggleClass('open');
					$("#5thOne").toggleClass('active');
			 
					var myTable = $('#dynamic-table').DataTable( {
						"dom" : "t",
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "scrollY" : "580",
						select: {
							style: 'multi',
							selector: 'td:first-of-type'
						},
						"language":{
							"zeroRecords": "조회 결과가 없습니다.",
						}
				    } );

					myTable.on( 'select', function ( e, dt, type, index ) {
						if ( type === 'row' ) {
							$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', true);
						}
					} );
					
					myTable.on( 'deselect', function ( e, dt, type, index ) {
						if ( type === 'row' ) {
							$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', false);
						}
					} );

					/////////////////////////////////
					//table checkboxes
					$('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);
					
					//select/deselect all rows according to table header checkbox
					$('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function(){
						var th_checked = this.checked;//checkbox inside "TH" table header
						
						$('#dynamic-table').find('tbody > tr').each(function(){
							var row = this;
							if(th_checked) myTable.row(row).select();
							else  myTable.row(row).deselect();
						});
					});

					//select/deselect a row when the checkbox is checked/unchecked
					$('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
						var row = $(this).closest('tr').get(0);
						if(this.checked) myTable.row(row).deselect();
						else myTable.row(row).select();
					});
					
				})
			});
		</script>
		<!-- script addon end -->
		<script type="text/javascript">
			jQuery(function($) {

				
			

				
				$("#sendApply").on('click',function(e){
					$.ajax({
						url:'/mngr/aramex/aramexAplly',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: '', 
						success : function(data) {

							alert(data.RST_MSG);
							var Status = data.RST_STATUS;
		
							if(Status == "SUCCESS"){
								location.reload();
							};
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
			            }
					})
				})
				
			})
			
			jQuery(function($) {
				$("#btn_Delete").on('click',function(e){
					$.ajax({
						url:'/mngr/aramex/aramexDelete',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: '', 
						success : function(data) {

							alert(data.RST_MSG);
							var Status = data.RST_STATUS;
		
							if(Status == "SUCCESS"){
								location.reload();
							};
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
			            }
					})
				})
			})
		</script>
		
	</body>
</html>
