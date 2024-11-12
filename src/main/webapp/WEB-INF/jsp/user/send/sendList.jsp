<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp"%>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/bootstrap-datepicker3.min.css" />
	<link rel="stylesheet" href="/assets/css/ui.jqgrid.min.css" />
<style type="text/css">
.colorBlack {
	color: #000000 !important;
}
.noneArea {
	margin:0px !important;
	padding:0px !important;
	border-bottom : none;
}

.containerTable {
	width: 100% !important;
}

table ,tr td{
	word-break:break-all;
}
body{
	font-size:12px;
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
</head>
<title>배송 현황</title>
<body class="no-skin">
	<!-- headMenu Start -->
	<div class="site-wrap">
		<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp"%>
		<!-- headMenu End -->

		<!-- Main container Start-->
		<div class="toppn">
			<div class="container">
				<div class="row">
					<div class="col-md-12 mb-0" style="font-size: 14px !important;">
						<a>배송 현황</a> <span class="mx-2 mb-0">/</span> <strong
							class="text-black">발송목록</strong>
					</div>
				</div>
			</div>
		</div>
		<!-- Main container Start-->
		<div class="container">
			<div class="page-content noneArea">
				<div class="page-header noneArea">
					<h3>발송목록</h3>
				</div>
				<form name="uploadForm" id="uploadForm" enctype="multipart/form-data">
					<div id="inner-content-side">
						<div id="search-div">
							<br>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<div class="row">
								<div class="col-xs-12 col-sm-11 col-md-12">
									<div class="row">
										<div class="col-xs-8 col-sm-11 col-md-12">
											<div class="col-xs-8 col-sm-11 col-md-2" style="padding:0px 0px 0px 15px!important">
												<div class="input-group">
													<span class="input-group-addon">
														<i class="fa fa-calendar bigger-110"></i>
													</span>
													<div class="input-group input-daterange">
														<input type="text" class=" form-control" name="startDate" id="startDate" value="${search.startDate}" autocomplete="off"/>
															<span class="input-group-addon">
																to
															</span>
														<input type="text" class=" form-control" name="endDate" id="endDate" value="${search.endDate}" autocomplete="off"/>
													</div>
													
												</div>
											</div>
											<div class="col-md-10" style="padding:0px!important">
												<span class="input-group col-md-12" style="display: inline-flex;">
													<input type="text" class="form-control" style="width:100%; max-width: 60px; text-align: center;" value="BL No." readonly>
													<input type="text" class="form-control" name="hawbNo" id="hawbNo" value="${search.hawbNo}" style="width:100%; max-width: 150px" autocomplete="off"/>
													<input type="text" class="form-control" style="width:100%; max-width: 70px; text-align: center;" value="orderNo" readonly>
													<input type="text" class="form-control" name="orderNo" id="orderNo" value="${search.orderNo}" style="width:100%; max-width: 150px" autocomplete="off"/>
													<button type="submit" class="btn btn-default no-border btn-sm">
														<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
													</button>
													<button class="btn btn-xs btn-primary" id="excelDown">엑셀자료 다운로드</button>
												</span>
											</div>
											<br>
										</div> 
									</div>
								</div>
							</div>
							<div class="row" style="margin-left:15px;margin-top:10px;">
								<button type="button" class="btn btn-xs btn-primary" id="invPdf" style="text-transform: unset !important;">
									Commercial Invoice (Pdf)
								</button>
								<button type="button" class="btn btn-xs btn-primary" id="invExcel" style="text-transform: unset !important;">
									Commercial Invoice (Excel)
								</button>
								<button type="button" class="btn btn-xs btn-success" id="packingExcel" style="text-transform: unset !important;">
									Packing List (Excel)
								</button>
								<button type="button" class="btn btn-xs btn-success" id="packingPdf" style="text-transform: unset !important;">
									Packing List (Pdf)
								</button>
								<button type="button" class="btn btn-xs btn-primary" id="blPdf" style="text-transform: unset !important;">
									송장 출력
								</button>
								<button type="button" class="btn btn-xs btn-primary" id="blFile" style="text-transform: unset !important;">
									송장 다운로드
								</button>
								<span class="red">&nbsp;&nbsp;※송장 출력 및 다운로드는 같은 배송사만 가능합니다.</span>
							</div>
							<div class="col-xs-12 col-sm-12 hidden-md hidden-lg">
								<div class="col-xs-8 col-sm-11 col-md-2">
								<button type="submit" class="btn btn-default no-border btn-sm" data-toggle="modal" data-target="#searchModal">
									<i class="ace-icon fa fa-search icon-on-right bigger-110">검색조건 입력하기</i>
								</button>
								</div>
							</div>
							
						</div>
					
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<div class="col-xs-12 form-group" style="margin:0px;">
							<div id="table-contents" class="table-contents">
								<div class="hr hr-8 dotted"></div>
								<div class="space-10"></div>
									<table id="dynamic-table" class="table table-bordered table-hover containerTable">
										<thead>
											<tr>
												<th class="center">
													<label class="pos-rel">
															<input type="checkbox" class="ace" /> <span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack" style="width:90px;">
													발송국가
												</th>
												<th class="center colorBlack" style="width: 90px;">
													도착국가
												</th>
												<th class="center colorBlack">
													배송사
												</th>
												<th class="center colorBlack" style="width:120px;">
													주문 등록 날짜
												</th>
												<th class="center colorBlack">
													BL No.
												</th>
												<th class="center colorBlack">
													Order No
												</th>
												<th class="center colorBlack" style="width:300px;">
													수취인 정보
												</th>
												<th class="center colorBlack">
													대표 상품
												</th>
												<th class="center colorBlack">
													실 무게
												</th>
												<th class="center colorBlack">
													부피 무게
												</th>
												<th class="center colorBlack" style="width:90px;">
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${sendList}" var="send" varStatus="status">
												<tr>
													<td class="center">
														<label class="pos-rel">
																<input type="checkbox" name="nno" class="ace" value="${send.nno}"/> <span class="lbl"></span>
																<input type="hidden" name="transCode" value="${send.transCode}"/>
														</label>
													</td>
													<td class="center colorBlack">
														${send.orgNationName}
													</td>
													<td class="center colorBlack">
														${send.dstnNationName}
													</td>
													<td class="center colorBlack">
														 ${send.remark}
													</td>
													<td class="center colorBlack">
														 ${send.orderDate}
													</td>
													<td class="center colorBlack">
														${send.hawbNo }
														<c:if test="${!empty send.shipperReference}">
															<br/>REFERENCE NO : ${send.shipperReference }
														</c:if>
														<c:if test="${!empty send.valueMatchNo}">
															<br/>${send.valueMatchNo }
														</c:if>
													</td>
													<td class="center colorBlack">
														${send.orderNo }
													</td>
													
													<td class="center colorBlack">
													<p style="float:left ;width:60%;text-align:left;font-weight:bold;">${send.cneeName} / ${send.nativeCneeName}</p>
													<p style="float:left ;width:30%;">${send.cneeTel}</p><br/>
													<c:choose>
														<c:when test="${send.cneeZip eq ''}">
														<br/>
															<p style="float:left ;width:90%;text-align:left">${send.cneeAddr}</p><br/>
															<p style="float:left ;width:90%;text-align:left">${send.nativeCneeAddr}</p>
														  </c:when>
														    <c:otherwise>
														    	<br/>
														    	<span style="float:left;width:100%;text-align:left;">
																	<label style="color:blue">[${send.cneeZip}]</label><br>
														    		<label> ${send.cneeAddr}</label><br>
														    		<label>${send.nativeCneeAddr}</label>
														    	</span>
														  </c:otherwise>
														</c:choose>
													</td>
													<td class="center colorBlack">
														${send.mainItem}
													</td>
													<td class="center colorBlack">
														${send.wta}
													</td>
													<td class="center colorBlack">
														${send.wtc}
													</td>
													
													<td class="center colorBlack">
													
														<%-- <a style="cursor: pointer;"onclick="window.open('/comn/deliveryTrack/${send.hawbNo}','배종조회','width=650,height=800')">[POD]</a> --%>        	
													
 														<c:choose>
														    <c:when test="${send.transCode eq 'FED'}">
														      	<a style="cursor: pointer;"onclick="window.open('https://www.fedex.com/apps/fedextrack/index.html?tracknumbers=${send.hawbNo}&cntry_code=kr','배종조회','width=650,height=800')">[POD]</a>
														    </c:when>
														   <%--  <c:when test="${send.transCode eq 'SEK'}">
														      	<a style="cursor: pointer;"onclick="window.open('http://track.omniparcel.com/1489353-${send.hawbNo}','배종조회','width=650,height=800')">[POD]</a>
														    </c:when> --%>
														    <c:when test="${send.transCode eq 'CJ'}">
														    	<a style="cursor: pointer;"onclick="window.open('/comn/tracking?hawbNo=${send.hawbNo}','배종조회','width=650,height=800')">[POD]</a>
														    </c:when>
														    <c:when test="${send.transCode eq 'VNP'}">
														    	<a style="cursor: pointer;"onclick="window.open('/comn/tracking?hawbNo=${send.hawbNo}','배종조회','width=650,height=800')">[POD]</a>
														    </c:when>
														    <c:otherwise>
														      	<a style="cursor: pointer;"onclick="window.open('/comn/deliveryTrack/${send.hawbNo}','배종조회','width=650,height=800')">[POD]</a>        	
														    </c:otherwise>
														</c:choose>
													
															
													</td>
												</tr>	
										 	</c:forEach>
										</tbody>
									</table>
							</div>
						</div>
						<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
					</div>
				</form>
				<form name="targetNnoForm" id="targetNnoForm">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<input type="hidden" name="targetInfos" id="targetInfos">
					<input type="hidden" name="targetTransCode" id="targetTransCode">
				</form>
			</div>
		</div>
	</div>
	



	<!-- Footer Start -->
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
	<!-- Footer End -->
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<!-- <script src="/assets/js/jquery-3.7.1.min.js"></script> -->
	<!-- <script src="/testView/js/jquery-3.3.1.min.js"></script> -->
	<script src="/testView/js/jquery-ui.js"></script>
	<script src="/testView/js/popper.min.js"></script>
	<!-- <script src="/testView/js/bootstrap.min.js"></script> -->
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/testView/js/owl.carousel.min.js"></script>
	<script src="/testView/js/jquery.magnific-popup.min.js"></script>
	<script src="/testView/js/aos.js"></script>

	<script src="/testView/js/main.js"></script>

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
	
	<script src="/assets/js/bootstrap-datepicker.min.js"></script>
	<!-- script on paging end -->

	<!-- script addon start -->


	<script type="text/javascript">
		var loading = "";
		jQuery(function($) {
			loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="/image/Pulse.gif" />').appendTo(document.body).hide();
			
			$(document).ready(function() {

			});


			$("#excelDown").on('click', function() {
				if ($("#startDate").val() == "" || $("#endDate").val() == "") {
					alert("기간을 설정해주세요.");
					return false;
				} else {
					$("#uploadForm").attr("action", "/cstmr/dlvry/excelDown");
					$("#uploadForm").attr("method", "POST");
					$("#uploadForm").submit();
				}
			});

			
			$('#startDate').datepicker({
				autoclose:true,
				todayHightlight:true,
				format: 'yyyymmdd',
			}).on('changeDate', function(e) {
				$("#endDate").val($("#startDate").val())
			});

			$('#endDate').datepicker({
				autoclose:true,
				todayHightlight:true,
				format: 'yyyymmdd',
			});

			$('#invPdf').on('click',function(e){
				var targets = new Array();
				$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
					var row = $(this).closest('tr').get(0);
					var td_checked = this.checked;
					if(td_checked){
						targets.push($(this).val());
					}
				});
				window.open("/cstmr/printCommercial?targetInfo="+targets+"&formType="+$("#formSelect").val(),"popForm","_blank");
				window.close();
			});

			$('#invExcel').on('click',function(e){
				var targets = new Array();
				$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
					var row = $(this).closest('tr').get(0);
					var td_checked = this.checked;
					if(td_checked){
						targets.push($(this).val());
					}
				});

				$("#targetInfos").val(targets);
				$("#targetNnoForm").attr("action", "/cstmr/printCommercialExcel");
				$("#targetNnoForm").attr("method", "get");
				$("#targetNnoForm").submit();
				
				
			});
			
			$('#packingExcel').on('click',function(e){
				
				var targets = new Array();
				$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
					var row = $(this).closest('tr').get(0);
					var td_checked = this.checked;
					if(td_checked){
						targets.push($(this).val());
					}
				});

				$("#targetInfos").val(targets);
				$("#targetNnoForm").attr("action", "/cstmr/printPackingListExcel");
				$("#targetNnoForm").attr("method", "get");
				$("#targetNnoForm").submit();
				
				
			});
			
			
			$("#packingPdf").on('click', function(e) {
				var targets = new Array();
				$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
					var row = $(this).closest('tr').get(0);
					var td_checked = this.checked;
					if(td_checked){
						targets.push($(this).val());
					}
				});
				window.open("/cstmr/printPackingListPdf?targetInfo="+targets+"&formType="+$("#formSelect").val(),"packingList","_blank");
				window.close();
			});

			$("#blPdf").on('click', function(e) {
				
				var targets = new Array();
				$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
					var row = $(this).closest('tr').get(0);
					var td_checked = this.checked;
					if(td_checked){
						targets.push($(this).val());
					}
				});

				console.log(targets);
				window.open("/comn/printHawb?targetInfo="+targets+"&formType="+$("#formSelect").val(),"popForm","_blank");

			});

			$("#blFile").on('click', function(e) {

				var targets = new Array();
				var transCodes = new Array();

				
				$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
					var row = $(this).closest('tr').get(0);
					var td_checked = this.checked;
					if(td_checked){
						targets.push($(this).val());
						transCodes.push($(row).find("input[name='transCode']").val());		
					}
				});
				
				var seen = {};
				var hasDuplicates = false;

				for (var i = 0; i < transCodes.length; i++) {
					var item = transCodes[i];
					if (seen[item]) {
						hasDuplicates = true;
						break;
					} else {
						seen[item] = true;
					}
				}

				if (!hasDuplicates && transCodes.length > 1) {
					alert("동일한 배송사만 선택 가능합니다.");
					return false;
				}

				var transCode = transCodes[0];
				loading.show();
				
				$.ajax({
					url : '/comn/createPdfZipFile',
					type : 'POST',
					beforeSend : function(xhr)
					{   
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					traditional : true,
					data : {nnoList: targets, transCode: transCode},
					timeout: 300000,
					error : function(xhr, status) {
						alert("다운로드 처리 중 시스템 오류가 발생 하였습니다. 관리자에게 문의해 주세요.");
						loading.hide();
						return false;
					},
					success : function(response) {
						loading.hide();

						if (response.status == "S") {
							var fileName = response.fileName;
			               	var downloadUrl = "/comn/downloadPdfZipFile?fileName=" + encodeURIComponent(fileName);
			               	window.location.href = downloadUrl;	
						} else {
							alert("다운로드 중 오류가 발생 하였습니다. 다시 시도해 주세요.");
						}
						
					}
					
				});
			});

		var myTable = 
			$('#dynamic-table')
			.DataTable( {
				"dom": 'lt',
				"paging":   false,
		        "ordering": false,
		        "info":     false,
		        "searching": false,
		        "autoWidth": false,
		        "scrollY": 500,
				select: {
					style: 'multi',
					selector:'td:first-child'
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
					if(th_checked) {
						if(!row.children[0].firstElementChild.firstElementChild.disabled)
							myTable.row(row).select();
					}
					else  myTable.row(row).deselect();
				});
			});
		});

	</script>
	<!-- script addon end -->
</body>
</html>
