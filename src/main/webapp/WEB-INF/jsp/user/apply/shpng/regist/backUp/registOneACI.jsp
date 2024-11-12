<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="col-xs-12 col-lg-12" >
		<div class="col-xs-12 col-lg-12 " style="font-size: 5;">
			<h2>주문정보</h2>
		</div>
		<!-- style="border:1px solid red" -->
		<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
			<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
				<h4 >발송 정보</h4>
				<div class="row hr-8">
					<div class="col-xs-1 center mp07 shipperNm">이름</div>
					<div class="col-xs-3 center">
						<input class="col-xs-12 shipperVal" type="text" name="shipperName" id="shipperName" value="${userInfo.userName}" />
					</div>
					<div class="col-xs-1 center mp07 shipperNm">전화번호</div>
					<div class="col-xs-3 center">
						<input class="col-xs-12 shipperVal" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="shipperTel" id="shipperTel" value="${userInfo.userTel}" />
					</div>
					<div class="col-xs-1 center mp07">휴대전화 번호</div>
					<div class="col-xs-3 center">
						<input class="col-xs-12" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="shipperHp" id="shipperHp" value="" />
					</div>
				</div>
				
				<div class="hr dotted"></div>
				<div class="row hr-8">
					<div class="col-xs-1 center mp07 shipperNm">
						<label>우편주소</label>
					</div>
					<div class="col-xs-3 center">
						<input class="col-xs-12 shipperVal" type="text" name="shipperZip" id="shipperZip" value="${userInfo.userZip}" />
					</div>
				
					<div class="col-xs-1 center mp07 shipperNm">
						<label>주소</label>
					</div>
					<div class="col-xs-3 center">
						<input class="col-xs-12 shipperVal" type="text" name="shipperAddr" id="shipperAddr" value="${userInfo.userAddr}" />
					</div>
					
					<div class="col-xs-1 center mp07">
						<label>상세주소</label>
					</div>
					<div class="col-xs-3 center ">
						<input class="col-xs-12" type="text" name="shipperAddrDetail" id="shipperAddrDetail" value="${userInfo.userAddrDetail}" />
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
				<h4 style="margin-top: 20px;">수취 정보</h4>
				<div class="row hr-8">
					<div class="col-xs-1 center mp07 cneeNm">이름 (Eng)</div>
					<div class="col-xs-3 center">
						<input class="col-xs-12 cneeVal" type="text" name="cneeName" id="cneeName" value="" />
					</div>
					<div class="col-xs-1 center mp07">이름 (Jpn)</div>
					<div class="col-xs-3 center">
						<input class="col-xs-12" type="text" name="nativeCneeName" id="nativeCneeName" value="" />
					</div>
				</div>
				<div class="row hr-8">
					<div class="col-xs-1 center mp07 cneeNm">전화번호</div>
					<div class="col-xs-3 center">
						<input class="col-xs-12 cneeVal" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="cneeTel" id="cneeTel" value="" />
					</div>
					<div class="col-xs-1 center mp07">휴대전화 번호</div>
					<div class="col-xs-3 center">
						<input class="col-xs-12" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="cneeHp" id="cneeHp" value="" />
					</div>
				</div>
				<div class="hr dotted"></div>
				<div class="row hr-8">
					<div class="col-xs-1 center mp07 cneeNm">
						<label>우편주소</label>
					</div>
					<div class="col-xs-3 center">
						<input class="col-xs-12 cneeVal" type="text" name="cneeZip" id="cneeZip" value="" />
					</div>
				</div>
				<div class="row hr-8">
					<div class="col-xs-1 center mp07">
						<label>주소 (Eng)</label>
					</div>
					<div class="col-xs-4 center">
						<input class="col-xs-12" type="text" name="cneeAddr" id="cneeAddr" value="" />
					</div>
					
					<div class="col-xs-1 center mp07">
						<label>상세주소 (Eng)</label>
					</div>
					<div class="col-xs-4 center">
						<input class="col-xs-12" type="text" name="cneeAddrDetail" id="cneeAddrDetail" value="" />
					</div>
				</div>
				<div class="row hr-8">
					<div class="col-xs-1 center mp07 cneeNm">
						<label>주소 (Jpn)</label>
					</div>
					<div class="col-xs-4 center">
						<input class="col-xs-12 cneeVal" type="text" name="nativeCneeAddr" id="nativeCneeAddr" value="" />
					</div>
					
					<div class="col-xs-1 center mp07">
						<label>상세주소 (Jpn)</label>
					</div>
					<div class="col-xs-4 center">
						<input class="col-xs-12" type="text" name="nativeCneeAddrDetail" id="nativeCneeAddrDetail" value="" />
					</div>
				</div>
			</div>
			
			<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
				<h4 style="margin-top: 20px;">운송장 정보</h4>
				<div class="row hr-8">
					<div class="col-xs-1 center mp07 orderNm">
						주문 번호
					</div>
					<div class="col-xs-3 center">
						<input class="col-lg-12 orderVal" type="text" name="orderNo" id="orderNo" value="" />
					</div>
					<div class="col-xs-1 center mp07">
						주문 날짜
					</div>
					<div class="col-xs-3 center">
						<div class="col-lg-12">
							<div class="input-group input-group-sm">
								<span class="input-group-addon">
									<i class="ace-icon fa fa-calendar"></i>
								</span>
								<input type="text" id="orderDate" name="orderDate" class="form-control" value="${userOrder.orderDate}"/>
							</div>
						</div>
						<%-- <input class="col-lg-12" type="text" name="orderDate" id="orderDate" value="${userOrder.orderDate}" placeholder="ex):20201231" /> --%>
					</div>
					<div class="col-xs-1 center orderNm">
						CURRENCY
					</div>
					<div class="col-xs-3 center" style="vertical-align: middle;">
						<select class="orderVal" name="chgCurrency" id="chgCurrency${status.count}" style="width:100%;">
							<option 
								<c:if test = "${userOrderItem[0].chgCurrency eq 'USD'}">
									selected
								</c:if> 
							value="USD">USD</option>
							<option 
								<c:if test = "${userOrderItem[0].chgCurrency eq 'GBP'}">
									selected
								</c:if>
							value="GBP">GBP</option>
						</select>
						<!-- <input class="col-xs-12 orderVal" type="text" name="chgCurrency" id="chgCurrency" value="" /> -->
					</div>
					<!-- <div class="col-xs-1 center mp07">
						구매 사이트
					</div>
					<div class="col-xs-3 center">
						<input class="col-xs-12" type="text" name="buySite" id="buySite" value="http://" />
					</div> -->
				</div>
				<div class="hr dotted"></div>
				<div class="row hr-8">
					<div class="col-xs-1 center mp07 orderNm">
						Height
					</div>
					<div class="col-xs-2 center">
						<input class="col-xs-12 volumeWeight orderVal" floatOnly type="text" name="userHeight" id="userHeight" value="" />
					</div>
					<div class="col-xs-1 center mp07 orderNm">
						Length
					</div>
					<div class="col-xs-2 center">
						<input class="col-xs-12 volumeWeight orderVal" floatOnly type="text" name="userLength" id="userLength" value="" />
					</div>
					<div class="col-xs-1 center mp07 orderNm">
						Width
					</div>
					<div class="col-xs-2 center">
						<input class="col-xs-12 volumeWeight orderVal" floatOnly type="text" name="userWidth" id="userWidth" value="" />
					</div>
					<div class="col-xs-1 center mp07 orderNm">
						길이 단위
					</div>
					<div class="col-xs-2 center">
						<select class="volumeWeight orderVal" id="dimUnit" name="dimUnit" style="width:100%;">
							<option value="CM">CM</option>
							<option value="IN">INCH</option>
						</select>
					</div>
				</div>
				<div class="row hr-8">
					<div class="col-xs-1 center mp07 orderNm">
						실무게
					</div>
					<div class="col-xs-2 center">
						<input class="col-xs-12 orderVal" floatOnly type="text" name="userWta" id="userWta" value="" />
					</div>
					<div class="col-xs-1 center mp07 orderNm">
						부피 무게
					</div>
					<div class="col-xs-2 center">
						<input class="col-xs-12 orderVal" floatOnly type="text" name="userWtc" id="userWtc" value="" />
					</div>
					<div class="col-xs-1 center mp07 orderNm">
						박스 개수
					</div>
					<div class="col-xs-2 center">
						<input class="col-xs-12 orderVal" numberOnly type="text" name="boxCnt" id="boxCnt" value="" />
					</div>
					<div class="col-xs-1 center mp07 orderNm">
						무게 단위
					</div>
					<div class="col-xs-2 center">
						<select id="wtUnit orderVal" name="wtUnit" style="width:100%;">
							<option value="KG">KG</option>
							<option value="LB">LB</option>
						</select>
					</div>
				</div>
				
				<div class="hr dotted"></div>
			</div>
		</div>
	</div>
	<!-- 기본정보 End -->
	<!-- 설정정보 Start -->
	<br/>
	<br/>
	<div class="col-xs-12 col-lg-12" >
		<div class="col-xs-12 col-lg-12 form-group" style="font-size: 5;">
			<h2>상품정보</h2>
		</div>
		<!-- style="border:1px solid red" -->
		<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
			<div style="text-align: right">
				<!-- <button class="btn btn-white btn-danger btn-bold" type="button" id="delBtns" name="delBtns">
					삭제
				</button> -->
				<button class="btn btn-white btn-info btn-bold" type="button" id="addBtns" name="addBtns">
					추가
				</button>
			</div>
			<br/>
			
			
			<div id="accordion" class="accordion-style1 panel-group">
				<div class="panel panel-default panels1">
					<div class="panel-heading">
						<h4 class="panel-title">
							<span class="accordion-toggle" >
								<span data-toggle="collapse" href="#collapseOne" >
								&nbsp;Item #1
								<i class="ace-icon fa fa-angle-double-down bigger-110" data-icon-hide="ace-icon fa fa-angle-double-down" data-icon-show="ace-icon fa fa-angle-right"></i>
								</span>
								<i class="fa fa-times bigger-110" style="float: right;" id="delBtns" name="delBtns" onclick="javascript:fn_delete(this);"></i>
							</span>
						</h4>
					</div>

					<div class="panel-collapse collapse multi-collapse in" id="collapseOne">
						<div class="panel-body">
							<div class="row form-group hr-8">
								<div class="col-lg-12 col-xs-12">
									<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >상품명</div>
									<div class="col-lg-6 col-xs-12">
										<input class="width-100 itemVal" type="text" name="itemDetail" id="itemDetail1" value="" />
									</div>
								</div>
							</div>
							
							<div class="row form-group hr-8">
								<div class="col-lg-12 col-xs-12">
									<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >Q10 CODE</div>
									<div class="col-lg-6 col-xs-12">
										<input class="width-100 itemVal" type="text" name="cusItemCode" id="cusItemCode1" value="" />
									</div>
								</div>
							</div>
							
							<div class="row form-group hr-8">
								<div class="col-lg-12 col-xs-12">
									<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >상품재질</div>
									<div class="col-lg-6 col-xs-12">
										<input class="width-100 itemVal" type="text" name="itemMeterial" id="itemMeterial1" value="" />
									</div>
								</div>
							</div>
						
							<div class="row form-group hr-8">
								<div class="col-lg-12 col-xs-12">
									<div class="col-lg-3 col-xs-12 pd-1rem" >BRAND</div>
									<div class="col-lg-6 col-xs-12">
										<input class="width-100" type="text" name="brandItem" id="brand1" value="" />
									</div>
								</div>
							</div>
							
							
							
							<div class="row form-group hr-8">
								<div class="col-lg-12 col-xs-12">
									<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >상품 개수</div>
									<div class="col-lg-6 col-xs-12">
										<input class="width-100 itemVal" numberOnly type="text" name="itemCnt" id="itemCnt1" value="" />
									</div>
								</div>
							</div>
							<div class="row form-group hr-8">
								<div class="col-lg-12 col-xs-12">
									<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >단가</div>
									<div class="col-lg-6 col-xs-12">
										<input class="width-100 itemVal" floatOnly type="text" name="unitValue" id="unitValue1" value="" />
									</div>
								</div>
							</div>
							
							<div class="row form-group hr-8">
								<div class="col-lg-12 col-xs-12">
									<div class="col-lg-3 col-xs-12 pd-1rem itemNm" >제조국가</div>
									<div class="col-lg-6 col-xs-12">
										<select class="chosen-select form-control tag-input-style width-100 pd-1rem itemVal" id="makeCntry1" name="makeCntry" data-placeholder="국가를 선택해 주세요">
											<option value="">  </option>
											<c:forEach items="${nationList}" var="nation">
												<option value="${nation.nationCode}">${nation.nationEName}(${nation.nationName })</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	<!-- 설정정보 End -->
	
	<div class="col-lg-12 col-xs-12">
	<br />
		<div class="col-lg-1 col-xs-6 col-lg-offset-5">
			<div id="button-div">
				<div id="backPage" style="text-align: center">
					<button id="backToList" type="button" class="btn btn-sm btn-danger">취소</button>
				</div>
			</div>
		</div>
		<div class="col-lg-1 col-xs-6">
			<div id="button-div">
				<div id="rgstrUser" style="text-align: center">
					<button id="rgstrInfo" type="button" class="btn btn-sm btn-primary">등록</button>
				</div>
			</div>
		</div>
	</div>
</div>
		<!-- Footer End -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <script src="/testView/js/jquery-3.3.1.min.js"></script> -->
		<script src="/testView/js/jquery-ui.js"></script>
		<script src="/testView/js/popper.min.js"></script>
		<!-- <script src="/testView/js/bootstrap.min.js"></script> -->
		<script src="/assets/js/bootstrap.min.js"></script>
		<script src="/testView/js/owl.carousel.min.js"></script>
		<script src="/testView/js/jquery.magnific-popup.min.js"></script>
		<script src="/testView/js/aos.js"></script>
		
		<script src="/testView/js/main.js"></script>
		
		<!-- script on paging start -->
		<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="/assets/js/jquery-ui.min.js"></script>
		<script src="/assets/js/jquery.dataTables.min.js"></script>
		<script src="/assets/js/jquery.dataTables.bootstrap.min.js"></script>
		<script src="/assets/js/fixedHeader/dataTables.fixedHeader.min.js"></script>
		<script src="/assets/js/fixedHeader/dataTables.fixedHeader.js"></script>
		<script src="/assets/js/dataTables.buttons.min.js"></script>
		<script src="/assets/js/buttons.flash.min.js"></script>
		<script src="/assets/js/buttons.html5.min.js"></script>
		<script src="/assets/js/buttons.print.min.js"></script>
		<script src="/assets/js/buttons.colVis.min.js"></script>
		<script src="/assets/js/dataTables.select.min.js"></script>
		
		<script src="/assets/js/bootstrap-tag.min.js"></script>
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/chosen.jquery.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		
		
		<script type="text/javascript">
			var cnt = 1;
			jQuery(function($) {
				$(document).ready(function() {
					$("#forthMenu").toggleClass('open');
					$("#forthMenu").toggleClass('active'); 
					$("#4thOne").toggleClass('active');
				});
				//select target form change
			})
			
			$(".volumeWeight").on('change',function(e){
				var temp = "";
				if($("#dimUnit").val() == 'CM'){
					temp = ($("#userHeight").val()*$("#userLength").val()*$("#userWidth").val())/6000;
				}else{
					temp = ($("#userHeight").val()*$("#userLength").val()*$("#userWidth").val())/166;
				}
				
				$("#userWtc").val(temp.toFixed(2));
			})
			
			$("input:text[numberOnly]").on("keyup", function() {
	            $(this).val($(this).val().replace(/[^0-9]/g,""));
	        });
	
	        $("input:text[floatOnly]").on("keyup", function() {
	            $(this).val($(this).val().replace(/[^0-9.]/g,""));
	        });

			function fn_delete(test){
				if($(".panel").length > 1){
					test.closest('.panel').remove()
				}else{
					alert("최소 1개의 상품정보를 입력해야 합니다.")
				}
			};
			$( "#orderDate" ).datepicker({
				showOtherMonths: true,
				selectOtherMonths: false,
				dateFormat:'yymmdd' , 
				autoclose:true, 
				todayHighlight:true
			});
			

			$('#backToList').on('click', function(e){
				history.back();
			});

			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true, search_contains: true }); 
				//resize the chosen on window resize
		
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				}).trigger('resize.chosen');
				//resize chosen on sidebar collapse/expand
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					})
				});
			}


			$('#addBtns').on('click',function(e){
				$("#cnt").val(cnt);
				var formData = $("#registForm").serialize();
				$.ajax({
					url:'/cstmr/apply/itemList',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: formData,
					success : function(data) {
						$("#accordion").append(data);
						cnt++;
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})
			});

			$('#rgstrInfo').on('click',function(e){
				var valiChk = 0;
				$(".shipperVal").each(function(e){
					if($(this).val() == ""){
						alert("SHIPPER정보의 "+$($(".shipperNm").get(e)).text().trim()+"을 확인해 주세요.")
						$(this).focus();
						valiChk = 1;
						return false;
					}
				})
				if(valiChk==1){
					return;
				}
				$(".cneeVal").each(function(e){
					if($(this).val() == ""){
						alert("수취인 정보의 "+$($(".cneeNm").get(e)).text().trim()+"을 확인해 주세요.")
						$(this).focus();
						valiChk = 1;
						return false;
						
					}
				})
				if(valiChk==1){
					return;
				}
				$(".orderVal").each(function(e){
					if($(this).val() == ""){
						alert("운송장 정보의 "+$($(".orderNm").get(e)).text().trim()+"을 확인해 주세요.")
						$(this).focus();
						valiChk = 1;
						return false;
					}
				})
				if(valiChk==1){
					return;
				}
				$(".itemVal").each(function(e){
					if($(this).val() == ""){
						var itemNum = e/7;
						itemNum = parseInt(itemNum)+1;
						alert("ITEM"+itemNum+"정보의 "+$($(".itemNm").get(e)).text().trim()+"을 확인해 주세요.")
						$(this).focus();
						valiChk = 1;
						return false;
					}
				})
				if(valiChk==1){
					return;
				}

				$("[name=brandItem]").each(function(e){
					if($(this).val() == ""){
						$(this).val("d#none#b");
					}
				})

				var formData = $("#registForm").serialize();
				$.ajax({
					url:'/cstmr/apply/registOrderInfo',
					type: 'POST',
					beforeSend : function(xhr)
					{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: formData,
					success : function(data) {
						if(data == "S"){
							$("[name=brandItem]").each(function(e){
								if($(this).val() == "d#none#b"){
									$(this).val("");
								}
							})
							alert("등록 되었습니다.");
							location.href="/cstmr/apply/shpngAgncy";
						}else{
							alert("등록중 오류가 발생했습니다. 데이터를 확인해 주세요")
						}
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
		            }
				})

			});

		</script>
		<!-- script addon end -->
	</body>
</html>
