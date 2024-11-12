<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="shortcut icon" type="image/x-icon" href="/image/favicon/logo_icon.ico" />
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<!-- <link rel="stylesheet" href="/assets/css/bootstrap-datepicker3.min.css" /> -->
	<link rel="stylesheet" href="/testView/css/bootstrap-datepicker3.css" />
	<link rel="stylesheet" href="/assets/css/ui.jqgrid.min.css" />
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
		
		table ,tr td{
			word-break:break-all;
		}
		
		.input-daterange {
			z-index: 99999;
		}
		
		
		#loading {
			height: 100vh;
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
			top: 50vh;
			left: 50%;
			/* height: 200px; */
			margin-top: -75px; 
			margin-left: -75px; 
			z-index: 999999;
		}
	 </style>
	<!-- basic scripts -->
	</head> 
	<title>BL 프린트</title>
	<body class="no-skin">
		<!-- headMenu Start -->
	<div class="site-wrap">
		<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="toppn">
			<div class="container">
				<div class="row">
					<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>등록내역</a> <span class="mx-2 mb-0">/</span> 
						<strong class="text-black">
							<c:if test="${urlType eq 'allAbout'}">
								BL 프린트
							</c:if>
							<c:if test="${urlType eq 'before'}">
								발송 대기
							</c:if>
							<c:if test="${urlType eq 'after'}">
								발송 완료
							</c:if>
							<c:if test="${urlType eq 'error'}">
								Error 목록
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
							BL 프린트  <h6 style="color:red;">※ 동일한 형태의 송장만 일괄 출력 가능합니다. 서로 형태가 다를 경우 국가별로 출력해 주세요.</h6>
						</c:if>
						<c:if test="${urlType eq 'before'}">
							발송 대기
						</c:if>
						<c:if test="${urlType eq 'after'}">
							발송 완료
						</c:if>
						<c:if test="${urlType eq 'error'}">
							Error 목록
						</c:if>
					</h3>
				</div>
				<form name="uploadForm" id="uploadForm" enctype="multipart/form-data" >
					<div id="inner-content-side" >
						<div id="search-div">
							<br>
							
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input type="hidden" name="page" id="page"/>
								<input type="hidden" id="targetInfo" name="target">
								<input type="hidden" id="formType" name="formType">
								<input type="hidden" name="orderType" value="NOMAL">
								<div class="row">
									<div class="col-xs-12 col-md-1">
										<select class="chosen-select-start tag-input-style width-100 pd-1rem nations" id="idx" name="idx">
											<option value="All"> 전체 </option>
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
									<div class="col-xs-12 col-md-2" style="text-align: left;">
										<div class="input-group">
											<span class="input-group-addon">
												<i class="fa fa-calendar bigger-110"></i>
											</span>
											<div class="input-group input-daterange">
												<input type="text" class="dateClass form-control" name="startDate" value="${startDate}" autocomplete="off"/>
													<span class="input-group-addon">
														to
													</span>
												<input type="text" class="dateClass form-control" name="endDate" value="${endDate}" autocomplete="off"/>
											</div>
										</div>
									</div>
									<div class="col-xs-12 col-md-3" style="text-align: left">
										<input type="text" style="display:none;"/>
										
										<input type="text" style="width:100%; border:none; border-bottom:1px solid gray;"id="searchTxt" name="searchTxt" value="${searchTxt}">
									</div>
									<div class="col-xs-12 col-md-1" style="text-align: left">
										<button type="button" class="btn btn-sm btn-white btn-primary" id="txtSearch" name="txtSearch">검색</button>
									</div>
									<div class="col-xs-12 col-md-3" style="text-align: right;">

									</div>
								</div>
							
						</div>
						<br>
						<div style="width:100%;overflow:auto;">
							<div style="float:left;width:50%;">
								<button type="button" class="btn btn-xs btn-primary" name="printCheck" id="printCheck">
										라벨 출력
								<!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
								</button>
								<button type="button" class="btn btn-xs btn-success" name="excelDownLoad" id="excelDownLoad">
										Excel 파일 다운로드
								<!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
								</button>
								<button type="button" class="btn btn-xs btn-primary" id="invPdf" style="text-transform: unset !important;">
									Commercial Invoice (Pdf)
								</button>
								<button type="button" class="btn btn-xs btn-primary" id="invExcel" style="text-transform: unset !important;">
									Commercial Invoice (Excel)
								</button>
								<button type="button" class="btn btn-xs btn-success" id="packingExcel" style="text-transform: unset !important;">
									Packing List (Excel)
								</button>
								<button type="button" class="btn btn-xs btn-primary" id="blFile" style="text-transform: unset !important;">
									라벨PDF 다운로드
								</button>
							</div>
							<div style="float:right;width:50%;text-align:right;">
								<c:if test="${urlType eq 'allAbout'}">
									<button type="button" class="btn btn-xs btn-danger" name="delCheck" id="delCheck">
									삭제
										<!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
								</button>
								</c:if>
							</div>
						</div>
						<div id="table-contents" class="table-contents">
						</div>
					</div>
				</form>
				<form name="targetNnoForm" id="targetNnoForm">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<input type="hidden" name="targetInfos" id="targetInfos">
					<input type="hidden" name="orderType" value="NOMAL">
				</form>
			</div>
		</div>
	</div>
		
			
		
	<!-- Footer Start -->
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
	<!-- Footer End -->
	<!-- <script src="/assets/js/jquery-2.1.4.min.js"></script> -->
	<script src="/testView/js/jquery-3.3.1.min.js"></script>
	<script src="/testView/js/jquery-ui.js"></script>
	<script src="/testView/js/popper.min.js"></script>
	<script src="/testView/js/bootstrap.min.js"></script>
	<!-- <script src="/assets/js/bootstrap.min.js"></script> -->
	<script src="/testView/js/owl.carousel.min.js"></script>
	<script src="/testView/js/jquery.magnific-popup.min.js"></script>
	<script src="/testView/js/aos.js"></script>
	
	<script src="/testView/js/main.js"></script>
	
	<script src="/assets/js/bootstrap-timepicker.min.js"></script>
	<script src="/assets/js/moment.min.js"></script>
	<script src="/assets/js/daterangepicker.min.js"></script>
	
	<!-- <script src="/assets/js/bootstrap-datepicker.min.js"></script> -->
	<script src="/testView/js/bootstrap-datepicker.js"></script>
	<script src="/assets/js/jquery.jqGrid.min.js"></script>
	<script src="/assets/js/grid.locale-en.js"></script>
	
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
		loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="/image/Pulse.gif" />').appendTo(document.body).hide();
		$(document).ready(function() {
			$("#forthMenu").toggleClass('open');
			$("#forthMenu").toggleClass('active'); 
			$("#4thOne").toggleClass('active');
			if(getParameterByName('page') != ""){
				$("#page").val(getParameterByName('page'));
				/* $("#startDate").val(getParameterByName('startDate'));
				$("#endDate").val(getParameterByName('endDate')); */
				$("#searchTxt").val(getParameterByName('searchTxt'));
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

			/* if($("#idx").val() == 'All'){
				$("#printCheck").hide();
				$("#excelDownLoad").hide();
			}else if($("#urlType").val() == 'error'){
				$("#printCheck").hide();
				$("#excelDownLoad").hide();
			}else{
				$("#printCheck").show();
				$("#excelDownLoad").show();
			} */

			$("#keywords").val($("#searchTxt").val());
			var formData = $("#uploadForm").serialize();
			$.ajax({
				url:'/cstmr/apply/shpngRegistListTable',
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
			
			var checkTF = true;
			$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(E){
				var targetTransCode = $("#transName"+E).val();
				var row = $(this).closest('tr').get(0);
				var td_checked = this.checked;
				if(td_checked){
					targets.push($(this).val());
					if(targetTransCode != $("#transName"+E).val()){
						checkTF = false;
						return;
					}
				}
			});
			if(!checkTF){
				alert("선택된 주문의 배송사가 다릅니다.")
				return;
			}
			window.open("/comn/printHawb?targetInfo="+targets+"&formType=&labelType=C","popForm","_blank");
			
			/* $.ajax({
				url:'/comn/printHawb',
				type: 'POST',
				beforeSend : function(xhr)
				{  
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				traditional : true,
				data: {
					target : targets,
					formType : $("#formSelect").val()
				},
				success : function(data) {
					window.open(data,"_blank");
	            }, 		    
	            error : function(xhr, status) {
	                alert(xhr + " : " + status);
	                alert("삭제에 실패 하였습니다. 입력정보를 확인해 주세요.");
	            }
			}) */
			
		});

		
		
		$('#excelDownLoad').on('click',function(e){

			var targets = new Array();
			$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(E){
				var row = $(this).closest('tr').get(0);
				var td_checked = this.checked;
				if(td_checked){
					targets.push($(this).val());
				}
			});
	

	
			if(targets.length==0){
				
				if ($("input[name='startDate']").val() == "" || $("input[name='endDate']").val() == "") {
					alert("자료 선택 또는 기간을 설정해 주세요");
					return false;
				}
			}
			
			var formData;
			var url;
			
			if(targets.length > 0) {
				url = "/comn/order/excelDownCheck";
				$("#targetInfos").val(targets);
				formData = $("#targetNnoForm").serialize();
			} else {
				url = "/comn/order/excelDownByDate";
				formData = $("#uploadForm").serialize();
			}
			

			loading.show();
			fn_procDownExcel(formData, url);

		})
		
		$('#invPdf').on('click',function(e){
			var targets = new Array();
			$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
				var row = $(this).closest('tr').get(0);
				var td_checked = this.checked;
				if(td_checked){
					targets.push($(this).val());
				}
			});
			window.open("/cstmr/printCommercial?targetInfo="+targets+"&formType="+$("#formSelect").val(),"popForm","_blank");
			window.close();
		});

		$('#invExcel').on('click',function(e){
			var targets = new Array();
			$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
				var row = $(this).closest('tr').get(0);
				var td_checked = this.checked;
				if(td_checked){
					targets.push($(this).val());
				}
			});

			$("#targetInfos").val(targets);
			$("#targetNnoForm").attr("action", "/cstmr/printCommercialExcel");
			$("#targetNnoForm").attr("method", "get");
			$("#targetNnoForm").submit();
			
			
		});
		
		$('#packingExcel').on('click',function(e){
			var targets = new Array();
			$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
				var row = $(this).closest('tr').get(0);
				var td_checked = this.checked;
				if(td_checked){
					targets.push($(this).val());
				}
			});

			$("#targetInfos").val(targets);
			$("#targetNnoForm").attr("action", "/cstmr/printPackingListExcel");
			$("#targetNnoForm").attr("method", "get");
			$("#targetNnoForm").submit();
			
			
		});
		
		
		$('#delCheck').on('click',function(e){
			LoadingWithMask();
			var targets = new Array();
			$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(E){
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
			if(confirm("삭제하시겠습니까?")){
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
			}else{
				$('#mask').hide();  
			}
		});

		$("#blFile").on('click', function(e) {

			var targets = new Array();
			var transCodes = new Array();

			
			$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(e){
				var row = $(this).closest('tr').get(0);
				var td_checked = this.checked;
				if(td_checked){
					targets.push($(this).val());
					transCodes.push($(row).find("input[name='transCode']").val());
				}
			});

			var seen = {};
			var hasDuplicates = false;

			for (var i = 0; i < transCodes.length; i++) {
				var item = transCodes[i];
				if (seen[item]) {
					hasDuplicates = true;
					break;
				} else {
					seen[item] = true;
				}
			}

			if (!hasDuplicates && transCodes.length > 1) {
				alert("동일한 배송사만 선택 가능합니다.");
				return false;
			}

			var transCode = transCodes[0];
			loading.show();
			
			$.ajax({
				url : '/comn/createPdfZipFile',
				type : 'POST',
				beforeSend : function(xhr)
				{   
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				traditional : true,
				data : {nnoList: targets, transCode: transCode},
				timeout: 300000,
				error : function(xhr, status) {
					alert("다운로드 처리 중 시스템 오류가 발생 하였습니다. 관리자에게 문의해 주세요.");
					loading.hide();
					return false;
				},
				success : function(response) {
					loading.hide();

					if (response.status == "S") {
						var fileName = response.fileName;
		               	var downloadUrl = "/comn/downloadPdfZipFile?fileName=" + encodeURIComponent(fileName);
		               	window.location.href = downloadUrl;	
					} else {
						alert("다운로드 중 오류가 발생 하였습니다. 다시 시도해 주세요.");
					}
					
				}
				
			});
		});
		

		var myTable = 
			$('#dynamic-table')
			.DataTable( {
				"dom": 'lt',
				"paging":   false,
		        "ordering": false,
		        "info":     false,
		        "searching": false,
		        "autoWidth": false,
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
				$("#page").val("1");
				$("#idx").trigger('change');
			});
			
			$("#searchTxt").keydown(function(key){
				if(key.keyCode == 13){
					$("#idx").trigger('change');
				}
			});

			$('input[name=date-range-picker]').daterangepicker({
				'applyClass' : 'btn-sm btn-success',
				'cancelClass' : 'btn-sm btn-default',
				locale: {
					applyLabel: 'Apply',
					cancelLabel: 'Cancel',
				}
			})
			
			//or change it into a date range picker
			$('.input-daterange').datepicker({
				autoclose:true,
				todayHighlight:true,
				format:'yyyy-mm-dd'
			});

			$('.dateClass').on('change',function(){
				if($(this).val().length < 10){
					if($(this).val().length == 4){
						$(this).val($(this).val() + "-");
					}
					if($(this).val().length == 7){
						$(this).val($(this).val() + "-");
					}
				}
			});

			$(document).one('ajaxloadstart.page', function(e) {
				$.jgrid.gridDestroy(grid_selector);
				$('.ui-jqdialog').remove();
			});
			
	});
	
	function fn_procDownExcel(formData, url) {
		$.ajax({
			url : url,
			method : 'GET',
			data : formData,
			xhrFields : {
				responseType : 'blob'
			},
			success : function(response) {
				var filename = 'OrderList_' + getToday() + '.xlsx';
				
			    var blob = new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
			    var link = document.createElement('a');
			    link.href = window.URL.createObjectURL(blob);
			    link.download = filename;
			    link.click();
			    loading.hide();
			},
			error : function(xhr, status) {
				alert("시스템 오류가 발생 하였습니다");
				loading.hide();
				return;	
			}
		});
	}
	
	function getToday(){
	    var date = new Date();
	    var year = date.getFullYear();
	    var month = ("0" + (1 + date.getMonth())).slice(-2);
	    var day = ("0" + date.getDate()).slice(-2);

	    return year + month + day;
	}
</script>
		<!-- script addon end -->
	</body>
</html>
