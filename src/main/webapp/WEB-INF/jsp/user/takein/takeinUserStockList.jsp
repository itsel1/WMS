<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
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
	</style>
	<!-- basic scripts -->
	</head> 
	<title>상품정보</title>
	<body class="no-skin"> 
		<!-- headMenu Start -->
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		<!-- Main container Start-->
		<div class="toppn">
	      <div class="container">
	        <div class="row">
	          <div class="col-md-12 mb-0" style="font-size:14px !important;"><a>상품정보</a> <span class="mx-2 mb-0">/
	          </span> <strong class="text-black">상품정보</strong></div>
	        </div>
	      </div>
	    </div>

	    
	    <!-- Main container Start-->
		     <div class="container">
		       <div class="page-content noneArea">
						<div class="page-header noneArea">
							<h3>
								상품정보
							</h3>
						</div>
							<div id="inner-content-side" >
									<br>
									<div class="col-xs-12 form-group" style="margin:0px;">
											<div class="row">											
												<form action="/cstmr/takein/takeinUserStockList" method="get" enctype="multipart/form-data">
												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
													<div style="text-align:left;float:left">
														<!-- <input  id="btnExcelSampleDown" style="padding:1.5px;" type="button" class="btn btn-white" value="Excel Form 다운로드"> -->
													</div>
													<br>
													<table  class="table table-bordered" style="max-width:1200px;float:left;">
														<thead>
															<tr>
																<th style="width:100px;" class="center colorBlack">Station</th>
																<td style="padding:0px;padding-top:5px;">
																<select name="orgStation" style="width:100%">
																	<option value=""
																			<c:if test="${parameterInfo.orgStation == ''}" >
																				selected 
																			</c:if>
																		>::전체::</option>
																	<c:forEach items="${orgStation}" var="orgStation" varStatus="status">
																		<option value="${orgStation.stationCode}"
																			<c:if test="${parameterInfo.orgStation == orgStation.stationCode}" >
																				selected 
																			</c:if>
																			>${orgStation.stationName}</option>
																	</c:forEach>
																</select> 
																</td>
																<th style="width:100px;" class="center colorBlack">상품 코드</th>
																<th style="padding:0px;"><input class="searchValue" type="text" name="cusItemCode" style="width:100%" value="${parameterInfo.cusItemCode}"/></th>
																<th style="width:100px;" class="center colorBlack">상품명</th>
																<th style="padding:0px;"><input class="searchValue" type="text" name="itemDetail" style="width:100%" value="${parameterInfo.itemDetail}"/></th>
																<th class="center" ><input style="width:100%;padding:0px;margin:0px;" class="btn-primary" type="submit" value="조회" id="btn_search"/></th>
															</tr>
														</thead>
													</table>
												</form>
											</div>
									</div>
							</div>
									<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;">
										<div class="display" >
											<form id="list_form" action="" method="post">
												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
											<br>
											<table id="dynamic-table" class="table table-bordered table-striped" Style="margin-top:10px;" >
												<thead>
													<tr>
														<th class="center colorBlack">
															입고일
														</th>
														<th class="center colorBlack">
															상품 코드
														</th>
														<th class="center colorBlack">
															Station
														</th>
														<th class="center colorBlack">
															HsCode
														</th>
														<th class="center colorBlack">
															상품명
														</th>
														<th class="center colorBlack">
															옵션
														</th>
														<th class="center colorBlack">
															수량단위
														</th>
														<th class="center colorBlack">
															유통기한
														</th>													
														<th class="center colorBlack" >
															입고수량
														</th>
														<th class="center colorBlack" >
															출고수량
														</th>
														<th class="center colorBlack" >
															재고수량
														</th>
														<th class="center colorBlack" >
															검수자
														</th>
													</tr>
												</thead>												
												<tbody>
												 <c:forEach items="${takeinUserStockList}" var="takeinUserStock" varStatus="status">
														<tr>
															<td class="center">
																${takeinUserStock.whInDate}
															</td>	
															<td class="center">
																${takeinUserStock.cusItemCode}
															</td>															
															<td class="center">
																${takeinUserStock.orgStationName}
															</td>
															<td class="center">
																${takeinUserStock.hsCode}
															</td>
															<td class="center">
																${takeinUserStock.itemDetail}
															</td>
															<td class="center">
																${takeinUserStock.itemOption}
															</td>
															<td class="center">
																${takeinUserStock.qtyUnit}
															</td>
															<td class="center">
																${takeinUserStock.mnDate}
															</td>
															<td style="text-align:right;">
																<%-- ${takeinUserStock.whInCnt} --%>
																<a href="#" style="font-weight:bold;" 
																   onclick="fn_takeinStockList('${takeinUserStock.orgStation}','${takeinUserStock.userId}','${takeinUserStock.groupIdx}')">${takeinUserStock.whInCnt}</a>
															</td>
															<td  style="text-align:right;">
																${takeinUserStock.whOutCnt}
															</td>
															<td style="text-align:right;">
																${takeinUserStock.whInCnt-takeinUserStock.whOutCnt}
															</td>
															<td class="center">
																${takeinUserStock.inSpector}
															</td>
													    </tr> 	 
													</c:forEach> 
												</tbody>
											</table>
											</form>
										</div>
									</div>
							</div>
					</div>
				</div>

		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
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
		<!-- script on paging end -->
		
		<!-- script addon start -->
		
		<script type="text/javascript">
	    $(document).ready(function(){
			
		    $("#chkAll").click(function() {
	            if($("#chkAll").prop("checked")){ //해당화면에 전체 checkbox들을 체크해준다
	                $("input[name='nno[]']").prop("checked",true); // 전체선택 체크박스가 해제된 경우
	            } else { //해당화면에 모든 checkbox들의 체크를해제시킨다.
	                $("input[name='nno[]'").prop("checked",false);
	            }
	        });

		    $("#btnExcelSampleDown").on('click',function(e){
				window.open("/takein/apply/takeinfoTmpexcelFormDown")
			})
		
		    $(".searchValue").click(function() {
		    	  if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
			            alert('test');
			        }
		    });
		
		    $("#btnInsert").click(function() {
		    	window.open("/cstmr/takein/popupTakeininfo","PopupWin", "width=720,height=600");
		    });

		    $("#btnpopupTakeininfo").click(function() {
		    	window.open("/cstmr/takein/popupTmpTakeininfo","PopupWin", "width=1820,height=800");
		    });

	    });

		function fn_takeinStockList(orgStation,userId,groupIdx){
			window.open("/cstmr/takein/popupTakeinStockList?orgStation="+orgStation+"&userId="+userId+"&groupIdx="+groupIdx,"PopupWin", "width=1820,height=600");
		}
		

		function fn_popupTakeininStock(orgStation,takeInCode){
			window.open("/cstmr/takein/popupTakeininfo?orgStation="+orgStation+"&takeInCode="+takeInCode,"PopupWin", "width=720,height=600");			
		}
	
		</script>
		
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#forthMenu").toggleClass('open');
					$("#forthMenu").toggleClass('active'); 
					$("#4thOne").toggleClass('active');
				});
		</script>
		<!-- script addon end -->
	</body>
</html>
