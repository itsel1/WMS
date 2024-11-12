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
					<div class="row form-group hr-8">
						<div class="col-lg-12 col-xs-12">
							<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >상품명</div>
							<div class="col-lg-6 col-xs-12">
								<input class="width-100 itemVal" type="text" name="itemDetail" id="itemDetail${cnt}" value="" />
							</div>
						</div>
					</div>
					
					<div class="row form-group hr-8">
						<div class="col-lg-12 col-xs-12">
							<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >Q10 CODE</div>
							<div class="col-lg-6 col-xs-12">
								<input class="width-100 itemVal" type="text" name="cusItemCode" id="cusItemCode{cnt}" value="" />
							</div>
						</div>
					</div>
					
					<div class="row form-group hr-8">
						<div class="col-lg-12 col-xs-12">
							<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >상품재질</div>
							<div class="col-lg-6 col-xs-12">
								<input class="width-100 itemVal" type="text" name="itemMeterial" id="itemMeterial${cnt}" value="" />
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
							<div class="col-lg-3 col-xs-12 pd-1remNm" >단가</div>
							<div class="col-lg-6 col-xs-12">
								<input class="width-100 itemVal" type="text" name="unitValue" id="unitValue${cnt}" value="" />
							</div>
						</div>
					</div>
					
					
					<div class="row form-group hr-8">
						<div class="col-lg-12 col-xs-12">
							<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >제조국가</div>
							<div class="col-lg-6 col-xs-12">
								
								<select class="chosen-select form-control tag-input-style width-100 pd-1rem itemVal" id="makeCntry${cnt}" name="makeCntry" data-placeholder="국가를 선택해 주세요">
									<option value="">  </option>
									<c:forEach items="${nations}" var="nations">
										<option value="${nations.nationCode}">${nations.nationName}(${nations.nationCode})</option>
									</c:forEach>
								</select>
							
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
		
		
		<script type="text/javascript">
			var targetIndex=0;

			if(!ace.vars['touch']) {
				targetIndex = $('.chosen-select').length;
				var targetId = '#makeCntry'+targetIndex;
				$(targetId).chosen({allow_single_deselect:true}); 
				//resize the chosen on window resize
		
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$(targetId).each(function(e) {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				}).trigger('resize.chosen');
				//resize chosen on sidebar collapse/expand
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$(targetId).each(function(e) {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				});
			}
			
		</script>
		<!-- script addon end -->
	</body>
</html>
