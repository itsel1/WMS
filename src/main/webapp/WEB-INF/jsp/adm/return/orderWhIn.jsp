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
	#header-table {
		width: 100%;
		box-shadow: 0px 0px 5px rgba(82, 125, 150, 0.3);
	}
	
	#header-table th {
		color: #fff;
		text-align: center;
		padding-top: 10px;
		padding-bottom: 10px;
		border-collapse: collapse;
		font-size: 13px;
		font-weight: 700;
		background-color: #527d96;
		box-shadow: 0px -1px 3px rgba(82, 125, 150, 0.6);
	}
	
	#header-table td {
		border: 1px solid #F7FBFF;
		border-collapse: collapse;
		font-size: 13px;
		padding-top: 5px;
		padding-bottom: 5px;
		text-align: center;	
	}
	
	.inputNumber {
		width: 60px;
		/* height: 32px; */
		height: 28px;
		font-size: 22px;
	}
	
	input[name="itemChk[]"] {
		position: relative;
		top: 3px;
	}
	
	.td_content {
		padding-left: 20px;
	}

	.radio-label {
		font-size: 18px;
		line-height: 2rem;
		margin-right: 10px;
	}
	.radio-label span {
		position: relative;
		top: 3px;
		font-weight: 600;
		color: #527d96;
	}
	
	input[name="whInRst"] {
		appearance: none;
		border: max(2px, 0.1em) solid #527d96;
		border-radius: 50%;
		width: 1.25em;
		height: 1.25em;
		transition: border 0.1s ease-in-out;
		outline: none;
		vertical-align: middle;
	}
	
	input[name="whInRst"]:checked {
		border: 0.4em solid #527d96;
		outline: none;
	}
	
	input[name="whInRst"]:focus-visible {
		outline-offset: max(2px, 0.1em);
		outline: max(2px, 0.1em) dotted #527d96;
	}
	
	input[name="whInRst"]:hover {
		cursor: pointer;
	}
	
	input[name="whInRst"]:hover + span {
		cursor: pointer;
	}
	
	input[name="whInRst"]:disabled {
		background: lightgray;
		box-shadow: none;
		opacity: 0.7;
		cursor: not-allowed;
	}
	
	input[name="whInRst"]:disabled + span {
		opacity: 0.7;
		cursor: not-allowed;
	}
	
	#whInTable {
		width: 95%;
		border-left: 1px solid #e2e2e2;
	}
	
	#whInTable th {
		font-size: 18px;
		font-weight: 600;
		width: 15%;
		text-align: center;
		color: #527d96;
		padding-top: 10px;
		padding-bottom: 10px;
		padding-right: 5px;
	}
	
	#whInTable td {
		width: 85%;
		padding: 0;
	}
	
	#whInTable td input[type="text"] {
		width: 80%;
		height: 32px;
	}
	
	#preview {
		display: flex;
		flex-wrap: nowrap;
		height: 0;
		overflow-x: auto;
		overflow-y: hidden;
		align-items: center;
		margin-top: 10px;
		transition: height 0.3s ease-in-out;
  	}
  	
  	#preview > img {
		object-fit: contain;
		max-height: 100%;
		max-width: 100%
	}
	
	.close-button {
		margin: 0;
		padding: 0;
		width: 20px;
		height: 20px;
		background: none;
		border: none;
		outline: none;
		text-align: center;
		font-weight: bold;
		font-size: 16px;
		cursor: pointer;
	}
	
	#registBtn {
		margin-left: 2px;
		width: 100px;
		height: 40px;
		border-radius: 2px;
		cursor: pointer;
		color: #fff;
		background-color: #527d96;
		border: none;
		box-shadow: 0 4px 16px rgba(82, 125, 150, 0.3);
		transition: 0.3s;
	}
	
	#registBtn:hover {
		background-color: rgba(82, 125, 150, 0.9);
		box-shadow: 0 2px 4px rgba(82, 125, 150, 0.6);
	}
	
	.inputWt {
		width: 80px !important;
		height: 32px;
		margin-right: 5px;
	}
	
	select {
		width: 80px;
		height: 32px;
		margin-right: 5px;
	}
	
	.labels {
		font-weight: 600;
		font-size: 20px;
		margin-top: 8px;
		color: #527d96;
	}
	
	#labels_sm {
		font-size: 12px;
		color: tomato;
		margin-left: 5px;
	}
	
</style>
</head>
<title>반품 리스트</title>
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
						<li class="active">입고 검수 작업</li>
					</ul>
				</div>
				<div class="page-content">
					<div class="page-header">
						<h1>검수 작업</h1>
					</div>
					<div style="width:100%;clear:both;">
						<table id="header-table">
							<tr>
								<th style="border-right:1px solid #fff;width:10%;">주문번호</th>
								<th style="border-right:1px solid #fff;width:10%;">원송장번호</th>
								<th style="border-right:1px solid #fff;width:10%;">반송장번호</th>
								<th style="border-right:1px solid #fff;width:10%;">수취인 ID</th>
								<th style="border-right:1px solid #fff;width:10%;">수취인 TEL</th>
								<th style="border-right:1px solid #fff;width:25%;">수취인 주소</th>
								<th style="width:25%;">입고 요청사항</th>
							</tr>
							<tr>
								<td>${orderInfo.orderNo}</td>
								<td>${orderInfo.koblNo}</td>
								<td>${orderInfo.trkNo}</td>
								<td>${orderInfo.userId}</td>
								<td>${orderInfo.cneeTel}</td>
								<td>${orderInfo.cneeAddr} ${orderInfo.cneeAddrDetail}</td>
								<td>
									<c:if test="${empty orderInfo.whMsg}">
										-
									</c:if>
									<c:if test="${!empty orderInfo.whMsg}">
										${orderInfo.whMsg}
									</c:if>
								</td>
							</tr>
						</table>
						<br/>
						<input type="hidden" name="nno" id="nno" value="${orderInfo.nno}">
						<input type="hidden" name="userId" id="userId" value="${orderInfo.userId}">
						<input type="hidden" name="trkCom" id="trkCom" value="${orderInfo.trkCom}">
						<input type="hidden" name="trkNo" id="trkNo" value="${orderInfo.trkNo}">
						<input type="hidden" name="totalItemCnt" id="totalItemCnt" value="${orderInfo.totalItemCnt}">
						<form id="stockForm" enctype="multipart/form-data">
							<div style="width:100%;clear:both;overflow-y:hidden;">
								<div style="width:50%;float:left;padding:10px;">
									<input type="hidden" id="totalItemCnt" value="${fn:length(itemList)}">
									<c:forEach items="${itemList}" var="itemList" varStatus="status">
										<div style="width:100%;box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16);margin-bottom:10px;" class="listItem">
										<!-- <div style="width:100%;box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16);margin-bottom:10px;" class="listItem" onclick="toggleCheckbox(this)"> -->
											<input type="hidden" name="itemCnt[]" value="${itemList.itemCnt}"> 
											<table id="contents-table">
												<tr>
													<td rowspan="5" style="width:20%">
													
														<%-- <img src="/image/no-image.png"  class="editable img-responsive urlImg">
														<c:if test="${!empty itemList.itemImgUrl}">
														<img class="editable img-responsive urlImg" src="${itemList.itemImgUrl}" loading="lazy">
														</c:if>
														<c:if test="${empty itemList.itemImgUrl}">
														<img src="/image/no-image.png"  class="editable img-responsive urlImg">
														</c:if> --%>
													</td>
													<th style="width:10%;text-align:right;font-weight:600;">
														<%-- <input type="checkbox" name="itemChk[]" value="${itemList.subNo}" id="itemChk${itemList.subNo}">
														<label for="itemChk${itemList.subNo}">상품${itemList.subNo}</label> --%>
														상품 ${itemList.subNo}
													</th>
													<td class="td_content" style="width:60%;font-size:18px !important;color:#0000CD;font-weight:600;">
														${itemList.itemDetail}
														<c:if test="${!empty itemList.brand}">
															<font style="font-size:14px !important;color:#527d96;font-weight:500;">| ${itemList.brand}</font>
														</c:if>
													</td>
												</tr>
												<tr>
													<th style="text-align:right;">제조국</th>
													<td class="td_content">${itemList.makeCntry}</td>
												</tr>
												<tr>
													<th style="text-align:right;">상품단가</th>
													<td class="td_content">
														${itemList.unitValue}
														<c:if test="${!empty itemList.unitCurrency}">
															(${itemList.unitCurrency})
														</c:if>
													</td>
												</tr>
												<tr>
													<th style="text-align:right;">입고 메모</th>
													<td class="td_content"><input type="text" name="whMemo[]" style="width:90%;height:28px;"></td>
												</tr>
												<tr>
													<th style="text-align:right;">입고 수량</th>
													<td class="td_content"><input type="text" class="inputNumber" name="whInCnt[]" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"> / ${itemList.itemCnt}</td>
												</tr>
											</table>
										</div>
									</c:forEach>
								</div>
								<div style="width:50%;float:right;padding-left:20px;">
									<table id="whInTable">
										<tr>
											<th colspan="2" style="text-align:left !important;padding-left:15px;">
												<label class="radio-label">
													<input type="radio" name="whInRst" value="WO">
													<span>정상 입고</span>
												</label>
												<label class="radio-label">
													<input type="radio" name="whInRst" value="WF">
													<span>검수 불합격</span>
												</label>
											</th>
										</tr>
										<tr class="hide" id="wf_tr">
											<th>불합격 사유</th>
											<td>
												<select id="wf_reason" style="width:100px;">
													<option value="" selected>::선택::</option>
													<option value="WF1">상품 파손</option>
													<option value="WF2">기타</option>
												</select>
												<input type="text" id="wf_reason_detail" style="width:200px;" readonly>
											</td>
										</tr>
										<tr>
											<th>랙 정보</th>
											<td><input type="text" name="rackCode" id="rackCode"></td>
										</tr>
										<tr>
											<th>사이즈</th>
											<td>
												<input type="text" name="width" class="inputWt" onKeyup="this.value=this.value.replace(/[^.0-9]/g,'');" placeholder="width">
												<input type="text" name="height" class="inputWt" onKeyup="this.value=this.value.replace(/[^.0-9]/g,'');" placeholder="height">
												<input type="text" name="length" class="inputWt" onKeyup="this.value=this.value.replace(/[^.0-9]/g,'');" placeholder="length"> 
												
												<select name="per" style="font-size:14px;">
													<option value="5000">5000</option>
													<option value="6000">6000</option>
												</select>
											</td>
										</tr>
										<tr>
											<th>단위</th>
											<td>
												<select name="wtUnit" style="font-size:14px;">
													<option value="KG">KG</option>
													<option value="LB">LB</option>
												</select>
												<select name="dimUnit" style="font-size:14px;">
													<option value="CM">CM</option>
													<option value="IN">IN</option>
												</select>
											</td>
										</tr>
									</table>
								</div>
							</div>
							<div style="padding-left:10px;">
								<label class="labels" for="image-upload">
									검수 사진
									<small id="labels_sm">* 이미지 재 업로드 시, 기존에 업로드한 이미지 자동 삭제. 이미지 다중 등록 시, Ctrl + 파일 클릭하여 등록</small>
								</label>
								<input type="file" name="whInImgs[]" id="image-upload" accept="image/*" multiple style="width:300px;">
								<div id="preview"></div>
							</div>
						</form>
						<div style="width:100%;margin-top:20px;margin-left:10px;">
							<input type="button" id="registBtn" value="입고 확인">
						</div>
						<form id="whInRstForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						</form>
					</div>
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
		var imageUpload = document.getElementById('image-upload');
		var preview = document.getElementById('preview');
		var filesData = [];
		
		imageUpload.addEventListener('change', function(e) {
			preview.innerHTML = '';
			filesData = [];

			var files = Array.from(e.target.files);
			files.forEach(function(file) {
				var reader = new FileReader();

				reader.onload = function(e) {
					var image = new Image();
					image.src = e.target.result;
					image.addEventListener('load', function() {
			          	const ratio = image.width / image.height;
				        const height = preview.clientHeight - 20;
				        const width = height * ratio;
				        image.style = `height: ${height}px; width: ${width}px; margin: 0 5px;`;
				    });

					var closeButton = document.createElement('button');
					closeButton.innerHTML = 'X';
					closeButton.className = 'close-button';
					closeButton.addEventListener('click', function() {
						preview.removeChild(image);
						preview.removeChild(closeButton);
						filesData = filesData.filter(function(item) {
							return item !== file;
						});
						updatePreviewHeight();
					});

					preview.appendChild(image);
					preview.appendChild(closeButton);
					filesData.push(file);
					updatePreviewHeight();
				};
				reader.readAsDataURL(file);
			});
		});

		function updatePreviewHeight() {
			if (preview.children.length > 0) {
				preview.style.height = '150px';
			} else {
				preview.style.height = '0px';
			}
		}

		jQuery(function($) {
			$(document).ready(function() {
			});

			$("input[name='whInRst']").on('change', function() {
				if ($("input[name='whInRst']:checked").val() == 'WF') {
					$("#wf_tr").removeClass('hide');
				} else {
					$("#wf_tr").addClass('hide');
				}
			})

			$("#wf_reason").on('change', function() {
				if ($(this).val() == 'WF1') {
					$("#wf_reason_detail").attr("readonly", true);
				} else {
					$("#wf_reason_detail").attr("readonly", false);
				}
			});

			/* $("#registBtn").click(function() {
				var checkedItems = [];
				var whInCntValues = [];
				
				var isChecked = $("input[name='whInRst']:checked").length > 0;

				if (!isChecked) {
					alert("정상 입고 또는 검수 불합격 중 한 가지를 선택해 주세요.");
					return;
				} else {

					if ($("input[name='whInRst']:checked").val() == 'WF') {
						if ($("#wf_reason option:selected").val() == '') {
							alert("불합격 사유를 선택해주세요.");
							return;
						} else if ($("#wf_reason option:selected").val() == 'WF2') {
							if ($("#wf_reason_detail").val() == '') {
								alert("불합격 상세 사유를 입력해 주세요.");
								return;
							}
						}
					} 
					
					if ($("#rackCode").val() == "") {
						$("#rackCode").focus();
						return;
					}
					
					var chkItemCnt = $("input[name='itemChk[]']:checked");

					if (chkItemCnt.length == 0) {
						alert("선택된 상품이 없습니다.");
						return;
					} else {
						chkItemCnt.each(function() {
							var listItem = $(this).closest('.listItem');
							var itemCntInput = listItem.find('input[name="itemCnt[]"]');
							var whInCntInput = listItem.find('input[name="whInCnt[]"]');

							var itemCntValue = itemCntInput.val();
							var whInCntValue = whInCntInput.val();

							if (whInCntValue === '') {
								whInCntInput.focus();
								return;
							}

							if (whInCntValue > itemCntValue) {
								alert("입고 수량이 주문 수량보다 많습니다.");
								whInCntInput.focus();
								return;
							}

							if (whInCntValue === '0') {
								alert("입고 수량을 다시 입력해 주세요.");
								whInCntInput.focus();
								return;
							}

							checkedItems.push($(this).val());
							whInCntValues.push(whInCntValue);
						});
						
						var whInResult = $("input[name='whInRst']:checked").val();
						console.log(filesData);

						var formData = new FormData();
						for (var i = 0; i < filesData.length; i++) {
							formData.append('fileList', filesData[i]);
						}

						for (var i = 0; i < checkedItems.length; i++) {
							formData.append('subNoList', checkedItems[i]);
						}

						for (var i = 0; i < whInCntValues.length; i++) {
							formData.append('whInCntList', whInCntValues[i]);
						}

						formData.append('whInResult', whInResult);
						formData.append('whMemo', $("#whMemo").val());
						formData.append('rackCode', $("#rackCode").val());

						if ($("input[name='width']").val() == '') {
							var userWidth = "0";
						}

						if ($("input[name='height']").val() == '') {
							var userHeight = "0";
						}

						if ($("input[name='length']").val() == '') {
							var userLength = "0";
						}
						
						formData.append('userWidth', userWidth);
						formData.append('userHeight', userHeight);
						formData.append('userLength', userLength);
						formData.append('per', $("select[name='per']").val());
						formData.append('wtUnit', $("select[name='wtUnit']").val());
						formData.append('dimUnit', $("select[name='dimUnit']").val());
						formData.append('nno', $("#nno").val());
						formData.append('userId', $("#userId").val());
						formData.append('trkCom', $("#trkCom").val());
						formData.append('trkNo', $("#trkNo").val());
						formData.append('totalItemCnt', $("#totalItemCnt").val());
						formData.append('whStatus', $("#wf_reason option:selected").val());
						formData.append('whStatusDetail', $("#wf_reason_detail").val());

						$.ajax({
							url : '/mngr/return/whInProc',
							type : 'POST',
							beforeSend : function(xhr)
							{
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
					        processData: false,
					        contentType: false,
					        data : formData,
							success : function(data) {
								console.log(data);
								if (data.STATUS == 'S10') {
									location.href = "/mngr/return/whInResult?nno="+data.NNO+'&chkCnt='+data.ITEMCNT;
								} else {
									alert("처리 중 오류가 발생 하였습니다.");
									return false;
								}
							},
							error : function(xhr, status) {
								alert("시스템 오류가 발생 하였습니다. 다시 시도해 주세요.");
							}
						});
						
					}
					
				}

			}); */
		});

		function toggleCheckbox(element) {
			var checkbox = element.querySelector('input[type="checkbox"]');
			var inputField = element.querySelector('input[name="whInCnt[]"]');
			
			if (inputField === document.activeElement) {
				return;
			}
			checkbox.checked = !checkbox.checked;
			
			if (checkbox.checked) {
				element.style.boxShadow = "0 0 5px rgba(0, 0, 0, 0.7)";
			} else {
				element.style.boxShadow = "0 3px 6px rgba(0, 0, 0, 0.16)";
			}
		}
	</script>
</body>
</html>