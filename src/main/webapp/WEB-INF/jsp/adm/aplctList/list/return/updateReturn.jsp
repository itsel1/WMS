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
		  
		  #searchBtn {
		  border: 1px solid #dcdcdc;
		  burder-radius: 2px;
		  }
		  
		  #searchBtn:hover {
		  	background-color: #d8d8d8;
			color: white;
		  }
		  
		  .button {
			  background-color: rgb(67,142,185); /* Green */
			  border: none;
			  color: white;
			  padding: 0px;
			  text-align: center;
			  text-decoration: none;
			  display: inline-block;
			  font-size: 12px;
			  margin: 4px 2px;
			  cursor: pointer;
			  -webkit-transition-duration: 0.4s; /* Safari */
			  transition-duration: 0.4s;
			  width: 100px;
			  height: 30px;
			}
			
			.button2:hover {
			  box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19);
			}
		 </style>
		<!-- basic scripts -->
		<!-- ace scripts -->

	</head>
	<title>반품정보</title>
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
							<li>반품</li>
							<li onclick="location.href='/mngr/aplctList/return/returnList'" style="cursor:pointer">반품 리스트</li>
							<li class="active">반품정보</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div class="page-content">
						<div class="page-header">
							<h1>
								반품정보
							</h1>
						</div>
						<div id="inner-content-side" >
						
						<div>
							<form name="returnForm" id="returnForm" action="/mngr/aplctList/return/updateOrder" method="get">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							
							
						<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
							
							
							<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
							<h4>반품정보</h4>
							<div class="profile-user-info  form-group hr-8">
								<div class="profile-info-name koblNo">송장 번호</div>
								<div class="profile-info-value">
									<input class="col-xs-12 shipperVal" type="text" name="koblNo" id="koblNo" value="${vo.koblNo}" style="width:200px; color:red; font-weight:bold;" readonly/>
								</div>	
								<div class="profile-info-name sellerId">USER ID</div>
								<div class="profile-info-value">
										<input class="col-xs-12 shipperVal" type="text" name="sellerId" id="sellerId" value="${vo.sellerId}" style="width:200px;" readonly/>
								</div>
								<div class="profile-info-name nno">NNO</div>
								<div class="profile-info-value">
										<input class="col-xs-12 shipperVal" type="text" name="nno" id="nno" value="${vo.nno}" style="width:200px;" readonly/>
								</div>
								
							</div>
								<div class="profile-user-info  form-group hr-8">
									<div class="profile-info-name dstnNation" ">도착지 정보</div>
									<div class="profile-info-value">
											<input class="col-xs-12 dstnNation" type="text" name="dstnNation" id="dstnNation" value="${vo.dstnNation}" style="width:200px;" />
									</div>
									<div class="profile-info-name orgStation" >출발지 정보</div>
									<div class="profile-info-value">
											<input class="col-xs-12 orgStation" type="text" name="orgStation" id="orgStation" value="${vo.orgStation}" style="width:200px;" />
									</div>
								</div>
							</div>
							
							<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
							<h4>반품 담당자 정보</h4>
							<div class="profile-user-info  form-group hr-8">
								<div class="profile-info-name attnName" style="width:150px;">반품 담당자 이름</div>
								<div class="profile-info-value">
									<input class="col-xs-12 attnName" type="text" name="attnName" id="attnName" value="${vo.attnName}" style="width:200px;" />
								</div>	
								<div class="profile-info-name attnTel" style="width:150px;">반품 담당자 연락처</div>
								<div class="profile-info-value">
										<input class="col-xs-12 attnTel" type="text" name="attnTel" id="attnTel" value="${vo.attnTel}" style="width:200px;"/>
								</div>
							</div>
							</div>
							
							<div class="col-xs-12 col-lg-12 form-group" style="font-size: 3;">
							<h4>수거정보</h4>
							<div class="profile-user-info  form-group hr-8">
									<div class="profile-info-name pickupName" style="width:150px;">수거 담당자 이름</div>
									<div class="profile-info-value">
										<input class="col-xs-12 pickupName" type="text" name="pickupName" id="pickupName" value="${vo.pickupName}" style="width:200px;" />
									</div>	
									<div class="profile-info-name pickupTel" style="width:150px;">수거 담당자 연락처</div>
									<div class="profile-info-value">
											<input class="col-xs-12 pickupTel" type="text" name="pickupTel" id="pickupTel" value="${vo.pickupTel}" style="width:200px;"/>
									</div>
								</div>
								<div class="profile-user-info  form-group hr-8">
									<div class="profile-info-name pickupZip" style="width:150px;">수거시 우편번호</div>
									<div class="profile-info-value">
											<input class="col-xs-12 pickupZip" type="text" name="pickupZip" id="pickupZip" value="${vo.pickupZip}" style="width:200px;" />
									</div>
									<div class="profile-info-name pickupAddr" style="width:150px;">수거시 주소</div>
									<div class="profile-info-value">
											<input class="col-xs-12 pickupAddr" type="text" name="pickupAddr" id="pickupAddr" value="${vo.pickupAddr}" style="width:200px;" />
									</div>
								</div>
								<div class="profile-user-info  form-group hr-8">
									<div class="profile-info-name pickupAddrDetail" style="width:150px;">수거시 상세주소</div>
									<div class="profile-info-value">
											<input class="col-xs-12 pickupAddrDetail" type="text" name="pickupAddrDetail" id="pickupAddrDetail" value="${vo.pickupAddrDetail}" style="width:700px;" />
									</div>
									
								</div>
								<div>
										
											<input type="button" id="updateItemBtn" value="정보수정" class="button button2">
											
											
										</div>
							</div>
						
						</div>
							</form>
						</div>
					</div>
						
				<div>
							
						
						
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
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		<script type="text/javascript">
		
			
			jQuery(function($) {
				$("#14thMenu-2").toggleClass('open');
				$("#14thMenu-2").toggleClass('active'); 
				$("#14thOne-2").toggleClass('active');	
				});
			
				$("#updateItemBtn").on('click', function(e){
					 $("#returnForm").attr("action","/mngr/aplctList/return/updateOrderReturn");
					 $("#returnForm").attr("method", "POST");
					 $("#returnForm").submit();
					
					
				 });
				
				var myTable = 
				$('#dynamic-table')
				.DataTable( {
					"dom": 't',
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
				
				
			
			
			});
		</script>
		<!-- script addon end -->
	</body>
</html>
