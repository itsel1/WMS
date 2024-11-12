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
							<div id="table-contents" class="table-contents" style="width:1600 px; overflow: auto;">
								<div class="display" style="width:2600px;">
									<table id="dynamic-table" class="table table-bordered table-hover">
											<colgroup>
												<col />
											</colgroup>
											<thead>
												<tr>
													<th class="center">
														<label class="pos-rel">
															<input type="checkbox" class="ace"/>
															<span class="lbl"></span>
														</label>
													</th>
													<th class="center colorBlack">
														비고
													</th>
													<th class="center colorBlack">
														User ID
													</th>
													<th class="center colorBlack">
														주문 번호
													</th>
													<th class="center colorBlack">
														Hawb No.
													</th>
													<th class="center colorBlack">
														거래처 명
													</th>
													<th class="center colorBlack">
														주문 일자
													</th>
													<th class="center colorBlack">
														수취인
													</th>
													<th class="center colorBlack">
														Tel
													</th>
													<th class="center colorBlack">
														우편번호
													</th>
													<th class="center colorBlack">
														수취인 주소
													</th>
													<th class="center colorBlack">
														상세
													</th>
													<th class="center colorBlack">
														HS CODE
													</th>
													<th class="center colorBlack">
														상품명
													</th>
													<th class="center colorBlack">
														단가
													</th>
													<th class="center colorBlack">
														수량
													</th>
													<th class="center colorBlack">
														금액
													</th>
													
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${inspList}" var="inspAllList" varStatus="status">
													<tr>
														<td class="center">
															<label class="pos-rel">																
																<input type="checkbox" class="form-field-checkbox"/>
																<span class="lbl"></span>
															</label>
														</td>
														<td class="center colorBlack">
														<input type="hidden" value="${inspAllList.value[0].nno}" id="targetList" name="targetList"/>
														<c:choose>
															<c:when test = "${inspAllList.value[0].status eq 'A'}">
																<span class="red">
																	이미 등록된 주문번호 입니다.
																</span>
															</c:when>
															<c:otherwise>
																
															</c:otherwise>
														</c:choose>
														
														</td>
														<td class="center colorBlack" >
															<c:choose>
																	<c:when test = "${inspAllList.value[0].status eq 'A'}">
																		<span class="red">${inspAllList.value[0].userId}</span>
																	</c:when>
																	<c:otherwise>
																		${inspAllList.value[0].userId}
																	</c:otherwise>
																</c:choose>
														</td>
														<td class="center colorBlack" >
															<a href="/mngr/aplctList/modifyAdminOne?types=insp&nno=${inspAllList.value[0].nno}">${inspAllList.value[0].orderNo}</a>
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].hawbNo}
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].companyName}
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].orderDate}
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].cneeName}
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].cneeTel}
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].cneeZip}
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].cneeAddr}
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].cneeAddrDetail}
														</td>
														<td class="center colorBlack" >
															<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																${fn:length(inspAllList.value)}
																${inspList.hsCode}<br/>
															</c:forEach>
														</td>
														<td class="center colorBlack">
															<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																${inspList.itemDetail}<br/>
															</c:forEach>
														</td>
														<td class="center colorBlack">
															<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																${inspList.unitValue}<br/>
															</c:forEach>
														</td>
														<td class="center colorBlack">
															<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																${inspList.itemCnt}<br/>
															</c:forEach>
														</td>
														<td class="center colorBlack">
															<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																${inspList.itemValue}<br/>
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
					$("#12thMenu").toggleClass('open');
					$("#12thMenu").toggleClass('active'); 
					$("#12thThr").toggleClass('active');
				});

				$("#delBtn").on('click',function(e){
					

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
