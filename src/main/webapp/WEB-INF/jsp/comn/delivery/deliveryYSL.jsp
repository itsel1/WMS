<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
.balloon_admin {
 position:relative;
 margin: 50px;
 width:400px;
 height:100px;
  background:rgb(255,219,202);
  border-radius: 10px !important;
}
.balloon_admin:after {
 border-top:15px solid rgb(255,219,202);
 border-left: 0px solid transparent;
 border-right: 15px solid transparent;
 border-bottom: 0px solid transparent;
 content:"";
 position:absolute;
 top:10px;
 right:-15px;
}

.balloon_user {
 position:relative;
 margin: 50px;
 width:400px;
 height:100px;
  background:rgb(187,225,201);
  border-radius: 10px !important;
}
.balloon_user:after {
 border-top:15px solid rgb(187,225,201);
 border-left: 15px solid transparent;
 border-right: 0px solid transparent;
 border-bottom: 0px solid transparent;
 content:"";
 position:absolute;
 top:10px;
 left:-15px;
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
	<body class="no-skin">
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="page-content">
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
							<form id="mgrMsgForm" name="mgrMsgForm">
							<input type="hidden" id="nno" name="nno" value="${parameters.nno}">
							<input type="hidden" id="groupIdx" name="groupIdx" value="${parameters.groupIdx}">
							<input type="hidden" id="subNo" name="subNo" value="${parameters.subNo}">
							<input type="hidden" id="whMemo" name="whMemo" value="">
							<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
								<table id="dynamic-table" class="table table-bordered ">
								 <thead>
									<tr>
										<th class="center " style='width:120px;'>Bl</th>
										<th class="left" style="background-color: white">${blinfo.Bl}</th>
									</tr>
									<tr>
										<th class="center " style='width:120px;'>OrderNo / RegNo</th>
										<th class="left" style="background-color: white">${blinfo.OrderNo}</th>
									</tr>
									<tr>
										<th class="center " style='width:120px;'>Consignee</th>
										<th class="left" style="background-color: white">${blinfo.Consignee}</th>
									</tr>
									<tr>
										<th class="center " style='width:120px;'>WarehousingDate</th>
										<th class="left" style="background-color: white">${blinfo.WarehousingDate}</th>
									</tr>
									<tr>
										<th class="center " style='width:120px;'>ShippingDate</th>
										<th class="left" style="background-color: white">${blinfo.ShippingDate}</th>
									</tr>
									<tr>
										<th class="center " style='width:120px;'>DeliveryCompany</th>
										<th class="left" style="background-color: white">${blinfo.DeliveryCompany}</th>
									</tr>
								</thead>
								</table>
								
								</br>
								
								<table id="dynamic-table" class="table table-bordered ">
								 <thead>
									<tr>
										<th class="center " style='width:120px;' colspan=4>Detail</th>
									</tr>
									<tr>
										<th class="center " style='width:120px;'>UpdateDateTime</th>
										<th class="center " style='width:120px;'>UpdateLocation</th>
										<th class="center " style='width:120px;'>UpdateDescription</th>
										<th class="center " style='width:120px;'>Comments</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach	items="${podDetatailArray}" var="podDetatail" varStatus="status">
										<%-- <c:forEach	items="${podDetatail}" var="podDetatailRow" varStatus="status"> --%> 
											<tr>
												<td class="ceter" >${podDetatail.UpdateDateTime}</td>
												<td class="ceter" >${podDetatail.UpdateLocation}</td>
												<td class="ceter" >${podDetatail.UpdateDescription}</td>
												<td class="ceter" >${podDetatail.Comments}</td>
												
											
											</tr>
										<%-- </c:forEach> --%>
									</c:forEach>
								</tbody>
								</table>
								
							</form>
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
		
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#6thMenu").toggleClass('open');
					$("#6thMenu").toggleClass('active'); 
					$("#6thThr").toggleClass('active');
					$("#message").focus();
				});

				$("#sendBtn").on('click',function(e){
					$("#whMemo").val($("#message").val());
					var formData = $("#mgrMsgForm").serialize();
					$.ajax({
						url:'/cstmr/stock/mgrMsg',
						type: 'POST',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data: formData,
						success : function(data) {
							location.reload();
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("등록에 실패 하였습니다. 회원 정보를 확인해 주세요.");
			            }
					})
				})
				
			})
		</script>
		<!-- script addon end -->
		
	</body>
</html>
