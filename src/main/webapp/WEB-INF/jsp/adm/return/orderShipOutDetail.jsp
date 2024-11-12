<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<style type="text/css">
	</style>
</head>
	<div id="tbl-div">
		<div class="tbl-return" id="tbl-list">
			<div class="tbl-header">
				<table cellpadding="0" cellspacing="0" border="0">
					<thead>
						<tr>
							<!-- <th style="border-right:1px solid #fff;width:10%;word-break:break-all;">
								<label class="pos-rel">
									<input type="checkbox" class="ace" id="checkAll"/> <span class="lbl"></span>
								</label>
							</th> -->
							<th style="border-right:1px solid #fff;width:25%;word-break:break-all;">재고번호</th>
							<th style="border-right:1px solid #fff;width:10%;word-break:break-all;">Rack</th>
							<th style="width:55%;word-break:break-all;">품명</th>
						</tr>
					</thead>
				</table>
			</div>
			<div class="tbl-content">
				<table cellpadding="0" cellspacing="0" border="0">
					<tbody>
						<c:if test="${fn:length(stockInfo) eq 0}">
							<tr>
								<td style="text-align:center;word-break:break-all;" colspan="3">데이터가 없습니다</td>
							</tr>
						</c:if>
						<c:forEach items="${stockInfo}" var="stockInfo" varStatus="status">
							<tr>
								<td style="width:25%;word-break:break-all;">${stockInfo.stockNo}</td>
								<td style="width:10%;word-break:break-all;">${stockInfo.rackCode}</td>
								<td style="width:55%;word-break:break-all;">${stockInfo.itemDetail}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div id="stockOut-div">
		<input type="hidden" id="subNo" value="${subNo}">
		<input type="hidden" id="scanCnt" value="${scanCnt}"> 
		<table id="stockOut-table">
			<thead>
				<tr>
					<th>상품명</th>
					<th>출고 수량</th>
					<th>상품 수량</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${stockOut}" var="stockOut">
					<tr>
						<td>${stockOut.itemDetail}</td>
						<td>
							<input type="text" class="borderNone
							<c:if test="${stockOut.subNo eq subNo}"> nowScanNo</c:if>
							<c:if test="${stockOut.subNo ne subNo}"> scanNo</c:if>
							" disabled id="${stockOut.subNo}" value="${stockOut.whoutCnt}">
						</td>
						<td>
							<input type="text" class="borderNone
							<c:if test="${stockOut.itemCnt eq stockOut.whoutCnt}"> greenBg</c:if>
							<c:if test="${stockOut.itemCnt ne stockOut.whoutCnt}"> redBg</c:if>
							" disabled id="subNoTotal${stockOut.subNo}" value="${stockOut.itemCnt}">
							<c:if test="${stockOut.itemCnt eq stockOut.whoutCnt}"><input type="hidden" name="itemStatus" id="itemStatus${stockOut.subNo}" value="T"></c:if>
							<c:if test="${stockOut.itemCnt ne stockOut.whoutCnt}"><input type="hidden" name="itemStatus" id="itemStatus${stockOut.subNo}" value="F"></c:if>
						</td>
					</tr>	
				</c:forEach>
			</tbody>
		</table>
	</div>
</html>