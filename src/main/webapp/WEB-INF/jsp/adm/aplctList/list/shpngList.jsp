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
	
	.cutStirng {
		text-align:left !important;
		text-overflow:ellipsis !important; 
		overflow:hidden !important; 
		white-space:nowrap !important;
	}
	
	
	 </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	</head> 
	<title>입고 작업</title>
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
								입고 작업
							</li>
							<li class="active">일반 입고 등록 리스트</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								운송장 등록 리스트
							</h1>
						</div>
						<div id="inner-content-side" >
							<form name="shpngForm" id="shpngForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
							<div id="search-div">
								<br>
								<div class="row">
									<div class="col-xs-12 col-sm-11 col-md-10">
										<span class="input-group" style="display: inline-flex; width:100%">
											<select style="height: 34px" id="searchType" name="searchType">
												<option <c:if test="${searchType eq '0' }"> selected </c:if> value="0">ALL</option>
												<option <c:if test="${searchType eq '1' }"> selected </c:if>value="1">USER ID</option>
												<option <c:if test="${searchType eq '2' }"> selected </c:if>value="2">운송 회사</option>
												<option <c:if test="${searchType eq '3' }"> selected </c:if>value="3">주문번호</option>
												<option <c:if test="${searchType eq '4' }"> selected </c:if>value="4">Hawb No</option>
												<option <c:if test="${searchType eq '5' }"> selected </c:if>value="5">우편번호</option>
											</select>
											<input type="text" class="form-control" name="keywords" value="${searchKeyword}" style="width:100%; max-width: 300px"/>
												<button id="srchKeyword" class="btn btn-default no-border btn-sm">
													<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
												</button>
										</span>
									</div>
								</div>
								<br>
								<!-- <div id="ems-excelDown" style="text-align:left;float:left;width:50%;">
									<button id="excelDown" class="btn btn-sm btn-primary">
										엑셀 다운로드 (EMS)
									</button>
								</div>  -->
								<div id="delete-user" style="text-align: right">
									<button type="button" class="btn btn-sm btn-primary" id="invPdf" style="text-transform: unset !important;">
										Commercial Invoice (Pdf)
									</button>
									<button type="button" class="btn btn-sm btn-primary" id="invExcel" style="text-transform: unset !important;">
										Commercial Invoice (Excel)
									</button>
									<button type="button" class="btn btn-sm btn-success" id="packingExcel" style="text-transform: unset !important;">
										Packing List (Excel)
									</button>
									<button id="delBtn" class="btn btn-sm btn-danger">
									 	삭제하기
									</button>
								</div>
							</div>
							<br>
							<div id="table-contents" class="table-contents"">
								<table id="dynamic-table" class="table table-bordered table-hover containerTable">
									<colgroup>
										<col width="35px"/>
										<col width="40px"/>  
										<col width="105px"/>
										<col width="101px"/>
										<col width="70px"/>
										<col width="104px"/>
										<col width="104px"/>
										<col width="156px"/>
										<col width="88px"/>
										<col width="87px"/>
										<col width="259px"/>
										<col width="41px"/>
										<col width="71px"/>
										<col width="261px"/>
										<col width="49px"/>
										<col width="43px"/>
										<col width="51px"/>
									</colgroup>
									<thead >
									<tr>
										<th class="center">
											<label class="pos-rel"> 
												<input type="checkbox" class="ace" /> 
												<span class="lbl"></span>
											</label>
										</th>
										<th class="center colorBlack">NO</th>
										<th class="center colorBlack">USER ID</th>
										<th class="center colorBlack">운송 회사</th>
										<th class="center colorBlack">도착국가</th>
										<th class="center colorBlack">주문 번호</th>
										<th class="center colorBlack">Hawb No.</th>
										<th class="center colorBlack">
											수취인
										</th>
										<th class="center colorBlack">Tel</th>
										<th class="center colorBlack">우편번호</th>
										<th class="center colorBlack">
											수취인 주소
										</th>
										<th class="center colorBlack">Box Qty</th>
										<th class="center colorBlack">실 무게(wta)<br/>부피 무게(wtc)</th>
										<th class="center colorBlack">상품명</th>
										<th class="center colorBlack">상품 <br/>개수</th>
										<th class="center colorBlack">단가</th>
										<th class="center colorBlack">Total<br/>Value</th>

									</tr>
									</thead>
									<tbody>
										<c:forEach items="${shpngList}" var="shpngAllList" varStatus="status">
											<tr
											<c:choose>
												<c:when test = "${shpngAllList.value[0].status eq 'A'}">
													style="background-color: rgb(255, 247, 247);" title="이미 입고된 주문입니다. 삭제가 불가능합니다."
												</c:when>
												<c:otherwise>
													
												</c:otherwise>
											</c:choose>
											
											>
												<td class="center">
													<label class="pos-rel"> 
														<input type="checkbox" name="nno[]" class="form-field-checkbox" value="${shpngAllList.value[0].nno}" /> 
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center colorBlack" style="word-break:break-all;">
													${status.count}
												</td>
												<td class="center colorBlack" style="word-break:break-all;">
													${shpngAllList.value[0].userId}
												</td>
												<td class="center colorBlack" style="word-break:break-all;">
													${shpngAllList.value[0].transName}
												</td>
												<td class="center colorBlack" style="word-break:break-all;">
													${shpngAllList.value[0].dstnNationName}
												</td>
												<td class="center colorBlack" style="word-break:break-all;">
														<a href="/mngr/aplctList/modifyAdminOne?transCode=${shpngAllList.value[0].transCode}&types=shpng&nno=${shpngAllList.value[0].nno}">${shpngAllList.value[0].orderNo}</a>
												</td>
												<td class="center colorBlack" style="word-break:break-all;">
													${shpngAllList.value[0].hawbNo}<br/>
													${shpngAllList.value[0].hawbNo2}
												</td>
												<td class="center colorBlack" style="word-break:break-all;">
													${shpngAllList.value[0].cneeName}<br/>
													${shpngAllList.value[0].nativeCneeName}
												</td>
												<td class="center colorBlack" style="word-break:break-all;">
													${shpngAllList.value[0].cneeTel}
												</td>
												<td class="center colorBlack" style="word-break:break-all;">
													${shpngAllList.value[0].cneeZip}
												</td>
												<td class="center colorBlack" style="word-break:break-all;">
													${shpngAllList.value[0].cneeAddr}<br/>
													${shpngAllList.value[0].nativeCneeAddr}
												</td>
												<td class="center colorBlack" >
													${shpngAllList.value[0].boxCnt}
												</td>
												<td class="center colorBlack">
													${shpngAllList.value[0].userWta}<br>
													${shpngAllList.value[0].userWtc}
												</td>
												
												<td class="center colorBlack">
													<c:forEach items="${shpngAllList.value}" var="shpngList" varStatus="status">
													<%-- <p class="cutStirng" title="${shpngList.itemDetail}"> --%>
														${shpngList.itemDetail}<br />
													<!-- </p> -->
													</c:forEach></td>
												<td class="center colorBlack">
													<c:forEach items="${shpngAllList.value}" var="shpngList" varStatus="status">
																${shpngList.itemCnt}<br />
													</c:forEach>
												</td>
												<td class="center colorBlack">
													<c:forEach items="${shpngAllList.value}" var="shpngList" varStatus="status">
																${shpngList.unitValue}<br />
													</c:forEach>
												</td>
												<td class="center colorBlack">
													<c:forEach items="${shpngAllList.value}" var="shpngList" varStatus="status">
														${shpngList.itemValue}<br />
													</c:forEach>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<input type="hidden" value="" id="delTarget" name="delTarget"/>
							<input type="hidden" value="" id="delTargetUser" name="delTargetUser"/>
						</form>
						<form name="targetNnoForm" id="targetNnoForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input type="hidden" name="targetInfos" id="targetInfos">
						</form>
					</div>
				</div>
			</div>
			<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
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
			window.onpageshow = function (event) {
				$("input:checkbox").attr("checked",false);
			}

			jQuery(function($) {
				$(document).ready(function() {
					$("#4thMenu").toggleClass('open');
					$("#4thMenu").toggleClass('active'); 
					$("#4thFiv").toggleClass('active');
					
				});
				

				$("#srchKeyword").on('click',function(e){
					$("#shpngForm").attr("action", "/mngr/aplctList/shpngList?keywords="+$("#keywords").val());    ///board/modify/로
					$("#shpngForm").attr("method", "get");            //get방식으로 바꿔서
					$("#shpngForm").submit();
				})
				
				$("#excelDown").click(function() {
					var targets = new Array();
					$("input[name='nno[]']").each(function() {
						var row = $(this).closest('tr').get(0);
						var check = this.checked;
						if (check) {
							targets.push($(this).val());
						}
					})
					
					$("#delTarget").val(targets);
					if ($("#delTarget").val() == "") {
						alert("선택된 대상이 없습니다.");
						return false;
					}

					$("#shpngForm").attr("action", "/mngr/takein/takeinEmsOrderExcelDown");
					$("#shpngForm").attr("method", "POST");
					$("#shpngForm").submit();
				})
				
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
								targets2.push(row.children[1].innerText);
							}
						});
						$("#delTarget").val(targets);
						$("#delTargetUser").val(targets2);
						var formData = $("#shpngForm").serialize();
						$("#shpngForm").attr("action", "/mngr/aplctList/deleteShpngList");    ///board/modify/로
						$("#shpngForm").attr("method", "post");            //get방식으로 바꿔서
						$("#shpngForm").submit();
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

				$('#invPdf').on('click',function(e){
					var targets = new Array();
					$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if(td_checked){
							targets.push($(this).val());
						}
					});
					window.open("/mngr/printCommercial?targetInfo="+targets+"&formType="+$("#formSelect").val(),"popForm","_blank");
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
					console.log($("#targetInfos").val());
					$("#targetNnoForm").attr("action", "/mngr/printCommercialExcel");
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
					console.log(targets);
					$("#targetNnoForm").attr("action", "/mngr/printPackingListExcel");
					$("#targetNnoForm").attr("method", "get");
					$("#targetNnoForm").submit();
					
					
				});

				
				//initiate dataTables plugin
				
				var myTable = 
				$('#dynamic-table')
				.DataTable( {
					"dom": 't',
					"paging":   false,
			        "ordering": false,
			        "info":     false,
			        "searching": false,
					select: {
						style: 'multi'
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
			})
		</script>
		<!-- script addon end -->
	</body>
</html>