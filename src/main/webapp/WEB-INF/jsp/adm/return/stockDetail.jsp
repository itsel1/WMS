<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
   <head>
   <link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/chosen.min.css" />
   <%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
   <style type="text/css">
input[type=text] {
		border: 1px solid #e6e6e6;
		height: 30px;
	}
	
	input[type=text]:hover, input[type=text]:focus {
		border: 1px solid #e6e6e6;
	}
	
	#tab-content {
		width: 70%;
		height: auto;
		border: 1px solid #527d96;
		margin: auto;
		margin-top: 30px;
		padding: 20px;
	}

	#return-tbl {
		margin-top: 10px;
	}
	
	#return-tbl th {
		width: 15%;
		padding-left: 15px;
		height: 35px;
	}
	
	#return-tbl td {
		width: 35%;
		height: 35px;
		padding-left: 5px;
	}
	
	#return-tbl input[type=text] {
		width: 100%;
		height: 35px;
		border: none;
	} 

	.headers {
		background-color: rgba(204, 204, 204, 0.19);
	}
	
	.t-headers {
		background-color: rgba(76, 144, 189, 0.72);
		color: #fff;
	}
	
	#return-tbl2 {
		width: 100%;
	}
	
	#return-tbl2 th {
		width: 15%;
		height: 35px;
		background-color: rgba(204, 204, 204, 0.19);
		padding-left: 10px;
	}
	
	#return-tbl2 td {
		padding-left: 5px;
	}
	
	#return-tbl2 td input[type=text] {
		border: none;
		width: 100%;
		height: 35px;
	}
	
	#head-tbl {
		width: 100%;
	}
	
	#head-tbl th {
		height: 35px;
		width: 10%;
		padding-left: 10px;
	}
	
	#head-tbl td input[type=text] {
		width: 250px;
	}
	
	#item-tbl {
		width: 100%;
		border: 1px solid #e2e2e2;
		border-collapse: collapse;
		margin-top: 20px;
	}
	
	#item-tbl tr:nth-child(1) th {
		background-color: rgba(76, 144, 189, 0.72);
		color: #fff;
		text-align: center;
		height: 35px;
	}
	
	#item-tbl tr:nth-child(2) th {
		height: 35px;
		background-color: rgba(204, 204, 204, 0.19);
		text-align: center;
	}
	
	#item-tbl td {
		height: 35px;
		text-align: center;
	}
	
	#insp-imgList {
		margin-top: 20px;
		width: 100%;
	}
	
	#insp-imgList #imgList-header {
		color: #fff;
		width: 100%;
		background-color: rgba(76, 144, 189, 0.72);
		text-align: center;
		vertical-align: middle;
		height: 35px;
		padding-top: 7px;
	}
	
	#image-box {
		width: 100%;
		white-space: nowrap;
		overflow-x: auto;
		border: 1px solid #e2e2e2;
	}
	
	#image-box img {
		height: 150px;
	}
	
	#image-box::-webkit-scrollbar {
	    width: 3%;
	}
	
	#image-box::-webkit-scrollbar-thumb {
		background-color: rgba(76, 144, 189, 0.72);
	    border-radius: 8px;
	}
	
	#image-box::-webkit-scrollbar-track {
	    background: #e2e2e2;
	}
    </style>
   <!-- basic scripts -->
   <!-- ace scripts -->
   
   </head> 
   <title>반품 리스트</title>
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
                        반품
                     </li>
                     <li class="active">반품 접수 리스트</li>
                  </ul><!-- /.breadcrumb -->
               </div>
               <div class="page-content">
                  <div class="page-header">
                     <h1>
                        반품 정보
                     </h1>
                  </div>
                  <div>
					<form name="form" id="form" enctype="multipart/form-data">
						<div id="tab-content">
							<table id="head-tbl">
								<tr>
									<th>원운송장번호</th>
									<td>${orderList.koblNo}</td>
								</tr>
								<tr>
									<th class="">픽업방법</th>
									<td colspan="2">
										<c:if test="${orderList.pickType eq 'A'}">직접전달</c:if>
										<c:if test="${orderList.pickType eq 'B'}">회수요청</c:if>
									</td>
								</tr>
							</table>
							<table style="width:100%;" id="return-tbl">
								<tr>
									<th class="t-headers" colspan="2" style="text-align:center;">반품 택배 정보</th>
									<th class="t-headers" colspan="2" style="text-align:center;">반품 담당자 정보</th>
								</tr>
								<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">	
									<th class="headers">택배회사</th>
									<td>${orderList.trkCom}</td>
									<th class="headers">이름</th>
									<td>${orderList.attnName}</td>
								</tr>
								<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
									<th class="headers">택배번호</th>
									<td>${orderList.trkNo}</td>
									<th class="headers">연락처</th>
									<td>${orderList.attnTel}</td>
								</tr>
								<tr>
									<th class="headers">접수일자</th>
									<td>${orderList.trkDate}</td>
									<th class="headers">이메일</th>
									<td>${orderList.attnEmail}</td>
								</tr>
								<tr>
									<th class="t-headers"  colspan="4" style="text-align:center;">발송 정보</th>
								</tr>
								<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
									<th class="headers">주문번호</th>
									<td>${orderList.orderNo}</td>
									<th class="headers">주문일자</th>
									<td>${orderList.orderDate}</td>
								</tr>
								<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
									<th class="headers">이름 (영문)</th>
									<td>${orderList.shipperName}</td>
									<th class="headers">우편번호</th>
									<td>${orderList.shipperZip}</td>
								</tr>
								<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
									<th class="headers">주소 (영문)</th>
									<td>${orderList.shipperAddr}</td>
									<th class="headers">상세주소 (영문)</th>
									<td>${orderList.shipperAddrDetail}</td>
								</tr>
								<tr>
									<th class="headers">전화번호</th>
									<td>${orderList.shipperTel}</td>
									<th class="headers">휴대전화번호</th>
									<td>${orderList.shipperHp}</td>
								</tr>
								<tr>
									<th class="t-headers"  colspan="4" style="text-align:center;">수취업체 정보</th>
								</tr>
								<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
									<th class="headers">업체명</th>
									<td>${orderList.cneeName}</td>
									<th class="headers">국가</th>
									<td>${orderList.dstnNation}</td>
								</tr>
								<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
									<th class="headers">주</th>
									<td>${orderList.cneeState}</td>
									<th class="headers">도시</th>
									<td>${orderList.cneeCity}</td>
								</tr>
								<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
									<th class="headers">주소</th>
									<td>${orderList.cneeAddr}</td>
									<th class="headers">상세 주소</th>
									<td>${orderList.cneeAddrDetail}</td>
								</tr>
								<tr>
									<th class="headers">우편번호</th>
									<td>${orderList.cneeZip}</td>
									<th class="headers">전화번호</th>
									<td>${orderList.cneeTel}</td>
								</tr>
							</table>
							<br>
							<table id="return-tbl2">
								<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19); border-top:1px solid rgba(204, 204, 204, 0.19);">
									<th>반품 구분</th>
									<td colspan="3">
										<c:if test="${orderList.returnType eq 'N'}">일반</c:if>
										<c:if test="${orderList.returnType eq 'E'}">긴급</c:if>
									</td>
								</tr>
								<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
									<th>반품 사유</th>
									<td style="width:20%;">
										<c:if test="${orderList.returnReason eq 'A'}">단순변심</c:if>
										<c:if test="${orderList.returnReason eq 'B'}">파손</c:if>
										<c:if test="${orderList.returnReason eq 'C'}">불량</c:if>
										<c:if test="${orderList.returnReason eq 'D'}">기타</c:if>
									</td>
									<th id="returnDetailRed">기타 상세</th>
									<td>${orderList.returnReasonDetail}</td>
								</tr>
								<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
									<th>입고 요청사항</th>
									<td colspan="3">${orderList.whMsg}</td>
								</tr>
								<tr style="border-bottom: 1px solid rgba(204, 204, 204, 0.19);">
									<th class="">위약 반송 정보</th>
									<td colspan="3">
										<c:if test="${orderList.taxType eq 'Y'}">위약반송</c:if>
										<c:if test="${orderList.taxType eq 'N'}">일반반송</c:if>
									</td>
								</tr>
							</table>
							<table id="item-tbl">
								<thead>
									<tr>
										<th colspan="5" style="border-bottom:1px solid #e2e2e2;">상품정보</th>
									</tr>
									<tr>
										<th style="border-right:1px solid #e2e2e2;">상품명</th>
										<th style="border-right:1px solid #e2e2e2;">요청수량</th>
										<th style="border-right:1px solid #e2e2e2;">입고수량</th>
										<th style="border-right:1px solid #e2e2e2;">단가</th>										
										<th>화폐단위</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${itemList}" var="itemList">
										<tr>
											<td>${itemList.itemDetail}</td>
											<td>${itemList.itemCnt}</td>
											<td>${itemList.whInCnt}</td>
											<td>${itemList.unitValue}</td>
											<td>${itemList.unitCurrency}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<div id="insp-imgList">
								<div id="imgList-header">
									검수사진
								</div>
								<div id="image-box">
								<c:forEach items="${whInImgList}" var="imgList">
									<img src="https://s3.ap-northeast-2.amazonaws.com/${imgList.fileDir}"/>
								</c:forEach>
								</div>
							</div>
						</div><!-- tab-content end -->
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
      
      	<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<script src="/testView/js/jquery-ui.js"></script>
		<script src="/testView/js/popper.min.js"></script>
		<script src="/assets/js/bootstrap.min.js"></script>
		<script src="/testView/js/owl.carousel.min.js"></script>
		<script src="/testView/js/jquery.magnific-popup.min.js"></script>
		<script src="/testView/js/aos.js"></script>
		<script src="/testView/js/main.js"></script>
		
		<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="/assets/js/jquery-ui.min.js"></script>
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
		
		<script src="/assets/js/bootstrap-tag.min.js"></script>
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/chosen.jquery.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
      <!-- script on paging end -->
      

      
      <!-- script addon start -->
      <script type="text/javascript">
      <!-- excel upload 추가 -->
      </script>
      <!-- script addon end -->

   </body>
</html>