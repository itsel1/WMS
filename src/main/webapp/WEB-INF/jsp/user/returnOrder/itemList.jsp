<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
	<head>

	<link rel="stylesheet" href="/assets/css/orderIn.css"/>
	</head> 
	<body>
		<div class="panel panel-default panels${cnt}">
			<div class="panel-heading">
				<h4 class="panel-title">
					<span class="accordion-toggle">
						<span data-toggle="collapse" href="#collapse${cnt}">
							Item #${cnt}
							<i class="ace-icon fa fa-angle-double-down bigger-110" data-icon-hide="ace-icon fa fa-angle-double-down" data-icon-show="ace-icon fa fa-angle-right"></i>
						</span>
						<i class="fa fa-times bigger-110" style="float: right;" id="delBtns" name="delBtns" onclick="javascript:fn_delete(this);"></i>
					</span>
				</h4>
			</div>
			<div class="panel-collapse collapse multi-collapse in" id="collapse${cnt}">
				<div class="panel-body">
					<div class="col-xs-12 col-lg-12">
						<div class="col-xs-12 col-lg-12" style="font-size: 3;">
							<div class="row hr-8">
								<div class="col-xs-1 center mp07">
									<label class="red">상품명</label>
								</div>
								<div class="col-xs-3">
									<input class="col-xs-12 itemDetail" type="text" name="itemDetail[]" id="itemDetail${cnt}" placeholder="영문 기입">
								</div>
								<div class="col-xs-1 center mp07">
									<label>현지 상품명</label>
								</div>
								<div class="col-xs-3">
									<input class="col-xs-12 nativeItemDetail" type="text" name="nativeItemDetail[]" id="nativeItemDetail${cnt}">
								</div>
								<div class="col-xs-1 center mp07">
									<label>상품코드</label>
								</div>
								<div class="col-xs-3">
									<input class="col-xs-12 cusItemCode" type="text" name="cusItemCode[]" id="cusItemCode${cnt}">
								</div>
							</div>
							<div class="hr dotted"></div>
							<div class="row hr-8">
								<div class="col-xs-1 center mp07">
									<label>브랜드</label>
								</div>
								<div class="col-xs-3">
									<input class="col-xs-12 brand" type="text" name="brand[]" id="brand${cnt}">
								</div>
								<div class="col-xs-1 center mp07">
									<label>상품 무게</label>
								</div>
								<div class="col-xs-3">
									<input class="col-xs-12 itemWta" type="text" name="itemWta[]" id="itemWta${cnt}" onkeyup="$(this).val($(this).val().replace(/[^0-9|.]/g,''));">
								</div>
								<div class="col-xs-1 center mp07">
									<label>무게 단위</label>
								</div>
								<div class="col-xs-3">
									<input class="col-xs-12 wtUnit" type="text" name="wtUnit[]" id="wtUnit${cnt}">
								</div>
							</div>
							<div class="hr dotted"></div>
							<div class="row hr-8">
								<div class="col-xs-1 center mp07">
									<label class="red">상품 수량</label>
								</div>
								<div class="col-xs-3">
									<input class="col-xs-12 itemCnt" type="text" name="itemCnt[]" id="itemCnt${cnt}" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''));">
								</div>
								<div class="col-xs-1 center mp07">
									<label class="red">판매 단가</label>
								</div>
								<div class="col-xs-3">
									<input class="col-xs-12 unitValue" type="text" name="unitValue[]" id="unitValue${cnt}" onkeyup="$(this).val($(this).val().replace(/[^0-9|.]/g,''));">
								</div>
								<div class="col-xs-1 center mp07">
									<label class="red">통화 단위</label>
								</div>
								<div class="col-xs-3">
									<select class="chosen-select-start unitCurrency" id="unitCurrency${cnt}" name="unitCurrency[]">
									<c:forEach items="${currencyList}" var="currencyList">
										<option <c:if test="${currencyList.currency eq 'USD'}"> selected </c:if> value="${currencyList.currency}">${currencyList.currency} (${currencyList.nationName} / ${currencyList.nationEName})</option>
									</c:forEach>
									</select>
								</div>
							</div>
							<div class="hr dotted"></div>
							<div class="row hr-8">
								<div class="col-xs-1 center mp07">
									<label class="red">제조국</label>
								</div>
								<div class="col-xs-3">
									<input class="col-xs-12 makeCntry" type="text" name="makeCntry[]" id="makeCntry${cnt}">
								</div>
								<div class="col-xs-1 center mp07">
									<label>제조 회사</label>
								</div>
								<div class="col-xs-3">
									<input class="col-xs-12 makeCom" type="text" name="makeCom[]" id="makeCom${cnt}">
								</div>
								<div class="col-xs-1 center mp07">
									<label>
										<a href="https://unipass.customs.go.kr/clip/index.do" style="color:#DD5A43;" target="_blank" title="HS Code 조회 사이트로 가기" >HS CODE
										<i class="fa fa-external-link"></i></a>
						</label>
								</div>
								<div class="col-xs-3">
									<input class="col-xs-12 hsCode" type="text" name="hsCode[]" id="hsCode${cnt}" onkeyup="$(this).val($(this).val().replace(/[^0-9|.-]/g,''));">
								</div>
							</div>
							<div class="hr dotted"></div>
							<div class="row hr-8">
								<div class="col-xs-1 center mp07">
									<label>상품 URL</label>
								</div>
								<div class="col-xs-3">
									<input class="col-xs-12 itemUrl" type="text" name="itemUrl[]" id="itemUrl${cnt}">
								</div>
								<div class="col-xs-1 center mp07">
									<label class="red">상품 이미지 URL</label>
								</div>
								<div class="col-xs-3">
									<input class="col-xs-12 itemImgUrl" type="text" name="itemImgUrl[]" id="itemImgUrl${cnt}">
								</div>
							</div>
							<div class="hr dotted"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
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
