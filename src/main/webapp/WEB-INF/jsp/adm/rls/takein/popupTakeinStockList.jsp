<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	
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
	<body class="no-skin">
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								출고 재고 리스트
							</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="col-xs-12 col-sm-12">
							<form id="dataForm">
								<label for="mnDate">유통기한</label>
								<input type="text" name="mnDate" id="mnDate" value="${takeInStocList[0].mnDate}" placeholder="YYYYMMDD"/>
								<input type="hidden" name="userId" value="${params.userId}"/>
								<input type="hidden" name="orgStation" value="${params.orgStation}"/>
								<input type="hidden" name="groupIdx" value="${params.groupIdx}"/>
								<input type="button" id="updateBtn" value="변경"/>
							</form>
						</div>
						<br/>
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
						  <table class="table table-bordered">
						  	 <thead>
						  	 	<tr>
						  	 		<th>UserId</th>
						  	 		<th>검수일</th>						  	 		
						  	 		<th>Rack</th>
						  	 		<th>고객상품코드</th>
						  	 		<th>재고번호</th>
						  	 		<th>상품명</th>
						  	 		<th>출고일</th>
						  	 		<th>운송장번호</th>
						  	 		<th>비고</th>
						  	 	</tr>
						  	 </thead>
						  	 <tbody>
						  	 <c:forEach items="${takeInStocList}" var="takeInStocList" varStatus="status">
						  	 	<tr>
						  	 		<td>${takeInStocList.userId}</td>						  	 		
						  	 		<td style="text-align:center">${takeInStocList.whInDate}</td>
						  	 		<td style="font-weight:bold">${takeInStocList.rackCode}</td>
						  	 		<td style="text-align:center">${takeInStocList.cusItemCode}</td>
						  	 		<td style="text-align:center">${takeInStocList.stockNo}</td>
						  	 		<td style="text-align:left">${takeInStocList.itemDetail}</td>
						  	 		<td style="text-align:center">${takeInStocList.outDate}</td>
						  	 		<td style="text-align:center">${takeInStocList.outHawb}</td>
						  	 		<td style="text-align:center">${takeInStocList.remark}</td>
						  	 	</tr>
						  	 	</c:forEach>
						  	 </tbody>
						  </table>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
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
		$('#mnDate').keyup(function(e){
			$(this).val($(this).val().replace(/[^0-9]/gi, ""));
		});
		
		$("#updateBtn").on('click', function(e) {
			var mnDate = $('#mnDate').val();
			
			if (mnDate.length != 8) {
				alert("제조일자는 YYYYMMDD 형식으로 입력해주세요.");
				return false;
			}
			
			var formData = $("#dataForm").serialize();

			$.ajax({
				url : '/mngr/takein/updateManuDate',
				type : 'POST',
				data : formData,
				beforeSend : function(xhr) { //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
					xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(response) {
					var status = response.status;
					
					if (status == "success") {
						alert("변경 되었습니다");
						location.reload();
					} else {
						alert("변경 중 오류가 발생 하였습니다.\n"+response.msg);
					}
				},
				error : function(error) {
					alert("시스템 오류가 발생 하였습니다.");
				}
			});
			
		});
		
		</script>

	</body>
</html>
