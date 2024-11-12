<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	
</style>
	<!-- basic scripts -->
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/assets/js/bootstrap.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts --> 
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
	<!-- basic scripts End-->
	</head> 
	<body class="no-skin">
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								사입코드 등록(ExcelUpload)
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
					<form action="/cstmr/takein/tmpTakeinInfoExcelUpload" method="post" enctype="multipart/form-data">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<table style="width:100%">
									<tbody>
										<tr>
											<td style="width:350px;">
												<input type="file" name="excelFile">
											</td>
											<td style="width:200px;">
												<input type="submit" class="btn-primary" value="Excel 등록">
											</td>
											<td style="width:100%;text-align:right" >
												<input id="btnExcelSampleDown" type="button" class="btn btn-white" value="Excel Form 다운로드">																	
											</td>
										</tr>
									</tbody>	
								</table>
							</form>
							<br>
							<br>
						 	<form id="mgrForm" action="" method="get">
						 					<div style="text-align:left;float:left">
												<input type="button" value="삭제" id="btnDelete" class="btn-danger">
											</div>
											<div style="text-align:right;float:right">
												<input type="button" class="btn-primary" value="등록" id="btnInsert"  >
											</div>
								<table class="table table-bordered" style="">
									<thead>
													<tr>
														<th class="center">
															<label class="pos-rel">
																<input type="checkbox" class="ace" id="chkAll"/>
																<span class="lbl"></span>
															</label>
														</th>
														<th class="center colorBlack">
															Station
														</th>
														<th class="center colorBlack">
															상품 코드
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
															상품제질
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
															상품imgUrl
														</th>
													</tr>
												</thead>												
												<tbody>								
										<c:forEach items="${takeinList}" var="takeinfo" varStatus="status">
											<tr>
												<td class="center">
													<input type="checkbox" name="idx[]" value="${takeinfo.idx}">
													<input type="hidden" name="errYn[]" value="${takeinfo.errYn}">
												</td>
												<td class="center">
													${takeinfo.orgStationName}
												</td>
												<td style="text-align:center;font-weight:bold;">
													${takeinfo.cusItemCode}
												</td>
												<td style="color:blue;font-weight:bold;text-align:center;">${takeinfo.hsCode}</td>
												<td>${takeinfo.brand}</td>
												<td>${takeinfo.itemDetail}</td>
												<td>${takeinfo.itemOption}</td>
												<td style="text-align:center;">${takeinfo.itemMeterial}</td>
												<td style="text-align:right;font-weight:bold">${takeinfo.unitValue}</td>
												<td style="text-align:right;">${takeinfo.wta}</td>
												<td style="text-align:center;">${takeinfo.makeCom}</td>
												<td style="text-align:center;">${takeinfo.makeCntry}</td>
												<td style="text-align:center;">${takeinfo.itemImgUrl }</td>
										    </tr>
										    <tr>
										    	<td colspan="15" style="color:red;font-weight:bold;">${takeinfo.errMsg }</td>
										    </tr>
										</c:forEach>
									</tbody>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>

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
		
		<!-- script on paging end -->
		
		<!-- script addon start -->
			<script type="text/javascript">

			jQuery(function($) {

				$(".onlyEN").keyup(function(event){ 
					 if (!(event.keyCode >=37 && event.keyCode<=40)) {
					    var inputVal = $(this).val();
					    $(this).val(inputVal.replace(/[^a-z0-9 ]/gi,''));
					   } 
			     });

			    $("#btnExcelSampleDown").on('click',function(e){
					window.open("/takein/apply/takeinfoTmpexcelFormDown")
				})

				$("#btnDelete").on('click',function(e){

				   var delConfirm = confirm('정말 삭제 하시겠습니까?');

				   if(!delConfirm) {
				      return false;
				   }

					var formData = $("#mgrForm").serialize();

					$.ajax({
						url:'/cstmr/takein/takeinInDel',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData, 
						success : function(data) {
							alert(data.rstMsg);
							var Status = data.rstStatus;

						 	if(Status == "SUCCESS"){
								location.reload();
								opener.location.reload();
							}; 
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다.");
			            }
					})
				});


			    $("#btnInsert").on('click',function(e){
						var formData = $("#mgrForm").serialize();
						$.ajax({
							url:'/cstmr/takein/takeinInInsert',
							type: 'GET',
							beforeSend : function(xhr)
							{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							data: formData, 
							success : function(data) {
								alert(data.rstMsg);
								var Status = data.rstStatus;

							 	if(Status == "SUCCESS"){
									location.reload();
									opener.location.reload();
								}; 
				            }, 		    
				            error : function(xhr, status) {
				                alert(xhr + " : " + status);
				                alert("등록에 실패 하였습니다.");
				            }
						})
					});


			    $("#chkAll").click(function() {
		            if($("#chkAll").prop("checked")){ //해당화면에 전체 checkbox들을 체크해준다
		                $("input[name='idx[]']").prop("checked",true); // 전체선택 체크박스가 해제된 경우
		            } else { //해당화면에 모든 checkbox들의 체크를해제시킨다.
		                $("input[name='idx[]'").prop("checked",false);
		            }
		        });
	
				
			})
		</script>
		<!-- script addon end -->
	  	<link rel="stylesheet" href="https://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>

		<script>
			var j$ = jQuery;
			j$( function() {
				j$( "#trkDate" ).datepicker({
					 //maxDate: "+0D",
					 showButtonPanel: true,
					 currentText: 'To Day',
					 dateFormat: "yymmdd",
					 changeMonth: true,
					 monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
					 monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
				});
			} );
		</script>

	</body>
</html>
