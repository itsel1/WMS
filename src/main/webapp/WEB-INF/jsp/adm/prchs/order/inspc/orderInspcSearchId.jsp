<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
		.searchInput{
			border:none !important; 
			border-bottom: 1px solid gray !important; 
			width:65% !important; 
			text-align: center !important;
		}
		
		table ,tr td{
			vertical-align: middle !important;
		}

    </style>
	</head> 
	<title>ID 검색</title>
	<body class="no-skin" style="background-color: white;">
		<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
		<input type="hidden" name="tempResult" id="tempResult" value=${tempResult }>
		<div id="detail-contents" class="detail-contents row center middle">
			<div id="home" class="tab-pane in active col-xs-offset-1 col-xs-10">
				<form id="orderInspForm" name="orderInspForm" enctype="multipart/form-data">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<table id="dynamic-table" class="table table-bordered table-dark">
						<tr>
							<td >
								ID
							</td>
							<td >
								<input type="text" class="searchInput" name="idArea" id="idArea" value="${searchIdVal}" >
								<button type="submit" class="btn btn-sm btn-white btn-primary" name="searchBtn" id="searchBtn" >검색</button>
							</td>
						</tr>
					</table>
					<table id="dynamic-table" class="table table-bordered table-dark">
						<tr>
							<td colspan="2">
								<strong>검색 결과</strong>
							</td>
						</tr>
						<tr>
							<td style="width:50%;">
								ID
							</td>
							<td>
								-
							</td>
						</tr>
						<c:forEach items= "${idList}" var="idsInfo">
							<tr>
								<td>
									${idsInfo.userId}
								</td>
								<td>
									<button type="button" class="btn btn-sm btn-success" onclick="javascript:fn_useId('${idsInfo.userId}')">사용하기</button>
								</td>
							</tr>
						</c:forEach>
					</table>
					
				</form>
			</div><!-- /#home -->
		</div>
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
		<!-- script addon start -->
		<script type="text/javascript">
		function fn_useId(id){
			if(confirm("해당 ID를 사용하시겠습니까?")){
				window.opener.document.getElementById("userId").value=id;
				window.close();
			}
		}

		jQuery(function($) {
			$(document).ready(function() {
				if($("#tempResult").val() == "T"){
					if(confirm("ID가 있습니다. 사용하시겠습니까?")){
						window.opener.document.getElementById("userId").value=$("#idArea").val();
						window.close();
					}
				}
					
			});
		})
    </script>
		
	</body>
</html>
