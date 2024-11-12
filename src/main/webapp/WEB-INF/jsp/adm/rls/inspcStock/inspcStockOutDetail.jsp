<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		
	<div class="col-xs-9 col-xs-offset-1">
			<table class="table table table-bordered table-hover">
				<thead>
					<tr>
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
							
