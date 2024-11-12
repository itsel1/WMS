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
												<form action="/cstmr/takein/takeinInfoList" method="get" enctype="multipart/form-data">
												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
													<div style="text-align:left;float:left">
														<input  id="btnExcelSampleDown" style="padding:1.5px;" type="button" class="btn btn-white" value="Excel Form 다운로드">
													</div>
													<br>
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
																<th style="padding:0px;"><input class="searchValue" type="text" name="cusItemCode" style="width:100%" value="${parameterInfo.orderNo}"/></th>
																<th style="width:100px;" class="center colorBlack">상품명</th>
																<th style="padding:0px;"><input class="searchValue" type="text" name="itemDetail" style="width:100%" value="${parameterInfo.hawbNo}"/></th>
																<th style="width:100px;" class="center colorBlack">승인여부</th>
																<td style="padding:0px;padding-top:5px;">
																	<select name="appvYn" style="width:100%">
																		<option value="">::전체::</option>
																		<option value="Y"
																			<c:if test="${parameterInfo.appvYn == 'Y'}" >
																				selected 
																			</c:if>
																		>승인</option>
																		<option value="N"
																			<c:if test="${parameterInfo.appvYn == 'N'}" >
																				selected 
																			</c:if>
																		>미승인</option>
																	</select>
																</td>
																<th style="width:100px;" class="center colorBlack">사용여부</th>
																<td style="padding:0px;padding-top:5px;">
																	<select name="useYn" style="width:100%">
																		<option value="Y"
																			<c:if test="${parameterInfo.useYn == 'Y'}" >
																				selected 
																			</c:if>
																		>사용</option>
																		<option value="N"
																			<c:if test="${parameterInfo.useYn == 'N'}" >
																				selected 
																			</c:if>
																		>미사용</option>
																	</select>
																</td>
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
											<div style="text-align:left;float:left">
												<input type="button" class="btn-primary" value="Excel Upload" id="btnpopupTakeininfo"  >
											</div>
											<!-- fn_popupTmpTakeininfo -->
											<div style="text-align:right;float:right">
												<input type="button" class="btn-primary" value="등록" id="btnInsert"  >
											</div>
											<br>
											<table id="dynamic-table" class="table table-bordered table-striped" Style="margin-top:10px;" >
												<thead>
													<tr>
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
															브랜드
														</th>
														<th class="center colorBlack">
															상품명
														</th>
														<th class="center colorBlack">
															옵션
														</th>
														<th class="center colorBlack">
															단가
														</th>
														<th class="center colorBlack">
															개별 실무게
														</th>
														<th class="center colorBlack">
															제조사
														</th>
														<th class="center colorBlack" >
															제조국
														</th>
														<th class="center colorBlack" >
															입고 수량
														</th>
														<th class="center colorBlack" >
															출고 수량
														</th>
														<th class="center colorBlack" >
															재고 수량
														</th>
														<th class="center colorBlack" >
															관리자 승인여부
														</th>
														<th class="center colorBlack" >
															사용여부
														</th>
													</tr>
												</thead>												
												<tbody>
												<c:forEach items="${takeinList}" var="takeinfo" varStatus="status">
														<tr>
															<td style="text-align:center;font-weight:bold;">
																<a href="#" onclick="fn_popupTakeininStock('${takeinfo.orgStation}','${takeinfo.takeInCode}')">${takeinfo.cusItemCode}</a>
															</td>
															<td class="center">
																${takeinfo.stationName}
															</td>															
															<td style="color:blue;font-weight:bold;text-align:center;">${takeinfo.hsCode}</td>
															<td>${takeinfo.brand}</td>
															<td>${takeinfo.itemDetail}<br>${takeinfo.nativeItemDetail}</td>
															<td>${takeinfo.itemOption}</td>
															<td style="text-align:right;">${takeinfo.unitValue}</td>
															<td style="text-align:right;">${takeinfo.wta}</td>
															<td style="text-align:center;">${takeinfo.makeCom}</td>
															<td style="text-align:center;">${takeinfo.makeCntry}</td>
															<td style="text-align:right;">${takeinfo.whInCnt}</td>
															<td style="text-align:right;">${takeinfo.whOutCnt}</td>
															<td style="text-align:right;">${takeinfo.whInCnt - takeinfo.whOutCnt}</td>
															<td style="text-align:center;">${takeinfo.appvYn}</td>
															<td style="text-align:center;">${takeinfo.useYn}</td>
													    </tr> 	
													</c:forEach> 
												</tbody>
											</table>
											</form>
										</div>
											<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
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

		function fn_popupTakeininStock(orgStation,takeInCode){
			window.open("/cstmr/takein/popupTakeininfo?orgStation="+orgStation+"&takeInCode="+takeInCode,"PopupWin", "width=720,height=600");			
		}
	
		</script>
		
		<!-- script addon end -->
	</body>
</html>
