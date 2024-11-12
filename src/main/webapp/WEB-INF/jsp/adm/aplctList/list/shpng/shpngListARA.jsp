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
	<title>운송장 등록 리스트</title>
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
								신청서 리스트
							</li>
							<li class="active">운송장 등록 리스트</li>
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
											<select style="height: 34px">
												<option>USER ID</option>
											</select>
											<input type="text" class="form-control" name="keywords" placeholder="${searchKeyword}" style="width:100%; max-width: 300px"/>
												<button id="srchKeyword" class="btn btn-default no-border btn-sm">
													<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
												</button>
										</span>
									</div>
								</div>
								<br>
								<div id="delete-user" style="text-align: right">
									<button id="delBtn" class="btn btn-sm btn-danger">
									 	삭제하기
									</button>
								</div>
							</div>
							<br>
							<div id="table-contents" class="table-contents">
								<table id="dynamic-table" class="table table-bordered table-hover containerTable">
									<%-- <colgroup>
										<col width="1%"/>
										<col width="9.8%"/>
										<col width="5%"/>
										<col width="9.5%"/>
										<col width="9%"/>
										<col width="6%"/>
										<col width="5%"/>
										<col width=""/>
										<col width="2%"/>
										<col width="2%"/>
										<col width="4%"/>
										<col width="10%"/>
										<col width="2%"/>
										<col width="2%"/>
										<col width="2%"/>
									</colgroup> --%>
									<thead >
											<tr>
												<th class="center">
													<label class="pos-rel"> 
														<input type="checkbox" class="ace" /> 
														<span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack" style="width:160px !important">비고</th>
												<th class="center colorBlack">USER ID</th>
												<th class="center colorBlack">주문 번호</th>
												<th class="center colorBlack">
													수취인(Jpn)<br>
													수취인(Eng)
												</th>
												<th class="center colorBlack">Tel</th>
												<th class="center colorBlack">우편번호</th>
												<th class="center colorBlack">
													수취인 주소(Jpn)<br>
													수취인 주소(Eng)
												</th>
												<th class="center colorBlack">Box Qty</th>
												<th class="center colorBlack">실 무게</th>
												<th class="center colorBlack">
													width(Cm)<br>
													length(Cm)<br>
													Height(Cm)
												</th>
												<th class="center colorBlack">상품명(Eng)</th>
												<th class="center colorBlack">상품 <br/>개수</th>
												<th class="center colorBlack">단가</th>
												<th class="center colorBlack">Total<br/>Value</th>
	
											</tr>
										</thead>
										<tbody >
											<c:forEach items="${shpngList}" var="shpngAllList" varStatus="status">
												<tr>
													<td class="center">
														<label class="pos-rel"> 
															<input type="checkbox" class="form-field-checkbox" value="${shpngAllList.value[0].nno}" /> 
															<span class="lbl"></span>
														</label>
													</td>
													<td class="center colorBlack">
														<c:choose>
															<c:when test = "${shpngAllList.value[0].status eq 'A'}">
																<span class="red">
																	이미 입고된 주문 입니다.<br/>삭제가 불가능 합니다.
																</span>
															</c:when>
															<c:otherwise>
																
															</c:otherwise>
														</c:choose>
														<%-- <c:if test="${shpngAllList.value[0].status eq 'A'}">
															<span class="red errorCol"> 이미 등록된 주문번호 입니다. </span>
														</c:if> 
														<c:if test="${empty shpngAllList.value[0].nativeCneeAddr}">
															<span class="red errorCol"> 
																수취인 주소(Jpn)를 확인해 주세요.<br />
															</span>
														</c:if> 
														<c:if test="${empty shpngAllList.value[0].cneeAddr}">
															<span class="red errorCol"> 
																수취인 주소(Eng)를 확인해 주세요.<br />
															</span>
														</c:if> 
														<c:if test="${empty shpngAllList.value[0].nativeCneeName}">
															<span class="red errorCol"> 
																수취인(Jpn)를 확인해 주세요<br />
															</span>
														</c:if> 
														<c:if test="${empty shpngAllList.value[0].cneeName}">
															<span class="red errorCol"> 
																수취인(Eng)를 확인해 주세요<br />
															</span>
														</c:if> 
														<c:if test="${empty shpngAllList.value[0].cneeTel}">
															<span class="red errorCol"> 
																Tel를 확인해 주세요<br />
															</span>
														</c:if> 
														<c:if test="${empty shpngAllList.value[0].cneeZip}">
															<span class="red errorCol"> 	
																우편번호 를 확인해 주세요<br />
															</span>
														</c:if> 
														<c:if test="${shpngAllList.value[0].boxCnt eq '0'}">
															<span class="red errorCol"> 
																BoxQty 를 확인해 주세요<br />
															</span>
														</c:if> 
														<c:if test="${shpngAllList.value[0].userWidth eq '0.0'}">
															<span class="red errorCol"> 
																width(Cm)를 확인해 주세요<br />
															</span>
														</c:if> 
														<c:if test="${shpngAllList.value[0].userLength eq '0.0'}">
															<span class="red errorCol"> 
																Length(Cm)를 확인해 주세요<br />
															</span>
														</c:if> 
														<c:if test="${shpngAllList.value[0].userHeight eq '0.0'}">
															<span class="red errorCol"> 
																Height(Cm)를 확인해 주세요<br />
															</span>
														</c:if> 
														<c:if test="${shpngAllList.value[0].userWta eq '0.0'}">
															<span class="red errorCol"> 
																실 무게를 확인해 주세요<br />
															</span>
														</c:if> 
														<c:forEach items="${shpngAllList.value}" var="shpngList" varStatus="status">
															<c:if test="${empty shpngList.itemDetail}">
																<span class="red errorCol"> 상품명를 확인해 주세요<br />
																</span>
															</c:if>
															<c:if test="${empty shpngList.itemMeterial}">
																<span class="red errorCol"> 상품 재질를 확인해 주세요<br />
																</span>
															</c:if>
															<c:if test="${shpngList.itemCnt eq '0'}">
																<span class="red errorCol"> 상품 개수를 확인해 주세요<br />
																</span>
															</c:if>
															<c:if test="${shpngList.unitValue eq '0.0'}">
																<span class="red errorCol"> 단가를 확인해 주세요<br />
																</span>
															</c:if>
															<c:if
																test="${shpngList.itemValue ne (shpngList.itemCnt * shpngList.unitValue)}">
																<span class="red errorCol"> Total value가 다릅니다.<br />
																</span>
															</c:if>
														</c:forEach> --%>
													</td>
													<td class="center colorBlack">
														${shpngAllList.value[0].userId}
													</td>
													<td class="center colorBlack">
														<a href="/mngr/aplctList/modifyAdminOne?types=shpng&nno=${shpngAllList.value[0].nno}">${shpngAllList.value[0].orderNo}</a>
													</td>
													<td class="center colorBlack">
														${shpngAllList.value[0].nativeCneeName}<br>
														${shpngAllList.value[0].cneeName}
													</td>
													<td class="center colorBlack">
														${shpngAllList.value[0].cneeTel}
													</td>
													<td class="center colorBlack">
														${shpngAllList.value[0].cneeZip}
													</td>
													<td class="center colorBlack">
														${shpngAllList.value[0].nativeCneeAddr}<br>
														${shpngAllList.value[0].cneeAddr}
													</td>
													<td class="center colorBlack">
														${shpngAllList.value[0].boxCnt}
													</td>
													<td class="center colorBlack">
														${shpngAllList.value[0].userWta}
													</td>
													<td class="center colorBlack">
														${shpngAllList.value[0].userWidth}<br>
														${shpngAllList.value[0].userLength}<br>
														${shpngAllList.value[0].userHeight}
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
					$("#4thMenu").toggleClass('open');
					$("#4thMenu").toggleClass('active'); 
					$("#4thFiv").toggleClass('active');
				});

				$("#srchKeyword").on('click',function(e){
					$("#shpngForm").attr("action", "/mngr/aplctList/shpngList?keywords="+$("#keywords").val());    ///board/modify/로
					$("#shpngForm").attr("method", "get");            //get방식으로 바꿔서
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
								targets2.push(row.children[2].innerText);
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

				
				//initiate dataTables plugin
				
				var myTable = 
				$('#dynamic-table')
				.DataTable( {
					"dom": 't',
					"paging":   false,
			        "ordering": false,
			        "info":     false,
			        "searching": false,
			        "scrollY" : 500,
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
