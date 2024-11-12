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
	<title>검품 입출고 내역</title>
	<body class="no-skin"> 
		<!-- headMenu Start -->
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		<!-- Main container Start-->
		<div class="toppn">
	      <div class="container">
	        <div class="row">
	          <div class="col-md-12 mb-0" style="font-size:14px !important;"><a>검품</a> <span class="mx-2 mb-0">/
	          </span> <strong class="text-black">검품 입출고 내역</strong></div>
	        </div>
	      </div>
	    </div>

	    
	    <!-- Main container Start-->
		     <div class="container">
		       <div class="page-content noneArea">
						<div class="page-header noneArea">
								
						</div>
									<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;">
										<div class="display" >
											<form id="list_form" action="" method="post">
												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
											<br>
											<table id="dynamic-table" class="table table-bordered table-striped" style="max-width:850px;" >
												<thead>
														<tr>
															<th class="center" Style="width:80px;">Station</th>
															<td class="center colorBlack" Style="width:80px;">
																${list[0].stationName}
															</td>
															<th class="center" Style="width:80px;">Order No</th>
															<td class="center colorBlack" Style="width:170px;">
																${list[0].orderNo}
															</td>
															<th class="center" Style="width:80px;">Hawb No</th>
															<td class="center colorBlack" Style="width:180px;">
																${list[0].hawbNo}
															</td>
															<th class="center" Style="width:80px;">수취인 명</th>
															<td class="center colorBlack" Style="width:180px;">
																${list[0].cneeName}
															</td>
														</tr>	
												</thead>
											</table>
											<table id="dynamic-table" class="table table-bordered table-striped" Style="margin-top:10px;" >
												<thead>
													<tr>
														<th class="center colorBlack" rowspan="2">
															상품코드
														</th>
														<th class="center colorBlack" rowspan="2">
															상품명
														</th>
														
														<th class="center colorBlack" rowspan="2">
															요청 수량
														</th>
														<th class="center colorBlack" rowspan="2">
															입고 수량
														</th>
														<th class="center colorBlack" rowspan="2">
															입고일
														</th>											
														<th class="center colorBlack" colspan="4">
															입고 상세
														</th>
														<th class="center colorBlack" rowspan="2">
															출고 송장
														</th>
														<th class="center colorBlack" colspan="4">
															공급사 정보
														</th>
													</tr>
													<tr>
														<th class="center colorBlack">
															정상
														</th>
														<th class="center colorBlack" >
															검수불합격
														</th>
														<th class="center colorBlack" >
															반품
														</th>
														<th class="center colorBlack" >
															폐기
														</th>
														<th class="center colorBlack">
															공급사 코드
														</th>
														<th class="center colorBlack" >
															공급사 명
														</th>
														<th class="center colorBlack" >
															공급사 연락처
														</th>
														<th class="center colorBlack" >
															공급사 주소
														</th>
													</tr>
												</thead>												
												<tbody>
												 <c:forEach items="${list}" var="list" varStatus="status">
														<tr>
															<td class="center">
																${list.cusItemCode}
															</td>	
															<td class="center">
																${list.itemDetail}
															</td>
															<td class="center">
																${list.itemCnt}
															</td>
															<td class="center">
																${list.whCnt}
															</td>
															<td class="center">
																${list.maxWhDate}
															</td>
															
															<td style="text-align:right">
																${list.woCnt}
															</td>
															<td style="text-align:right">
																${list.wfCnt}
															</td>
															<td style="text-align:right">
																${list.rtCnt}
															</td>
															<td style="text-align:right">
																${list.trCnt}
															</td>
															<td class="center">
																${list.outHawbNo}
															</td>
															<td class="center">
																${list.supplierCode}
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
	</body>
</html>
