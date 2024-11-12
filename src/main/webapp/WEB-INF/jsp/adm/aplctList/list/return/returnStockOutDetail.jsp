<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		
	<div class="row" style="width:60%;">
		<table id="dynamic-table" class="table table table-bordered table-hover">
			<thead>
				<tr>
					<th class="center" style="width:50px;">
						<label class="pos-rel">
							<input type="checkbox" class="ace"/><span class="lbl"></span>
						</label>
					</th>
					<th>
						Rack Code
					</th>
					<th>
						Item Detail
					</th>
					<th>
						재고번호
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${stockRack}" var ="stockRackInfo" varStatus="status">
					<tr>
						<td class="center">
							<label class="pos-rel">
								<input type="checkbox" id="check${status.count}" name="checkNo" class="ace" value="${stockRackInfo.stockNo}"/>
								<span class="lbl"></span>
							</label>
						</td>
						<td>
							${stockRackInfo.rackCode}
						</td>
						<td>
							${stockRackInfo.itemDetail}
						</td>
						<td>
							${stockRackInfo.stockNo}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 
		<table class="table table table-bordered table-hover" style="width:30%; float:right;">
			<thead>
				<tr>
					<th class="center" style="width:120px;">배송업체 설정</th>
					<th class="center" style="width:100px;">HAWB NO (직접 입력)</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="background:white; padding:0px;">
						<select class="form-control" id="transCode" name="transCode">
							<c:forEach items="${transList}" var="transList">
								<option value="${transList.transCode}">${transList.transCode}</option>
							</c:forEach>												
						</select>
					</td>
					<td style="background:white; padding:0px;">
						<input type="text" name="hawbNo" id="hawbNo" style="width:100%;"/>
					</td>
				</tr>
			</tbody>
		</table>
		 -->
		<br/><br/><br/>
	</div>
		
		
	<input type="hidden" id="nno" name="nno" value="${inspStockOut[0].nno }">
	<c:forEach items="${inspStockOut}" var ="stockOut" varStatus="status">
		<c:if test="${status.count eq 1 }">
			<div class="row">
				<div class="col-xs-12 col-md-4 bigger-200 center" >
					상품명
				</div>
				<div class="col-xs-6 col-md-3 bigger-200 center" >
					확인 개수
				</div>
				<div class="col-xs-6 col-md-3 bigger-200 center" >
					총 개수
				</div>
			</div>
			
			<div class="space-10"></div>
		</c:if>		
		<div class="row">
			<div class="col-xs-12 col-md-4 bigger-120 center" >
				<label>${stockOut.itemDetail}</label>
			</div>
			<div class="col-xs-6 col-md-3 center">
				<input type="text" class="borderNone
				<c:if test = "${stockOut.subNo eq scanSubNo}"> chgVal</c:if>
				<c:if test = "${stockOut.subNo ne scanSubNo}"> Val</c:if> 
				" disabled id="${stockOut.subNo}" name="subNoCnt" value="${stockOut.whoutCnt}"/>
			</div>
			<div class="col-xs-6 col-md-3 center">
				<!-- 총 개수 -->
				<input type="text" class="borderNone
				<c:if test = "${stockOut.itemCnt eq stockOut.whoutCnt}"> greenbg</c:if>
				<c:if test = "${stockOut.itemCnt ne stockOut.whoutCnt}"> redbg</c:if> 
					" id="subNoTotal${stockOut.subNo}" disabled name="subNoTotal" value="${stockOut.itemCnt}"/>
				<!-- 총 개수 -->
				<c:if test = "${stockOut.itemCnt eq stockOut.whoutCnt}"> <input type="hidden" name="itemStatus" id="itemStatus${status.count}" value="T"> </c:if>
				<c:if test = "${stockOut.itemCnt ne stockOut.whoutCnt}"> <input type="hidden" name="itemStatus" id="itemStatus${status.count}" value="F"> </c:if>
			</div>
		</div>
		<div class="space-2"></div>
	</c:forEach>
	<div class="space-10"></div>
<!-- <<<<<<< HEAD
			
	<script type="text/javascript">
		var myTable = 
			$('#dynamic-table')
			.DataTable( {
				"dom" : "t",
				"paging":   false,
		        "ordering": false,
		        "info":     false,
		        "searching": false,
		        "autoWidth": false,
		        "scrollY" : 350,
				select: {
					style: 'multi',
				},
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
	</script>			
=======
						
>>>>>>> refs/heads/KBR_Branch
 -->