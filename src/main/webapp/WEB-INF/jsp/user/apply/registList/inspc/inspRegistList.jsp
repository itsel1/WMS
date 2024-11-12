<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	  
	  .noneArea {
			margin:0px !important;
			padding:0px !important;
			border-bottom : none;
		}
		
		.containerTable {
			width: 100% !important;
		}
		
	 </style>
	<!-- basic scripts -->
	</head> 
	<title>검품배송 신청서</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<div class="site-wrap">
		<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
			<div class="toppn">
				<div class="container">
					<div class="row">
						<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>배송대행 신청</a> <span class="mx-2 mb-0">/</span> 
							<strong class="text-black">
	      						<c:if test="${urlType eq 'allAbout'}">
									검품배송 등록내역
								</c:if>
								<c:if test="${urlType eq 'before'}">
									발송 대기
								</c:if>
								<c:if test="${urlType eq 'after'}">
									발송 완료
								</c:if>
							</strong>
						</div>
	    			</div>
	  			</div>
			</div>
			<!-- Main container Start-->
			<div class="container">
				<div class="page-content noneArea">
					<div class="page-header noneArea">
						<h3>
							<c:if test="${urlType eq 'allAbout'}">
								검품배송 등록내역
							</c:if>
							<c:if test="${urlType eq 'before'}">
								발송 대기
							</c:if>
							<c:if test="${urlType eq 'after'}">
								발송 완료
							</c:if>
						</h3>
					</div>
					<form name="uploadForm" id="uploadForm" enctype="multipart/form-data" >
						<div id="inner-content-side" >
							<div id="search-div">
								<br>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input type="hidden" name="page" id="page"/>
								<div class="form-group">
									<div class="row">
										<div class="col-xs-12 col-md-2 form-group">
											<select class="chosen-select-start tag-input-style width-100 pd-1rem nations" id="idx" name="idx">
												<!-- <option value=""> 배송회사 선택 </option> -->
												<option value="All"> 전체 </option>
												<!-- <option value="All_All_All"> All Area </option> -->
												<c:forEach items="${userDstnNation}" var="userDstnNation">
													<option value="${userDstnNation.idx}"<c:if test="${userDstnNation.idx eq idx }"> selected </c:if>>${userDstnNation.orgName} > ${userDstnNation.nationName}</option>
												</c:forEach>
											</select>
											<input type="hidden" id="urlType" name="urlType" value="${urlType}">
										</div>
										<div class="col-xs-12 col-md-1" style="text-align: left;">
											<select style="width:100%;" id="txtType" name="txtType">
												<option <c:if test = "${txtType eq '0'}">selected</c:if> value="0">전체</option>
												<option <c:if test = "${txtType eq '1'}">selected</c:if> value="1">주문번호</option>
												<%-- <option <c:if test = "${txtType eq '2'}">selected</c:if> value="2">택배번호</option> --%>
												<option <c:if test = "${txtType eq '2'}">selected</c:if> value="2">송장번호</option> 
												<option <c:if test = "${txtType eq '3'}">selected</c:if> value="3">우편번호</option>
												<option <c:if test = "${txtType eq '4'}">selected</c:if> value="4">상품명</option>
											</select>
										</div>
										<div class="col-xs-12 col-md-5" style="text-align: left">
											<input type="text" style="display:none;"/>
											<input type="text" style="width:100%; border:none; border-bottom:1px solid gray;"id="searchTxt" name="searchTxt" value="${searchTxt}">
										</div>
										<div class="col-xs-12 col-md-1" style="text-align: left">
											<button type="button" class="btn btn-sm btn-white btn-primary" id="txtSearch" name="txtSearch">검색</button>
										</div>
										<div class="col-xs-12 col-md-3 form-group" style="text-align: right;">
										<!-- <button type="button" class="btn btn-sm btn-primary" name="printCheck" id="printCheck">
											 	송장 출력
											 ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출
											</button> -->
											<button type="button" class="btn btn-sm btn-danger" name="delCheck" id="delCheck">
											 	삭제
											 <!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
											</button>
										</div>
										<%-- <div class="row">
											<div class="col-xs-12 col-md-2" style="text-align: left;">
												<select style="width:100%;" id="txtType" name="txtType">
													<option <c:if test = "${txtType eq '0'}">selected</c:if> value="0">전체</option>
													<option <c:if test = "${txtType eq '1'}">selected</c:if> value="1">주문번호</option>
													<option <c:if test = "${txtType eq '2'}">selected</c:if> value="2">택배번호</option>
													<option <c:if test = "${txtType eq '3'}">selected</c:if> value="3">우편번호</option>
													<option <c:if test = "${txtType eq '4'}">selected</c:if> value="4">상품명</option>
												</select>
											</div>
											<div class="col-xs-12 col-md-5" style="text-align: left">
												<input type="text" style="display:none;"/>
												<input type="text" style="width:100%; border:none; border-bottom:1px solid gray;"id="searchTxt" name="searchTxt" value="${searchTxt}">
											</div>
											<div class="col-xs-12 col-md-1" style="text-align: left">
												<button type="button" class="btn btn-sm btn-white btn-primary" id="txtSearch" name="txtSearch">검색</button>
											</div>
										</div> --%>
									</div>
								</div>
							</div>
							<div id="table-contents" class="table-contents">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
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
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#forthMenu").toggleClass('open');
					$("#forthMenu").toggleClass('active'); 
					$("#4thOne").toggleClass('active');
					if(getParameterByName('page') != ""){
						$("#page").val(getParameterByName('page'));
						$("#searchTxt").val(getParameterByName('keywords'));
					}
					$("#idx").trigger('change');
				});
				//select target form change
				
				function getParameterByName(name) {
			        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
			        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
			                results = regex.exec(location.search);
			        return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
			    }
				
				$('#idx').on('change', function(){

					if($("#idx").val() == ''){
						$("#table-contents").html("");
						return;
					}

					var formData = $("#uploadForm").serialize();
					$.ajax({
						url:'/cstmr/apply/inspRegistListTable',
						type: 'POST',
						beforeSend : function(xhr)
						{  
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData,
						success : function(data) {
							$("#table-contents").html(data)
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
			            }
					})

					
				});
				
				function LoadingWithMask() {
				    //화면의 높이와 너비를 구합니다.
				    var maskHeight = $(document).height();
				    var maskWidth  = window.document.body.clientWidth;
				     
				    //화면에 출력할 마스크를 설정해줍니다.
				    var mask       ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
				  
				    //화면에 레이어 추가
				    $('body')
				        .append(mask)
				        
				    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
				    $('#mask').css({
				            'width' : maskWidth
				            ,'height': maskHeight
				            ,'opacity' :'0.3'
				    });
				  
				    //마스크 표시
				    $('#mask').show();  
				    //로딩중 이미지 표시
				}

				$('#printCheck').on('click',function(e){
					var targets = new Array();
					$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if(td_checked){
							targets.push($(this).val());
						}
					});
					window.open("/comn/printHawb?targetInfo="+targets+"&formType="+$("#formSelect").val(),"popForm","_blank");
					/* window.open("/comn/printHawb?targetInfo="+$("#TEST").val()+"&formType="+$("#formSelect").val(),"popForm","_blank"); */
				})
				
				$('#delCheck').on('click',function(e){
					LoadingWithMask();
					var targets = new Array();
					$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if(td_checked){
							targets.push($(this).val());
						}
					});
					if(targets.length==0){
						alert("삭제 대상이 없습니다.");
						$('#mask').hide();  
						return false;
					}
					$.ajax({
						url:'/cstmr/apply/delOrderList',
						type: 'POST',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						traditional : true,
						data: {
							targets : targets
						},
						success : function(data) {
							if(data == "S"){
								alert("삭제 되었습니다.")
								$("#idx").trigger('change');
								$('#mask').hide();
							}else if(data == "F"){
								alert("삭제중 오류가 발생하였습니다. 관리자에게 문의해 주세요.")
								$("#idx").trigger('change');
								$('#mask').hide();
							}
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("삭제에 실패 하였습니다. 입력정보를 확인해 주세요.");
			                $("#idx").trigger('change');
							$('#mask').hide();
			            }
					})
					
				})
				

				var myTable = 
					$('#dynamic-table')
					.DataTable( {
						"dom": 'lt',
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "autoWidth": false,
				        "scrollY": 500,
						select: {
							style: 'multi',
							selector:'td:first-child'
						},
						"language":{
							"zeroRecords": "조회 결과가 없습니다.",
						}
				    } );
					
					myTable.on( 'select', function ( e, dt, type, index ) {
						if ( type === 'row' ) {
							$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', true);
						}
					} );
					myTable.on( 'deselect', function ( e, dt, type, index ) {
						if ( type === 'row' ) {
							$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', false);
						}
					} );
					 
					/////////////////////////////////
					//table checkboxes
					$('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);


					//select/deselect all rows according to table header checkbox
					$('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function(){
						var th_checked = this.checked;//checkbox inside "TH" table header
						
						$('#dynamic-table').find('tbody > tr').each(function(){
							var row = this;
							if(th_checked) {
								if(!row.children[0].firstElementChild.firstElementChild.disabled)
									myTable.row(row).select();
							}
							else  myTable.row(row).deselect();
						});
					});
					$("#txtSearch").on('click',function(e){
						$("#idx").trigger('change');
					})
					$("#searchTxt").keydown(function(key){
						if(key.keyCode == 13){
							$("#idx").trigger('change');
						}
					});
			})
		</script>
		<!-- script addon end -->
	</body>
</html>
