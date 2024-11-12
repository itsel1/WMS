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
					<div class="col-lg-3 col-xs-12 pd-1rem" >상품명</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="itemDetail" id="itemDetail${cnt}" value="" />
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
					<div class="col-lg-3 col-xs-12 pd-1rem" >BRAND</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="brandItem" id="brand${cnt}" value="" />
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
					<div class="col-lg-3 col-xs-12 pd-1rem" >단가</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="unitValue" id="unitValue${cnt}" value="" />
					</div>
				</div>
			</div>
			
			
			<div class="row form-group hr-8">
				<div class="col-lg-12 col-xs-12">
					<div class="col-lg-3 col-xs-12 pd-1rem" >제조국가</div>
					<div class="col-lg-6 col-xs-12">
						<input class="width-100" type="text" name="makeCntry" id="makeCntry${cnt}" value="" />
					</div>
				</div>
			</div>
			
		</div>
	</div>
</div>
