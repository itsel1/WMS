<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	  
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
		
		table ,tr td div .rightBorderN{
			background-color: rgba(255,255,255,0) !important;border-right:none;
		}
		
		table ,tr td div .leftBorderN{
			background-color: rgba(255,255,255,0) !important;border-left:none;
		}
		
		table ,tr th div {
			background-color: rgba(255,255,255,0) !important;border-bottom:none; border-right:none;
		}
		
		table ,tr th div .rightBorderN{
			background-color: rgba(255,255,255,0) !important;border-right:none;
		}
		
		table ,tr th div .leftBorderN{
			background-color: rgba(255,255,255,0) !important;border-left:none;
		}
	 </style>
	<!-- basic scripts -->
	</head> 
	<body class="no-skin">
		<div class="space-10"></div>
		<input type="hidden" id="nno" name="nno" value="">
		<div class="hr hr-8 dotted"></div>
		<div class="space-20"></div>
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
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${inspList}" var="inspAllList" varStatus="status">
						<tr	>
							<td class="center no-padding">
								<label class="pos-rel">																
									<input type="checkbox" class="form-field-checkbox" value="${inspAllList.value[0].nno}"/>
									<span class="lbl"></span>
								</label>
							</td>
							<td class="center colorBlack no-padding">
								<div class="row no-padding" style="border-top:1px solid black; border-bottom:1px solid #e0e0e0; display: flex;">
									<div class="col-xs-1 rightBorderN no-padding" style="font-weight:bold;">
										상품 이미지
									</div>
									<div class="col-xs-11 no-padding" style="border-bottom:none; border-left:1px solid #e0e0e0;">
										<div class="col-xs-2" style="font-weight:bold;">
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
										<div class="col-xs-3" style="font-weight:bold;">
											수취인 주소
										</div>
									</div>
								</div>
								
								<div class="row center no-padding" style="border-bottom:none; display: flex;">
									<div class="col-xs-1 no-padding rightBorderN">
										<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
											<img src="${inspList.itemImgUrl}" style="width:28%;"><br/>
										</c:forEach>
									</div>
									<div class="col-xs-11 no-padding" style="border-left:1px solid #e0e0e0;">
										<div class="row rightBorderN no-padding" style="border-bottom:1px solid #e0e0e0; display: flex;">
											<input type="hidden" name="nno" value="${inspAllList.value[0].nno}"/>
											<div class="col-xs-2 ">
												<a href="#" onClick="javascript:readModify('insp','${inspAllList.value[0].nno}'); return false;">${inspAllList.value[0].orderNo}</a><br/>
												<c:if test="${!empty inspAllList.value[0].shipperReference}">
													REFERENCE NO : ${inspAllList.value[0].shipperReference}
												</c:if>
											</div>
											<div class="col-xs-1" id="transName${status.index}" name="transName${status.index}">
												${inspAllList.value[0].transName}
											</div>
											<div class="col-xs-1">
												${inspAllList.value[0].orgStationName} / ${inspAllList.value[0].dstnNationName}
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
											<div class="col-xs-3">
												${inspAllList.value[0].cneeAddr} ${inspAllList.value[0].cneeAddrDetail}
											</div>
										</div>
										<div class="row rightBorderN no-padding" style="border-bottom:1px solid #e0e0e0; display: flex;">
											<div class="col-xs-1" style="font-weight:bold;">
												HSCODE
											</div>
											<div class="col-xs-2" style="font-weight:bold;">
												TRK NO.
											</div>
											<div class="col-xs-1" style="font-weight:bold;">
												상품 번호
											</div>
											<div class="col-xs-4" style="font-weight:bold;">
												상품명 
											</div>
											<div class="col-xs-1" style="font-weight:bold;">
												단가 
											</div>
											<div class="col-xs-1" style="font-weight:bold;">
												수량
											</div>
											<div class="col-xs-2" style="font-weight:bold;">
												금액
											</div>
										</div>
										<c:forEach items="${inspAllList.value}" var="inspList" varStatus="statusItem">
											<div class="row rightBorderN no-padding item-div" style="border-bottom:1px solid #e0e0e0;  display: flex;">
												<input type="hidden" name="subNo" value="${inspList.subNo}"/>
												<div class="col-xs-1" >
													${inspList.hsCode} 
												</div>
												<div class="col-xs-2" >
													<input type="text" name="trkNo" style="height:20px;width:100%;" value="${inspList.trkNo}">
													<%-- ${inspList.trkNo} --%>
												</div>
												<div class="col-xs-1" >
													${statusItem.count} 
												</div>
												<div class="col-xs-4" >
													${inspList.itemDetail} 
												</div>
												<div class="col-xs-1" >
													${inspList.unitValue} 
												</div>
												<div class="col-xs-1" >
													${inspList.itemCnt}
												</div>
												<div class="col-xs-2" >
													${inspList.itemValue}
													<c:set var="itemTotalVal" value="${itemTotalVal+inspList.itemValue }"/>
												</div>
											</div>
										</c:forEach>
										<div class="row rightBorderN no-padding" style="border-top:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0;  display: flex;">
											<div class="col-xs-2 col-xs-offset-8" >
												총 금액
											</div>
											<div class="col-xs-2" >
													${itemTotalVal}
													<c:set var="itemTotalVal" value=""/>
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
				$("#uploadForm").attr("action", "/cstmr/apply/modifyRegistInfoInsp");    ///board/modify/로
				$("#uploadForm").attr("method", "post");            //get방식으로 바꿔서
				$("#uploadForm").submit();
			}

			jQuery(function($) {
				var myTable = 
					$('#dynamic-table')
					.DataTable( {
						"dom": 'lt',
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "autoWidth": false,
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
					
					$("input[name='trkNo']").on('keyup', function(e) {
						if (e.keyCode == 13) {
							
							if(confirm("업데이트 하시겠습니까?")) {

								var nno = $(this).closest('tr').find('input[name="nno"]').val();
								var subNo = $(this).closest('.item-div').find('input[name="subNo"]').val();
								
								var trkNo = $(this).val();
								
								$.ajax({
									url : '/cstmr/apply/inspTrkNoUpdate',
									type : 'POST',
									beforeSend : function(xhr)
									{  
									    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
									},
									data : {nno: nno, subNo: subNo, trkNo: trkNo},
									success : function(response) {
										if (response.code == 'S') {
											alert("정상 처리 되었습니다.");
											location.reload();
										} else {
											alert("업데이트 중 시스템 오류가 발생 하였습니다.");
										}
									},
									error : function(error) {
										alert("시스템 오류가 발생 하였습니다.");
									}
								});
							}

						}
					});

			});
		
		</script>
		<!-- script addon end -->
	</body>
</html>
