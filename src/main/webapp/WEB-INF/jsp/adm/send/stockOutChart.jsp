<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
   <style>
   .colorBlack {
			color: #000000 !important;
		}
		
		.wrapper {
			display: grid;
			width: 90%;
			margin: auto;
			grid-template-columns: 390px 300px 300px 350px;
			grid-template-rows: 265px;
			/* grid-template-rows: repeat(5, minmax(60px, auto)); */
			gap: 1rem;
		}
		
		.wrapper2 {
			display : grid;
			width: 90%;
			margin: auto;
			margin-top: 1rem;
			grid-template-columns: 390px 300px 300px 350px;
			grid-template-rows: 330px;
			gap : 1rem;
		}
		
		.chartjsLegend {
			position: absolute;
			top: 180px;
			left: 345px;
		    vertical-align: middle;
		}
		
		.chartjsLegend li span {
		    display: inline-block;
		    width: 12px;
		    height: 12px;
		    margin-right: 5px;
		    /* border-radius: 25px; */
		    vertical-align: middle;
		}
		
		.chartjsLegend  ul li {
			list-style : none;			
		}
		
		#chartjsLegend2 {
			position: absolute;
			top: 200px;
			left: 1350px;
		    vertical-align: middle;
		}
		
		#chartjsLegend2 li span {
		    display: inline-block;
		    width: 12px;
		    height: 12px;
		    margin-right: 5px;
		    /* border-radius: 25px; */
		    vertical-align: middle;
		}
		
		#chartjsLegend2  ul li {
			list-style : none;			
		}
		
		.fa-exclamation-circle {
			display: inline-block;
			font-weight: normal;
		}
		
		.tooltip-text {
			display : none;
			positon : absolute;
			border : 1px solid;
			border-radius : 5px;
			padding: 5px;
			font-size : 0.8em;
			color : white;
			background : black;
		}
		
		
		.fa-exclamation-circle:hover .tooltip-text {
			display : inline-block;
		}
		
		
		#years {
			text-align: center;
			border: 1px solid #dcdcdc;
			border-radius: 10px;
		}
		
		#months {
			text-align: center;
			border: 1px solid #dcdcdc;
			border-radius: 10px;
		}
		
   </style>
   
   <head>
   <%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
   <style type="text/css">
     .colorBlack {color:#000000 !important;}
    </style>
   <!-- basic scripts -->
   <!-- ace scripts -->
	
	<!-- <![endif]-->
	
	<!--[if IE]>
	<script src="assets/js/jquery-1.11.3.min.js"></script>
	<![endif]-->
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>

   </head> 
   <title>출고 현황</title>
   <body class="no-skin">
		<!-- headMenu Start -->
		<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main Container Start -->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try {ace.settings.loadState('main-container')} catch(e) {}
			</script>
			<!-- Side Menu start -->
			<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp" %>
			<!-- Side Menu end -->
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
	
							<li>
								발송 리스트
							</li>
							<li class="active">출고 현황</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								출고 현황
							</h1>
						</div>
						<br/>
						<div id="inner-content-side" class="wrapper">
							<div class="item" style="border: 1px solid #FDE1B4; border-radius: 20px; padding: 10px;">
								<span style="font-weight:bold; font-size:14px; margin-left:10px; margin-top:5px; margin-bottom:10px;">배송사별 출고 건 수
									<small style="float:right; font-weight:normal; font-size:8px;" id="smallText1"></small>
								</span>
								<canvas id="transChart" width="200" height="200" style="margin-left: 30px; margin-top:10px;"></canvas>
								<div id='chartjsLegend' class="chartjsLegend"></div>
							</div>
							<div class="item" style="grid-column: 2 / 4; border: 1px solid #B8D7FF; border-radius: 20px; padding: 10px;">
								<span style="font-weight:bold; font-size:14px; margin-left:10px; margin-top:5px; margin-bottom:10px;">업체별 출고 건 수
									<small style="float:right; font-weight:normal; font-size:8px;" id="smallText2"></small>
								</span>
								<canvas style="margin-top:12px; margin-left:2px;" id="userChart" width="570" height="210"></canvas>
							</div>
							<div class="item" style="border: 1px solid #E598BB; border-radius: 20px; padding: 10px;">
								<span style="font-weight:bold; font-size:14px; margin-left:10px; margin-top:5px; margin-bottom:10px;">국내외 출고 현황
									<small style="float:right; font-weight:normal; font-size:8px;" id="smallText3"></small>
								</span>
								<canvas id="inandoutChart" width="200" height="200" style="margin-left: 20px; margin-top:10px;"></canvas>
								<div id='chartjsLegend2'></div>
							</div>
						</div>
						<div class="wrapper2">
							<div class="item" style="grid-column: 1 / 3; border: 1px solid #27DBB7; border-radius: 20px; padding: 10px;">
								<span style="font-weight:bold; font-size:14px; margin-left:10px; margin-top:5px; margin-bottom:15px;">월 별 출고 건 수
									<select id="select_year" style="width:100px; margin-right:20px; font-weight:normal; float:right;">
									</select>
								</span>
								<small style="font-weight:normal; margin-left:310px; vertical-align:bottom;">조회 연도를 선택해주세요</small>
								<canvas id="monthlyChart" width="630" height="260" style="margin-left: 20px; margin-top:25px;"></canvas>
							</div>
							<div class="item" style="grid-column: 3 / 5; border: 1px solid #F4AAAA; border-radius: 20px; padding: 10px;">
								<span style="font-weight:bold; font-size:14px; margin-left:10px; margin-top:5px; margin-bottom:15px;">일자 별 출고 건 수
									<i style="font-weight:normal;" class="fa fa-exclamation-circle" aria-hidden="true">
										<span class="tooltip-text">월별 차트에서 조회할 월의 포인트를 클릭하세요</span>
									</i>
									<small style="float:right; font-weight:normal; font-size:8px;" id="smallText4"></small>
								</span>
								<canvas id="dailyChart" width="620" height="260" style="margin-left:8px; margin-top:20px;"></canvas>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Main Container End -->
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->

		<!--[if !IE]> -->
		
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
   
	   	<script src="/assets/js/Chart.bundle.min.js"></script>
		<script src="/assets/js/Chart.min.js"></script>
		<script src="/assets/js/chartjs-plugin-datalabels.js"></script>
		<script src="/assets/js/chartjs-plugin-doughnutlabel.js"></script>
		<script src="/assets/js/chartjs-plugin-annotation.js"></script>
		<script src="/assets/js/chartjs-plugin-annotation.min.js"></script>
		
	

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
		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>
		
		<!-- <script src="https://cdn.jsdelivr.net/npm/chart.js@3.6.0/dist/chart.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2.0.0"></script> -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {

					$('dropdown-toggle').dropdown();

					$("#8thMenu").toggleClass('open');
					$("#sendStatus").toggleClass('active');
				})
			})
			var inCnt = ${inCnt};
			var outCnt = ${outCnt};

			var backgroundColorList = ['#FF6384', '#FF9F40', '#FFCD56', '#4BC0C0', '#36A2EB', '#9966FF', '#FFFC66'];

			var transTotalCnt = ${transTotalCnt};
			var today = new Date();
			var year = today.getFullYear();
			var month = ('' + (today.getMonth() + 1)).slice(-2);

			var now = new Date();
			var nowYear = now.getYear();
			var firstDate, lastDate;

			var formatDate = function(date) {
				var myMonth = date.getMonth()+1;
				var myWeekDay = date.getDate();

				var addZero = function(num) {
					if (num < 10) {
						num = "0" + num;
					}
					return num;
				}

				var md = addZero(myMonth)+'/'+addZero(myWeekDay);
				return md;
			}

			firstDate = new Date(now.getYear(), now.getMonth(), 1);
			lastDate = new Date(now.getYear(), now.getMonth()+1, 0);
			var firstDay = formatDate(firstDate);
			var lastDay = formatDate(lastDate);

			var element1 = document.getElementById('smallText1');
			element1.innerHTML = '('+firstDay+' ~ '+lastDay+')';
			var element2 = document.getElementById('smallText2');
			element2.innerHTML = '('+firstDay+' ~ '+lastDay+')';
			var element3 = document.getElementById('smallText3');
			element3.innerHTML = '('+firstDay+' ~ '+lastDay+')';
			var element4 = document.getElementById('smallText4');
			element4.innerHTML = '('+firstDay+' ~ '+lastDay+')';
					
			var transList = ${json2};
			var transCodeList = new Array();
			var transNameList = new Array();
			var transCntList = new Array();
			var transColorList = new Array();
			
			for (var j = 0; j < transList.length; j++) {
				transCodeList.push(transList[j].TRANS_CODE);
				transNameList.push(transList[j].TRANS_NAME);
				transCntList.push(transList[j].CNT);
				transColorList.push(getRandomColor());
			}
			

			var jsonData = ${json};
			var jsonObject = JSON.stringify(jsonData);
			var datass = JSON.parse(jsonObject);
			var userList = new Array();
			var cntList = new Array();
			var colorList = new Array();	
			
			for (var i = 0; i < datass.length; i++) {
				userList.push(datass[i].userId.toLowerCase());
				cntList.push(datass[i].cnt);
				colorList.push(getRandomColor());
			}


			function getRandomColor() {

				var colorCode = "#70" + Math.round(Math.random() * 0xffffff).toString(16);

				var R = Math.floor(Math.random()*200);
				var G = Math.floor(Math.random()*200);
				var B = Math.floor(Math.random()*200);

				var color = 'RGBA(' + R + ', ' + G + ', ' + B + ', 0.8)';

				return color;				
			}

			var ctx = document.getElementById('transChart').getContext('2d');
			var transChart = new Chart(ctx, {
				type : 'doughnut',
				data : {
					labels : transNameList,
					datasets : [{
						data : transCntList,
						backgroundColor : backgroundColorList,
					}]
				},
				options : {
					responsive : false,
					legend : {
						display : false,
					},
					plugins : {
						datalabels : {
							display : true,
							backgroundColor : '#F4FFFF',
							borderRadius : 3,
							font : {
								color : 'black',
								weight : 'bold'
							}
						},
						doughnutlabel : {
							labels : [{
								text : 'total',
								},{
								text : transTotalCnt,
								font : {
									size : 20,
									weight : 'bold'
								}
							}]
						}
					}
				}
			})

			document.getElementById('chartjsLegend').innerHTML = transChart.generateLegend();


			var ctx = document.getElementById('userChart').getContext('2d');
			var userChart = new Chart(ctx, {
				type : 'bar',
				data : {
					labels : userList,
					datasets : [{
						data : cntList,
						backgroundColor : 'rgb(255, 99, 132)',
					}]
				},
				options : {
					responsive : false,
					legend : {
						display : false
					},
					scales : {
						xAxes : [{
							ticks : {
								fontSize : 11,
							},
							gridLines : {
								display : false,
							},
							maxBarThickness : 8,
							
						}]
					},
					plugins : {
						datalabels : {
							display : true,
							anchor : 'end',
							font : {
								weight : 'bold'
							}
						}
					}
				}
			})


			var ctx = document.getElementById('inandoutChart').getContext('2d');
			var inandoutChart = new Chart(ctx, {
				type : 'pie',
				data : {
					labels : ['국내 출고', '해외 출고'],
					datasets : [{
						data : [inCnt, outCnt],
						backgroundColor : ['#D2D2FF', '#6A5ACD'],
					}]
				},
				options : {
					responsive : false,
					legend : {
						display: false
					},
					plugins : {
						datalabels : {
							display : true,
							backgroundColor : 'white',
							borderRadius : 3,
							font : {
								color : 'black',
								weight : 'bold'
							}
						},
					}
				}
			})

			document.getElementById('chartjsLegend2').innerHTML = inandoutChart.generateLegend();
			
			var jsonData = ${stockOut};
			var jsonObject = JSON.stringify(jsonData);
			var stockOutData = JSON.parse(jsonObject);

			var months = [...(new Set(stockOutData.map((val) => {return val.depMonth.substring(0,4)})))];

			for (var i = 0; i < months.length; i++) {
				var option = new Option();
				option.value = months[i];
				option.text = months[i]+'년';
				document.getElementById('select_year').options.add(option);
			}

			$("#select_year").val(year).prop('selected', true);
			
			var labels = [...(new Set(stockOutData.map((val) => {return val.stationName})))];
			for (var label in labels) {
				var data = stockOutData.filter((val) => {
					return val.stationName == labels[label]}).map((val) => {
						return {x: val.depMonth, y: val.cnt}})
			}
	
			var depMonthList = new Array();
			var cntList2 = new Array();
			for (var j = 0; j < data.length; j++) {
				depMonthList.push(data[j].x);
				cntList2.push(data[j].y);
			}

			
			var ctx = document.getElementById('monthlyChart').getContext('2d');
			var monthlyChart = new Chart(ctx, {
				type : 'bar',
				data : {
					labels : depMonthList,
					datasets : [{
						data : cntList2,
						backgroundColor : '#FF7F50',
					}]
				},
				options : {
					responsive : false,
					legend : {
						display : false,
					},
					plugins : {
						datalabels : {
							display : false,
						}
					}
				}
			})

			var selectedYear = document.getElementById('select_year').value;
			var newDatas = stockOutData.filter(e => {
				return e.depMonth.substring(0,4) === selectedYear
			});

			var showData = selectedYear === year ? stockOutData : newDatas;

			for (var label in labels) {
				var dataa = showData.filter((val) => {
					return val.stationName == labels[label]}).map((val) => {
						return {x: val.depMonth, y: val.cnt}})
			}
	
			var showDepMonthList = new Array();
			var showCntList = new Array();
			for (var j = 0; j < dataa.length; j++) {
				showDepMonthList.push(dataa[j].x.substring(4,6)+'월');
				showCntList.push(dataa[j].y);
			}


			var gradientStroke = ctx.createLinearGradient(500, 0, 100, 0);
			gradientStroke.addColorStop(0, "#80b6f4");
			gradientStroke.addColorStop(1, "#f49080");

			monthlyChart.destroy();
			monthlyChart = new Chart(ctx, {
				type : 'line',
				data : {
					labels : showDepMonthList,
					datasets : [{
						data : showCntList,
						fill : false,
						lineTension : 0,
						borderColor : gradientStroke,
						pointBorderWidth : 5,
						pointBorderColor : gradientStroke,
						pointBackgroundColor : gradientStroke,
						pointHoverBackgroundColor : gradientStroke,
						pointHoverBorderColor : gradientStroke,
						pointHoverBorderWidth : 10,
					}]
				},
				options : {
					responsive : false,
					legend : {
						display : false,
					},
					plugins : {
						datalabels : {
							display : false,
							fontSize : 8,
							anchor : 'end',
							font : {
								weight : 'bold'
							}
						}
					},
					scales : {
						xAxes : [{
							gridLines: {
								display : false,
							}
						}],
					}
				}
			})

			monthlyChart.update();

			$("#select_year").on('change', function() {
				var selectedYear = document.getElementById('select_year').value;
				var newDatas = stockOutData.filter(e => {
					return e.depMonth.substring(0,4) === selectedYear
				});

				var showData = selectedYear === year ? stockOutData : newDatas;

				for (var label in labels) {
					var dataa = showData.filter((val) => {
						return val.stationName == labels[label]}).map((val) => {
							return {x: val.depMonth, y: val.cnt}})
				}
		
				var showDepMonthList = new Array();
				var showCntList = new Array();
				for (var j = 0; j < dataa.length; j++) {
					showDepMonthList.push(dataa[j].x.substring(4,6)+'월');
					showCntList.push(dataa[j].y);
				}

				monthlyChart.destroy();
				monthlyChart = new Chart(ctx, {
					type : 'line',
					data : {
						labels : showDepMonthList,
						datasets : [{
							data : showCntList,
							fill : false,
							lineTension : 0,
							pointBorderWidth : 5,
							borderColor : gradientStroke,
							pointBorderColor : gradientStroke,
							pointBackgroundColor : gradientStroke,
							pointHoverBackgroundColor : gradientStroke,
							pointHoverBorderColor : gradientStroke,
							pointHoverBorderWidth : 10,
						}]
					},
					options : {
						responsive : false,
						legend : {
							display : false,
						},
						plugins : {
							datalabels : {
								display : false,
								fontSize : 8,
								anchor : 'end',
								font : {
									weight : 'bold'
								}
							},
						},
						scales : {
							xAxes : [{
								gridLines: {
									display : false,
								}
							}],
						}
					}
				})

				monthlyChart.update();
			})
			
			var dailyData = ${dailyChart};
			var depDateList = new Array();
			var dailyCntList = new Array();

			for (var x = 0; x < dailyData.length; x++) {
				depDateList.push(dailyData[x].DEP_DATE.substring(6,8));
				dailyCntList.push(dailyData[x].cnt);
			}

			
			var ctx2 = document.getElementById('dailyChart').getContext('2d');
			var dailyChart = new Chart(ctx2, {
				type : 'bar',
				data : {
					labels : depDateList,
					datasets : [{
						data : dailyCntList,
						backgroundColor : '#C86464',
						minBarThickness : 10,
						maxBarThickness : 10,
					}]
				},
				options : {
					responsive : false,
					legend : {
						display : false
					},
					scales : {
						xAxes : [{
							ticks : {
								fontSize : 11,
							},
							gridLines : {
								display : false,
							}
						}]
					},
					plugins : {
						datalabels : {
							display : false,
							anchor : 'end',
							border : 1,
							font : {
								color : 'white',
							}
						},
					}
				}
			})

			
			document.getElementById('monthlyChart').onclick = function(evt) {
				var activePoints = monthlyChart.getElementsAtEvent(evt);

				if (activePoints.length > 0) {
					var clickedElementIndex = activePoints[0]["_index"];
					var label = monthlyChart.data.labels[clickedElementIndex];
					var value = monthlyChart.data.datasets[0].data[clickedElementIndex];
				}

				if (label == null || value == null) {
					return false;
				} else {
					var year = $("#select_year").val();
					var month = label.substring(0,2);
					var sendData = year+month;

					$.ajax({
						url : '/mngr/send/sendYearData',
						type : 'POST',
						beforeSend: function(xhr) {
							xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						data : {sendData: sendData},
						success : function(data) {
							var depDateList = new Array();
							var cntList = new Array();
							for (var i = 0; i < data.dailyList.length; i++) {
								depDateList.push(data.dailyList[i].DEP_DATE.substring(6,8));
								cntList.push(data.dailyList[i].cnt);
							}

							dailyChart.destroy();
							dailyChart = new Chart(ctx2, {
								type : 'bar',
								data : {
									labels : depDateList,
									datasets : [{
										data : cntList,
										backgroundColor : '#C86464',
										minBarThickness : 10,
										maxBarThickness : 10,
									}]
								},
								options : {
									responsive : false,
									legend : {
										display : false
									},
									scales : {
										xAxes : [{
											ticks : {
												fontSize : 11,
											},
											gridLines : {
												display : false,
											}
										}],
									},
									plugins : {
										datalabels : {
											display : false,
											anchor : 'end',
											border : 1,
											font : {
												color : 'white',
											}
										}
									}
								}
							})

							dailyChart.update();

							var firstDay = month + '/01';
							var lastDate = new Date(year, month, 0);
							var lastDay = formatDate(lastDate);
							
							var elements = document.getElementById('smallText4');
							elements.innerHTML = '('+firstDay+' ~ '+lastDay+')';
							
						},
						error : function(request,status,error) {
							alert('조회에 실패하였습니다');
							console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						}
					})
				}

			}

			


			
		</script>
		<!-- script addon end --> 
		
	</body>
</html>