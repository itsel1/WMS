<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	<style type="text/css">
				
		#loading {
			height: 100%;
			left: 0px;
			position: fixed;
			_position: absolute;
			top: 0px;
			width: 100%;
			filter: alpha(opacity = 50);
			-moz-opacity: 0.5;
			opacity: 0.5;
		}
		.loading {
			background-color: white;
			z-index: 999999;
		}
		#loading_img {
			position: absolute;
			top: 50%;
			left: 50%;
			/* height: 200px; */
			margin-top: -75px; 
			margin-left: -75px; 
			z-index: 999999;
		}
		
		.datetime {
			height: 30px;
			width: 120px;
		}
		
		.fa-exclamation-circle {
			color: red;
			cursor: help;
		}
		
        .collect-order-sub {
            display: flex;
            padding: 10px;
            border: 1px solid #e6e6e6;
            border-radius: 7px;
            align-items: center;
        }

        .collect-order-sub > div {
            margin-right: 10px;
        }

        .border-radius-form {
            border-radius: 5px;
        }

        .tooltip-icon {
            display: inline-block;
            position: relative;
            font-size: 13px;
            line-height: 14px;
            font-weight: 600;
            text-align: center;
            cursor: help;
            margin: 0 5px;
            color: Gold;
        }

        .tooltip-text {
            visibility: hidden;
            width: 200px;
            background-color: #555;
            color: #fff;
            text-align: left;
            border-radius: 6px;
            padding: 10px;
            position: absolute;
            z-index: 1;
            top: 100%;
		    left: 100%;
		    margin-top: 5px;
		    margin-left: 10px;
            opacity: 0;
            transition: opacity 0.3s;
        }

        .tooltip-text::after {
            position: absolute;
            top: 100%;
            left: 50%;
            margin-left: -5px;
            border-width: 5px;
            border-style: solid;
            border-color: #555 transparent transparent transparent;
        }

        .tooltip-icon:hover .tooltip-text {
            visibility: visible;
            opacity: 1;
        }

        .for-shopee {
            display: flex;
            align-items: center;
        }
        
        .datetime-range-form {
        	border: 1px solid #e6e6e6;
        	box-sizing: border-box;
            border-radius: 5px;
            padding: 5px 10px;
        }
        
        #dateime-range[readonly='readonly'] {
        	border: none;
        	outline: none;
        	cursor: default;
        	background: white;
        	margin-left: 5px;
        	width: 200px;
        	text-align: center;
        }
	</style>
	<!-- basic scripts --> 
	</head> 
	<title>사입 신청서</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		<!-- Main container Start-->
		<div class="toppn">
	      <div class="container">
	        <div class="row">
	          <div class="col-md-12 mb-0" style="font-size:14px !important;"><a>사입주문 등록</a> <span class="mx-2 mb-0">/
	          </span> <strong class="text-black">사입주문 등록</strong></div>
	        </div>
	      </div>
	    </div>
	    <!-- Main container Start-->
		<div class="container">
			<div class="page-content noneArea">
				<div class="page-header noneArea">
					<h3>
						사입주문 등록
					</h3>
				</div>
				<div id="inner-content-side">
					<div>
						</br>
						<form id="uploadForm" method="post" enctype="multipart/form-data">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<table style="width:100%">
								<tbody>
									<tr>
										<td style="width:350px;">
											<input type="file" name="excelFile" id="excelFile">
										</td>
										<td style="width:200px;">
											<input type="button" class="btn-primary" value="Excel 등록" id="excelUp">
										</td>
										<td style="width:100%;text-align:right" >
											<input id="btnExcelSampleDown" type="button" class="btn btn-white" value="Excel Form 다운로드">
										</td>
									</tr>
								</tbody>	
							</table>
						</form>
					</div>
					<div class="row"><br style="line-height:0.8;"/></div>
					<!-- <div class="collect-order-main">
						 <div>
						 	<label style="font-weight:bold;">쇼핑몰 주문 수집</label>
						 </div>
						 <div class="collect-order-sub">
						 	<div>
						 	<select name="shopType" id="shopType" class="border-radius-form">
						 		<option value="">::선택::</option>
								<option value="shopify">Shopify</option>
								<option value="shopee">Shopee</option>
						 	</select>
						 	</div>
						 	<div class="for-shopee">
								<span>수집 주문 기간</span>
								<span class="tooltip-icon"><i class="fa fa-exclamation-triangle"></i>
								<span class="tooltip-text">주문일자 기간의 범위는 최대 15일까지만 설정 가능합니다</span>
								</span>
								<div class="datetime-range-form"><i class="fa fa-calendar" style="font-size:15px;"></i>
									<input type="text" id="dateime-range" readonly="readonly">
									<input type="hidden" name="fromDate" value="">
									<input type="hidden" name="toDate" value="">
								</div>
						 	</div>
						 	<div>
						 		<input type="button" value="수집" class="btn-success" id="collect-order-btn">
						 	</div>
						 </div>
					</div> -->
				</div>
				<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;">
					<div class="hr hr-8 dotted"></div>
					<div class="space-20"></div>
					<div class="display" >
						<form id="list_form" action="" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<input type="hidden" name="transCodeIn" id="transCodeIn" value="" />
						<input type="hidden" name="nno" id="nno" value="" />
						<div style="text-align:left;float:left">
							<input type="button" value="삭제" id="btn_del" class="btn-danger">
						</div>
						<div style="text-align:right;float:right">
							<input type="button" class="btn-primary" value="등록" id="btn_dlvin"  >
						</div>
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
							<c:forEach items="${takeinList}" var="takeorderinfo" varStatus="status">
								<tr>
									<td style="text-align: center">
										<input type="checkbox" name="nno[]" value="${takeorderinfo.nno}">
										<input type="hidden" name="errYn[]" value="${takeorderinfo.errYn}">
									</td> 
									<td style="text-align: center">
										${status.count}
									</td>
									<td class="center colorBlack">
										<a href="#" style="font-weight: bold;" onClick="javascript:readModify('takein', '${takeorderinfo.nno}'); return false;">${takeorderinfo.orderNo}</a></td>
									<td>${takeorderinfo.orderDate}</td>
									<td>${takeorderinfo.orgStationName}</td>
									<td>${takeorderinfo.dstnNationName}</td>
									<td>${takeorderinfo.transName}</td>
									<td>${takeorderinfo.cneeName}</td>
									<td>Tel : ${takeorderinfo.cneeTel}</br>Hp : ${takeorderinfo.cneeHp}</br>E-mail : ${takeorderinfo.cneeEmail}</td>
									<td style="overflow: auto;">
										<span>${takeorderinfo.cneeState}</span>
										<span>${takeorderinfo.cneeCity}</span>
										</br>
										<span style="font-weight:bold">${takeorderinfo.cneeZip}</span>
										</br>
										<span>${takeorderinfo.cneeAddr}</span>
										<span>${takeorderinfo.cneeAddrDetail}</span>
									</td>
								</tr>
								<tr>
									<td colspan=10 style="padding-left:10px;">
										<table class="" style="width:100%">
											<tr>
												<th rowspan="1" class="center colorBlack" style="width:200px;"></th>
												<th rowspan="2" class="center colorBlack" style="width:80px;">hscode</th>
												<th rowspan="2" class="center colorBlack" style="width:80px;">Brand</th>
												<th rowspan="2" class="center colorBlack" style="width:180px;">상품코드</th>
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
												<td style="text-align:center;font-weight:bold;">${itemDetail.cusItemCode}</td>
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
							</tbody>
						</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <script src="/testView/js/jquery-3.3.1.min.js"></script> -->
		<script src="/testView/js/jquery-ui.js"></script>
		<script src="/testView/js/popper.min.js"></script>
		<!-- <script src="/testView/js/bootstrap.min.js"></script> -->
		<script src="/assets/js/bootstrap.min.js"></script>
		<script src="/testView/js/owl.carousel.min.js"></script>
		<script src="/testView/js/jquery.magnific-popup.min.js"></script>
		<script src="/testView/js/aos.js"></script>
		
		<script src="/testView/js/main.js"></script>
		
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
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		
		<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
		<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
		<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
		<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
		
		<script type="text/javascript">
		var loading = "";
	    $(document).ready(function(){
	    	$("input[name='fromDate']").val(moment().format('YYYY-MM-DD'));
			$("input[name='toDate']").val(moment().format('YYYY-MM-DD'));
			
	    	loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="/image/Pulse.gif" />').appendTo(document.body).hide();
		    $("#chkAll").click(function() {
	            if($("#chkAll").prop("checked")){ //해당화면에 전체 checkbox들을 체크해준다
	                $("input[name='nno[]']").prop("checked",true); // 전체선택 체크박스가 해제된 경우
	            } else { //해당화면에 모든 checkbox들의 체크를해제시킨다.
	                $("input[name='nno[]'").prop("checked",false);
	            }
	        });

		    $("#btn_dlvin").click(function() {
				loading.show();
				var targets1 = new Array();
				var targets2 = new Array();
				$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(e){
					var td_checked = this.checked;
					if(td_checked){
						targets1.push($(this).val());
						targets2.push($("input[name='errYn[]'").get(e).value);
						
					}
				});

				
		    	$.ajax({
					url:'/cstmr/takein/takeinDvlIn',
					type: 'POST',
					beforeSend : function(xhr)
					{   
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					traditional : true,
					data: {
						targets1 : targets1,
						targets2 : targets2
					},
					success : function(data) {
						loading.hide();
						location.reload();
		            }, 		    
		            error : function(xhr, status) {
		                alert("등록 처리 중 오류 발생");
						loading.hide();
						location.reload();
		            }
				})
				  
		        /* $("#list_form").attr('action','/cstmr/takein/takeinDvlIn');
	            $("#list_form").submit(); */
		    });

		    $("#btn_del").click(function() {
		        if(!confirm('정말 삭제 하시겠습니까?')){
	                return false;
	            }
		        $("#list_form").attr('action','/cstmr/takein/takeinTmpDel');
	            $("#list_form").submit();
		    });

	    });

	    $("#registBtn").click(function() {
		    location.href="/cstmr/takein/registTakeinOrder";
		    //window.open("/cstmr/takein/popupTakeinOrderRegist", "PopupWin","width=1400,height=700");
		})

	    $("#btnExcelSampleDown").on('click',function(e){
			window.open("/takein/apply/excelFormDown")
		});

		$("#excelUp").on('click', function(e) {
			if ($("#excelFile").val() == "") {
				alert("선택된 파일이 없습니다.");
				return false;
			}

			var formData = new FormData();
			formData.append("file", $("#excelFile")[0].files[0]);
			loading.show();
			$.ajax({
				url : '/cstmr/takein/takeinExcelUpload',
				type : 'POST', 
				data : formData,
				beforeSend : function(xhr)
				{
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				processData : false,
				contentType : false,
				error : function(request, error) {
					alert("처리 중 오류 발생");
					loading.hide();
					location.reload();
				},
				success : function(data) {
					if (data == "F") {
						alert("등록 중 오류 발생");
					} else {
						alert("등록 되었습니다.");
					}
					loading.hide();
					location.reload();
				}
			});
		});
		
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

		function readModify(type, nno) {
			$("#nno").val(nno);
			var formData = $("#list_form").serialize();
			$("#list_form").attr('action', '/cstmr/takein/modifyTakeinOrderInfo');
			$("#list_form").attr('method', 'POST');
			$("#list_form").submit();
		}
		/*
		document.getElementById('shopType').addEventListener('change', function() {
            var forShopee = document.querySelector('.for-shopee');
            if (this.value === 'shopee') {
                forShopee.style.display = 'flex';
            } else {
                forShopee.style.display = 'none';
            }
        });
		*/
		var today = moment().format('YYYY-MM-DD');
		
		$("#dateime-range").daterangepicker({
			locale : {
				format: 'YYYY-MM-DD',
                separator: ' ~ ',
                applyLabel: 'confirm',
                cancelLabel: 'cancel',
                fromLabel: 'From',
                toLabel: 'To',
                customRangeLabel: 'Custom',
                weekLabel: 'W',
                daysOfWeek: ['일', '월', '화', '수', '목', '금', '토'],
                monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                firstDay: 1
			},
			startDate: today,
            endDate: today,
			maxSpan: {
				days: 14
			}
		}, function(start, end, label) {
			var fromDate = start.format('YYYY-MM-DD');
			var toDate = end.format('YYYY-MM-DD');
			$("input[name='fromDate']").val(fromDate);
			$("input[name='toDate']").val(toDate);
		});
		
		 
		$("#collect-order-btn").on('click', function(e) {
			if ($("#shopType option:selected").val() == "") {
				alert("연동할 쇼핑몰을 선택해 주세요");
				return false;
			}
				
			var shopType = $("#shopType option:selected").val();
			loading.show();
			
			var formData = {
					shopType: shopType, 
					fromDate: $("input[name='fromDate']").val(), 
					toDate: $("input[name='toDate']").val(),
					orderType: 'TAKEIN'
					};

			$.ajax({
				url : '/cstmr/integrate/collectOrders',
				type : 'POST',
				data : formData,
				beforeSend : function(xhr)
				{
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {
					console.log(data);
					alert(data.msg);
					loading.hide();
					location.reload();
				},
				error : function(request, status, error) {
					alert("주문 수집 중 시스템 오류가 발생 하였습니다.");
					loading.hide();
					location.reload();
				}
			});
			
		});
		
		</script>
		
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#forthMenu").toggleClass('open');
					$("#forthMenu").toggleClass('active'); 
					$("#4thOne").toggleClass('active');
				});
			});
		</script>
	</body>
</html>