<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	  #searchBtn {
	  	border-radius: 0.5rem;
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
								회원 관리
							</li>
							<li class="active">FB 판매사 목록</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								FB 판매사 목록
							</h1>
						</div>
						<br/>
						<div id="inner-content-side" >
							<form class="form-inline">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">USER ID</div>
										<input type="text" name="userId" id="userId" class="form-control" value="${params.userId}">
									</div>
									<button type="submit" class="btn btn-warning" id="searchBtn"><i class="fa fa-search"></i></button>
								</div>
							</form>
							<br/>
							<div id="search-div">
								<div id="rgstr-user" style="text-align: left">
									<input type="button" class="btn btn-xs btn-primary" id="rgstBtn" value="판매사 생성">
									<input type="button" class="btn btn-xs btn-success text-right" id="sendFb" value="판매사 등록"> 
								</div>
							</div>
							<div id="table-contents" class="table-contents" style="width:70%;margin-top:10px;">
								<table id="dynamic-table" class="table table-bordered table-hover" style="width:100%;">
									<thead>
										<tr>
											<th class="center" style="width:5%;">
												<label class="pos-rel">
														<input type="checkbox" class="ace" /> <span class="lbl"></span>
												</label>
											</th>
											<th class="center colorBlack" style="width:10%;">WMS 아이디</th>
											<th class="center colorBlack" style="width:10%;">FB 판매사명</th>
											<th class="center colorBlack" style="width:19%;">발송인명</th>
											<th class="center colorBlack" style="width:10%;">사업자 등록번호</th>
											<th class="center colorBlack" style="width:7%;">FB<br>수출신고 여부</th>
											<th class="center colorBlack" style="width:7%;">FB<br>사용 여부</th>
											<th class="center colorBlack" style="width:7%;">판매사<br>등록 여부</th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${!empty userList}">
												<c:forEach items="${userList}" var="userList">
													<tr>
														<td class="center">
															<label class="pos-rel">
																	<input type="checkbox" class="ace" value="${userList.userId}"/> <span class="lbl"></span>
															</label>	
														</td>
														<td class="center">
															<a href="/mngr/acnt/registFbUserModify?idx=${userList.idx}&userId=${userList.userId}">
																${userList.userId}
															</a>
														</td>
														<td class="center">${userList.sellerName}</td>
														<td class="center">${userList.shipperName}</td>
														<td class="center">${userList.comRegNo}</td>
														<td class="center">
															<c:if test="${userList.expUseYn eq 'P'}">
																Y
															</c:if>
															<c:if test="${userList.expUseYn ne 'P'}">
																N
															</c:if>
														</td>
														<td class="center">${userList.fbUseYn}</td>
														<td class="center">${userList.fbSendYn}</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td class="center" colspan="8">조회 결과가 없습니다</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								<br/>
								<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
							</div>
							<br />
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
					$("#14thTwo").toggleClass('active');
				})

				$("#rgstBtn").click(function() {
					location.href = "/mngr/acnt/registFbUserInfo";
				})
				
				$("#sendFb").click(function() {
					var targets = new Array();

					$("#dynamic-table").find('tbody > tr > td input[type=checkbox]').each(function() {
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if(td_checked) {
							targets.push($(this).val());
						}
					})
					
					if (targets == "") {
						alert("등록할 대상이 없습니다.");
					} else {
						LoadingWithMask();
						
						$.ajax({
							url : '/mngr/acnt/fastboxUserSend',
							type : 'POST',
							data : {userList: targets},
							beforeSend : function(xhr)
							{
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							success : function(result) {
								var fail = result.filter(isFail);
								var innerText = "";
								$.each(fail, function(index, val) {
									innerText += "판매사명 : " + val.userId + " | 사유 : " + val.msg + "\n";
								})
								if (fail.length > 0) {
									alert("판매사 "+result.length+"건 중 " +fail.length+"건이 등록 실패 하였습니다.\n\n"+innerText+"\n\n관리자에게 문의해 주세요");
								} else {
									alert("판매사 "+result.length+"건 등록에 성공 하였습니다.");
								}
								location.reload();
							},
							error : function(xhr, status) {
								alert("판매사 등록에 실패 하였습니다. 다시 시도해주세요.");
							}
						})
					}
				})

				function isFail(obj) {
					if(obj.status == 'F') {
						return true;
					}
				};
				
				var myTable = 
					$('#dynamic-table')
					.DataTable( {
						"dom" : "lt",
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "autoWidth": false,
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

				function LoadingWithMask() {
	               var maskHeight = $(document).height();
	               var maskWidth = window.document.body.clientWidth;

	               var mask ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
	                 
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