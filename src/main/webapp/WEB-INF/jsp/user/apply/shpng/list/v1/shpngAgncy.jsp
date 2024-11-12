<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WMS</title>
<%@ include file="/WEB-INF/jsp/importFile/template/head.jsp" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/template/assets/css/contents.css">
<!-- css files for DataTables -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.11.3/css/dataTables.bootstrap.min.css"/>

<!-- javascript files for DataTables & Plugins -->
<script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.11.3/js/dataTables.bootstrap.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.2.9/js/dataTables.responsive.min.js"></script>
<style>
</style>
</head>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<body>
	<div class="container-fluid g-0">
		<header>
			<%@ include file="/WEB-INF/jsp/importFile/template/header.jsp" %>
		</header>
		<main>
		<%@ include file="/WEB-INF/jsp/importFile/template/sidebar3.jsp" %>
		
			<div class="display-area p-4" id="displayArea">
				<div class="main-contents p-4">
					<div class="main-section">
						<h4 class="main-title">배송대행 신청</h4>
						<table id="userTable" class="table table-striped table-bordered table-hover" >
						   <thead>
						       <tr>
						           <th>Email</th>
						           <th>Name</th>
						       </tr>
						   </thead>
						   <!-- tbody 태그? 데이터 테이블이 다 만들어준다 -->
						</table>
					</div>
				</div>
			</div>
			
		</main>
	</div>
	
	
<!-- BOOTSTRAP JS -->
<script src="/template/assets/js/head.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

<script type="text/javascript">
	var table = null;
	var loading = "";
	
	jQuery(function($) {
		loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="/image/Pulse.gif" />').appendTo(document.body).hide();
		
		$(document).ready(function() {
			table = $("#userTable").DataTable({
				processing: true,
				serverSide: true,
				ordering: false,
				ajax: {
					url : '/cstmr/apply/v1/shpngAgncyTable',
					type : 'POST',
					beforeSend : function(xhr) {
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					dataType : 'json',
				},
				columns: [
					{data : "email"},
					{data : "name"}
				]
			});
		});
	});

</script>
</body>
</html>