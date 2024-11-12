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
					<div class="col-md-12 mb-0" style="font-size:14px !important;">
						<a>배송대행 신청</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">검품배송 신청서</strong></div>
				</div>
			</div>
		</div>
	    <!-- Main container Start-->
		<div class="container">
			<div class="page-content noneArea">
				<div class="page-header noneArea">
					<h3>
						검품배송 신청서
					</h3>
				</div>
				<form name="uploadForm" id="uploadForm" enctype="multipart/form-data" >
					<div id="inner-content-side" >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<div class="col-xs-12 form-group" style="margin:0px;">
							<div class="row">
								<div class="col-xs-12 col-md-2 form-group">
									<select id="dstnNation" name="dstnNation" style="height: 34px; width: 100%;">
										<c:forEach items="${transComList}" var="transCom" varStatus="status">
											<option value="${transCom.transCode}" <c:if test="${transCom.transCode eq selDstn}">selected</c:if>>${transCom.transName}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-xs-12 col-md-5 form-group">
									<input type="file" id="excelFile" name="excelFile"
										style="display: initial; width: 70%; cursor: pointer; border: 1px solid #D5D5D5;"
										accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
									<i class="fa fa-times bigger-110" id="delBtns" name="delBtns" style="cursor: pointer;"></i>
										&nbsp;&nbsp;&nbsp;
									<button type="button" class="btn btn-white btn-inverse btn-xs" id="testBtn" name="testBtn">excel Form 다운로드</button>
								</div>
								<div class="col-xs-12 col-md-5" style="text-align: right;">
									<button type="button" class="btn btn-sm btn-success " name="excelUp" id="excelUp">
										엑셀 파일 적용하기
									</button>
									<button type="button" class="btn btn-sm btn-primary" name="rgstrOne" id="rgstrOne">
										수기등록
										<!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
									</button>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12 col-md-6" id="rgstr" style="text-align: left">
									<button type="button" class="btn btn-sm btn-primary" name="rgstrCheck" id="rgstrCheck">
										배송자료 등록
										<!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
									</button>
									<button type="button" class="btn btn-sm btn-danger" name="delCheck" id="delCheck">
										삭제
										<!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
									</button>
								</div>
								
							</div>
						</div>
						<div class="col-xs-12 form-group" style="margin:0px;">
							<div id="table-contents" class="table-contents">
								<div class="hr hr-8 dotted"></div>
								<div class="space-10"></div>
								<div id="tableArea" name="tableArea">
									<table id="dynamic-table" class="table table-bordered table-hover containerTable">
										<thead >
											<tr>
												<th class="center">
													<label class="pos-rel"> 
														<input type="checkbox" class="ace" /> 
														<span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack">비고</th>
												<th class="center colorBlack">주문 번호</th>
												<th class="center colorBlack">출발지</th>
												<th class="center colorBlack">도착지</th>
												<th class="center colorBlack">
													수취인(Jpn)<br>
													수취인(Eng)
												</th>
												<th class="center colorBlack">
													수취인 주소(Jpn)<br>
													수취인 주소(Eng)
												</th>
												<th class="center colorBlack">Tel</th>
												<th class="center colorBlack">우편번호</th>
												<th class="center colorBlack">Box Qty</th>
												<th class="center colorBlack">실 무게</th>
												<th class="center colorBlack">
													width(Cm)<br>
													length(Cm)<br>
													Height(Cm)
												</th>
												<th class="center colorBlack">상품명(Eng)</th>
												<th class="center colorBlack">상품 개수</th>
												<th class="center colorBlack">단가</th>
												<th class="center colorBlack">Total Value</th>
	
											</tr>
										</thead>
										<tbody >
											<c:forEach items="${inspList}" var="inspAllList" varStatus="status">
												<tr>
													<td class="center">
														<label class="pos-rel"> 
															<input type="checkbox" class="form-field-checkbox" value="${inspAllList.value[0].nno}" /> 
															<span class="lbl"></span>
														</label>
													</td>
													<td class="center colorBlack">
													<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
														<c:forEach items="${inspList.errorList}" var="errorList" varStatus="status1">
															<span class="red errorCol"> 주문정보의 ${errorList}<br />
																</span>
														</c:forEach>
														<c:forEach items="${inspList.errorItem}" var="errorItem" varStatus="status2">
															<span class="red errorCol">${status.count}번 상품의 ${errorItem}<br />
																</span>
														</c:forEach>
													</c:forEach> 
													<c:if test="${inspAllList.value[0].deliveryYn eq '0'}">
														<span class="red errorCol"> 
															운송 가능 국가가 아닙니다.<br />
														</span>
													</c:if> 
													
													<%-- <c:if test="${!empty optionOrderVO.orderNoYn}">
														<c:if test="${inspAllList.value[0].status eq 'A'}">
															<span class="red errorCol"> 이미 등록된 주문번호 입니다. </span>
														</c:if> 
														<c:if test="${empty inspAllList.value[0].orderNo}">
															<span class="red errorCol"> 주문번호가 없습니다. </span>
														</c:if>
														<c:if test="${fn:contains(inspAllList.value[0].status,'TMP09')}">
															<span class="red errorCol"> 이미 등록된 주문번호 입니다. <br /></span>
														</c:if>
														<c:if test="${fn:contains(inspAllList.value[0].status,'TMP01')}">
															<span class="red errorCol"> 주문번호가 너무 깁니다.<br /></span>
														</c:if>
													</c:if>	
													<c:if test="${!empty optionOrderVO.shipperZipYn}">
														<c:if test="${fn:contains(inspAllList.value[0].status,'TMP02')}">
															<span class="red errorCol"> 송하인 우편번호가 너무 깁니다.(최대 8자리) <br /></span>
														</c:if>
													</c:if>
													<c:if test="${!empty optionOrderVO.shipperTelYn}">	
														<c:if test="${fn:contains(inspAllList.value[0].status,'TMP03')}">
															<span class="red errorCol"> 송하인 전화번호 양식을 확인해 주세요 <br /></span>
														</c:if>
													</c:if>
													<c:if test="${!empty optionOrderVO.shipperHpYn}">
														<c:if test="${fn:contains(inspAllList.value[0].status,'TMP04')}">
															<span class="red errorCol"> 송하인 핸드폰번호 양식을 확인해 주세요 <br /></span>
														</c:if>
													</c:if>
													<c:if test="${!empty optionOrderVO.cneeTelYn}">
														<c:if test="${fn:contains(inspAllList.value[0].status,'TMP05')}">
															<span class="red errorCol"> 수취인 연락처1을 확인해 주세요 <br /></span>
														</c:if>
														<c:if test="${empty inspAllList.value[0].cneeTel}">
															<span class="red errorCol"> 
																Tel를 확인해 주세요<br />
															</span>
														</c:if> 
													</c:if>
													<c:if test="${!empty optionOrderVO.cneeHpYn}">
														<c:if test="${fn:contains(inspAllList.value[0].status,'TMP06')}">
															<span class="red errorCol"> 수취인 연락처2을 확인해 주세요 <br /></span>
														</c:if>
													</c:if>
													<c:if test="${!empty optionOrderVO.cneeZipYn}">
														<c:if test="${fn:contains(inspAllList.value[0].status,'TMP07')}">
															<span class="red errorCol"> 수취인 우편주소 양식을 확인해 주세요.(최대 8자리)<br /></span>
														</c:if> 
														<c:if test="${empty inspAllList.value[0].cneeZip}">
															<span class="red errorCol"> 	
																우편번호 를 확인해 주세요<br />
															</span>
														</c:if> 
													</c:if>
													<c:if test="${!empty optionOrderVO.nativeCneeAddrYn}">
														<c:if test="${empty inspAllList.value[0].nativeCneeAddr}">
															<span class="red errorCol"> 
																수취인 주소(Jpn)를 확인해 주세요.<br />
															</span>
														</c:if> 
													</c:if>
														<c:if test="${empty inspAllList.value[0].cneeAddr}">
															<span class="red errorCol"> 
																수취인 주소(Eng)를 확인해 주세요.<br />
															</span>
														</c:if> 
													<c:if test="${!empty optionOrderVO.nativeCneeNameYn}">
														<c:if test="${empty inspAllList.value[0].nativeCneeName}">
															<span class="red errorCol"> 
																수취인(Jpn)를 확인해 주세요<br />
															</span>
														</c:if> 
													</c:if>
													<c:if test="${!empty optionOrderVO.cneeNameYn}">
														<c:if test="${empty inspAllList.value[0].cneeName}">
															<span class="red errorCol"> 
																수취인(Eng)를 확인해 주세요<br />
															</span>
														</c:if>
													 </c:if>
														
														
														
													<c:if test="${!empty optionOrderVO.boxCntYn}">
														<c:if test="${inspAllList.value[0].boxCnt eq '0'}">
															<span class="red errorCol"> 
																BoxQty 를 확인해 주세요<br />
															</span>
														</c:if> 
													</c:if>
													<c:if test="${!empty optionOrderVO.userWidthYn}">
														<c:if test="${inspAllList.value[0].userWidth eq '0.0'}">
															<span class="red errorCol"> 
																width(Cm)를 확인해 주세요<br />
															</span>
														</c:if> 
													</c:if>
													<c:if test="${!empty optionOrderVO.userLengthYn}">
														<c:if test="${inspAllList.value[0].userLength eq '0.0'}">
															<span class="red errorCol"> 
																Length(Cm)를 확인해 주세요<br />
															</span>
														</c:if> 
													</c:if>
													<c:if test="${!empty optionOrderVO.userHeightYn}">
														<c:if test="${inspAllList.value[0].userHeight eq '0.0'}">
															<span class="red errorCol"> 
																Height(Cm)를 확인해 주세요<br />
															</span>
														</c:if> 
													</c:if>
													<c:if test="${!empty optionOrderVO.userWtaYn}">
														<c:if test="${inspAllList.value[0].userWta eq '0.0'}">
															<span class="red errorCol"> 
																실 무게를 확인해 주세요<br />
															</span>
														</c:if> 
													</c:if>
														<c:if test="${inspAllList.value[0].deliveryYn eq '0'}">
															<span class="red errorCol"> 
																운송 가능 국가가 아닙니다.<br />
															</span>
														</c:if> 
														
													<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
														<c:if test="${!empty optionItemVO.itemDetailYn}">
															<c:if test="${empty inspList.itemDetail}">
																<span class="red errorCol"> 상품명를 확인해 주세요<br />
																</span>
															</c:if>
														</c:if>
														<c:if test="${!empty optionItemVO.itemMeterialYn}">
															<c:if test="${empty inspList.itemMeterial}">
																<span class="red errorCol"> 상품 재질를 확인해 주세요<br />
																</span>
															</c:if>
														</c:if>
														<c:if test="${!empty optionItemVO.itemCntYn}">
															<c:if test="${inspList.itemCnt eq '0'}">
																<span class="red errorCol"> 상품 개수를 확인해 주세요<br />
																</span>
															</c:if>
														</c:if>
														<c:if test="${!empty optionItemVO.unitValueYn}">
															<c:if test="${inspList.unitValue eq '0.0'}">
																<span class="red errorCol"> 단가를 확인해 주세요<br />
																</span>
															</c:if>
														</c:if>
														<c:if test="${inspList.itemValue ne (inspList.itemCnt * inspList.unitValue)}">
															<span class="red errorCol"> Total value가 다릅니다.<br />
															</span>
														</c:if>
														<c:if test="${!empty optionItemVO.makeCntryYn}">
															<c:if test="${empty inspList.makeCntry}">
																<span class="red errorCol"> 제조국를 확인해 주세요<br />
																</span>
															</c:if>
														</c:if>
														</c:forEach> --%> 
													</td>
													<td class="center colorBlack">
														<a href="/cstmr/apply/modifyInfoInsp?nno=${inspAllList.value[0].nno}">${inspAllList.value[0].orderNo}</a>
													</td>
													<td class="center colorBlack">
														${inspAllList.value[0].orgStationName}
													</td>
													<td class="center colorBlack">
														${inspAllList.value[0].dstnNationName}
													</td>
													<td class="center colorBlack">
														${inspAllList.value[0].nativeCneeName}<br>
														${inspAllList.value[0].cneeName}
													</td>
													<td class="center colorBlack">
														${inspAllList.value[0].nativeCneeAddr}<br>
														${inspAllList.value[0].cneeAddr}
													</td>
													<td class="center colorBlack">
														${inspAllList.value[0].cneeTel}
													</td>
													<td class="center colorBlack">
														${inspAllList.value[0].cneeZip}
													</td>
													<td class="center colorBlack">
														${inspAllList.value[0].boxCnt}
													</td>
													<td class="center colorBlack">
														${inspAllList.value[0].userWta}
													</td>
													<td class="center colorBlack">
														${inspAllList.value[0].userWidth}<br>
														${inspAllList.value[0].userLength}<br>
														${inspAllList.value[0].userHeight}
													</td>
													<td class="center colorBlack">
														<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
															${inspList.itemDetail}<br />
														</c:forEach></td>
													<td class="center colorBlack">
														<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																	${inspList.itemCnt}<br />
														</c:forEach>
													</td>
													<td class="center colorBlack">
														<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																	${inspList.unitValue}<br />
														</c:forEach>
													</td>
													<td class="center colorBlack">
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
						</div>
					</div>
				</form>
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
			jQuery(function($) {
				$(document).ready(function() {
					$("#forthMenu").toggleClass('open');
					$("#forthMenu").toggleClass('active'); 
					$("#4thOne").toggleClass('active');
					jQuery('.errorCol').closest('tr').css('background-color', 'rgb(255,242,242)');
				});
				//select target form change
				/* $('#formSelect').on('change', function(){
					$('#tableForm').children().remove();
					if($(this).val() != 0){
						var list = { selOption: $(this).val() }
						$.ajax({
							type : "POST",
							beforeSend : function(xhr)
							{   //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							url : "shpngSubpage",
							data : list,
							dataType : "text",
							error : function(){
								alert("subpage ajax Error");
							},
							success : function(data){
								$("#tableForm").html(data);
							}
						})
					}
				}); */

				function LoadingWithMask() {
				    //화면의 높이와 너비를 구합니다.
				    var maskHeight = $(document).height();
				    var maskWidth  = window.document.body.clientWidth;
				     
				    //화면에 출력할 마스크를 설정해줍니다.
				    var mask       ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
				  
				    //화면에 레이어 추가
				    $('body')
				        .append(mask)
				        
				    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
				    $('#mask').css({
				            'width' : maskWidth
				            ,'height': maskHeight
				            ,'opacity' :'0.3'
				    });
				  
				    //마스크 표시
				    $('#mask').show();  
				    //로딩중 이미지 표시
				}



				$("#dstnNation").on('change',function(e){
					location.href = '/cstmr/apply/inspDlvry?transCode='+$("#dstnNation").val();
				})


				$('#rgstrOne').on('click', function(e) {
					location.href = '/cstmr/apply/insp/registOne';
				});

				$('#delBtns').on('click', function(e) {
					$('#excelFile').val("");
				});


				$('#excelUp').on('click', function(e) {
					if($('#excelFile').val() == ""){
						alert("선택된 파일이 없습니다")
						return
					}
					var datass = new FormData();
					datass.append("file", $("#excelFile")[0].files[0])
					LoadingWithMask();
					$.ajax({
						type : "POST",
						beforeSend : function(xhr)
						{   //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						url : "inspAgncyExcelUpload?formSelect="+$("#dstnNation").val(), 
						data : datass,
						processData: false,
			            contentType: false,
						error : function(){
							alert("subpage ajax Error");
						},
						success : function(data){
							if(data == "F"){
								alert("등록중 오류가 발생했습니다.")
								location.href="/cstmr/apply/inspDlvry?transCode="+$("#dstnNation").val();
							}
							alert(data);
							location.href="/cstmr/apply/inspDlvry?transCode="+$("#dstnNation").val();
							
						}
					})
				});

				$('#rgstrCheck').on('click',function(e){
					LoadingWithMask();
					var targets = new Array();
					$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if(td_checked){
							if(row.children[1].childElementCount == 0){
								targets.push($(this).val());
							}
						}
					});
					$.ajax({
						url:'/cstmr/apply/registOrderList',
						type: 'POST',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						traditional : true,
						data: {
							targets : targets,
							transCode : $("#dstnNation").val()
						},
						success : function(data) {
							if(data == "S"){
								alert("등록 되었습니다.")
								location.href="/cstmr/apply/inspDlvry?transCode="+$("#dstnNation").val();
							}else if(data == "F"){
								alert("등록중 오류가 발생하였습니다. 관리자에게 문의해 주세요.")
								location.href="/cstmr/apply/inspDlvry?transCode="+$("#dstnNation").val();
							}else if(data =="N"){
								alert("등록할 수 있는 데이터가 없습니다.");
								location.href="/cstmr/apply/inspDlvry?transCode="+$("#dstnNation").val();
							}
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다. 입력정보를 확인해 주세요.");
			            }
					})
				})
				
				$('#delCheck').on('click',function(e){
					LoadingWithMask();
					var targets = new Array();
					$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if(td_checked){
							targets.push($(this).val());
						}
					});
					$.ajax({
						url:'/cstmr/apply/delOrderListTmp',
						type: 'POST',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						traditional : true,
						data: {
							targets : targets
						},
						success : function(data) {
							if(data == "S"){
								alert("삭제 되었습니다.")
								location.href="/cstmr/apply/inspDlvry?transCode="+$("#dstnNation").val();
							}else if(data == "F"){
								alert("삭제중 오류가 발생하였습니다. 관리자에게 문의해 주세요.")
							}
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("삭제에 실패 하였습니다. 입력정보를 확인해 주세요.");
			            }
					})
					
				})
				

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
							style: 'multi',
							selector:'td:first-child'
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
							if(th_checked) {
								if(!row.children[0].firstElementChild.firstElementChild.disabled)
									myTable.row(row).select();
							}
							else  myTable.row(row).deselect();
						});
					});
					$("#testBtn").on('click',function(e){
						window.open("/cstmr/apply/excelFormDown?transCode="+$("#dstnNation").val())
					})
			})
		</script>
		<!-- script addon end -->
	</body>
</html>
