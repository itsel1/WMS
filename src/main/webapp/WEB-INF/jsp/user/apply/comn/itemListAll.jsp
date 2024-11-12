<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
	<head>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	  
	  .infoName{background: yellow;}
	  .profile-info-name, .profile-info-value {
	  		border-top: none;
	  		border-bottom: 1px dotted #D5E4F1;
  		}
	 </style>
	<!-- basic scripts -->
	</head> 
	<body>
		<div class="panel panel-default panels${cnt}">
			<input type="hidden" id="transCodes" value="${targetCode}">
			<input type="hidden" id="dstn_nation" value="${dstnNation}">
			<div class="panel-heading">
				<h4 class="panel-title">
					<span class="accordion-toggle" >
						<span data-toggle="collapse" href="#collapse${cnt}" >
						&nbsp;Item #${cnt}
						<i class="ace-icon fa fa-angle-double-down bigger-110" data-icon-hide="ace-icon fa fa-angle-double-down" data-icon-show="ace-icon fa fa-angle-right"></i>
						</span>
						<i class="fa fa-times bigger-110" style="float: right;" id="delBtns" name="delBtns" onclick="javascript:fn_delete(this);"></i>
					</span>
					
				</h4>
			</div>
		
			<div class="panel-collapse collapse multi-collapse in" id="collapse${cnt}">
				<div class="panel-body">
					<c:if test="${(!empty expressItemVO.trkComYnExpress) or (!empty expressItemVO.trkDateYnExpress)}">
					<div class="row  hr-8">
					<c:if test="${!empty expressItemVO.trkComYnExpress}">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.trkComYn}"> itemNm </c:if>" >국내 택배 회사</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.trkComYn}"> itemVal </c:if>" type="text" name="trkCom" id="trkCom${cnt}" value="${orderItem.trkCom}" />
								<input class="width-100" type="hidden" name="subNo" id="subNo${cnt}" value="${orderItem.subNo}" />
							</div>
						</div>
					</c:if>
					<c:if test="${!empty expressItemVO.trkDateYnExpress}">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.trkDateYn}"> itemNm </c:if>" >운송날짜</div>
							<div class="col-md-9 col-xs-12">
								<div class="input-group input-group-sm">
									<span class="input-group-addon">
										<i class="ace-icon fa fa-calendar"></i>
									</span>
									<input type="text" id="trkDate${cnt}" name="trkDate" class="width-100 checkItem <c:if test="${!empty optionItemVO.trkDateYn}"> itemVal </c:if>" onclick="javascript:test(this);" value="${orderItem.trkDate}"/>
								</div>
							</div>
						</div>
					</c:if>
					</div>
					</c:if>
					<c:if test="${(!empty expressItemVO.trkNoYnExpress) or (!empty expressItemVO.hsCodeYnExpress)}">
					<div class="row  hr-8">
					<c:if test="${!empty expressItemVO.trkNoYnExpress}">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.trkNoYn}"> itemNm </c:if>" >송장 번호</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.trkNoYn}"> itemVal </c:if>" type="text" name="trkNo" id="trkNo${cnt}" value="${orderItem.trkNo}" />
								<%-- <div class="input-group input-group-sm">
									
									<span class="input-group-addon" href="#modal-form" role="button" class="blue" data-toggle="modal">
										<i class="ace-icon fa fa-plus" id="hawbNoAdd" name="hawbNoAdd" style="font-size: 20px"></i>
									</span>
								</div> --%>
							</div>
						</div>
						</c:if>
						<c:if test="${!empty expressItemVO.hsCodeYnExpress}">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.hsCodeYn}"> itemNm </c:if>" >
								<a style="font-weight:bold; cursor:pointer;" onclick="fn_popupSearchCode('${cnt}')">HS CODE&nbsp;<i class="fa fa-external-link" id="searchIcon" style="font-size:14px; cursor:pointer;"></i></a>
								<%-- HS CODE&nbsp;<i class="fa fa-external-link" id="searchIcon${cnt}" style="font-size:14px; cursor:pointer;"></i> --%>
							</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.hsCodeYn}"> itemVal </c:if>" type="text" name="hsCode" id="hsCode${cnt}" value="${orderItem.hsCode}" autocomplete="off"/>
							</div>
						</div>
						</c:if>
					</div>
					</c:if>
					<c:if test="${(!empty expressItemVO.trkComYnExpress) or (!empty expressItemVO.trkDateYnExpress) or (!empty expressItemVO.trkNoYnExpress) or (!empty expressItemVO.hsCodeYnExpress)}"> 
					<div class="hr dotted"></div>
					</c:if>
					
					
					
					<div class="row  hr-8">
					<c:if test="${!empty expressItemVO.itemDetailYnExpress}">
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemDetailYn}"> itemNm </c:if>" >상품명</div>
							<div class="col-lg-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemDetailYn}"> itemVal </c:if>" type="text" name="itemDetail" id="itemDetail${cnt}" value="${orderItem.itemDetail}" />
							</div>
						</div>
					</c:if>
					<c:if test="${!empty expressItemVO.nativeItemDetailYnExpress}">
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.nativeItemDetailYn}"> itemNm </c:if>" >현지 상품명</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.nativeItemDetailYn}"> itemVal </c:if>" type="text" name="nativeItemDetail" id="nativeItemDetail${cnt}" value="${orderItem.nativeItemDetail}" />
							</div>
						</div>
					</c:if>
					
					<c:if test="${!empty expressItemVO.itemExplanYnExpress}">
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemExplanYn}"> itemNm </c:if>" >상품 상세설명</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemExplanYn}"> itemVal </c:if>" type="text" name="itemExplan" id="itemExplan${cnt}" value="${orderItem.itemExplan}" />
							</div>
						</div>
						
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 itemNm " >BOX 번호</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem itemVal" type="text" name="inBoxNum" id="inBoxNum${status.count}" value="${orderItem.inBoxNum}" />
							</div>
						</div>
					</c:if>
					
					<c:if test="${!empty expressItemVO.itemCntYnExpress}">	
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemCntYn}"> itemNm </c:if>" >상품 개수</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemCntYn}"> itemVal </c:if>" type="text" numberOnly name="itemCnt" id="itemCnt${cnt}" value="${orderItem.itemCnt}" />
							</div>
						</div>
					</c:if>
					<c:if test="${!empty expressItemVO.qtyUnitYnExpress}">	
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.qtyUnitYn}"> itemNm </c:if>" >상품 단위</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.qtyUnitYn}"> itemVal </c:if>" type="text" name="qtyUnit" id="qtyUnit${cnt}" value="${orderItem.qtyUnit}" />
							</div>
						</div>
					</c:if>
					<c:if test="${!empty expressItemVO.brandYnExpress}">	
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.brandYn}"> itemNm </c:if>" >BRAND</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.brandYn}"> itemVal </c:if>" type="text" name="brandItem" id="brand${cnt}" value="${orderItem.brand}" />
							</div>
						</div>
					</c:if>
					<c:if test="${!empty expressItemVO.unitValueYnExpress}">		
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.unitValueYn}"> itemNm </c:if>">상품 단가</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.unitValueYn}"> itemVal </c:if>" type="text" floatOnly name="unitValue" id="unitValue${cnt}" value="${orderItem.unitValue}" />
							</div>
						</div>
					</c:if>
					<c:if test="${!empty expressItemVO.itemDivYnExpress}">	
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemDivYn}"> itemNm </c:if>" >상품 종류</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemDivYn}"> itemVal </c:if>" type="text" name="itemDiv" id="itemDiv${cnt}" value="${orderItem.itemDiv}" />
							</div>
						</div>
					</c:if>
					<c:if test="${!empty expressItemVO.cusItemCodeYnExpress}">	
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.cusItemCodeYn}"> itemNm </c:if>" >상품 코드</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.cusItemCodeYn}"> itemVal </c:if>" type="text" name="cusItemCode" id="cusItemCode${cnt}" value="${orderItem.cusItemCode}" />
							</div>
						</div>
					</c:if>
					<c:if test="${!empty expressItemVO.itemMeterialYnExpress}">	
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemMeterialYn}"> itemNm </c:if>" >상품재질</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemMeterialYn}"> itemVal </c:if>" type="text" name="itemMeterial" id="itemMeterial${cnt}" value="${orderItem.itemMeterial}" />
							</div>
						</div>
					</c:if>
					<div class="col-md-6 col-xs-12 hr-8">
						<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemUserWtaYn}"> itemNm </c:if>" >상품 무게</div>
						<div class="col-md-9 col-xs-12">
							<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemUserWtaYn}"> itemVal </c:if>" floatOnly type="text" name="userItemWta" id="userItemWta${cnt}" value="${orderItem.useItemrWta}" />
						</div>
					</div>
					<%-- <c:if test="${!empty expressItemVO.hsCodeYnExpress}">	
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >상품 실무게</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem" type="text" name="userWtaItem" id="userWta${cnt}" value="" />
							</div>
						</div>
					</c:if>
					<c:if test="${!empty expressItemVO.hsCodeYnExpress}">
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >상품 부피무게</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem" type="text" name="userWtcItem" id="userWtc${cnt}" value="" />
							</div>
						</div>
					</c:if> --%>
					<c:if test="${!empty expressItemVO.itemWtUnitYnExpress}">
						<div class="col-md-6 col-xs-12 hr-8 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemWtUnitYn}"> itemNm </c:if>" >무게 단위</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemWtUnitYn}"> itemVal </c:if>" type="text" name="wtUnitItem" id="wtUnitItem${cnt}" value="${orderItem.wtUnit}" />
							</div>
						</div>
					</c:if>
					<c:if test="${!empty expressItemVO.chgCurrencyYnExpress}">
							<div class="col-md-6 col-xs-12 hr-8">
								<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.chgCurrencyYn}"> itemNm </c:if>" >통화 단위</div>
								<div class="col-md-9 col-xs-12">
									<%-- <input class="width-100 checkItem <c:if test="${!empty optionItemVO.chgCurrencyYn}"> itemVal </c:if>" type="text" name="chgCurrency" id="chgCurrency${cnt}" value="${orderItem.chgCurrency}" /> --%>
									<select class="width-100 checkItem <c:if test="${!empty optionItemVO.chgCurrencyYn}"> itemVal </c:if>" id="chgCurrency${cnt}" name="chgCurrency">
										<c:forEach items="${currencyList}" var="currencyList">
											<option <c:if test="${currencyList.nationCode eq dstnNation}">selected</c:if> value="${currencyList.currency}">${currencyList.currency} / ${currencyList.nationName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
					</c:if>
					
					<c:if test="${!empty expressItemVO.itemBarcodeYnExpress}">
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemBarcodeYn}"> itemNm </c:if>" >상품 바코드</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemBarcodeYn}"> itemVal </c:if>" type="text" name="itemBarcode" id="itemBarcode${cnt}" value="${orderItem.itemBarcode}" />
							</div>
						</div>
					</c:if>
						
					</div>
					
					<div class="hr dotted"></div>
						
					<!-- 
					<div class="row  hr-8">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >포장 단위</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem" type="text" name="packageUnit" id="packageUnit${cnt}" value="" />
							</div>
						</div>
					</div>
					<div class="row  hr-8">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >환율</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem" type="text" name="exchangeRate" id="exchangeRate${cnt}" value="" />
							</div>
						</div>
					</div>
					
					<div class="row  hr-8">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >환전 여부</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem" type="text" name="chgCurrency" id="chgCurrency${cnt}" value="" />
							</div>
						</div>
					</div>
					<div class="row  hr-8">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >환전 금액</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem" type="text" name="chgAmt" id="chgAmt${cnt}" value="" />
							</div>
						</div>
					</div>
					
					<div class="row  hr-8">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >사입코드</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem" type="text" name="takeInCode" id="takeInCode${cnt}" value="" />
							</div>
						</div>
					</div>
					<div class="hr dotted"></div> -->
					
					<c:if test="${(!empty expressItemVO.makeCntryYnExpress) or (!empty expressItemVO.makeComYnExpress)}">
					<div class="row  hr-8">
					<c:if test="${!empty expressItemVO.makeCntryYnExpress}">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.makeCntryYn}"> itemNm </c:if>" >제조국</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.makeCntryYn}"> itemVal </c:if>" type="text" name="makeCntry" id="makeCntry${cnt}" value="${orderItem.makeCntry}" />
							</div>
						</div>
					</c:if>
					<c:if test="${!empty expressItemVO.makeComYnExpress}">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.makeComYn}"> itemNm </c:if>" >제조 회사</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.makeComYn}"> itemVal </c:if>" type="text" name="makeCom" id="makeCom${cnt}" value="${orderItem.makeCom}" />
							</div>
						</div>
					</c:if>
					</div>
					</c:if>
					<c:if test="${(!empty expressItemVO.itemUrlYnExpress) or (!empty expressItemVO.itemImgUrlYnExpress)}">
					<div class="row  hr-8">
					<c:if test="${!empty expressItemVO.itemUrlYnExpress}">	
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemUrlYn}"> itemNm </c:if>">상품 URL</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemUrlYn}"> itemVal </c:if>" type="text" name="itemUrl" id="itemUrl${cnt}" value="${orderItem.itemUrl}" />
							</div>
						</div>
					</c:if>
					<c:if test="${!empty expressItemVO.itemImgUrlYnExpress}">
						<div class="col-md-6 col-xs-12 hr-8">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemImgUrlYn}"> itemNm </c:if>">상품 이미지 URL</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemImgUrlYn}"> itemVal </c:if>" type="text" name="itemImgUrl" id="itemImgUrl${cnt}" value="${orderItem.itemImgUrl}" />
							</div>
						</div>
					</c:if>
					</div>
					</c:if>
				</div>
			</div>
		</div>
		<!-- Footer Start -->
		<!-- Footer End -->
		
		<!-- ace scripts -->
		<!-- script on paging end -->
		
		<!-- script addon start -->
		
		
		<!-- script addon end -->
	</body>
	<script type="text/javascript">
	jQuery(function($) {
		$(document).ready(function() {
			console.log("ASD");
			$("#trkDate"+cnt).trigger('click');
			$("#trkCom"+cnt).focus();
			$(".shipperNm").addClass('red');
			$(".cneeNm").addClass('red');
			$(".orderNm").addClass('red');
			$(".itemNm").addClass('red');

			if ($("#trans_code").val() == 'FB') {
				if ($("#destination").val() == 'JP') {
					$("input[name='itemDiv']").attr('placeholder', '통관용인 영문으로 입력해 주세요.');
				} else {
					$("input[name='itemDiv']").attr('placeholder', '');
				}
			}

/*
			$("#searchIcon"+cnt).on('click', function() {
				
				var _width = '480';
			    var _height = '480';
			    var _left = (screen.width/2)-(_width/2);
			    var _top = (screen.height/2)-(_height/2);
			    
				window.open("/cstmr/apply/searchItemCode?cnt="+cnt, "HS_CODE", 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);

			})
*/
			function fn_popupSearchCode(num) {
				var _width = '520';
			    var _height = '360';
			    var _left = (screen.width/2)-(_width/2);
			    var _top = (screen.height/2)-(_height/2);
			    
				window.open("/cstmr/apply/searchItemCode?cnt="+num, "HS_CODE", 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
			}	
		})
	});
	</script>
</html>
