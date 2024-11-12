<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<style type="text/css">
	.no-gutters {
	padding: 0 !important;
	margin: 0 !important;
	}
	
	.inStyle{
		background-color: #93CBF9 !important;
		font-size:70px !important; 
		width:100% !important;
	}
	
	.real-blue{
		color:blue !important;
	}
	
	.real-red{
		color:red !important;
	}
	.table>thead>tr>th{
		color: #000000 !important;
	}
	.space-bottom-10{
		margin:0 0 10px 0;
		max-height: 1px;
		min-height: 1px;
		overflow: hidden;
	}
	
	</style>
	</head> 
	<body class="no-skin">
		<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
		<div id="detail-contents" class="detail-contents">
			<div id="home" class="tab-pane in active">
				<div class="row">
					<div class="col-sm-12">
						<h2>MAWB NO : ${mawbNo}</h2>
						<!-- <button class="btn btn-success btn-sm" id="downExpLicence" name="downExpLicence">
							세관신고 자료 다운로드
							<i class="ace-icon fa fa-download  align-top bigger-125 icon-on-right">
							</i>
						</button>
						<div class="hidden">
						<input type="file" id="excelFile" name="excelFile"
								style="display: initial; width: 50%; cursor: pointer; border: 1px solid #D5D5D5;"
								accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
						</div>
						<button class="btn btn-purple btn-sm" id="upExpLicence" name="upExpLicence">
							수출신고 자료 업로드
							<i class="ace-icon fa fa-upload  align-top bigger-125 icon-on-right">
							</i>
						</button>
						
						<button class="btn btn-primary btn-sm" id="sendExpLicence" name="sendExpLicence">
							수출신고 자료 전송
							<i class="ace-icon fa fa-send  align-top bigger-125 icon-on-right">
							</i>
						</button> -->
						<input type="hidden" id="targetMawbNo" name="targetMawbNo" value="${mawbNo}"/>
					</div>
				</div>
				<!-- 수출신고 자료 업로드 양식 > A2,B2부터 수출신고번호 - 고유번호 순으로 작성 -->
				<div class="hr space-bottom-10 dotted"></div>
				<br/>
				<div id="table-contents" class="table-contents center" style="width:100%; overflow: auto;">
					<div class="display" style="width:1600px;">
						<div class="row" >
							<div class="col-sm-2" style="text-align: left;">
								<div style="width:50%;float:left;">
									<input type="text" style="width:100%;" id="bagNo" name="bagNo" value="" placeholder="bag no 입력">
								</div>
								<div style="width:50%;float:right;">
									<button type="button"  class="btn btn-primary btn-sm" id="updateBagNo">bag no 업데이트</button>
								</div>
								<%-- <label style="font-weight:bold;">Bag No :&nbsp;&nbsp;</label>
								<select id="bagNo" name="bagNo" style="width:100px;">
									<option value="all">::전체::</option>
									<c:if test="${!empty bagNoList}">
										<c:forEach items="${bagNoList}" var="bagList">
											<option value="${bagList.BAG_NO}">${bagList.BAG_NO}</option>
										</c:forEach>
									</c:if>
								</select> --%>
							</div>
							<div class="col-sm-10" style="text-align: right;">
								<button class="btn btn-primary btn-sm" id="printCheck" name="printCheck">
									운송장 출력
									<i class="ace-icon fa fa-print  align-top bigger-125 icon-on-right">
									</i>
								</button>
								<input type="button" class="btn btn-white btn-inverse" id="printTypeC" value="BlPrint (C)"/>
								<button class="btn btn-danger btn-sm" id="returnHawb" name="returnHawb">
									출고 취소
									<i class="ace-icon fa fa-reply icon-only"></i>
								</button>	
								<button class="btn btn-primary btn-sm" id="downExcel" name="downExcel">
									Excel 다운로드
									<i class="ace-icon fa fa-print  align-top bigger-125 icon-on-right">
									</i>
								</button>	
								<button class="btn btn-primary btn-sm" id="commercial" name="commercial">
									commercial invoice 출력
									<i class="ace-icon fa fa-print  align-top bigger-125 icon-on-right">
									</i>
								</button>
								<button class="btn btn-primary btn-sm" id="commercialExcel" name="commercialExcel">
									commercialExcel invoice 출력
									<i class="ace-icon fa fa-print  align-top bigger-125 icon-on-right">
									</i>
								</button>
								<%-- <c:if test="${hawbVoList[0].transCode eq 'FB' || hawbVoList[0].transCode eq 'FB-EMS'}">
								<input type="button" class="btn btn-primary btn-sm" value="FASTBOX 배송지시" id="sendFastbox">
								</c:if> --%>
								<!-- <button class="btn btn-primary btn-sm" id="TEST123" name="TEST123">
									임시버튼
									<i class="ace-icon fa fa-send  align-top bigger-125 icon-on-right">
									</i>
								</button> -->						
								
								<br/>
								<br/>
							</div>

							<div class="col-sm-12 center" id="data-table">
								<table id="dynamic-table" class="table table-bordered table-hover">
									<thead>
										<tr>
											<th class="center">
												<label class="pos-rel">
														<input type="checkbox" class="ace" /> <span class="lbl"></span>
												</label>
											</th>
											<th class="center colorBlack">
												No.
											</th>
											<th class="center colorBlack">
												Bag No.
											</th>
											<th class="center colorBlack">
												HawbNo
											</th>
											<th class="center colorBlack">
												Agency BL
											</th>
											<th class="center colorBlack">
												UserID
											</th>
											<th class="center colorBlack">
												Company
											</th>
											<th class="center colorBlack">
												수취인
											</th>
											<th class="center colorBlack">
												운송사
											</th>
											<th class="center colorBlack">
												BOX
											</th>
											<th class="center colorBlack">
												W/T(A)
											</th>
											<th class="center colorBlack">
												W/T(C)
											</th>
											<th class="center colorBlack">
												FTP 전송일자
											</th>
											<c:if test="${hawbVoList[0].transCode eq 'FB' || hawbVoList[0].transCode eq 'FB-EMS'}">
											<th class="center colorBlack">
												배송지시 결과
											</th>	
											</c:if>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${hawbVoList}" var="hawbVO" varStatus="status">
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" id="check${status.count}" name="nno[]" value="${hawbVO.nno}"/>
														<span class="lbl"></span>
													</label>
												</td>
												<td>
													${status.count}
												</td>
												<td>
													${hawbVO.bagNo}
												</td>
												<td id="hawb" name="hawb">
													<a href="/mngr/aplctList/modifyAdminOne?transCode=${hawbVO.transCode}&types=check&nno=${hawbVO.nno}" onclick="window.open(this.href,'_blank'); return false">${hawbVO.hawbNo}</a>
												</td>
												<td>
													<c:if test="${!empty hawbVO.agencyBl}">
														${hawbVO.agencyBl}
													</c:if>
													<c:if test="${empty hawbVO.agencyBl}">
														-
													</c:if>
												</td>
												<td>
													${hawbVO.userId}
												</td>
												<td>
													${hawbVO.comName}
												</td>
												<td>
													${hawbVO.cneeName}
												</td>
												<td>
													${hawbVO.transName}
												</td>
												<td>
													${hawbVO.boxCnt}
												</td>
												<td>
													${hawbVO.wta}
												</td>
												<td>
													${hawbVO.wtc}
												</td>
												<td>
													<c:if test="${!empty hawbVO.ftpInDate}">
														${hawbVO.ftpInDate}
													</c:if>
													<c:if test="${empty hawbVO.ftpInDate}">
														
													</c:if>
												</td>
												<c:if test="${hawbVO.transCode eq 'FB' || hawbVO.transCode eq 'FB-EMS'}">
												<td>${hawbVO.fbSendYn}</td>
												</c:if>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<form name="targetNnoForm" id="targetNnoForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input type="hidden" name="targetInfos" id="targetInfos">
						</form>
						
					</div>
				</div>
			</div><!-- /#home -->
		</div>
			
		<!-- script addon start -->
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
		
		<!-- script addon End -->
		<script type="text/javascript">
			var count = 0;
			jQuery(function($) {
				$(document).load(function() {
					$("#6thMenu").toggleClass('open');
					$("#6thMenu").toggleClass('active'); 
					$("#6thFor").toggleClass('active');
					$.ajaxSetup({
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					    	xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				    	}
					});
				});

				//Table Function

				var myTable = 
					$('#dynamic-table')
					.DataTable( {
						"dom" : "t",
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "scrollY" : "500",
						select: {
							style: 'multi',
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

					$('#updateBagNo').on('click', function() {
						var hawbList = new Array();
						var totalCnt = $(".selected").size();
						for(var i = 0; i < totalCnt; i++){
							hawbList.push($($('.selected').get(i)).find("#hawb").text().trim());
						}
						if(hawbList.length==0){
							alert("체크된 HawbNo가 없습니다.");
							return;
						}

						if($('#bagNo').val() == "") {
							alert("bagNo 입력은 필수입니다.");
							return;
						}

						$.ajax({
							url : '/mngr/rls/updateBagNo',
							type : 'POST',
 							beforeSend : function(xhr)
 							{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
 							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
 							},
							data : {target: hawbList, bagNo: $("#bagNo").val()},
 							success : function(data) {
								alert("등록 되었습니다.");
								location.reload(); 
 				            }, 		    
 				            error : function(xhr, status) {
								alert("시스템 오류 발생");
 				            }
						});
					});

					$('#returnHawb').on('click', function(){
						var delList = new Array();
						var totalCnt = $(".selected").size();
						for(var i = 0; i < totalCnt; i++){
							delList.push($($('.selected').get(i)).find("#hawb").text().trim());
						}
						if(delList.length==0){
							alert("체크된 HawbNo가 없습니다.");
							return;
						}
 						$.ajax({
 							url:'/mngr/rls/mawbHawbReturn',
 							data:{target: delList,
 								  mawbNo : $("#targetMawbNo").val()},
 							type: 'POST',
 							beforeSend : function(xhr)
 							{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
 							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
 							},
 							success : function(data) {
 	 							if(data.resultMsg == "S"){
 	 								jQuery(grid_selector).jqGrid("clearGridData");
 	 								jQuery(grid_selector).jqGrid("setGridParam",
 	 								{
 	 									url:"/mngr/rls/mawbLoad",
 	 									datatype: "json"
 	 								}).trigger("reloadGrid");
 	 								
 	 								$('#testarea').empty();
 	 								alert("총 "+totalCnt+"건이 출고 취소 되었습니다.");
 	 								$('#testarea').load("/mngr/rls/mawbListField?target="+data.mawbNo);
 	 							}else if (data.resultMsg == "F"){
									alert("데이터 작업중 오류가 발생했습니다. 관리자에게 문의 해 주세요")
 	 							}else {
									alert(data.resultMsg);
 	 							}
 				            }, 		    
 				            error : function(xhr, status) {
 				                alert(xhr + " : " + status);
 				                alert("출고 취소에 실패 하였습니다.");
 				            }
 						})

					});

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
						window.close();
					})
					
					$("#printTypeC").on('click', function(e) {
					var targets = new Array();
					
					 $("input[name='nno[]']").each(function(){
						 var check =  this.checked;
						 if(check){
							targets.push($(this).val());
						 };
					});

					 if(targets == ""){
						alert("출력할 운송장이 없습니다.");
						return false;
					}
					
					window.open("/comn/printHawb?targetInfo="+targets+"&formType="+$("#transCode").val()+"&labelType=C","popForm","_blank");
				});
				
					
					$('#downExcel').on('click',function(e){
						$("#targetInfos").val($("#targetMawbNo").val());
						$("#targetNnoForm").attr("action", "/mngr/rls/downMawbExcel");    ///board/modify/로
						$("#targetNnoForm").attr("method", "post");            //get방식으로 바꿔서
						$("#targetNnoForm").submit();
					});

					/* fastbox 배송지시 */
					$("#sendFastbox").on('click', function(e) {
						var delList = new Array();
						var totalCnt = $(".selected").size();
						for(var i = 0; i < totalCnt; i++){
							delList.push($($('.selected').get(i)).find("#hawb").text().trim());
						}
						if(delList.length==0){
							alert("체크된 HawbNo가 없습니다.");
							return;
						}
						
 						$.ajax({
 							url:'/mngr/rls/fastboxSendDelivery',
 							data:{target: delList,
 								  mawbNo : $("#targetMawbNo").val()},
 							type: 'POST',
 							beforeSend : function(xhr)
 							{   
 							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
 							},
 							success : function(data) {
 	 							if(data.resultMsg == "S"){
 	 								jQuery(grid_selector).jqGrid("clearGridData");
 	 								jQuery(grid_selector).jqGrid("setGridParam",
 	 								{
 	 									url:"/mngr/rls/mawbLoad",
 	 									datatype: "json"
 	 								}).trigger("reloadGrid");
 	 								
 	 								$('#testarea').empty();
 	 								alert("총 "+totalCnt+"건이 배송지시 처리 되었습니다.");
 	 								$('#testarea').load("/mngr/rls/mawbListField?target="+data.mawbNo);
 	 							}else if (data.resultMsg == "F"){
									alert("데이터 작업중 오류가 발생했습니다. 관리자에게 문의 해 주세요")
 	 							}else {
									alert(data.resultMsg);
 	 							}
 				            }, 		    
 				            error : function(xhr, status) {
 				                alert(xhr + " : " + status);
 				            }
 						})
					})
					
					
					
					$('#commercial').on('click',function(e){
						var targets = new Array();
						$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
							var row = $(this).closest('tr').get(0);
							var td_checked = this.checked;
							if(td_checked){
								targets.push($(this).val());
							}
						});
						window.open("/comn/printCommercial?targetInfo="+targets+"&formType="+$("#formSelect").val(),"popForm","_blank");
						window.close();
					})
					
					$('#commercialExcel').on('click',function(e){
						var targets = new Array();
						$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
							var row = $(this).closest('tr').get(0);
							var td_checked = this.checked;
							if(td_checked){
								targets.push($(this).val());
							}
						});

						$("#targetInfos").val(targets);
						$("#targetNnoForm").attr("action", "/comn/printCommercialExcel");
						$("#targetNnoForm").attr("method", "get");
						$("#targetNnoForm").submit();
						
						
					})
					
					/* $('#TEST123').on('click',function(e){
						console.log("ASD");
						var targets = new Array();
						$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
							var row = $(this).closest('tr').get(0);
							var td_checked = this.checked;
							if(td_checked){
								targets.push($(this).val());
							}
						});
						$("#targetInfos").val(targets);
						
						var formData = $("#targetNnoForm").serialize();
						$.ajax({
							url:'/mngr/rls/TEST123',
							type: 'POST',
							data : formData,
							beforeSend : function(xhr)
							{   //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							success : function(data) {
								//location.reload();
				            }, 		    
				            error : function(xhr, status) {
				                alert(xhr + " : " + status);
				                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
				            }
						})
					}) */
					

					$('#downExpLicence').on('click',function(e){
						var targets = new Array();
						$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
							var row = $(this).closest('tr').get(0);
							targets.push($(this).val());
						});
						$("#targetInfos").val(targets);
						$("#targetNnoForm").attr("action", "/mngr/rls/downExpLicence");    ///board/modify/로
						$("#targetNnoForm").attr("method", "post");            //get방식으로 바꿔서
						$("#targetNnoForm").submit();
					})
					
					$('#upExpLicence').on('click',function(e){
						$("#excelFile").trigger("click");
					})
					$("#excelFile").on('change',function(e){
						var datass = new FormData();
						datass.append("file", $("#excelFile")[0].files[0]);
						LoadingWithMask();
						
						$.ajax({
							url:'/mngr/rls/upExpLicence',
							type: 'POST',
							data : datass,
							processData: false,
				            contentType: false,
							beforeSend : function(xhr)
							{ 
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							success : function(data) {
								
				            }, 		    
				            error : function(xhr, status) {
				                alert(xhr + " : " + status);
				                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
				            },
				            complete : function() {
				            	$('#mask').hide();
				            	$("#excelFile").val("");
				            }
						})
					})
					/*
					$("#bagNo").change(function() {
						var bagNo = $("#bagNo").val();
						var mawbNo = $("#targetMawbNo").val();

						var datass = {bagNo: bagNo, mawbNo: mawbNo};
						console.log(datass);
						
						$.ajax({
							url : '/mngr/rls/mawbListBagField',
							type : 'POST',
							data : datass,
							beforeSend : function(xhr)
							{ 
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							success : function(result) {
								var values = [];
								values = result.hawbList;

								$("#data-table").empty();
								
								html = "";

								html += '<table id="dynamic-table" class="table table-bordered table-hover">';
								html += '<thead><tr><th class="center"><label class="pos-rel"><input type="checkbox" class="ace"/>';
								html += '<span class="lbl"></span></label></th><th class="center colorBlack">No.</th><th class="center colorBlack">Bag No.</th>';
								html += '<th class="center colorBlack">HawbNo</th><th class="center colorBlack">Agency BL</th><th class="center colorBlack">UserID</th>';
								html += '<th class="center colorBlack">Company</th><th class="center colorBlack">수취인</th>';
								html += '<th class="center colorBlack">운송사</th><th class="center colorBlack">BOX</th>';
								html += '<th class="center colorBlack">W/T(A)</th><th class="center colorBlack">W/T(C)</th>';
								html += '<th class="center colorBlack">FTP 전송일자</th></tr></thead><tbody>';
								
								$.each(values, function(index, value) {
									var cnt = index+1;
									
									html += '<tr><td class="center" style="word-break:break-all;vertical-align:middle;"><label class="pos-rel">';
									html += '<input type="checkbox" class="ace" id="check'+cnt+'" name="check'+cnt+'" value="'+value.NNO+'">';
									html += '<span class="lbl"></span></label></td>';
									html += '<td>'+cnt+'</td><td>'+value.BAG_NO+'</td>';
									html += '<td id="hawb" name="hawb"><a href="/mngr/aplctList/modifyAdminOne?transCode='+value.TRANS_CODE+'&types=check&';
									html += 'nno='+value.NNO+'" onclick="window.open(this.href, "_blank"); return false">'+value.HAWB_NO+'</a></td>';
									if (value.AGENCY_BL == '') {
										html += '<td>-</td>';
									} else {
										html += '<td>'+value.AGENCY_BL+'</td>';	
									}
									html += '<td>'+value.USER_ID+'</td><td>'+value.COM_NAME+'</td><td>'+value.CNEE_NAME+'</td>';
									html += '<td>'+value.TRANS_NAME+'</td><td>'+value.BOX_CNT+'</td><td>'+value.WTA+'</td>';
									html += '<td>'+value.WTC+'</td>';
									if (value.FTP_IN_DATE == null) {
										html += '<td></td>';
									} else {
										html += '<td>'+value.FTP_IN_DATE+'</td>';
									}
									html += '</tr>';
								}) 
								html += '</tbody></table>';
								
								$("#data-table").append(html);

								var myTable = 
									$('#dynamic-table')
									.DataTable( {
										"dom" : "t",
										"paging":   false,
								        "ordering": false,
								        "info":     false,
								        "searching": false,
								        "scrollY" : "500",
										select: {
											style: 'multi',
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
												

							},
							error : function(xhr, status) {
								alert("조회에 실패 하였습니다.");
							}
						})
					})
					*/
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
			})
		</script>
		<!-- script addon end -->
	</body>
</html>
