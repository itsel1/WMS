<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	<style type="text/css">
		.colorBlack {
			color: #000000 !important;
		}
		.noneArea {
			margin:0px !important;
			padding:0px !important;
			border-bottom : none;
		}
		
		.containerTable {
			width: 100% !important;
		}
		
		table ,tr td{
			word-break:break-all;
		}
		
		
		#loading {
			height: 100%;
			left: 0px;
			position: fixed;
			_position: absolute;
			top: 0px;
			width: 100%;
			filter: alpha(opacity = 50);
			-moz-opacity: 0.5;
			opacity: 0.5;
		}
		.loading {
			background-color: white;
			z-index: 999999;
		}
		#loading_img {
			position: absolute;
			top: 50%;
			left: 50%;
			/* height: 200px; */
			margin-top: -75px; 
			margin-left: -75px; 
			z-index: 999999;
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
	          <div class="col-md-12 mb-0" style="font-size:14px !important;"><a>배송대행 신청</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">검품배송 신청서</strong></div>
	        </div>
	      </div>
	    </div>
	    <!-- Main container Start-->
		     <div class="container">
		       <div class="page-content noneArea">
					<div class="page-header noneArea">
						<h3>
							검품배송 신청서
						</h3>
					</div>
					<form name="uploadForm" id="uploadForm" enctype="multipart/form-data" >
						<div id="inner-content-side" >
							<input type="hidden" name="testTrans" id="testTrans" value="${transCode}" />
							<input type="hidden" name="page" id="page"/>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<h5>
								Excel Form 다운로드
							</h5>
							<div class="row">
								<div class="col-xs-12 col-md-4 form-group">
									<select class="chosen-select-start tag-input-style pd-1rem nations" id="idx" name="idx">
										<option value=""> Please select </option>
										<c:forEach items="${userDstnNation}" var="userDstnNation">
											<option value="${userDstnNation.idx}">${userDstnNation.orgName} > ${userDstnNation.nationName}</option>
										</c:forEach>
									</select>
									&nbsp;&nbsp;&nbsp;
									<button type="button" class="btn btn-white btn-inverse btn-xs" id="testBtn" name="testBtn">다운로드</button>
								</div>
							</div>
							<h5>
								배송자료 업로드
							</h5>
							<div class="row">
								<div class="col-xs-12 col-md-7 form-group">
									<input type="file" id="excelFile" name="excelFile"
										style="display: initial; width: 70%; cursor: pointer; border: 1px solid #D5D5D5;"
										accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
									<i class="fa fa-times bigger-110" id="delBtns" name="delBtns" style="cursor: pointer;"></i>
								</div>
								<div class="col-xs-12 col-md-5" style="text-align: right;">
									<button type="button" class="btn btn-sm btn-success " name="excelUp" id="excelUp">
										엑셀 파일 적용하기
									</button>
									<button type="button" class="btn btn-sm btn-primary" name="rgstrOne" id="rgstrOne">
										수기등록
										<!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
									</button>
								</div>
							</div>
							<!-- <div class="row">
								<div class="col-xs-12 col-md-6" id="rgstr" style="text-align: left">
									<button type="button" class="btn btn-sm btn-primary" name="rgstrCheck" id="rgstrCheck">
										배송자료 등록
										ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출
									</button>
									<button type="button" class="btn btn-sm btn-danger" name="delCheck" id="delCheck">
										삭제
										ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출
									</button>
								</div>
								
							</div> -->
							<div class="hr hr-8 dotted"></div>
							<div class="space-10"></div>
							<h5>
								배송자료 검색
							</h5>
							<div class="row">
								<div class="col-xs-12 col-md-2 form-group" style="text-align: left;">
									<select class="chosen-select-start tag-input-style width-100 pd-1rem nations" id="searchIdx" name="searchIdx">
										<option value="All"> 배송 회사 선택 </option>
										<option value="All"> 전체 </option>
										<c:forEach items="${userDstnNation}" var="userDstnNation">
											<option value="${userDstnNation.idx}">${userDstnNation.orgName} > ${userDstnNation.nationName}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-xs-12 col-md-1 form-group" style="text-align: right;">
									<select class="tag-input-style width-100 pd-1rem" id="txtType" name="txtType">
										<option value="0">검색조건 선택</option>
										<option <c:if test = "${txtType eq '0'}">selected</c:if> value="0">전체</option>
										<option <c:if test = "${txtType eq '1'}">selected</c:if> value="1">주문번호</option>
										<option <c:if test = "${txtType eq '3'}">selected</c:if> value="3">우편번호</option>
										<option <c:if test = "${txtType eq '4'}">selected</c:if> value="4">상품명</option>
									</select>
								</div>
								<div class="col-xs-12 col-md-6 form-group" style="text-align: left">
									<input type="text" style="display:none;"/>
									<input type="text" style="width:80%; border:none; border-bottom:1px solid gray;"id="searchTxt" name="searchTxt" value="${searchTxt}">&nbsp;&nbsp;&nbsp;
									<button type="button" class="btn btn-sm btn-white btn-primary" id="txtSearch" name="txtSearch">검색</button>
								</div>
								<div class="col-xs-12 col-md-3 form-group" id="rgstr" style="text-align: right">
									<button type="button" class="btn btn-sm btn-primary" name="rgstrCheck" id="rgstrCheck">
										선택 배송자료 등록
										<!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
									</button>
									<button type="button" class="btn btn-sm btn-danger" name="delCheck" id="delCheck">
										선택 삭제
										<!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
									</button>
								</div>
							</div>
							
							
							
							<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;">
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
		
		<!-- script addon start -->
		
		
		<script type="text/javascript">
			var loading = "";
			jQuery(function($) {
				$(document).ready(function() {
					loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="/image/Pulse.gif" />').appendTo(document.body).hide();
					$("#forthMenu").toggleClass('open');
					$("#forthMenu").toggleClass('active'); 
					$("#4thOne").toggleClass('active');
					if(getParameterByName('page') != ""){
						$("#page").val(getParameterByName('page'));
						$("#searchTxt").val(getParameterByName('keywords'));
					}
					jQuery('.errorCol').parent().parent().css('background-color', 'rgb(255,247,247)');

					if($("#testTrans").val() != "")
						$("#searchIdx").val($("#testTrans").val());
					$("#searchIdx").trigger('change'); 
				});
				//select target form change
				/* $('#formSelect').on('change', function(){
					$('#tableForm').children().remove();
					if($(this).val() != 0){
						var list = { selOption: $(this).val() }
						$.ajax({
							type : "POST",
							beforeSend : function(xhr)
							{   //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							url : "shpngSubpage",
							data : list,
							dataType : "text",
							error : function(){
								alert("subpage ajax Error");
							},
							success : function(data){
								$("#tableForm").html(data);
							}
						})
					}
				}); */

				function getParameterByName(name) {
			        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
			        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
			                results = regex.exec(location.search);
			        return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
			    }
			    
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



				$("#searchIdx").on('change',function(e){

					/* var splitString = $("#totals").val().split('_');
					$("#orgStation").val(splitString[0]);
					$("#dstnNation").val(splitString[1]);
					$("#transCode").val(splitString[2]); */

					if($("#searchIdx").val() == ''){
						$("#table-contents").html("");
						return;
					}
					loading.show();
					var formData = $("#uploadForm").serialize();
					$.ajax({
						url:'/cstmr/apply/inspDlvryTable',
						type: 'POST',
						beforeSend : function(xhr)
						{  
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData,
						success : function(data) {
							loading.hide();
							$("#table-contents").html(data);
			            }, 		    
			            error : function(xhr, status) {
				            alert("데이터 조회에 실패 하였습니다.");
							loading.hide();
			            }
					})
				})


				$('#rgstrOne').on('click', function(e) {
					location.href = '/cstmr/apply/insp/registOne';
				});

				$('#delBtns').on('click', function(e) {
					$('#excelFile').val("");
				});


				$('#excelUp').on('click', function(e) {
					if($("#transCode").val() == ""){
						alert("선택된 배송회사가 없습니다.")
						return;
					}
					if($('#excelFile').val() == ""){
						alert("선택된 파일이 없습니다")
						return
					}
					var datass = new FormData();
					datass.append("file", $("#excelFile")[0].files[0]);
					loading.show();
					$.ajax({
						type : "POST",
						beforeSend : function(xhr)
						{   //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						url : "inspAgncyExcelUpload?formSelect="+$("#transCode").val(),
						data : datass,
						processData: false,
			            contentType: false,
						error : function(){
							alert("처리 중 오류 발생");
							loading.hide();
							location.reload();
						},
						success : function(data){
							alert(data);
							loading.hide();
							location.reload();
							
						}
					})
				});

				$('#rgstrCheck').on('click',function(e){
					//LoadingWithMask();
					var targets = new Array();
					var targetsTrans = new Array();
					var uploadTypeList = new Array();
					var orderTypes = "insp";
					$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(E){
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if(td_checked){
							if($("#errMsg"+E).text().trim() == ""){
								targets.push($(this).val());
								targetsTrans.push($("#transName"+E).val());
								uploadTypeList.push($("#uploadType"+E).val());
							}
						}
					});
					loading.show();
					$.ajax({
						url:'/cstmr/apply/registOrderList',
						type: 'POST',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						traditional : true,
						data: {
							targets : targets,
							transCode : targetsTrans,
							orderType : orderTypes,
							uploadType : uploadTypeList
						},
						success : function(data) {
							alert(data);
							loading.hide();
							location.reload();
			            }, 		    
			            error : function(xhr, status) {
			                alert("등록 처리 중 오류 발생");
							loading.hide();
							location.reload();
			            }
					})
				})
				
				$('#delCheck').on('click',function(e){
					loading.show();
					var targets = new Array();
					$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if(td_checked){
							targets.push($(this).val());
						}
					});
					$.ajax({
						url:'/cstmr/apply/delOrderListTmp',
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
								alert("삭제 되었습니다.");
							}else if(data == "F"){
								alert("삭제 처리 중 오류 발생");
							}
							loading.hide();
							location.reload();
			            }, 		
			            error : function(request,status,error) {
				            alert("시스템 오류 발생");
							loading.hide();
							location.reload();
						}  
					})
					
				})
				

				var myTable = 
					$('#dynamic-table')
					.DataTable( {
						"dom": 't',
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "scrollY" : 500,
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
					$("#testBtn").on('click',function(e){
						if($("#transCode").val() == ''){
							alert("지역을 선택해 주세요")
							return;
						}
						
						
						window.open("/cstmr/apply/excelFormDown?idx="+$("#idx").val())
					})
					$("#txtSearch").on('click',function(e){
						$("#searchIdx").trigger('change');
					})
					$("#searchTxt").keydown(function(key){
						if(key.keyCode == 13){
							$("#searchIdx").trigger('change');
						}
					});
			})
		</script>
		<!-- script addon end -->
	</body>
</html>
