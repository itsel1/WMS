<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<!-- <%@ include file="/WEB-INF/jsp/importFile/head.jsp" %> -->
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
		<script src="/TestProject/assets/js/jquery-2.1.4.min.js"></script>

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
	이름: ${name}<br>
		<!-- headMenu Start -->

		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			<!-- SideMenu Start -->
			<%-- <%@ include file="/managerSideMenu.jsp" %> --%>
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
								반품관리
							</li>
							<li class="active">반품정보</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
							<div class="page-header">
								<h1>
									반품 관리
								</h1>
							</div>
							<form id="mainForm" name="uploadAramexExcel" id="uploadAramexExcel" enctype="multipart/form-data">
								<table class="table table-bordered" style="max-width:700px;">
									<thead>
									<tr>
										<th class="center colorBlack" style="width:120px;">진행사항</th>
										<td>
											<select style="width:200px;" name="transCode" id="transCode">
												<option value="">::전체::</option>
												<option value="">접수대기중</option>
												<option value="">수거중</option>
												<option value="">입고</option>
												<option value="">출고</option>
												<option value="">취소</option>
											</select>
										</td>
										<th class="center colorBlack" style="width:120px;">원송장번호</th>
										<td style="padding:0px 5px;width:200px; vertical-align: middle">
											<input style="width:100%" name="userId" type="text" value="${parameterInfo.userId}">
										</td>
										<td style="padding:0px 10px;width:50px; vertical-align:middle;" >
											<input type="submit" value="조회">
										</td>
									</tr>
									</thead>
								</table>
							</form>
								<form name="takeSearchForm" id="takeSearchForm">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div style="text-align: left;width:45%;float:left">
									    <input type="button" class="btn btn-white btn-inverse btn-xs"  value="접수하기" onclick="window.open('http://localhost:8010/TestProject/aci_express.jsp','aci_express','width=1000,height=500,location=no,status=no,scrollbars=yes')"/>
									</div>
									사용자 : ${name}
									<!-- <div style="text-align: right;width:45%;float:right">
										<button type="button" class="btn btn-white btn-inverse btn-xs" onclick="fn_takeinBlScan()">출고작업</button>
									</div> -->
									<input type="hidden" value="" id="delTarget" name="delTarget"/>
									<input type="hidden" value="" id="delTargetUser" name="delTargetUser"/>
								</form>
								<br>
							<br>
								<div id="table-contents" class="table-contents" style="overflow: auto;">
								<div class="display" >
									<table   id="dynamic-table " class="table table-bordered ">
										<thead>
											<tr>
												<th class="center" rowspan="2">
													<label class="pos-rel">
														<input type="checkbox" class="ace" id="chkAll"/>
														<span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack" rowspan="2">
													No
												</th>
												<th class="center colorBlack" rowspan="2">
													진행상황
												</th>
												<th class="center colorBlack" rowspan="2">
													원운송장번호
												</th>
												<th class="center colorBlack" rowspan="2">
													발송인명
												</th>
												<th class="center colorBlack" rowspan="2">
													반송유형
												</th>
												<th class="center colorBlack" rowspan="2">
													등록일
												</th>
												<th class="center colorBlack" colspan="3">
													수거택배
												</th>
												<th class="center colorBlack" colspan="2">
													발송정보
												</th>
												<th class="center colorBlack" colspan="2">
													문의사항
												</th>
											</tr>
											<tr>
												<th class="center colorBlack">택배사</th>
												<th class="center colorBlack">택배번호</th>
												<th class="center colorBlack">접수일</th>
												<th class="center colorBlack">택배사</th>
												<th class="center colorBlack">택배번호</th>
												<th class="center colorBlack">고객 메세지</th>
												<th class="center colorBlack">관리자 메세지</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${takeinOrderList}" var="takeinOrderList" varStatus="status">
											<tr style="background-color: #f9f9f9;">
												<td style="text-align: center">
													<input type="checkbox" name="nno[]" value="${takeinOrderList.nno}">
												</td>
												<td class="center colorBlack">											
													${takeinOrderList.num}
												</td> 
												<td class="center colorBlack">											
													${takeinOrderList.transCode}
												</td>
												<td class="center colorBlack">
													${takeinOrderList.hawbNo}
												</td>
												<td class="center colorBlack">
													${takeinOrderList.shipName}
												</td>
												<td class="center colorBlack">
													${takeinOrderList.shipType}
												</td>
												<td class="center colorBlack">
													${takeinOrderList.regiDate}
												</td>
												<td class="center colorBlack">
													${takeinOrderList.collectEx}
												</td>
												<td class="center colorBlack">
													${takeinOrderList.collectExTel}
												</td>
												<td class="center colorBlack">
													${takeinOrderList.rctpDate}
												</td>
												<td class="center colorBlack">
													${takeinOrderList.sendEx}
												</td>
												<td class="center colorBlack">
													${takeinOrderList.sendExTel}
												</td>
												<td class="center colorBlack">
													${takeinOrderList.cusMsg}
												</td>
												<td class="center colorBlack">
													${takeinOrderList.managerMsg}
												</td>
											</tr>
												<c:forEach items="${takeinOrderList.takeinOrderItem}" var="takeinOrderItem" varStatus="status">
													<tr>
														<td colspan="8" style="text-align:left;padding-left:55%;border:0px;height:10px;padding-top:3px;padding-bottom:0px;">
															<div class="row" style="margin:0px;">
																<div class="col-xs-12 col-md-2">
																	[${takeinOrderItem.cusItemCode}]
																</div>
																<div class="col-xs-12 col-md-2">
																	${takeinOrderItem.brand}
																</div>
																<div class="col-xs-12 col-md-7">
																	${takeinOrderItem.itemDetail}
																</div>
															</div>
															<div class="row" style="margin:0px;">
																<div class="col-xs-12 col-md-3">
																	${takeinOrderItem.unitValue} * ${takeinOrderItem.itemCnt} =  ${takeinOrderItem.unitValue * takeinOrderItem.itemCnt}
																</div>
																<div class="col-xs-12 col-md-2">
																	${takeinOrderItem.itemValue}
																</div>
															</div>
														</td>
													</tr>	
												</c:forEach>
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
							<%-- <%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %> --%>
							</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		<!-- Footer Start -->
		<%-- <%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %> --%>
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
		
		<script src="/TestProject/assets/js/jquery.dataTables.min.js"></script>
		<script src="/TestProject/assets/js/jquery.dataTables.bootstrap.min.js"></script>
		<script src="/TestProject/assets/js/fixedHeader/dataTables.fixedHeader.min.js"></script>
		<script src="/TestProject/assets/js/fixedHeader/dataTables.fixedHeader.js"></script>
		<script src="/TestProject/assets/js/dataTables.buttons.min.js"></script>
		<script src="/TestProject/assets/js/buttons.flash.min.js"></script>
		<script src="/TestProject/assets/js/buttons.html5.min.js"></script>
		<script src="/TestProject/assets/js/buttons.print.min.js"></script>
		<script src="/TestProject/assets/js/buttons.colVis.min.js"></script>
		<script src="/TestProject/assets/js/dataTables.select.min.js"></script>
	
		<!-- script addon end -->
		<script type="text/javascript">

			jQuery(function($) {

				$("#5thMenu").toggleClass('open');
				$("#5thThr").toggleClass('active');

				 $("#transCode").change(function(){
					 $("#mainForm").submit();
				 });
				
				$('#printCheck').on('click',function(e){

					var targets = new Array();
					
					 $("input[name='nno[]']").each(function(){
						 var check =  this.checked;
						 if(check){
							targets.push($(this).val());
						 };
					});

						if(targets == ""){
							alert("출력할 운송장이 없습니다.");
							return false;
						}
						
						window.open("/comn/printHawb?targetInfo="+targets+"&formType="+$("#transCode").val(),"popForm","_blank");
				})
				
				
				$("#chkAll").click(function() {
		            if($("#chkAll").prop("checked")){ //해당화면에 전체 checkbox들을 체크해준다
		                $("input[name='nno[]']").prop("checked",true); // 전체선택 체크박스가 해제된 경우
		            } else { //해당화면에 모든 checkbox들의 체크를해제시킨다.
		                $("input[name='nno[]'").prop("checked",false);
		            }
		        });

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
			
			function fn_takeinStockList(){
					window.open("/mngr/takein/popupTakeinOutList","PopupStock", "width=1350,height=860");
			}
			
			function fn_takeinBlScan(){
					window.open("/mngr/takein/popupTakeinBlScan","PopupWinScanBl", "width=1350,height=860");
			}
			function fn_readShopify(){
				LoadingWithMask();
				$.ajax({
					url:'/api/shopify',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: '', 
					success : function(data) {
						var notice = "";
						for(var i =0; i < data.length; i++){
							notice += data[i]+"\n";
						}
						alert(notice);
						location.reload()
						
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			}

			function fn_sendShopify_Fed(){
				var targets = new Array();
				
				 $("input[name='nno[]']").each(function(){
					 var check =  this.checked;
					 if(check){
						targets.push($(this).val());
					 };
				});

				if(targets == ""){
					alert("출력할 운송장이 없습니다.");
					return false;
				}


				LoadingWithMask();
				$.ajax({
					url:'/api/shopifyFedUpdate',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data:{datas: targets}, 
					success : function(data) {
						var notice = "";
						for(var i =0; i < data.length; i++){
							notice += data[i]+"\n";
						}
						alert(notice);
						location.reload()
						
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})

				/*window.open("/api/shopifyFedUpdate?targetInfo="+targets+"&formType="+$("#formSelect").val(),"popForm","_blank");*/
			}


			function fn_sendShopify_Usps(){
				var targets = new Array();
				
				 $("input[name='nno[]']").each(function(){
					 var check =  this.checked;
					 if(check){
						targets.push($(this).val());
					 };
				});

				if(targets == ""){
					alert("출력할 운송장이 없습니다.");
					return false;
				}


				LoadingWithMask();
				$.ajax({
					url:'/api/shopifyFedUpdateUsps',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data:{datas: targets}, 
					success : function(data) {
						var notice = "";
						for(var i =0; i < data.length; i++){
							notice += data[i]+"\n";
						}
						alert(notice);
						location.reload()
						
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})

				
				
				/*window.open("/api/shopifyFedUpdate?targetInfo="+targets+"&formType="+$("#formSelect").val(),"popForm","_blank");*/
			}

			

			$("#delBtn").on('click',function(e){
				if(confirm("삭제하시겠습니까?")){
					var targets = new Array();
					$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if(td_checked){
								targets.push($(this).val());
						}
					});

					var targets2 = new Array();
					$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if(td_checked){
							targets2.push(row.children[3].innerText);
						}
					});
					$("#delTarget").val(targets);
					$("#delTargetUser").val(targets2);
					var formData = $("#shpngForm").serialize();
					$("#takeSearchForm").attr("action", "/mngr/aplctList/deleteShpngList");    ///board/modify/로
					$("#takeSearchForm").attr("method", "post");            //get방식으로 바꿔서
					$("#takeSearchForm").submit();
				}
				/* $.ajax({
					url:'/mngr/aplctList/deleteShpngList',
					type: 'POST',
					data: formData,
					success : function(data) {
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("삭제에 실패 하였습니다. 관리자에게 문의해 주세요.");
		            }
				}) */ 

			})
			

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
			    $('#mask')({
			            'width' : maskWidth
			            ,'height': maskHeight
			            ,'opacity' :'0.3'
			    });
			  
			    //마스크 표시
			    $('#mask').show();  
			    //로딩중 이미지 표시
			}
		</script>
		
	</body>
</html>
