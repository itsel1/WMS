<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<style>
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
   
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
     .colorBlack {color:#000000 !important;}
    </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
   
	</head> 
	<title>반품 합격 리스트</title>
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
								반품
							</li>
							<li class="active">반품 검수 합격 리스트</li>
						</ul><!-- /.breadcrumb -->
					</div>
	               
					<div class="page-content">
						<div class="page-header">
							<h1>
								반품 검수 합격 리스트
							</h1>
						</div>
						<div id="inner-content-side" >
							<form name="returnForm" id="returnForm">
		                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		                    	<div id="table-contents" class="table-contents"">
									<table id="dynamic-table" class="table table-bordered table-hover containerTable" style="vertical-align: middle;">
										<c:forEach items="${returnInspList}" var="inspList" varStatus="status">
											<colgroup>
				                    	 		<col width="1%"/>
												<col width="11%"/> 
												<col width="14%"/>
												<col width="10%"/>
												<col width="9%"/>
												<col width="16%"/>
												<col width="17%"/>
												<col width="13%"/>
												<col width="9%"/>
											</colgroup>
											<tr>
			            	 					<td rowspan="7" style="vertical-align: middle;">
			            	 						${status.count }
			            	 					</td>
				                    	 		<td>
				                    	 			KOBL NO : ${inspList.KOBL_NO} 
				                    	 		</td>
				                    	 		
				                    	 		
				                    	 		<td>
				                    	 			ORDER_NO : ${inspList.ORDER_NO }
				                    	 		</td>
				                    	 		<td>출발지/도착지 : KR/${inspList.DSTN_NATION }</td>
				                    	 		<td>주문일자 : ${inspList.ORDER_DATE}</td>
				                    	 		<td>
				                    	 			RETURN TRK COM : ${inspList.RE_TRK_COM}
			                    	 			</td>
				                    	 		<td>
				                    	 			RETURN TRK NO  : ${inspList.RE_TRK_NO}
				                    	 		</td>
				                    	 		<td>
				                    	 			구분 : 주기 발송<%-- ${inspList.RETURN_TYPE } --%>
				                    	 		</td>
				                    	 		
				                    	 		<td>USER ID : ${inspList.W_USER_ID}</td>
			                    	 		</tr>
				                    	 	<tr>
				                    	 		<td colspan="8" style="text-align: center;">
				                    	 			반송 신청인 정보
				                    	 		</td>
				                    	 	</tr>
				                    	 	<tr>
				                    	 		<td>정산 ID : ${inspList.CALCULATE_ID}</td>
				                    	 		<td>
				                    	 			반송인 : ${inspList.PICKUP_NAME }
				                    	 		</td>
				                    	 		
				                    	 		<td>
				                    	 			연락처1: ${inspList.PICKUP_TEL }<br/>
				                    	 			연락처2: ${inspList.PICKUP_MOBILE }
				                    	 		</td>
				                    	 		<td>
				                    	 			우편번호: ${inspList.PICKUP_ZIP }
				                    	 		</td>
				                    	 		<td>
				                    	 			주소: ${inspList.PICKUP_ADDR }
				                    	 		</td>
				                    	 		<td>
				                    	 			영문 주소: ${inspList.PICKUP_ENG_ADDR }
				                    	 		</td>
				                    	 		<td>
				                    	 			사유 : 단순 변심<%-- ${inspList.RETURN_REASON } --%>
				                    	 		</td>
				                    	 		
				                    	 		<td>
				                    	 			위약반송 여부 : ${inspList.TAX_TYPE }
				                    	 		</td>
				                    	 	</tr>
				                    	 	<tr>
				                    	 		<td colspan="8" style="text-align: center;">
				                    	 			수취 업체 정보
				                    	 		</td>
				                    	 	</tr>
				                    	 	<tr>
				                    	 		<td>
				                    	 			SELLER ID : ${inspList.SELLER_ID}
				                    	 		</td>
				                    	 		<td>
				                    	 			업체명(수취인 명) : ${inspList.SENDER_NAME }
				                    	 		</td>
				                    	 		
				                    	 		<td>
				                    	 			연락처1: ${inspList.SENDER_TEL }<br/>
				                    	 			연락처2: ${inspList.SENDER_HP }
				                    	 		</td>
				                    	 		<td>
				                    	 			우편번호: ${inspList.SENDER_ZIP }
				                    	 		</td>
				                    	 		<td>
				                    	 			주소: ${inspList.SENDER_ADDR } ${inspList.SENDER_BUILD_NM}
				                    	 		</td>
				                    	 		<td>
				                    	 			STATE : ${inspList.SENDER_STATE }
				                    	 		</td>
				                    	 		<td>
				                    	 			CITY : ${inspList.SENDER_CITY }
				                    	 		</td>
				                    	 		<td>E-MAIL : ${inspList.SENDER_EMAIL}</td>
				                    	 	</tr>
				                    	 	<tr>
				                    	 		<td colspan="8" style="text-align: center;">
				                    	 			상품 정보
				                    	 		</td>
				                    	 	</tr>
				                    	 	<tr>
				                    	 		<td colspan="8" style="vertical-align: middle;">
				                    	 			<div class="row">
				                    	 				<div class="col-xs-12">
				                    	 					<c:if test="${!empty inspList.ERROR_MSG}">
				                    	 						<font color="red" style="font-size: large;">
				                    	 							${inspList.ERROR_MSG}
				                    	 						</font>
				                    	 					</c:if>
				                    	 				</div>
				                    	 				<div class="col-xs-12">
						                    	 			<table class="table table-bordered table-hover containerTable">
						                    	 				<colgroup>
									                    	 		<col width="1%"/>
																	<col width="11%"/>
																	<col width="82%"/>
																</colgroup>
						                    	 				<c:forEach items="${inspList.orderItemList}" var="inspItemList" varStatus="itemStatus">
							                    	 				<tr>
							                    	 					<td style="vertical-align: middle;">
							                    	 						${itemStatus.count }
							                    	 					</td>
							                    	 					<td style="text-align: center;">
							                    	 						<img src="${inspItemList.ITEM_IMG_URL }" width="100px" height="100px" loading="lazy">
							                    	 					</td>
							                    	 					<td  style="vertical-align: middle;">
							                    	 						<div class="row">
							                    	 							<div class="col-xs-2">
							                    	 								<div class="row">
							                    	 									<div class="col-xs-4">
							                    	 										브랜드 :
							                    	 									</div>
							                    	 									<div class="col-xs-8 text-left">
							                    	 										${inspItemList.BRAND }	
							                    	 									</div>
							                    	 								</div>
							                    	 							</div>
							                    	 							<div class="col-xs-1">
							                    	 								<div class="row">
							                    	 									<div class="col-xs-8">
							                    	 										제조국 :	
							                    	 									</div>
							                    	 									<div class="col-xs-4 text-left">
							                    	 										${inspItemList.MAKE_CNTRY }	
							                    	 									</div>
							                    	 								</div>
							                    	 							</div>
							                    	 							<div class="col-xs-3">
							                    	 								<div class="row">
							                    	 									<div class="col-xs-3">
							                    	 										제조사 :	
							                    	 									</div>
							                    	 									<div class="col-xs-9 text-left">
							                    	 										${inspItemList.MAKE_COM }	
							                    	 									</div>
							                    	 								</div>
							                    	 							</div>
							                    	 							<div class="col-xs-6">
																					<div class="row">
																						<div class="col-xs-2">
							                    	 										상품명 :	
																						</div>
																						<div class="col-xs-10 text-left">
							                    	 										${inspItemList.ITEM_DETAIL }	
																						</div>
																					</div>
																				</div>
																			</div>
																			<div class="row">
																				<br/>
																			</div>
																			<div class="row">
																				<div class="col-xs-2">
							                    	 								<div class="row">
							                    	 									<div class="col-xs-4">
							                    	 										단가 :	
							                    	 									</div>
							                    	 									<div class="col-xs-8 text-left">
							                    	 										${inspItemList.UNIT_VALUE } ${inspItemList.UNIT_CURRENCY }		
							                    	 									</div>
							                    	 								</div>
							                    	 							</div>
							                    	 							<div class="col-xs-1">
							                    	 								<div class="row">
							                    	 									<div class="col-xs-8">
							                    	 										수량 :	
							                    	 									</div>
							                    	 									<div class="col-xs-4 text-left">
							                    	 										${inspItemList.ITEM_CNT }	
							                    	 									</div>
							                    	 								</div>
							                    	 							</div>
							                    	 							<div class="col-xs-3">
							                    	 								<div class="row">
							                    	 									<div class="col-xs-3">
							                    	 										금액 :	
							                    	 									</div>
							                    	 									<div class="col-xs-9 text-left">
							                    	 										${inspItemList.UNIT_VALUE * inspItemList.ITEM_CNT } ${inspItemList.UNIT_CURRENCY }
							                    	 									</div>
							                    	 								</div>
							                    	 							</div>
																			</div>
																		</td>
																		<c:if test="${itemStatus.first}">
																			<td rowspan="${fn:length(inspList.orderItemList)}" style="vertical-align: middle; border:none;">
																				<c:if test="${inspList.STATE eq 'C003' }">
																					<font color="red">승인 대기</font>
																				</c:if>
																				<c:if test="${inspList.STATE eq 'C004' }">
																					<input class="btn btn-sm btn-success btn-white" id="btnReturnInsp" name="btnReturnInsp" onclick="inspOut('${inspList.ORDER_REFERENCE}')" type="button" value="출고하기"/>
																				</c:if>
																			</td>
																		</c:if>
																	</tr>
																</c:forEach>
															</table>
														</div>
													</div>
												</td>
											</tr>
										</c:forEach>
		                    	 	</table>
		                    	 </div>
							</form>
						</div>
					</div>
	                <%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
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
      <!-- excel upload 추가 -->
         jQuery(function($) {
            $(document).ready(function() {
               $("#14thMenu-2").toggleClass('open');
               $("#14thMenu-2").toggleClass('active'); 
               $("#14thTwo-3").toggleClass('active');   
            });

            function LoadingWithMask() {
               var maskHeight = $(document).height();
               var maskWidth = window.document.body.clientWidth;

               var mask ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
                 
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

           /*  $("#btnReturnInsp").on('click',function(e){
                alert("ASD");
                
				//location.href="/asd";
			}) */
            
         });

         function inspOut(orderReference){
        	 location.href="/mngr/aplctList/return/inspListOut/"+orderReference;
         }
      </script>
      <!-- script addon end -->

   </body>
</html>