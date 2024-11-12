<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="panel panel-default panels${cnt}">
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
			<div class="row form-group hr-8">
			<div class="col-lg-12 col-xs-12">
				<div class="col-lg-4 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >HS_CODE</div>
					<div class="col-lg-9 col-xs-12">
						<input class="width-100" type="text" name="hsCode" id="hsCode${cnt}" value="" />
						<input class="width-100" type="hidden" name="subno" id="subno" value="${cnt}" />
					</div>
				</div>
				<div class="col-lg-4 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >상품 종류</div>
					<div class="col-lg-9 col-xs-12">
						<input class="width-100" type="text" name="itemDiv" id="itemDiv${cnt}" value="" />
					</div>
				</div>
				<div class="col-lg-4 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >BRAND</div>
					<div class="col-lg-9 col-xs-12">
						<input class="width-100" type="text" name="brandItem" id="brand${cnt}" value="" />
					</div>
				</div>
				</div>
			</div>
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >상품 상세</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="itemDetail" id="itemDetail${cnt}" value="" />
					</div>
				</div>
			</div>
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >상품 개수</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="itemCnt" id="itemCnt${cnt}" value="" />
					</div>
				</div>
			</div>
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >상품재질</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="itemMeterial" id="itemMeterial${cnt}" value="" />
					</div>
				</div>
			</div>
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >ITEM_URL</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="itemUrl" id="itemUrl${cnt}" value="" />
					</div>
				</div>
			</div>
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >ITEM_IMAGE_URL</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="itemImgUrl" id="itemImgUrl${cnt}" value="" />
					</div>
				</div>
			</div>
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >USER_WTA</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="userWtaItem" id="userWta${cnt}" value="" />
					</div>
				</div>
			</div>
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >USER_WTC</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="userWtcItem" id="userWtc${cnt}" value="" />
					</div>
				</div>
			</div>
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >WT_UNIT</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="wtUnit" id="wtUnit${cnt}" value="" />
					</div>
				</div>
			</div>
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >UNIT_VALUE</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="unitValue" id="unitValue${cnt}" value="" />
					</div>
				</div>
			</div>
			
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >QTY_UNIT</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="qtyUnit" id="qtyUnit${cnt}" value="" />
					</div>
				</div>
			</div>
			
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >MAKE_CNTRY</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="makeCntry" id="makeCntry${cnt}" value="" />
					</div>
				</div>
			</div>
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >MAKE_COM</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="makeCom" id="makeCom${cnt}" value="" />
					</div>
				</div>
			</div>
			
			
			
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >PACKAGE_UNIT</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="packageUnit" id="packageUnit${cnt}" value="" />
					</div>
				</div>
			</div>
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >EXCHANGE_RATE</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="exchangeRate" id="exchangeRate${cnt}" value="" />
					</div>
				</div>
			</div>
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >CHG_CURRENCY</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="chgCurrency" id="chgCurrency${cnt}" value="" />
					</div>
				</div>
			</div>
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >CHG_AMT</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="chgAmt" id="chgAmt${cnt}" value="" />
					</div>
				</div>
			</div>
			
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >TAKE_IN_CODE</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="takeInCode" id="takeInCode${cnt}" value="" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
