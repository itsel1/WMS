<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/bootstrap-datepicker3.min.css" />
	<link rel="stylesheet" href="/assets/css/ui.jqgrid.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	
	<style type="text/css">
		#hsCodeBtn {
			margin-left: 150px;
			padding: 5px 8px;
			border: 1px solid #6FAED9;
			background-color: #FFFFFF;
		}
	
		#hsCodeBtn:hover {
			background-color: #6FAED9;
			color: #ffffff;
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
	<title>MAWB List</title>
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
								출고
							</li>
							<li class="active">Manifest</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								Manifest Update
							</h1>
						</div>
						<div>
							<form id="searchForm" name="searchForm" action="/mngr/rls/manifest">
								<table class="table table-bordered" style="width:25%;">
									<thead>
										<tr>
											<th class="center">MAWB NO</th>
											<td style="padding:0px;">
												<input type="text" name="mawbNo" id="mawbNo" style="width:100%;height:45px;" value="${params.mawbNo}">
											</td>
											<td class="center">
												<input type="button" id="submit_btn" value="검색" class="btn btn-sm btn-primary btn-white">
											</td>
										</tr>
									</thead>
								</table>
							</form>
						</div>
						<div style="width:50%; margin-bottom:10px; margin-top:30px;">
							 <input type="file" id="excelFile" name="excelFile" style="display: initial; width: 30%; cursor: pointer; border: 1px solid #D5D5D5;"
                                 accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
                              <i class="fa fa-times bigger-110" id="delBtns" name="delBtns" style="cursor: pointer;"></i>
                              <button type="button" style="margin-left:30px; margin-bottom:2px;" class="btn btn-sm btn-white" name="excelUp" id="excelUp" >Upload</button>
                              <span style="margin-left:10px; font-size:12px;"><font class="red">* </font>다운받은 엑셀과 같은 양식으로 업로드</span>
                              <button id="hsCodeBtn">
                              	HS CODE <i class="fa fa-search"></i>
                              </button>
						</div>
						<div style="width:100%">
							<div style="width:50%; float:left;">
								<table id="main-table" class="table table-bordered table-striped" style="width:100%;">
									<thead>
										<tr>
											<th class="center">No</th>
											<th class="center">MAWB NO</th>
											<th class="center">DSTN.</th>
											<th class="center">운송장 개수</th>
											<th class="center">적용 개수</th>
											<th class="center">Excel Down</th>
											<th class="center">Date</th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${!empty mawbList}">
												<c:forEach items="${mawbList}" var="list" varStatus="status">
													<tr>
														<td class="center">
															${status.count}
														</td>
														<td class="center" style="font-weight:bold;">
															<%-- <a href="/mngr/rls/updateHistory?mawbNo=${list.mawbNo}">${list.mawbNo}</a> --%>
															<a style="cursor:pointer;" onclick="fn_showUpdateInfo('${list.mawbNo}')">${list.mawbNo}</a>
														</td>
														<td class="center">
															${list.dstnStation}
														</td>
														<td style="text-align:right;">
															${list.hawbNo}
														</td>
														<td style="text-align:right;">
															${list.hawbNoNew}
														</td>
														<td class="center">
															<a href="/mngr/rls/manifest/excelDown?mawbNo=${list.mawbNo}">[Excel]</a>
														</td>
														<td class="center">
															<fmt:formatDate value="${list.wDate}" pattern="yyyy-MM-dd HH:ss:mm"/>
															<c:if test="${list.hawbNoNew ne '0'}">
																&nbsp;<label class="blue" style="font-weight:bold; font-size:10px;">New</label>
															</c:if>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td colspan="8" class="center">
														조회 결과가 없습니다.
													</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								<br/>
								<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
							</div>
							<div style="width:49%; float:right;">
								<table id="history-table" class="table table-bordered table-striped">
									
								</table>
							</div>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!-- Main container End-->
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		<!--[if !IE]> -->
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
		<!-- script on paging end -->
		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#6thMenu").toggleClass('open');
					$("#6thMenu").toggleClass('active'); 
					$("#6thMani").toggleClass('active');
				})

				$("#submit_btn").click(function() {
					$("#searchForm").attr('method', 'get');
					$("#searchForm").submit();
				})

				$('#delBtns').on('click', function(e) {
		            $('#excelFile').val("");
	            });

				$("#excelUp").on('click', function() {
					if($("#excelFile").val() == "") {
						alert("파일이 없습니다");
						return false;
					}

					var datass = new FormData();
					datass.append("file", $("#excelFile")[0].files[0])

					$.ajax({
						url : '/mngr/rls/manifest/excelUp',
						type : 'POST',
						data : datass,
						processData : false,
						contentType : false,
						beforeSend : function(xhr)
		                  {   //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
		                      xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
		                  },
		               	success : function(data) {
			               	location.reload();
			            },
			            error : function(xhr, status) {
				            alert("업로드에 실패 하였습니다.");
				        }
					})
				})

				$("#hsCodeBtn").click(function() {
					var _width = '520';
				    var _height = '360';
				    var _left = (screen.width/2)-(_width/2);
				    var _top = (screen.height/2)-(_height/2);

				    window.open("/mngr/rls/searchItemCode", "HS_CODE", 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
				})
			})
			
			function fn_showUpdateInfo(mawbNo) {

				$.ajax({
					url : '/mngr/rls/updateHistory',
					type : 'POST',
					data : {mawbNo: mawbNo},
					beforeSend : function(xhr)
                 	{   //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
                      xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
                 	},
	                success : function(data) {
		                var list = [];
		                list = data.changeList;
		                console.log(list);
		                html = '';
		                html += '<thead><tr><th class="center">No.</th><th class="center">User ID</th>';
		                html += '<th class="center">Hawb No</th><th class="center">Agency Bl</th>';
		                html += '<th class="center"></th>';
		                html += '<tbody>';
		            	if (data.status == 'S') {
				            $.each(list, function(index, value) {
					            var cnt = index+1;
					            html += '<tr><td class="center">'+cnt+'</td><td class="center">'+value.userId+'</td>';
					            html += '<td class="center">'+value.hawbNo+'</td><td class="center">'+value.agencyBl+'</td>';
					            html += '<td class="center"><a href="/mngr/rls/updateHawbInfo?nno='+value.nno+'">[상세보기]</a></td>';
					        })
					        html += '</tbody>';
					        $("#history-table").empty();
					        $("#history-table").append(html);
			            } else {
							alert("조회 중 오류가 발생 하였습니다.")
				        }	
		            },
		            error : function(xhr, status) {
			            alert("조회 실패");
			        }
				})
			}
		</script>
		<!-- script addon end -->
		
	</body>
</html>
