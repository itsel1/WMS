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
	
	#searchBtn {
		border:none;
		height:36px;
		width:100%;
		font-weight:bold;
		color:#fff;
		background:rgba(52, 122, 183, 0.9);
	}
	
	#searchBtn:hover {
		font-weight:bold;
		background:rgba(52, 122, 183, 0.7);
	}

	#loading {
		height: 100%;
		left: 0px;
		position: fixed;
		_position: absolute;
		top: 0px;
		width: 100%;
		filter: alpha(opacity = 50);
		-moz-opacity: 0.5;
		opacity: 0.5;
	}
	
	.loading {
		background-color: white;
		z-index: 999999;
	}
	
	#loading_img {
		position: absolute;
		top: 50%;
		left: 50%;
		/* height: 200px; */
		margin-top: -75px; 
		margin-left: -75px; 
		z-index: 999999;
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
							<li class="active">출고 작업</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								사입 출고 작업
							</h1>
						</div>
						<form id="mainForm" name="mainForm" enctype="multipart/form-data">
							<table class="table table-bordered" style="width:80%;">
								<thead>
								<tr>
									<th class="center colorBlack" style="width:8%;">배송사</th>
									<td style="padding:0px;width:8%;">
										<select style="width:100%;height:36px;" name="transCode" id="transCode">
											<option value="">::전체::</option>
											<c:forEach items="${transComList}" var="transComList" varStatus="status">
												<option value="${transComList.transCode}"
													<c:if test="${parameterInfo.transCode eq transComList.transCode}">selected</c:if>
												>${transComList.transName}</option>
											</c:forEach>
											<!-- <option>CJ</option>
											<option></option> -->
										</select>
									</td>
									<th class="center colorBlack" style="width:8%;">UserID</th>
									<td style="padding:0px;width:12%;">
										<input style="width:100%;height:36px;" name="userId" type="text" value="${parameterInfo.userId}">
									</td>
									<th class="center colorBlack" style="width:8%;">HawbNo</th>
									<td style="padding:0px;width:12%;">
										<input style="width:100%;height:36px;" type="text" name="hawbNo" value="${parameterInfo.hawbNo}" >
									</td>
									<th class="center colorBlack" style="width:8%;">OrderNo</th>
									<td style="padding:0px;width:12%;">
										<input style="width:100%;height:36px;" type="text" name="orderNo" value="${parameterInfo.orderNo}">
									</td>
									<th class="center colorBlack" style="width:8%;">수취인</th>
									<td style="padding:0px;width:12%;">
										<input type="text" style="width:100%;height:36px;" name="cneeName" value="${parameterInfo.cneeName}">
									</td>
									<td style="padding:0px;width:4%;height:36px;">
										<input type="submit" value="조회" id="searchBtn">
									</td>
								</tr>
								</thead>
							</table>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<div style="text-align: left;width:80%;float:left">
							    <input type="button" class="btn btn-white btn-inverse btn-xs"  value="출고 재고" onclick="fn_takeinStockList()"/>
								<input type="button" id="printCheck" class="btn btn-white btn-inverse btn-xs"  value="BlPrint"/>
								<c:if test="${parameterInfo.orgStation eq '082'}">
								<input type="button" class="btn btn-white btn-inverse btn-xs" id="printTypeC" value="BlPrint (C)"/>
								</c:if>
								<input type="button" class="btn btn-white btn-inverse btn-xs" value="패킹리스트 출력" id="packingCheck" />
								<input type="button" class="btn btn-white btn-inverse btn-xs" value="BlPrint + 패킹리스트" id="blPackingCheck"/>
								<c:if test="${orgStation eq '213'}">
									<input type="button" class="btn btn-white btn-inverse btn-xs"  value="Shopify 사입 가져오기" onclick="fn_readShopify()"/>
									<input type="button" class="btn btn-white btn-inverse btn-xs" value="usps로 등록" onclick="fn_sendUsps()"/>
									<input type="button" class="btn btn-white btn-inverse btn-xs"  value="fedex로 등록" onclick="fn_sendShopify_Fed()"/>
								</c:if>
								<!-- <input type="button" class="btn btn-white btn-inverse btn-xs"  value="usps로 재등록" onclick="fn_sendShopify_Usps()"/> -->
								<!-- <input type="button" class="btn btn-white btn-inverse btn-xs" value="usps 등록 (Shipstation)" onclick="fn_sendShipstation_Usps()"> -->
								<!-- <input type="button" class="btn btn-white btn-inverse btn-xs" value="리스트 전체 다운로드" id="downAllList"> -->
								<input type="button" class="btn btn-white btn-inverse btn-xs" value="리스트 전체 다운로드" id="test-button">
								<button type="button" class="btn btn-white btn-primary btn-xs" id="invPdf" style="text-transform: unset !important;">
									Commercial Invoice (Pdf)
								</button>
								<button type="button" class="btn btn-white btn-primary btn-xs" id="invExcel" style="text-transform: unset !important;">
									Commercial Invoice (Excel)
								</button>
								<iframe id="downloadFrame" style="display: none;"></iframe>
							</div>
							<div style="text-align: right;width:20%;float:right">
								<%-- <c:if test="${parameterInfo.orgStation eq '082'}">
								<button type="button" class="btn btn-white btn-inverse btn-xs" id="checkOutBatch" name="checkOutBatch">일괄출고</button>
								</c:if> --%>
								<button type="button" class="btn btn-white btn-inverse btn-xs" id="delBtn" name="delBtn">삭제</button>
								<button type="button" class="btn btn-white btn-inverse btn-xs" id="resetScan" name="resetScan">스캔 초기화</button>
								<button type="button" class="btn btn-white btn-inverse btn-xs" onclick="fn_takeinBlScan()">출고작업</button>
							</div>
							
							<input type="hidden" value="" id="delTarget" name="delTarget"/>
							<input type="hidden" value="" id="delTargetUser" name="delTargetUser"/>
						
							<br>
							<br>
							<div id="table-contents" class="table-contents" style="overflow: auto;">
								<div class="display" >
									<table id="dynamic-table " class="table table-bordered ">
										<thead>
											<tr>
												<th class="center">
													<label class="pos-rel">
														<input type="checkbox" class="ace" id="chkAll"/>
														<span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack">
													No
												</th>
												<th class="center colorBlack">
													등록일
												</th>
												<th class="center colorBlack">
													주문번호
												</th>
												<th class="center colorBlack">
													UserID
												</th>
												<th class="center colorBlack">
													배송사
												</th>
												<th class="center colorBlack">
													운송장 번호
												</th>
												<th class="center colorBlack">
													수취인명
												</th>
												<th class="center colorBlack">
													도착국가
												</th>
												<th class="center colorBlack">
													주소
												</th>
												<th class="center colorBlack">
													전화번호
												</th>
												<th class="center colorBlack">
													상품수량<br/>(Total)
												</th>
												<th class="center colorBlack">
													상품무게<br/>(Total)
												</th>
												<th class="center colorBlack">
													상품코드 x 수량
												</th>
											</tr>
										</thead>
										<tbody style="background-color:#fff;">
											<c:forEach items="${takeinOrderList}" var="list" varStatus="status">
											<c:set var="cusItemCode" value="${fn:replace(list.cusItemCode, ',', '<br/>')}" />
											<tr>
												<td style="text-align: center">
													<input type="checkbox" name="nno[]" value="${list.nno}">
												</td>
												<td class="center colorBlack">											
													${list.num}
												</td> 
												<td class="center colorBlack">
													${fn:substring(list.nno,0,8)}
												</td> 
												<td class="center colorBlack">											
													<a href="/mngr/takein/modifyAdminOne?transCode=${list.transCode}&types=takein&nno=${list.nno}">${list.orderNo}</a>
												</td>
												<td class="center colorBlack">
													${list.userId}
												</td>
												<td class="center colorBlack">
													${list.transCode}
												</td>
												<td class="center colorBlack">
													<a href="/mngr/takein/modifyAdminOne?transCode=${list.transCode}&types=takein&nno=${list.nno}">${list.hawbNo}</a>
												</td>
												<td class="center colorBlack">
													${list.cneeName}
												</td>
												<td class="center colorBlack">
													${list.dstnNation}
												</td>
												<td style="text-align:left;width:200px;">
													${list.cneeZip} ${list.cneeAddr} ${list.cneeAddrDetail}
												</td>
												<td class="center colorBlack">
													${list.cneeTel}
												</td>
												<td style="text-align:right;">
													${list.totalItemCnt}
												</td>
												<td style="text-align:right;">
													${list.totalWta}
												</td>
												<td class="center colorBlack">
													<c:out value="${cusItemCode}" escapeXml="false"/>
												</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
							<input type="hidden" id = "orgStation" name="orgStation" value="">
							<input type="hidden" id = "groupIdx" name="groupIdx" value="">
							<input type="hidden" id = "userId" name="userId" value="">
						</form>
						<form name="targetNnoForm" id="targetNnoForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input type="hidden" name="targetInfos" id="targetInfos">
						</form>
					</div>
				</div>
				<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
			</div>
		</div>
		<!-- /.main-content -->
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
	
		<!-- script addon end -->
		<script type="text/javascript">
			var loading = "";
			jQuery(function($) {
				loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="/image/Pulse.gif" />').appendTo(document.body).hide();
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
				});

				$("#printTypeC").on('click', function(e) {
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
					
					window.open("/comn/printHawb?targetInfo="+targets+"&formType="+$("#transCode").val()+"&labelType=C","popForm","_blank");
				});
				
				$("#packingCheck").on('click', function() {
					var targets = new Array();
					$("input[name='nno[]']").each(function() {
						var check = this.checked;
						if(check) {
							targets.push($(this).val());
						}
					})

					if (targets == "") {
						alert("출력할 운송장이 없습니다.");
						return false;
					}
					console.log(targets);
					window.open("/comn/printPacking?targetInfo="+targets, "popForm", "_blank");
				})
				
				$("#blPackingCheck").on('click', function() {
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
						
						window.open("/comn/printHawb?targetInfo="+targets+"&formType="+$("#transCode").val()+"&packChk=Y","popForm","_blank");
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
					url:'/api/shopifyTest',
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

			function fn_sendUsps() {
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
					url:'/api/ssapiUsps',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data:{datas: targets}, 
					success : function(data) {
						alert(data.msg);
						location.reload();
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			}

			function fn_sendShipstation_Usps() {
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
					url:'/api/shipStationUsps',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data:{datas: targets}, 
					success : function(data) {
						alert(data.msg);
						location.reload();
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			}

			$('#checkOutBatch').on('click',function(e){
				
				var targets = new Array();
				$("input[name='nno[]']").each(function(){
					 var check =  this.checked;
					 if(check){
						targets.push($(this).val());
					 };
				});
				
				LoadingWithMask();
				$.ajax({
					url:'/mngr/takein/takeInStockInBatchAll',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data:{datas: targets}, 
					success : function(data) {
						alert("success");
						location.reload();
						
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
					
					

			})

			$("#delBtn").on('click',function(e){
				if(confirm("삭제하시겠습니까?")){
					var targets = new Array();
					var targets2 = new Array();
					 $("input[name='nno[]']").each(function(){
						 var row = $(this).closest('tr').get(0);
						 var check =  this.checked;
						 if(check){
							targets.push($(this).val());
							targets2.push(row.children[3].innerText);
						 };
					});
					$("#delTarget").val(targets);
					$("#delTargetUser").val(targets2);
					if($("#delTarget").val() == ""){
						alert("선택된 대상이 없습니다.")
						return;
					}

					
					var formData = $("#mainForm").serialize();
					$("#mainForm").attr("action", "/mngr/takein/takeinOrderList");
					$("#mainForm").attr("method", "post");
					$("#mainForm").submit();
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

			});

			$("#downAllList").on('click', function(e) {
				//loading.show();

				
				$("#mainForm").attr("action", "/mngr/takein/takeinOrderListExcelDown");
				$("#mainForm").attr("method", "POST");
				$("#mainForm").submit();

				$("#mainForm").on('submit', function(e) {
					console.log("11123");
					//loading.hide();

					$("#mainForm").attr("action", "/mngr/takein/takeinOrderList");
					$("#mainForm").attr("method", "GET");
				});
			});
			
			$('#invPdf').on('click',function(e){
				var targets = new Array();
				
				 $("input[name='nno[]']").each(function(){
					 var check =  this.checked;
					 if(check){
						targets.push($(this).val());
					 };
				});

				window.open("/mngr/printCommercial?targetInfo="+targets+"&formType="+$("#formSelect").val(),"popForm","_blank");
				window.close();
			});

			$('#invExcel').on('click',function(e){
				var targets = new Array();
				
				 $("input[name='nno[]']").each(function(){
					 var check =  this.checked;
					 if(check){
						targets.push($(this).val());
					 };
				});
				$("#targetInfos").val(targets);

				$("#targetNnoForm").attr("action", "/mngr/printCommercialExcel");
				$("#targetNnoForm").attr("method", "get");
				$("#targetNnoForm").submit();
				
				
			});
			

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

			$("#registBtn").click(function() {
				window.open("/mngr/takein/popupTakeinReg","PopupWin","width=800,height=800");
			});

			$("#test-button").on('click', function(e) {
				loading.show();
				var iframe = document.getElementById('downloadFrame');
				iframe.src = '/mngr/takein/takeinOrderListExcelDownTest';

				var checkInterval = setInterval(function() {
					if (iframe.contentDocument && iframe.contentDocument.readyState == 'complete' 
						&& iframe.contentDocument.documentElement && iframe.contentDocument.documentElement.outerHTML.length > 0) {
						loading.hide();
						clearInterval(checkInterval);
					}
				},800);

			});
			
			$("#resetScan").on('click', function(e) {
				$.ajax({
					url : '/mngr/takein/resetTakeinScanHis',
					type : 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						var msg = data.rstMsg;
						alert(msg);
						location.reload();
		            }, 		    
		            error : function(xhr, status) {
						alert("System Error Occured");
		            }
					
				})

			});
			
		</script>
		
	</body>
</html>
