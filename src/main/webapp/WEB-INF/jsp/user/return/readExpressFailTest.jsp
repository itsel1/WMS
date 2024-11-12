<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	
<style type="text/css">
@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Gowun+Dodum&family=Nanum+Gothic:wght@700&display=swap');

.main-container * {
	font-family: 'Nanum Gothic', sans-serif;
}

.col-xs-12 col-lg-12{
	width: 90%;
}
	
body {
	padding-left: 10px;
	padding-top: 10px;
}

* {
	font-family: arial, sans-serif;
}

.noticeTable {
	width: 90%;
	border-radius: 10px;
	box-shadow: 2px 2px 5px 2px gray;
	padding-left: 15px;
	padding-bottom: 15px;
	padding-right: 15px;
}

.noticeTable th {
	border: none;
}

.noticeTable td {
	border: none;
}

#notice_text {
	border: solid 1px #a0a0a0;
	width: 100%;
	height: 180px;
	box-sizing: border-box;
	resize: both;
	padding: 10px;
	font-size: 13px;
}

.comm_text {
	width: 100%;
	height: 100%;
	border: solid 1px #a0a0a0;
	box-sizing: border-box;
	margin-top: 4px;
}

.hawbTable {
	width: 580px;
	border: solid 1px #ddd;
	background: #E4E6E9;
	border-collapse: collapse;
}

.hawbTable td {
	border: solid 1px #ddd;
	padding-right: 8px;
}

#koblNo {
	width: 100%;
	height: 40px;
	margin-right: 10px;
	border: solid 1px #ddd;
	background-color: white;
	font-size: 26px;
	vertical-align: middle;
	color: #828282;
}

input:focus {
	outline: none;
}

input:enabled {
	background: white;
}

#hawb_btn {
	background: #dcdcdc;
	display: block;
	color: inherit;
	font: inherit;
	margin: 0 auto;
	width: 60px;
	height: 30px;
	cursor: pointer;
	border: solid 1px black;
	border-radius: 2px;
}

#hawb_btn:hover {
	border: solid 1px black;
	background: #ccc;
}

.info_title {
	font-size: 18px;
	font-weight: bold;
	color: black;
}

.infoTable {
	width: 90%;
	border: solid 1px #ddd;
	border-collapse: collapse;
	box-shadow: 1px 1px 1px #ddd;
	font-size: 14px;
	box-shadow: 1px black;
	background: #fff;
}

.infoTable td {
	border: solid 1px #ccc;
}

.infoTable td:nth-child(odd) {
	color: #707070;
	border: solid 1px #ccc;
	background: linear-gradient(to top, #E4E6E9, white);
}

.info_input {
	width: 100%;
	height: 30px;
	border: none;
	font-size: 14px;
}

.tableHeader {
	font-family: 'Open Sans';
	text-align: center;
	padding: 5px 0;
	font-size: 14px;
	color: #000000 !important;
	font-weight: bold;
	background: linear-gradient(to top, #E4E6E9, white);
	border: solid 1px #ccc;
}

.td_name {
	font-family: 'Open Sans';
	text-align: center;
	padding: 5px 0;
	font-size: 13px;
	color: #000000 !important;
	font-weight: bold;
	width: 140px;
	background: linear-gradient(to top, #E4E6E9, white);
}

.rtn_radio {
	margin: 6px;
	vertical-align: middle;
}

.radio_div {
	vertical-align: middle;
}

.cancel_info {
	width: 90%;
	border: solid 1px #ddd;
	border-collapse: collapse;
	border: solid 1px #bebebe;
	box-shadow: 1px 1px 1px #ddd;
}

.cancel_info td {
	border: solid 1px #ccc;
	border-collapse: collapse;
	padding: 5px 0;
	font-size: 13px;
	color: #000000 !important;
}

#cncl_radio {
	margin-right: 8px;
}

.goods_info {
	width: 90%;
	height: 30px;
	border: solid 1px #ddd;
	border-collapse: collapse;
	box-shadow: 1px 1px 1px #ddd;
}

.goods_info td {
	border: solid 1px #ccc;
	border-collapse: collapse;
	padding: 5px 0;
	font-size: 13px;
	color: #000000 !important;
}

.remand_info {
	width: 90%;
	border: solid 1px #ddd;
	border-collapse: collapse;
	box-shadow: 1px 1px 1px #ddd;
	color: #000000 !important;
}

.remand_info td {
	border: solid 1px #ccc;
	border-collapse: collapse;
	padding: 5px 0;
	font-size: 13px;
	color: #000000 !important;
}

.remand_info tr {
	height: 2em;
}

.td_name2 {
	font-family: 'Open Sans';
	padding: 5px 0;
	font-size: 13px;
	color: #000000 !important;
	line-height: 1.5;
	text-align: center;
	font-weight: bold;
	width: 180px;
	background: linear-gradient(to top, #E4E6E9, white);
}

a:link {
	color: black;
	text-decoration: none;
}

a:visited {
	color: black;
	text-decoration: none;
}

a:hover {
	color: black;
	text-decoration: underline;
}

a:active {
	color: black;
	text-decoration: none;
}

.return_type {
	width: 90%;
	border: solid 1px #ddd;
	border-collapse: collapse;
	box-shadow: 1px 1px 1px #ddd;
	color: #000000 !important;
}

.return_type td {
	border: solid 1px #ccc;
	border-collapse: collapse;
	color: #000000 !important;
}

#rtnBtn {
	width: 80px;
	height: 40px;
	font-weight: bold;
	margin-top: 40px;
	color: inherit;
	font: inherit;
	cursor: pointer;
	border: solid 1px black;
	display: block;
	margin: 40px auto;
}

#rtnBtn:hover {
	background: #d8d8d8;
	cursor: pointer;
	border-radius: 2px;
}

::placeholder {
	color: gray;
	opacity: 0.5;
}
.colorBlack {color:#000000 !important;}

.backYellow {background-color: #ffffc9 !important;}

.b01_simple_rollover {
    color: #000000;
    border: #000000 solid 1px;
    padding: 10px;
    background-color: #ffffff;
}

.b01_simple_rollover:hover {
    color: #ffffff;
    background-color: #000000;
}

#main-contents-table {
	border: 1px solid rgba(76, 144, 189, 0.72);
}

#main-contents-table th {
	border: 1px solid #ccc;
	overflow: hidden;
}

#main-contents-table td {
	border: 1px solid #ccc;
}

.td-bg {
	background-color:rgba(204, 204, 204, 0.19);
}

#item-table {
	border: 1px solid rgba(76, 144, 189, 0.72);
}

#item-table th {
	border: 1px solid #ccc;
	overflow: hidden;
}

#item-table td {
	padding: 10px;
	border: 1px solid #ccc;
}

</style>
</head>
	<title>상품정보</title>
	<body class="no-skin"> 
		<!-- headMenu Start -->
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
			<!-- headMenu End -->
			<!-- Main container Start-->
			<div class="toppn">
				<div class="container">
		        	<div class="row">
						<div class="col-md-12 mb-0" style="font-size:14px !important;">
							<a>반품 페이지</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">반품상세정보</strong>
						</div>
		        	</div>
				</div>
			</div>
	    </div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="container">
			<div class="page-content noneArea">
				<div id="inner-content-side" >
				<br>
				<h2 style="margin-left:4px;">반품 정보
					<label style="font-size:14px; margin-left:10px; color:#0078FF; font-weight:bold;">
						<c:if test="${returnInspOne.STATE eq 'C002'}">정상입고</c:if>
						<c:if test="${returnInspOne.STATE eq 'C003'}">출고 대기</c:if>
						<c:if test="${returnInspOne.STATE eq 'C004'}">출고 승인</c:if>
						<c:if test="${returnInspOne.STATE eq 'D004'}">검수 불합격</c:if>
						<c:if test="${returnInspOne.STATE eq 'D005'}">폐기</c:if>
					</label>
				</h2>
				<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
				<!-- Main container Start-->
					<div class="main-container ace-save-state" id="main-container">
						<script type="text/javascript">
							try{ace.settings.loadState('main-container')}catch(e){}
						</script>
						<div class="main-contents">
							<br/>
							<table id="main-contents-table" style="width:100%;">
								<tr style="height:36px;">
									<th class="center" colspan="12" style="background:rgba(76, 144, 189, 0.72);color:#fff;">반송 정보</th>
								</tr>
								<tr style="height:34px;">
									<th class="center td-bg" style="width:8%;">원운송장번호</th>
									<td class="center" style="width:8%;">${returnInspOne.KOBL_NO}</td>
									<th class="center td-bg" style="width:8%;">주문번호</th>
									<td class="center" style="width:8%;">${returnInspOne.ORDER_NO}</td>
									<th class="center td-bg" style="width:8%;">주문날짜</th>
									<td class="center" style="width:8%;">${returnInspOne.ORDER_DATE}</td>
									<th class="center td-bg" style="width:8%;">도착지</th>
									<td class="center" style="width:8%;">${returnInspOne.DSTN_NATION}</td>
									<th class="center td-bg" style="width:8%;">반송택배사</th>
									<td class="center" style="width:8%;">${returnInspOne.RE_TRK_COM}</td>
									<th class="center td-bg" style="width:8%;">반송택배번호</th>
									<td class="center" style="width:8%;">${returnInspOne.RE_TRK_NO}</td>
								</tr>
								<tr style="height:34px;">
									<th class="center td-bg" style="width:8%;">위약반송 여부</th>
									<td class="center" style="width:8%;">
										<c:if test="${returnInspOne.TAX_TYPE eq 'Y'}">
											<a style="cursor:pointer;" onclick="fn_openReturnTaxPopup('${returnInspOne.NNO}')">파일 확인</a>
										</c:if>
										<c:if test="${returnInspOne.TAX_TYPE ne 'Y'}">
											${returnInspOne.TAX_TYPE}
										</c:if>
									</td>
									<th class="center td-bg" style="width:8%;">반송 구분</th>
									<td class="center" style="width:8%;">
										<c:if test="${returnInspOne.RETURN_TYPE eq 'N'}">
											일반
										</c:if>
										<c:if test="${returnInspOne.RETURN_TYPE eq 'E'}">
											긴급
										</c:if>
									</td>
									<th class="center td-bg" style="width:8%;">반송 사유</th>
									<td class="center" style="width:8%;">
										<c:if test="${returnInspOne.RETURN_REASON eq 'A'}">
											단순변심
										</c:if>
										<c:if test="${returnInspOne.RETURN_REASON eq 'B'}">
											파손
										</c:if>
										<c:if test="${returnInspOne.RETURN_REASON eq 'C'}">
											불량
										</c:if>
										<c:if test="${returnInspOne.RETURN_REASON eq 'D'}">
											기타
										</c:if>
									</td>
									<th class="center td-bg" style="width:8%;">사유 상세</th>
									<td class="center" style="width:8%;" colspan="2">
										<c:if test="${empty returnInspOne.RETURN_REASON_DETAIL}">
											-
										</c:if>
										<c:if test="${!empty returnInspOne.RETURN_REASON_DETAIL}">
											${returnInspOne.RETURN_REASON_DETAIL}
										</c:if>
									</td>
									<th class="center td-bg" style="width:8%;">입고 요청 사항</th>
									<td class="center" style="width:8%;" colspan="2">
										<c:if test="${empty returnInspOne.WH_MSG}">
											-
										</c:if>
										<c:if test="${!empty returnInspOne.WH_MSG}">
											${returnInspOne.WH_MSG}
										</c:if>
									</td>
								</tr>
								<tr style="height:36px;">
									<th class="center" colspan="12" style="background:rgba(76, 144, 189, 0.72);color:#fff;">반송 신청인 정보</th>
								</tr>
								<tr style="height:34px;">
									<th class="center td-bg" style="width:8%;">이름</th>
									<td class="center" style="width:8%;">${returnInspOne.PICKUP_NAME}</td>
									<th class="center td-bg" style="width:8%;">연락처</th>
									<td class="center" style="width:8%;">
										<c:if test="${empty returnInspOne.PICKUP_TEL}">
											${returnInspOne.PICKUP_MOBILE}
										</c:if>
										<c:if test="${!empty returnInspOne.PICKUP_TEL}">
											${returnInspOne.PICKUP_TEL}
										</c:if>
									</td>
									<th class="center td-bg" style="width:8%;">우편번호</th>
									<td class="center" style="width:8%;">${returnInspOne.PICKUP_ZIP}</td>
									<th class="center td-bg" style="width:8%;">주소</th>
									<td class="center" style="width:8%;" colspan="5">
										<c:if test="${!empty returnInspOne.PICKUP_ADDR}">
											${returnInspOne.PICKUP_ADDR}
										</c:if>
										<c:if test="${empty returnInspOne.PICKUP_ADDR}">
											${returnInspOne.PICKUP_ENG_ADDR}
										</c:if>
									</td>
								</tr>
								<tr style="height:36px;">
									<th class="center" colspan="12" style="background:rgba(76, 144, 189, 0.72);color:#fff;">수취 업체 정보</th>
								</tr>
								<tr style="height:34px;">
									<th class="center td-bg" style="width:8%;">업체명</th>
									<td class="center" style="width:8%;">${returnInspOne.SENDER_NAME}</td>
									<th class="center td-bg" style="width:8%;">연락처</th>
									<td class="center" style="width:8%;">
										<c:if test="${empty returnInspOne.SENDER_TEL}">
											${returnInspOne.SENDER_HP}
										</c:if>
										<c:if test="${!empty returnInspOne.SENDER_TEL}">
											${returnInspOne.SENDER_TEL}
										</c:if>
									</td>
									<th class="center td-bg" style="width:8%;">우편번호</th>
									<td class="center" style="width:8%;">${returnInspOne.SENDER_ZIP}</td>
									<th class="center td-bg" style="width:8%;">주소</th>
									<td class="center" style="width:8%;" colspan="5">
										${returnInspOne.SENDER_ADDR}, ${returnInspOne.SENDER_CITY}, ${returnInspOne.SENDER_STATE}
									</td>
								</tr>
								<tr style="height:36px;">
									<th class="center" colspan="12" style="background:rgba(76, 144, 189, 0.72);color:#fff;">상품 정보</th>
								</tr>
							</table>
						</div>
						<div class="item-contents">
							<input type="hidden" id="itemCnt" name="itemCnt" value="${fn:length(returnInspOne.orderItemList)}"/>
							<table id="item-table" style="width:100%;">
								<c:forEach items="${returnInspOne.orderItemList}" var="items" varStatus="itemStatus">
									<tr style="height:40px;">
										<th class="center td-bg" colspan="8">상품 ${itemStatus.count}</th>
									</tr>
									<tr>
										<td class="center" style="" rowspan="4">
											<a href="${items.ITEM_URL}" target="_blank">
												<img src="${items.ITEM_IMG_URL}" style="height:144px;" loading="lazy">
											</a>
										</td>
										<th class="center td-bg" style="width:5%;">상품명</th>
										<td colspan="5">${items.ITEM_DETAIL}</td>
										<th class="center td-bg" style="width:50%;">검수 사진</th>
									</tr>
									<tr style="height:36px;">
										<th class="center td-bg" style="width:5%;">브랜드</th>
										<td>
											<c:if test="${!empty items.BRAND}">
												${items.BRAND}
											</c:if>
											<c:if test="${empty items.BRAND}">
												-
											</c:if>
										</td>
										<th class="center td-bg" style="width:5%;">제조국</th>
										<td>${items.MAKE_CNTRY}</td>
										<th class="center td-bg" style="width:5%;">제조사</th>
										<td>
											<c:if test="${!empty items.MAKE_COM}">
												${items.MAKE_COM}
											</c:if>
											<c:if test="${empty items.MAKE_COM}">
												-
											</c:if>
										</td>
										<td style="width:50%;" rowspan="3">
											<c:forEach items="${items.imgList}" var="imgList" varStatus="imgListStatus">
												<a href="https://s3.ap-northeast-2.amazonaws.com/${imgList.FILE_DIR}" target="_blank">
													<img style="width:100px;" src="https://s3.ap-northeast-2.amazonaws.com/${imgList.FILE_DIR}" loading="lazy">
												</a>
											</c:forEach>
										</td>
									</tr>
									<tr style="height:36px;">
										<th class="center td-bg" style="width:5%;">상품수량</th>
										<td style="text-align:right;">${items.ITEM_CNT}</td>
										<th class="center td-bg" style="width:5%;">입고수량</th>
										<td style="text-align:right;">${items.STOCK_CNT}</td>
										<th class="center td-bg" style="width:5%;">실무게</th>
										<td style="text-align:right;">${items.ITEM_WTA} <font style="font-size:10px;">(${items.ITEM_WT_UNIT})</font></td>
									</tr>
									<tr style="height:36px;">
										<th class="center td-bg" style="width:5%;">Width</th>
										<td style="text-align:right;">${items.ITEM_WIDTH} <font style="font-size:10px;">(${items.ITEM_DIM_UNIT})</font></td>
										<th class="center td-bg" style="width:5%;">Height</th>
										<td style="text-align:right;">${items.ITEM_HEIGHT} <font style="font-size:10px;">(${items.ITEM_DIM_UNIT})</font></td>
										<th class="center td-bg" style="width:5%;">Length</th>
										<td style="text-align:right;">${items.ITEM_LENGTH} <font style="font-size:10px;">(${items.ITEM_DIM_UNIT})</font></td>
									</tr>
								</c:forEach>
							</table>
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
			function fn_openReturnTaxPopup(nno) {
				var _width = '480';
			    var _height = '500';
			    var _left = (screen.width/2)-(_width/2);
			    var _top = (screen.height/2)-(_height/2);
	
			    window.open("/return/orderInfo/taxReturn?nno="+nno, "mywindow", 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
			}
		</script>
		<!-- script addon end -->
	</body>
</html>
