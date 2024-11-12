<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
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
		
		table ,tr td div {
			background-color: rgba(255,255,255,0) !important;border-right:1px solid #e0e0e0; 
		}
		
		table ,tr td div .rightBorder{
			background-color: rgba(255,255,255,0) !important;border-right:none;
		}
		table ,tr th div {
			background-color: rgba(255,255,255,0) !important;border-bottom:none; border-right:none;
		}
		
		table ,tr th div .rightBorder{
			background-color: rgba(255,255,255,0) !important;border-right:none;
		}
	</style>
	<!-- basic scripts -->
	</head> 
	<body class="no-skin">
		<input type="hidden" id="nno" name="nno" value="">
		<div class="hr hr-8 dotted"></div>
		<div class="space-20"></div>
		<table id="dynamic-table" class="table table-bordered table-hover containerTable">
			<colgroup>
				<col width="50"/>
			</colgroup>
			<thead>
				<tr>
					<th class="center colorBlack">
						<label class="pos-rel">
							<input type="checkbox" class="ace"/>
							<span class="lbl"></span>
						</label>
					</th>
					<th class="center colorBlack">
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${inspList}" var="inspAllList" varStatus="status">
					<tr >
						<td class="center no-padding">
							<label class="pos-rel">																
								<input type="checkbox" class="form-field-checkbox" value="${inspAllList.value[0].nno}"/>
								<span class="lbl"></span>
							</label>
						</td>
						<td class="center colorBlack no-padding">
							<div class="row no-padding" style="background-color: rgba(255,255,255,0) !important;border-top:1px solid black; border-bottom:1px solid #e0e0e0; display: flex;">
								<div class="col-xs-1 rightBorder no-padding" style="font-weight:bold;">
									상품 이미지
								</div>
								<div class="col-xs-11 no-padding" style="border-bottom:none; border-left:1px solid #e0e0e0;">
									<div class="col-xs-1" style="font-weight:bold;">
										주문 번호 
									</div>
									<div class="col-xs-1" style="font-weight:bold;">
										배송 회사 
									</div>
									<div class="col-xs-1" style="font-weight:bold;">
										출발지/도착지
									</div>
									<div class="col-xs-1" style="font-weight:bold;">
										주문 일자
									</div>
									<div class="col-xs-2" style="font-weight:bold;">
										수취인
									</div>
									<div class="col-xs-1" style="font-weight:bold;">
										Tel
									</div>
									<div class="col-xs-1" style="font-weight:bold;">
										우편번호
									</div>
									<div class="col-xs-4" style="font-weight:bold;">
										수취인 주소
									</div>
								</div>
							</div>
							<div class="row center no-padding" style="border-bottom:none; display: flex;">
								<div class="col-xs-1 no-padding rightBorder">
									<c:forEach items="${inspAllList.value}" var="inspList">
										<img src="${inspList.itemImgUrl}" style="width:28%;">
									</c:forEach>
								</div>
								<div class="col-xs-11 no-padding" style="border-left:1px solid #e0e0e0;">
									<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important; border-bottom:1px solid #e0e0e0; display: flex;">
										<div class="col-xs-1 ">
											<a href="#" onClick="javascript:readModify('insp','${inspAllList.value[0].nno}'); return false;">${inspAllList.value[0].orderNo}</a>
										</div>
										<div class="col-xs-1" >
											${inspAllList.value[0].transName}
										<input type="hidden" id="transName${status.index}" name="transName${status.index}" value="${inspAllList.value[0].transCode}">
										<input type="hidden" id="uploadType${status.index}" name="uploadType${status.index}" value="${inspAllList.value[0].uploadType}">
										</div>
										<div class="col-xs-1">
											${inspAllList.value[0].orgStationName}/${inspAllList.value[0].dstnNationName}
										</div>
										<div class="col-xs-1">
											${inspAllList.value[0].orderDate}
										</div>
										<div class="col-xs-2">
											${inspAllList.value[0].cneeName}
										</div>
										<div class="col-xs-1">
											${inspAllList.value[0].cneeTel}
										</div>
										<div class="col-xs-1">
											${inspAllList.value[0].cneeZip}
										</div>
										<div class="col-xs-4">
											${inspAllList.value[0].cneeAddr} ${inspAllList.value[0].cneeAddrDetail}
										</div>
									</div>
									<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important; border-bottom:1px solid #e0e0e0; display: flex;">
										<div class="col-xs-1" style="font-weight:bold;">
											HSCODE
										</div>
										<div class="col-xs-1" style="font-weight:bold;">
											상품 번호
										</div>
										<div class="col-xs-4" style="font-weight:bold;">
											상품명 
										</div>
										<div class="col-xs-2" style="font-weight:bold;">
											단가 
										</div>
										<div class="col-xs-2" style="font-weight:bold;">
											수량
										</div>
										<div class="col-xs-2" style="font-weight:bold;">
											금액
										</div>
									</div>
									<c:forEach items="${inspAllList.value}" var="inspList" varStatus="statusItem">
										<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important; border-bottom:1px solid #e0e0e0; display: flex;">
											<div class="col-xs-1" >
												${inspList.hsCode} 
											</div>
											<div class="col-xs-1" >
												${statusItem.count} 
											</div>
											<div class="col-xs-4" >
												${inspList.itemDetail} 
											</div>
											<div class="col-xs-2" >
												${inspList.unitValue} 
											</div>
											<div class="col-xs-2" >
												${inspList.itemCnt}
											</div>
											<div class="col-xs-2" >
												${inspList.itemValue}
												<c:set var="itemTotalVal" value="${itemTotalVal+inspList.itemValue }"/>
											</div>
										</div>
									</c:forEach>
									<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important;border-top:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0; display: flex;">
										<div class="col-xs-2 col-xs-offset-8" style="font-weight:bold;">
											총 금액
										</div>
										<div class="col-xs-2" >
												${itemTotalVal}
												<c:set var="itemTotalVal" value=""/>
										</div>
									</div>
									<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important;border-top:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0; display: flex;">
										<div class="col-xs-12" style="font-weight:bold;">
											오류 내역
										</div>
									</div>
									<div class="row rightBorder no-padding" id="errMsg${status.index}" name="errMsg${status.index}" style="text-align:left; rgba(255,255,255,0) !important;border-top:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0; display: flex;">
										<div class="col-xs-12"" >
											<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
												<c:forEach items="${inspList.errorList}" var="errorList" varStatus="status1">
													<span class="red errorCol"> 주문정보의 ${errorList}<br />
														</span>
												</c:forEach>
												<c:forEach items="${inspList.errorItem}" var="errorItem" varStatus="status2">
													<span class="red errorCol">상품정보의 ${errorItem}<br />
														</span>
												</c:forEach>
											</c:forEach> 
											<c:if test="${inspAllList.value[0].deliveryYn eq '0'}">
												<span class="red errorCol"> 
													운송 가능 국가가 아닙니다.<br />
												</span>
											</c:if> 
										</div>
									</div>
								</div>
							</div>
							
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
		<script type="text/javascript">
			function readModify(type,nno){
				$("#nno").val(nno);
				var formData = $("#uploadForm").serialize();
				$("#uploadForm").attr("action", "/cstmr/apply/modifyInfoInsp");    ///board/modify/로
				$("#uploadForm").attr("method", "post");            //get방식으로 바꿔서
				$("#uploadForm").submit();
			}
			jQuery(function($) {
				$(document).ready(function() {
					$("#forthMenu").toggleClass('open');
					$("#forthMenu").toggleClass('active'); 
					$("#4thOne").toggleClass('active');
					jQuery('.errorCol').closest('tr').css('background-color', 'rgb(255,242,242)');
				});

				var myTable = 
					$('#dynamic-table')
					.DataTable( {
						"dom": 't',
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
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
							else  {
								myTable.row(row).deselect();
							}
						});
					});
			})
		</script>
		<!-- script addon end -->
	</body>
</html>
