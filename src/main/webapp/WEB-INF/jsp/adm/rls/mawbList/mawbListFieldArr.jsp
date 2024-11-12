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
						<div class="row" style="text-align: left;">
							<div class="col-sm-12">
								<label style="font-weight:bold;">Bag No :&nbsp;&nbsp;</label>
								<select id="bagNo" name="bagNo" style="width:100px;">
									<option value="all">::전체::</option>
									<c:if test="${!empty bagNoList}">
										<c:forEach items="${bagNoList}" var="bagList">
											<option value="${bagList.BAG_NO}">${bagList.BAG_NO}</option>
										</c:forEach>
									</c:if>
								</select>
								<!-- <button class="btn btn-primary btn-sm" id="inRegistCheck" name="inRegistCheck">
									입고 확인
									<i class="ace-icon fa fa-print  align-top bigger-125 icon-on-right">
									</i>
								</button> -->
								<!-- <button class="btn btn-primary btn-sm" id="downExcel" name="downExcel">
									Excel 다운로드
									<i class="ace-icon fa fa-print  align-top bigger-125 icon-on-right">
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
												Bag No
											</th>
											<th class="center colorBlack">
												HawbNo
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
												W/T(V)
											</th>
											<th class="center colorBlack">
												현장 수령
											</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${hawbVoList}" var="hawbVO" varStatus="status">
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" id="check${status.count}" name="check$" value="${hawbVO.nno}"/>
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
													<c:if test="${!empty hawbVO.arrIn}">
														<a href="/mngr/rls/mawbListArrChk?target=${mawbNo}&nno=${hawbVO.nno}" onclick="window.open(this.href,'_blank','width=1500, height=960','resizable=yes','directories=no'); return false">수령 확인</a>
													</c:if>
													<c:if test="${empty hawbVO.arrIn}">
														
													</c:if>
													
												</td>
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


					$('#inRegistCheck').on('click',function(e){
						window.open("/mngr/rls/inRegistMawb?target="+$("#targetMawbNo").val(),"popForm","_blank");
						window.close();
					})
					
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
								html += '<th class="center colorBlack">HawbNo</th><th class="center colorBlack">UserID</th>';
								html += '<th class="center colorBlack">Company</th><th class="center colorBlack">수취인</th>';
								html += '<th class="center colorBlack">운송사</th><th class="center colorBlack">BOX</th>';
								html += '<th class="center colorBlack">W/T(A)</th><th class="center colorBlack">W/T(C)</th>';
								html += '<th class="center colorBlack">현장 수령</th></tr></thead><tbody>';
								
								$.each(values, function(index, value) {
									var cnt = index+1;
									
									html += '<tr><td class="center" style="word-break:break-all;vertical-align:middle;"><label class="pos-rel">';
									html += '<input type="checkbox" class="ace" id="check'+cnt+'" name="check'+cnt+'" value="'+value.NNO+'">';
									html += '<span class="lbl"></span></label></td>';
									html += '<td>'+cnt+'</td><td>'+value.BAG_NO+'</td>';
									html += '<td id="hawb" name="hawb"><a href="/mngr/aplctList/modifyAdminOne?transCode='+value.TRANS_CODE+'&types=check&';
									html += 'nno='+value.NNO+'" onclick="window.open(this.href, "_blank"); return false">'+value.HAWB_NO+'</a></td>';
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
