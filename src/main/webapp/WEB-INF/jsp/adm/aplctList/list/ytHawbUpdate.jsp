<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
   <head>
   <%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
   <style type="text/css">
	.popupBtn {
		border: none;
		background: none;
		outline: none;
		color: Teal;
		font-weight: bold;
	}
	
	.popupBtn:hover {
		text-decoration: underline;
	}
    </style>
   <!-- basic scripts -->
   <!-- ace scripts -->
   
   </head> 
   <title>YT Hawb 업데이트</title>
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
                        입고 작업
                     </li>
                     <li class="active">YT Hawb 업데이트</li>
                  </ul><!-- /.breadcrumb -->
               </div>
               <div class="page-content">
					<div class="page-header">
						<h1>
							YT Hawb 업데이트
						</h1>
					</div>
					<div id="inner-content-side" >
						<form name="registForm" id="registForm">
	                    	<div id="search-div">
	                    		<table id="search-table" class="table table-bordered table-hover containerTable" style="width:50%;">
	                    			<thead>
		                    			<tr>
		                    				<th class="center">HAWB NO</th>
		                    				<td style="padding:0px;">
		                    					<input type="text" style="width:100%;height:42px;" name="hawbNo" id="hawbNo" value="${parameters.hawbNo}"/>
		                    				</td>
		                    				<th class="center">주문번호</th>
		                    				<td style="padding:0px;">
		                    					<input type="text" style="width:100%;height:42px;" name="orderNo" id="orderNo" value="${parameters.orderNo}"/>
		                    				</td>
		                    				<th class="center">User ID</th>
		                    				<td style="padding:0px;">
		                    					<input type="text" style="width:100%;height:42px;" name="userId" id="userId" value="${parameters.userId}"/>
		                    				</td>
		                    				<td class="center">
		                    					<input type="button" id="searchBtn" value="Search"/>
		                    				</td>
		                    			</tr>
		                    		</thead>
	                    		</table>
							</div>
							<br/>
							<input type="button" id="excel-down" value="주문서 다운로드">
							<br/><br/>
							<div id="table-contents">
								<table id="dynamic-table" class="table table-bordered table-hover containerTable" style="width:100%;">
									<thead>
										<tr style="border-bottom: 2px solid #ccc;">
											<th class="center" style="width:5%;">
												<label class="pos-rel"> 
													<input type="checkbox" class="ace" id="checkAll"/> 
													<span class="lbl"></span>
												</label>
											</th>
											<th class="center" style="width:15%;">Nno</th>
											<th class="center" style="width:10%;">도착국가</th>
											<th class="center" style="width:10%;">User ID</th>
											<th class="center" style="width:10%;">Company</th>
											<th class="center" style="width:10%;">Order No</th>
											<th class="center" style="width:10%;">Hawb No</th>
											<th class="center" style="width:10%;">수취인명</th>
											<th class="center" style="width:20%;">주소</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${orderList}" var="dataList" varStatus="status">
											<tr>
												<td class="center">
													<label class="pos-rel"> 
														<input type="checkbox" class="ace" value="${dataList.nno}" name="chk"/> 
														<span class="lbl"></span>
													</label>
													<input type="hidden" name="itemCnt" value="${dataList.itemCnt}">
												</td>
												<td class="center">
													<input type="button" value="${dataList.nno}" class="popupBtn" onclick="fn_hawbUpPopup('${dataList.nno}')">
												</td>
												<td class="center">${dataList.dstnNation}</td>
												<td class="center">${dataList.userId}</td>
												<td class="center">${dataList.companyName}</td>
												<td class="center">${dataList.orderNo}</td>
												<td class="center">${dataList.hawbNo}</td>
												<td class="center">${dataList.cneeName}</td>
												<td class="center">${dataList.cneeAddr}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</form>
						<form id="excelForm">
	                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input type="hidden" name="nnoList" id="nnoList"/>
							<input type="hidden" name="cntList" id="cntList"/>
						</form>
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
				$("#4thMenu").toggleClass('open');
           		$("#4thMenu").toggleClass('active'); 
           		$("#4thFifth").toggleClass('active');   

			});

			$("#searchBtn").click(function() {
				$("#registForm").attr("action", "/mngr/order/ytList");
				$("#registForm").attr("method", "GET");
				$("#registForm").submit();
				$("#registForm").attr("action", "");
				$("#registForm").attr("method", "");
			});

			$("#checkAll").click(function() {
				if ($("#checkAll").is(":checked")) {
					$("input[name=chk]").prop("checked", true);
				} else {
					$("input[name=chk]").prop("checked", false);
				}
			});

			$("input[name=chk]").click(function() {
				var total = $("input[name=chk]").length;
				var checked = $("input[name=chk]:checked").length;

				if (total != checked) {
					$("#checkAll").prop("checked", false);
				} else {
					$("#checkAll").prop("checked", true);
				}
			});

			$("#excel-down").click(function() {

				if ($("input[name=chk]:checked").length == 0) {
					alert("최소 1개 이상의 주문을 선택해 주세요.");
					return;
				}
				
				var nnoList = new Array();
				var cntList = new Array();

				$("input[name='chk']").each(function(e) {
					var row = $(this).closest('tr').get(0);
					var checked = this.checked;

					if (checked) {
						nnoList.push($(this).val());
						cntList.push($(row).find("input[name='itemCnt']").val());
					}
				});

				$("#nnoList").val(nnoList);
				$("#cntList").val(cntList);

				$("#excelForm").attr("action", "/mngr/order/ytListExcelDown");
				$("#excelForm").attr("method", "post");
				$("#excelForm").submit();
			});
			
		});

		function fn_hawbUpPopup(nno) {
			var width = '500';
		    var height = '650';
		    var left = (screen.width/2)-(width/2);
		    var top = (screen.height/2)-(height/2);
		    window.open('/mngr/order/ytHawbUpdate?nno='+nno, "hawbUpPopup", 'width='+ width +', height='+ height +', left=' + left + ', top='+ top);
		}

      </script>
      <!-- script addon end -->

   </body>
</html>