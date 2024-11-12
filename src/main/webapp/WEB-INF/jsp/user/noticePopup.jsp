<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp"%>
<style>
	@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap');
	
	body {
		font-family: 'Noto Sans KR', sans-serif;
	}
	
	#main-contents {
		padding: 5px 20px;
		margin: auto;
	}
	
	#body-content {
		display: flex;
		flex-direction: column;
		box-sizing: border-box;
		border: 1px solid #e6e6e6;
		border-radius: 10px;
		padding: 5px;
	}
	
	#body-content div {
		font-size: 14px;
		font-weight: 600;
		margin: 2px 10px;
	}
	
	#body-content p {
		margin-left: 16px;
		font-size: 20px;
		font-weight: 700;
		margin-top: 5px;
	}
	
	#table {
		border: 1px solid #e6e6e6;
		width: 100%;
	}
	
	#table th, td {
		border: 1px solid #e6e6e6;
		text-align: center;
	}
</style>
</head>
<body>
	<div style="text-align:right;padding:5px;">
		<button type="button" id="close-button" style="background:none;border:none;font-weight:600;padding-right:15px;" onclick="self.close();">닫기 X</button>
	</div>
	<div id="main-contents">
		<div id="body-content">
			<p>[공지] WMS 시스템 수출신고관련 업데이트 안내</p><br/>
			<div>안녕하십니까 ACI 월드와이드 입니다.</div>
			<div>귀사의 일익 번창하심을 기원합니다. </div><br/>
			<div>2024년 03월 25일부터 WMS 전자상거래 수출신고 정보가 변경됨을 알려드립니다.</div>
			<div>- 정정 내용 : 기존 오기재 되어있는 명칭 정정 및 수출목록변환 신고 추가</div><br/>
			<div> ※ 전자상거래 수출신고 정보 변경 사항 </div>
			<div>
				<table id="table">
					<thead>
						<tr>
							<th style="width:50%;">변경 전</th>
							<th style="width:50%;">변경 후</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>수출신고 안함</td>
							<td>수출신고 안함 (목록통관)</td>
						</tr>
						<tr>
							<td>정식 수출신고 (대행)</td>
							<td rowspan="3">간이 수출신고 (200불 미만)</td>
						</tr>
						<tr>
							<td>정식 수출신고</td>
						</tr>
						<tr>
							<td>간이 수출신고</td>							
						</tr>
						<tr>
							<td>X</td>
							<td>일반 수출신고 (200불 이상)</td>
						</tr>
						<tr>
							<td>X</td>
							<td>수출목록 변환신고</td>
						</tr>
					</tbody>
				</table>
			</div>
			<br/>
			<div>전자상거래 수출신고 정보 변경에 따라 주문등록 엑셀 양식이 수정되었습니다. </div>
			<div>신규 Excel 양식을 다운로드 하시어 주문 등록 부탁드립니다.</div>
			<br/><br/>
			<div>감사합니다.</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
	
	<script type="text/javascript">

	</script>
</body>
</html>