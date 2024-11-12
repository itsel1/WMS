<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp"%>
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
</style>
<!-- basic scripts -->
</head>
<title>배송대행 신청서</title>
<body class="no-skin">
	<!-- headMenu Start -->
	<div class="site-wrap">
		<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp"%>
		<!-- headMenu End -->

		<!-- Main container Start-->
		<div class="toppn">
			<div class="container">
				<div class="row">
					<div class="col-md-12 mb-0" style="font-size: 14px !important;">
						<a>배송대행 신청</a> <span class="mx-2 mb-0">/</span> <strong
							class="text-black">배송대행 신청서</strong>
					</div>
				</div>
			</div>
		</div>
		<!-- Main container Start-->
		<div class="container">
			<div class="page-content noneArea">
				<div class="page-header noneArea">
					<h3>배송대행 신청서</h3>
				</div>
				<form name="uploadForm" id="uploadForm" enctype="multipart/form-data">
					<div id="inner-content-side">
						<input type="hidden" name="testTrans" id="testTrans" value="${transCode}" />
						<input type="hidden" name="page" id="page"/>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<div class="row">
							<div class="col-xs-12 col-md-2 form-group">
								<select class="chosen-select-start form-control tag-input-style width-100 pd-1rem nations" id="transCode" name="transCode">
									<option value=""> Please select </option>
									<c:forEach items="${userDstnNation}" var="userDstnNation">
										<%-- <option value="${userDstnNation.stationCode}_${userDstnNation.dstnNation}_${userDstnNation.transCode}">${userDstnNation.orgNationEName} - ${userDstnNation.dstnNationEName}</option> --%>
										<option value="${userDstnNation.transCode}">${userDstnNation.nationCode}</option>
									</c:forEach>
								</select>
								<%-- <input type="hidden" id="orgStation" name="orgStation" value="">
								<input type="hidden" id="dstnNation" name="dstnNation" value="">
								<input type="hidden" id="transCode" name="transCode" value="${targetCode}"> --%>
							</div>
							<div class="col-xs-12 col-md-7 form-group">
								<input type="file" id="excelFile" name="excelFile"
									style="display: initial; width: 50%; cursor: pointer; border: 1px solid #D5D5D5;"
									accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
								<i class="fa fa-times bigger-110" id="delBtns" name="delBtns" style="cursor: pointer;"></i>
								&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-white btn-inverse btn-xs" id="testBtn" name="testBtn">excel Form 다운로드</button>
							</div>
							<div class="col-xs-12 col-md-3" style="text-align: right;">
								<button type="button" class="btn btn-sm btn-success " name="excelUp" id="excelUp">
									엑셀 파일 적용하기
								</button>
								<button type="button" class="btn btn-sm btn-primary" name="rgstrOne" id="rgstrOne">
									수기등록
									<!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
								</button>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-md-6" id="rgstr" style="text-align: left">
								<button type="button" class="btn btn-sm btn-primary" name="rgstrCheck" id="rgstrCheck">
									배송자료 등록
									<!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
								</button>
								<button type="button" class="btn btn-sm btn-danger" name="delCheck" id="delCheck">
									삭제
									<!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
								</button>
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
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
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
			jQuery(function($) {
				$(document).ready(function() {
					$("#forthMenu").toggleClass('open');
					$("#forthMenu").toggleClass('active'); 
					$("#4thOne").toggleClass('active');
					jQuery('.errorCol').parent().parent().css('background-color', 'rgb(255,247,247)');
					$("#transCode").val($("#testTrans").val());
					$("#transCode").trigger('change'); 
					/* $("#totals").val($("#transCode").val());
					*/
				
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

				$("#transCode").on('change',function(e){

					/* var splitString = $("#totals").val().split('_');
					$("#orgStation").val(splitString[0]);
					$("#dstnNation").val(splitString[1]);
					$("#transCode").val(splitString[2]); */

					if($("#transCode").val() == ''){
						$("#table-contents").html("");
						return;
					}
						
					
					var formData = $("#uploadForm").serialize();
					$.ajax({
						url:'/cstmr/apply/shpngAgncyTable',
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
					
				})



				$('#rgstrOne').on('click', function(e) {
					location.href = '/cstmr/apply/shpng/registOne';
				});

				$('#delBtns').on('click', function(e) {
					$('#excelFile').val("");
				});


				$('#excelUp').on('click', function(e) {
					if($('#excelFile').val() == ""){
						alert("선택된 파일이 없습니다")
						return
					}
					var datass = new FormData();
					datass.append("file", $("#excelFile")[0].files[0])
					LoadingWithMask();
					$.ajax({
						type : "POST",
						beforeSend : function(xhr)
						{   //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						url : "/cstmr/apply/shpngAgncyExcelUpload?formSelect="+$("#transCode").val(),
						data : datass,
						processData: false,
			            contentType: false,
						error : function(){
							alert("subpage ajax Error");
							$("#transCode").trigger('change');
							$('#mask').hide();
						},
						success : function(data){
							if(data == "F"){
								alert("등록중 오류가 발생했습니다.")
							}
							$("#transCode").trigger('change');
							$('#mask').hide();
						}
					})
				});

				$('#rgstrCheck').on('click',function(e){
					LoadingWithMask();
					var targets = new Array();
					$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if(td_checked){
							if(row.lastElementChild.lastElementChild.lastElementChild.childElementCount == 0){
								targets.push($(this).val());
							}
						}
					});
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
							transCode : $("#transCode").val()
						},
						success : function(data) {
							if(data == "S"){
								alert("등록 되었습니다.")
								$("#transCode").trigger('change');
								$('#mask').hide();
							}else if(data == "F"){
								alert("등록중 오류가 발생하였습니다. 관리자에게 문의해 주세요.")
								$("#transCode").trigger('change');
								$('#mask').hide();
							}else if(data =="N"){
								alert("등록할 수 있는 데이터가 없습니다.");
								$("#transCode").trigger('change');
								$('#mask').hide();
							}
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다. 입력정보를 확인해 주세요.");
			                $("#transCode").trigger('change');
			                $('#mask').hide();
			            }
					})
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
								alert("삭제 되었습니다.")
								$("#transCode").trigger('change');
								$('#mask').hide();
							}else if(data == "F"){
								alert("삭제중 오류가 발생하였습니다. 관리자에게 문의해 주세요.")
								$("#transCode").trigger('change');
								$('#mask').hide();
							}
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("삭제에 실패 하였습니다. 입력정보를 확인해 주세요.");
			                $("#transCode").trigger('change');
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

					$("#testBtn").on('click',function(e){
						if($("#transCode").val() == ''){
							alert("지역을 선택해 주세요")
							return;
						}
						
						window.open("/cstmr/apply/excelFormDown?transCode="+$("#transCode").val())

					})
			})
		</script>
	<!-- script addon end -->
</body>
</html>
