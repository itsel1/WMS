<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<!-- headMenu Start -->
	<%-- <div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important; width:90%" id="add${cnt}" name="add${cnt}">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<h4>상품  ${cnt}</h4>
		<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
			<div class="profile-user-info  form-group hr-8">
				<div class="profile-info-name subNo" style="width:150px;">SUBNO</div>
				<div class="profile-info-value">
						<input class="col-xs-12 subNo" type="text" name="subNo" id="subNo" value="${item.subNo}" style="width:200px;" readonly/>
				</div>
				<div class="profile-info-name" style="width:150px;">상품명</div>
				<div class="profile-info-value">
						<input class="col-xs-12 backYellow" type="text" name="itemDetail" id="itemDetail" value="${item.itemDetail}" style="width:90%;" />
				</div>
			</div>
		</div>
		<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
			<div class="profile-user-info  form-group hr-8">
				<div class="profile-info-name" style="width:150px;">브랜드</div>
				<div class="profile-info-value">
						<input class="col-xs-12 backYellow" type="text" name="brand" id="brand" value="${item.brand}" style="width:90%;" />
				</div>
				<div class="profile-info-name" style="width:150px;">상품 무게</div>
				<div class="profile-info-value">
						<input class="col-xs-12 backYellow" type="text" name="itemWta" id="itemWta" value="${item.itemWta}" style="width:90%;" />
				</div>
				<div class="profile-info-name" style="width:150px;">무게 단위</div>
				<div class="profile-info-value">
					<input class="col-xs-12 backYellow" type="text" name="wtUnit" id="wtUnit" value="${item.wtUnit}" style="width:100%;" />
				</div>
			</div>
		</div>
		<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
			<div class="profile-user-info  form-group hr-8">
				<div class="profile-info-name" style="width:150px;">상품 개수</div>
				<div class="profile-info-value">
					<input class="col-xs-12 backYellow" type="text" name="itemCnt" id="itemCnt" value="${item.itemCnt}" style="width:100%; "/>
				</div>
				<div class="profile-info-name" style="width:150px;">상품가격</div>
				<div class="profile-info-value">
					<input class="col-xs-12 backYellow" type="text" name="unitValue" id="unitValue" value="${item.unitValue}" style="width:100%;"/>
				</div>
				<div class="profile-info-name" style="width:150px;">통화</div>
				<div class="profile-info-value">
					<input class="col-xs-12 backYellow" type="text" name="unitCurrency" id="unitCurrency" value="${item.unitCurrency}" style="width:100%;"/>																		
				</div>
			</div>
		</div>
		<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">	
			<div class="profile-user-info  form-group hr-8">
				<div class="profile-info-name" style="width:150px;">제조국</div>
				<div class="profile-info-value">
						<input class="col-xs-12 backYellow" type="text" name="makeCntry" id="makeCntry" value="${item.makeCntry}" style="width:200px;" />
				</div>
				<div class="profile-info-name" style="width:150px;">제조회사</div>
				<div class="profile-info-value">
						<input class="col-xs-12 backYellow" type="text" name="makeCom" id="makeCom" value="${item.makeCom}" style="width:200px;" />
				</div>
				<div class="profile-info-name" style="width:150px;">HS CODE</div>
				<div class="profile-info-value">
						<input class="col-xs-12 backYellow" type="text" name="hsCode" id="hsCode" value="${item.hsCode}" style="width:200px;" />
				</div>
			</div>
		</div>
		<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
			<div class="profile-user-info  form-group hr-8">
				<div class="profile-info-name" style="width:150px;">상품 URL</div>
				<div class="profile-info-value">
						<input class="col-xs-12 backYellow" type="text" name="itemUrl" id="itemUrl" value="${item.itemUrl}" style="width:80%;" />
				</div>
			</div>		
			<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">		
			</div>
			<div class="profile-user-info  form-group hr-8">
				<div class="profile-info-name" style="width:150px;">상품 이미지 URL</div>
				<div class="profile-info-value">
						<input class="col-xs-12 backYellow" type="text" name="itemImgUrl" id="itemImgUrl" value="${item.itemImgUrl}" style="width:80%" />
				</div>
			</div>
			<input type="button" class="button" onclick="getIndex2(${voStatus.index})" name="deleteItemBtn" value="삭제" class="button button2" id="deleteBtn" style="float:right">
		</div>	
	</div> --%>
	<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important" id="add${cnt}" name="add${cnt}">
		<div class="col-xs-12 col-lg-12" style="font-size: 3;">
			<input type="hidden" name="dstnNation" id="dstnNation" value="${dstnNation}"/>
			<h4>상품 ${cnt}</h4>
			<div class="hr dotted"></div>
			<div class="row hr-8">
				<div class="col-xs-1 center mp07">
					<font class="red">*&nbsp;</font><label>상품명</label>
				</div>
				<div class="col-xs-3">
					<input class="col-xs-12 itemDetail require" type="text" name="itemDetail" id="itemDetail" value="${item.itemDetail}" placeholder="한국 제외 도착지는 영문으로 입력해 주세요." />
				</div>
				<div class="col-xs-1 center mp07">
					<font class="red hide nativeItemDetail_ft" id="nativeItemDetail_ft">*&nbsp;</font><label>상품명 (일문)</label>
				</div>
				<div class="col-xs-3">
					<input class="col-xs-12 nativeItemDetail" type="text" name="nativeItemDetail" id="nativeItemDetail" value="${item.nativeItemDetail}" placeholder="도착지가 일본일 경우 필수입니다."/>
				</div>
				<div class="col-xs-1 center mp07">
					<label><font class="red hide cusItemCode_ft" id="cusItemCode_ft">*&nbsp;</font>상품코드</label>
				</div>
				<div class="col-xs-3">
					<input class="col-xs-12 cusItemCode" type="text" name="cusItemCode" id="cusItemCode" value="${item.cusItemCode}" placeholder="도착지가 일본일 경우 필수입니다."/>
				</div>
			</div>
			<div class="hr dotted"></div>
			<div class="row hr-8">
				<div class="col-xs-1 center mp07">브랜드</div>
				<div class="col-xs-3 center">
					<input class="col-xs-12 brand" type="text" name="brand" id="brand" value="${item.brand}"/>
				</div>
				<div class="col-xs-1 center"><font class="red">*&nbsp;</font>상품 무게</div>
				<div class="col-xs-3 center">
					<input class="col-xs-12 itemWta require" priceOnly type="text" name="itemWta" id="itemWta" value="${item.itemWta}" />
				</div>
				<div class="col-xs-1 center">무게 단위</div>
				<div class="col-xs-3 center">
					<input class="col-xs-12 wtUnit" type="text" name="wtUnit" id="wtUnit" value="${item.wtUnit}" />
				</div>
			</div>
			<div class="hr dotted"></div>
			<div class="row form-group hr-8">
				<div class="col-xs-1 center"><font class="red">*&nbsp;</font>상품 수량</div>
				<div class="col-xs-3 center">
					<input class="col-xs-12 itemCnt require" numberOnly type="text" name="itemCnt" id="itemCnt" value="${item.itemCnt}" />
				</div>
				<div class="col-xs-1 center mp07"><font class="red">*&nbsp;</font>상품 가격</div>
				<div class="col-xs-3 center">
					<input class="col-xs-12 unitValue require" priceOnly type="text" name="unitValue" id="unitValue" value="${item.unitValue}" />
				</div>
				<div class="col-xs-1 center mp07"><font class="red">*&nbsp;</font>통화</div>
				<div class="col-xs-3 center ">
					<select class="chosen-select-start form-control tag-input-style unitCurrency" id="unitCurrency" name="unitCurrency">
						<c:forEach items="${currencyList}" var="currencyList">
							<option value="${currencyList.currency}">${currencyList.currency} (${currencyList.nationName} / ${currencyList.nationEName})</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="hr dotted"></div>
			<div class="row hr-8">
				<div class="col-xs-1 center mp07">
					<label><font class="red">*&nbsp;</font>제조국</label>
				</div>
				<div class="col-xs-3 center">
					<input class="col-xs-12 makeCntry require" type="text" name="makeCntry" id="makeCntry" value="${item.makeCntry}" />
				</div>
				<div class="col-xs-1 center mp07">
					<label>제조회사</label>
				</div>
				<div class="col-xs-3 center">
					<input class="col-xs-12 makeCom" type="text" name="makeCom" id="makeCom" value="${item.makeCom}" />
				</div>
				<div class="col-xs-1 center mp07">
					<label>
					<font class="red hide hsCode_ft" id="hsCode_ft">*&nbsp;</font>
					<a href="https://unipass.customs.go.kr/clip/index.do" target="_blank" title="HS Code 조회 사이트로 가기" >HS CODE
					<i class="fa fa-exclamation-circle"></i>
					</a></label>
				</div>
				<div class="col-xs-3 center ">
					<input class="col-xs-12 hsCode" type="text" name="hsCode" id="hsCode" value="${item.hsCode}" placeholder="도착지가 일본일 경우 필수입니다."/>
				</div>
			</div>
			<div class="hr dotted"></div>
			<div class="row hr-8">
				<div class="col-xs-1 center mp07">
					<label>상품 URL</label>
				</div>
				<div class="col-xs-3">
					<input class="col-xs-12 itemUrl" type="text" name="itemUrl" id="itemUrl" value="${item.itemUrl}" />
				</div>
				<div class="col-xs-1 center mp07">
					<label><font class="red hide itemImgUrl_ft" id="itemImgUrl_ft">*&nbsp;</font>상품 이미지 URL</label>
				</div>
				<div class="col-xs-3">
					<input class="col-xs-12 itemImgUrl" type="text" name="itemImgUrl" id="itemImgUrl" value="${item.itemImgUrl}" />
				</div>
			</div>
			<div class="hr dotted"></div>
		</div>
	</div>
	<script type="text/javascript">
	jQuery(function($) {
		$(document).ready(function() {

			var value = $("#dstnNation").val();

			if (value == 'JP') {
				$(".pickupMobile_ft").removeClass('hide');
				$(".nativeItemDetail_ft").removeClass('hide');
				$(".cusItemCode_ft").removeClass('hide');
				$(".hsCode_ft").removeClass('hide');
				$(".itemImgUrl_ft").removeClass('hide');
			
			} else {
				$(".pickupMobile_ft").addClass('hide');
				$(".nativeItemDetail_ft").addClass('hide');
				$(".cusItemCode_ft").addClass('hide');
				$(".hsCode_ft").addClass('hide');
				$(".itemImgUrl_ft").addClass('hide');
			}


		});

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

		$("input:text[numberOnly]").on("keyup", function() {
            $(this).val($(this).val().replace(/[^0-9]/g,""));
        });
        
		$("input:text[zipCodeOnly]").on('keyup', function() {
			$(this).val($(this).val().replace(/[^0-9|-]/g,""));
		});

		$("input:text[priceOnly]").on('keyup', function() {
			$(this).val($(this).val().replace(/[^0-9.]/g,""));
		});
	});
	
	</script>
</html>
