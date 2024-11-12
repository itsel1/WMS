<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
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
<script>
	function closePopUp() {
		window.close();
	}
</script>
</head>
<body>
	<table class="noticeTable">
		<!-- 반품 사용 안내서 -->
		<tr>
			<th
				style="font-size: 28px; font-weight: normal; text-align: left; padding: 10px 0 14px 0; background: white;">반품
				사용 안내서</th>
		</tr>
		<tr>
			<td>
				<form>
					<textarea id="notice_text" readonly style="line-height: 1.4;">
ACI는 이용자의 개인정보를 사전 동의 없이 반품회수대행 업무 범위를 초과하여 이용하거나 원칙적으로 이용자의 개인정보를 외부에 공개하지 않습니다.

다만 아래의 경우에는 예외로 합니다.
- 법령의 규정에 의거하거나, 수사 목적으로 법령에 정해진 절차와 방법에 따라 수사기관의 요구가 있는 경우
- 반품회수대행 서비스를 위한 당사자간(ACI의 서비스 위탁 업체) 원활한 의사소통 및 배송, 상담 등 거래여행을 위하여 관련된 정보를 필요 범위 내에서 거래 당사자에게 제공에 동의한 경우</textarea>
				</form>
			</td>
		<tr>
			<td>
				<p>
					<label for="agreement_check"> <input type="checkbox"
						id="agreement_check" name="agreement_check"> <font
						style="font-size: 14px; font-weight: bold;">동의 합니다.</font>
					</label>
				</p>
			</td>
		</tr>
	</table>
	<div class="main_div">
		<div>
			<br>
			<p style="color: #FF0000; font-weight: bold; line-height: 1;">*
				해외로 발송되는 반품것만 입력해주세요</p>
			<p style="color: #FF0000; font-weight: bold; line-height: 1;">*
				국내 회수 및 반품은 입력하지 말아 주세요</p>
		</div>
		<br>
		<div>
			<p style="font-size: 20px; font-weight: bold; color: black;">운송장
				번호 찾기</p>
			<table class="hawbTable">
				<tr>
					<td><form>
							<label for="hawbNum"></label> <input type="text" name="hawbNum"
								id="hawb_input">
						</form></td>
					<td width="100"><form>
							<label for="hawbBtn"></label> <input type="button" name="hawbBtn"
								id="hawb_btn" value="검색" onClick="">
						</form></td>
				</tr>
			</table>
		</div>
		<br> <br>
		<div class="infoDiv">
			<table class="infoTable">
				<tr>
					<th class="tableHeader" colspan="2">반품 담당자 정보</th>
				</tr>
				<tr>
					<td class="td_name">이름</td>
					<td><input type="text" name="manager_name" id="info_input"
						placeholder="입력하세요"></td>
				</tr>
				<tr>
					<td class="td_name">전화번호</td>
					<td><input type="text" name="manager_num" id="info_input"></td>
				</tr>
				<tr>
					<td class="td_name">이메일</td>
					<td><input type="email" name="manager_email" id="info_input"></td>
				</tr>
			</table>
			<br> <br>
			<table class="infoTable">
				<tr>
					<th class="tableHeader" colspan="2">반품 발송 정보</th>
				</tr>
				<tr>
					<td class="td_name">이름</td>
					<td><input type="text" name="return_name" id="info_input"></td>
				</tr>
				<tr>
					<td class="td_name">주소</td>
					<td><input type="text" name="return_addr" id="info_input"></td>
				</tr>
				<tr>
					<td class="td_name">전화번호</td>
					<td><input type="email" name="return_num" id="info_input"></td>
				</tr>
			</table>
			<br> <br>
			<table class="infoTable">
				<tr>
					<th class="tableHeader" colspan="2">해외 도착지 정보</th>
				</tr>
				<tr>
					<td class="td_name">회사명</td>
					<td><input type="text" name="dstn_name" id="info_input"></td>
				</tr>
				<tr>
					<td class="td_name">주소</td>
					<td><input type="text" name="dstn_addr" id="info_input"></td>
				</tr>
				<tr>
					<td class="td_name">전화번호</td>
					<td><input type="email" name="dstn_num" id="info_input"></td>
				</tr>
			</table>
		</div>
		<br> <br> <br>
		<div class="radio_div">
			<form>
				<label for="rtn_div"><font
					style="font-size: 18px; font-weight: bold; color: black;">반송
						구분</font></label> <input type="radio" name="rtn_div" class="rtn_radio">일반배송
				<input type="radio" name="rtn_div" class="rtn_radio">긴급배송
			</form>
		</div>
		<br>
		<div>
			<table class="cancel_info">
				<tr height="38">
					<td class="td_name">취소사유</td>
					<td><input type="radio" name="cncl" id="cncl_radio"><label
						for="cncl">단순변심</label> <input type="radio" name="cncl"
						id="cncl_radio"><label for="cncl">파손</label> <input
						type="radio" name="cncl" id="cncl_radio"><label for="cncl">불량</label>
						<input type="radio" name="cncl" id="cncl_radio"><label
						for="cncl">기타</label></td>
				</tr>
				<tr height="20">
					<td class="td_name">취소사유 상세</td>
					<td><input type="text" name="cncl_text" id="info_input"></td>
				</tr>
			</table>
		</div>
		<br>
		<br>
		<br>
		<br>
		<div>
			<table class="goods_info">
				<colgroup>
					<col width="33%">
					<col width="33%">
					<col width="33%">
				</colgroup>
				<tr>
					<th class="td_name2" colspan="3">상품정보</th>
				</tr>
				<tr>
					<td style="text-align: center;">상품명</td>
					<td style="text-align: center;">상품수량</td>
					<td style="text-align: center;">반품수량</td>
				</tr>
			</table>
		</div>
		<br>
		<br>
		<br>
		<br>
		<div>
			<table class="remand_info">
				<tr>
					<th class="tableHeader" colspan="3">위약반송 정보<small
						style="padding: 6px; color: #FF0000;"> 관부가세 환급시 반드시 작성해
							주세요</small></th>
				</tr>
				<tr>
					<td class="td_name2">관부가세 환급여부</td>
					<td colspan="2"><input type="radio" name="rmnd"
						id="rmnd_radio"><label for="cncl">환급</label> <input
						type="radio" name="rmnd" id="rmnd_radio"><label for="cncl">미환급</label>
					</td>
				</tr>
				<tr>
					<td class="td_name2">반송사유서</td>
					<td style="padding-left: 4px; width: 300px;"><input
						type="file"></td>
					<td style="text-align: left; padding-left: 4px;"><a href="">[반송사유서
							다운로드]</a></td>
				</tr>
				<tr>
					<td class="td_name2">통장사본</td>
					<td style="padding-left: 4px;"><input type="file"></td>
					<td></td>
				</tr>
				<tr>
					<td class="td_name2">네이버반품 캡쳐본</td>
					<td style="padding-left: 4px;"><input type="file"></td>
					<td></td>
				</tr>
				<tr>
					<td class="td_name2">TOCKTOCK 캡쳐본</td>
					<td style="padding-left: 4px;"><input type="file"></td>
					<td></td>
				</tr>
				<tr>
					<td class="td_name2">반품 커머셜</td>
					<td style="padding-left: 4px;"><input type="file"></td>
					<td style="text-align: left; padding-left: 4px;"><a href="">[반품
							커머셜 다운로드]</a></td>
				</tr>
			</table>
		</div>
		<br>
		<br>
		<br>
		<div>
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
					<td><form>
							<textarea id="comm_text" rows="8"></textarea>
						</form></td>
				</tr>
			</table>
			<div class="rtnBtn_div">
				<form>
					<input type="button" value="반품신청" id="rtnBtn" name="rtnBtn"
						onClick="closePopUp()">
				</form>
			</div>
		</div>
	</div>
	<br>
	<br>
	<br>
	<br>
	<br>
	<!-- <footer style="text-align: left; float: right; margin-bottom:60px;">
		<p style="line-height: 1.2; font-size: 12px;">
			주)에이씨아이 월드와이드<br> 서울특별시 강서구 남부순환로 19길 121 (외발산동 217-1) / 사업자등록번호
			: 105-81-52457<br> 고객센터 : 1588-0300, 02)2663-3300 전자상거래
			032)744-9550
		</p>
	</footer> -->
</body>
</html>