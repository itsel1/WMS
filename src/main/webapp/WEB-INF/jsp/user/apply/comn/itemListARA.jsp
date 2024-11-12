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
					<div class="row  hr-8">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >국내 택배 회사</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100" type="text" name="trkCom" id="trkCom${cnt}" value="" />
							</div>
						</div>
					
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >운송날짜</div>
							<div class="col-md-9 col-xs-12">
								<div class="input-group input-group-sm">
									<span class="input-group-addon">
										<i class="ace-icon fa fa-calendar"></i>
									</span>
									<input type="text" id="trkDate${cnt}" dateChk name="trkDate" class="form-control" onclick="javascript:test(this);" value=""/>
								</div>
							</div>
						</div>
					</div>
					<div class="row  hr-8">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >송장 번호</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100" type="text" name="trkNo" id="trkNo${cnt}"value="" />
							</div>
						</div>
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07" >HS CODE</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100" type="text" name="hsCode" id="hsCode1" value="" />
							</div>
						</div>
					</div>
					
					<div class="hr dotted"></div>
					
					<div class="row  hr-8">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >상품 코드</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100" type="text" name="cusItemCode" id="cusItemCode${cnt}" value="" />
							</div>
						</div>
						
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >상품 종류</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100" type="text" name="itemDiv" id="itemDiv${cnt}" value="" />
							</div>
						</div>
					</div>
					
					<div class="row  hr-8">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >BRAND</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100" type="text" name="brandItem" id="brand${cnt}" value="" />
							</div>
						</div>
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >상품명</div>
							<div class="col-lg-9 col-xs-12">
								<input class="width-100" type="text" name="itemDetail" id="itemDetail${cnt}" value="" />
							</div>
						</div>
					</div>
					
					<div class="row  hr-8">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >상품 개수</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100" type="text" name="itemCnt" id="itemCnt${cnt}" value="" />
							</div>
						</div>
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"">상품 단위 가격</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100" type="text" name="unitValue" id="unitValue${cnt}" value="" />
							</div>
						</div>
					</div>
					
					<div class="row  hr-8">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >상품 단위</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100" type="text" name="qtyUnit" id="qtyUnit${cnt}" value="" />
							</div>
						</div>
					</div>
					<div class="hr dotted"></div>
					<div class="row  hr-8">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >제조국</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100" type="text" name="makeCntry" id="makeCntry${cnt}" value="" />
							</div>
						</div>
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >제조 회사</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100" type="text" name="makeCom" id="makeCom${cnt}" value="" />
							</div>
						</div>
					</div>
					<div class="hr dotted"></div>
					<div class="row  hr-8">
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07" center mp07" >ITEM_URL</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100" type="text" name="itemUrl" id="itemUrl${cnt}" value="" />
							</div>
						</div>
						<div class="col-md-6 col-xs-12">
							<div class="col-md-3 col-xs-12 pd-1rem center mp07" center mp07" >ITEM_IMAGE_URL</div>
							<div class="col-md-9 col-xs-12">
								<input class="width-100" type="text" name="itemImgUrl" id="itemImgUrl${cnt}" value="" />
							</div>
						</div>
					</div>
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
		})
	});
	</script>
</html>
