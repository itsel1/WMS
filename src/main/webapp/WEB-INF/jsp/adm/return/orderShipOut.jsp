<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/head.jsp"%>
<style type="text/css">
	#resetLink{
		text-decoration: none;
		margin-left: 3px;
	}
	
	#resetLink:hover {
		font-size: 21px;
		color: #527d96;
		margin-left: 0;
	}
	
	#scanBarcodeNo {
		width: 100%;
		height: 100px;
		font-size: 50px;
		font-weight: 600;
		color: DarkRed;
	}
	
	#scanBarcodeNo:focus {
		outline: none !important;
		border-color: darkRed;
	}
	
	#noticeText {
		font-weight: 500 !important;
		font-size: 20px;
		color: #527d96;
		height: 32px;
		margin-top: 10px;
		text-align: right;
	}
	
	#scanBarcodeRst {
		width: 100%;
		height: 100px;
		font-size: 50px;
		font-weight: 600;
		color: #527d96;
	}
	
	.tbl-return {
		margin: auto;
		width: 99%;
		box-shadow: 0px 0px 5px rgba(82, 125, 150, 0.3);
	}
	
	.tbl-return table {
		width: 100%;
		table-layout: fixed;
	}
	
	.tbl-return .tbl-header {
		background-color: #527d96;
		box-shadow: 0px -1px 3px rgba(82, 125, 150, 0.6);
	}
	
	.tbl-return .tbl-content {
		max-height: 230px;
		height: auto;
		overflow-x: auto;
		margin-top: 0px;
	}
	
	.tbl-return th {
		text-align: left;
		font-weight: 600;
		font-size: 14px;
		color: #fff;
		text-transform: uppercase;
		text-align: center;
		padding: 10px;
	}
	
	.tbl-return td {
		padding: 10px;
		text-align: center;
		vertical-align: middle;
		font-weight: 500;
		font-size: 12px;
		border-bottom: 1px solid #e8e8e8;
	}
	
	.tbl-content::-webkit-scrollbar {
		width: 6px;
	}
	
	.tbl-content::-webkit-scrollbar-track {
		-webkit-box-shadow: inset 0 0 6px rgba(82, 125, 150, 0.3);
	}
	
	.tbl-content::-webkit-scrollbar-thumb {
		-webkit-box-shadow: inset 0 0 6px rgba(82, 125, 150, 1);
	}
	
	input[disabled].borderNone {
		border: none !important;
		text-align: center;
	}
	
	input[disabled].nowScanNo {
		background-color: rgb(255,255,202) !important;
		font-weight: bold;
	}
	
	input[disabled].scanNo {
		background-color: white !important;
		font-weight: bold;
	}
	
	input[disabled].redBg {
		background-color: rgb(255,237,237) !important;
		font-weight: bold;
	}
	
	input[disabled].greenBg {
		background-color: rgb(237,255,237) !important;
		font-weight: bold;
	}
	
	#whout-contents {
		border-top: 1px solid #e2e2e2;
	}
	
	
	#stockOut-table {
		width: 60%;
		margin: auto;
		margin-top: 5px;
	}
	
	#stockOut-table th, td {
		text-align: center;
		padding: 10px;
	}
	
	#stockOut-table th {
		font-size: 16px;
		font-weight: 600;
	}
	
	#boxSize-div {
		width: 100%;
		padding-top: 10px;
		display: inline-flex;
		justify-content: center;
		align-content: center;
		align-items: center;
	}
	
	#boxSize-div label {
		text-align: center;
	}
	
	#boxSize-div input {
		width: 100px;
		height: 50px;
		font-size: 25px;
		font-weight: 600;
		border-top: none;
		border-left: none;
		border-right: none;
	}
	
	#boxSize-div select {
		width: 80px;
		height: 40px;
		font-size: 20px;
		font-weight: 500;
		margin-left: 10px;
	}
	
	.size-label {
		margin-right: 10px;
		margin-left: 10px;
		font-weight: 600;
		font-size: 18px;
	}
	
	#wtUnit {
		margin-right: 30px;
	}
	
	#transCode option[value=""][disabled] {
		display: none;
	}
	
</style>
</head>
<title>검수 작업</title>
<body class="no-skin">
	<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp"%>
	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.loadState('main-container')
			} catch (e) {
			}
		</script>
		<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<div class="breadcrumbs ace-save-state" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> Home</li>
						<li>반품</li>
						<li class="active">반품 출고 작업</li>
					</ul>
				</div>
				<div class="page-content">
					<div class="page-header">
						<h1>출고 작업</h1>
					</div>
					<form id="whoutForm">
						<div style="width:100%;clear:both;overflow-y:hidden;height:260px;">
							<input type="hidden" id="nno" name="nno" value="${nno}">
							<div style="width:40%;float:left;padding-right:10px;">
								<input type="text" id="scanBarcodeNo">
								<input type="text" id="scanBarcodeRst" readonly>
								<p id="noticeText"><a href="/mngr/return/orderStockOutReset?nno=${nno}" id="resetLink">스캔 데이터 초기화</a></p>
							</div>
							<div style="width:60%;float:right;">
								<div style="width:100%;" id="detail-contents"></div>
							</div>
						</div>
						<br>
						<div style="width:100%;margin:auto;" id="whout-contents"></div>
						
						<div style="margin-top:10px;display:none;" id="calc-div">
							<div id="boxSize-div">
								<label class="size-label">BOX<br>수량</label>
								<input type="number" id="boxCnt" name="boxCnt" value="1" min="1" max="99">
								<label class="size-label">실 무게<br>W/T(A)</label>
								<input type="text" class="wta" id="wta" name="wta" floatOnly>
								<label class="size-label">부피 무게<br>W/T(C)</label>
								<input type="text" class="wtc" id="wtc" name="wtc" floatOnly readonly>
								<select name="wtUnit" id="wtUnit">
									<option value="KG">KG</option>
									<option value="LB">LB</option>
								</select>
								<label class="size-label">가로<br>width</label>
								<input type="text" class="width" name="width" id="width" floatOnly>
								<label class="size-label">세로<br>length</label>
								<input type="text" class="length" name="length" id="length" floatOnly>
								<label class="size-label">높이<br>height</label>
								<input type="text" class="height" name="height" id="height" floatOnly>
								<select name="dimUnit" id="dimUnit">
									<option value="CM">CM</option>
									<option value="IN">INCH</option>
								</select>
								<label class="size-label">per</label>
								<input type="text" class="per" name="per" id="per" numberOnly value="6000">
							</div>
							<br>
							<div style="width:100%;text-align:center;margin-top:30px;">
								<select id="transCode" name="transCode">
									<option value="" disabled selected>※배송사 선택※</option>
									<c:forEach items="${transCodeList}" var="transCodeList">
										<option value="${transCodeList.transCode}">${transCodeList.transCode} / ${transCodeList.transName}</option>
									</c:forEach>
								</select>
							</div>
							<br>
							<div style="width:100%;text-align:center;margin-top:30px;">
								<input type="button" class="btn btn-danger" id="cancel-btn" value="취소">
								<input type="button" class="btn btn-primary" id="regist-btn" value="출고">
							</div>
						</div>
						<div style="width:100%;text-align:center;display:none;margin-top:30px;" id="force-btn-div">
							<input type="button" class="btn btn-sm btn-danger" id="force-btn" value="강제출고">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- Footer Start -->
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
	<!-- Footer End -->

	<!--[if !IE]> -->
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<!-- <![endif]-->
	<script type="text/javascript">
		if ('ontouchstart' in document.documentElement)
			document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>" + "<"+"/script>");
	</script>
	<script src="/assets/js/bootstrap.min.js"></script>
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


	<script type="text/javascript">
		jQuery(function($) {
			var nno = $("#nno").val();
			$(document).ready(function() {
				$("#14thMenu-2").toggleClass('open');
           		$("#14thMenu-2").toggleClass('active'); 
           		$("#returnMenu-3").toggleClass('active');   
				showDetail('', nno);
				$("#scanBarcodeNo").focus();
			});

			$("#scanBarcodeNo").on('keyup', function(e) {
				if (e.keyCode === 13) {
					var audio = new Audio();
					var stockNo = $(this).val();
					
					$.ajax({
						url : '/mngr/return/orderShipOutChk',
						type : 'POST',
						beforeSend : function(xhr) {
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data : {stockNo: stockNo, nno: nno},
						error : function(xhr, status) {
							alert("시스템 오류가 발생 하였습니다. 다시 시도해 주세요.");
						},
						success : function(response) {
							console.log(response.requestSt);
							if (response.rstStatus == 'SUCCSESS') {
								audio.src = '/js/pass.mp3';
								audio.play();
								showDetail(stockNo, nno);
								$("#scanBarcodeRst").css("font-size", "50px");
								$("#scanBarcodeRst").val("적용 되었습니다.");
								$("#scanBarcodeRst").addClass('blue');
								$("#scanBarcodeRst").removeClass('red');
								$("#scanBarcodeNo").val("");
								$("#scanBarcodeNo").focus();
							} else {
								audio.src = '/js/stop.mp3';
								audio.play();
								$("#scanBarcodeRst").css("font-size", "30px");
								$("#scanBarcodeRst").val(response.rstMsg);
								$("#scanBarcodeRst").addClass('red');
								$("#scanBarcodeRst").removeClass('blue');
								$("#scanBarcodeNo").val("");
								$("#scanBarcodeNo").focus();
							}
						}
					})
				}
			});

			$(document).on('keyup', 'input:text[numberOnly]', function(e) {
				$(this).val($(this).val().replace(/[^0-9]/g, ""));
			});

			$(document).on('keyup', 'input:text[floatOnly]', function(e) {
				$(this).val($(this).val().replace(/[^0-9.]/g, ""));
			});

			$("#boxCnt").on('keydown', function(e) {
				var key = e.key;

				if (!/^[0-9]$/.test(key)) {
					e.preventDefault();
				}
			});

			$("#force-btn").on('click', function(e) {
				if (confirm("출고 수량과 상품 수량이 다릅니다.\n그래도 진행 하시겠습니까?")) {
					$("#calc-div").show();
					$("#force-btn-div").hide();
				}
			});

			$("#cancel-btn").on('click', function(e) {
				location.href = '/mngr/return/orderStockOutReset?nno='+nno+'&actWork=cncl';
			});

			$("#width, #length, #height, #per").on('change', function(e) {
				wtCalc();
			});

			$("#regist-btn").on('click', function(e) {
				if ($("#wta").val() == "") {
					alert("실 무게를 입력해 주세요");
					$("#wta").focus();
					return;
				}

				if ($("#width").val() == "" || $("#length").val() == "" || $("#height").val() == "") {
					$("#wtc").val("0");
					$("#width").val("0");
					$("#length").val("0");
					$("#height").val("0");
				}

				var formData = $("#whoutForm").serialize();
				$.ajax({
					url : '/mngr/return/orderShipOut',
					type : 'POST',
					beforeSend : function(xhr) {
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data : formData,
					error : function(xhr, status) {
						alert("처리 중 오류가 발생 하였습니다.\n시스템 관리자에게 문의해 주세요.");
					},
					success : function(response) {
						if (response.STATUS == 'SUCCESS') {
							alert("출고 처리 되었습니다.");
							location.href = "/mngr/return/hawbNoList";
						} else {
							alert("출고 처리 중 오류가 발생 하였습니다.");
							return false;
						}
					}
				})
			});
		});

		
		function showDetail(stockNo, nno) {
			$.ajax({
				url : '/mngr/return/orderShipOutDetail',
				type : 'POST',
				data : {stockNo: stockNo, nno: nno},
				beforeSend : function(xhr) {
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				error : function(xhr, status) {
					alert("시스템 오류가 발생 하였습니다. 다시 시도해 주세요.");
				},
				success : function(response) {
					var chk = "F";
					var parser = new DOMParser();
					var htmlDoc = parser.parseFromString(response, 'text/html');
					var whoutDiv = htmlDoc.querySelector("#stockOut-div").innerHTML;
					var listDiv = htmlDoc.querySelector("#tbl-div").innerHTML;

					$("#detail-contents").html(listDiv);
					$("#whout-contents").html(whoutDiv);

					$("#calc-div").hide();
					$("#force-btn-div").hide();
					
					$("[name='itemStatus']").each(function() {
						if ($(this).val() === 'T') {
							chk = 'T';
						} else {
							chk = 'F';
						}
					});

					$("#wta").val("");
					$("#wtc").val("");
					$("#width").val("");
					$("#length").val("");
					$("#height").val("");
					
					var subNo = $("#subNo").val();
					var scanCnt = $("#scanCnt").val();

					if (subNo != '0') {
						if (chk === 'T') {
							$("#force-btn-div").hide();
							$("#calc-div").show();
						} else {
							$("#force-btn-div").show();
						}
					} else {
						if (scanCnt != '0') {
							if (chk === 'T') {
								$("#force-btn-div").hide();
								$("#calc-div").show();
							} else {
								$("#force-btn-div").show();
							}
						}
					}
				}
			})
		}
		// function showDetail() end

		function wtCalc() {
			var width = $("#width").val();
			var length = $("#length").val();
			var height = $("#height").val();
			var per = $("#per").val();
			var result = 0;
			result = width*length*height/per;
			result = result.toFixed(1);
			$("#wtc").val(result);
		}
		// function wtCalc() end

		

		/*
		function checkboxClicked() {
			var checkboxes = document.querySelectorAll('tbody input[type="checkbox"]');
			var allChecked = true;
			for (var i = 0; i < checkboxes.length; i++) {
				if (!checkboxes[i].checked) {
					allChecked = false;
					break;
				}
			}
			document.getElementById('checkAll').checked = allChecked;

			var row = this.closest('tr');
			if (this.checked) {
				row.style.backgroundColor = 'WhiteSmoke';
			} else {
				row.style.backgroundColor = '';
			}
		}

		var checkboxes = document.querySelectorAll('tbody input[type="checkbox"]');
		for (var i = 0; i < checkboxes.length; i++) {
			checkboxes[i].addEventListener('click', checkboxClicked);

			if (checkboxes[i].checked) {
				var row = checkboxes[i].closest('tr');
				row.style.backgroundColor = 'WhiteSmoke';
			}
		}

		document.getElementById('checkAll').addEventListener('click', function() {
			var checkboxes = document.querySelectorAll('tbody input[type="checkbox"]');
			for (var i = 0; i < checkboxes.length; i++) {
				checkboxes[i].checked = this.checked;

				var row = checkboxes[i].closest('tr');
				if (this.checked) {
					row.style.backgroundColor = 'WhiteSmoke';
				} else {
					row.style.backgroundColor = '';
				}
			}
		});

		document.getElementById('scanBarcodeNo').addEventListener('keyup', function(event) {
			if (event.keyCode === 13) {
				var searchText = this.value;
				var rows = document.querySelectorAll('tbody tr');
				for (var i = 0; i < rows.length; i++) {
					var td = rows[i].getElementsByTagName('td')[1];
					var stockNumber = td.textContent || td.innerText;
					if (stockNumber === searchText) {
						var checkbox = rows[i].querySelector('input[type="checkbox"]');
						checkbox.checked = true;
						rows[i].style.backgroundColor = 'WhiteSmoke';
					} 
				}

				this.value = '';
			}
		});
		*/
	</script>
</body>
</html>