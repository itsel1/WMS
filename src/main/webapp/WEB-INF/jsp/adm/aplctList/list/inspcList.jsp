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
	<!-- ace scripts -->
	
	</head> 
	<title>검품 등록 리스트</title>
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
							<li class="active">검품 등록 리스트</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								검품 등록 리스트
							</h1>
						</div>
						<div id="inner-content-side" >
							<form name="inspForm" id="inspForm">
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
								<div id="delete-user" style="text-align:right;margin-right:28px;">
									<button id="delBtn" class="btn btn-sm btn-danger">삭제하기</button>
								</div>
							</div>
							<div id="table-contents" class="table-contents" style="width:100%; overflow: auto; margin-top:8px;">
								<div class="display" style="width:100%;">
									<table id="dynamic-table" class="table table-bordered table-hover">
										<thead>
											<tr>
												<th class="center" style="width:3%;">
													<label class="pos-rel">
														<input type="checkbox" class="ace"/>
														<span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack" style="width:7%;">USER ID</th>
												<th class="center colorBlack" style="width:7%;">운송회사</th>
												<th class="center colorBlack" style="width:5%;">도착국가</th>
												<th class="center colorBlack" style="width:7%;">주문번호</th>
												<th class="center colorBlack" style="width:7%;">HAWB NO</th>
												<th class="center colorBlack" style="width:5%;">수취인</th>
												<th class="center colorBlack" style="width:5%;">Tel</th>
												<th class="center colorBlack" style="width:5%;">우편번호</th>
												<th class="center colorBlack" style="width:10%;">수취인 주소</th>
												<th class="center colorBlack" style="width:3%;">Box</th>
												<th class="center colorBlack" style="width:7%;">W/T(A) / W/T(C)</th>
												<th class="center colorBlack" style="width:10%;">상품명</th>
												<th class="center colorBlack" style="width:5%;">상품 개수</th>
												<th class="center colorBlack" style="width:7%;">단가</th>
												<th class="center colorBlack" style="width:7%;">Total</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${inspList}" var='inspAllList' varStatus="status">
												<tr
													<c:choose>
														<c:when test="${inspAllList.value[0].status eq 'A'}">
															style="background-color:rgb(255, 247, 247);"
														</c:when>
														<c:otherwise>
														</c:otherwise>
													</c:choose>
												>
													<td class="center">
														<label class="pos-rel"> 
															<input type="checkbox" class="form-field-checkbox" value="${inspAllList.value[0].nno}" /> 
															<span class="lbl"></span>
														</label>
													</td>
													<td class="center colorBlack" style="word-break:break-all;">
														${inspAllList.value[0].userId}
													</td>
													<td class="center colorBlack" style="word-break:break-all;">
														${inspAllList.value[0].transName}
													</td>
													<td class="center colorBlack" style="word-break:break-all;">
														${inspAllList.value[0].dstnNationName}
													</td>
													<td class="center colorBlack" style="word-break:break-all;">
														<a href="/mngr/aplctList/modifyAdminOne?transCode=${inspAllList.value[0].transCode}&types=insp&nno=${inspAllList.value[0].nno}">${inspAllList.value[0].orderNo}</a>
													</td>
													<td class="center colorBlack" style="word-break:break-all;">
														${inspAllList.value[0].hawbNo}
													</td>
													<td class="center colorBlack" style="word-break:break-all;">
														${inspAllList.value[0].cneeName}
													</td>
													<td class="center colorBlack" style="word-break:break-all;">
														${inspAllList.value[0].cneeTel}
													</td>
													<td class="center colorBlack" style="word-break:break-all;">
														${inspAllList.value[0].cneeZip}
													</td>
													<td class="center colorBlack" style="word-break:break-all;">
														${inspAllList.value[0].cneeAddr} ${inspAllList.value[0].cneeAddrDetail} 
													</td>
													<td style="text-align:right;padding-right:10px;">
														${inspAllList.value[0].boxCnt}
													</td>
													<td class="center colorBlack">
														${inspAllList.value[0].userWta} / 
														${inspAllList.value[0].userWtc}
													</td>
													
													<td class="center colorBlack">
														<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
														<%-- <p class="cutStirng" title="${shpngList.itemDetail}"> --%>
															${inspList.itemDetail}<br />
														<!-- </p> -->
														</c:forEach></td>
													<td style="text-align:right;padding-right:10px;">
														<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																${inspList.itemCnt}<br />
														</c:forEach>
													</td>
													<td style="text-align:right;padding-right:10px;">
														<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																${inspList.unitValue}<br />
														</c:forEach>
													</td>
													<td style="text-align:right;padding-right:10px;">
														<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
															${inspList.itemValue}<br />
														</c:forEach>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
							<input type="hidden" value="" id="delTarget" name="delTarget"/>
							<input type="hidden" value="" id="delTargetUser" name="delTargetUser"/>
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
					$("#12thMenu").toggleClass('open');
					$("#12thMenu").toggleClass('active'); 
					$("#12thThr").toggleClass('active');
				});

				$("#delBtn").on('click',function(e){
					if(confirm("삭제하시겠습니까?")){
						var targets = new Array();
						
						$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
							var row = $(this).closest('tr').get(0);
							var td_checked = this.checked;
							if(td_checked){
								targets.push($(this).val());
								/* if(row.children[1].childElementCount == 1){
									targets.push(row.children[1].firstElementChild.value);
								} */
							}
						});
						console.log(targets);
						
						var targets2 = new Array();
						$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
							var row = $(this).closest('tr').get(0);
							var td_checked = this.checked;
							if(td_checked){
								targets2.push(row.children[1].innerText);
								/* if(row.children[1].childElementCount == 1){
									targets2.push(row.children[2].innerText);
								} */
							}
						});
						$("#delTarget").val(targets);
						$("#delTargetUser").val(targets2);
						console.log(targets);
						console.log(targets2);
						var formData = $("#inspForm").serialize();
						$("#inspForm").attr("action", "/mngr/aplctList/deleteinspList");    ///board/modify/로
						$("#inspForm").attr("method", "post");            //get방식으로 바꿔서
						$("#inspForm").submit();
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
