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
	<title>검수 작업</title>
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
							<li class="active">검수 작업</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								검수 작업
							</h1>
						</div>
						<div class="col-xs-12 col-sm-12" id="inner-content-side">
							<div id="search-div">
								<br>
								<form class="hidden-xs hidden-sm" name="dtSearch" id="dtSearch">	
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<input type="hidden" name="page" id="page" value="" />
									<div class="row">
										<div class="col-xs-12 col-sm-11 col-md-10">
											<span class="input-group" style="display: inline-flex; width:100%">
												<select style="height: 34px" id="cneeType" name="cneeType">
													<option selected value="0">전체</option>
													<option value="1">수취인 이름</option>
													<option value="2">상품명</option>
													<option value="3">TrackNo</option>
													<option value="4">HawbNo</option>
													<option value="5">ORDER NO</option>
													<option value="6">USER ID</option>
												</select>
												<input type="text" style="display:none;"/>
												<input type="text" class="form-control" id="housText" name="housText" style="width:100%; max-width: 150px" value="${housText}"/>
												<input type="hidden" class="form-control" id="nno" name="nno"/>
												<button type="button" class="btn btn-default no-border btn-sm" name="dtSearchBtn" id="dtSearchBtn">
													<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
												</button>
											</span>
										</div>
									</div>
								</form>
								<br>
								
							</div>
							<br>
							<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
							<div id="table-contents" class="table-contents">
								<form name="rgstr-button" id="search-botton" method="post">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!-- Main container End-->
		
		<!-- 모바일 검색 조건 입력 modal -->
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
		<!-- ace scripts -->
		
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					var params = {};
					
					$("#12thMenu").toggleClass('open');
					$("#12thMenu").toggleClass('active'); 
					$("#12thFor").toggleClass('active');
				});
			})
				$('#housText').focus();
				$("#housText").keydown(function (key) {
					if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
						searchDetail();
					}
				});

				$("#dtSearchBtn").on("click",function(){
					searchDetail();
				});


				$("#modalSearchBtn").on("click",function(){
					searchDetail2();
				});

				/* $("#InputTrkNo").focus();
				$("#InputTrkNo").keydown(function (key) {
				    if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
				        var trkNo = $("#InputTrkNo").val();
				
				        if(trkNo==""){
				            return false;
				        }
				
				        $.ajax({url: "firstin_m.php",
				               type: "post",
				               data:{actWork:'firstin',TrkNo:$("#InputTrkNo").val()} ,
				         }).done(function(data) {
				             var obj = JSON.parse(data);
				             var audio = new Audio();
				
				             if(obj.Status == "duple"){
				                 audio.src = '/js/beep11.wav';
				                 audio.play();
				             }
				             if(obj.Status == "stop"){
				                 audio.src = '/js/stop.mp3';
				                 audio.play();
				             }
				
				             if(obj.Status == "pass"){
				                 audio.src = '/js/pass.mp3';
				                 audio.play();
				             }
				
				             if(obj.cnt != ""){
				                      $("#regCNT").text(obj.cnt);
				             }
				
				             $("#ChkMsg").attr('value',obj.Msg);
				            $("#InputTrkNo").attr('value','');
				         });
				    }
				}); */

				
				function searchDetail(){
					var formData = $("#dtSearch").serialize();
					$.ajax({
						url:'/mngr/order/orderInspcDetail',
						type: 'POST',
						data: formData,
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						success : function(data) {
							history.pushState(null, null, "/mngr/order/orderInspc");
							$('#table-contents').html(data);
							$('#boxWeight').focus();
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("조회에 실패 하였습니다. 조건을 확인해 주세요.");
			            }
					}) 

				};

				$(window).on('popstate', function(event) {
			        window.location = document.location.href;
			    });
				
				function searchDetail2(){
					var formData = $("#mobileForm").serialize();
					$.ajax({
						url:'/mngr/order/orderInspcDetail2',
						type: 'POST',
						data: formData,
						success : function(data) {
							$('#table-contents').html(data);
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("수정에 실패 하였습니다. 수정사항을 확인해 주세요.");
			            }
					}) 

				}

				
			
		</script>
		<!-- script addon end -->
		
	</body>
</html>
