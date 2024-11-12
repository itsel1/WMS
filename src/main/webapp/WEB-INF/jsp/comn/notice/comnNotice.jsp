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
						<div class="page-header">
							<h3>
								공지 사항
							</h3>
						</div>
						<div id="inner-content-side" >
							<div id="search-div">
								<form name="searchForm" id="searchForm" >
									<br>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="form-group">
										<div class="col-xs-12 col-md-3 form-group site-block-top-search pull-right">
											<span class="icon icon-search2" style="font-size: 20px"></span>
											<input id="searchWord" name="searchWord" type="text" class="form-control border-0" placeholder="Search" style="border: none;border-bottom: 1px solid black; margin-left:15px">
										</div>
									</div>
								</form>
							</div>
							<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;">
								<div class="hr hr-8 solid"></div>
								<table id="dynamic-table" class="noticeTable" style="width:100%;">
									<colgroup>
										<col width="10%"/>
										<col width="70%"/>
										<col width="20%"/>
									</colgroup>
									<thead>
										<tr>
											<th class="center">
												번호
											</th>
											<th >
												제목
											</th>
											<th class="center">
												작성일
											</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${fn:length(noticeInfo) eq 0}">
											<tr>	
												<td colspan='3'>
													조회결과가 없습니다.
												</td>
											</tr>
										</c:if>
										<c:forEach items="${noticeInfo}" var="noticeVO">
											<tr style="cursor: pointer;" onclick="javascript:fnTrClick('${noticeVO.idx}')">
												<td class="center">
													${noticeVO.rownum}
												</td>
												<td >
													${noticeVO.title}
												</td>
												<td class="center">
													${noticeVO.date}
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
							
							</div>
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
		
		<script src="/testView/js/main.js"></script>
		<script type="text/javascript">
		function fnTrClick(idx){
			location.href="/cstmr/srvc/ntcDetail?ntcNo="+idx;
		}
		
		</script>
	</body>
</html>
