<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
.padding0 {
	padding: 0px !important;
}

.colorBlack { 
	color: #000000 !important;
}

.table>tbody>tr>td {
	vertical-align: middle !important;
}

.profile-info-name {
	padding: 0px;
	color: black !important;
	text-align: center !important;
}

.profile-user-info-striped {
	border: 0px solid white !important;
}
/* .profile-user-info-striped{border: 0px solid white !important;}
	  .profile-info-name {padding:6px 15px; width:0px; color:black !important; background-color: unset !important;} */
.profile-user-info {
	width: 100% !important;
	margin: 0px;
}

/* .col-xs-12 .col-sm-12 {
	padding: 0px !important;
}
 */
.tdBackColor {
	background-color: #FFFFFF;
}

.tdHeader {
	border: 1px solid black;
	padding: 0px !important;
}

.tdHeaderB {
	border: 1px solid black;
	border-bottom: none;
}

.tdHeaderT {
	border: 1px solid black;
	border-top: none;
}

.tdHeaderL {
	border: 1px solid black;
	border-left: none;
	border-right: none;
}

.tdHeaderR {
	border: 1px solid black;
	border-right: none;
	border-left: none;
}

.tdHeaderTR {
	border: 1px solid black;
	border-top: none;
}

@media ( min-width :768px) {
	.tdHeaderB {
		border: 1px solid black;
		border-bottom: none;
	}
	.tdHeaderT {
		border: 1px solid black;
		border-top: none;
	}
	.tdHeaderL {
		border: 1px solid black;
		border-left: none;
	}
	.tdHeaderR {
		border: 1px solid black;
		border-right: none;
	}
	.tdHeaderTR {
		border: 1px solid black;
		border-right: none;
		border-top: none;
	}
}

@media only screen and (max-width:480px) {
	.hidden-480 {
		display: none !important;
	}
}

@media only screen and (max-width:480px) {
	.hidden-489 {
		display: inline!important;
	}
}
</style>
	<!-- basic scripts -->

		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script src="assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/assets/js/bootstrap.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>

	<!-- basic scripts End-->
	</head> 
	<title>사입</title>
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
								사입
							</li>
							<li class="active">주문 등록</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<form id="mainForm" name="mainForm" action="/mngr/takein/takeinOrderListTmp">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input type="hidden" name="nno" id="nno" value="">
							<input type="hidden" name="userId" id="userId" value="">
						<div class="page-header">
							<h1>
								사입 주문 등록
							</h1>
						</div>
						<table class="table table-bordered" style="width: 1000px;">
							<thead>
							<tr>
								<th class="center colorBlack" style="width:100px;">Trans Com</th>
								<td style="padding:0px;width:180px;height:40px;">
									<select style="width:100%;height:100%;" name="transCode" id="transCode">
										<option value="">::전체::</option>
										<c:forEach items="${transComList}" var="transComList" varStatus="status">
											<option value="${transComList.transCode}"
												<c:if test="${parameterInfo.transCode eq transComList.transCode}">selected</c:if>
											>${transComList.transName}</option>
										</c:forEach>
										<!-- <option>CJ</option>
										<option></option> -->
									</select>
								</td>
								<th class="center colorBlack" style="width:100px;height:40px;">UserID</th>
								<td style="padding:0px;width:180px;height:40px;">
									<input style="width:100%;height:100%;" name="userId" type="text" value="${parameterInfo.userId}">
								</td>
								<th class="center colorBlack" style="width:150px;">OrderNo</th>
								<td style="padding:0px;width:180px;height:40px;">
									<input style="width:100%;height:100%;" type="text" name="orderNo" value="${parameterInfo.orderNo}">
								</td>
								<td style="width:60px;height:40px;">
									<input type="submit" value="조회">
								</td>
							</tr>
							</thead>
						</table>
							<div style="text-align: left;width:45%;float:left">
								<!-- <button type="button" class="btn btn-white btn-xs" id="registBtn" name="registBtn">주문 등록하기</button> -->
								<button type="button" class="btn btn-white btn-xs" id="registBtn2" name="registBtn2">주문 등록하기</button>
								<button type="button" class="btn btn-white btn-xs" id="excelSampleDown">Excel Sample</button>
							</div>
							<div style="text-align: right;width:45%;float:right">
								<button type="button" class="btn btn-danger btn-xs" id="delBtn" name="delBtn">주문 삭제</button>
								<button type="button" class="btn btn-primary btn-xs" onclick="fn_takeinBlScan()">등록 완료</button>
							</div>
							
							<input type="hidden" value="" id="delTarget" name="delTarget"/>
							<input type="hidden" value="" id="delTargetUser" name="delTargetUser"/>
						
							<br>
							<br>
							<div id="table-contents" class="table-contents" style="overflow: auto;">
								<div class="display" >
									<table id="dynamic-table" class="table table-bordered table-striped" >
										<thead>
											<tr>
												<th class="center">
													<label class="pos-rel">
														<input type="checkbox" class="ace" id="chkAll"/>
														<span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack">
													번호
												</th>
												<th class="center colorBlack">
													주문 번호
												</th>
												<th class="center colorBlack">
													User ID
												</th>
												<th class="center colorBlack">
													주문 일자
												</th>
												<th class="center colorBlack">
													Station
												</th>
												<th class="center colorBlack">
													도착국가
												</th>
												<th class="center colorBlack">
													배송사
												</th>
												<th class="center colorBlack">
													수취인명
												</th>
												<th class="center colorBlack">
													연락처
												</th>
												<th class="center colorBlack" colspan=2>
													Addr
												</th>
											</tr>
										</thead>												
										<tbody>
											<c:choose>
												<c:when test="${!empty takeinList}">
													<c:forEach items="${takeinList}" var="takeorderinfo" varStatus="status">
														<tr>
														 	<td style="text-align: center">
																<input type="checkbox" name="nno[]" value="${takeorderinfo.nno}">
																<input type="hidden" name="errYn[]" value="${takeorderinfo.errYn}">
															</td> 
															<td style="text-align: center">
																${status.count}
															</td>
															<td class="center">
																<a href="#" style="font-weight: bold;" onClick="javascript:readModify('takein', '${takeorderinfo.nno}', '${takeorderinfo.userId}'); return false;">${takeorderinfo.orderNo}</a>
															</td>
															<td class="center">${takeorderinfo.userId}
																<input type="hidden" name="userId[]" value="${takeorderinfo.userId}">
															</td>
															<td class="center">${takeorderinfo.orderDate}</td>
															<td class="center">${takeorderinfo.orgStationName}</td>
															<td class="center">${takeorderinfo.dstnNationName}</td>
															<td class="center">${takeorderinfo.transName}</td>
															<td class="center">${takeorderinfo.cneeName}</td>
															<td>Tel : ${takeorderinfo.cneeTel}
															</br>
															Hp : ${takeorderinfo.cneeHp}
															</br>
															E-mail : ${takeorderinfo.cneeEmail}
															</td>
															<td style="overflow: auto;">
																<span>${takeorderinfo.cneeState}</span>
																<span>${takeorderinfo.cneeCity}</span>
																</br>
																<span style="font-weight:bold">
																	${takeorderinfo.cneeZip}
																</span>
																</br>
																<span>
																	${takeorderinfo.cneeAddr}
																</span>
																<span>
																	${takeorderinfo.cneeAddrDetail}
																</span>
															</td>
														</tr>
														<tr>
															<td colspan=10 style="padding-left:10px;">
																<table class="" style="width:100%">
																	<tr>
																		<th rowspan="1" class="center colorBlack" style="width:380px;"></th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">hscode</th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">Brand</th>
																		<th rowspan="2" class="left colorBlack" style="min-width:190px;">itemDetail</th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">단가</th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">수량</th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">금액</th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">제조국</th>
																		<th rowspan="2" class="center colorBlack" style="width:80px;">제조사</th>
																		<th rowspan="1" class="center colorBlack" style="width:80px;"> total 금액</th>
																	</tr>
																	<tr>
																		<th class="left" style="width:80px;color:red;" rowspan="100">${takeorderinfo.errMsg}</th>
																		<th class="center colorBlack" style="width:80px;" rowspan="100">${takeorderinfo.totalAmt}</th>
																	</tr>
																	<c:forEach items="${takeorderinfo.orderItem}" var="itemDetail" varStatus="status">
																	<tr>
																		<td style="text-align:center;color:blue;font-weight:bold;">${itemDetail.hsCode}</td>
																		<td style="text-align:center;font-weight:bold;">${itemDetail.brand}</td>
																		<td style="text-align:left;font-weight:bold;">${itemDetail.itemDetail}</td>																	
																		<td style="text-align:center;">${itemDetail.unitValue}</td>
																		<td style="text-align:center;">${itemDetail.itemCnt}</td>
																		<td style="text-align:center;">
																			<fmt:formatNumber value="${itemDetail.unitValue * itemDetail.itemCnt}" pattern="#.##"/>
																		</td>
																		<td style="text-align:center;">${itemDetail.makeCntry}</td>
																		<td style="text-align:center;">${itemDetail.makeCom}</td>
																	</tr>
																</c:forEach>
																</table>
															</td>
														</tr>		
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr>
														<td colspan="11" class="center colorBlack">조회 결과가 없습니다</td>
													</tr>
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
							</div>
						</form>
					</div>
				</div>
				<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
			</div>
		</div>
		<!-- /.main-content -->
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
	
		<!-- script addon end -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#5thMenu").toggleClass('open');
					$("#5thMenu").toggleClass('active');
					$("#5thSp").toggleClass('active');
				})
			})

			$("#chkAll").click(function() {
	            if($("#chkAll").prop("checked")){ //해당화면에 전체 checkbox들을 체크해준다
	                $("input[name='nno[]']").prop("checked",true); // 전체선택 체크박스가 해제된 경우
	            } else { //해당화면에 모든 checkbox들의 체크를해제시킨다.
	                $("input[name='nno[]'").prop("checked",false);
	            }
	        });

			$("#registBtn").click(function() {
				window.open("/mngr/takein/popupTakeinReg","PopupWin","width=1400,height=700");
			})
			
			
			
			function fn_orderInfo(nno, userId, transCode) {
				window.open("/mngr/takein/popupTakeinOrderInfoUp?nno="+nno+"&userId="+userId, "PopupWin", "width=1400, height=700");
			}

			$("#delBtn").click(function() {
				if(!confirm('정말 삭제 하시겠습니까?')) {
					return false;
				}
				
				$("#mainForm").attr('action', '/mngr/takein/takeinOrderListTmpDel');
				$("#mainForm").attr('method', 'post');
				$("#mainForm").submit();
			});

			$("#excelSampleDown").click(function() {
				window.open("/mngr/takein/apply/excelFormDown");
			})
			
			function fn_takeinBlScan() {
				LoadingWithMask();
				var targets1 = new Array();
				var targets2 = new Array();
				var userIds = new Array();
				
				$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(e){
					var td_checked = this.checked;
					if(td_checked){
						targets1.push($(this).val());
						userIds.push($("input[name='userId[]'").get(e).value);
						targets2.push($("input[name='errYn[]'").get(e).value);
						
					}
				});

				$.ajax({
					url : '/mngr/takein/takeinOrderDlvIn',
					type : 'POST',
					beforeSend : function(xhr) {
						xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					traditional : true,
					data : {
						targets1 : targets1,
						targets2 : targets2,
						userIds : userIds
					},
					success : function(data) {
						document.location.reload();
					},
					error : function(request,status,error) {
						alert("등록에 실패 하였습니다.");
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					}
				})
			}

			function LoadingWithMask() {
			    //화면의 높이와 너비를 구합니다.
			    var maskHeight = $(document).height();
			    var maskWidth  = window.document.body.clientWidth;
			     
			    //화면에 출력할 마스크를 설정해줍니다.
			    var mask       ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
			  
			    //화면에 레이어 추가
			    $('body')
			        .append(mask)
			        
			    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
			    $('#mask').css({
			            'width' : maskWidth
			            ,'height': maskHeight
			            ,'opacity' :'0.3'
			    });
			  
			    //마스크 표시
			    $('#mask').show();  
			    //로딩중 이미지 표시
			}

			function readModify(type, nno, userId) {
				$("#nno").val(nno);
				$("#userId").val(userId);
				

				console.log($("#nno").val());
				console.log($("#userId").val());
				var formData = $("#mainForm").serialize();
				$("#mainForm").attr('action', '/mngr/takein/popupTakeinOrderInfoUp');
				$("#mainForm").attr('method', 'POST');
				$("#mainForm").submit();
			}

			$("#registBtn2").on('click', function() {
				location.href = "/mngr/takein/registTakeinOrderInfo2";
			})
			
		</script>
		
	</body>
</html>
