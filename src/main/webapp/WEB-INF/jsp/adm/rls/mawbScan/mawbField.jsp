<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/assets/css/bootstrap-datepicker3.min.css" />
	<link rel="stylesheet" href="/assets/css/ui.jqgrid.min.css" />
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	.no-gutters {
	padding: 0 !important;
	margin: 0 !important;
	}
	
	.inStyle{
		background-color: #93CBF9 !important;
		font-size:120px !important; 
		width:100% !important;
	}
	
	.real-blue{
		color:blue !important;
	}
	
	.real-red{
		color:red !important;
	}
	
	.bagStyle{
		background-color: #FADCA5 !important;
		font-size:120px !important;
		color: red;
		width:70% !important;
	}
	
	#bagNo {
		border: 1px solid #428BCA;
		height: 63px;
		width: 100%;
		font-size: 40px;
	}
	
	#bagNo:focus {
		border: 2px solid #428BCA;
	}
	
	#addBtn {
		height: 63px;
		width: 100%;
		font-size: 30px;
	}
	
	</style>
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	</head> 
	<body class="no-skin" style="background-color: white;">
		<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
		<div id="detail-contents" class="detail-contents">
			<div id="home" class="tab-pane in active">
				<div class="row no-gutters">
					<div class="col-xs-12 col-sm-12">
						<div class="col-xs-12 col-sm-12 ">
							<label style="font-size:100px;">대상 MAWB : </label><label class="light-red" style="font-size:900%;">${targetMawb }</label>
							<input type="hidden" id="targeMawbNo" name="targeMawbNo" value="${targetMawb}"/>
						</div>
					</div>
					<!-- <div class="col-xs-12 col-sm-12">
						<div class="col-xs-12 col-sm-12 col-lg-1">
							<label class="bigger-300">Bag :</label>
						</div>
						<div class="col-xs-12 col-sm-12 col-lg-3" >
							<input type="text" id="bagNo" name="bagNo" value="1"/>
						</div>
						<div class="col-xs-12 col-sm-12 col-lg-8">
							<label class="bigger-300">Bag No 변경을 원할 경우 새로 입력</label>
						</div>
					</div> -->
					<div class="col-xd-12 col-sm-12">
						<div class="col-xd-12 col-sm-12">
							<table id="bag-table" style="width:50%;">
								<tr>
									<th style="width:20%;" class="bigger-260">Bag No </th>
									<th style="width:70%;">
										<input type="text" id="bagNo" name="bagNo" value=""/>
									</th>
									<th style="width:10%;">
										<input type="button" class="btn btn-primary btn-white" id="addBtn" name="bagNo" value="+"/>
									</th>
								</tr>
							</table>
						</div>
						<!-- <div class="col-xs-12 col-sm-12 col-lg-12 align-left">
							<span style="height: 63px;">
								<label class="bigger-300">Bag No :&nbsp;</label>
								<input type="text" id="bagNo" name="bagNo" value="1"/>
								<input type="text" id="addBtn" name="bagNo" value="+"/>
								<label class="bigger-100" style="margin-left:8px; font-weight:bold;">※ Bag No 변경 시 새로 입력</label>
							</span>
						</div> -->
					</div>
					<div class="col-xs-12 col-sm-12">
						<div class="col-xs-12 col-sm-12 ">
							<input type="text" class="inStyle" id="target" name="target"/>
						</div>
						<div class="col-xs-12 col-sm-12 ">
							<input type="text" class="inStyle real-blue" id="targetResult" readonly value=""/>
						</div>
						<div class="col-xs-12 col-sm-12 col-lg-6 align-left">
							<label class="bigger-300">현재 등록 건수 :</label><label class="bigger-300" id="currentIn">0</label><label class="bigger-300">건</label>
						</div>
						<div class="col-xs-12 col-sm-12 col-lg-6 align-right">
							<label class="bigger-300">총 등록 건수 :</label><label class="bigger-300" id="totalIn">${hawbCnt}</label><label class="bigger-300">건</label>
						</div>
					</div>
					<c:if test="${userId eq 'admin1' || userId eq 'ppk'}">
					<div class="col-xs-12 col-sm-12">
						<div class="col-xs-12 col-sm-12">
							<input type="button" class="btn btn-primary" id="regiBtn" value="강제 등록" />
 						</div>
					</div>
					</c:if>
				</div>
				<!-- <div class="row no-gutters">
					
				</div>
				<div class="row no-gutters">
					
				</div>
				<div>
					
				</div> -->
				<div class="space-20"></div>
			</div><!-- /#home -->
		</div>
			
		<!-- script addon start -->
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>
		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/jquery.jqGrid.min.js"></script>
		<script src="/assets/js/grid.locale-en.js"></script>
		<script src="/js/jquery.sound.js"></script>
		
		<script type="text/javascript">
			var count = 0;
			$("#target").focus();
			jQuery(function($) {
				$(document).load(function() {
					$("#6thMenu").toggleClass('open');
					$("#6thMenu").toggleClass('active'); 
					$("#6thTwo").toggleClass('active');
				});

				$("#target").keydown(function (key) {
					if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
						var string=$("#target").val();
						var no=string.replace(' ','');
						$("#target").val(no)
						var trkNo = $("#target").val();
						var audio = new Audio();
						if(trkNo==""){
							audio.src = '/js/stop.mp3';
							audio.play();
							$("#targetResult").removeClass("real-blue");
							$("#targetResult").addClass("real-red");
							$("#targetResult").val("잘못된 송장번호 입니다.");
							$("#target").val("");
							$("#target").focus(); 
				            return false;
				        }
						$.ajax({
							url:'/mngr/rls/mawbHawbInsert',
							data:{target: trkNo,
								  targetMawb : $("#targeMawbNo").val(), 
								  bagNo : $("#bagNo").val()},
							type: 'POST',
							beforeSend : function(xhr)
							{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							success : function(data) {  
							
								var status = data.result.substr(0,1);
								var rstMsg = data.result.substr(1,100);

								if(status=="S"){
									audio.src = '/js/pass.mp3';
									audio.play();
									$("#targetResult").addClass("real-blue");
									$("#targetResult").removeClass("real-red");
									$("#targetResult").val(rstMsg);
									count++;
									$("#currentIn").text(count);
									$("#totalIn").text(data.hawbCnt);
									$("#target").val("");
									$("#target").focus();
								}else if(status=="A"){
									$("#targetResult").removeClass("real-blue");
									$("#targetResult").addClass("real-red");
									audio.src = '/js/beep11.wav';
									audio.play();
									$("#targetResult").val(rstMsg);
						        	$("#target").val("");
						        	$("#target").focus();
						        }else{
						        	audio.src = '/js/stop.mp3';
									audio.play();
						        	$("#targetResult").removeClass("real-blue");
									$("#targetResult").addClass("real-red");
									$("#targetResult").val(rstMsg);
									$("#target").val("");
									$("#target").focus();
						        }
								
				            }, 		    
				            error : function(xhr, status) {
				            	audio.src = '/js/stop.mp3';
								audio.play();
				                alert("등록에 실패 하였습니다. 등록사항을 확인해 주세요.");
				                $("#targetResult").removeClass("real-blue");
								$("#targetResult").addClass("real-red");
								$("#targetResult").val("등록 실패");
								$("#target").val("");
								$("#target").focus();
				            }
						})
				        
				       /*  if(trkNo=="a"){
							$("#targetResult").removeClass("real-blue");
							$("#targetResult").addClass("real-red");
							$("#targetResult").val("등록 실패");
				        }else{
				        	$("#targetResult").addClass("real-blue");
							$("#targetResult").removeClass("real-red");
							$("#targetResult").val("등록 성공");
							count++;
							$("#target").val("");
							$("#target").focus();
							$("#currentIn").text(count);
				        } */
						/* alert("입고 로직 수행"); */
				        /* searchDetail(); */
					}
				});
				$(window).bind("beforeunload", function (e){
					opener.location.href = '/mngr/rls/mawbAply';
					close();
				});

				$("#addBtn").on('click', function() {
					var bagNo = $("#bagNo").val();

					if (!isNaN(bagNo)) {
						bagNo = parseInt(bagNo);
						$("#bagNo").val(bagNo+1);
					} else {
						alert("숫자만 가능합니다.");
						return false;
					}
				})

				$("#regiBtn").on('click', function() {
					var target = $("#target").val();
					var targeMawbNo = $("#targeMawbNo").val();
					var bagNo = $("#bagNo").val();
					
					if (target == "") {
						alert("등록할 송장 번호를 입력하세요.");
						return false;
					} else {
						$.ajax({
							url : '/mngr/rls/mawbHawbInserUp',
							type : 'POST',
							beforeSend : function(xhr)
							{
							    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
							},
							data : {hawbNo : target, mawbNo : targeMawbNo, bagNo : bagNo},
							success : function(data) {
								if (data == 'S') {
									$("#targetResult").removeClass("real-red");
									$("#targetResult").addClass("real-blue");
									$("#targetResult").val("등록 성공");
									$("#target").val("");
									$("#target").focus();
									count++;
									$("#currentIn").text(count);
									$("#totalIn").text(data.hawbCnt);
									$("#target").val("");
									$("#target").focus();
								} else {
									alert("등록 중 오류가 발생 하였습니다.");
									$("#targetResult").removeClass("real-blue");
									$("#targetResult").addClass("real-red");
									$("#targetResult").val("등록 실패");
									$("#target").val("");
									$("#target").focus();
								}
							},
							error : function(xhr, status) {
								alert("등록에 실패 하였습니다. 다시 시도해 주세요.");
							}
						})
					}
				})
			})
		</script>
		<!-- script addon end -->
	</body>
</html>
