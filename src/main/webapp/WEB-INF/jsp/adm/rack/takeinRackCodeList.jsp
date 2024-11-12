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
	  border: 1px solid #dcdcdc;
	  burder-radius: 2px;
	  }
	  
	  #searchBtn:hover {
	  	background-color: #d8d8d8;
		color: white;
	  }
	  
	  .mp07{
  			margin:0px;
  			padding:7px;
  		} 
	 </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	</head> 
	<title>사입 Rack</title>
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
								사입 Rack
							</li>
							<li class="active">랙 관리</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<form name="mainForm" id="mainForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<div class="page-content" style="margin-left: 10px;">
							<div class="page-header">
								<h1>
									랙 관리
								</h1>
							</div>
							<div class="row">
								<div class="col-xs-12 col-sm-11 col-md-10">
									<span class="input-group" style="display: inline-flex; width: 100%">
										<select name="searchType" id="searchType" style="height: 34px;">
											<option <c:if test="${searchType eq '0' }"> selected </c:if>value="0">ALL</option>
											<option <c:if test="${searchType eq '1' }"> selected </c:if>value="1">Rack Code</option>
											<option <c:if test="${searchType eq '2' }"> selected </c:if>value="2">Rack Name</option>
										</select>
										<input type="text" class="form-control" name="keywords" value="${searchKeyword}" style="width:100%; max-width:300px;" />
										<button type="submit" class="btn btn-default no-border btn-sm">
											<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
										</button>
									</span>
								</div>
							</div>
							<br/><br/>
							<div id="inner-content-side" >
								<div id="rgstr-user" style="text-align:left">
									<button type="submit" class="btn btn-sm btn-primary" name="rgstBtn" id="rgstBtn">
									 	Rack 등록
									</button>
									<button type="submit" class="btn btn-sm btn-danger" name="delBtn" id="delBtn" >
										Rack 삭제
									</button>
									<input type="hidden" name="targetParm" id="targetParm" value=""/>
								</div>
								<br>
								<div id="table-contents" class="table-contents" style="width: 49%; float: left;">
									<table id="dynamic-table" class="table table-bordered table-hover" style="width:100%;">
										<thead>
											<tr>
												<th class="center">
													<label class="pos-rel">
															<input type="checkbox" class="ace" /> <span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack">Rack</th>
												<th class="center colorBlack">Station</th>
												<th class="center colorBlack">비고</th>
												<th class="center colorBlack">재고 수량</th>
												<th class="center colorBlack" style="width:100px;">Order By</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${rackList}" var="rackList" varStatus="status">
												<tr>
													<td id="checkboxTd" class="center" style="word-break:break-all;">
														<label class="pos-rel">
															<input type="checkbox" class="ace" value="${rackList.rackCode}"/>
															<span class="lbl"></span>
														</label>
													</td>
													<td class="center" style="word-break:break-all;">
														<a style="font-weight:bold; cursor:pointer;" onclick="fn_rackInfo('${rackList.orgStation}','${rackList.rackCode}')">${rackList.rackName}</a>
													</td>
													<td class="center" style="word-break:break-all;">
														${rackList.orgStation}
													</td>
													<td class="center" style="work-break:break-all;">
														${rackList.rackRemark}
													</td>
													<td style="work-break:break-all; text-align: right; font-weight: bold;">
														${rackList.cnt}
													</td>
													<td style="text-align:right;">
														${rackList.orderBy}
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<br/>
									<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
								</div>
								<div class="rackInfo" style="width: 38%; float: left; margin-left:90px; border: 1px solid #dee2e6!important; padding: 3rem!important">
									<div class="row hr-8">
										<div id="text">
											<h4>정보 수정</h4>
										</div>
									</div>
									<br/>
									<div class="row hr-8">
										<div class="col-xs-2 center mp07 colorBlack" style="font-weight: bold;">
											Rack Code
										</div>
										<div class="col-xs-5 center">
											<input type="text" id="rackCode" name="rackCode" readonly class="onlyEN" style="width: 400px;"/>
										</div>
									</div>
									<div class="hr dotted"></div>
									<div class="row hr-8">
										<div class="col-xs-2 center mp07 colorBlack" style="font-weight: bold;">
											Rack Name
										</div>
										<div class="col-xs-5 center">
											<input type="text" id="rackName" name="rackName" style="width: 400px;"/>
										</div>
									</div>
									<div class="hr dotted"></div>
									<div class="row hr-8">
										<div class="col-xs-2 center mp07 colorBlack" style="font-weight: bold;">
											Remark
										</div>
										<div class="col-xs-5 center">
											<input type="text" id="rackRemark" name="rackRemark" style="width: 400px;"/>
										</div>
									</div>
									<div class="hr dotted"></div>
									<div class="row hr-8">
										<div class="col-xs-2 center mp07 colorBlack" style="font-weight: bold;">
											정렬 순서
										</div>
										<div class="col-xs-1 center">
											<input type="number" id="orderBy" name="orderBy"/>
										</div>
									</div>
									<div class="hr dotted"></div>
									<div class="row hr-8">
										<div class="col-xs-2 center mp07 colorBlack" style="font-weight: bold;">
											사용 여부
										</div>
										<div class="col-xs-5" style="padding-top:7px;">
											<label>
												<input type="radio" name="useYn" class="ace" value="Y" />
												<span class="lbl">사용</span>
											</label>
											<label style="margin-left:10px;">
												<input type="radio" name="useYn" class="ace" value="N" />
												<span class="lbl">미사용</span>
											</label>
										</div>
									</div>
									<div class="row hr-8" style="text-align: right; padding-right: 10px;">
										<input type="button" id="btnUpdate" class="btn btn-white btn-sm" value="수정"/>
									</div>
								</div>
							</div>
						</div>
					</form>
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
					$("#15thMenu").toggleClass('open');
					$("#15thMenu").toggleClass('active'); 
					$("#15thOne").toggleClass('active');

					$(".rackInfo").hide();
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

				    

					$('#mainForm').attr('action', '/mngr/takein/rack/rackCodeListDel');
				   	$('#targetParm').val(str);
				   	
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
			});
			
			$('#rgstBtn').on('click', function(e) {
				window.open("/mngr/takein/rack/popupRegistRack", "PopupWin", "width=700,height=420");
			})
			
			/* rack update popup */
			function fn_rackInfo(orgStation, rackCode) {
				
				$.ajax({
					url : '/mngr/takein/rack/selectRackInfo',
					type : 'GET',
					data : { orgStation: orgStation, rackCode: rackCode },
					success : function(data) {
						console.log(data.rackInfo.useYn);
						$("#rackName").val(data.rackInfo.rackName);
						$("#rackCode").val(data.rackInfo.rackCode);
						$("#rackRemark").val(data.rackInfo.rackRemark);
						$("#orderBy").val(data.rackInfo.orderBy);

						
						if (data.rackInfo.useYn == "Y") {
							$("input:radio[name='useYn']:input[value='Y']").prop('checked', true);
						} 
						
						if (data.rackInfo.useYn == "N") {
							$("input:radio[name='useYn']:input[value='N']").prop('checked', true);
						} 

						var html = '';
						html += '<h4>'+data.rackInfo.rackCode+' 정보</h4>';

						$("#text").empty();
						$("#text").html(html);
						$(".rackInfo").show();
						
					},
					error : function(request,status,error) {
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						alert("데이터 조회 실패");
					}
				})
			}


			$("#btnUpdate").on('click', function() {
				if ($("#rackName").val() == "") {
					alert("Rack Name을 입력해주세요");
					$("#rackName").focus();
					return false;
				}

				var formData = $("#mainForm").serialize();
				console.log(formData);
				$.ajax({
					url : '/mngr/takein/rack/updateRackInfo',
					type : 'PATCH',
					beforeSend: function(xhr) {
						xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data: formData,
					success: function(data) {
						if (data == "S") {
							alert("수정 되었습니다.");
							location.reload();
						} else {
							alert("수정 중 오류가 발생하였습니다.\n관리자에게 문의해 주세요.");
						}
					},
					error: function(xhr, status) {
						alert(xhr + " : " + status);
						alert("수정에 실패 하였습니다.");
					}
				})
			})
	
		</script>
		<!-- script addon end -->
</body>
</html> 