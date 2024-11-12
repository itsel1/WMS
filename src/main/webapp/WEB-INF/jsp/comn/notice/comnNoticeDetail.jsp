<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<html lang="en">
	<head>
	<style type="text/css">
	table.noticeTable > thead > tr 
	{
	   border-bottom: 1px solid #e6e6e6;
	   border-width : 2px;
	}
	
	table.noticeTable > thead > tr > th
	{
		padding: 20px 10px;
    	text-align: center;
	}
	table.noticeTable > tbody > tr > td{
		padding: 20px 10px !important;
    	text-align: center;
	}
	table.noticeTable > tbody > tr{
		border-bottom: 1px solid #e6e6e6;
	}
	 </style>
	
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor-viewer.min.css" />
	</head> 
	<title>공지사항</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
			<div class="toppn">
				<div class="container">
					<div class="row">
						<div class="col-md-12 mb-0" style="font-size:14px !important;"><a>고객센터</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">공지 사항</strong></div>
					</div>
				</div>
			</div>  
			
			<!-- Main container Start-->
			<div class="container">
				<div class="page-content">
					<div class="hr hr-8 solid"></div>
					<div class="page-header">
						<h2>
							${noticeDetail.title }
						</h2>
						${noticeDetail.date }
					</div>
					<div id="inner-content-side" >
						<div id="table-contents" class="table-contents" style="width:100%; overflow: auto; font-size:medium;">
							<div id="viewer"></div>
							<input type="hidden" id="viewContents" value='${noticeDetail.contents }'/>
						
						</div>
					</div>
					<div class="hr hr-8 solid"></div>
					<div id="btnDiv" class="pull-right">
					
					<c:if test="${noticeDetail.nextYn > 0 }">
						<button class="btn btn-white btn-md btn-default" onclick="javascript:fnTrClick('${noticeDetail.nextYn}')">이전</button>
					</c:if>
					<c:if test="${noticeDetail.prevYn  > 0 }">
						<button class="btn btn-white btn-md btn-default" onclick="javascript:fnTrClick('${noticeDetail.prevYn}')">다음</button>
					</c:if>
						<button class="btn btn-white btn-md btn-info" id="backList">목록</button>
					</div>
				</div>
			</div>
		</div>
			
		<!-- Main container End-->
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		
		<script src="/testView/js/jquery-3.3.1.min.js"></script>
		<script src="/testView/js/jquery-ui.js"></script>
		<script src="/testView/js/popper.min.js"></script>
		<script src="/testView/js/bootstrap.min.js"></script>
		<script src="/testView/js/owl.carousel.min.js"></script>
		<script src="/testView/js/jquery.magnific-popup.min.js"></script>
		<script src="/testView/js/aos.js"></script>
		<script src="https://uicdn.toast.com/editor/latest/toastui-editor-viewer.js"></script>
		<script src="/testView/js/main.js"></script>
		<script type="text/javascript">
		function fnTrClick(idx){
			location.href="/cstmr/srvc/ntcDetail?ntcNo="+idx;
		}
		$('#backList').on('click',function(e){
			location.href="/cstmr/srvc/ntc";
		})
		
		const viewer = new toastui.Editor({
			el: document.querySelector('#viewer'),
			initialValue : $("#viewContents").val()
		})
			
		
		</script>
	</body>
</html>
