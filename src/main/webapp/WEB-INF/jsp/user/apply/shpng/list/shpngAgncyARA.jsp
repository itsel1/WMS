<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
</head>
<body class="no-skin">
	<div class="hr hr-8 dotted"></div>
	<div class="space-10"></div>
	<div id="tableArea" name="tableArea">
		<table id="dynamic-table" class="table table-bordered table-hover containerTable">
			<thead>
				<tr>
					<th class="center">
						<label class="pos-rel">
							<input type="checkbox" class="ace"/>
							<span class="lbl"></span>
						</label>
					</th>
					<th class="center colorBlack">
						<div class="row center no-padding" style="border-bottom:none;">
							<div class="col-xs-2">
								주문 번호							
							</div>
							<div class="col-xs-1">
								출발지/도착지							
							</div>
							<div class="col-xs-1">
								주문 일자			
							</div>
							<div class="col-xs-1">
								수취인						
							</div>
							<div class="col-xs-1">
								TEL							
							</div>
							<div class="col-xs-1">
								우편번호							
							</div>
							<div class="col-xs-5">
								수취인 주소				
							</div>
						</div>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${shpngList}" var="shpngAllList" varStatus="status">
					<tr>
						<td class="center no-padding">
							<label class="pos-rel">																
								<input type="checkbox" class="form-field-checkbox" value="${shpngAllList.value[0].nno}"/>
								<span class="lbl"></span>
							</label>
						</td>
						<td class="center colorBlack no-padding">
							<div class="row no-padding" style="background-color: rgba(255,255,255,0) !important;border-top:1px solid black; border-bottom:1px solid #e0e0e0; display: flex;">
								<div class="col-xs-2 no-padding">
									<a href="/cstmr/apply/modifyShpng?transCode=ARA&nno=${shpngAllList.value[0].nno}">${shpngAllList.value[0].orderNo}</a>
								</div>
								<div class="col-xs-1 no-padding">
									${shpngAllList.value[0].orgStationName} / ${shpngAllList.value[0].dstnNationName}
								</div>
								<div class="col-xs-1 no-padding">
									${shpngAllList.value[0].orderDate}
								</div>
								<div class="col-xs-1 no-padding">
									${shpngAllList.value[0].cneeName}
								</div>
								<div class="col-xs-1 no-padding">
									${shpngAllList.value[0].cneeTel}
								</div>
								<div class="col-xs-1 no-padding">
									${shpngAllList.value[0].cneeZip}
								</div>
								<div class="col-xs-5 no-padding">
									${shpngAllList.value[0].cneeAddr} ${shpngAllList.value[0].cneeAddrDetail}
								</div>
							</div>						
							<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important; border-bottom:1px solid #e0e0e0; display: flex;">
								<div class="col-xs-2" style="font-weight:bold;">
									HSCODE
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
							<c:forEach items="${shpngAllList.value}" var="shpngList" varStatus="status">
								<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important; border-bottom:1px solid #e0e0e0; display: flex;">
									<div class="col-xs-2" >
										${shpngList.hsCode} 
									</div>
									<div class="col-xs-4" >
										${shpngList.itemDetail} 
									</div>
									<div class="col-xs-2" >
										${shpngList.unitValue} 
									</div>
									<div class="col-xs-2" >
										${shpngList.itemCnt}
									</div>
									<div class="col-xs-2" >
										${shpngList.itemValue}
										<c:set var="itemTotalVal" value="${itemTotalVal+shpngList.itemValue }"/>
									</div>
								</div>
							</c:forEach>
							<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important; border-bottom:1px solid #e0e0e0; display: flex;">
								<div class="col-xs-2 col-xs-offset-8" >
									총 금액
								</div>
								<div class="col-xs-2" >
										${itemTotalVal}
								</div>
							</div>
							<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important; border-top:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0; display: flex;">
								<div class="col-xs-12" style="font-weight:bold;">
									오류목록
								</div>
							</div>
							<div class="row rightBorder no-padding" style="text-align:left; rgba(255,255,255,0) !important;border-top:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0; display: flex;">
								<div class="col-xs-12">
									<c:forEach items="${shpngAllList.value}" var="shpngList" varStatus="status">
										<c:forEach items="${shpngList.errorList}" var="errorList" varStatus="status1">
											<span class="red errorCol"> 주문정보의 ${errorList}<br />
												</span>
										</c:forEach>
										<c:forEach items="${shpngList.errorItem}" var="errorItem" varStatus="status2">
											<span class="red errorCol">${status.count}번 상품의 ${errorItem}<br />
												</span>
										</c:forEach>
									</c:forEach> 
									<c:if test="${shpngAllList.value[0].deliveryYn eq '0'}">
										<span class="red errorCol"> 
											운송 가능 국가가 아닙니다.<br />
										</span>
									</c:if> 
								</div>
							</div>
						</td>
					</tr>	
				</c:forEach>
			</tbody>
		</table>
	</div>

	<script type="text/javascript">
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
						"dom": 'lt',
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "scrollY": 500,
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
