<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
.padding0 {
	padding: 0px !important;
}

.colorBlack {
	color: #000000 !important;
}

.table>tbody>tr>td {
	vertical-align: middle !important;
}

.profile-info-name {
	padding: 0px;
	color: black !important;
	text-align: center !important;
}

.profile-user-info-striped {
	border: 0px solid white !important;
}
/* .profile-user-info-striped{border: 0px solid white !important;}
	  .profile-info-name {padding:6px 15px; width:0px; color:black !important; background-color: unset !important;} */
.profile-user-info {
	width: 100% !important;
	margin: 0px;
}

/* .col-xs-12 .col-sm-12 {
	padding: 0px !important;
}
 */
.tdBackColor {
	background-color: #FFFFFF;
}

.tdHeader {
	border: 1px solid black;
	padding: 0px !important;
}

.tdHeaderB {
	border: 1px solid black;
	border-bottom: none;
}

.tdHeaderT {
	border: 1px solid black;
	border-top: none;
}

.tdHeaderL {
	border: 1px solid black;
	border-left: none;
	border-right: none;
}

.tdHeaderR {
	border: 1px solid black;
	border-right: none;
	border-left: none;
}

.tdHeaderTR {
	border: 1px solid black;
	border-top: none;
}

@media ( min-width :768px) {
	.tdHeaderB {
		border: 1px solid black;
		border-bottom: none;
	}
	.tdHeaderT {
		border: 1px solid black;
		border-top: none;
	}
	.tdHeaderL {
		border: 1px solid black;
		border-left: none;
	}
	.tdHeaderR {
		border: 1px solid black;
		border-right: none;
	}
	.tdHeaderTR {
		border: 1px solid black;
		border-right: none;
		border-top: none;
	}
}

@media only screen and (max-width:480px) {
	.hidden-480 {
		display: none !important;
	}
}

@media only screen and (max-width:480px) {
	.hidden-489 {
		display: inline!important;
	}
}
</style>
	<!-- basic scripts -->

		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script src="assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/assets/js/bootstrap.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>

	<!-- basic scripts End-->
	</head> 
	<title>Aramex List</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			<!-- SideMenu Start -->
			<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp" %>
			<!-- SideMenu End -->
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
							<li>
								Aramex
							</li>
							<li class="active">Aramex Upload</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								Aramex Upload
							</h1>
						</div>
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
							<div id="search-div">
								<br>
								<form class="hidden-xs hidden-sm" name="dtSearchForm" id="dtSearchForm" action="/mngr/aramex/aramexList" >
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="row">
										<div class="col-xs-12 col-sm-11 col-md-10">
										<span class="input-group" style="display: inline-flex; width:100%">
											<input type="text" class="form-control" style="max-width: 80px;" value="UserID" disabled>
											<select name="errorYn" class="form-control" style="max-width: 120px;" >
												<option value="">::전체::</option>
												<option value="N">정상 건 </option>
												<option value="Y">오류 건 </option>
											</select>
											<input type="text" class="form-control" style="max-width: 80px;" value="HsCode" disabled>
											<select name="hsCodeYn" class="form-control" style="max-width: 120px;" >
												<option value="">::전체::</option>
												<option value="N">등록 건 </option>
												<option value="Y">미등록 건 </option>
											</select>
												<button type="button" class="btn btn-default no-border btn-sm" name="dtSearchBtn" id="dtSearchBtn">
													<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
												</button>
											</span>
										</div>
									</div>
								</form>
								<br>
								<br>
								<br>
								
								<form name="uploadAramexExcel" id="uploadAramexExcel" enctype="multipart/form-data">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="col-xs-12 col-md-12" style="text-align: left;">
										<input type="file" id="excelFile" name="excelFile"
												style="display: initial; width: 20%; cursor: pointer; border: 1px solid #D5D5D5;"
												accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
											<i class="fa fa-times bigger-110" id="delBtns" name="delBtns" style="cursor: pointer;"></i>
											<button type="button" class="btn btn-white btn-inverse btn-xs" id="excelUpBtn" name="excelUpBtn">excel 적용</button>
									</div>
									<div class="col-xs-12 col-md-12" style="text-align: right;">
										<button type="button" class="btn btn-white btn-inverse btn-xs" id="sendApply" name="sendApply">등록하기</button>
										<button type="button" class="btn btn-white btn-inverse btn-xs" id="btn_Delete" name="">삭제</button>
									</div>
									</br>
								</form>
								<br>
							</div>
							<br>
							<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
							<div id="table-contents" class="table-contents" style="width:100%;height:500px; overflow: auto;">
									<table style="width:2000px;" class="table table-bordered table-striped">
										<thead>
											<tr>
												<th class="center colorBlack">
													Num
												</th>
												<th class="center colorBlack">
													출발 Station
												</th>
												<th class="center colorBlack">
													도착 Station
												</th>
												<th class="center colorBlack">
													MawbNo
												</th>
												<th class="center colorBlack">
													HawbNo
												</th>
												<th class="center colorBlack">
													수취인명
												</th>
												<th class="center colorBlack">
													수취인 국가
												</th>
												<th class="center colorBlack">
													수취인 Status
												</th>
												<th class="center colorBlack">
													수취인 City
												</th>
												<th class="center colorBlack">
													수취인 ZipCode
												</th>
												<th class="center colorBlack">
													수취인 Adress
												</th>
												<th class="center colorBlack">
													수취인 Tel
												</th>
												<th class="center colorBlack">
													수취인 Email
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${aramexList}" var="aramexList" varStatus="status">
													<tr>
														<td  style="width:60px;text-align:center">
															${aramexList.num}
														</td>
														<td style="width:120px;text-align:center">
															${aramexList.origin}
														</td>
														<td style="width:120px;text-align:center">
															<span>${aramexList.destination}</span>
														</td>
														<td style="width:120px;text-align:center">
															<span>${aramexList.mawbno}</span>
														</td>
														<td style="width:120px;text-align:center">
															<span>${aramexList.awb}</span>
														</td>
														<td style="width:150px;">
															${aramexList.consigneename}
														</td>
														<td style="width:120px;text-align:center">
															${aramexList.destcountry}
														</td>
														<td style="width:120px;text-align:center">
															<span>${aramexList.deststate}</span>
														</td>
														<td style="width:120px;text-align:center">
															<span>${aramexList.destcity}</span>
														</td>
														<td style="width:120px;text-align:center">
															${aramexList.destzipcode}
														</td>
														<td style="text-overflow: ellipsis;">
															${aramexList.consigneeaddress}
														</td>
														<td>
															<span>${aramexList.consigneetel}</span>
														</td>	
														<td>
															<span>${aramexList.consigneeemail}</span>
														</td>
													</tr>
													<tr>
														<th colspan="20">
															<c:choose>
															    <c:when test="${aramexList.hsCode eq ''}">
															        <p style="color:blue;padding:0px;font-size:12px;margin:0px;">HsCode 미등록 건 </p>
															    </c:when>
															    <c:otherwise>
															        <p style="color:blue;padding:0px;font-size:12px;"> </p>
															    </c:otherwise>
															</c:choose>
															   <p style="color:red;padding:0px;font-size:12px;">${aramexList.errorMsg}</p>
														</th>
												    </tr>
											</c:forEach>
										</tbody>
									</table>
									<form id="stockForm" action="">
									<input type="hidden" id = "orgStation" name="orgStation" value="">
									<input type="hidden" id = "groupIdx" name="groupIdx" value="">
									<input type="hidden" id = "userId" name="userId" value="">
								</form>
								
								<form id="mgrMsgForm" name="mgrMsgForm">
								</form>
							</div>
							<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>

		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		
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
		<script type="text/javascript">
		
			$("#dtSearchBtn").on('click',function(){
				$("#dtSearchForm").attr("method", "get");            //get방식으로 바꿔서
				$("#dtSearchForm").submit();
			});

			$("#excelUpBtn").on('click', function(e){

				if($("#excelFile").val() ==""){
					alert("선택된 파일이 없습니다.");
					return false;
				}

				var datass = new FormData();
				datass.append("file",$("#excelFile")[0].files[0])
				LoadingWithMask();
				$.ajax({
					type : "POST",
					beforeSend : function(xhr)
					{   //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					url : "/mngr/aramex/aramexExcelUpload?formSelect="+$("#formSelect").val(),
					data : datass,
					processData: false,
		            contentType: false,
					error : function(){
						alert("subpage ajax Error");
						location.href="/mngr/aramex/aramexList";
					},
					success : function(data){
						if(data == "F"){
							alert("등록중 오류가 발생했습니다.")
							location.href="/mngr/aramex/aramexList";
						}
						alert(data);
						location.href="/mngr/aramex/aramexList";
					}
				})
				
				


			})
			
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


			function fn_popupStockCancle(groupIdx, OrgStation, userId){
			

				$("#groupIdx").val(groupIdx);
				$("#orgStation").val(OrgStation);
				$("#userId").val(userId);

				
				var result = confirm("입고 취소 하시겠습니까?");
				if(result){
					var formData = $("#stockForm").serialize();
					$.ajax({
						url:'/comn/StockCancle',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData, 
						success : function(data) {

							alert(data.RST_MSG);
							var Status = data.RST_STATUS;
		
							if(Status == "SUCCESS"){
								location.reload();
							};
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다.");
			            }
					})
				}
			}

			function fn_popupReturn(groupIdx, OrgStation, userId){
				 window.open("/mngr/stock/popupReturn?&groupIdx="+groupIdx+"&orgStation="+OrgStation+"&userId="+userId,"PopupWin", "width=1200,height=500"); 
			}

			function fn_popupTrash(groupIdx, OrgStation, userId){
				 window.open("/mngr/stock/popupTrash?&groupIdx="+groupIdx+"&orgStation="+OrgStation+"&userId="+userId,"PopupWin", "width=1200,height=500"); 
			}
			

			function fn_popupOpen(groupIdx, OrgStation, userId){	
				window.open("/mngr/rls/groupidx/popupMsg?&groupIdx="+groupIdx+"&orgStation="+OrgStation+"&userId="+userId,"PopupWin", "width=1200,height=500");
			}

			function fn_pdfPopup(groupIdx, nno){
				$("#groupIdx").val(groupIdx);
				$("#nno").val(nno);
				window.open("/mngr/order/pdfPopup?nno="+nno+"&groupIdx="+groupIdx,"_blank","toolbar=yes","resizable=no","directories=no","width=480, height=360");
			}
				
			jQuery(function($) {
				$(document).ready(function() {
					$("#1-9thMenu").toggleClass('open');
					$("#1-9thMenu").toggleClass('active'); 
					$("#1-9thOne").toggleClass('active');

					var myTable = $('#dynamic-table').DataTable( {
						"dom" : "t",
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "scrollY" : "580",
						select: {
							style: 'multi',
							selector: 'td:first-of-type'
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
							if(th_checked) myTable.row(row).select();
							else  myTable.row(row).deselect();
						});
					});

					//select/deselect a row when the checkbox is checked/unchecked
					$('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
						var row = $(this).closest('tr').get(0);
						if(this.checked) myTable.row(row).deselect();
						else myTable.row(row).select();
					});
					
				})
			});
		</script>
		<!-- script addon end -->
		<script type="text/javascript">
			jQuery(function($) {
				$("#sendApply").on('click',function(e){
					$.ajax({
						url:'/mngr/aramex/aramexAplly',
						type: 'GET',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: '', 
						success : function(data) {

							alert(data.RST_MSG);
							var Status = data.RST_STATUS;
		
							if(Status == "SUCCESS"){
								location.reload();
							};
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
			            }
					})
				})
				
			})

			})
		</script>
		
	</body>
</html>
