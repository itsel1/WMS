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
	<title>검품배송 신청서</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		<!-- Main container Start-->
		<div class="toppn">
	      <div class="container">
	        <div class="row">
	          <div class="col-md-12 mb-0" style="font-size:14px !important;"><a>사입주문 등록</a> <span class="mx-2 mb-0">/
	          </span> <strong class="text-black">사입주문 등록</strong></div>
	        </div>
	      </div>
	    </div>
	    <!-- Main container Start-->
		     <div class="container">
		       <div class="page-content noneArea">
						<div class="page-header noneArea">
							<h3>
								사입 등록
							</h3>
						</div>
							<div id="inner-content-side" >
								
									<div class="col-xs-12 form-group" style="margin:0px;">
											<div class="row">
												</br>
												<form action="/cstmr/takein/takeinExcelUpload" method="post" enctype="multipart/form-data">
												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
													<table style="width:100%">
														<tbody>
															<tr>
																<td style="width:350px;">
																	<input type="file" name="excelFile">
																</td>
																<td style="width:200px;">
																	<input type="submit" value="Excel 등록">
																</td>
															</tr>
														</tbody>	
													</table>
												</form>
											</div>
									</div>
							</div>
									<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;">
										<div class="hr hr-8 dotted"></div>
										<div class="space-20"></div>
										<div class="display" >
											<form id="list_form" action="" method="post">
												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
											<div style="text-align:left;float:left">
												<input type="button" value="등록" id="btn_reg">
												<!-- <input type="button" value="삭제" id="btn_del"> -->
											</div>
											<div style="text-align:right;float:right">
												<input type="button" value="삭제" id="btn_del">
											</div>
											<table id="dynamic-table" class="table table-bordered table-striped" >
												<thead>
													<tr>
														<th class="center">
															<label class="pos-rel">
																<input type="checkbox" class="ace" id="chkAll"/>
																<span class="lbl"></span>
															</label>
														</th>
														<th class="center colorBlack">
															주문 번호
														</th>
														<th class="center colorBlack">
															주문 일자
														</th>
														<th class="center colorBlack">
															Station
														</th>
														<th class="center colorBlack">
															도착국가
														</th>
														<th class="center colorBlack">
															수취인명
														</th>
														<th class="center colorBlack">
															연락처
														</th>
														<th class="center colorBlack" colspan=2>
															Addr
														</th>
													</tr>
												</thead>												
												<tbody>
													<c:forEach items="${takeinList.orderinfo}" var="takeorderinfo" varStatus="status">
														<tr>
															<%-- <td style="text-align: center">
																<input type="checkbox" name="nno[]" value="${takeorderinfo.nno}">
															</td> --%>
															<td>${takeorderinfo.orderNo}</td>
															<%-- <td>${takeorderinfo.orderDate}</td>
															<td>${takeorderinfo.orgStationName}</td>
															<td>${takeorderinfo.dstnNationName}</td>
															<td>${takeorderinfo.cneeName}</td>
															<td>Tel : ${takeorderinfo.cneeTel}
																</br>
																Hp : ${takeorderinfo.cneeHp}
																</br>
																E-mail :
															</td>
															<td style="overflow: auto;">
																<span>${takeorderinfo.cneeState}</span>
																<span>${takeorderinfo.cneeCity}</span>
																</br>
																<span style="font-weight:bold">
																	${takeorderinfo.cneeZip}
																</span>
																</br>
																<span>
																	${takeorderinfo.cneeAddr}
																</span>
																<span>
																	${takeorderinfo.cneeAddrDetail}
																</span>
															</td> --%>
														</tr>
														<tr>
															<td colspan=9 style="padding-left:600px;">
																<table class="" style="width:100%">
																	<tr>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">hscode</th>
																		<th rowspan="2" class="left colorBlack" style="min-width:90px;">itemDetail</th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">단가</th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">수량</th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">금액</th>
																		<th rowspan="1" class="center colorBlack" style="width:80px;"> total 금액</th>
																	</tr>
																	<tr>
																		<th class="center colorBlack" style="width:80px;" rowspan="100">${takeorderinfo.userId}</th>
																	</tr>
																<c:forEach items="${takeinList.orderItem}" var="itemDetail" varStatus="status">
																	<tr>
																		<td style="text-align:center;color:blue;font-weight:bold;">${itemDetail}</td>
																	</tr>
																</c:forEach>
																</table>
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

		    $("#btn_del").click(function() {
		        if(!confirm('정말 삭제 하시겠습니까?')){
	                return false;
	            }
		        $("#actWork").attr('value','Del');
		        $("#list_form").attr('action','/cstmr/takein/takeinDel');
	            $("#list_form").submit();
		    });

	    });
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
