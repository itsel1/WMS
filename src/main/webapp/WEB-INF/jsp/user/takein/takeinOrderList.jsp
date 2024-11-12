<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="shortcut icon" type="image/x-icon" href="/image/favicon/logo_icon.ico" />
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	<%@ include file="/WEB-INF/jsp/importFile/date.jsp" %>
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
	<title>사입 등록 내역</title>
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
								사입 등록 내역
							</h3>
						</div>
							<div id="inner-content-side" >
									<br>
									<div class="col-xs-12 form-group" style="margin:0px;">
											<div class="row">											
												<form action="/cstmr/takein/takeinOrderList" method="get" enctype="multipart/form-data">
												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
													<table  class="table table-bordered" style="max-width:70%;float:left;">
														<thead>
															<tr>
																<th style="width:50px;" class="center colorBlack"><i class="fa fa-calendar"></i></th>
																<th style="padding:0px;"><input type="text" name="fromDate" id="fromDate" style="width:100%;" value="${parameterInfo.startDate}" autocomplete="off"></th>
																<th style="width:20px;" class="center colorBlack">-</th>
																<th style="padding:0px;"><input type="text" name="toDate" id="toDate" style="width:100%;" value="${parameterInfo.endDate}" autocomplete="off"></th>
																<th style="width:100px;" class="center colorBlack">OrderNo</th>
																<th style="padding:0px;"><input class="searchValue" type="text" name="orderNo" style="width:100%" value="${parameterInfo.orderNo}"/></th>
																<th style="width:100px;" class="center colorBlack">HawbNo</th>
																<th style="padding:0px;"><input class="searchValue" type="text" name="hawbNo" style="width:100%" value="${parameterInfo.hawbNo}"/></th>
																<th style="width:100px;" class="center colorBlack">수취인명</th>
																<th style="padding:0px;background-color: white"><input class="searchValue" type="text" name="cneeName" style="width:100%" value="${parameterInfo.cneeName}"/></th>
																<th class="center" ><input style="width:100%;padding:0px;margin:0px;" class="btn-primary" type="submit" value="조회" id="btn_search"/></th>
															</tr>
														</thead>
													</table>
												</form>
											</div>
									</div>
							</div>
									<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;">
										<!-- <div class="hr hr-8 dotted"></div>
										<div class="space-20"></div> -->
										<div class="display" >
											<form id="list_form" action="" method="post">
												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
												<input type="hidden" name="orderType" value="TAKEIN">
												<input type="hidden" name="idx" value="All">
												<input type="hidden" name="startDate" id="startDate">
												<input type="hidden" name="endDate" id="endDate">
												<input type="hidden" name="urlType" value="allAbout">
											<div style="text-align:left;float:right">
												<input type="button" value="Excel 다운로드" id="btn_excel" class="btn-info">
												<input type="button" value="삭제" id="btn_del" class="btn-danger">
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
															운송장번호
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
												<c:forEach items="${takeinList}" var="takeorderinfo" varStatus="status">
														<tr>
														 <td style="text-align: center">
																<input type="checkbox" name="nno[]" value="${takeorderinfo.nno}">
																<input type="hidden" name="errYn[]" value="${takeorderinfo.errYn}">
															</td> 
																<td class="center colorBlack">${takeorderinfo.orderNo}</td>
																<td>${takeorderinfo.orderDate}</td>
																<td>${takeorderinfo.orgStationName}</td>
																<td>${takeorderinfo.dstnNationName}</td>
																<td class="center colorBlack">${takeorderinfo.hawbNo}</td>
																<td>${takeorderinfo.cneeName}</td>
																<td>Tel : ${takeorderinfo.cneeTel}
																<br>
																Hp : ${takeorderinfo.cneeHp}
																<br>
																E-mail : ${takeorderinfo.cneeEmail}
															</td>
															<td style="overflow: auto;">
																<span>${takeorderinfo.cneeState}</span>
																<span>${takeorderinfo.cneeCity}</span>
																<br>
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
															</td>
														</tr>
														<tr>
															<td colspan=9 style="padding-left:10px;">
																<table  style="width:100%;">
																	<tr>
																		<th rowspan="1" class="center colorBlack" style="width:380px;"></th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">hscode</th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">Brand</th>
																		<th rowspan="2" class="left colorBlack" style="min-width:190px;">itemDetail</th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">단가</th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">수량</th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">금액</th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">제조국</th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">제조사</th>
																		<th rowspan="1" class="center colorBlack" style="width:80px;"> total 금액</th>
																	</tr>
																	<tr>
																		<th class="left" style="width:80px;color:red;" rowspan="100"></th>
																		<th class="center colorBlack" style="width:80px;" rowspan="100">${takeorderinfo.totalAmt}</th>
																	</tr>
																	<c:forEach items="${takeorderinfo.orderItem}" var="itemDetail" varStatus="status">
																	<tr>
																		<td style="text-align:center;color:blue;font-weight:bold;">${itemDetail.hsCode}</td>
																		<td style="text-align:center;font-weight:bold;">${itemDetail.brand}</td>
																		<td style="text-align:left;font-weight:bold;">${itemDetail.itemDetail}</td>																	
																		<td style="text-align:center;">${itemDetail.unitValue}</td>
																		<td style="text-align:center;">${itemDetail.itemCnt}</td>
																		<td style="text-align:center;">${itemDetail.unitValue * itemDetail.itemCnt}</td>
																		<td style="text-align:center;">${itemDetail.makeCntry}</td>
																		<td style="text-align:center;">${itemDetail.makeCom}</td>
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
											<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
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
		<script src="/assets/js/daterangepicker.min.js"></script>
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
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

		    $(".searchValue").click(function() {
		    	  if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
			            alert('test');
			        }
		    });
		
		    $("#btn_dlvin").click(function() {
		        $("#list_form").attr('action','/cstmr/takein/takeinDvlIn');
	            $("#list_form").submit();
		    });

		    $("#btn_del").click(function() {
		        if(!confirm('정말 삭제 하시겠습니까?')){
	                return false;
	            }
		        $("#list_form").attr('action','/cstmr/takein/takeinDel');
	            $("#list_form").submit();
		    });

			$('#fromDate').datepicker({
				autoclose:true,
				todayHightlight:true,
				format: 'yyyymmdd',
			}).on('changeDate', function(e) {
				$("#toDate").val($("#fromDate").val());
			});

			$('#toDate').datepicker({
				autoclose:true,
				todayHightlight:true,
				format: 'yyyymmdd',
			});

			$("#btn_excel").click(function() {
				$("#startDate").val($("#fromDate").val());
				$("#endDate").val($("#toDate").val());
				$("#list_form").attr('action','/comn/downBlExcel');
	            $("#list_form").submit();
			})
	    });
		</script>
		
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#5thMenu").toggleClass('open');
					$("#5thMenu").toggleClass('active'); 
					$("#5thThr").toggleClass('active');
				});
		</script>
		<!-- script addon end -->
	</body>
</html>
