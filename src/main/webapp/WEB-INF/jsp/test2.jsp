<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	 </style>
	<!-- basic scripts -->
	</head> 
	<title>사용자 관리</title>
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
			<%@ include file="/WEB-INF/jsp/importFile/userSideMenu.jsp" %>
			<!-- SideMenu End -->
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="#">대 메뉴</a>
							</li>
	
							<li>
								<a href="#">중 메뉴</a>
							</li>
							<li class="active">소 메뉴</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div id="inner-content-side" >
							<div id="search-div">
								<br>
								<form name="search-button" id="search-botton" method="get" action="/test2">
									<div class="row">
										<div class="col-xs-12 col-sm-11 col-md-10">
											<span class="input-group" style="display: inline-flex; width:100%">
												<select style="height: 34px">
													<option>USER ID</option>
												</select>
												<input type="text" class="form-control" name="keywords" placeholder="${searchKeyword}" style="width:100%; max-width: 300px"/>
													<button type="submit" class="btn btn-default no-border btn-sm">
														<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
													</button>
											</span>
										</div>
									</div>
								</form>
								<br>
								<form name="rgstr-button" id="search-botton" method="post" action="/test2.do">
									<div id="rgstr-user" style="text-align: right">
										<button type="submit" class="btn btn-sm btn-primary">
										 등록하기
										</button>
									</div>
								</form>
							</div>
							<br>
							<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;">
								<div class="display" style="width:2500px;">
									<table id="dynamic-table" class="table table-bordered table-hover">
										<colgroup>
											<col width="20"/>  <!-- checkBox -->
											<col width="150"/> <!-- ID -->
											<col width="150"/> <!-- 거래처 명 -->
											<col width="110"/> <!-- 전화번호 -->
											<col width="*"/>   <!-- 주소 -->
											                   
											<col width="150"/> <!-- 대표자 이름 -->
											<col width="110"/> <!-- 대표자 HP -->
											<col width="170"/> <!-- 대표자 E-mail -->
											<col width="170"/> <!-- 대표자 NateOn -->
											<col width="170"/> <!-- 대표자 Kakao -->
											                   
											<col width="150"/> <!-- 담당자 이름 -->
											<col width="110"/> <!-- 담당자 HP -->
											<col width="170"/> <!-- 담당자 E-mail -->
											<col width="170"/> <!-- 담당자 NateOn -->
											<col width="170"/> <!-- 담당자 KaKao -->
											                   
											<col width="50"/>  <!-- 승인여부 -->
										</colgroup>
										<thead>
											<tr>
												<th rowspan="2" class="center">
													<label class="pos-rel">
														<input type="checkbox" class="ace"/>
														<span class="lbl"></span>
													</label>
												</th>
												<th rowspan="2" class="center colorBlack" >아이디</th>
												<th rowspan="2" class="center colorBlack" >거래처 명</th>
												<th rowspan="2" class="center colorBlack" >전화번호</th>
												<th rowspan="2" class="center colorBlack" >주소</th>
												<th colspan="5" class="center colorBlack" >대표자</th>
												<th colspan="5" class="center colorBlack" >담당자</th>
												<th rowspan="2" class="center colorBlack" >승인여부</th>
											</tr>
											<tr>
												<th class="center colorBlack" >이름</th>
												<th class="center colorBlack" >HP</th>
												<th class="center colorBlack" >Email</th>
												<th class="center colorBlack" >NateOn</th>
												<th class="center colorBlack" >KakaoTalk</th>
												<th class="center colorBlack" >이름</th>
												<th class="center colorBlack" >HP</th>
												<th class="center colorBlack" >Email</th>
												<th class="center colorBlack" >NateOn</th>
												<th class="center colorBlack" >KakaoTalk</th>
												
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${userInfo}" var="test">
												<tr>
													<td class="center" style="word-break:break-all;">
														<label class="pos-rel">
															<input type="checkbox" class="ace" />
															<span class="lbl"></span>
														</label>
													</td>
													<td class="center" style="word-break:break-all;">
														${test.userId}
													</td>
													<td class="center" style="word-break:break-all;">
														${test.acntName}
													</td>
													<td class="center" style="word-break:break-all;">
														${test.telNum}
													</td>
													<td class="center" style="word-break:break-all;">
														${test.adres}
													</td>
													<td class="center" style="word-break:break-all;">
														${test.rprsnName}
													</td>
													<td class="center" style="word-break:break-all;">
														${test.rprsnHpNum}
													</td>
													<td class="center" style="word-break:break-all;">
														${test.rprsnEmail}
													</td>
													<td class="center" style="word-break:break-all;">
														${test.rprsnNateon}
													</td>
													<td class="center" style="word-break:break-all;">
														${test.rprsnKakao}
													</td>
													<td class="center" style="word-break:break-all;">
														${test.mngrName}
													</td>
													<td class="center" style="word-break:break-all;">
														${test.mngrHpNum}
													</td>
													<td class="center" style="word-break:break-all;">
														${test.mngrEmail}
													</td>
													<td class="center" style="word-break:break-all;">
														${test.mngrNateon}
													</td>
													<td class="center" style="word-break:break-all;">
														${test.mngrKakao}
													</td>
													<td class="center" style="word-break:break-all;">
														${test.aprvlYn}
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!-- Main container End-->
		
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
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
				//initiate dataTables plugin
				
				var myTable = 
				$('#dynamic-table')
				.DataTable( {
					"paging":   false,
			        "ordering": false,
			        "info":     false,
			        "searching": false,
			        "scrollY" : 500,
					select: {
						style: 'multi'
					},
					"language":{
						"zeroRecords": "조회 결과가 없습니다.",
					}
			    } );
			
				
				myTable.on( 'select', function ( e, dt, type, index ) {
					if ( type === 'row' ) {
						$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', true);
					}
				} );
				myTable.on( 'deselect', function ( e, dt, type, index ) {
					if ( type === 'row' ) {
						$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', false);
					}
				} );
			
			
			
			
				/////////////////////////////////
				//table checkboxes
				$('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);
				
				//select/deselect all rows according to table header checkbox
				$('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function(){
					var th_checked = this.checked;//checkbox inside "TH" table header
					
					$('#dynamic-table').find('tbody > tr').each(function(){
						var row = this;
						if(th_checked) myTable.row(row).select();
						else  myTable.row(row).deselect();
					});
				});
				
				//select/deselect a row when the checkbox is checked/unchecked
				$('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
					var row = $(this).closest('tr').get(0);
					if(this.checked) myTable.row(row).deselect();
					else myTable.row(row).select();
				});
			
			})
		</script>
		<!-- script addon end -->
	</body>
</html>
