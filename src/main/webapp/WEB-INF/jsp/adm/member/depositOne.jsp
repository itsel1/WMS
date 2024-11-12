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
	<title>사용자 관리</title>
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
								거래처 관리
							</li>
							<li class="active">예치금 관리</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								예치금 내역
							</h1>
						</div>
						<div id="inner-content-side" >
							<form name="search-button" id="search-botton" method="get" action="/mngr/acnt/depositList">
								<br>
								<h2>${userInfo.USER_ID}</h2>
								<h3><font color="red">예치금 잔액 : ${userInfo.DEPOSIT_COST}</font></h3>
								<div id="table-contents" class="table-contents">
									<table id="dynamic-table" class="table table-bordered table-hover" style="width:100%">
										<colgroup>
											<col width="3%"/>
											<col width="15%"/>
											<col width="10%"/>
											<col width="10%"/>
											<col/>
											<col/>
											<col/>
											<col/>
											<col/>
											<col/>
											<col/>
											<col/>
											<col/>
											<col/>
											<col width="8%"/>  
										</colgroup>
										<thead>
											<tr>
												<th class="center colorBlack" >No.</th>
												<th class="center colorBlack" >REG NO</th>
												<th class="center colorBlack" >반송 송장번호</th>
												<th class="center colorBlack" >등록일</th>
												<th class="center colorBlack" >예치금 증액</th>
												<th class="center colorBlack" >예치금 감액</th>
												<th class="center colorBlack" >검수 비용</th>
												<th class="center colorBlack" >추가 비용</th>
												<th class="center colorBlack" >합포장 비용</th>
												<th class="center colorBlack" >사진추가 비용</th>
												<th class="center colorBlack" >부자재 비용</th>
												<th class="center colorBlack" >폐기 비용</th>
												<th class="center colorBlack" >배송 비용</th>
												<th class="center colorBlack" >기타 비용</th>
												<th class="center colorBlack" >비고</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${depositHis}" var="test" varStatus="rtnStatus">
												<tr>
													<td class="center" style="word-break:break-all;">
														${rtnStatus.count }
													</td>
													<td class="center"style="word-break:break-all;">
														<c:choose>
															<c:when test="${test.A001 ne 0}">
																예치금 입금
															</c:when>
															<c:when test="${test.D001 ne 0}">
																예치금 단순 차감
															</c:when>
															<c:otherwise>
																${test.REG_NO}	
															</c:otherwise>
														</c:choose>
														
													</td>
													<td class="center"style="word-break:break-all;">
														${test.RE_TRK_NO}
													</td>
													<td class="center"style="word-break:break-all;">
														${test.W_DATE}
													</td>
													<td class="center"style="word-break:break-all;">
														${test.A001}
													</td>													
													<td class="center"style="word-break:break-all;">
														${test.D001}
													</td>				
													<td class="center"style="word-break:break-all;">
														${test.B001}
													</td>				
													<td class="center"style="word-break:break-all;">
														${test.B002}
													</td>
													<td class="center"style="word-break:break-all;">
														${test.B004}
													</td>
													<td class="center"style="word-break:break-all;">
														${test.B005}
													</td>
													<td class="center"style="word-break:break-all;">
														${test.B006}
													</td>				
													<td class="center"style="word-break:break-all;">
														${test.B003}
													</td>				
													<td class="center"style="word-break:break-all;">
														${test.C001}
													</td>				
													<td class="center"style="word-break:break-all;">
														${test.B007}
													</td>
													<td class="center"style="word-break:break-all;">
														${test.REMARK}
													</td>
												</tr>
											</c:forEach>
										</tbody>
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
					$("#14thMenu-2").toggleClass('open');
					$("#14thMenu-2").toggleClass('active'); 
					$("#14thTwo-5").toggleClass('active'); 

					$("[name=depositCostDiv]").each(function(){
						var money = $(this).text();
						var money2 = money.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
						$(this).text(money2);
					});
					
					
					
					/* $("#getSelectIndvdList").toggleClass('active'); */
					
				});
				
				//initiate dataTables plugin
				
				
				
				var myTable = 
				$('#dynamic-table')
				.DataTable( {
					"dom" : "t",
					"paging":   false,
			        "ordering": false,
			        "info":     false,
			        "searching": false,
			        "autoWidth": false,
					"language":{
						"zeroRecords": "조회 결과가 없습니다.",
					}
			    } );
			});
			function addDeposit(userId){
				$("#costValue").val($("#costValue"+userId).val())
				$("#depositId").val(userId)
				$("#calculate").val("A")
				$("#search-botton").attr("action", "/mngr/aplctList/return/depositListp");
				$("#search-botton").attr("method", "post");            
				$("#search-botton").submit();
				
			};

			function minusDeposit(userId){
				$("#costValue").val($("#costValue"+userId).val())
				$("#depositId").val(userId)
				$("#calculate").val("B")
				$("#search-botton").attr("action", "/mngr/aplctList/return/depositListp");
				$("#search-botton").attr("method", "post");
				$("#search-botton").submit();
			};
			
			
		</script>
		<!-- script addon end -->
	</body>
</html>
