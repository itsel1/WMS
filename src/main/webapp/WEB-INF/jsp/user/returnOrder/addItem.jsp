<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
	<head>

	</head>
	<body>
		<table class="panel panel-default panels${cnt} item-tbl" style="width:100%;margin-bottom:0px;">
			<tr>
				<th colspan="4" style="text-align:right !important;padding-right:10px;background:rgba(76, 144, 189, 0.72);">
					<input type="button" class="item-del" value="삭제" onclick="javascript:fn_deleteItem(this);">
				</th>
			</tr>
			<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
				<th class="req">상품명</th>
				<td><input type="text" name="itemDetail" class="itemDetail" placeholder="영문 기입"></td>
				<th>현지 상품명</th>
				<td><input type="text" name="nativeItemDetail" class="nativeItemDetail"></td>
			</tr>
			<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
				<th>상품코드</th>
				<td><input type="text" name="cusItemCode" class="cusItemCode"></td>
				<th>브랜드</th>
				<td><input type="text" name="brand" class="brand"></td>
			</tr>
			<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
				<th>상품 무게</th>
				<td><input type="text" name="itemWta" class="itemWta"></td>
				<th>무게 단위</th>
				<td><input type="text" name="wtUnit" class="wtUnit"></td>
			</tr>
			<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
				<th class="req">상품 수량</th>
				<td><input type="text" name="itemCnt" class="itemCnt"></td>
				<th class="req">판매 단가</th>
				<td><input type="text" name="unitValue" class="unitValue"></td>
			</tr>
			<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
				<th class="req">통화 단위</th>
				<td>
					<select class="chosen-select-start" name="unitCurrency" class="unitCurrency">
						<c:forEach items="${currencyList}" var="currencyList">
							<option <c:if test="${currencyList.currency eq 'USD'}"> selected </c:if> value="${currencyList.currency}">${currencyList.currency} (${currencyList.nationName} / ${currencyList.nationEName})</option>
						</c:forEach>
					</select>
				</td>
				<th class="req">제조국</th>
				<td><input type="text" name="makeCntry" class="makeCntry"></td>
			</tr>
			<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
				<th>제조 회사</th>
				<td><input type="text" name="makeCom" class="makeCom"></td>
				<th class="req">HS CODE</th>
				<td><input type="text" name="hsCode" class="hsCode"></td>
			</tr>
			<tr>
				<th>상품 url</th>
				<td><input type="text" name="itemUrl" class="itemUrl"></td>
				<th>상품 이미지 url</th>
				<td><input type="text" name="itemImgUrl" class="itemImgUrl"></td>
			</tr>
		</table>
		<script type="text/javascript">
			if(!ace.vars['touch']) {
				$('.chosen-select-start').chosen({allow_single_deselect:true, search_contains: true }); 
				//resize the chosen on window resize
	
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select-start').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				}).trigger('resize.chosen');
				//resize chosen on sidebar collapse/expand
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select-start').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				});
			}
		</script>
	</body>
</html>