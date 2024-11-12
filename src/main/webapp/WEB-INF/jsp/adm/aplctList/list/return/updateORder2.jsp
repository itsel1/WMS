<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<style>
	
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
.profile-info-value{
     word-break:break-all;
}
.my_button {
     display: inline-block;
     width: 200px;
     text-align: center;
     padding: 10px;
     background-color: #006BCC;
     color: #fff;
     text-decoration: none;
     border-radius: 5px;
}
        
.my_button2 {
     display: inline-block;
     width: 100px;
     text-align: center;
     padding: 10px;
     background-color: #006BCC;
     color: #fff;
     text-decoration: none;
     border-radius: 5px;
}

.imgs_wrap {

     border: 2px solid #A8A8A8;
     margin-top: 30px;
     margin-bottom: 30px;
     padding-top: 10px;
     padding-bottom: 10px;

}
.imgs_wrap img {
    max-width: 150px;
    margin-left: 10px;
    margin-right: 10px;
}
        
.add_imgs_wrap {

    border: 2px solid #A8A8A8;
    margin-top: 30px;
    margin-bottom: 30px;
    padding-top: 10px;
    padding-bottom: 10px;

}
.add_imgs_wrap img {
    max-width: 150px;
    margin-left: 10px;
    margin-right: 10px;
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

#comm_text {
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

#hawb_input {
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

#info_input {
	width: 90%;
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
	width: 140px;
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
</style>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	 </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
	
	</head> 
	<title>반품 검수 페이지</title>
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
								신청서 리스트
							</li>
							<li class="active">반품 검수 페이지</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						
						<div id="inner-content-side" >
	<form enctype="multipart/form-data" name="returnForm" id="returnForm" method="POST" action="/mngr/aplctList/return/fileUpload">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


	<div class="main_div">
		<div>
			<h1>반품 검수 페이지</h1>
		</div>
		<br>
		<div>
			<p style="font-size: 20px; font-weight: bold; color: black;">운송장
				번호</p>
				

			<div>
				
				<label for="hawbNo">
				<input type="text" name="hawbNo"
								id="hawb_input" value="${vo.hawbNo }" style="width:300px;" readonly>
								</label> <input type="hidden" name="nno" value="${nno }" >
								
			</div>
		</div>
		<br> <br>
		<div class="infoDiv">
			<table class="infoTable">
				<tr>
					<th class="tableHeader" colspan="2">반품 담당자 정보</th>
				</tr>
				<tr>
					<td class="td_name">이름</td>
					<td><input type="text" name="attnName" id="info_input" value="${vo.attnName }" style="width:100%" readonly></td>
				</tr>
				<tr>
					<td class="td_name">전화번호</td>
					<td><input type="text" name="attnTel" id="info_input" value="${vo.attnTel }" style="width:100%" readonly></td>
				</tr>
				<tr>
					<td class="td_name">이메일</td>
					<td><input type="email" name="attnEmail" id="info_input" value="${vo.attnEmail }" style="width:100%" readonly></td>
				</tr>
			</table>
			<br> <br>
			<table class="infoTable">
				<tr>
					<th class="tableHeader" colspan="2">반품 발송 정보</th>
				</tr>
				<tr>
					<td class="td_name">이름</td>
					<td><input type="text" name="shipperName" id="info_input" value="${vo.shipperName }" style="width:100%" readonly></td>
				</tr>
				<tr>
					<td class="td_name">주소</td>
					<td><input type="text" name="shipperAddr" id="info_input" value="${vo.shipperAddr }" style="width:100%" readonly></td>
				</tr>
				<tr>
					<td class="td_name">전화번호</td>
					<td><input type="text" name="shipperEmail" id="info_input" value="${vo.shipperTel }" style="width:100%" readonly></td>
				</tr>
			</table>
			<br> <br>
			<table class="infoTable">
				<tr>
					<th class="tableHeader" colspan="2">해외 도착지 정보</th>
				</tr>
				<tr>
					<td class="td_name">회사명</td>
					<td><input type="text" name="cneeName" id="info_input" value="${vo.cneeName }" style="width:100%" readonly></td>
				</tr>
				<tr>
					<td class="td_name">주소</td>
					<td><input type="text" name="cneeAddr" id="info_input" value="${vo.cneeAddr }" style="width:100%" readonly></td>
				</tr>
				<tr>
					<td class="td_name">전화번호</td>
					<td><input type="text" name="cneeTel" id="info_input" value="${vo.cneeTel }" style="width:100%" readonly></td>
				</tr>
			</table>
		</div>
		<br> <br> <br>
		<div class="radio_div">
			
				<label for="rtn_div"><font
					style="font-size: 18px; font-weight: bold; color: black;">반송
						구분</font></label> <input type="radio" name="rtn_div" value=0 class="rtn_radio" id="rtnType1" onclick="return(false)">일반배송
				<input type="radio" name="rtn_div" value = 1 class="rtn_radio" id="rtnType2" onclick="return(false)">긴급배송
			
		</div>
		<br>
		<div>
			<table class="cancel_info">
				<tr height="38">
					<td class="td_name">취소사유</td>
					<td><input type="radio" name="cncl" id="cncl_radio1" value="단순변심" onclick="return(false)"><label
						for="cncl">단순변심</label> <input type="radio" name="cncl"
						id="cncl_radio2" value="파손" onclick="return(false)"><label for="cncl">파손</label> <input
						type="radio" name="cncl" id="cncl_radio3" value="불량" onclick="return(false)"><label for="cncl">불량</label>
						<input type="radio" name="cncl" id="cncl_radio4" value="기타" onclick="return(false)"><label
						for="cncl">기타</label></td>
				</tr>
				<tr height="20">
					<td class="td_name">취소사유 상세</td>
					<td><input type="text" name="cncl_text" id="info_input" value="${vo.cnclText }" style="width:100%" readonly></td>
				</tr>
			</table>
			<br>
			<br>
			<table class="return_type">
				<tr height="30">
					<td colspan="2" class="td_name2"
						style="text-align: left; padding-left: 4px; font-size: 16px;"><a
						href="" id="return_a">[반품 단가표]</a></td>
				</tr>
				<tr height="30">
					<td class="td_name2">반품유형 선택</td>
					<td style="font-size: 14px;"><input type="radio"
						name="rtn_type" checked><label for="rtn_type">프리미엄
							반품서비스</label></td>
				</tr>
				<tr height="100">
					<td class="td_name2">Comment</td>
					<td>
							<textarea id="comm_text" rows="8" name="" readonly>${vo.comment } </textarea>
					</td>
				</tr>
			</table>
		</div>
		<br>
		
		<br>
		<br>	
		<br>
		<br>
		
				
			<div>	
		
				<div id="accordion" class="accordion-style1 panel-group">
				
					<c:forEach items="${items }" var="item" varStatus="voStatus">
					<div id="addForm">
						<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important; width:90%">
							
							<h4><input type="checkbox" id = "rgstChk${item.subNo }" name="rgstChk" value="${item.subNo }">  상품  ${voStatus.index+1}</h4>
							<div style="width:30%; height:100%; float:left">
								<img src="${item.itemImgUrl }" style="width:400px;height:400px">
								
							</div>
							<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;width:60%; float:left;">
								
								<div class="profile-user-info  form-group hr-8">
						
									<div class="profile-info-value"><span style="font-size:150%;">상품명 : ${item.itemDetail} / ${item.nativeItemDetail }</span></div>
									
									
								</div>
							</div>
									
							<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;width:60%; float:left;">
								
								
								<div class="profile-user-info  form-group hr-8">
									
									<div class="profile-info-name nno" style="width:150px;">NNO</div>
									<div class="profile-info-value">
										<input class="col-xs-12 shipperVal" type="text" name="nno" id="nno" value="${item.nno}" style="width:200px;" readonly/>
									</div>	
									<div class="profile-info-name subNo" style="width:150px;">SUBNO</div>
									<div class="profile-info-value">
											<input class="col-xs-12 subNo" type="text" name="subNo" id="subNo" value="${item.subNo}" style="width:200px;" readonly/>
									</div>
						
									
								</div>
							</div>
							
							<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;width:60%; float:left;">
								
								
								<div class="profile-user-info  form-group hr-8">
									
									<div class="profile-info-name trkCom" style="width:150px;">택비사</div>
									<div class="profile-info-value">
										<input class="col-xs-12 trkCom" type="text" name="trkCom" id="trkCom" style="width:200px;"/>
									</div>	
									<div class="profile-info-name trkNo" style="width:150px;">택배번호</div>
									<div class="profile-info-value">
											<input class="col-xs-12 trkNo" type="text" name="trkNo" id="trkNo" style="width:200px;"/>
									</div>
						
									
								</div>
							</div>
							
							
							
							
							
							<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;width:60%; float:left;">
							
								<div class="profile-user-info  form-group hr-8">
									
									<div class="profile-info-name brand" style="width:150px;">브랜드</div>
									<div class="profile-info-value">
											<input class="col-xs-12 itemDetail" type="text" name="brand" id="brand" value="${item.brand}" style="width:200px;" readonly />
									</div>
									<div class="profile-info-name itemMeterial" style="width:200px;">상품 성분</div>
									<div class="profile-info-value">
											<input class="col-xs-12 nativeItemDetail" type="text" name="itemMeterial" id="itemMeterial" value="${item.itemMeterial}" style="width:100px;" />
									</div>
									
									<div class="profile-info-name wtUnit" style="width:200px;">무게 단위</div>
									<div class="profile-info-value">
											<input class="col-xs-12 wtUnit" type="text" name="wtUnit" id="wtUnit" value="${item.wtUnit}" style="width:100px;" />
									</div>
								</div>
							</div>
							
							
							<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;width:60%; float:left;">
							
								<div class="profile-user-info  form-group hr-8">
									<div class="profile-info-name itemCnt" style="width:150px;">입고 개수</div>
									<div class="profile-info-value">
											<input class="col-xs-12 housCnt" type="text" name="housCnt" id="housCnt${voStatus.count }" style="width:100px; background-color:#facfd2; color:red; font-weight:bold; font-size:140%" placeholder="${item.itemCnt }" onchange="javascript:fn_containValue(this,${item.itemCnt }, ${voStatus.count })" numberonly/>
									</div>
									<div class="profile-info-name itemCnt" style="width:150px;">상품 개수</div>
									<div class="profile-info-value">
											<input class="col-xs-12 itemCnt" type="text" name="itemCnt" id="itemCnt" value="${item.itemCnt}" style="width:100px; background-color:#facfd2; color:red; font-weight:bold; font-size:140%" readonly/>
									</div>
									<div class="profile-info-name unitValue" style="width:150px;">상품가격</div>
									<div class="profile-info-value">
											<input class="col-xs-12 unitValue" type="text" name="unitValue" id="unitValue" value="${item.unitValue}" style="width:200px;"/>
									</div>
									<div class="profile-info-name unitCurrency" style="width:150px;">통화</div>
									<div class="profile-info-value">
											<input class="col-xs-12 unitCurrency" type="text" name="unitCurrency" id="unitCurrency" value="${item.unitCurrency}" style="width:200px;"/>
									</div>
								</div>
							</div>
								
							<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;width:60%; float:left;">	
								<div class="profile-user-info  form-group hr-8">
									<div class="profile-info-name makeCntry" style="width:150px;">제조국</div>
									<div class="profile-info-value">
											<input class="col-xs-12 makeCntry" type="text" name="makeCntry" id="makeCntry" value="${item.makeCntry}" style="width:200px;" />
									</div>
									<div class="profile-info-name makeCom" style="width:150px;">제조회사</div>
									<div class="profile-info-value">
											<input class="col-xs-12 makeCom" type="text" name="makeCom" id="makeCom" value="${item.makeCom}" style="width:200px;" />
									</div>
									<div class="profile-info-name hsCode" style="width:150px;">HS CODE</div>
									<div class="profile-info-value">
											<input class="col-xs-12 hsCode" type="text" name="hsCode" id="hsCode" value="${item.hsCode}" style="width:200px;" />
									</div>
								</div>
							</div>
							
							
							<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;width:60%; float:left;">
								<div class="profile-user-info  form-group hr-8">
									<div class="profile-info-name itemUrl" style="width:250px;">입고 메모</div>
									<div class="profile-info-value">
											<input class="col-xs-12 inspcMemo" type="text" name="inspcMemo" id="inspcMemo"  style="width:700px;" />
									</div>
							</div>		
									
							<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;width:60%; float:left;">		
								</div>
								<div class="profile-user-info  form-group hr-8">
									
									<div class="profile-info-name itemImgUrl" style="width:250px;">입고 기록</div>
									<div class="profile-info-value">
											<input class="col-xs-12 itemImgUrl" type="text" name="inspc" id="itemImgUrl"  style="width:700px;" />
									</div>
									
								</div>
								
							<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;width:60%; float:left;" >		
								</div>
								<div class="profile-user-info hidden" id = "addInfo${voStatus.count}" name = "addInfo" >
									<div class="profile-info-row " style="float:left">
										<div class="profile-info-name"> 추가입고 <br/>사진업로드  </div>
		
										<div class="profile-info-value" style="vertical-align: middle;">
											<div class="add_wrap col-md-12 col-lg-2" style="text-align: center;">
									            <div id="add_imgs_wrap${voStatus.count}_1"class="add_imgs_wrap">
									            	<span class="red">* 재등록 시, 이미지 변경</span>
									            	<img id="img" />
									            </div>
												<a href="javascript:" class="my_button2 registImage" id="${voStatus.count}_1">사진 업로드1</a>
												<br/>
									            <input type="file" id="add_imgs${voStatus.count}_1" name="add_imgs${voStatus.count}" style="display:none"/>
									        </div>
											<div class="add_wrap col-md-12 col-lg-2" style="text-align: center;">
									            <div id="add_imgs_wrap${voStatus.count}_2"class="add_imgs_wrap">
									           		<span class="red">* 재등록 시, 이미지 변경</span>
									            	<img id="img" />
									            </div>
												<a href="javascript:" class="my_button2 registImage" id="${voStatus.count}_2">사진 업로드2</a>
												<br/>
									            <input type="file" id="add_imgs${voStatus.count}_2" name="add_imgs${voStatus.count}" style="display:none"/>
									        </div>
											<div class="add_wrap col-md-12 col-lg-2" style="text-align: center;">
									            <div id="add_imgs_wrap${voStatus.count}_3"class="add_imgs_wrap">
									            	<span class="red">* 재등록 시, 이미지 변경</span>
									            	<img id="img" />
									            </div>
												<a href="javascript:" class="my_button2 registImage" id="${voStatus.count}_3">사진 업로드3</a>
												<br/>
									            <input type="file" id="add_imgs${voStatus.count}_3" name="add_imgs${voStatus.count}" style="display:none"/>
									        </div>
											<div class="add_wrap col-md-12 col-lg-2" style="text-align: center;">
									            <div id="add_imgs_wrap${voStatus.count}_4"class="add_imgs_wrap">
									            	<span class="red">* 재등록 시, 이미지 변경</span>
									            	<img id="img" />
									            </div>
												<a href="javascript:" class="my_button2 registImage" id="${voStatus.count}_4">사진 업로드4</a>
												<br/>
									            <input type="file" id="add_imgs${voStatus.count}_4" name="add_imgs${voStatus.count}" style="display:none"/>
									        </div>
											<div class="add_wrap col-md-12 col-lg-2" style="text-align: center;">
									            <div id="add_imgs_wrap${voStatus.count}_5"class="add_imgs_wrap">
									            	<span class="red">* 재등록 시, 이미지 변경</span>
									            	<img id="img" />
									            </div>
												<a href="javascript:" class="my_button2 registImage" id="${voStatus.count}_5">사진 업로드5</a>
												<br/>
									            <input type="file" id="add_imgs${voStatus.count}_5" name="add_imgs${voStatus.count}" style="display:none"/>
									        </div>
										</div>
									</div>
								</div>
							</div>
								
								
							<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;width:60%; float:left;" >		
								</div>
								<div class="profile-user-info hidden" name="failField" style="font-size:medium; border-top:none;">
									<div class="profile-info-row" style="width:90%">
										<div class="profile-info-name" style="width:90%;float:left">
											<label class="bolder red bigger-150">이미지</label> 
										</div>
										<div class="profile-info-value">
										<span class="red" style="float:left"> *이미지 재업로드 시, 기존에 업로드한 이미지 자동 삭제. 이미지 다중 등록 시, Ctrl + 파일 클릭하여 등록</span>
										<div>
									        <div id="imgs_wrap${voStatus.count}"class="imgs_wrap">
									            <img id="img" />
									        </div>
									    </div>
								        <div class="input_wrap">
								            <a href="javascript:" onclick="fileUploadAction(${voStatus.count});" class="my_button" >이미지 등록</a>
								            <input type="file" id="input_imgs${voStatus.count}" name="input_imgs" style="display:none" multiple/>
								            <input type="hidden" id="itemImgCnt${voStatus.count}" name="itemImgCnt" value="1"/>
								        </div>
									</div>
										
									</div>
								</div>

								
							</div>
							 
							
							
						</div></c:forEach>
						<div id="radio_div" style="text-align:center; width:90%">

							  <div class="row">
								<div class="col-xs-12 col-sm-12">
									<div class="profile-user-info" style="font-size:medium;">
										<div class="profile-info-row">
											<div class="profile-info-name"> 
												<label class="bolder blue">검수상태</label>
											</div>
											<div class="profile-info-value">
												<div class="control-group">
													<div class="radio">
														<label>
															<input name="form-field-radio" type="radio" checked="true" class="ace input-lg" value="WO" id="radio1" onclick="imageDiv(radio1)"/>
															<span class="lbl bigger-120"> 정상입고</span>
														</label>
														<label>
															<input name="form-field-radio" type="radio" class="ace input-lg" value="WP" id="radio2" onclick="imageDiv(radio2)"/>
															<span class="lbl bigger-120"> 부분입고</span>
														</label>
														<label>
															<input name="form-field-radio" type="radio" class="ace input-lg" value="WF" id="radio3" onclick="imageDiv(radio3)"/>
															<span class="lbl bigger-120"> 검수 불합격</span>
														</label>
													</div>
													<input type="hidden" name="whStatus" id="whStatus" >
												</div>										
											</div>
										</div>
									</div>
									<div class="hr hr-8 dotted"></div>
								</div>
							</div>
			 
						<div class="row">
								<div class="col-xs-12 col-sm-4" >
									<div class="widget-box transparent hidden" name="rackField">
										<div class="widget-header widget-header-small">
											<h2 class="widget-title smaller">
												<i class="ace-icon fa fa-check-square-o bigger-170"></i>
												Rack 정보
											</h2>
										</div>
										<div class="widget-body">
											<div class="widget-main">
												<input class="bigger-210 light-red" type="text" id="rack" name="rack" value="rack#1" style="background-color: yellow"/>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12 col-sm-3 col-sm-offset-3">
									<button type="button" class="btn btn-lg" id="backBtn" name="backBtn">처음으로</button>
								</div>
								<div class="col-xs-12 col-sm-3">
									<button type="button" class="btn btn-lg btn-success" id="saveBtn" name="saveBtn">저장하기</button>
								</div>
							</div>
							</div>
							</form>
						</div><!-- /#home -->
					</div>
					
				</div>
				
					
			</div>
		</div>
				

		
		<br>
		<br>
		<br>
	
		
	</form>


			
						
						<div>
							
	
			
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
		/*
			// 검수 불합격일 경우 파일 업로드 div show
			function imageDiv(objValue) {
				if($('input:radio[id=radio1]').is(':checked')){
					$(".showDiv").hide();
					return false;
				}else{
					$(".showDiv").show();
					return false;
				}
			}*/
			
			/*
			// 반품 불합격일 경우 업로드할 제품사진 미리보기 div
			function setImages(event,subNo) {
				for(let image of event.target.files){
					let reader = new FileReader();
					
					reader.onload = function(event){
						let img = document.createElement("img");
						img.setAttribute("src", event.target.result);
						img.style.width='300px';
						img.style.height='300px';
						document.querySelector("div#imageContainer"+subNo).appendChild(img);
					}
					console.log(image);
					reader.readAsDataURL(image);
				}
			}*/
		
			// 상품 삭제
			function getIndex2(index){
				$("#returnForm").attr("action", "/mngr/aplctList/return/deleteReturn?index="+index);
				$("#returnForm").attr("method", "POST");
				$("#returnForm").submit();
				alert('삭제되었습니다.');
			}
			
			// 저장
			function update(){
				 $("#returnForm").attr("action","/mngr/aplctList/return/updatePage");
				 $("#returnForm").attr("method", "POST");
				 $("#returnForm").submit();
				 alert('수정되었습니다');
			}
			
			// 파일 업로드 
			function upload() {
				$("#returnForm").attr("action", "/mngr/aplctList/return/fileUpload");
				$("#returnForm").attr("method", "POST");
				$("#returnForm").submit();
			}
			
			// 상품 추가 
			function addDiv() {
				$("#returnForm").attr("action", "/mngr/aplctList/return/addDiv");
				$("#returnForm").attr("method", "POST");
				$("#returnForm").submit();
				
			}
			
			function addInspc() {
				$("#returnForm").attr("action", "/mngr/aplctList/return/addInspc");
				$("#returnForm").attr("method", "POST");
				$("#returnForm").submit();
			}
			
			 var sel_files = [];
		     var targetsNum = "";
		     var targetIdAdd = "";
		        
		        
			 $(document).ready(function() {
		        	$("[name=input_imgs]").on("change", handleImgFileSelect);
		        	$("[name*=add_imgs]").on("change", handleImgFileSelectAdd);
		        }); 
			 
			 function fileUploadAction(targetId) {
		        	targetsNum = targetId;
		        	/* $("#input_imgs"+targetId).val(""); */
		            $("#input_imgs"+targetId).trigger('click');
		        }
			 
			 function handleImgFileSelect(e) {

		            // 이미지 정보들을 초기화
		            sel_files = [];
		            $("#imgs_wrap"+targetsNum).empty();
		            var files = e.target.files;
		            var filesArr = Array.prototype.slice.call(files);
					$("#itemImgCnt"+targetsNum).val(filesArr.length);
		            var index = 0;
		            filesArr.forEach(function(f) {
		                if(!f.type.match("image.*")) {
		                    alert("확장자는 이미지 확장자만 가능합니다.");
		                    return;
		                }

		                sel_files.push(f);

		                var reader = new FileReader();
		                reader.onload = function(e) {
		                	$("#itemImgName"+targetsNum).val($("#itemImgName"+targetsNum).val()+f.name+",");
		                    var html = "<a href=\"javascript:void(0);\" onclick=\"deleteImageAction(img_id_"+index+")\" id=\"img_id_"+index+"\"><img src=\"" + e.target.result + "\" data-file='"+f.name+"' class='selProductFile' title='Click to remove'><i class='ace-icon fa fa-times red2'></i></a>";
		                    $("#imgs_wrap"+targetsNum).append(html);
		                    
		                    index++;
		                }
		                reader.readAsDataURL(f);
		                
		            });
		        }
			 
			 $(".registImage").on('click',function(e){
		        	targetIdAdd= $(this).attr("id");
		            $("#add_imgs"+$(this).attr("id")).trigger('click');
				})
			 
			 function handleImgFileSelectAdd(e) {

		            // 이미지 정보들을 초기화
		            sel_files = [];
		            $("#add_imgs_wrap"+targetIdAdd).empty();
		            var files = e.target.files;
		            var filesArr = Array.prototype.slice.call(files);
		            var index = 0;
		            filesArr.forEach(function(f) {
		                if(!f.type.match("image.*")) {
		                    alert("확장자는 이미지 확장자만 가능합니다.");
		                    return;
		                }
		               // f.style.width='150px';
		                sel_files.push(f);

		                var reader = new FileReader();
		                reader.onload = function(e) {
		                    var html = "<a href=\"javascript:void(0);\" onclick=\"deleteImageAction(img_id_"+targetIdAdd+")\" id=\"img_id_"+targetIdAdd+"\"><img src=\"" + e.target.result + "\" data-file='"+f.name+"' class='selProductFile'  title='Click to remove'><i class='ace-icon fa fa-times red2'></i></a>";
		                    $("#add_imgs_wrap"+targetIdAdd).append(html);
		                    
		                    index++;
		                }
		                reader.readAsDataURL(f);
		                
		            });
		        }
			 
			 function deleteImageAction(index) {
		            sel_files.splice(index, 1);

		            //var img_id = "#img_id_"+index.id;
		            $("#"+index.id).remove();
		        }

		        $("input:text[numberOnly]").on("keyup", function() {
		            $(this).val($(this).val().replace(/[^0-9]/g,""));
		        });

		        $("input:text[floatOnly]").on("keyup", function() {
		            $(this).val($(this).val().replace(/[^0-9.]/g,""));
		        });

		        $("input[id*='horiz']").change(function(){
		        	wtCalc();
		        	maximum();
		        });
		        $("input[id*='verti']").change(function(){
		        	wtCalc();
		        	maximum();
		        });
		        $("input[id*='height']").change(function(){
		        	wtCalc();
		        	maximum();
		        });
		        $("input[id*='value']").change(function(){
		        	wtCalc();
		        	maximum();
		        });
		        $("#boxWeight").change(function(){
		        	maximum();
		        });

		        function maximum(){
		        	var size = $("input[id*='result']").size();
		        	var results = $("#boxWeight").val();
					for(loop=0;loop<size;loop++){	
						if(parseFloat(results) < parseFloat($("input[id*='result']").get(loop).value)){
							results = $("input[id*='result']").get(loop).value;
						}
					}
					$("#maxi").text(results);
		        };

		        function wtCalc(){
		            var horiz = 0;
		            var verti = 0;
		            var height = 0;
		            var value = 0;
		            var result = 0;
		        	var size = $("input[id*='horiz']").size();
		        	for(loop=0;loop<size;loop++){
		        		horiz = $("input[id*='horiz']").get(loop).value;
		        		verti = $("input[id*='verti']").get(loop).value;
		        		height = $("input[id*='height']").get(loop).value;
		        		value = $("input[id*='value']").get(loop).value;
		        		result = (horiz*verti*height)/value;
		        		result = result.toFixed(1);
		        		if(result != 0){
		        			$("input[id*='result']").get(loop).value = result;
		          		}else{
		          			$("input[id*='result']").get(loop).value = "";
		           		}
		           	}
		        };
		        

			
			 function fn_containValue(ids, totalVal,index){
					if($(ids).val() > totalVal){
						if(confirm("입력된 상품 개수가 많습니다. 추가 검수 상품이 맞습니까?")){
							if($("#addInfo"+index).hasClass("hidden")){
								$("#addInfo"+index).toggleClass("hidden");
								var temp = $(ids).val() - totalVal;
								$("#addOkCnt"+index).val(temp);
								$("#addFailCnt"+index).val(0);
							}
						}else{
							$(ids).val("");
						}
						
						
					}else{
						if(!$("#addInfo"+index).hasClass("hidden"))
							$("#addInfo"+index).toggleClass("hidden");
					}
		        }
			
			
			jQuery(function($) {
				$(document).ready(function() {
					$("#14thMenu").toggleClass('open');
					$("#14thMenu").toggleClass('active'); 
					$("#14thThr").toggleClass('active');	
					
					$("input:radio[name=rtn_div]:input[value='${vo.rtnDiv}']").attr("checked", true);
					$("input:radio[name=cncl]:input[value='${vo.cncl}']").attr("checked", true);
					$("input:radio[name=rmnd]:input[value='${vo.rmnd}']").attr("checked", true);
					
					
					
					let cncl = "${rtn.cncl}";
					let rtnType="${rtn.rtnType}";
					let rmnd = "${rtn.rmnd}";
					console.log(cncl);
					console.log(rtnType);
					
					// 취소사유 라디오버튼 불러오기 
					if(cncl== "단순변심"){
						$("#cncl_radio1").prop("checked", true);
					}else if(cncl=="파손"){
						$("#cncl_radio2").prop("checked", true);
					}else if(cncl=="불량"){
						$("#cncl_radio3").prop("checked", true);
					}else if(cncl=="기타"){
						$("#cncl_radio4").prop("checked", true);
					}
					
					// 반송구분 라디오버튼 불러오기
					if(rtnType=="일반배송"){
						$("#rtnType1").prop("checked", true);
					}else if(rtnType=="긴급배송"){
						$("#rtnType2").prop("checked", true);
					}
					
					// 관부가세 환급여부 
					if(rmnd=="환급"){
						$("#rmnd_radio1").prop("checked", true);
					}else if(rmnd="미환급"){
						$("#rmnd_radio2").prop("checked", true);
					}
					
					
					$(":input:radio[name=form-field-radio]").on('change',function(){
						if($(":input:radio[name=form-field-radio]:checked").val() == "WF"){
							$("[name=failField]").removeClass("hidden");
							$("[name=rackField]").removeClass("hidden");
							$("[name=nomalField]").addClass("hidden");
							$("[name=orderMemo]").each( function(){
					            if($(this).val()==""){
					            	$(this).removeClass("woInput");
					            	$(this).addClass("wfInput");
					            }
					        });
						}else if($(":input:radio[name=form-field-radio]:checked").val() == "WO"){
							$("[name=nomalField]").removeClass("hidden");
							$("[name=failField]").addClass("hidden");
							$("[name=rackField]").addClass("hidden");
							$("[name=orderMemo]").removeClass("wfInput");					
							$("[name=orderMemo]").addClass("woInput");
						}else{
							$("[name=nomalField]").addClass("hidden");
							$("[name=failField]").addClass("hidden");
							$("[name=rackField]").removeClass("hidden");
							$("[name=orderMemo]").each( function(){
					            if($(this).val()==""){
					            	$(this).removeClass("woInput");
					            	$(this).addClass("wfInput");
					            }
					        });
						}
					});
					
				});
				
				$("[name=rgstChk]").on('change',function(e){
					if(this.checked){
						$("#rgstYN"+this.value).val("Y");
					}else{
						$("#rgstYN"+this.value).val("N");
					}
					
				})
				
				
				$("[name=orderMemo]").on('change',function(e){
					if(this.value==""){
						this.classList.add("wfInput");
						this.classList.remove("woInput");
					}else{
						this.classList.add("woInput");
						this.classList.remove("wfInput");
					}
					
				})
				
				$("#backBtn").on("click",function(){
					location.reload();
				})
				
				$("#saveBtn").on("click",function(e){
					var obj = document.getElementsByName('form-field-radio');
					var checked_index = -1;
					var checked_value = '';
					var passChk = 0;
					for( i=0; i<obj.length; i++ ) {
						if(obj[i].checked) {
							checked_index = i;
							checked_value = obj[i].value;
						}
				}
					
				$("#whStatus").val($(":input:radio[name=form-field-radio]:checked").val());
					if($("#whStatus").val() == "WO"){
						$("[name=housCnt]").each(function(e){
							if($("[name=rgstChk]").get(e).checked){
								if($(this).val()==""){
									/* if(confirm("입고수량이 입력되지 않았습니다. 상품개수를 입고수량으로 대체휴ㅏ")){
										$(this).val($(this).attr('placeholder'));
									}else{ */
										alert("입고수량이 비어있습니다.")
										$(this).focus();
										passChk = 1;
										return false;
									/* } */
									
								}else{
									if($(this).val() < $("[name=whTotalItemCnt]").get(e).value){
										alert("입고수량이 상품개수보다 작습니다.")
										passChk = 1;
										return false;
									}
									
								}
							}
							
						})
					}else{
						$("[name=housCnt]").each(function(e){
							if($("[name=rgstChk]").get(e).checked){
								if($(this).val()==""){
									alert("입고수량이 비어있습니다.")
									$(this).focus();
									passChk = 1;
									return false;
								}
							}
	
						})
					}
				if(passChk == 1){
					return false;
				}
				$('#orderInspForm').attr("action","/mngr/aplctList/return/addInspc");
				$('#orderInspForm').attr("method","post");
				//$('#orderInspForm').submit();
			});
				
				

		       
				
				var myTable = 
				$('#dynamic-table')
				.DataTable( {
					"dom": 't',
					"paging":   false,
			        "ordering": false,
			        "info":     false,
			        "searching": false,
			        "scrollY" : 500,
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
				
				
			
			
			});
		</script>
		<!-- script addon end -->
	</body>
</html>
