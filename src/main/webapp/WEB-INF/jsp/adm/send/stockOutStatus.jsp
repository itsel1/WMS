<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
		<%@ include file="/WEB-INF/jsp/importFile/date.jsp" %>
		
		<style type="text/css"> 
		.colorBlack {
			color: #000000 !important;
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
	<title>출고 목록</title>
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
						<div id="inner-content-side">
							<form id="stockOutForm" name="stockOutForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<div id="search-div">
									<table class="table table-bordered" style="width: 800px; height: 20px;">
										<thead>
											<tr>
												<th class="center colorBlack" style="width: 50px;">
													<i class="fa fa-calendar bigger-110"></i>
												</th>
												<td style="padding: 0px; width: 180px;">
													<div style="height: 100%;" class="input-group input-daterange">
														<input style="width: 100%; height: 100%;" type="text" class="form-control" name="fromDate" id="fromDate" value="${parameterInfo.fromDate}">
													</div>												
												</td>
												<th class="center colorBlack" style="width: 30px;">
													TO
												</th>
												<td style="padding: 0px; width: 180px;">
													<div style="height: 100%;" class="input-group input-daterange">
														<input style="width: 100%; height: 100%;" type="text" class="form-control" name="toDate" id="toDate" value="${parameterInfo.toDate}">
													</div>												
												</td>
												<th class="center colorBlack">Station</th>
												<td style="padding: 0px; width: 180px;">
													<input class="form-control" list="stationCodeList" name="orgStation" id="orgStation" style="width: 100%; height: 100%;" value="${parameterInfo.orgStation}">
													<datalist id="stationCodeList" name="stationCodeList" >
													<c:forEach items="${stationCodeList}" var="stationCodeList" varStatus="status">
														<option value="${stationCodeList.orgStation}"
															<c:if test="${stationCodeList eq stationCodeList.orgStation}">selected</c:if>>
															${stationCodeList.orgStation}
														</option>
													</c:forEach>
													<!-- datalist --></td>
												<%-- <th class="center colorBlack">USER ID</th>
												<td style="padding: 0px; width: 180px;">
													<input style="width: 100%; height: 100%;" type="text" class="form-control" name="userId" id="userId" value="${parameterInfo.userId}">
												</td> --%>
												<td style="width: 50px;">
													<input type="submit" style="margin: 0 auto; text-align: center;" value="검색" id="searchBtn"></td>
											</tr>
										</thead>
									</table>
								</div><!-- search div -->
								<br>
								<div id="chart-div">
									<select class="form-control browser-default dropdown" id="select_year" style="width:100px;" onchange="changeData()">
									</select>
									<canvas id="chart" width="1200" height="500"></canvas>
								</div>
								<br/>
								<%-- <div id="chart-div2" >
									<canvas id="chart2" width="900" height="400"></canvas>
								</div> --%>
							</form>
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
		<!-- script on paging end -->
		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>
		
		<script src="https://cdn.jsdelivr.net/npm/chart.js@3.6.0/dist/chart.min.js"></script>
		
		<!-- script addon start -->
		<script type="text/javascript">
			$(document).ready(function() {
				$("#8thMenu").toggleClass('open');
				$("#8thMenu").toggleClass('active'); 
				$("#8thFourth").toggleClass('active');

			});

			/* get json data */
			var jsonData = ${json};
			var jsonObject = JSON.stringify(jsonData);
			var datas = JSON.parse(jsonObject);

			console.log(datas);

			var months = [...(new Set(datas.map((val) => {return val.depMonth.substring(0, 4)})))];

			var allOption = new Option();
			allOption.value = "전체";
			allOption.text = "전체";
			document.getElementById("select_year").options.add(allOption);
			for (var i = 0; i < months.length; i++) {
				var option = new Option();
				option.value = months[i];
				option.text = months[i];

				document.getElementById("select_year").options.add(option);
			}

			/* draw chart */
			var ctx = document.getElementById('chart').getContext('2d');
			var myChart = new Chart(ctx, {
				type: 'line',
			    data: {
			        datasets: getDataset(datas),
			    },
			    options : {
				    responsive : false,
				}
			});


			/* set chart */
			function getDataset(obj) {
				var labels = [...(new Set(obj.map((val) => {return val.stationName})))];
				var datasets = [];
				
				for (var label in labels) {
					var colorCode = "#" + Math.round(Math.random() * 0xffffff).toString(16);
					var dataset = {};
					dataset['label'] = labels[label];
					var data = obj.filter((val) => {
						return val.stationName == labels[label]}).map((val) => {
							return {
								x: val.depMonth, y: val.cnt }});
					
					dataset.data = data;
					dataset.borderColor = colorCode;
					dataset.backgroundColor = colorCode;
					dataset.fill = false;
					datasets.push(dataset);
				}

				return datasets;
			}

			/* filter chart */
			function changeData() {
				var selectedYear = document.getElementById("select_year").value;
				var newDatas = datas.filter(e => {
					return e.depMonth.substring(0, 4) === selectedYear
				});

				var showData = selectedYear === '전체' ? datas : newDatas;

				myChart.destroy();
				myChart = new Chart(ctx, {
					type : 'line', 
					data : {
						datasets : getDataset(showData),
					},
					options : {
						responsive : false
					}
				});

				myChart.update();
			}

			//document.getElementById("chart-div").onclick = clickHandler;

			function clickHandler(evt){

			    const points = myChart.getElementsAtEventForMode(evt, 'nearest', { intersect: true}, true);

			    if (points.length){
			        const firstPoint = points[0];

			        var label = myChart.data.datasets[firstPoint.datasetIndex].label;
			        var value = myChart.data.datasets[firstPoint.datasetIndex].data[firstPoint.index];
			        //var month = value.x.substring(4) + '월';
			        var month = value.x;
			       
			    }

			    var target = {stationName: label, depMonth: month};

			    $.ajax({
					url : "/mngr/send/stockOutChartMonth",
					type : "GET",
					data : target,
					success : function(data) {
						console.log(data.list);
						getClickedChart(data.list);
					},
					error: function(request,status,error) {
						console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					}
				})
			}

			var clickedChart = null;
			var clickedCtx = document.getElementById('chart2').getContext('2d'); 

			function getClickedChart(obj) {

				var config = {
						type : 'line',
						data : {
							datasets : getDataset2(obj),
						},
						options : {
							responsive : false,
						}
				};

				if (clickedChart != null) {
					$("#chart2").remove();
					$("#chart-div2").append('<canvas id="chart2" width="900" height="400"></canvas>');

					if (document.getElementById('chart-div2') != null) {
						clickedCtx = document.getElementById('chart2').getContext('2d');
						clickedChart = new Chart(clickedCtx, config);
					}
				} else {
					clickedChart = new Chart(clickedCtx, config);
				}
				
			}

			function getDataset2(obj) {
				var labels = [...(new Set(obj.map((val) => {return val.STATION_NAME})))];
				var datasets = [];
				
				for (var label in labels) {
					var colorCode = "#" + Math.round(Math.random() * 0xffffff).toString(16);
					var dataset = {};
					dataset['label'] = labels[label];
					var data = obj.filter((val) => {
						return val.STATION_NAME == labels[label]}).map((val) => {
							return {
								x: val.DEP_DATE, y: val.CNT }});
					
					dataset.data = data;
					dataset.borderColor = colorCode;
					dataset.backgroundColor = colorCode;
					dataset.fill = false;
					datasets.push(dataset);
				}

				return datasets;
			}

			
			$('input[name=date-range-picker]').daterangepicker({
				'applyClass' : 'btn-sm btn-success',
				'cancelClass' : 'btn-sm btn-default',
				locale: {
					applyLabel: 'Apply',
					cancelLabel: 'Cancel',
				}
			})
			
			//or change it into a date range picker
			$('.input-daterange').datepicker({
				autoclose:true,
				todayHighlight:true,
				format:'yyyy-mm-dd'
			})
			


		</script>
		<!-- script addon end --> 
		
	</body>
</html>