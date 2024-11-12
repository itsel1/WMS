<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
.padding0 {
	padding: 0px !important;
}

.colorBlack {
	color: #000000 !important;
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
	background-color: rgba(255,255,255,0) !important;border-bottom:none; border-right:1px solid #e0e0e0;
}

table ,tr th div .rightBorder{
	background-color: rgba(255,255,255,0) !important;border-right:none;
}


@media only screen and (max-width:480px) {
	.hidden-480 {
		display: none !important;
	}
}

@media only screen and (max-width:480px) {
	.hidden-489 {
		display: inline!important;
	}
}

.containerTable {
	width: 100% !important;
}
</style>
	</head> 
	<body class="no-skin">
		<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
		<div id="table-contents" class="table-contents">
			<table id="dynamic-table" class="table table-bordered table-hover containerTable">
				<thead>
					<tr>
						<th class="center colorBlack">
							<label class="pos-rel">
								<input type="checkbox" class="ace"/>
								<span class="lbl"></span>
							</label>
						</th>
						<th class="colorBlack">
							<div class="row center no-padding" style="border-bottom:none;">
								<div class="col-xs-1  no-padding">
									상품 이미지
								</div>
								<div class="col-xs-10  no-padding" style="border-bottom:none;">
									<div class="col-xs-2">
										주문 번호  / HAWB NO
									</div>
										
									<div class="col-xs-1">
										출발지/도착지
									</div>
									<div class="col-xs-1">
										주문 일자
									</div>
									<div class="col-xs-2">
										수취인 / USER ID
									</div>
									<div class="col-xs-1">
										Tel
									</div>
									<div class="col-xs-1">
										우편번호
									</div>
									<div class="col-xs-4">
										수취인 주소
									</div>
								</div>
								<div class="col-xs-1 no-padding" style="border-bottom:none;">
									<div class="col-xs-12">
									</div>
								</div>
							</div>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${orderInspList}" var="orderInspVO" varStatus="status">
						<c:set var="itemTotalVal" value="0"/>
						<tr	>
							<td class="center no-padding">
								<label class="pos-rel">																
									<input type="checkbox" class="form-field-checkbox" value="${orderInspVO.nno}"/>
									<span class="lbl"></span>
								</label>
							</td>
							<td class="center colorBlack no-padding">
								<div class="row no-padding" style="background-color: rgba(255,255,255,0) !important;border-top:1px solid black; border-bottom:1px solid #e0e0e0; display: flex;">
									<div class="col-xs-1  no-padding">
										<c:set var="imgList" value="${fn:split(orderInspVO.itemImgUrl,'|')}"/>
										<c:forEach items="${imgList}" var="imgList" varStatus="status">
											<img src="${imgList}" style="width:110px;"><br/>
										</c:forEach>
									</div>
									<div class="col-xs-10 no-padding">
										<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important; border-bottom:1px solid #e0e0e0; display: flex;">
											<div class="col-xs-2 ">
													주문번호 : 
													${orderInspVO.orderNo}<br/>
													Hawb No : 
													${orderInspVO.hawbNo}
												
											</div>
											<div class="col-xs-1">
												${orderInspVO.orgStationName}/${orderInspVO.dstnNationName}
											</div>
											<div class="col-xs-1">
												${orderInspVO.orderDate}
											</div>
											<div class="col-xs-2">
												${orderInspVO.cneeName} / ${orderInspVO.userId}
											</div>
											<div class="col-xs-1">
												${orderInspVO.cneeTel}
											</div>
											<div class="col-xs-1">
												${orderInspVO.cneeZip}
											</div>
											<div class="col-xs-4">
												${orderInspVO.cneeAddr} ${orderInspVO.cneeAddrDetail}
											</div>
										</div>
										<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important; border-bottom:1px solid #e0e0e0; display: flex;">
											<div class="col-xs-2" style="font-weight:bold;">
												TRK NO.
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
										
										<c:set var="unitList" value="${fn:split(orderInspVO.unitValue,'|')}"/>
										<c:set var="detailList" value="${fn:split(orderInspVO.itemDetail,'|')}"/>
										<c:set var="cntList" value="${fn:split(orderInspVO.itemCnt,'|')}"/>
										<c:set var="trkList" value="${fn:split(orderInspVO.trkNo,'|')}"/>
										
										<c:forEach items="${detailList}" var="detailInfo" varStatus="status">
											<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important; border-bottom:1px solid #e0e0e0; display: flex;">
												<div class="col-xs-2 " >
													${trkList[status.index]} 
												</div>
												<div class="col-xs-4" >
													${detailInfo} 
												</div>
												<div class="col-xs-2" >
													${unitList[status.index]} 
												</div>
												<div class="col-xs-2" >
													${cntList[status.index]} 
												</div>
												<div class="col-xs-2" >
													${cntList[status.index]} * ${unitList[status.index]} = ${cntList[status.index]*unitList[status.index]}
													<c:set var="itemTotalVal" value="${itemTotalVal+(cntList[status.index]*unitList[status.index]) }"/>
												</div>
											</div>
										</c:forEach>
										<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important;border-top:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0; display: flex;">
											<div class="col-xs-2 col-xs-offset-8" >
												총 금액
											</div>
											<div class="col-xs-2" >
													${itemTotalVal}
											</div>
										</div>
									</div>
									<div class="col-xs-1 no-padding" style="border-bottom:none; margin:auto;" >
										<input type="hidden" id = "hawbNo+${status.count}" name = "hawbNo" value="${orderInspVO.hawbNo}"/>
										<input type="hidden" id= "targetNno+${status.count}" name = "targetNno" value="${orderInspVO.nno }"/>
										<button type="button" class="btn btn-primary btn-sm btn-white" id="detailBtn" name="detailBtn" onclick="javascript:detailBtnClick('${orderInspVO.nno}')">입고작업 하기</button>
									</div>
								</div>
								
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		
		<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
		</div>
			
		<!-- Main container End-->
		<!-- script addon start -->
		<script type="text/javascript">	
		
			jQuery(function($) {
				$(document).load(function() {
					$("#12thMenu").toggleClass('open');
					$("#12thMenu").toggleClass('active'); 
					$("#12thFor").toggleClass('active');
				});
			})

			var myTable = 
			$('#dynamic-table')
			.DataTable( {
				"dom" : "t",
				"paging":   false,
		        "ordering": false,
		        "info":     false,
		        "searching": false,
				select: {
					style: 'multi',
					selector: 'td:first-of-type'
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
					if(th_checked) myTable.row(row).select();
					else  myTable.row(row).deselect();
				});
			});
			
			//select/deselect a row when the checkbox is checked/unchecked
			$('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
				var row = $(this).closest('tr').get(0);
				if(this.checked) myTable.row(row).deselect();
				else myTable.row(row).select();
			});

			$('#dynamic-table > tbody > tr > td[id=hawbNoTd]').on('click', function(){
				$("#hawbNoSch").val($(this).closest('tr').find("#hawbNo").val());
				$("#nno").val($(this).closest('tr').find("#targetNno").val())
				searchDetail();
			});


			function detailBtnClick(hawbNo){
				$("#housText").val(hawbNo);
				searchDetail();
			}

		</script>
		<!-- script addon end -->
		
	</body>
</html>
