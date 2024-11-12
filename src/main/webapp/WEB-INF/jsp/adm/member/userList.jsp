<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link rel="shortcut icon" type="image/x-icon" href="/image/favicon/logo_icon.ico" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	  
	  #useYn {
		position: relative;
		top: 1px;
		margin-left: 3px;
		}
	  
	  #useYnLabel {
		margin-left: 5px;
		position: relative;
		top: -1px;
		font-size: 12px;
		}
	 </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	
	</head> 
	<title>회원 관리</title>
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
								관리자
							</li>
							<li class="active">거래처 목록</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								거래처
							</h1>
						</div>
						<div id="inner-content-side" >
						
							<div id="search-div">
								<br>
								<form name="search-button" id="search-botton" method="get" action="/mngr/acnt/userList">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="row">
										<div class="col-xs-12 col-sm-11 col-md-10">
											<span class="input-group" style="display: inline-flex; width:100%">
												<select style="height: 34px">
													<option>USER ID</option>
												</select>
												<input type="text" class="form-control" name="keywords" value="${searchKeyword}" style="width:100%; max-width: 300px"/>
													<button type="submit" class="btn btn-default no-border btn-sm">
														<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
													</button>
											</span>
										</div>
									</div>
								</form>
								<br>
								<form name="rgdelbutton" id="rgdelbutton" method="get" action="/mngr/acnt/regist">
									<input type="hidden" name="chkYn" id="chkYn">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div style="width:100%;">
										<div style="text-align:left;float:left;width:50%;">
											<label for="useYn" id="useYnLabel">미 승인건</label>										
											<input type="checkbox" name="useYn" id="useYn" <c:if test="${infoMap.chk eq 'Y'}">checked</c:if>>
										</div>
										<div id="rgstr-user" style="text-align: right;float:right;width:49%;">
											<button type="submit" class="btn btn-sm btn-primary" name="rgstBtn" id="rgstBtn">
											 	등록하기
											</button>
											<button type="submit" class="btn btn-sm btn-danger" name="delBtn" id="delBtn">
												삭제하기
											</button>
											<input type="hidden" name="targetParm" id="targetParm" value=""/>
										</div>
									</div>
								</form>
							</div>
							<br>
							<div id="table-contents" class="table-contents" >
								<table id="dynamic-table" class="table table-bordered table-hover" style="width:100%;">
									<thead>
										<tr>
											<th class="center">
												<label class="pos-rel">
														<input type="checkbox" class="ace" /> <span class="lbl"></span>
												</label>
											</th>
											<!-- <th class="center colorblack">No</th> -->
											<th class="center colorBlack">아이디</th>
											<th class="center colorBlack">사용자 명</th>
											<th class="center colorBlack">전화번호</th>
											<th class="center colorBlack">휴대전화 번호</th>
											<th class="center colorBlack">주소</th>
											<th class="center colorBlack">Email</th>
											<th class="center colorBlack">구분</th>
											<th class="center colorBlack">Zone</th>
											<th class="center colorBlack">승인 여부</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${userInfo}" var="test" varStatus="status">
											<tr>
												<td id="checkboxTd" class="center" style="word-break:break-all;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" value="${test.userId}"/>
														<span class="lbl"></span>
													</label>
												</td>
												<%-- <td class="center">
													<a href="/mngr/redirectToUserPage?userId=${test.userId}">${status.count}</a>
												</td> --%>
												<td class="center" style="word-break:break-all;">
													<%-- <a href="/mngr/acnt/modify/${test.userId}">${test.userId}</a>
													기존 : <a href="/mngr/acnt/modify/${test.userId}">${test.userId}</a> --%>
													<a href="/mngr/acnt/modifys/${test.userId}">${test.userId}</a>
												</td>
												<td class="center" style="word-break:break-all;">
													${test.userName}
												</td>
												<td class="center" style="word-break:break-all;">
													${test.userTel}
												</td>
												<td class="center" style="word-break:break-all;">
													${test.userHp}
												</td>
												<td class="center" style="word-break:break-all;">
													${test.userAddr}
													${test.userAddrDetail}
												</td>
												<td class="center" style="word-break:break-all;">
													${test.userEmail}
												</td>
												<td class="center" style="word-break:break-all;">
													<c:if test="${test.role eq 'USER' && test.etprYn eq 'Y'}">
														기업
													</c:if>
													<c:if test="${test.role eq 'USER' && test.etprYn eq 'N'}">
														개인
													</c:if>
													<c:if test="${test.role eq 'RETURN'}">
														반품
													</c:if>
												</td>
												<td class="center" style="word-break:break-all;">
													<a href="/mngr/acnt/zone/${test.userId}">설정</a>
												</td>
												<td class="center" style="word-break:break-all;">
													${test.aprvYn}
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<br/>
							<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
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
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/assets/js/bootstrap.min.js"></script>
		
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
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#zeroMenu").toggleClass('open');
					$("#zeroMenu").toggleClass('active');
					$("#zeroOne0").toggleClass('active');
				});

				$("#useYn").on('click', function(e) {
				    if ($(this).prop('checked')) {
					    $("#chkYn").val("Y");
					} else {
						$("#chkYn").val("N");
					}

				    $('#rgdelbutton').attr('action', '/mngr/acnt/userList');
					$("#rgdelbutton").submit();
				});
				

				$('#delBtn').on('click',function(e){
					var str = "";
					$("td input:checkbox:checked").each(function (index) {  
				        str += $(this).val() + ",";  
				    });
				    if(str == "")  {
					    alert("삭제할 대상이 없습니다.");
					    return false;
				    }
					
					$('#rgdelbutton').attr('action', '/mngr/acnt/delete');
				    $('#targetParm').val(str)
				});

				var myTable = 
					$('#dynamic-table')
					.DataTable( {
						"dom" : "t",
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "autoWidth": false,
						select: {
							style: 'multi'
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

		</script>
		<!-- script addon end -->
</body>
</html>